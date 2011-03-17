<%@ page import="com.wellmia.Answer" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Create Answer</title>         
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${resource(dir:'/controllers.gsp')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list">Answer List</g:link></span>
        </div>
        <div class="body">
            <h1>Create Answer</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${answerInstance}">
            <div class="errors">
                <g:renderErrors bean="${answerInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" method="post" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="answerRating">Answer Rating:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:answerInstance,field:'answerRating','errors')}">
                                    <g:textField name="answerRating" value="${fieldValue(bean: answerInstance, field: 'answerRating')}" />
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="content">Content:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:answerInstance,field:'content','errors')}">
                                    <g:textField name="content" value="${answerInstance?.content}" />
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="dateCreated">Date Created:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:answerInstance,field:'dateCreated','errors')}">
                                    <g:datePicker name="dateCreated" precision="day" value="${answerInstance?.dateCreated}" />
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="memberCreatorId">Member Creator Id:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:answerInstance,field:'memberCreatorId','errors')}">
                                    <g:textField name="memberCreatorId" value="${answerInstance?.memberCreatorId}" />
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="memberCreatorUsername">Member Creator Username:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:answerInstance,field:'memberCreatorUsername','errors')}">
                                    <g:textField name="memberCreatorUsername" value="${answerInstance?.memberCreatorUsername}" />
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="questionId">Question Id:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:answerInstance,field:'questionId','errors')}">
                                    <g:textField name="questionId" value="${answerInstance?.questionId}" />
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="subject">Subject:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:answerInstance,field:'subject','errors')}">
                                    <g:textField name="subject" value="${answerInstance?.subject}" />
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
