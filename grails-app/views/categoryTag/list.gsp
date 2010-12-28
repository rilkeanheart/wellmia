<%@ page import="com.wellmia.CategoryTag" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>CategoryTag List</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${resource(dir:'/index.gsp')}">Home</a></span>
            <span class="menuButton"><g:link class="create" action="create">New CategoryTag</g:link></span>
        </div>
        <div class="body">
            <h1>CategoryTag List</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                   	        <g:sortableColumn property="id" title="Id" />
                        
                   	        <g:sortableColumn property="category" title="Category" />
                        
                   	        <g:sortableColumn property="thresholdSensitivity" title="Threshold Sensitivity" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${categoryTagInstanceList}" status="i" var="categoryTagInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${categoryTagInstance.id}">${fieldValue(bean:categoryTagInstance, field:'id')}</g:link></td>
                        
                            <td>${fieldValue(bean:categoryTagInstance, field:'category')}</td>
                        
                            <td>${fieldValue(bean:categoryTagInstance, field:'thresholdSensitivity')}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${categoryTagInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
