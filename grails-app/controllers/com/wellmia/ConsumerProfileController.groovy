package com.wellmia

class ConsumerProfileController {
    
    def index = { redirect(action:list,params:params) }

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
}
