package com.wellmia

class CategoryTagController {

    def index = { redirect(action:list,params:params) }

    // the delete, save and update actions only accept POST requests
    static allowedMethods = [delete:'POST', save:'POST', update:'POST']

    def list = {
        params.max = Math.min( params.max ? params.max.toInteger() : 10,  100)
        [ categoryTagInstanceList: CategoryTag.list( params ), categoryTagInstanceTotal: CategoryTag.count() ]
    }

    def show = {
        def categoryTagInstance = CategoryTag.get( params.id )

        if(!categoryTagInstance) {
            flash.message = "CategoryTag not found with id ${params.id}"
            redirect(action:list)
        }
        else { return [ categoryTagInstance : categoryTagInstance ] }
    }

    def delete = {
		CategoryTag.withTransaction {
	        def categoryTagInstance = CategoryTag.get( params.id )
	        if(categoryTagInstance) {
	            try {
	                categoryTagInstance.delete(flush:true)
	                flash.message = "CategoryTag ${params.id} deleted"
	                redirect(action:list)
	            }
	            catch(org.springframework.dao.DataIntegrityViolationException e) {
	                flash.message = "CategoryTag ${params.id} could not be deleted"
	                redirect(action:show,id:params.id)
	            }
	        }
	        else {
	            flash.message = "CategoryTag not found with id ${params.id}"
	            redirect(action:list)
	        }			
		}
    }

    def edit = {
        def categoryTagInstance = CategoryTag.get( params.id )

        if(!categoryTagInstance) {
            flash.message = "CategoryTag not found with id ${params.id}"
            redirect(action:list)
        }
        else {
            return [ categoryTagInstance : categoryTagInstance ]
        }
    }

    def update = {
		CategoryTag.withTransaction {
	        def categoryTagInstance = CategoryTag.get( params.id )
	        if(categoryTagInstance) {
	            if(params.version) {
	                def version = params.version.toLong()
	                if(categoryTagInstance.version > version) {
	                    
	                    categoryTagInstance.errors.rejectValue("version", "categoryTag.optimistic.locking.failure", "Another user has updated this CategoryTag while you were editing.")
	                    render(view:'edit',model:[categoryTagInstance:categoryTagInstance])
	                    return
	                }
	            }
	            categoryTagInstance.properties = params
	            if(!categoryTagInstance.hasErrors() && categoryTagInstance.save()) {
	                flash.message = "CategoryTag ${params.id} updated"
	                redirect(action:show,id:categoryTagInstance.id)
	            }
	            else {
	                render(view:'edit',model:[categoryTagInstance:categoryTagInstance])
	            }
	        }
	        else {
	            flash.message = "CategoryTag not found with id ${params.id}"
	            redirect(action:list)
	        }			
		}
    }

    def create = {
        def categoryTagInstance = new CategoryTag()
        categoryTagInstance.properties = params
        return ['categoryTagInstance':categoryTagInstance]
    }

    def save = {
        def categoryTagInstance = new CategoryTag(params)
		CategoryTag.withTransaction {
	        if(categoryTagInstance.save(flush:true)) {
	            flash.message = "CategoryTag ${categoryTagInstance.id} created"
	            redirect(action:show,id:categoryTagInstance.id)
	        }
	        else {
	            render(view:'create',model:[categoryTagInstance:categoryTagInstance])
	        }
		}
    }
}
