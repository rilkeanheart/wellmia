<%@ page import="com.wellmia.CategoryTag" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Create CategoryTag</title>         
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${resource(dir:'/index.gsp')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list">CategoryTag List</g:link></span>
        </div>
        <div class="body">
            <h1>Create CategoryTag</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${categoryTagInstance}">
            <div class="errors">
                <g:renderErrors bean="${categoryTagInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" method="post" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="category">Category:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:categoryTagInstance,field:'category','errors')}">
                                    <g:textField name="category" value="${categoryTagInstance?.category}" />
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="thresholdSensitivity">Threshold Sensitivity:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:categoryTagInstance,field:'thresholdSensitivity','errors')}">
                                    <g:textField name="thresholdSensitivity" value="${fieldValue(bean: categoryTagInstance, field: 'thresholdSensitivity')}" />
                                </td>
                            </tr> 
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><input class="save" type="submit" value="Create" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
