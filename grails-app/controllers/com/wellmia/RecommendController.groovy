package com.wellmia

class RecommendController {

  static navigation = [
      group:'mainTabs',
      order:50,
      title:'Feedback',
      action:'index'
  ]

  def index = {
    redirect (url: "http://www.getsatisfaction.com/wellmia")
  }
}
