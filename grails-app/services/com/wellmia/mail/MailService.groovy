package com.wellmia.mail

import com.google.appengine.api.mail.MailService
import com.google.appengine.api.mail.MailService.Message
import com.google.appengine.api.mail.MailServiceFactory
import javax.mail.MessagingException
import javax.mail.internet.AddressException

class MailService {

 boolean transactional = true

 def sendMail(emailTo, emailFrom, subject, msgBody) {

   try {
       def service = MailServiceFactory.getMailService()

       def msg = new com.google.appengine.api.mail.MailService.Message()
       msg.setSender(emailFrom)
       msg.setTo(emailTo)
       msg.setSubject(subject)
       msg.setHtmlBody(msgBody)
       service.send(msg)

   } catch (AddressException e) {
   // ...
   } catch (MessagingException e) {
   // ...
   }

 }
}
