

<%@ page import="com.wellmia.CategoryTag" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'categoryTag.label', default: 'CategoryTag')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/controllers.gsp')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${categoryTagInstance}">
            <div class="errors">
                <g:renderErrors bean="${categoryTagInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${categoryTagInstance?.id}" />
                <g:hiddenField name="version" value="${categoryTagInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="category"><g:message code="categoryTag.category.label" default="Category" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: categoryTagInstance, field: 'category', 'errors')}">
                                    <g:textField name="category" value="${categoryTagInstance?.category}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="categoryType"><g:message code="categoryTag.categoryType.label" default="Category Type" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: categoryTagInstance, field: 'categoryType', 'errors')}">
                                    <g:textField name="categoryType" value="${categoryTagInstance?.categoryType}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="feedExpirationDate"><g:message code="categoryTag.feedExpirationDate.label" default="Feed Expiration Date" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: categoryTagInstance, field: 'feedExpirationDate', 'errors')}">
                                    <g:datePicker name="feedExpirationDate" precision="day" value="${categoryTagInstance?.feedExpirationDate}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="feedIsStale"><g:message code="categoryTag.feedIsStale.label" default="Feed Is Stale" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: categoryTagInstance, field: 'feedIsStale', 'errors')}">
                                    <g:checkBox name="feedIsStale" value="${categoryTagInstance?.feedIsStale}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="thresholdSensitivity"><g:message code="categoryTag.thresholdSensitivity.label" default="Threshold Sensitivity" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: categoryTagInstance, field: 'thresholdSensitivity', 'errors')}">
                                    <g:textField name="thresholdSensitivity" value="${fieldValue(bean: categoryTagInstance, field: 'thresholdSensitivity')}" />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
