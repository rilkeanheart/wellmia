package com.wellmia

import javax.servlet.http.HttpServletResponse

class CronController {

    def newsItemService
    def categoryTagStatService

    def doNewsUpdateService = {
        def offset = (params.int("offset") != null) ? params.int("offset") : 0
        def maxSourcesToProcess = (params.int("max") != null) ? params.int("max") : 1000

        def categorize = params.boolean("categorize")

        if(categorize == null)
          categorize = true

        newsItemService.updateNewsItems(offset, maxSourcesToProcess, categorize)
        response.outputStream << "OK"
        response.setStatus(HttpServletResponse.SC_OK)
    }

    def updateCategoryTagRank = {
        categoryTagStatService.updateCategoryStats()
        response.outputStream << "OK"
        response.setStatus(HttpServletResponse.SC_OK)
    }

}
