package com.wellmia

import javax.servlet.http.HttpServletResponse

class EmailNotifyController {

    def mailService

    def sendGroupMail = {
        def emailFrom = URLDecoder.decode(params.emailFrom)
        def subject = URLDecoder.decode(params.subject)
        def msgBody = URLDecoder.decode(params.msgBody)
        //def emailTo = Arrays.asList(URLDecoder.decode(params.emailTo))
        List emailTo = URLDecoder.decode(params.emailTo).tokenize( "[, ]" ).collect { it as String }
        System.out.println("Sending e-mail to:  ${emailTo}")
        mailService.sendGroupMail(emailTo, emailFrom, subject, msgBody)
        response.outputStream << "OK"
        response.setStatus(HttpServletResponse.SC_OK)
    }
}
