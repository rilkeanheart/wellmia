package com.wellmia

import com.wellmia.security.SecUser

class NewsFeedController {

  def springSecurityService
  def newsFeedService

  def listItems = {

    if(springSecurityService.isLoggedIn()) {
      def principal = springSecurityService.principal

        ConsumerProfile thisConsumer = SecUser.get(principal.id).consumerProfile

        def feedList = newsFeedService.getTimeLineFeed(thisConsumer)

        return [consumer : thisConsumer, feedList : feedList ]

    } else  {
      redirect(uri: '/login')
    }

  }

  def topItems = {
    if(springSecurityService.isLoggedIn()) {
      def principal = springSecurityService.principal

        ConsumerProfile thisConsumer = SecUser.get(principal.id).consumerProfile

        def feedList = newsFeedService.getTopItemsFeed(thisConsumer)

        return [consumer : thisConsumer, feedList : feedList ]

    } else  {
      redirect(uri: '/login')
    }
  }

  def followedItems = {
    if(springSecurityService.isLoggedIn()) {
      def principal = springSecurityService.principal

        ConsumerProfile thisConsumer = SecUser.get(principal.id).consumerProfile

        def feedList = newsFeedService.getFollowedItemsFeed(thisConsumer)

        return [consumer : thisConsumer, feedList : feedList ]

    } else  {
      redirect(uri: '/login')
    }
  }

}
