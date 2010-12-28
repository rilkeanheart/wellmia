<%@ page import="com.wellmia.ConsumerProfile" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Show ConsumerProfile</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${resource(dir:'/index.gsp')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list">ConsumerProfile List</g:link></span>
            <span class="menuButton"><g:link class="create" action="create">New ConsumerProfile</g:link></span>
        </div>
        <div class="body">
            <h1>Show ConsumerProfile</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>

                    
                        <tr class="prop">
                            <td valign="top" class="name">Id:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:consumerProfileInstance, field:'id')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Birth Date:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:consumerProfileInstance, field:'birthDate')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Conditions:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:consumerProfileInstance, field:'conditions')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Feed Expiration Date:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:consumerProfileInstance, field:'feedExpirationDate')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Gender:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:consumerProfileInstance, field:'gender')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Interest Tags:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:consumerProfileInstance, field:'interestTags')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Photo:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:consumerProfileInstance, field:'photo')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Sec User:</td>
                            
                            <td valign="top" class="value"><g:link controller="secUser" action="show" id="${consumerProfileInstance?.secUser?.id}">${consumerProfileInstance?.secUser?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Treatments:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:consumerProfileInstance, field:'treatments')}</td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <input type="hidden" name="id" value="${consumerProfileInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" value="Edit" /></span>
                    <span class="button"><g:actionSubmit class="delete" onclick="return confirm('Are you sure?');" value="Delete" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
