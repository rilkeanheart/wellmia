<%@ page import="com.wellmia.NewsItem" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>NewsItem List</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${resource(dir:'/controllers.gsp')}">Home</a></span>
            <span class="menuButton"><g:link class="create" action="create">New NewsItem</g:link></span>
        </div>
        <div class="body">
            <h1>NewsItem List</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                   	        <g:sortableColumn property="id" title="Id" />
                        
                   	        <g:sortableColumn property="category" title="Category" />
                        
                   	        <g:sortableColumn property="comments" title="Comments" />
                        
                   	        <g:sortableColumn property="content" title="Content" />
                        
                   	        <g:sortableColumn property="dateCreated" title="Date Created" />
                        
                   	        <g:sortableColumn property="imageHeight" title="Image Height" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${newsItemInstanceList}" status="i" var="newsItemInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${newsItemInstance.id}">${fieldValue(bean:newsItemInstance, field:'id')}</g:link></td>
                        
                            <td>${fieldValue(bean:newsItemInstance, field:'category')}</td>
                        
                            <td>${fieldValue(bean:newsItemInstance, field:'comments')}</td>
                        
                            <td>${fieldValue(bean:newsItemInstance, field:'content')}</td>
                        
                            <td>${fieldValue(bean:newsItemInstance, field:'dateCreated')}</td>
                        
                            <td>${fieldValue(bean:newsItemInstance, field:'imageHeight')}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${newsItemInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
