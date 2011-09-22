<%--
  Created by IntelliJ IDEA.
  User: mike
  Date: 12/26/10
  Time: 5:34 AM
  To change this template use File | Settings | File Templates.
--%>

<%--<%@ page contentType="text/html;charset=UTF-8" %>  --%>
<html>
    <head>
        <g:if test="${newsItem}!=null">
            <title>${newsItem.title} - Wellmia</title>
        </g:if>
        <g:else>
            <title>Page Not Found - Wellmia</title>
        </g:else>
        <meta name="layout" content="wellmia" />
    </head>
    <body>
        <section class="showDetails">
            <h1 class="articleTitle"><a href="/newsItem/showDetails/${newsItem.id}">${newsItem.title}</a></h1>
            <div class="articleUtilities">
                <g:render template="/newsItem/newsItemStats" />
                <g:if test="${bIsFollowed == true}">
                    <a class="follow_button" href="#" feedItemId="" bShouldFollow="${!bIsFollowed}">Followed</a>
                </g:if>
                <g:else>
                    <a class="follow_button" href="#" feedItemId="" bShouldFollow="${!bIsFollowed}">Follow This</a>
                </g:else>
            </div>
            <input id="itemId" type="hidden" name="feedItemId" value="${newsItem.id}"/>
            <input type="hidden" name="feedItemType" value="${newsItem.class.name}"/>
            <g:render template="/newsItem/newsItem" model="[newsItem : newsItem]"/>
        </section>
    </body>
</html>
