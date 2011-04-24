package com.wellmia

import grails.converters.JSON

class FeedEdgeController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [feedEdgeInstanceList: FeedEdge.list(params), feedEdgeInstanceTotal: FeedEdge.count()]
    }

    def create = {
        def feedEdgeInstance = new FeedEdge()
        feedEdgeInstance.properties = params
        return [feedEdgeInstance: feedEdgeInstance]
    }

    def save = {
        def feedEdgeInstance = new FeedEdge(params)
        if (feedEdgeInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'feedEdge.label', default: 'FeedEdge'), feedEdgeInstance.id])}"
            redirect(action: "show", id: feedEdgeInstance.id)
        }
        else {
            render(view: "create", model: [feedEdgeInstance: feedEdgeInstance])
        }
    }

    def show = {
        def feedEdgeInstance = FeedEdge.get(params.id)
        if (!feedEdgeInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'feedEdge.label', default: 'FeedEdge'), params.id])}"
            redirect(action: "list")
        }
        else {
            [feedEdgeInstance: feedEdgeInstance]
        }
    }

    def edit = {
        def feedEdgeInstance = FeedEdge.get(params.id)
        if (!feedEdgeInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'feedEdge.label', default: 'FeedEdge'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [feedEdgeInstance: feedEdgeInstance]
        }
    }

    def update = {
        def feedEdgeInstance = FeedEdge.get(params.id)
        if (feedEdgeInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (feedEdgeInstance.version > version) {
                    
                    feedEdgeInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'feedEdge.label', default: 'FeedEdge')] as Object[], "Another user has updated this FeedEdge while you were editing")
                    render(view: "edit", model: [feedEdgeInstance: feedEdgeInstance])
                    return
                }
            }
            feedEdgeInstance.properties = params
            if (!feedEdgeInstance.hasErrors() && feedEdgeInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'feedEdge.label', default: 'FeedEdge'), feedEdgeInstance.id])}"
                redirect(action: "show", id: feedEdgeInstance.id)
            }
            else {
                render(view: "edit", model: [feedEdgeInstance: feedEdgeInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'feedEdge.label', default: 'FeedEdge'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def feedEdgeInstance = FeedEdge.get(params.id)
        if (feedEdgeInstance) {
            try {
                feedEdgeInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'feedEdge.label', default: 'FeedEdge'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'feedEdge.label', default: 'FeedEdge'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'feedEdge.label', default: 'FeedEdge'), params.id])}"
            redirect(action: "list")
        }
    }

    def followFeedEdgeAjax = {
      boolean isSaved = false
      def jsonData = []

      //TODO:  Move this code and create code into a a reusable Service
      if(springSecurityService.isLoggedIn()) {
        def principal = springSecurityService.principal

        def secuser = SecUser.get(principal.id)
        ConsumerProfile thisConsumer = secuser.consumerProfile
        def feedId = params.feedItemId

        def List<Comment> commentList
        def newsItem
        def commentInstance

        FeedEdge.withTransaction {
          def feedEdgeItem = FeedEdge.get(feedId)
          if(feedEdgeItem &&
             feedEdgeItem.consumerId == thisConsumer.id){
              feedEdgeItem.setProperty("isFollowed", true)
              if(feedEdgeItem.save())
                isSaved = true
          }
        }
      }

      jsonData << [isSaved : isSaved]
      render text: jsonData as JSON, contentType: 'text/plain'
    }
}