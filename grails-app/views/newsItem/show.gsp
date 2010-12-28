<%@ page import="com.wellmia.NewsItem" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Show NewsItem</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${resource(dir:'/index.gsp')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list">NewsItem List</g:link></span>
            <span class="menuButton"><g:link class="create" action="create">New NewsItem</g:link></span>
        </div>
        <div class="body">
            <h1>Show NewsItem</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>

                    
                        <tr class="prop">
                            <td valign="top" class="name">Id:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:newsItemInstance, field:'id')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Category:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:newsItemInstance, field:'category')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Comments:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:newsItemInstance, field:'comments')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Content:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:newsItemInstance, field:'content')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Date Created:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:newsItemInstance, field:'dateCreated')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Image Height:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:newsItemInstance, field:'imageHeight')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Image Title:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:newsItemInstance, field:'imageTitle')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Image Width:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:newsItemInstance, field:'imageWidth')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Is Reviewed:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:newsItemInstance, field:'isReviewed')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">News Source:</td>
                            
                            <td valign="top" class="value"><g:link controller="newsSource" action="show" id="${newsItemInstance?.newsSource?.id}">${newsItemInstance?.newsSource?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Publish Date:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:newsItemInstance, field:'publishDate')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Title:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:newsItemInstance, field:'title')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Url Link:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:newsItemInstance, field:'urlLink')}</td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <input type="hidden" name="id" value="${newsItemInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" value="Edit" /></span>
                    <span class="button"><g:actionSubmit class="delete" onclick="return confirm('Are you sure?');" value="Delete" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
