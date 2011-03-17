package com.wellmia.security

import org.springframework.security.core.context.SecurityContextHolder

import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils

class SecurityInfoController {

  def accessDecisionManager
  def authenticationManager
  def channelProcessingFilter
  def logoutFilter
  def logoutHandlers
  def objectDefinitionSource
  def springSecurityFilterChain
  def userCache

  def index = {}

  def config = {
      [conf: new TreeMap(SpringSecurityUtils.securityConfig.flatten())]
  }

  def mappings = {
      // Map<Object, Collection<ConfigAttribute>>
      [configAttributeMap: new TreeMap(objectDefinitionSource.configAttributeMap),
       securityConfigType: SpringSecurityUtils.securityConfig.securityConfigType]
  }

  def currentAuth = {
      [auth: SecurityContextHolder.context.authentication]
  }

  def usercache = {
      [cache: SpringSecurityUtils.securityConfig.cacheUsers ? userCache.cache : null]
  }

  def filterChain = {
      [filterChainMap: springSecurityFilterChain.filterChainMap]
  }

  def logoutHandler = {
      render view: 'logoutHandlers', model: [handlers: logoutHandlers]
  }

  def voters = {
      [voters: accessDecisionManager.decisionVoters]
  }

  def providers = {
      [providers: authenticationManager.providers]
  }
/*
	def secureChannel = {
		def securityMetadataSource = channelProcessingFilter?.securityMetadataSource
		render securityMetadataSource.getClass().name
	}
*/

}
