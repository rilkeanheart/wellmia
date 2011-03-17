package com.wellmia

import org.apache.commons.lang.WordUtils

import com.wellmia.security.SecUser

class TopicsController {

  def springSecurityService
  def newsFeedService

  def timeline = {

    // Determine if there is a categoryTag for the $id
    def categoryTagId = params.id
    def categoryTag
    def thisConsumer
    def feedList

    if(springSecurityService.isLoggedIn()) {
      def principal = springSecurityService.principal

        thisConsumer = SecUser.get(principal.id).consumerProfile
    }

    if(categoryTagId) {
        categoryTagId = categoryTagId.replace('_',' ')
        categoryTagId = WordUtils.capitalizeFully(categoryTagId, " -.".toCharArray())
        categoryTag = CategoryTag.findWhere(category : categoryTagId)
    }

    if(categoryTag) {
        feedList = newsFeedService.updateTopicNewsFeed(categoryTag)
        return [consumer : thisConsumer, feedList : feedList, categoryTag : categoryTag ]
    } else {
        redirect(controller: 'home', action: 'index')
    }

    return [consumer : thisConsumer, feedList : feedList, categoryTag : categoryTag ]
  }

  def topItems = {

    // Determine if there is a categoryTag for the $id
    def categoryTagId = params.id
    def categoryTag
    def thisConsumer
    def feedList

    if(springSecurityService.isLoggedIn()) {
      def principal = springSecurityService.principal

        thisConsumer = SecUser.get(principal.id).consumerProfile
    }

    if(categoryTagId) {
        categoryTagId = categoryTagId.replace('_',' ')
        categoryTagId = WordUtils.capitalizeFully(categoryTagId, "-.".toCharArray())
        categoryTag = CategoryTag.findWhere(category : categoryTagId)
    }

    if(categoryTag) {
        feedList = newsFeedService.updateTopicNewsFeed(categoryTag)

        //TODO:  Sort the feedlist based on feed Item ratings
        feedList = new TreeSet(feedList)
    }

    return [consumer : thisConsumer, feedList : feedList, categoryTag : categoryTag ]
  }

}