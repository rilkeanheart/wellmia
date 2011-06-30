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
                                <h1>${newsItem.title}</h1>
                                <div>
                                    ${newsItem.rating} Votes
                                </div>
                                <div>
                                    ${newsItem.numberOfViews} Views
                                </div>
                                <g:if test="${bIsFollowed == true}">
                                    <a class="follow_button" href="#" feedItemId="" bShouldFollow="${!bIsFollowed}">Followed</a>
                                </g:if>
                                <g:else>
                                    <a class="follow_button" href="#" feedItemId="" bShouldFollow="${!bIsFollowed}">Follow This</a>
                                </g:else>
                                <input id="itemId" type="hidden" name="feedItemId" value="${newsItem.id}"/>
                                <input type="hidden" name="feedItemType" value="${newsItem.class.name}"/>
                                <div id="pagelet_news_stream_dep" class="news_list_container_dep">
                                    <div id="news_stream_items_dep" class="news_list_dep">
                                        <g:render template="/newsItem/newsItem" model="[newsItem : newsItem]"/>
                                    </div>
                                </div>


    </body>
</html>
