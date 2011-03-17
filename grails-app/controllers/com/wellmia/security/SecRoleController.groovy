package com.wellmia.security

import grails.converters.JSON

import org.springframework.dao.DataIntegrityViolationException
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils

class SecRoleController {

  static allowedMethods = [save: 'POST', update: 'POST', delete: 'POST']
  static defaultAction = 'search'

  def grailsApplication
  def springSecurityService
  def wellmiaSecurityService

  def create = {
      [role: lookupRoleClass().newInstance(params)]
  }

  def save = {
      def role = lookupRoleClass().newInstance(params)

      role.withTransaction {
        if (!role.save(flush: true)) {
           render view: 'create', model: [role: role]
           return
        } else {
          flash.message = "${message(code: 'default.created.message', args: [message(code: 'role.label', default: 'Role'), role.id])}"
          redirect action: edit, id: role.id
        }
      }

  }

  def edit = {
      def role = params.name ? lookupRoleClass().findByAuthority(params.name) : null
      if (!role) role = findById()
      if (!role) return

      setIfMissing 'max', 10, 100
      def users = lookupUserRoleClass().findAllBySecRoleId(role.id)*.secUser
      //int userCount = lookupUserRoleClass().countByRole(role)

      [role: role, users: users, userCount: users.size()]
  }

  def update = {
      def role = findById()
      if (!role) return
      if (!versionCheck('role.label', 'Role', role, [role: role])) {
          return
      }

      if (!springSecurityService.updateRole(role, params)) {
          render view: 'edit', model: [role: role]
          return
      }

      flash.message = "${message(code: 'default.updated.message', args: [message(code: 'role.label', default: 'Role'), role.id])}"
      redirect action: edit, id: role.id
  }

  def delete = {
      def role = findById()
      if (!role) return

      try {
          role.withTransaction {
            lookupUserRoleClass().removeAll role
            springSecurityService.deleteRole(role)
            flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'role.label', default: 'Role'), params.id])}"
            redirect action: search
          }
      }
      catch (DataIntegrityViolationException e) {
          flash.error = "${message(code: 'default.not.deleted.message', args: [message(code: 'role.label', default: 'Role'), params.id])}"
          redirect action: edit, id: params.id
      }
  }

  def search = {}

  def roleSearch = {

      boolean useOffset = params.containsKey('offset')
      setIfMissing 'max', 10, 100
      setIfMissing 'offset', 0

      String name = params.authority ?: 'ROLE_'
      def roles = search(name, false, 10, params.int('offset'))
      if (roles.size() == 1 && !useOffset) {
          forward action: 'edit', params: [name: roles[0].authority]
          return
      }

      String hql =
          "SELECT DISTINCT r.authority " +
          "FROM ${lookupRoleClassName()} r " +
          "WHERE LOWER(r.authority) LIKE :name"
      def searchString = "${name.toLowerCase()}%"

      def fullRoles = search(name, false, 1000, 0)

      render view: 'search', model: [results: roles,
                                     totalCount: fullRoles.size(),
                                     authority: params.authority,
                                     searched: true]
  }

  /**
   * Ajax call used by autocomplete textfield.
   */
  def ajaxRoleSearch = {

      def jsonData = []

      if (params.term?.length() > 1) {
          setIfMissing 'max', 10, 100
          setIfMissing 'offset', 0

          def names = search(params.term, true, params.int('max'), params.int('offset'))
          for (name in names) {
              jsonData << [value: name]
          }
      }

      render text: jsonData as JSON, contentType: 'text/plain'
  }

  protected search(String name, boolean nameOnly, int max, int offset) {
      String hql =
          "SELECT DISTINCT ${nameOnly ? 'r.authority' : 'r'} " +
          "FROM ${lookupRoleClassName()} r " +
          "WHERE LOWER(r.authority) LIKE :name " +
          "ORDER BY r.authority"
      lookupRoleClass().executeQuery hql, [name: "${name.toLowerCase()}%"], [max: max, offset: offset]
  }

  protected findById() {
      def role = lookupRoleClass().get(params.id)
      if (!role) {
          flash.error = "${message(code: 'default.not.found.message', args: [message(code: 'role.label', default: 'Role'), params.id])}"
          redirect action: search
      }

      role
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
