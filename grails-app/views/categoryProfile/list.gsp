<%@ page import="com.wellmia.CategoryProfile" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>CategoryProfile List</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${resource(dir:'/controllers.gsp')}">Home</a></span>
            <span class="menuButton"><g:link class="create" action="create">New CategoryProfile</g:link></span>
        </div>
        <div class="body">
            <h1>CategoryProfile List</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                   	        <g:sortableColumn property="id" title="Id" />
                        
                   	        <g:sortableColumn property="categoryName" title="Category Name" />
                        
                   	        <g:sortableColumn property="isExpired" title="Is Expired" />
                        
                   	        <g:sortableColumn property="ngramCounts" title="Ngram Counts" />
                        
                   	        <g:sortableColumn property="ngramRanks" title="Ngram Ranks" />
                        
                   	        <g:sortableColumn property="thresholdSensitivity" title="Threshold Sensitivity" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${categoryProfileInstanceList}" status="i" var="categoryProfileInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${categoryProfileInstance.id}">${fieldValue(bean:categoryProfileInstance, field:'id')}</g:link></td>
                        
                            <td>${fieldValue(bean:categoryProfileInstance, field:'categoryName')}</td>
                        
                            <td>${fieldValue(bean:categoryProfileInstance, field:'isExpired')}</td>
                        
                            <td>${fieldValue(bean:categoryProfileInstance, field:'ngramCounts')}</td>
                        
                            <td>${fieldValue(bean:categoryProfileInstance, field:'ngramRanks')}</td>
                        
                            <td>${fieldValue(bean:categoryProfileInstance, field:'thresholdSensitivity')}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${categoryProfileInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
