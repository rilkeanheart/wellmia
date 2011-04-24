package com.wellmia

import com.wellmia.security.SecUser

class HealthProfileController {

  def springSecurityService

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

        return [consumer : thisConsumer]

    } else  {
      redirect(uri: '/login')
    }
  //If this is the user's first time in, we will show a dialog
  //Otherwise just show the page to add health interests

  }
}
