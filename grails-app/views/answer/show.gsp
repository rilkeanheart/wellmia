<%@ page import="com.wellmia.Answer" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Show Answer</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${resource(dir:'/controllers.gsp')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list">Answer List</g:link></span>
            <span class="menuButton"><g:link class="create" action="create">New Answer</g:link></span>
        </div>
        <div class="body">
            <h1>Show Answer</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>

                    
                        <tr class="prop">
                            <td valign="top" class="name">Id:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:answerInstance, field:'id')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Answer Rating:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:answerInstance, field:'answerRating')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Content:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:answerInstance, field:'content')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Date Created:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:answerInstance, field:'dateCreated')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Member Creator Id:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:answerInstance, field:'memberCreatorId')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Member Creator Username:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:answerInstance, field:'memberCreatorUsername')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Question Id:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:answerInstance, field:'questionId')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Subject:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:answerInstance, field:'subject')}</td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <input type="hidden" name="id" value="${answerInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" value="Edit" /></span>
                    <span class="button"><g:actionSubmit class="delete" onclick="return confirm('Are you sure?');" value="Delete" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
