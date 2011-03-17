package com.wellmia.security

import java.text.SimpleDateFormat

//import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils

class WellmiaSecurityService {

  static final String DATE_FORMAT = 'd MMM yyyy HH:mm:ss'

  static transactional = true

  def grailsApplication

  boolean updatePersistentLogin(persistentLogin, newProperties) {
      if (newProperties.lastUsed && newProperties.lastUsed instanceof String) {
          Calendar c = Calendar.instance
          c.time = new SimpleDateFormat(DATE_FORMAT).parse(newProperties.lastUsed)
          persistentLogin.lastUsed = c.time
      }

      if (newProperties.token) {
          persistentLogin.token = newProperties.token
      }

      persistentLogin.save()
      return !persistentLogin.hasErrors()
  }

  void deletePersistentLogin(persistentLogin) {
      persistentLogin.delete()
  }

  boolean updateRegistrationCode(registrationCode, String username, String token) {
      registrationCode.token = token
      registrationCode.username = username
      registrationCode.save()
      return !registrationCode.hasErrors()
  }

  void deleteRegistrationCode(registrationCode) {
      registrationCode.delete()
  }

  boolean updateAclClass(aclClass, String newName) {
      aclClass.className = newName
      aclClass.save()
      return !aclClass.hasErrors()
  }

  void deleteAclClass(aclClass) {
      // will fail if there are FK references
      aclClass.delete()
  }

  boolean updateAclSid(aclSid, String newName, boolean newPrincipal) {
      aclSid.sid = newName
      aclSid.principal = newPrincipal
      aclSid.save()
      return !aclSid.hasErrors()
  }

  void deleteAclSid(aclSid) {
      // will fail if there are FK references
      aclSid.delete()
  }

  boolean updateAclObjectIdentity(aclObjectIdentity, long objectId, long aclClassId,
          Long parentId, Long ownerId, boolean entriesInheriting) {

      aclObjectIdentity.objectId = objectId
      aclObjectIdentity.entriesInheriting = entriesInheriting

      if (aclObjectIdentity.aclClass.id != aclClassId) {
          aclObjectIdentity.aclClass = retrieveAclClass('AclClass', aclClassId)
      }

      if (parentId) {
          if (aclObjectIdentity.parent?.id != parentId) {
              aclObjectIdentity.parent = retrieveAclClass('AclObjectIdentity', parentId)
          }
      }
      else {
          aclObjectIdentity.parent = null
      }

      if (ownerId) {
          if (aclObjectIdentity.owner.id != ownerId) {
              aclObjectIdentity.owner = retrieveAclClass('AclSid', ownerId)
          }
      }
      else {
          aclObjectIdentity.parent = null
      }

      aclObjectIdentity.save()
      return !aclObjectIdentity.hasErrors()
  }

  void deleteAclObjectIdentity(aclObjectIdentity) {
      // will fail if there are FK references
      aclObjectIdentity.delete()
  }

  boolean updateAclEntry(aclEntry, long aclObjectIdentityId, long sidId, int aceOrder,
          int mask, boolean granting, boolean auditSuccess, boolean auditFailure) {

      aclEntry.aceOrder = aceOrder
      aclEntry.mask = mask
      aclEntry.granting = granting
      aclEntry.auditSuccess = auditSuccess
      aclEntry.auditFailure = auditFailure

      if (aclEntry.aclObjectIdentity.id != aclObjectIdentityId) {
          aclEntry.aclObjectIdentity = retrieveAclClass('AclObjectIdentity', aclObjectIdentityId)
      }

      if (aclEntry.sid.id != sidId) {
          aclEntry.sid = retrieveAclClass('AclSid', sidId)
      }

      aclEntry.save()
      return !aclEntry.hasErrors()
  }

  void deleteAclEntry(aclEntry) {
      aclEntry.delete()
  }

  protected retrieveAclClass(String name, id) {
      def clazz = grailsApplication.getClassForName('org.codehaus.groovy.grails.plugins.springsecurity.acl' + name)
      clazz.get id
  }

}
