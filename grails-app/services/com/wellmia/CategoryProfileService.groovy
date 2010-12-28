package com.wellmia

class CategoryProfileService {

    static transactional = false

    def updateCategoryProfiles() {

      //def newsItems = NewsItem.findAllWhere(isReviewed: true)
      List newsItems = NewsItem.findAllByIsReviewed(true)

      newsItems.each { newsItem ->

        String newsString = newsItem.getProperty('title')
        newsString += ' '
        newsString += newsItem.getProperty('content')

        NGramProfiler profiler = new NGramProfiler(newsString)

        def ngramCountsMap = profiler.createProfile()

        def newsItemCategories = newsItem.getProperty('category')

        newsItemCategories.each { category ->

          def categoryProfileInstance = CategoryProfile.findWhere(categoryName : category)

          if(categoryProfileInstance == null) {

            CategoryTag categoryTag = CategoryTag.findWhere(category : category)
            //Profile doesn't exist we need to create a new profile
            CategoryProfile.withTransaction {
              def ngramCounts = new HashMap<String, Integer>()
              def ngramRanks = new HashMap<String, Integer>()

              double dThreshHoldSensitivity = categoryTag?.thresholdSensitivity

              categoryProfileInstance = new CategoryProfile(categoryName: category,
                                                    isExpired: true,
                                                    thresholdSensitivity: dThreshHoldSensitivity,
                                                    ngramCounts: ngramCounts,
                                                    ngramRanks: ngramRanks)

              if(!categoryProfileInstance.hasErrors()) {
                 if(!categoryProfileInstance.save(flush:true))
                   throw new CategoryProfileServiceException(message: "Error creating a new category profile $categoryProfileInstance.categoryName", categoryProfile : categoryProfileInstance)
              } else
                throw new CategoryProfileServiceException(message: "Error creating a new category profile $categoryProfileInstance.categoryName:  $categoryProfileInstance.errors()", categoryProfile : categoryProfileInstance)
            }
          }

          // Clear the data if the profile is expired
          if(categoryProfileInstance.isExpired) {
            categoryProfileInstance.ngramCounts.clear()
            categoryProfileInstance.ngramRanks.clear()
          }

          // Update the ngramCounts Map
          ngramCountsMap.each {key, value ->
            if(categoryProfileInstance.ngramCounts.containsKey(key)) {
              Integer newCount = value
              newCount += categoryProfileInstance.ngramCounts[key]
              categoryProfileInstance.ngramCounts[key] = newCount
            } else {
              categoryProfileInstance.ngramCounts[key] = value
            }
          }

          // Make sure to indicate that the data in this profile is now valid
          //CategoryProfile.withTransaction {
          println "Attempting to update Category Profile (new)"
          CategoryProfile.withTransaction {
            categoryProfileInstance.setProperty('isExpired', false)
          categoryProfileInstance.merge()
          }
        }

      }
    }

    def calculateCategoryProfileRanks() {
      def categoryItems = CategoryProfile.list()

      categoryItems.each {item ->
        CategoryProfile.withTransaction {
          Map newMap =  HashMapUtility.sortHashMapByValuesD(item.ngramCounts)
          item.setProperty('ngramRanks', newMap)
        }
      }
    }
}

  class CategoryProfileServiceException extends RuntimeException {
      String message
      CategoryProfile categoryProfile
  }
