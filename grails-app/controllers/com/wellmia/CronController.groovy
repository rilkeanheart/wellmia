package com.wellmia


class CronController {

    def newsItemService

    def doNewsUpdateService = {
        def offset = (params.int("offset") != null) ? params.int("offset") : 0
        def maxSourcesToProcess = (params.int("max") != null) ? params.int("max") : 1000

        def categorize = params.boolean("categorize")

        if(categorize == null)
          categorize = true

        newsItemService.updateNewsItems(offset, maxSourcesToProcess, categorize)
        flash.message = "Completed news update method"
        redirect(controller: 'newsItem', action: 'list')
    }

}
