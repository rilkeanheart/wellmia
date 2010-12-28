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
        <title>Wellmia Newspage</title>
        <meta name="layout" content="wellmia" />
     </head>
    <body>
        <!-- your primary primary content goes here -->
        <div id="pagelet_composer">
            <div class="padded_bottom_margin padded_top_margin">
                <div class="uiComposer">
                    <form method="get" action="/" ajaxify="1">
                        <ul class="uiList uiListHorizontal clearfix">
                            <li>
                                <span class="fsm fwn fcg">
                                    <strong>Post:</strong>
                                </span>
                            </li>
                            <li>
                                <ul class="uiList uiListHorizontal clearfix">
                                    <li>
                                        <a class="uiIconLink uiComposerAttachment"  data-endpoint="/ajax/composer/attachment/status/status.php">
                                          <img class="img uiComposerImg" src="${resource(dir:'images',file:'question.png')}"/>
                                            <strong class="attachmentName">
                                                Status
                                                <i class="nub showWhenOpen"></i>
                                            </strong>
                                        </a>
                                    </li>
                                   <li>
                                        <a class="uiIconLink uiComposerAttachment" >
                                            <img class="img uiComposerImg" src="${resource(dir:'images',file:'question.png')}"/>
                                            <strong class="attachmentName">
                                                Question
                                                <i class="nub showWhenOpen"></i>
                                            </strong>
                                        </a>
                                    </li>
                                   <li>
                                        <a class="uiIconLink uiComposerAttachment" >
                                            <img class="img uiComposerImg" src="${resource(dir:'images',file:'question.png')}"/>
                                            <strong class="attachmentName">
                                                Link
                                                <i class="nub showWhenOpen"></i>
                                            </strong>
                                        </a>
                                    </li>
                                </ul>
                            </li>
                        </ul>
                        <input type="hidden" value="2123424" name="targetid" autocomplete="off"/>
                        <input class="hidden_elem" type="submit"  name="xhpc"/>
                    </form>
                </div>
            </div>
        </div>
        <div id="pagelet_stream_header">
            <div class="clearfix padded_bottom_margin padded_top_margin UIInernationalStreamTop">
                <div class="uiHeader uiHeaderWithImage uiHeaderPage wm_stream_header">
                    <div class="clearfix uiHeaderTop">
                        <div class="UIHeaderActions rfloat fsl fwb fcb">
                            Top News
                            <a href="www.cnn.com">
                                <span class="fwn">
                                    Most Recent
                                    <span class="uiBubbleCount mls">
                                        <span class="numberContainer">
                                            <span class="number countValue fsm">${feedList.size()}</span>
                                            <span class="maxCountIndicator"></span>
                                        </span>
                                    </span>
                                </span>
                            </a>
                            <div class="uiStreamHeaderSelect">
                                <form  action="/" method="get">
                                    <select name="newsfeed_subject">
                                        <option value="1">All Conditions</option>
                                        <option value="2">High Blood Pressure</option>
                                        <option value="3">Hypertension</option>
                                        <option value="4">Obesity</option>
                                        <option value="4">Saved</option>
                                    </select>
                                </form>
                                <input class="hidden_elem" type="submit" name="xhpc"/>
                            </div>
                        </div>
                        <div>
                            <h2 class="uiHeaderTitle">
                                <i class="uiHeaderImage img spritemap"></i>
                                My News
                            </h2>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div id="pagelet_news_stream" class="news_list_container">
            <div id="news_stream_items" class="news_list">
              <g:each var="feedItem" in="${feedList}">
                <g:if test="${com.wellmia.NewsItem.class.isInstance(feedItem)}">
                  <g:render template="/newsItem/newsItem" model="[newsItem : feedItem]"/>
                </g:if>
                <g:if test="${com.wellmia.Post.class.isInstance(feedItem)}">
                  <g:render template="/post/post" model="[post : post]"/>
                </g:if>
              </g:each>
            </div>
        </div>
    </body>
</html>
