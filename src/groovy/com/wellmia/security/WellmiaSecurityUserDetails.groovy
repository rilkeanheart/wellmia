package com.wellmia.security

import org.codehaus.groovy.grails.plugins.springsecurity.GrailsUser
import org.springframework.security.core.GrantedAuthority
//import org.springframework.security.core.userdetails.User

/**
 * Created by IntelliJ IDEA.
 * User: mike
 * Date: 12/24/10
 * Time: 6:37 AM
 * To change this template use File | Settings | File Templates.
 */
class WellmiaSecurityUserDetails extends GrailsUser{

  final String placeHolder
  WellmiaSecurityUserDetails(String username, String password, boolean enabled,
                             boolean accountNonExpired, boolean credentialsNonExpired,
                             boolean accountNonLocked, Collection<GrantedAuthority> authorities,
                             String id, String placeHolder) {
    super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities, id)
    this.placeHolder = placeHolder
  }
}
