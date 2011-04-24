package com.wellmia.security

import com.wellmia.ConsumerProfile
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils
import groovy.text.SimpleTemplateEngine

import org.codehaus.groovy.grails.commons.ApplicationHolder as AH
import org.codehaus.groovy.grails.plugins.springsecurity.NullSaltSource

import grails.converters.JSON

import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils

import org.springframework.security.authentication.AccountExpiredException
import org.springframework.security.authentication.CredentialsExpiredException
import org.springframework.security.authentication.DisabledException
import org.springframework.security.authentication.LockedException
import org.springframework.security.core.context.SecurityContextHolder as SCH
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

class RegisterController {

  static allowedMethods = [save: 'POST', update: 'POST', delete: 'POST']

  def grailsApplication
  def springSecurityService
  def wellmiaSecurityService
  def recaptchaService

  /**
   * Dependency injection for the authenticationTrustResolver.
   */
  def authenticationTrustResolver

  static defaultAction = 'index'

  def mailService
  def saltSource

	def index = {
		def config = SpringSecurityUtils.securityConfig

		if (springSecurityService.isLoggedIn()) {
			redirect uri: config.successHandler.defaultTargetUrl
			return
		}

		String postUrl = "${request.contextPath}${config.apf.filterProcessesUrl}"
		render view: 'index', model: [postUrl: postUrl,
                                   defaultTargetURL : SpringSecurityUtils.securityConfig.successHandler.defaultTargetUrl,
		                           rememberMeParameter: config.rememberMe.parameter,
                                   command: new RegisterCommand()]
	}

	def register = { RegisterCommand command ->

		if (   (command.hasErrors()) ||
               (!recaptchaService.verifyAnswer(session,request.getRemoteAddr(),params))) {
			render view: 'index', model: [command: command]
			return
		}

        //Clean up Recaptcha if we made it this far
        recaptchaService.cleanUp(session)

		String salt = saltSource instanceof NullSaltSource ? null : command.username
		String password = springSecurityService.encodePassword(command.password, salt)
        def gender = params.getProperty("gender")
        def accept = command.accept
        def optIn = command.emailOptIn

        lookupUserClass().withTransaction {

            def userProfile = new ConsumerProfile(gender: command.gender)
            def user = lookupUserClass().newInstance(email: command.email, username: command.username,
                    gender: command.gender, country: command.country, password: password,
                    consumerProfile:  userProfile, termsAndPrivacyAccepted : command.accept,
                    emailOptIn : command.emailOptIn, accountLocked: true, enabled: true)
            if (user.validate() && userProfile.validate()) {
                if(user.save() && userProfile.save()) {
                    def registrationCode = new RegistrationCode(username: user.username).save()
                    String url = generateLink('verifyRegistration', [t: registrationCode.token])

                    def conf = SpringSecurityUtils.securityConfig
                    def body = conf.wellmia.ui.register.emailBody
                    if (body.contains('$')) {
                        body = evaluate(body, [user: user, url: url])
                    }

                    def emailFromString = SpringSecurityUtils.securityConfig.wellmia.ui.register.emailFrom
                    def emailSubjectString = SpringSecurityUtils.securityConfig.wellmia.ui.register.emailSubject

                    log.info("Sending mail:  To: ${command.email}, From: ${emailFromString}")

                    mailService.sendMail(command.email,
                                         emailFromString,
                                         emailSubjectString,
                                         body.toString())

                    def userEmail = command.email
                    def bodyOfEmail = body.toString()

                    render view: 'index', model: [emailSent: true, email: userEmail, body: bodyOfEmail]
                } else {
                    // User did not save
                    log.error("Error saving user:  ${command.username}")
                    render view: 'index', model: [command: command]
                }
            } else {

                log.error("Register user failed for ${command.username}")
                user.errors.allErrors.each {
                    log.error(it)
                }
                render view: 'index', model: [command: command]
            }
        }

		return
	}

	def verifyRegistration = {

		def conf = SpringSecurityUtils.securityConfig
		String defaultTargetUrl = conf.successHandler.defaultTargetUrl

		String token = params.t

		def registrationCode = token ? RegistrationCode.findByToken(token) : null
		if (!registrationCode) {
			flash.error = message(code: 'wellmia.security.ui.register.badCode')
			redirect uri: defaultTargetUrl
			return
		}

		def user
		RegistrationCode.withTransaction { status ->
			user = lookupUserClass().findByUsername(registrationCode.username)
			if (!user) {
				return
			}
			user.accountLocked = false
			user.save()
			def UserRole = lookupUserRoleClass()
			def Role = lookupRoleClass()
			for (roleName in conf.wellmia.ui.register.defaultRoleNames) {
				UserRole.create user, Role.findByAuthority(roleName)
			}
			registrationCode.delete()
		}

		if (!user) {
			flash.error = message(code: 'wellmia.security.ui.register.badCode')
			redirect uri: defaultTargetUrl
			return
		}

        //TODO:  Consider not reauthenticating and having the user login post verification
		springSecurityService.reauthenticate user.username

		flash.message = message(code: 'wellmia.security.ui.register.complete')
		redirect uri: conf.wellmia.ui.register.postRegisterUrl ?: defaultTargetUrl
	}

	def forgotPassword = {

		if (!request.post) {
			// show the form
			return
		}

		String username = params.username
		if (!username) {
			flash.error = message(code: 'wellmia.security.ui.forgotPassword.username.missing')
			return
		}

		def user = lookupUserClass().findByUsername(username)
		if (!user) {
			flash.error = message(code: 'wellmia.security.ui.forgotPassword.user.notFound')
			return
		}

		RegistrationCode.withTransaction { status ->
            def registrationCode = new RegistrationCode(username: user.username).save()
		}

		String url = generateLink('resetPassword', [t: registrationCode.token])

		def conf = SpringSecurityUtils.securityConfig
		def body = conf.wellmia.ui.forgotPassword.emailBody
		if (body.contains('$')) {
			body = evaluate(body, [user: user, url: url])
		}
		mailService.sendMail(user.email,
                             conf.wellmia.ui.forgotPassword.emailFrom,
                             conf.wellmia.ui.forgotPassword.emailSubject,
                             body.toString())

		[emailSent: true]
	}

	def resetPassword = { ResetPasswordCommand command ->

		String token = params.t

		def registrationCode = token ? RegistrationCode.findByToken(token) : null
		if (!registrationCode) {
			flash.error = message(code: 'wellmia.security.ui.resetPassword.badCode')
			redirect uri: SpringSecurityUtils.securityConfig.successHandler.defaultTargetUrl
			return
		}

		if (!request.post) {
			return [token: token, command: new ResetPasswordCommand()]
		}

		command.username = registrationCode.username
		command.validate()

		if (command.hasErrors()) {
			return [token: token, command: command]
		}

		String salt = saltSource instanceof NullSaltSource ? null : registrationCode.username
		RegistrationCode.withTransaction { status ->
			def user = lookupUserClass().findByUsername(registrationCode.username)
			user.password = springSecurityService.encodePassword(command.password, salt)
			user.save()
			registrationCode.delete()
		}

		springSecurityService.reauthenticate registrationCode.username

		flash.message = message(code: 'wellmia.security.ui.resetPassword.success')

		def conf = SpringSecurityUtils.securityConfig
		String postResetUrl = conf.wellmia.ui.register.postRegisterUrl ?: conf.successHandler.defaultTargetUrl
		redirect uri: postResetUrl
	}

	protected String generateLink(String action, linkParams) {
		createLink(base: "$request.scheme://$request.serverName:$request.serverPort$request.contextPath",
				controller: 'register', action: action,
				params: linkParams)

	}

	protected String evaluate(s, binding) {
		new SimpleTemplateEngine().createTemplate(s).make(binding)
	}

	static final passwordValidator = { String password, command ->
		if (command.username && command.username.equals(password)) {
			return 'command.password.error.username'
		}

	  /*  if (password && password.length() >= 8 && password.length() <= 64 &&
				(!password.matches('^.*\\p{Alpha}.*$') ||
				!password.matches('^.*\\p{Digit}.*$') ||
				!password.matches('^.*[!@#$%^&].*$'))) {
			return 'command.password.error.strength'
		}*/


	}

	static final password2Validator = { value, command ->
		if (command.password != command.password2) {
			return 'command.password2.error.mismatch'
		}
	}

	static final email2Validator = { value, command ->
		if (command.email != command.email2) {
			return 'command.email2.error.mismatch'
		}
	}


  protected boolean versionCheck(String messageCode, String messageCodeDefault, instance, model) {
      if (params.version) {
          def version = params.version.toLong()
          if (instance.version > version) {
              instance.errors.rejectValue('version', 'default.optimistic.locking.failure',
                      [message(code: messageCode, default: messageCodeDefault)] as Object[],
                      "Another user has updated this instance while you were editing")
              render view: 'edit', model: model
              return false
          }
      }
      true
  }

  protected void setIfMissing(String paramName, long valueIfMissing, Long max = null) {
      long value = (params[paramName] ?: valueIfMissing).toLong()
      if (max) {
          value = Math.min(value, max)
      }
      params[paramName] = value
  }

  protected String lookupUserClassName() {
      SpringSecurityUtils.securityConfig.userLookup.userDomainClassName
  }

  protected Class<?> lookupUserClass() {
      grailsApplication.getDomainClass(lookupUserClassName()).clazz
  }

  protected String lookupRoleClassName() {
      SpringSecurityUtils.securityConfig.authority.className
  }

  protected Class<?> lookupRoleClass() {
      grailsApplication.getDomainClass(lookupRoleClassName()).clazz
  }

  protected String lookupUserRoleClassName() {
      SpringSecurityUtils.securityConfig.userLookup.authorityJoinClassName
  }

  protected Class<?> lookupUserRoleClass() {
      grailsApplication.getDomainClass(lookupUserRoleClassName()).clazz
  }

  protected String lookupRequestmapClassName() {
      SpringSecurityUtils.securityConfig.requestMap.className
  }

  protected Class<?> lookupRequestmapClass() {
      grailsApplication.getDomainClass(lookupRequestmapClassName()).clazz
  }

}

class RegisterCommand {

	String username
	String email
    String email2
	String password
	String password2
    String gender
    String country
    boolean accept
    boolean emailOptIn

	static constraints = {
		username blank: false, validator: { value, command ->
            boolean isValid = false
            if (value) {
                if(!value.find("[;?', ]")) {
                    def User = AH.application.getDomainClass(
                        SpringSecurityUtils.securityConfig.userLookup.userDomainClassName).clazz
                    if (!User.findByUsername(value)) {
                        isValid = true
                    }
                }
			}
            return isValid
		}
		email blank: false, email: true, validator: { value, command ->
			if (value) {
				def User = AH.application.getDomainClass(
					SpringSecurityUtils.securityConfig.userLookup.userDomainClassName).clazz
				if (User.findByEmail(value)) {
					return 'registerCommand.email.unique'
				}
			}
        }
        email2 validator: RegisterController.email2Validator
		password blank: false, minSize: 6, maxSize: 64, validator: RegisterController.passwordValidator
		password2 validator: RegisterController.password2Validator
        accept blank: false
	}
}

class ResetPasswordCommand {
	String username
	String password
	String password2

	static constraints = {
		password blank: false, minSize: 6, maxSize: 64, validator: RegisterController.passwordValidator
		password2 validator: RegisterController.password2Validator
	}
}
