<%@ page import="com.wellmia.NewsSource" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Edit NewsSource</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${resource(dir:'/index.gsp')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list">NewsSource List</g:link></span>
            <span class="menuButton"><g:link class="create" action="create">New NewsSource</g:link></span>
        </div>
        <div class="body">
            <h1>Edit NewsSource</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${newsSourceInstance}">
            <div class="errors">
                <g:renderErrors bean="${newsSourceInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <input type="hidden" name="id" value="${newsSourceInstance?.id}" />
                <input type="hidden" name="version" value="${newsSourceInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="mostRecentNewsDate">Most Recent News Date:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:newsSourceInstance,field:'mostRecentNewsDate','errors')}">
                                    <g:datePicker name="mostRecentNewsDate" precision="day" value="${newsSourceInstance?.mostRecentNewsDate}" />
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="name">Name:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:newsSourceInstance,field:'name','errors')}">
                                    <g:textField name="name" value="${newsSourceInstance?.name}" />
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="newsItems">News Items:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:newsSourceInstance,field:'newsItems','errors')}">
                                    
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="newsSourceRank">News Source Rank:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:newsSourceInstance,field:'newsSourceRank','errors')}">
                                    <g:textField name="newsSourceRank" value="${fieldValue(bean: newsSourceInstance, field: 'newsSourceRank')}" />
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="sourceHomeURL">Source Home URL:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:newsSourceInstance,field:'sourceHomeURL','errors')}">
                                    <g:textField name="sourceHomeURL" value="${newsSourceInstance?.sourceHomeURL}" />
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="sourceRSSLink">Source RSSL ink:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:newsSourceInstance,field:'sourceRSSLink','errors')}">
                                    <g:textField name="sourceRSSLink" value="${newsSourceInstance?.sourceRSSLink}" />
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
