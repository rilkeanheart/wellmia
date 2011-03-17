
package com.wellmia


import com.wellmia.security.SecUser

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

        String questionContent = params.subject + ' ' + params.content

        def categories = categorizeService.categorizeText(thisConsumer, questionContent)

        if(!categories.isEmpty()) {
          // Create & persist question
          questionInstance = new Question(subject: params.subject,
                                          content: params.content,
                                          category: categories,
                                          memberCreatorId: thisConsumer.id,
                                          memberCreatorUsername: secuser.username)
              Question.withTransaction {
                  if(questionInstance.save(flush:true)) {
                    // Mark user's feed as stale so the newsFeed is guaranteed to refresh if reloaded
                    thisConsumer.feedIsStale = true

                    // Send ajax version of added question
                    render(template:"/question/question", model: [question: questionInstance])
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

    def addCommentAjax = {
        //TODO:  Move this code and create code into a a reusable Service
        if(springSecurityService.isLoggedIn()) {
          def principal = springSecurityService.principal

          def secuser = SecUser.get(principal.id)
          ConsumerProfile thisConsumer = secuser.consumerProfile
          def paramsList = params

          def commentableItemType = Class.forName(params.commentableItemType)
          def commentableItemid = params.commentableItemId
          def List<Comment> commentList
          def question
          def commentInstance

          Comment.withTransaction {
            question = Question.get(commentableItemid)
            commentInstance = new Comment(content: params.content,
                                          memberCreatorId: thisConsumer.id,
                                          memberCreatorUsername: secuser.username,
                                          commentableItemId: commentableItemid,
                                          commentableItemType: commentableItemType)
            question.getComments().add(commentInstance)
          }

          //newsItem = NewsItem.get(commentableItemid)

          render(template:"/comment/comments", model: [commentableItem: question])
          //render(template:"/comment/comment", model: [comment: commentInstance])

        } else  {
          redirect(uri: '/login')
        }
    }

}
