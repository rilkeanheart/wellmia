package com.wellmia

import groovy.time.*

class NewsFeedService {

    static transactional = true

    def List updateConsumerNewsFeed(ConsumerProfile consumer) {

      def newsFeed = []
      def now = new Date()

      if(consumer.feedExpirationDate < now) {

        // Updating news items
        // "Select b from NewsItem WHERE dateCreated >= :refreshDate AND category IN (:category1, :category2,  :category3)"
       // for each newsItem create a newsedge consumer.newsFeed << newItems
        String query = "SELECT a from NewsItem a WHERE a.dateCreated >= :refreshDate AND a.category IN ("
        Map params = [refreshDate : consumer.feedExpirationDate]

        int i = 0
        consumer.interestTags.each { item ->
          String parameter = "category" + Integer.toString(i)
          params[parameter] = item
          parameter = ":" + parameter
          if(i < (consumer.interestTags.size() - 1))
            parameter += ", "
          query += parameter
          i++
        }

        query += ")"
        def newsItems = NewsItem.findAll(query,params)

        newsItems.each { item ->
          FeedEdge newsEdge = new FeedEdge(consumerId : consumer.id, feedItemId : item.id,
                                           sortedDate : item.publishDate,
                                           feedItemClassName : item.class.name).save(flush: true)
        }

        def fiveteenMinutes = new DatumDependentDuration(0,0,0,0,15,0,0)
        consumer.feedExpirationDate = fiveteenMinutes + new Date()
      }

      // TODO Implement offset and max elements and sort
      String query = "SELECT a from FeedEdge a WHERE consumerId = :consumerId ORDER BY a.sortedDate desc"
      Map params = [consumerId : consumer.id]
      List feedEdges = FeedEdge.findAll(query,params)
      feedEdges.each {feedEdge ->
        def gcl = new GroovyClassLoader()
        //Class edgeTargetClass = gcl.parseClass(feedEdge.feedItemClassName)
        def targetClassItem = (feedEdge.feedItemClassName as Class).get(feedEdge.feedItemId)
        //Object targetClassItem = edgeTargetClass.get(feedEdge.feedItemId)
        newsFeed << targetClassItem
      }

      return newsFeed

    }
}
