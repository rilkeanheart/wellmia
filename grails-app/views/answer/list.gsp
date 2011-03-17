<%@ page import="com.wellmia.Answer" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Answer List</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${resource(dir:'/controllers.gsp')}">Home</a></span>
            <span class="menuButton"><g:link class="create" action="create">New Answer</g:link></span>
        </div>
        <div class="body">
            <h1>Answer List</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                   	        <g:sortableColumn property="id" title="Id" />
                        
                   	        <g:sortableColumn property="answerRating" title="Answer Rating" />
                        
                   	        <g:sortableColumn property="content" title="Content" />
                        
                   	        <g:sortableColumn property="dateCreated" title="Date Created" />
                        
                   	        <g:sortableColumn property="memberCreatorId" title="Member Creator Id" />
                        
                   	        <g:sortableColumn property="memberCreatorUsername" title="Member Creator Username" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${answerInstanceList}" status="i" var="answerInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${answerInstance.id}">${fieldValue(bean:answerInstance, field:'id')}</g:link></td>
                        
                            <td>${fieldValue(bean:answerInstance, field:'answerRating')}</td>
                        
                            <td>${fieldValue(bean:answerInstance, field:'content')}</td>
                        
                            <td>${fieldValue(bean:answerInstance, field:'dateCreated')}</td>
                        
                            <td>${fieldValue(bean:answerInstance, field:'memberCreatorId')}</td>
                        
                            <td>${fieldValue(bean:answerInstance, field:'memberCreatorUsername')}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${answerInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
