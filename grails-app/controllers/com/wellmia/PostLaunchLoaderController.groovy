package com.wellmia

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.appengine.api.taskqueue.TaskOptions.Method;
import static com.google.appengine.api.taskqueue.TaskOptions.Builder.withUrl;
import com.google.appengine.api.datastore.Blob


class PostLaunchLoaderController {

    def bootstrapNewsSourceService
    def bootstrapConditionTypeService
    def bootstrapTreatmentTypeService

	def doLoadTreatmentService = {
		def offset = params.int("offset")
		def maxSourcesToProcess = params.int("max")

		bootstrapTreatmentTypeService.serviceMethod(offset, maxSourcesToProcess)
		//flash.message = "Completed news update method"
		redirect(uri: '/controllers.gsp')
	}

	def doLoadTreatmentCategoryTagService = {
		def offset = params.int("offset")
		def maxSourcesToProcess = params.int("max")

		bootstrapTreatmentTypeService.categoryTagServiceMethod(offset, maxSourcesToProcess)
		redirect(uri: '/controllers.gsp')
	}

	def doLoadConditionTypeService = {
		def offset = params.int("offset")
		def maxSourcesToProcess = params.int("max")

        // Load ConditionTypes and associated CategoryTags
        bootstrapConditionTypeService.serviceMethod()
		//flash.message = "Completed news update method"
		redirect(uri: '/controllers.gsp')
	}

	def doLoadNewsSourceService = {
		def offset = params.int("offset")
		def maxSourcesToProcess = params.int("max")

        //Load News Sources
        bootstrapNewsSourceService.loadNewsSources()
		//flash.message = "Completed news update method"
		redirect(uri: '/controllers.gsp')
	}

	def doQueueLoadTreatmentService = {
		def offset = params.int("offset")
		def maxSourcesToProcess = params.int("max")

        Queue queue = QueueFactory.getDefaultQueue();
        TaskOptions taskOptions = TaskOptions.Builder.withUrl("/postLaunchLoaderController/doLoadTreatmentService")
                                  .param("offset",offset.toString())
                                  .param("max", maxSourcesToProcess.toString())

        //def target = java.net.URLEncoder.encode(urlString)

        queue.add(taskOptions)

		redirect(uri: '/controllers.gsp')
	}

	def doQueueLoadTreatmentCategoryTagService = {
		def offset = params.int("offset")
		def maxSourcesToProcess = params.int("max")

        Queue queue = QueueFactory.getDefaultQueue();
        TaskOptions taskOptions = TaskOptions.Builder.withUrl("/postLaunchLoaderController/doLoadTreatmentService")
                                  .param("offset",offset.toString())
                                  .param("max", maxSourcesToProcess.toString())

        //def target = java.net.URLEncoder.encode(urlString)

        queue.add(taskOptions)

		redirect(uri: '/controllers.gsp')
	}

	def doQueueLoadNewsItemService = {
		def offset = params.int("offset")
		def maxSourcesToProcess = params.int("max")

        Queue queue = QueueFactory.getDefaultQueue();
        TaskOptions taskOptions = TaskOptions.Builder.withUrl("/newsItem/doNewsUpdateService?offset=0&max=100")
                                  .param("offset",offset.toString())
                                  .param("max", maxSourcesToProcess.toString())

        //def target = java.net.URLEncoder.encode(urlString)

        queue.add(taskOptions)

		redirect(uri: '/controllers.gsp')
	}

    def addDefaultAvatars = {

        //get all Consumer Users
        ConsumerProfile.withTransaction {
            File avatarFileMale = new File ('WEB-INF/defaultAvatars/avatarm.png')
            File avatarFileFemale = new File ('WEB-INF/defaultAvatars/avatarf.png')

            def allConsumerProfiles = ConsumerProfile.list()
            allConsumerProfiles.each { item ->
                if(item.avatar == null) {
                    if(item.getGender()=="Male") {
                        item.avatar = new Blob(avatarFileMale.newDataInputStream().bytes)
                    } else {
                        item.avatar = new Blob(avatarFileFemale.newDataInputStream().bytes)
                    }

                    item.avatarMIMEType = "image/png"
                    item.save()
                }
            }
        }

        redirect(uri: '/controllers.gsp')
    }
}
