<%@ page import="com.wellmia.NewsItem" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Edit NewsItem</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${resource(dir:'/index.gsp')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list">NewsItem List</g:link></span>
            <span class="menuButton"><g:link class="create" action="create">New NewsItem</g:link></span>
        </div>
        <div class="body">
            <h1>Edit NewsItem</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${newsItemInstance}">
            <div class="errors">
                <g:renderErrors bean="${newsItemInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <input type="hidden" name="id" value="${newsItemInstance?.id}" />
                <input type="hidden" name="version" value="${newsItemInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="category">Category:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:newsItemInstance,field:'category','errors')}">
                                    
                                </td>
                            </tr>

                            <g:each var="categoryTag" in="${com.wellmia.CategoryTag.list()}">
                                <tr class="prop">
                                  <td valign="top" class="name">

                                  </td>
                                  <td valign="top" class="value ${hasErrors(bean:newsItemInstance,field:'category','errors')}">
                                    <g:if test="${newsItemInstance?.category?.contains(categoryTag.category)}">
                                      <g:checkBox name="category" value="${categoryTag.category}" checked="${true}"/>   ${categoryTag.category}
                                    </g:if>
                                    <g:else>
                                      <g:checkBox name="category" value="${categoryTag.category}" checked="${false}"/>  ${categoryTag.category}
                                    </g:else>
                                  </td>
                                </tr>
                            </g:each>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="comments">Comments:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:newsItemInstance,field:'comments','errors')}">
                                    
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="content">Content:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:newsItemInstance,field:'content','errors')}">
                                    <g:textField name="content" value="${newsItemInstance?.content}" />
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="dateCreated">Date Created:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:newsItemInstance,field:'dateCreated','errors')}">
                                    <g:datePicker name="dateCreated" precision="day" value="${newsItemInstance?.dateCreated}" />
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="imageHeight">Image Height:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:newsItemInstance,field:'imageHeight','errors')}">
                                    <g:textField name="imageHeight" value="${fieldValue(bean: newsItemInstance, field: 'imageHeight')}" />
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="imageTitle">Image Title:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:newsItemInstance,field:'imageTitle','errors')}">
                                    <g:textField name="imageTitle" value="${newsItemInstance?.imageTitle}" />
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="imageWidth">Image Width:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:newsItemInstance,field:'imageWidth','errors')}">
                                    <g:textField name="imageWidth" value="${fieldValue(bean: newsItemInstance, field: 'imageWidth')}" />
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="isReviewed">Is Reviewed:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:newsItemInstance,field:'isReviewed','errors')}">
                                    <g:checkBox name="isReviewed" value="${newsItemInstance?.isReviewed}" />
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="newsSource">News Source:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:newsItemInstance,field:'newsSource','errors')}">
                                    <g:select name="newsSource.id" from="${com.wellmia.NewsSource.list()}" optionKey="id" value="${newsItemInstance?.newsSource?.id}"  />
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="publishDate">Publish Date:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:newsItemInstance,field:'publishDate','errors')}">
                                    <g:datePicker name="publishDate" precision="day" value="${newsItemInstance?.publishDate}" />
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="title">Title:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:newsItemInstance,field:'title','errors')}">
                                    <g:textField name="title" value="${newsItemInstance?.title}" />
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="urlLink">Url Link:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:newsItemInstance,field:'urlLink','errors')}">
                                    <g:textField name="urlLink" value="${newsItemInstance?.urlLink}" />
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
