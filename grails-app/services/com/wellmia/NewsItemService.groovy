package com.wellmia


class NewsItemService {
  static transactional = true

  def updateNewsItems = {int iOffset, int iMaxSourcesToProcess, boolean categorize ->
    def newsSources = NewsSource.list(sort: 'newsSourceRank', order: 'asc', offset: iOffset, max: iMaxSourcesToProcess)
    println "Update News Item Service:  Obtained $newsSources.size() NewsSources"
    def newsItems = []


    newsSources.each {

      try {
          def xmlFeed = new XmlParser().parse(it.sourceRSSLink)

          def newsSource = it
          def mostRecentNewsItemDate
          Date now = new Date()

          (0..<xmlFeed.channel.item.size()).each {

            def item = xmlFeed.channel.item.get(it)

            Date publishedDate = new Date(item.pubDate.text())

            //TODO:  include safety for invalid feedItem published date

            if (publishedDate > newsSource.mostRecentNewsDate) {

              def content = item.description.text()
              if(content.size() > 500)
                 content = content.substring(0,499)


              def newsItemCategories = newsSource.defaultCategories
              if(newsSource.isGeneral) {
                    //TODO:  insert logic to categorize based on text
                    // analyze text to see if there are matches to categories

                    /*String contentString = newsItem.title + ' ' + newsItem.content
                    NGramProfiler profiler = new NGramProfiler(contentString)
                    Map<String,Integer> profileNGramCount = profiler.createProfile()
                    //Map<String,Integer> profileNGramRank = HashMapUtility.sortHashMapByValuesD(profileNGramCount)

                    List allCategories = CategoryProfile.list()
                    allCategories.each { category ->
                        double difference = NGramProfiler.calculateDifference(profileNGramCount,
                                                                            category.ngramCounts)

                        double threshold =  category.getProperty('thresholdSensitivity')
                        if(difference <= threshold) {
                            //TODO:  use difference score to order category list
                            println "Found one for category:  "  +  category.getProperty('categoryName')
                            newsItem.category.add(category.getProperty('categoryName'))
                        }
                    }*/
              }

              def itemTitle = item.title.text()
              NewsItem.withTransaction {
                  def tempItem = NewsItem.findWhere(title: itemTitle, publishDate: publishedDate)
                  if(tempItem) {
                      newsSource.defaultCategories.each { newCategory ->
                          tempItem.category.add(newCategory)
                      }
                      println "Updating News Item:  ${itemTitle}"
                      if(!tempItem.merge(flush:true))
                        //throw new NewsItemUpdateException(message: "Error merging news item to source $newsSource:  $newsItem", newsItem: newsItem)
                        println "Error merging news item to source $newsSource:  $newsItem"
                  } else {
                      def newsItem = new NewsItem(title: item.title.text(),
                              urlLink: item.link.text(),
                              publishDate: item.pubDate.text(),
                              content: content,
                              newsSource: newsSource,
                              dateCreated: now,
                              category: newsItemCategories,
                              isReviewed: true //imageTitle: it.getTitle()
                      )
                      println "Creating News Item:  ${itemTitle}"
                      if(!newsItem.validate()) {
                          println "Error: $newsItem.title "
                          newsItem.errors.each{ error->
                              println error
                          }
                      } else {
                        newsItem.save(flush:true)
                      }
                  }
              }

              if (mostRecentNewsItemDate < publishedDate)
                mostRecentNewsItemDate = publishedDate
            }
          }

          if (mostRecentNewsItemDate != null) {
            NewsSource.withTransaction {
              newsSource.setProperty("mostRecentNewsDate", mostRecentNewsItemDate)
              newsSource.merge()
            }
          }

      }catch (Exception e) {
          println e
      }

    }

    println "Finished News Item Service at: ${new Date()}"
    return newsItems
  }
}

class NewsItemUpdateException extends RuntimeException {
	String message
	//NewsItem newsItem
}
