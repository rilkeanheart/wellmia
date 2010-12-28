package com.wellmia


class NewsItemService {
  static transactional = true

  def updateNewsItems = {int iOffset, int iMaxSourcesToProcess ->
    def newsSources = NewsSource.list(sort: 'newsSourceRank', order: 'asc', offset: iOffset, max: iMaxSourcesToProcess)
    println "Update News Item Service:  Obtained $newsSources.size() NewsSources"
    def newsItems = []
    newsSources.each {
      //Include if below fails once deployed to app engine
      //URL url = new URL(it.sourceRSSLink)
      //InputStream inStream = url.openStream()
      //def xmlFeed = new XmlParser().parse(inStream)
      //Reference Groovy.page.409 //may need to catch exceptions ref. app-engine.page.243

      def xmlFeed = new XmlParser().parse(it.sourceRSSLink)

      def newsSource = it
      def mostRecentNewsItemDate

      (0..<xmlFeed.channel.item.size()).each {

        def item = xmlFeed.channel.item.get(it)

        Date publishedDate = new Date(item.pubDate.text())
        //TODO:  include safety for invalid feedItem published date

        if (publishedDate > newsSource.mostRecentNewsDate) {
          def newsItem = new NewsItem(title: item.title.text(),
                  urlLink: item.link.text(),
                  publishDate: item.pubDate.text(),
                  content: item.description.text(),
                  newsSource: newsSource,
                  dateCreated: new Date(),
                  category: []
                  //imageTitle: it.getTitle()
          )

          String contentString = newsItem.title + ' ' + newsItem.content
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

          }

          // categorize(input: string output: List<String>)
          //    create ngram for item (use some service)
          //    cycle through each profile ngram
          //    compare {    } pass in ngram1, ngram 2, sensitivity   <- Hold sensitivities in category profile
          //      add tag score
          //    build list sorting by highest score
          //    return list
//
          if(!newsItem.category.isEmpty()) {
            NewsItem.withTransaction {
              if(!newsItem.save(flush:true))
                 throw new NewsItemUpdateException(message: "Error adding news item to source $newsSource:  $newsItem", newsItem: newsItem)
            }
          }

          newsItems += newsItem
          if (mostRecentNewsItemDate < newsItem.publishDate)
            mostRecentNewsItemDate = newsItem.publishDate

          println "Finished processing: $newsItem.title"
        }
      }

      if (mostRecentNewsItemDate != null) {
        //newsSource.addToNewsItems(newsItems)

        NewsSource.withTransaction {
          newsSource.setProperty("mostRecentNewsDate", mostRecentNewsItemDate)
          newsSource.merge()
        }
      }

    }

    System.out.println newsItems

    return newsItems
  }
}

class NewsItemUpdateException extends RuntimeException {
	String message
	NewsItem newsItem
}
