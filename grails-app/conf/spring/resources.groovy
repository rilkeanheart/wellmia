// Place your Spring DSL code here
beans = {

  // Security
  userDetailsService(com.wellmia.security.WellmiaSecurityUserDetailsService)
//  xmlns security: 'http://www.springframework.org/schema/security'

//  wellmiaSecurityUserDetailsService(com.wellmia.security.WellmiaSecurityUserDetailsService) {
//    userService = ref('userService')
//  }

//  passwordEncoder(org.springframework.security.authentication.encoding.Md5PasswordEncoder)

//  security.'http'('auto-config': true, 'access-denied-page': '/user/index') {
//    security.'intercept-url'('pattern': '/user/index*', 'filters': 'none')
//    security.'intercept-url'('pattern': '/user/signup', 'filters': 'none')
//    security.'intercept-url'('pattern': '/favicon.ico', 'filters': 'none')
//    security.'intercept-url'('pattern': '/**/*.html', 'access': 'ROLE_USER')
//    security.'intercept-url'('pattern': '/**/*.js', 'access': 'ROLE_USER')
//    security.'intercept-url'('pattern': '/**/*.gsp', 'access': 'ROLE_USER')
//    security.'intercept-url'('pattern': '/**', 'access': 'ROLE_USER')
//    security.'form-login'('authentication-failure-url': '/user/index?login_error=1', 'default-target-url': '/', 'login-page': '/user/index')
//    security.'remember-me'('key': '99sdfll74soq')
//    security.'logout'('logout-success-url': '/user/index')
//  }


//  security.'authentication-manager'('alias': 'authenticationManager') {
//    security.'authentication-provider'('user-service-ref': 'jpaUserDetailsService') {
//      security.'password-encoder'('hash': 'md5')
//    }
//  }
}
