package com.wellmia

import grails.converters.JSON
import com.wellmia.security.SecUser
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import org.codehaus.groovy.grails.commons.ApplicationHolder

class NewsItemController {
    
    def newsItemService
    def springSecurityService

    def index = { redirect(action:list,params:params)
    }

    // the delete, save and update actions only accept POST requests
    static allowedMethods = [delete:'POST', save:'POST', update:'POST']

    def list = {
        params.max = Math.min( params.max ? params.max.toInteger() : 10,  100)
        [ newsItemInstanceList: NewsItem.list( params ), newsItemInstanceTotal: NewsItem.count() ]
    }

    def show = {
        def newsItemInstance = NewsItem.get( params.id )

        if(!newsItemInstance) {
            flash.message = "NewsItem not found with id ${params.id}"
            redirect(action:list)
        }
        else { return [ newsItemInstance : newsItemInstance ] }
    }

    def delete = {
		NewsItem.withTransaction {
	        def newsItemInstance = NewsItem.get( params.id )
	        if(newsItemInstance) {
	            try {
	                newsItemInstance.delete(flush:true)
	                flash.message = "NewsItem ${params.id} deleted"
	                redirect(action:list)
	            }
	            catch(org.springframework.dao.DataIntegrityViolationException e) {
	                flash.message = "NewsItem ${params.id} could not be deleted"
	                redirect(action:show,id:params.id)
	            }
	        }
	        else {
	            flash.message = "NewsItem not found with id ${params.id}"
	            redirect(action:list)
	        }			
		}
    }

    def edit = {
        def newsItemInstance = NewsItem.get( params.id )

        if(!newsItemInstance) {
            flash.message = "NewsItem not found with id ${params.id}"
            redirect(action:list)
        }
        else {
            return [ newsItemInstance : newsItemInstance ]
        }
    }

    def update = {
		NewsItem.withTransaction {
	        def newsItemInstance = NewsItem.get( params.id )
	        if(newsItemInstance) {
	            if(params.version) {
	                def version = params.version.toLong()
	                if(newsItemInstance.version > version) {
	                    
	                    newsItemInstance.errors.rejectValue("version", "newsItem.optimistic.locking.failure", "Another user has updated this NewsItem while you were editing.")
	                    render(view:'edit',model:[newsItemInstance:newsItemInstance])
	                    return
	                }
	            }
	            newsItemInstance.properties = params
	            if(!newsItemInstance.hasErrors() && newsItemInstance.save()) {
	                flash.message = "NewsItem ${params.id} updated"
	                redirect(action:show,id:newsItemInstance.id)
	            }
	            else {
	                render(view:'edit',model:[newsItemInstance:newsItemInstance])
	            }
	        }
	        else {
	            flash.message = "NewsItem not found with id ${params.id}"
	            redirect(action:list)
	        }			
		}
    }

    def create = {
        def newsItemInstance = new NewsItem()
        newsItemInstance.properties = params
        return ['newsItemInstance':newsItemInstance]
    }

    def save = {
        def newsItemInstance = new NewsItem(params)
		NewsItem.withTransaction {
	        if(newsItemInstance.save(flush:true)) {
	            flash.message = "NewsItem ${newsItemInstance.id} created"
	            redirect(action:show,id:newsItemInstance.id)
	        }
	        else {
	            render(view:'create',model:[newsItemInstance:newsItemInstance])
	        }
		}
    }

    def showDetails = {
        def newsItemId = params.id
        def newsItemInstance
        boolean bIsFollowed = false


        NewsItem.withTransaction {
            if(newsItemId) {
                newsItemInstance =  NewsItem.get(newsItemId)
                if(newsItemInstance) {
                    newsItemInstance.numberOfViews = newsItemInstance.numberOfViews + 1
                }
            }
        }

        if(springSecurityService.isLoggedIn()) {
          def principal = springSecurityService.principal
          ConsumerProfile thisConsumer = SecUser.get(principal.id).consumerProfile
          def feedEdge = FeedEdge.findWhere(consumerId : thisConsumer?.id, feedItemId : newsItemId)
          bIsFollowed = feedEdge?.isFollowed
        }

        render(view:'showDetails',model:[newsItem:newsItemInstance, bIsFollowed:bIsFollowed])
    }

    def showLink = {

        def newsItemId = params.id
        def newsItemInstance

        NewsItem.withTransaction {
            if(newsItemId) {
                newsItemInstance =  NewsItem.get(newsItemId)
                if(newsItemInstance) {
                    newsItemInstance.numberOfViews = newsItemInstance.numberOfViews + 1
                }
            }
        }

        if(newsItemInstance)
            redirect(url: newsItemInstance.urlLink)
        else
            redirect(action:list,params:params)
    }


    def addCommentAjax = {
      //TODO:  Move this code and create code into a a reusable Service
      if(springSecurityService.isLoggedIn()) {
        def principal = springSecurityService.principal

        def secuser = SecUser.get(principal.id)
        ConsumerProfile thisConsumer = secuser.consumerProfile

        def commentableItemType = Class.forName(params.commentableItemType)
        def commentableItemid = params.commentableItemId
        def List<Comment> commentList
        def newsItem
        def commentInstance

        Comment.withTransaction {
          newsItem = NewsItem.get(commentableItemid)
          commentInstance = new Comment(content: params.content,
                                            memberCreatorId: thisConsumer.id,
                                            memberCreatorUsername: secuser.username,
                                            commentableItemId: commentableItemid,
                                            commentableItemType: commentableItemType)
          newsItem.getComments().add(commentInstance)
        }

        //newsItem = NewsItem.get(commentableItemid)
        //Update all interested users
        def ctx = ApplicationHolder.application.mainContext.servletContext
        def contextPath = ctx.contextPath

        def notifyList = newsItem.notifyList
        notifyList.remove(secuser.email)
        notifyList.add("michael.green@wellmia.com")
        String strSubject = SpringSecurityUtils.securityConfig.wellmia.ui.notifyUserOnUpdate.emailSubjectStarter
        strSubject += "\"" + newsItem.title + "\""
        String strMessageBody = strSubject + "<br/>"
        strMessageBody += secuser.username + ":<br/>"
        strMessageBody += "<br/>" + params.content + "<br/>"
        strMessageBody += "<br/>" + "To View Item:  " + "<a href=\"${contextPath}/newsItem/showDetail/${newsItem.id}\">Click Here</a><br/>"
        Queue queue = QueueFactory.getDefaultQueue();
        TaskOptions taskOptions = TaskOptions.Builder.withUrl("/emailNotify/sendGroupMail")
                                  .param("emailFrom",URLEncoder.encode(SpringSecurityUtils.securityConfig.wellmia.ui.notifyUserOnUpdate.emailFrom))
                                  .param("subject",URLEncoder.encode(strSubject))
                                  .param("msgBody",URLEncoder.encode(strMessageBody))
                                  .param("emailTo",notifyList.encodeAsURL())
        queue.add(taskOptions)

        render(template:"/comment/comments", model: [commentableItem: newsItem])
        //render(template:"/comment/comment", model: [comment: commentInstance])

      } else  {
          //TODO:  redirect user, but capture comment and targetURL
        redirect(uri: '/home')
      }
    }

    def addAddToNotifyListAjax = {
        //TODO:  Move this code and create code into a a reusable Service
        def jsonData = []
        boolean bIsSaved = false

        if(springSecurityService.isLoggedIn()) {
            def principal = springSecurityService.principal

            def secuser = SecUser.get(principal.id)

            def newsItemid = params.newsItemId
            def newsItem

            NewsItem.withTransaction {
              newsItem = NewsItem.get(newsItemid)
              newsItem.notifyList.add(secuser.email)
              if(newsItem.save())
                bIsSaved = true
            }
        }

        if (bIsSaved)
            jsonData << [isSaved: "true"]
        else
            jsonData << [isSaved: "false"]


        render text: jsonData as JSON, contentType: 'text/plain'

    }


}
