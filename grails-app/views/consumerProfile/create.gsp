<%@ page import="com.wellmia.ConsumerProfile" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Create ConsumerProfile</title>         
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${resource(dir:'/index.gsp')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list">ConsumerProfile List</g:link></span>
        </div>
        <div class="body">
            <h1>Create ConsumerProfile</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${consumerProfileInstance}">
            <div class="errors">
                <g:renderErrors bean="${consumerProfileInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" method="post" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="birthDate">Birth Date:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:consumerProfileInstance,field:'birthDate','errors')}">
                                    <g:datePicker name="birthDate" precision="day" value="${consumerProfileInstance?.birthDate}" />
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="feedExpirationDate">Feed Expiration Date:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:consumerProfileInstance,field:'feedExpirationDate','errors')}">
                                    <g:datePicker name="feedExpirationDate" precision="day" value="${consumerProfileInstance?.feedExpirationDate}" />
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="gender">Gender:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:consumerProfileInstance,field:'gender','errors')}">
                                    <g:textField name="gender" value="${consumerProfileInstance?.gender}" />
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="photo">Photo:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:consumerProfileInstance,field:'photo','errors')}">
                                    
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="secUser">Sec User:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:consumerProfileInstance,field:'secUser','errors')}">
                                    <g:select name="secUser.id" from="${com.wellmia.security.SecUser.list()}" optionKey="id" value="${consumerProfileInstance?.secUser?.id}"  />
                                </td>
                            </tr> 
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><input class="save" type="submit" value="Create" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
