package com.wellmia

import grails.converters.JSON
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils
import com.wellmia.security.SecUser
import com.google.appengine.api.datastore.Blob

class ConsumerProfileController {

    def springSecurityService
    def categorizeService

    def index = { redirect(action:list,params:params) }

	/*static navigation = [
		[group:'accountSettings', title:'Account', action:'editAccount', order: 0],
		[title:'Password', action:'editPassword', order: 10],
		[title:'Profile', action:'editProfile', order: 20]
		[title:'Privacy', action:'editPrivacy', order: 30]
		[title:'Notifications', action:'editNotifications', order: 40]
	]*/

    // the delete, save and update actions only accept POST requests
    static allowedMethods = [delete:'POST', save:'POST', update:'POST']

    def list = {
        params.max = Math.min( params.max ? params.max.toInteger() : 10,  100)
        [ consumerProfileInstanceList: ConsumerProfile.list( params ), consumerProfileInstanceTotal: ConsumerProfile.count() ]
    }

    def show = {
        def consumerProfileInstance = ConsumerProfile.get( params.id )

        if(!consumerProfileInstance) {
            flash.message = "ConsumerProfile not found with id ${params.id}"
            redirect(action:list)
        }
        else { return [ consumerProfileInstance : consumerProfileInstance ] }
    }

    def delete = {
		ConsumerProfile.withTransaction {
	        def consumerProfileInstance = ConsumerProfile.get( params.id )
	        if(consumerProfileInstance) {
	            try {
	                consumerProfileInstance.delete(flush:true)
	                flash.message = "ConsumerProfile ${params.id} deleted"
	                redirect(action:list)
	            }
	            catch(org.springframework.dao.DataIntegrityViolationException e) {
	                flash.message = "ConsumerProfile ${params.id} could not be deleted"
	                redirect(action:show,id:params.id)
	            }
	        }
	        else {
	            flash.message = "ConsumerProfile not found with id ${params.id}"
	            redirect(action:list)
	        }			
		}
    }

    def edit = {
        def consumerProfileInstance = ConsumerProfile.get( params.id )

        if(!consumerProfileInstance) {
            flash.message = "ConsumerProfile not found with id ${params.id}"
            redirect(action:list)
        }
        else {
            return [ consumerProfileInstance : consumerProfileInstance ]
        }
    }

    def update = {
		ConsumerProfile.withTransaction {
	        def consumerProfileInstance = ConsumerProfile.get( params.id )
	        if(consumerProfileInstance) {
	            if(params.version) {
	                def version = params.version.toLong()
	                if(consumerProfileInstance.version > version) {
	                    
	                    consumerProfileInstance.errors.rejectValue("version", "consumerProfile.optimistic.locking.failure", "Another user has updated this ConsumerProfile while you were editing.")
	                    render(view:'edit',model:[consumerProfileInstance:consumerProfileInstance])
	                    return
	                }
	            }
	            consumerProfileInstance.properties = params
	            if(!consumerProfileInstance.hasErrors() && consumerProfileInstance.save()) {
	                flash.message = "ConsumerProfile ${params.id} updated"
	                redirect(action:show,id:consumerProfileInstance.id)
	            }
	            else {
	                render(view:'edit',model:[consumerProfileInstance:consumerProfileInstance])
	            }
	        }
	        else {
	            flash.message = "ConsumerProfile not found with id ${params.id}"
	            redirect(action:list)
	        }			
		}
    }

    def create = {
        def consumerProfileInstance = new ConsumerProfile()
        consumerProfileInstance.properties = params
        return ['consumerProfileInstance':consumerProfileInstance]
    }

    def save = {
        def consumerProfileInstance = new ConsumerProfile(params)
		ConsumerProfile.withTransaction {
	        if(consumerProfileInstance.save(flush:true)) {
	            flash.message = "ConsumerProfile ${consumerProfileInstance.id} created"
	            redirect(action:show,id:consumerProfileInstance.id)
	        }
	        else {
	            render(view:'create',model:[consumerProfileInstance:consumerProfileInstance])
	        }
		}
    }

    def showAvatar = {
        //TODO:  Move this code and create code into a a reusable Service
        if(springSecurityService.isLoggedIn()) {
            def principal = springSecurityService.principal

            def secuser = SecUser.get(principal.id)
            ConsumerProfile thisConsumer = secuser.consumerProfile

            def userAvatar = thisConsumer.avatar
            def userAvatarMIMEType = thisConsumer.avatarMIMEType

            //TODO:  Implement exception handling to close file & stream handles
            response.setContentType(userAvatarMIMEType)
            response.setContentLength(userAvatar.getBytes().length)
            OutputStream out = response.getOutputStream()
            try {
                out.write(userAvatar.getBytes())
            } catch(IOException e) {
                log.error(e)
            } finally {
                out.close();
            }
        }
    }

    def upload_avatar = {

        if(springSecurityService.isLoggedIn()) {
            def principal = springSecurityService.principal

            def secuser = SecUser.get(principal.id)
            ConsumerProfile thisConsumer = secuser.consumerProfile

              //Get the avatar file from the multi-part request
              def f = request.getFile('avatar')

              // List of OK mime-types
              def okcontents = ['image/png', 'image/jpeg', 'image/gif']
              if (! okcontents.contains(f.getContentType())) {
                  flash.message = "Avatar must be one of: ${okcontents}"
                  render(view:'select_avatar', model:[user:user])
              return;
              }

	        render(view:'basicProfile',model:[consumerProfileInstance:thisConsumer])
        }

        def user = User.current(session)  // or however you select the current user

          //Get the avatar file from the multi-part request
          def f = request.getFile('avatar')

          // List of OK mime-types
          def okcontents = ['image/png', 'image/jpeg', 'image/gif']
          if (! okcontents.contains(f.getContentType())) {
              flash.message = "Avatar must be one of: ${okcontents}"
              render(view:'select_avatar', model:[user:user])
              return;
          }

          // Save the image and mime type
          user.avatar = f.getBytes()
          user.avatarType = f.getContentType()
          log.info("File uploaded: " + user.avatarType)

          // Validation works, will check if the image is too big
          if (!user.save()) {
              render(view:'select_avatar', model:[user:user])
              return;
          }

            flash.message = "Avatar (${user.avatarType}, ${user.avatar.size()} bytes) uploaded."
            redirect(action:'show')
    }

    def updateInterestTags = {
	  def config = SpringSecurityUtils.securityConfig

      //TODO:  Move this code and create code into a a reusable Service
      if(springSecurityService.isLoggedIn()) {
        def principal = springSecurityService.principal

        def secuser = SecUser.get(principal.id)
        ConsumerProfile thisConsumer = secuser.consumerProfile

        def paramsList = params
        def taglist = params.interests
        //def unvalidatedCategories = taglist as Set<String>

        def categories = categorizeService.validateCategories(taglist as Set<String>)
        def oldCategories = thisConsumer.getInterestTags()
        ConsumerProfile.withTransaction {
            thisConsumer.setProperty("interestTags", categories)
            thisConsumer.setProperty("feedIsStale", true)
        }

        //Now update the categoryTag interest properties
        //Increment counters for new Tags
        def categoriesCopy = new LinkedList(categories)
        categoriesCopy.removeAll(categories.intersect(oldCategories))
        categoriesCopy.each { newCategory ->
            CategoryTag.withTransaction {
                def categoryToUpdate = CategoryTag.findWhere(category: newCategory)
                if(categoryToUpdate) {
                    categoryToUpdate.setProperty("numberOfFollowers", (categoryToUpdate.numberOfFollowers+1))
                }
            }
        }

        //Decrement counters for oldTags
        def oldCategoriesCopy = new LinkedList(oldCategories)
        oldCategoriesCopy.removeAll(categories.intersect(oldCategories))
        oldCategoriesCopy.each { deadCategory ->
            CategoryTag.withTransaction {
                def categoryToUpdate = CategoryTag.findWhere(category: deadCategory)
                if(categoryToUpdate) {
                    categoryToUpdate.setProperty("numberOfFollowers", (categoryToUpdate.numberOfFollowers-1))
                }
            }
        }

        redirect uri: config.successHandler.defaultTargetUrl
      } else  {
        redirect(uri: '/home')
      }

      return

    }

    def followFeedItemAjax = {
        //TODO:  Move this code and create code into a a reusable Service
        def jsonData = []
        boolean bIsFollowed = false

        if(springSecurityService.isLoggedIn()) {
            def principal = springSecurityService.principal

            def secuser = SecUser.get(principal.id)
            ConsumerProfile thisConsumer = SecUser.get(principal.id).consumerProfile

            def feedItemid = params.feedItemId
            def bShouldFollow = params.bShouldFollow.asBoolean()
            def feedEdgeItem
            def feedItem

            FeedEdge.withTransaction {
              feedEdgeItem = FeedEdge.findWhere(consumerId : thisConsumer.id, feedItemId : feedItemid)
              feedEdgeItem?.setIsFollowed(bShouldFollow)
              if(feedEdgeItem?.save())
                bIsFollowed = true
            }

            if(bIsFollowed && feedEdgeItem) {
                bIsFollowed = false
                //Add the users e-mail to the feed items notify list
                (feedEdgeItem.feedItemClassName as Class).withTransaction {
                  feedItem = (feedEdgeItem.feedItemClassName as Class).get(feedEdgeItem.feedItemId)
                  feedItem.notifyList.add(secuser.email)
                  if(feedItem.save())
                    bIsFollowed = true
                }
            }

        }

        if (bIsFollowed)
            jsonData << [bIsFollowed: "true"]
        else
            jsonData << [bIsFollowed: "false"]


        render text: jsonData as JSON, contentType: 'text/plain'

    }

    def editAccount = {
        //TODO:  Move this code and create code into a a reusable Service
        if(springSecurityService.isLoggedIn()) {
            def principal = springSecurityService.principal

            def secuser = SecUser.get(principal.id)
            ConsumerProfile thisConsumer = secuser.consumerProfile
	        render(view:'editAccount',model:[consumer:thisConsumer, user:secuser])
        } else {
            redirect(uri: '/home')
        }
    }

    def updateAccount = {

    }

    def editPassword = {
        //TODO:  Move this code and create code into a a reusable Service
        if(springSecurityService.isLoggedIn()) {
            def principal = springSecurityService.principal

            def secuser = SecUser.get(principal.id)
            ConsumerProfile thisConsumer = secuser.consumerProfile
	        render(view:'editPassword',model:[consumer:thisConsumer, user:secuser])
        } else {
            redirect(uri: '/home')
        }
    }

    def updatePassword = {

    }

    def editProfile = {
        //TODO:  Move this code and create code into a a reusable Service
        if(springSecurityService.isLoggedIn()) {
            def principal = springSecurityService.principal

            def secuser = SecUser.get(principal.id)
            ConsumerProfile thisConsumer = secuser.consumerProfile
	        render(view:'editProfile',model:[consumer:thisConsumer, user:secuser])
        } else {
            redirect(uri: '/home')
        }
    }

    def updateProfile = {
        if(springSecurityService.isLoggedIn()) {
            def principal = springSecurityService.principal

            def secuser = SecUser.get(principal.id)
            ConsumerProfile thisConsumer = secuser.consumerProfile

            //Get the avatar file from the multi-part request
            def f = request.getFile('avatar')

            // List of OK mime-types
            def okcontents = ['image/png', 'image/jpeg', 'image/gif']
            if (! okcontents.contains(f.getContentType())) {
              flash.message = "Avatar must be one of: ${okcontents}"
              render(view:'editProfile', model:[user:user])
            } else {
                // Save the image and mime type
                ConsumerProfile.withTransaction {
                    def consumer = ConsumerProfile.get(thisConsumer.id)
                    byte[] avatarBytes = f.getBytes()
                    consumer.avatar = new Blob(avatarBytes)
                    consumer.avatarMIMEType = f.getContentType()
                    if(!consumer.save()) {
                        flash.message = "File too large: ${avatarBytes.length} bytes and maximum is 700K"
                        render(view:'editProfile',model:[consumer:thisConsumer, user:secuser])
                    } else {
                        flash.message = "Avatar (${consumer.avatarMIMEType}, ${avatarBytes.length} bytes) uploaded."
                        log.info("File uploaded: " + consumer.avatarMIMEType)
                        render(view:'editProfile',model:[consumer:thisConsumer, user:secuser])
                    }
                }
            }
        }
    }

    def editPrivacy = {

    }

    def updatePrivacy = {

    }
    def editNotifications = {

    }

    def updateNotifications = {

    }

}
