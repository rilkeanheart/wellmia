<%@ page import="com.wellmia.NewsSource" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Show NewsSource</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${resource(dir:'/index.gsp')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list">NewsSource List</g:link></span>
            <span class="menuButton"><g:link class="create" action="create">New NewsSource</g:link></span>
        </div>
        <div class="body">
            <h1>Show NewsSource</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>

                    
                        <tr class="prop">
                            <td valign="top" class="name">Id:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:newsSourceInstance, field:'id')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Most Recent News Date:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:newsSourceInstance, field:'mostRecentNewsDate')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Name:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:newsSourceInstance, field:'name')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">News Items:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:newsSourceInstance, field:'newsItems')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">News Source Rank:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:newsSourceInstance, field:'newsSourceRank')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Source Home URL:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:newsSourceInstance, field:'sourceHomeURL')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Source RSSL ink:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:newsSourceInstance, field:'sourceRSSLink')}</td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <input type="hidden" name="id" value="${newsSourceInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" value="Edit" /></span>
                    <span class="button"><g:actionSubmit class="delete" onclick="return confirm('Are you sure?');" value="Delete" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
