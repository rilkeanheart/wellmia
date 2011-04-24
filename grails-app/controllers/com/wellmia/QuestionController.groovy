
package com.wellmia


import com.wellmia.security.SecUser
import grails.converters.JSON
import org.apache.commons.lang.WordUtils

class QuestionController {
    
    def springSecurityService
    def categorizeService

    def index = { redirect(action:list,params:params) }

    // the delete, save and update actions only accept POST requests
    static allowedMethods = [delete:'POST', save:'POST', update:'POST']

    def list = {
        params.max = Math.min( params.max ? params.max.toInteger() : 10,  100)
        [ questionInstanceList: Question.list( params ), questionInstanceTotal: Question.count() ]
    }

    def show = {
        def questionInstance = Question.get( params.id )

        if(!questionInstance) {
            flash.message = "Question not found with id ${params.id}"
            redirect(action:list)
        }
        else { return [ questionInstance : questionInstance ] }
    }

    def delete = {
		Question.withTransaction {
	        def questionInstance = Question.get( params.id )
	        if(questionInstance) {
	            try {
	                questionInstance.delete(flush:true)
	                flash.message = "Question ${params.id} deleted"
	                redirect(action:list)
	            }
	            catch(org.springframework.dao.DataIntegrityViolationException e) {
	                flash.message = "Question ${params.id} could not be deleted"
	                redirect(action:show,id:params.id)
	            }
	        }
	        else {
	            flash.message = "Question not found with id ${params.id}"
	            redirect(action:list)
	        }			
		}
    }

    def edit = {
        def questionInstance = Question.get( params.id )

        if(!questionInstance) {
            flash.message = "Question not found with id ${params.id}"
            redirect(action:list)
        }
        else {
            return [ questionInstance : questionInstance ]
        }
    }

    def update = {
		Question.withTransaction {
	        def questionInstance = Question.get( params.id )
	        if(questionInstance) {
	            if(params.version) {
	                def version = params.version.toLong()
	                if(questionInstance.version > version) {
	                    
	                    questionInstance.errors.rejectValue("version", "question.optimistic.locking.failure", "Another user has updated this Question while you were editing.")
	                    render(view:'edit',model:[questionInstance:questionInstance])
	                    return
	                }
	            }
	            questionInstance.properties = params
	            if(!questionInstance.hasErrors() && questionInstance.save()) {
	                flash.message = "Question ${params.id} updated"
	                redirect(action:show,id:questionInstance.id)
	            }
	            else {
	                render(view:'edit',model:[questionInstance:questionInstance])
	            }
	        }
	        else {
	            flash.message = "Question not found with id ${params.id}"
	            redirect(action:list)
	        }			
		}
    }

    def create = {
        def questionInstance = new Question()
        questionInstance.properties = params
        return ['questionInstance':questionInstance]
    }

    def save = {
        def questionInstance = new Question(params)
		Question.withTransaction {
	        if(questionInstance.save(flush:true)) {
	            flash.message = "Question ${questionInstance.id} created"
	            redirect(action:show,id:questionInstance.id)
	        }
	        else {
	            render(view:'create',model:[question:questionInstance])
	        }
		}
    }

    def saveQuestionAjax = {
      //TODO:  Move this code and create code into a a reusable Service
      if(springSecurityService.isLoggedIn()) {
        def principal = springSecurityService.principal

        def secuser = SecUser.get(principal.id)
        ConsumerProfile thisConsumer = secuser.consumerProfile
        def questionInstance

        //String questionContent = params.subject + ' ' + params.content
        def taglist = new String(params.tags).split(", ")
        //def unvalidatedCategories = taglist as Set<String>

        //def categories = categorizeService.categorizeText(thisConsumer, questionContent)
        def categories = categorizeService.validateCategories(taglist as Set<String>)

        if(!categories.isEmpty()) {
            // Create & persist question
            questionInstance = new Question(subject: params.subject,
                                          content: params.content,
                                          category: categories,
                                          memberCreatorId: thisConsumer.id,
                                          memberCreatorUsername: secuser.username)

            boolean questionAdded = false
            Question.withTransaction {
              if(questionInstance.save(flush:true)) {
                questionAdded = true
            }

            if(questionAdded) {
                // Update consumer's profile
                ConsumerProfile.withTransaction {
                    // Mark user's feed as stale so the newsFeed is guaranteed to refresh if reloaded
                    thisConsumer.feedIsStale = true
                    categories.each {item ->
                        thisConsumer.getInterestTags()?.add(item)
                    }
                }
                // Send ajax version of added question
                render(template:"/question/question", model: [question: questionInstance])
            } else {
              //Question did not save for some reason)
              response.setStatus(500)
              render( contentType:'text/plain', text: "Could not commit question")
            }
          }

        } else {
          //Question was not specific enough (or doesn't pertain to interest categories)
          response.setStatus(500)
          render( contentType:'text/plain', text: "Could not find category")
        }
      } else  {
        redirect(uri: '/login')
      }
    }

    def addAnswerAjax = {
        //TODO:  Move this code and create code into a a reusable Service
        if(springSecurityService.isLoggedIn()) {
          def principal = springSecurityService.principal

          def secuser = SecUser.get(principal.id)
          ConsumerProfile thisConsumer = secuser.consumerProfile
          def paramsList = params

          //TODO:  remove - def commentableItemType = Class.forName(params.commentableItemType)
          def questionId = params.commentableItemId
          def List<Comment> answerList
          def question
          def answerInstance

          Answer.withTransaction {
            question = Question.get(questionId)
            answerInstance = new Answer(content: params.content,
                                        memberCreatorId: thisConsumer.id,
                                        memberCreatorUsername: secuser.username,
                                        questionId: questionId)
            question.getAnswers().add(answerInstance)
          }

          render(template:"/answer/answers", model: [question: question])
          //TODO:  remove - render(template:"/comment/comment", model: [comment: commentInstance])

        } else  {
          redirect(uri: '/login')
        }
    }

    def ajaxQuestionTagAutocomplete = {
        if(springSecurityService.isLoggedIn()) {

            def jsonData = []
            //def paramsList = params
            params.max = Math.min( params.max ? params.max.toInteger() : 10,  100)

            if (params.term?.length() > 2) {
                def tagSearchTerm = params.term
                tagSearchTerm = WordUtils.capitalizeFully(tagSearchTerm, " -.".toCharArray())

                tagSearchTerm.toLowerCase();
                //setIfMissing 'max', 10, 100

                //TODO:  Add aliases into search term to cover drugs
                def results = CategoryTag.executeQuery(
                      "SELECT DISTINCT c " +
                      "FROM CategoryTag c " +
                      "WHERE LOWER(c.category) LIKE :name " +
                      //" OR LOWER(c.alias) LIKE :name " +
                      "ORDER BY c.category",
                      [name: "${tagSearchTerm}%"],
                      [max: params.max])

                for (result in results) {
                  jsonData << [label : result.toString(), value: result.category]
                }
            }

            render text: jsonData as JSON, contentType: 'text/plain'

        } else  {
          redirect(uri: '/login')
        }
    }

    def addAddToNotifyListAjax = {
        //TODO:  Move this code and create code into a a reusable Service
        def jsonData = []
        boolean bIsSaved = false

        if(springSecurityService.isLoggedIn()) {
            def principal = springSecurityService.principal

            def secuser = SecUser.get(principal.id)

            def questionItemid = params.newsItemId
            def questionItem

            NewsItem.withTransaction {
              questionItem = NewsItem.get(questionItemid)
              questionItem.notifyList.add(secuser.email)
              if(questionItem.save())
                bIsSaved = true
            }
        }

        if (bIsSaved)
            jsonData << [isSaved: "true"]
        else
            jsonData << [isSaved: "false"]


        render text: jsonData as JSON, contentType: 'text/plain'

    }

    protected void setIfMissing(String paramName, long valueIfMissing, Long max = null) {
      long value = (params[paramName] ?: valueIfMissing).toLong()
      if (max) {
          value = Math.min(value, max)
      }
      params[paramName] = value
  }

}
