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
        <title>Wellmia News:  </title>
        <meta name="layout" content="wellmia" />
        <g:javascript library="jquery" plugin="jquery"/>
    </head>
    <body>
        <div id="bd" role="main">
            <div id="yui-main">
                <div class="yui-b">
                    <div class="yui-ge">
                        <div class="yui-u first">
                            <div id="maincenter" class="mystory">
                                <h1>${newsItemInstance.title} - Wellmia</h1>
                                <hr />
                                <div id="pagelet_news_stream_dep" class="news_list_container_dep">
                                    <div id="news_stream_items_dep" class="news_list_dep">
                                      <g:render template="/newsItem/newsItemSummary" model="[newsItemInstance : newsItem]"/>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div role="complementary" class="yui-u">
                            <g:if test="${false}">
                                <g:render template="/addsection" />
                            </g:if>
                        </div>
                    </div>
                </div>
            </div>
            <div id="user" role="sidebar" class="yui-b">
                <img class="sidebarAvatar" src="${resource(dir:'images',file:'user.png')}" alt="user"/>
                <div class="username">
                    <sec:loggedInUserInfo field="username"/>
                </div>
                <!--<div class="usertab">
                  Connect with others
                </div>
                   <ul>
                       <li><a href="#">View My News</a></li>
                       <li><a href="#">View My Messages</a></li>
                       <li><a href="#">See who is on-line</a></li>
                       <li><a href="#">View Groups</a></li>
                       <li><a href="#">Search/Read Questions</a></li>
                   </ul>
                <div class="usertab">
                  Manage My Wellness
                </div>
                   <ul>
                       <li><a href="#">Manage My Wellness</a></li>
                       <li><a href="#"> Manage My Conditions</a></li>
                       <li><a href="#">Obesity</a></li>
                       <li><a href="#">Hypertension</a></li>
                       <li><a href="#">High Cholesterol</a></li>
                   </ul>
                <div class="usertab">
                  <a href="#">Manage My Medications</a>
                </div>-->
            </div>
        </div>
    <g:javascript >
        function clearComment(e) {
            $("#"+e).val('');
        }

        function showSpinner(visible, e) {
        if(visible == true)
            $("#"+e).show();
        else
            $("#"+e).hide();
        }

        function postQuestionOK(data,textStatus) {
            $('#pagelet_news_stream_dep').prepend(data);
        }

        function postQuestionFailure(XMLHttpRequest,textStatus,errorThrown) {
            //TODO:  all error handling here
        }

        function split( val ) {
          return val.split( /,\s*/ );
        }

        function extractLast( term ) {
          return split( term ).pop();
    }
    </g:javascript>
    </body>
</html>
