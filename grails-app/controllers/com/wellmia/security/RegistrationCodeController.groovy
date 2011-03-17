package com.wellmia.security

import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils
import grails.converters.JSON
import org.springframework.dao.DataIntegrityViolationException

class RegistrationCodeController {

  static allowedMethods = [save: 'POST', update: 'POST', delete: 'POST']
  static defaultAction = 'search'

  def grailsApplication
  def springSecurityService
  def wellmiaSecurityService

  def edit = {
      def registrationCode = findById()
      if (!registrationCode) return

      [registrationCode: registrationCode]
  }

  def update = {
      def registrationCode = findById()
      if (!registrationCode) return
      if (!versionCheck('registrationCode.label', 'RegistrationCode', registrationCode, [registrationCode: registrationCode])) {
          return
      }

      if (!wellmiaSecurityService.updateRegistrationCode(registrationCode, params.username, params.token)) {
          render view: 'edit', model: [registrationCode: registrationCode]
          return
      }

      flash.message = "${message(code: 'default.updated.message', args: [message(code: 'registrationCode.label', default: 'RegistrationCode'), registrationCode.id])}"
      redirect action: edit, id: registrationCode.id
  }

  def delete = {
      def registrationCode = findById()
      if (!registrationCode) return

      try {
          wellmiaSecurityService.deleteRegistrationCode registrationCode
          flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'registrationCode.label', default: 'RegistrationCode'), params.id])}"
          redirect action: search
      }
      catch (DataIntegrityViolationException e) {
          flash.error = "${message(code: 'default.not.deleted.message', args: [message(code: 'registrationCode.label', default: 'RegistrationCode'), params.id])}"
          redirect action: edit, id: params.id
      }
  }

  def search = {}

  def registrationCodeSearch = {

      boolean useOffset = params.containsKey('offset')
      setIfMissing 'max', 10, 100
      setIfMissing 'offset', 0

      def hql = new StringBuilder('FROM RegistrationCode rc WHERE 1=1 ')
      def queryParams = [:]

      if (params.token) {
          hql.append " AND LOWER(rc.token) LIKE :token"
          queryParams.token = params.token.toLowerCase() + '%'
      }

      if (params.username) {
          hql.append " AND LOWER(rc.username) LIKE :username"
          queryParams.username = params.username.toLowerCase() + '%'
      }

      int totalCount = RegistrationCode.executeQuery("SELECT COUNT(DISTINCT rc) $hql", queryParams)[0]

      int max = params.int('max')
      int offset = params.int('offset')

      String orderBy = ''
      if (params.sort) {
          orderBy = " ORDER BY rc.$params.sort ${params.order ?: 'ASC'}"
      }

      def results = RegistrationCode.executeQuery(
              "SELECT DISTINCT rc $hql $orderBy",
              queryParams, [max: max, offset: offset])
      def model = [results: results, totalCount: totalCount, searched: true]

      // add query params to model for paging
      for (name in ['username', 'token']) {
           model[name] = params[name]
      }

      render view: 'search', model: model
  }

  /**
   * Ajax call used by autocomplete textfield.
   */
  def ajaxRegistrationCodeSearch = {

      def jsonData = []

      if (params.term?.length() > 2) {
          String username = params.term

          setIfMissing 'max', 10, 100

          def results = RegistrationCode.executeQuery(
                  "SELECT DISTINCT rc.username " +
                  "FROM RegistrationCode rc " +
                  "WHERE LOWER(rc.username) LIKE :name " +
                  "ORDER BY rc.username ",
                  [name: "${username.toLowerCase()}%"],
                  [max: params.max])

          for (result in results) {
              jsonData << [value: result]
          }
      }

      render text: jsonData as JSON, contentType: 'text/plain'
  }

  protected RegistrationCode findById() {
      def registrationCode = RegistrationCode.get(params.id)
      if (!registrationCode) {
          flash.error = "${message(code: 'default.not.found.message', args: [message(code: 'registrationCode.label', default: 'RegistrationCode'), params.id])}"
          redirect action: search
      }

      registrationCode
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
