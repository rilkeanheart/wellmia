package com.wellmia

import org.apache.commons.lang.WordUtils

import com.wellmia.security.SecUser

class TopicsController {

  static defaultAction = "topCategories"

  def springSecurityService
  def newsFeedService
  def categoryTagStatService



  def topCategories = {
      // Get top categories
      def topCategories = categoryTagStatService.topCategoryStats(100)
      return [topCategories : topCategories]
  }

  def timeLine = {

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
        feedList = newsFeedService.getTimeLineFeed(categoryTag)
        def map = [consumer : thisConsumer, feedList : feedList, categoryTag : categoryTag, viewType : "Timeline"]
        render(view: "/newsFeed/listItems",  model : map)
    } else {
        redirect(action: 'index')
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
        feedList = newsFeedService.getTopItemsFeed(categoryTag)

        //TODO:  Sort the feedlist based on feed Item ratings
        feedList = new TreeSet(feedList)
    }

     def map = [consumer : thisConsumer, feedList : feedList, categoryTag : categoryTag, viewType : "Timeline"]
    render(view : "/newsFeed/topItems", model : map)
  }

  def showCategories = {
      //Takes two parameters categoryType and beginsWith
      //displays a list of categories (up to 1000) matching those parameters
      String categoryType = params.get("categoryType")
      String beginsWith = params.get("beginsWith")

      def categoryList = categoryTagStatService.getCategories(categoryType,beginsWith)
      return [categoryList: categoryList, beginsWith:beginsWith]
  }

}