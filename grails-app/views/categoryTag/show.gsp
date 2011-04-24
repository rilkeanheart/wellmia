
<%@ page import="com.wellmia.CategoryTag" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'categoryTag.label', default: 'CategoryTag')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/controllers.gsp')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.show.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="categoryTag.id.label" default="Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: categoryTagInstance, field: "id")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="categoryTag.category.label" default="Category" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: categoryTagInstance, field: "category")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="categoryTag.categoryType.label" default="Category Type" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: categoryTagInstance, field: "categoryType")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="categoryTag.feedExpirationDate.label" default="Feed Expiration Date" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${categoryTagInstance?.feedExpirationDate}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="categoryTag.feedIsStale.label" default="Feed Is Stale" /></td>
                            
                            <td valign="top" class="value"><g:formatBoolean boolean="${categoryTagInstance?.feedIsStale}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="categoryTag.thresholdSensitivity.label" default="Threshold Sensitivity" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: categoryTagInstance, field: "thresholdSensitivity")}</td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${categoryTagInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
