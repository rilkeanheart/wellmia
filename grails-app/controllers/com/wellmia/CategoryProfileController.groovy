

package com.wellmia

class  CategoryProfileController {

    def categoryProfileService
    def index = { redirect(action:list,params:params) }

    // the delete, save and update actions only accept POST requests
    static allowedMethods = [delete:'POST', save:'POST', update:'POST']

    def list = {
        params.max = Math.min( params.max ? params.max.toInteger() : 10,  100)
        [ categoryProfileInstanceList: CategoryProfile.list( params ), categoryProfileInstanceTotal: CategoryProfile.count() ]
    }

    def show = {
        def categoryProfileInstance = CategoryProfile.get( params.id )

        if(!categoryProfileInstance) {
            flash.message = "CategoryProfile not found with id ${params.id}"
            redirect(action:list)
        }
        else { return [ categoryProfileInstance : categoryProfileInstance ] }
    }

    def delete = {
		CategoryProfile.withTransaction {
	        def categoryProfileInstance = CategoryProfile.get( params.id )
	        if(categoryProfileInstance) {
	            try {
	                categoryProfileInstance.delete(flush:true)
	                flash.message = "CategoryProfile ${params.id} deleted"
	                redirect(action:list)
	            }
	            catch(org.springframework.dao.DataIntegrityViolationException e) {
	                flash.message = "CategoryProfile ${params.id} could not be deleted"
	                redirect(action:show,id:params.id)
	            }
	        }
	        else {
	            flash.message = "CategoryProfile not found with id ${params.id}"
	            redirect(action:list)
	        }
		}
    }

    def edit = {
        def categoryProfileInstance = CategoryProfile.get( params.id )

        if(!categoryProfileInstance) {
            flash.message = "CategoryProfile not found with id ${params.id}"
            redirect(action:list)
        }
        else {
            return [ categoryProfileInstance : categoryProfileInstance ]
        }
    }

    def update = {
		CategoryProfile.withTransaction {
	        def categoryProfileInstance = CategoryProfile.get( params.id )
	        if(categoryProfileInstance) {
	            if(params.version) {
	                def version = params.version.toLong()
	                if(categoryProfileInstance.version > version) {

	                    categoryProfileInstance.errors.rejectValue("version", "categoryProfile.optimistic.locking.failure", "Another user has updated this CategoryProfile while you were editing.")
	                    render(view:'edit',model:[categoryProfileInstance:categoryProfileInstance])
	                    return
	                }
	            }
	            categoryProfileInstance.properties = params
	            if(!categoryProfileInstance.hasErrors() && categoryProfileInstance.save()) {
	                flash.message = "CategoryProfile ${params.id} updated"
	                redirect(action:show,id:categoryProfileInstance.id)
	            }
	            else {
	                render(view:'edit',model:[categoryProfileInstance:categoryProfileInstance])
	            }
	        }
	        else {
	            flash.message = "CategoryProfile not found with id ${params.id}"
	            redirect(action:list)
	        }
		}
    }

    def create = {
        def categoryProfileInstance = new CategoryProfile()
        categoryProfileInstance.properties = params
        return ['categoryProfileInstance':categoryProfileInstance]
    }

    def save = {
      def paramTest = params
      def categoryProfileInstance = new CategoryProfile(params)
		CategoryProfile.withTransaction {
	        if(categoryProfileInstance.save(flush:true)) {
	            flash.message = "CategoryProfile ${categoryProfileInstance.id} created"
	            redirect(action:show,id:categoryProfileInstance.id)
	        }
	        else {
	            render(view:'create',model:[categoryProfileInstance:categoryProfileInstance])
	        }
		}
    }

	def doExpireCategoryProfiles = {
        def categoryProfiles = CategoryProfile.list()

        categoryProfiles.each {
          CategoryProfile.withTransaction {
            item.setProperty('isExpired', true)
            item.merge()
          }
        }

        flash.message = "Completed expiring category profiles"
		redirect(action: 'list')
	}

	def doUpdateCategoryProfiles = {
		categoryProfileService.updateCategoryProfiles()
        println "Done updating category profiles"
        flash.message = "Completed updating category profiles"
		redirect(action: 'list')
	}

  	def doCalculateCategoryRanks = {
		categoryProfileService.calculateCategoryProfileRanks()

        flash.message = "Completed calculating category profile ranks"
		redirect(action: 'list')
	}
}