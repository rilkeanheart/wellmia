<%@ page import="com.wellmia.ConsumerProfile" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>ConsumerProfile List</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${resource(dir:'/controllers.gsp')}">Home</a></span>
            <span class="menuButton"><g:link class="create" action="create">New ConsumerProfile</g:link></span>
        </div>
        <div class="body">
            <h1>ConsumerProfile List</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                   	        <g:sortableColumn property="id" title="Id" />
                        
                   	        <g:sortableColumn property="birthDate" title="Birth Date" />
                        
                   	        <g:sortableColumn property="conditions" title="Conditions" />
                        
                   	        <g:sortableColumn property="feedExpirationDate" title="Feed Expiration Date" />
                        
                   	        <g:sortableColumn property="gender" title="Gender" />
                        
                   	        <g:sortableColumn property="photo" title="Photo" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${consumerProfileInstanceList}" status="i" var="consumerProfileInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${consumerProfileInstance.id}">${fieldValue(bean:consumerProfileInstance, field:'id')}</g:link></td>
                        
                            <td>${fieldValue(bean:consumerProfileInstance, field:'birthDate')}</td>
                        
                            <td>${fieldValue(bean:consumerProfileInstance, field:'conditions')}</td>
                        
                            <td>${fieldValue(bean:consumerProfileInstance, field:'feedExpirationDate')}</td>
                        
                            <td>${fieldValue(bean:consumerProfileInstance, field:'gender')}</td>
                        
                            <td>${fieldValue(bean:consumerProfileInstance, field:'photo')}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${consumerProfileInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
