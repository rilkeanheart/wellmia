<%@ page import="com.wellmia.Comment" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Create Comment</title>         
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${resource(dir:'/controllers.gsp')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list">Comment List</g:link></span>
        </div>
        <div class="body">
            <h1>Create Comment</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${commentInstance}">
            <div class="errors">
                <g:renderErrors bean="${commentInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" method="post" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="commentableItemId">Commentable Item Id:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:commentInstance,field:'commentableItemId','errors')}">
                                    <g:textField name="commentableItemId" value="${commentInstance?.commentableItemId}" />
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="content">Content:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:commentInstance,field:'content','errors')}">
                                    <g:textField name="content" value="${commentInstance?.content}" />
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="dateCreated">Date Created:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:commentInstance,field:'dateCreated','errors')}">
                                    <g:datePicker name="dateCreated" precision="day" value="${commentInstance?.dateCreated}" />
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="memberCreatorId">Member Creator Id:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:commentInstance,field:'memberCreatorId','errors')}">
                                    <g:textField name="memberCreatorId" value="${commentInstance?.memberCreatorId}" />
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
