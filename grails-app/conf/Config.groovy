import grails.plugins.springsecurity.SecurityConfigType
import com.wellmia.security.SecUser

// locations to search for config files that get merged into the main config
// config files can either be Java properties files or ConfigSlurper scripts

// grails.config.locations = [ "classpath:${appName}-config.properties",
//                             "classpath:${appName}-config.groovy",
//                             "file:${userHome}/.grails/${appName}-config.properties",
//                             "file:${userHome}/.grails/${appName}-config.groovy"]

// if(System.properties["${appName}.config.location"]) {
//    grails.config.locations << "file:" + System.properties["${appName}.config.location"]
// }

grails.project.groupId = appName // change this to alter the default package name and Maven publishing destination
grails.mime.file.extensions = true // enables the parsing of file extensions from URLs into the request format
grails.mime.use.accept.header = false
grails.mime.types = [ html: ['text/html','application/xhtml+xml'],
                      xml: ['text/xml', 'application/xml'],
                      text: 'text/plain',
                      js: 'text/javascript',
                      rss: 'application/rss+xml',
                      atom: 'application/atom+xml',
                      css: 'text/css',
                      csv: 'text/csv',
                      all: '*/*',
                      json: ['application/json','text/json'],
                      form: 'application/x-www-form-urlencoded',
                      multipartForm: 'multipart/form-data'
                    ]

// URL Mapping Cache Max Size, defaults to 5000
//grails.urlmapping.cache.maxsize = 1000

// The default codec used to encode data with ${}
grails.views.default.codec = "html" // none, html, base64
grails.views.gsp.encoding = "UTF-8"
grails.converters.encoding = "UTF-8"
// enable Sitemesh preprocessing of GSP pages
grails.views.gsp.sitemesh.preprocess = true
// scaffolding templates configuration
grails.scaffolding.templates.domainSuffix = 'Instance'

// Set to false to use the new Grails 1.2 JSONBuilder in the render method
grails.json.legacy.builder = false
// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true
// whether to install the java.util.logging bridge for sl4j. Disable for AppEngine!
grails.logging.jul.usebridge = false
// packages to include in Spring bean scanning
grails.spring.bean.packages = []

// set per-environment serverURL stem for creating absolute links
environments {
    production {
        grails.serverURL = "http://wellmia.appspot.com"

        //For certain environments where you don't want mails to be delivered such as during testing
        //grails.mail.disabled=true

        //Lets you override the email address mails are sent to and from
        //grails.mail.overrideAddress="test@address.com"
    }
    development {
        //grails.mail.port = com.icegreen.greenmail.util.ServerSetupTest.SMTP.port
        grails.serverURL = "http://localhost:8080/${appName}"

        //For certain environments where you don't want mails to be delivered such as during testing
        //grails.mail.disabled=true

        //Lets you override the email address mails are sent to and from
        //grails.mail.overrideAddress="test@address.com"
    }
    test {
        //grails.mail.port = com.icegreen.greenmail.util.ServerSetupTest.SMTP.port
        grails.serverURL = "http://localhost:8080/${appName}"

        //For certain environments where you don't want mails to be delivered such as during testing
        //grails.mail.disabled=true

        //Lets you override the email address mails are sent to and from
        //grails.mail.overrideAddress="test@address.com"
    }
}

// log4j configuration
log4j = {
    // Example of changing the log pattern for the default console
    // appender:
    //
    //appenders {
    //    console name:'stdout', layout:pattern(conversionPattern: '%c{2} %m%n')
    //}

    error  'org.codehaus.groovy.grails.web.servlet',  //  controllers
           'org.codehaus.groovy.grails.web.pages', //  GSP
           'org.codehaus.groovy.grails.web.sitemesh', //  layouts
           'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
           'org.codehaus.groovy.grails.web.mapping', // URL mapping
           'org.codehaus.groovy.grails.commons', // core / class loading
           'org.codehaus.groovy.grails.plugins', // plugins
           'org.codehaus.groovy.grails.orm.hibernate', // hibernate integration
           'org.springframework',
           'org.hibernate',
           'net.sf.ehcache.hibernate'

    warn   'org.mortbay.log'

    //trace 'com.icegreen.greenmail'

}

// JQuery
grails.views.javascript.library="jquery"

// Added by the Spring Security Core plugin:
grails.plugins.springsecurity.userLookup.userDomainClassName = 'com.wellmia.security.SecUser'
grails.plugins.springsecurity.authority.className = 'com.wellmia.security.SecRole'
grails.plugins.springsecurity.userLookup.authorityJoinClassName = 'com.wellmia.security.SecUserSecRole'

//grails.plugins.springsecurity.auth.loginFormUrl = "/"
grails.plugins.springsecurity.successHandler.defaultTargetUrl = "/newsFeed/listItems"
//SpringSecurityUtils.securityConfig.successHandler.defaultTargetUrl
//config.successHandler.defaultTargetUrl

// Static Map For Security Settings
grails.plugins.springsecurity.securityConfigType = SecurityConfigType.InterceptUrlMap
grails.plugins.springsecurity.interceptUrlMap = [
      '/'                                       : ['IS_AUTHENTICATED_ANONYMOUSLY'],
      '/login/**'                               : ['IS_AUTHENTICATED_ANONYMOUSLY'],
      '/js/**'                                  : ['IS_AUTHENTICATED_ANONYMOUSLY'],
      '/css/**'                                 : ['IS_AUTHENTICATED_ANONYMOUSLY'],
      '/images/**'                              : ['IS_AUTHENTICATED_ANONYMOUSLY'],
      '/plugins/**'                             : ['IS_AUTHENTICATED_ANONYMOUSLY'],
      '/register/**'                            : ['IS_AUTHENTICATED_ANONYMOUSLY'],
      '/home/**'                                : ['IS_AUTHENTICATED_ANONYMOUSLY'],
      '/terms/**'                               : ['IS_AUTHENTICATED_ANONYMOUSLY'],
      '/privacy/**'                             : ['IS_AUTHENTICATED_ANONYMOUSLY'],
      '/secUser/ajaxEmailUniqueSearch'          : ['IS_AUTHENTICATED_ANONYMOUSLY'],
      '/secUser/ajaxUserUniqueSearch'           : ['IS_AUTHENTICATED_ANONYMOUSLY'],
      '/topics/**'                              : ['IS_AUTHENTICATED_ANONYMOUSLY'],
      '/members/**'                             : ['IS_AUTHENTICATED_ANONYMOUSLY'],
      '/remote_api/**'                          : ['IS_AUTHENTICATED_ANONYMOUSLY'],
      '/cron/**'                                : ['IS_AUTHENTICATED_ANONYMOUSLY'],
      '/classDumper/**'                         : ['IS_AUTHENTICATED_ANONYMOUSLY'],
      '/emailNotify/**'                         : ['IS_AUTHENTICATED_ANONYMOUSLY'],
      '/newsItem/showLink/*'                    : ['IS_AUTHENTICATED_ANONYMOUSLY'],
      '/newsItem/showDetails/*'                 : ['IS_AUTHENTICATED_ANONYMOUSLY'],
      '/consumerProfile/updateInterestTags'     : ['IS_AUTHENTICATED_REMEMBERED'],
      '/consumerProfile/showAvatar'             : ['IS_AUTHENTICATED_REMEMBERED'],
      '/consumerProfile/editAccount'            : ['IS_AUTHENTICATED_REMEMBERED'],
      '/consumerProfile/editNotifications'      : ['IS_AUTHENTICATED_REMEMBERED'],
      '/consumerProfile/editPassword'           : ['IS_AUTHENTICATED_REMEMBERED'],
      '/consumerProfile/editPrivacy'            : ['IS_AUTHENTICATED_REMEMBERED'],
      '/consumerProfile/editProfile'            : ['IS_AUTHENTICATED_REMEMBERED'],
      '/consumerProfile/updateAccount'          : ['IS_AUTHENTICATED_REMEMBERED'],
      '/consumerProfile/updateNotifications'    : ['IS_AUTHENTICATED_REMEMBERED'],
      '/consumerProfile/updatePassword'         : ['IS_AUTHENTICATED_REMEMBERED'],
      '/consumerProfile/updatePrivacy'          : ['IS_AUTHENTICATED_REMEMBERED'],
      '/consumerProfile/updateProfile'          : ['IS_AUTHENTICATED_REMEMBERED'],
      '/consumerProfile/followFeedItemAjax'     : ['IS_AUTHENTICATED_REMEMBERED'],
      '/answer/delete'                          : ['ROLE_ADMIN'],
      '/answer/edit'                            : ['ROLE_ADMIN'],
      '/answer/index'                           : ['ROLE_ADMIN'],
      '/answer/list'                            : ['ROLE_ADMIN'],
      '/answer/save'                            : ['ROLE_ADMIN'],
      '/answer/show'                            : ['ROLE_ADMIN'],
      '/answer/update'                          : ['ROLE_ADMIN'],
      '/newsItem/create'                        : ['ROLE_ADMIN'],
      '/newsItem/delete'                        : ['ROLE_ADMIN'],
      '/newsItem/edit'                          : ['ROLE_ADMIN'],
      '/newsItem/index'                         : ['ROLE_ADMIN'],
      '/newsItem/list'                          : ['ROLE_ADMIN'],
      '/newsItem/save'                          : ['ROLE_ADMIN'],
      '/newsItem/show'                          : ['ROLE_ADMIN'],
      '/newsItem/update'                        : ['ROLE_ADMIN'],
      '/question/create'                        : ['ROLE_ADMIN'],
      '/question/delete'                        : ['ROLE_ADMIN'],
      '/question/edit'                          : ['ROLE_ADMIN'],
      '/question/index'                         : ['ROLE_ADMIN'],
      '/question/list'                          : ['ROLE_ADMIN'],
      '/question/save'                          : ['ROLE_ADMIN'],
      '/question/update'                        : ['ROLE_ADMIN'],
      '/categoryProfile/**'                     : ['ROLE_ADMIN'],
      '/postLaunchLoader/**'                    : ['ROLE_ADMIN'],
      '/categoryTag/**'                         : ['ROLE_ADMIN'],
      '/comment/**'                             : ['ROLE_ADMIN'],
      '/consumerProfile/**'                     : ['ROLE_ADMIN'],
      '/newsSource/**'                          : ['ROLE_ADMIN'],
      '/user/**'                                : ['ROLE_ADMIN'],
      '/index.gsp'                              : ['ROLE_ADMIN'],
      '/controllers.gsp'                        : ['ROLE_ADMIN'],
//      '/newsItem/list'     : ['ROLE_USER,ROLE_ADMIN'],
//       '/newsItem/index'    : ['ROLE_ADMIN'],
//      '/member/*'          : ['IS_AUTHENTICATED_REMEMBERED'],
//      '/post/followAjax'   : ['ROLE_USER'],
//      '/post/addPostAjax'  : ['ROLE_USER', 'IS_AUTHENTICATED_FULLY'],
      '/**'                : ['IS_AUTHENTICATED_REMEMBERED']
]

// Security Registration Code Settings
grails.plugins.springsecurity.wellmia.ui.register.emailBody = '''\
Hi $user.username,<br/>
<br/>
You (or someone pretending to be you) created an account at Wellmia.com with this email address.<br/>
<br/>
If you made the request, please click <a href="$url">here</a> to finish the registration.
'''
grails.plugins.springsecurity.wellmia.ui.register.emailFrom = 'do.not.reply@wellmia.com'
grails.plugins.springsecurity.wellmia.ui.register.emailSubject = 'New Wellmia Account'
grails.plugins.springsecurity.wellmia.ui.register.defaultRoleNames = ['ROLE_USER']
grails.plugins.springsecurity.wellmia.ui.register.postRegisterUrl = null // use defaultTargetUrl if not set

// Security Forgot Password Settings
grails.plugins.springsecurity.wellmia.ui.forgotPassword.emailBody = '''\
Hi $user.username,<br/>
<br/>
You (or someone pretending to be you) requested that your password for Wellmia.com be reset.<br/>
<br/>
If you didn't make this request then ignore the email; no changes have been made.<br/>
<br/>
If you did make the request, then click <a href="$url">here</a> to reset your password.
'''
grails.plugins.springsecurity.wellmia.ui.forgotPassword.emailFrom = 'do.not.reply@wellmia.com'
grails.plugins.springsecurity.wellmia.ui.forgotPassword.emailSubject = 'Wellmia Password Reset'
grails.plugins.springsecurity.wellmia.ui.forgotPassword.postResetUrl = "/register" // use defaultTargetUrl if not set

// User Notify Email Settings
grails.plugins.springsecurity.wellmia.ui.notifyUserOnUpdate.emailBody = '''\
<br/>
You (or someone pretending to be you) requested that your password for Wellmia.com be reset.<br/>
<br/>
If you didn't make this request then ignore the email; no changes have been made.<br/>
<br/>
If you did make the request, then click <a href="$url">here</a> to reset your password.
'''

grails.plugins.springsecurity.wellmia.ui.notifyUserOnUpdate.emailFrom = 'do.not.reply@wellmia.com'
grails.plugins.springsecurity.wellmia.ui.notifyUserOnUpdate.emailSubjectStarter = 'New response to '

// Security Filter Settings
grails.plugins.springsecurity.useSecurityEventListener = true
grails.plugins.springsecurity.onAuthenticationSuccessEvent = { e, appCtx ->
    // handle AuthenticationSuccessEvent

    //Update login dates
    SecUser.withTransaction {
        secUser = SecUser.findByUsername(e.source.principal.username)
        secUser.priorLogin = secUser.lastLogin
        secUser.lastLogin = new Date()
    }
}


// Mail Plugin Settings
grails.mail.default.from="do.not.reply@gmail.com"

/*grails {
   mail {
     host = "smtp.gmail.com"
     port = 465
     username = "youracount@gmail.com"
     password = "yourpassword"
     props = ["mail.smtp.auth":"true",
              "mail.smtp.socketFactory.port":"465",
              "mail.smtp.socketFactory.class":"javax.net.ssl.SSLSocketFactory",
              "mail.smtp.socketFactory.fallback":"false"]

   }
} */

//google.appengine.application="wellmia-stage"
google.appengine.application="wellmia"
