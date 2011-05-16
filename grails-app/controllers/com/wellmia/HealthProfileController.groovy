package com.wellmia

import com.wellmia.security.SecUser

class HealthProfileController {

  def springSecurityService
  def categoryTagStatService

  static navigation = [
      group:'mainTabs',
      order:40,
      title:'Health Interests',
      action:'index'
  ]

  def index = {
    if(springSecurityService.isLoggedIn()) {
      def principal = springSecurityService.principal

        ConsumerProfile thisConsumer = SecUser.get(principal.id).consumerProfile
        def topCategories = categoryTagStatService.topCategoryStats(20)

        return [consumer : thisConsumer, topCategories : topCategories]

    } else  {
      redirect(uri: '/login')
    }
  //If this is the user's first time in, we will show a dialog
  //Otherwise just show the page to add health interests

  }
}
