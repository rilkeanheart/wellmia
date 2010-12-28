

package com.wellmia

class NewsSourceController {
    
    def index = { redirect(action:list,params:params) }

    // the delete, save and update actions only accept POST requests
    static allowedMethods = [delete:'POST', save:'POST', update:'POST']

    def list = {
        params.max = Math.min( params.max ? params.max.toInteger() : 10,  100)
        [ newsSourceInstanceList: NewsSource.list( params ), newsSourceInstanceTotal: NewsSource.count() ]
    }

    def show = {
        def newsSourceInstance = NewsSource.get( params.id )

        if(!newsSourceInstance) {
            flash.message = "NewsSource not found with id ${params.id}"
            redirect(action:list)
        }
        else { return [ newsSourceInstance : newsSourceInstance ] }
    }

    def delete = {
		NewsSource.withTransaction {
	        def newsSourceInstance = NewsSource.get( params.id )
	        if(newsSourceInstance) {
	            try {
	                newsSourceInstance.delete(flush:true)
	                flash.message = "NewsSource ${params.id} deleted"
	                redirect(action:list)
	            }
	            catch(org.springframework.dao.DataIntegrityViolationException e) {
	                flash.message = "NewsSource ${params.id} could not be deleted"
	                redirect(action:show,id:params.id)
	            }
	        }
	        else {
	            flash.message = "NewsSource not found with id ${params.id}"
	            redirect(action:list)
	        }			
		}
    }

    def edit = {
        def newsSourceInstance = NewsSource.get( params.id )

        if(!newsSourceInstance) {
            flash.message = "NewsSource not found with id ${params.id}"
            redirect(action:list)
        }
        else {
            return [ newsSourceInstance : newsSourceInstance ]
        }
    }

    def update = {
		NewsSource.withTransaction {
	        def newsSourceInstance = NewsSource.get( params.id )
	        if(newsSourceInstance) {
	            if(params.version) {
	                def version = params.version.toLong()
	                if(newsSourceInstance.version > version) {
	                    
	                    newsSourceInstance.errors.rejectValue("version", "newsSource.optimistic.locking.failure", "Another user has updated this NewsSource while you were editing.")
	                    render(view:'edit',model:[newsSourceInstance:newsSourceInstance])
	                    return
	                }
	            }
	            newsSourceInstance.properties = params
	            if(!newsSourceInstance.hasErrors() && newsSourceInstance.save()) {
	                flash.message = "NewsSource ${params.id} updated"
	                redirect(action:show,id:newsSourceInstance.id)
	            }
	            else {
	                render(view:'edit',model:[newsSourceInstance:newsSourceInstance])
	            }
	        }
	        else {
	            flash.message = "NewsSource not found with id ${params.id}"
	            redirect(action:list)
	        }			
		}
    }

    def create = {
        def newsSourceInstance = new NewsSource()
        newsSourceInstance.properties = params
        return ['newsSourceInstance':newsSourceInstance]
    }

    def save = {
        def newsSourceInstance = new NewsSource(params)
		NewsSource.withTransaction {
	        if(newsSourceInstance.save(flush:true)) {
	            flash.message = "NewsSource ${newsSourceInstance.id} created"
	            redirect(action:show,id:newsSourceInstance.id)
	        }
	        else {
	            render(view:'create',model:[newsSourceInstance:newsSourceInstance])
	        }
		}
    }
}
