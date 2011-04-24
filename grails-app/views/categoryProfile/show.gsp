<%@ page import="com.wellmia.CategoryProfile" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Show CategoryProfile</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${resource(dir:'/controllers.gsp')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list">CategoryProfile List</g:link></span>
            <span class="menuButton"><g:link class="create" action="create">New CategoryProfile</g:link></span>
        </div>
        <div class="body">
            <h1>Show CategoryProfile</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>

                    
                        <tr class="prop">
                            <td valign="top" class="name">Id:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:categoryProfileInstance, field:'id')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Category Name:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:categoryProfileInstance, field:'categoryName')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Is Expired:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:categoryProfileInstance, field:'isExpired')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Ngram Counts:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:categoryProfileInstance, field:'ngramCounts')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Ngram Ranks:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:categoryProfileInstance, field:'ngramRanks')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Threshold Sensitivity:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:categoryProfileInstance, field:'thresholdSensitivity')}</td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <input type="hidden" name="id" value="${categoryProfileInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" value="Edit" /></span>
                    <span class="button"><g:actionSubmit class="delete" onclick="return confirm('Are you sure?');" value="Delete" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
