package com.wellmia

class BootstrapNewsSourceService  {

    static transactional = true

    def loadNewsSources() throws Exception {

        def defaultNewsDate = new Date() - 90 //90 days ago
        def fullCategoryList = []
        def newSourceProperties
        def count = 0

        new File ('WEB-INF/bootstrap/newsSources.csv').eachLine { line ->

            // Skip the first line (it shows column names)
            if(count > 0) {
                // Parse line into elements
                //newSourceProperties = line.split("\t: ")
                newSourceProperties = line.split(",")

                def name = newSourceProperties[0].toString().replaceAll (/"/, '')
                def sourceHomeURL = newSourceProperties[1].toString().replaceAll (/"/, '')
                def sourceRSSLink = newSourceProperties[2].toString().replaceAll (/"/, '')
                boolean isGeneral = (newSourceProperties[3].toString() == "TRUE")
                Set<String> categoryList = new HashSet<String>()

                if(!isGeneral) {
                    // Capture categories
                    if(newSourceProperties.size() > 4) {
                        categoryList << (newSourceProperties[4].toString()).replaceAll (/"/, '')
                        if(newSourceProperties.size() > 5) {
                            categoryList << (newSourceProperties[5].toString()).replaceAll (/"/, '')
                            if(newSourceProperties.size() > 6) {
                                categoryList << (newSourceProperties[6].toString()).replaceAll (/"/, '')
                            }
                        }
                    }
                }

                // Add news source, if we don't already have it
                if(NewsSource.findBySourceRSSLink(sourceRSSLink) == null) {
                  def temp = new NewsSource(sourceRSSLink: sourceRSSLink,
                                 name: name,
                                 sourceHomeURL: sourceHomeURL,
                                 mostRecentNewsDate: defaultNewsDate,
                                 newsSourceRank: Long.getLong("1"),
                                 defaultCategories: categoryList,
                                 isGeneral: isGeneral,
                                 isActive: true).save(failOnError: true, flush: true)
                }

                // Collect the set of new categories
                if(!categoryList.isEmpty())
                    fullCategoryList += categoryList

            }

            count++
        }

    }
}
