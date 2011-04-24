<%@ page import="com.wellmia.CategoryProfile" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Edit CategoryProfile</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${resource(dir:'/controllers.gsp')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list">CategoryProfile List</g:link></span>
            <span class="menuButton"><g:link class="create" action="create">New CategoryProfile</g:link></span>
        </div>
        <div class="body">
            <h1>Edit CategoryProfile</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${categoryProfileInstance}">
            <div class="errors">
                <g:renderErrors bean="${categoryProfileInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <input type="hidden" name="id" value="${categoryProfileInstance?.id}" />
                <input type="hidden" name="version" value="${categoryProfileInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="categoryName">Category Name:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:categoryProfileInstance,field:'categoryName','errors')}">
                                    <g:textField name="categoryName" value="${categoryProfileInstance?.categoryName}" />
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="isExpired">Is Expired:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:categoryProfileInstance,field:'isExpired','errors')}">
                                    <g:checkBox name="isExpired" value="${categoryProfileInstance?.isExpired}" />
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="ngramCounts">Ngram Counts:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:categoryProfileInstance,field:'ngramCounts','errors')}">
                                    
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="ngramRanks">Ngram Ranks:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:categoryProfileInstance,field:'ngramRanks','errors')}">
                                    
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="thresholdSensitivity">Threshold Sensitivity:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:categoryProfileInstance,field:'thresholdSensitivity','errors')}">
                                    <g:textField name="thresholdSensitivity" value="${fieldValue(bean: categoryProfileInstance, field: 'thresholdSensitivity')}" />
                                </td>
                            </tr> 
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" value="Update" /></span>
                    <span class="button"><g:actionSubmit class="delete" onclick="return confirm('Are you sure?');" value="Delete" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
