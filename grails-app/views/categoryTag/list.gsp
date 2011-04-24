<%@ page import="com.wellmia.CategoryTag" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'categoryTag.label', default: 'CategoryTag')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/controllers.gsp')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="id" title="${message(code: 'categoryTag.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="category" title="${message(code: 'categoryTag.category.label', default: 'Category')}" />
                        
                            <g:sortableColumn property="categoryType" title="${message(code: 'categoryTag.categoryType.label', default: 'Category Type')}" />
                        
                            <g:sortableColumn property="feedExpirationDate" title="${message(code: 'categoryTag.feedExpirationDate.label', default: 'Feed Expiration Date')}" />
                        
                            <g:sortableColumn property="feedIsStale" title="${message(code: 'categoryTag.feedIsStale.label', default: 'Feed Is Stale')}" />
                        
                            <g:sortableColumn property="thresholdSensitivity" title="${message(code: 'categoryTag.thresholdSensitivity.label', default: 'Threshold Sensitivity')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${categoryTagInstanceList}" status="i" var="categoryTagInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${categoryTagInstance.id}">${fieldValue(bean: categoryTagInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: categoryTagInstance, field: "category")}</td>
                        
                            <td>${fieldValue(bean: categoryTagInstance, field: "categoryType")}</td>
                        
                            <td><g:formatDate date="${categoryTagInstance.feedExpirationDate}" /></td>
                        
                            <td><g:formatBoolean boolean="${categoryTagInstance.feedIsStale}" /></td>
                        
                            <td>${fieldValue(bean: categoryTagInstance, field: "thresholdSensitivity")}</td>
                        
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
