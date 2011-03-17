<%@ page import="com.wellmia.Question" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Question List</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${resource(dir:'/controllers.gsp')}">Home</a></span>
            <span class="menuButton"><g:link class="create" action="create">New Question</g:link></span>
        </div>
        <div class="body">
            <h1>Question List</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                   	        <g:sortableColumn property="id" title="Id" />
                        
                   	        <g:sortableColumn property="content" title="Content" />
                        
                   	        <g:sortableColumn property="dateCreated" title="Date Created" />
                        
                   	        <g:sortableColumn property="memberCreatorId" title="Member Creator Id" />
                        
                   	        <g:sortableColumn property="memberCreatorUsername" title="Member Creator Username" />
                        
                   	        <g:sortableColumn property="subject" title="Subject" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${questionInstanceList}" status="i" var="questionInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${questionInstance.id}">${fieldValue(bean:questionInstance, field:'id')}</g:link></td>
                        
                            <td>${fieldValue(bean:questionInstance, field:'content')}</td>
                        
                            <td>${fieldValue(bean:questionInstance, field:'dateCreated')}</td>
                        
                            <td>${fieldValue(bean:questionInstance, field:'memberCreatorId')}</td>
                        
                            <td>${fieldValue(bean:questionInstance, field:'memberCreatorUsername')}</td>
                        
                            <td>${fieldValue(bean:questionInstance, field:'subject')}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${questionInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
