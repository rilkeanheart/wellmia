package com.wellmia

import grails.converters.JSON

import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils

import org.springframework.security.authentication.AccountExpiredException
import org.springframework.security.authentication.CredentialsExpiredException
import org.springframework.security.authentication.DisabledException
import org.springframework.security.authentication.LockedException
import org.springframework.security.core.context.SecurityContextHolder as SCH
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

class HomeController {

	static navigation = [
		group:'mainTabs',
		order:10,
		title:'Home',
		action:'newsFeed'
	]

  /**
	 * Dependency injection for the authenticationTrustResolver.
	 */
	def authenticationTrustResolver

	/**
	 * Dependency injection for the springSecurityService.
	 */
	def springSecurityService

    def index = {
		def config = SpringSecurityUtils.securityConfig

		if (springSecurityService.isLoggedIn()) {
			redirect uri: config.successHandler.defaultTargetUrl
			return
		}

		String view = 'index'
		String postUrl = "${request.contextPath}${config.apf.filterProcessesUrl}"
        String defaultTargetURL = SpringSecurityUtils.securityConfig.successHandler.defaultTargetUrl;
		render view: view, model: [postUrl: postUrl,
                                   defaultTargetURL : defaultTargetURL,
		                           rememberMeParameter: config.rememberMe.parameter]
    }

    def newsFeed = {
      redirect(controller:'newsFeed', action:'listItems')
    }
}
