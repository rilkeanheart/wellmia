package com.wellmia.security

import org.apache.commons.lang.StringUtils
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.context.support.MessageSourceAccessor
import org.springframework.dao.DataAccessException
import org.springframework.dao.DataRetrievalFailureException
import org.springframework.security.core.SpringSecurityMessageSource
//import org.codehaus.groovy.grails.plugins.springsecurity.GrailsUser
import org.codehaus.groovy.grails.plugins.springsecurity.GrailsUserDetailsService
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils
import org.springframework.security.core.authority.GrantedAuthorityImpl
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.authentication.BadCredentialsException

/**
 * Created by IntelliJ IDEA.
 * User: mike
 * Date: 12/23/10
 * Time: 10:30 PM
 * To change this template use File | Settings | File Templates.
 */
class WellmiaSecurityUserDetailsService implements GrailsUserDetailsService {

  static final Log log = LogFactory.getLog(WellmiaSecurityUserDetailsService)
  MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor()
  /**
   * Some Spring Security classes (e.g. RoleHierarchyVoter) expect at least one role,
   * so we give a user with no granted roles this one which gets past that restriction
   * but doesn't grant anything. *
   */
  static final List NO_ROLES = [new GrantedAuthorityImpl(SpringSecurityUtils.NO_ROLE)]
  UserDetails loadUserByUsername(String username, boolean loadRoles)
    throws UsernameNotFoundException {
      return loadUserByUsername(username)
  }

  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
    if(StringUtils.isBlank(username)) {
       log.error "Username was empty. Authentication failed."
       throw new BadCredentialsException(messages.getMessage("login.username.blank", [] as Object[], "Username may not be empty"), username)
    }

    def secUser = null

    try {
       secUser = SecUser.findByUsername(username)
    }
    catch(Exception e) {
       log.error "Problem retrieving user from database", e
       throw new DataRetrievalFailureException(messages.getMessage("login.database.error", [] as Object[], "User could not be retrieved from database. Please try later"))
    }

    if(secUser == null) {
       log.error "User for username '${username}' not found in database. Authentication failed."
       throw new UsernameNotFoundException(messages.getMessage("JdbcDaoImpl.notFound", [username] as Object[], "Username {0} not found"), username)
    }

    if(log.infoEnabled) {
       log.info "Found user for username '${username}': ${user}"
    }

    def grantedAuthorities = []

    secUser.getAuthorities().each { secRole ->
       grantedAuthorities << new GrantedAuthorityImpl(secRole.authority)
    }

//    if(grantedAuthorities.size() == 0) {
//       log.error "User needs to have at least one granted authority!"
//       throw new UsernameNotFoundException(messages.getMessage("JdbcDaoImpl.noAuthority", [username] as Object[], "User {0} has no GrantedAuthority"), username)
//    }

    if(log.infoEnabled) {
       log.info "User found for username '${username}'."
    }

    List authorities = (grantedAuthorities.size() >= 0) ? grantedAuthorities : NO_ROLES

    return new WellmiaSecurityUserDetails(secUser.username, secUser.password, secUser.enabled,
                                          !secUser.accountExpired, !secUser.passwordExpired,
                                          !secUser.accountLocked, authorities, secUser.id, "placeholder")
  }
}
