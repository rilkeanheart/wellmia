<%@ page import="com.wellmia.NewsSource" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>NewsSource List</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${resource(dir:'/controllers.gsp')}">Home</a></span>
            <span class="menuButton"><g:link class="create" action="create">New NewsSource</g:link></span>
        </div>
        <div class="body">
            <h1>NewsSource List</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                   	        <g:sortableColumn property="id" title="Id" />
                        
                   	        <g:sortableColumn property="mostRecentNewsDate" title="Most Recent News Date" />
                        
                   	        <g:sortableColumn property="name" title="Name" />
                        
                   	        <g:sortableColumn property="newsSourceRank" title="News Source Rank" />
                        
                   	        <g:sortableColumn property="sourceHomeURL" title="Source Home URL" />
                        
                   	        <g:sortableColumn property="sourceRSSLink" title="Source RSSL ink" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${newsSourceInstanceList}" status="i" var="newsSourceInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${newsSourceInstance.id}">${fieldValue(bean:newsSourceInstance, field:'id')}</g:link></td>
                        
                            <td>${fieldValue(bean:newsSourceInstance, field:'mostRecentNewsDate')}</td>
                        
                            <td>${fieldValue(bean:newsSourceInstance, field:'name')}</td>
                        
                            <td>${fieldValue(bean:newsSourceInstance, field:'newsSourceRank')}</td>
                        
                            <td>${fieldValue(bean:newsSourceInstance, field:'sourceHomeURL')}</td>
                        
                            <td>${fieldValue(bean:newsSourceInstance, field:'sourceRSSLink')}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${newsSourceInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
