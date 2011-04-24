package com.wellmia

import groovy.time.TimeCategory
import groovy.time.DatumDependentDuration


class NewsFeedService {

    static transactional = true

    def List getTimeLineFeed(ConsumerProfile consumer) {
        def newsFeed = []

        updateConsumerNewsFeed(consumer)

        // TODO Implement offset and max elements and sort
        String query = "SELECT a from FeedEdge a WHERE consumerId = :consumerId ORDER BY a.sortedDate desc"
        Map params = [consumerId : consumer.id]
        List feedEdges = FeedEdge.findAll(query,params)
        feedEdges.each {feedEdge ->
            def gcl = new GroovyClassLoader()
            def targetClassItem = (feedEdge.feedItemClassName as Class).get(feedEdge.feedItemId)
            newsFeed << targetClassItem
        }

        return newsFeed

    }

    def List getTimeLineFeed(CategoryTag topic) {
        def newsFeed = []

        updateTopicNewsFeed(topic)

        // TODO Implement offset and max elements and sort
        String query = "SELECT a from TopicFeedEdge a WHERE categoryTagId = :categoryTagId ORDER BY a.sortedDate desc"
        Map params = [categoryTagId : topic.id]
        List feedEdges = TopicFeedEdge.findAll(query,params)
        feedEdges.each {feedEdge ->
            def gcl = new GroovyClassLoader()
            def targetClassItem = (feedEdge.feedItemClassName as Class).get(feedEdge.feedItemId)
            newsFeed << targetClassItem
        }

      return newsFeed
    }

    def List getTopItemsFeed(ConsumerProfile consumer) {
        def newsFeed = []

        updateConsumerNewsFeed(consumer)

        // TODO Implement offset and max elements and sort
        String query = "SELECT a from FeedEdge a WHERE consumerId = :consumerId ORDER BY a.sortedDate desc"
        Map params = [consumerId : consumer.id]
        List feedEdges = FeedEdge.findAll(query,params)
        def fullFeed = []
        feedEdges.each {feedEdge ->
            def gcl = new GroovyClassLoader()
            def targetClassItem = (feedEdge.feedItemClassName as Class).get(feedEdge.feedItemId)
            fullFeed << targetClassItem
        }

        // Filter the list and pull any non-zero rated items
        fullFeed.each {targetItem ->
            if(targetItem.getProperty("rating") > 0)
                newsFeed << targetItem
        }

        // Now sort the newsFeed list
        newsFeed.sort {it.getProperty("rating")}

        return newsFeed
    }

    def List getTopItemsFeed(CategoryTag topic) {
        def newsFeed = []

        updateTopicNewsFeed(topic)

        // TODO Implement offset and max elements and sort
        String query = "SELECT a from TopicFeedEdge a WHERE categoryTagId = :categoryTagId ORDER BY a.sortedDate desc"
        Map params = [categoryTagId : topic.id]
        List feedEdges = TopicFeedEdge.findAll(query,params)
        def fullFeed = []
        feedEdges.each {feedEdge ->
            def gcl = new GroovyClassLoader()
            def targetClassItem = (feedEdge.feedItemClassName as Class).get(feedEdge.feedItemId)
            fullFeed << targetClassItem
        }

        // Filter the list and pull any non-zero rated items
        fullFeed.each {targetItem ->
            if(targetItem.getProperty("rating") > 0)
                newsFeed << targetItem
        }

        // Now sort the newsFeed list
        newsFeed.sort {it.getProperty("rating")}

        return newsFeed
    }

    def List getFollowedItemsFeed(ConsumerProfile consumer) {
        def newsFeed = []

        // TODO Implement offset and max elements and sort
        String query = "SELECT a from FeedEdge a WHERE consumerId = :consumerId AND isFollowed = :isFollowed ORDER BY a.sortedDate desc"
        Map params = [consumerId : consumer.id, isFollowed : "True"]
        List feedEdges = FeedEdge.findAll(query,params)
        feedEdges.each {feedEdge ->
            def gcl = new GroovyClassLoader()
            def targetClassItem = (feedEdge.feedItemClassName as Class).get(feedEdge.feedItemId)
            newsFeed << targetClassItem
        }

        return newsFeed
    }

    def boolean updateConsumerNewsFeed(ConsumerProfile consumer) {

      def now = new Date()

      if(  ((consumer.feedExpirationDate < now) ||
           (consumer.feedIsStale)) &&
           (!consumer.interestTags.isEmpty())) {

        // Updating news items
        // "Select b from NewsItem WHERE dateCreated >= :refreshDate AND category IN (:category1, :category2,  :category3)"
       // for each newsItem create a newsedge consumer.newsFeed << newItems
        // twoMinutesPastMidnight - 2.minutes
        def startFrom = consumer.feedExpirationDate

        String newsQuery = "SELECT a from NewsItem a WHERE a.dateCreated >= :refreshDate AND a.category IN ("

        // TODO:  Clean up use of time durations (it is in two places immediately below)
        def newsRefreshPeriod = new DatumDependentDuration(0,0,0,0,15,0,0)

        if(consumer.feedIsStale) {
          use (TimeCategory) {
              startFrom = consumer.feedExpirationDate - 15.minutes
          }
        }

        Map params = [refreshDate : startFrom]
        String queryDetails = new String()

        int i = 0
        consumer.interestTags.each { item ->
          String parameter = "category" + Integer.toString(i)
          params[parameter] = item
          parameter = ":" + parameter
          if(i < (consumer.interestTags.size() - 1))
            parameter += ", "
          queryDetails += parameter
          i++
        }

        queryDetails += ")"
        newsQuery += queryDetails
        def newsItems = NewsItem.findAll(newsQuery,params)

        newsItems.each { item ->
          FeedEdge newsEdge = new FeedEdge(consumerId : consumer.id, feedItemId : item.id,
                                           sortedDate : item.publishDate,
                                           feedItemClassName : item.class.name).save(flush: true)
        }

        // Updating questions
        String questionQuery = "SELECT a from Question a WHERE a.dateCreated >= :refreshDate AND a.category IN ("
        questionQuery += queryDetails
        def newQuestions = Question.findAll(questionQuery,params)

        newQuestions.each { item ->
          FeedEdge newsEdge = new FeedEdge(consumerId : consumer.id, feedItemId : item.id,
                                           sortedDate : item.dateCreated,
                                           feedItemClassName : item.class.name).save(flush: true)
        }

        // Updating Status
        String statusQuery = "SELECT a from Status a WHERE a.dateCreated >= :refreshDate AND a.category IN ("
        statusQuery += queryDetails
        def newStatusItems = Status.findAll(statusQuery,params)

        newStatusItems.each { item ->
          FeedEdge newsEdge = new FeedEdge(consumerId : consumer.id, feedItemId : item.id,
                                           sortedDate : item.dateCreated,
                                           feedItemClassName : item.class.name).save(flush: true)
        }



        consumer.feedExpirationDate = newsRefreshPeriod + new Date()
        consumer.feedIsStale = false
      }

      return true

    }

    def boolean updateTopicNewsFeed(CategoryTag topic) {

      def now = new Date()

      if(  ((topic.feedExpirationDate < now) ||
           (topic.feedIsStale)) ) {

        // Updating news items
        def startFrom = topic.feedExpirationDate

        String newsQuery = "SELECT a from NewsItem a WHERE a.dateCreated >= :refreshDate AND a.category IN ("

        // TODO:  Clean up use of time durations (it is in two places immediately below)
        def newsRefreshPeriod = new DatumDependentDuration(0,0,0,0,15,0,0)

        if(topic.feedIsStale) {
          use (TimeCategory) {
              startFrom = topic.feedExpirationDate - 15.minutes
          }
        }

        Map params = [refreshDate : startFrom]
        String queryDetails = new String()

        queryDetails += ":category0"
        params["category0"] = topic.category

        int i = 1
        topic.alias?.each { item ->
          String parameter = "category" + Integer.toString(i)
          params[parameter] = item
          parameter = ":" + parameter
          //if(i < (topic.alias?.size() - 1))
          //  parameter += ", "
          queryDetails += ", ${parameter}"
          i++
        }

        queryDetails += ")"
        newsQuery += queryDetails
        def newsItems = NewsItem.findAll(newsQuery,params)

        newsItems.each { item ->
          TopicFeedEdge feedEdge = new TopicFeedEdge(categoryTagId : topic.id, feedItemId : item.id,
                                           sortedDate : item.publishDate,
                                           feedItemClassName : item.class.name).save(flush: true)
        }

        // Updating questions
        String questionQuery = "SELECT a from Question a WHERE a.dateCreated >= :refreshDate AND a.category IN ("
        questionQuery += queryDetails
        def newQuestions = Question.findAll(questionQuery,params)

        newQuestions.each { item ->
          TopicFeedEdge feedEdge = new TopicFeedEdge(categoryTagId : topic.id, feedItemId : item.id,
                                           sortedDate : item.dateCreated,
                                           feedItemClassName : item.class.name).save(flush: true)
        }

        // Updating Status
        String statusQuery = "SELECT a from Status a WHERE a.dateCreated >= :refreshDate AND a.category IN ("
        statusQuery += queryDetails
        def newStatusItems = Status.findAll(statusQuery,params)

        newStatusItems.each { item ->
          TopicFeedEdge feedEdge = new TopicFeedEdge(categoryTagId : topic.id, feedItemId : item.id,
                                           sortedDate : item.dateCreated,
                                           feedItemClassName : item.class.name).save(flush: true)
        }

        topic.feedExpirationDate = newsRefreshPeriod + new Date()
        topic.feedIsStale = false
      }

      return true

    }


}
