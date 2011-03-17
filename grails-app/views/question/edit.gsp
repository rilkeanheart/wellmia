<%@ page import="com.wellmia.Question" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Edit Question</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${resource(dir:'/controllers.gsp')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list">Question List</g:link></span>
            <span class="menuButton"><g:link class="create" action="create">New Question</g:link></span>
        </div>
        <div class="body">
            <h1>Edit Question</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${questionInstance}">
            <div class="errors">
                <g:renderErrors bean="${questionInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <input type="hidden" name="id" value="${questionInstance?.id}" />
                <input type="hidden" name="version" value="${questionInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="categories">Categories:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:questionInstance,field:'categories','errors')}">
                                    
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="content">Content:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:questionInstance,field:'content','errors')}">
                                    <g:textField name="content" value="${questionInstance?.content}" />
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="dateCreated">Date Created:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:questionInstance,field:'dateCreated','errors')}">
                                    <g:datePicker name="dateCreated" precision="day" value="${questionInstance?.dateCreated}" />
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="memberCreatorId">Member Creator Id:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:questionInstance,field:'memberCreatorId','errors')}">
                                    <g:textField name="memberCreatorId" value="${questionInstance?.memberCreatorId}" />
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="memberCreatorUsername">Member Creator Username:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:questionInstance,field:'memberCreatorUsername','errors')}">
                                    <g:textField name="memberCreatorUsername" value="${questionInstance?.memberCreatorUsername}" />
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="subject">Subject:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:questionInstance,field:'subject','errors')}">
                                    <g:textField name="subject" value="${questionInstance?.subject}" />
                                </td>
                            </tr> 
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" value="Update" /></span>
                    <span class="button"><g:actionSubmit class="delete" onclick="return confirm('Are you sure?');" value="Delete" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
