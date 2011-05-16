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
        <title>Health Interests:  </title>
        <meta name="layout" content="wellmia" />
        <g:javascript library="jquery" plugin="jquery"/>
    </head>
    <body>
        <div id="bd" role="main">
            <div id="yui-main">
                <div class="yui-b">
                    <div class="yui-ge">
                        <div class="yui-u first">
                            <div id="maincenter" class="health_intereests">
                                <g:if test='${firstLogin}'>
                                    <div id="welcome_dialog" style="display: none;">
                                          <div id="wellmia-welcome" class="title">Wellcome to wellmia <sec:loggedInUserInfo field="username"/>.</div>
                                          <div class="sub-title">Please tell us about your health interests</div>
                                          <div class="sub-title-light">
                                              wellmia works by making it easy to connect with people and stay informed on health topics <strong>most interesting to you.</strong>
                                          </div>
                                          <div class="sub-title-light">
                                              please take a moment and tell us about you.  Use the "Health Interests" tab to tell us what interests you.  You call identify
                                              <strong>health conditions</strong> you currently manage, <strong>therapies</strong> (drugs, procedures, etc.) related to your health
                                              conditions, or even <strong>tests</strong> you may be considering to help manage your health.  The more you tell us, the easier it will
                                              be to connect you.
                                          </div>
                                          <div class="sub-title-light">
                                              Thanks and good health,
                                          </div>
                                          <div class="sub-title-light">
                                              The wellmia team
                                          </div>
                                    </div>
                                </g:if>
                                <div div id="profileMain">
                                    <div id="profileMainHeader"><strong>What Matters to You?</strong></div><hr>
                                    <span class="profileBasicText">Use the fields below to add health interest categories.  <br><br> You can select from hundreds of <strong>conditions</strong> and thousands of <strong>therapies</strong> (drugs, surgical, etc.)</span>
                                    <span class="profileBasicText">The more you add, the easier it will be to connect, share, and learn.</span><br><br>
                                    <div>
                                        <span class="profileBasicText">Here are the most followed health categories:</span>
                                        <hr/>
                                        <ol>
                                            <g:each status="i" var="category" in="${topCategories}">
                                                <g:if test="${i < 10}">
                                                    <li class="column1">
                                                        <a class="news_teaser_dep link" href="/topics/${category.categoryTag.replace(' ','_')}">${category.categoryTag}</a>
                                                        (  ${(category.lastInterestRank - category.interestRank) > 0 ? '+' : '-'} ${(category.lastInterestRank - category.interestRank)}  )
                                                    </li>
                                                </g:if>
                                                <g:else>
                                                    <g:if test="${i == 10}">
                                                        <li class="column2 reset">
                                                            <a class="news_teaser_dep link" href="/topics/${category.categoryTag.replace(' ','_')}">${category.categoryTag}</a>
                                                            (  ${(category.lastInterestRank - category.interestRank) > 0 ? '+' : '-'} ${(category.lastInterestRank - category.interestRank)}  )
                                                        </li>
                                                    </g:if>
                                                    <g:else>
                                                        <li class="column2">
                                                            <a class="news_teaser_dep link" href="/topics/${category.categoryTag.replace(' ','_')}">${category.categoryTag}</a>
                                                            (  ${(category.lastInterestRank - category.interestRank) > 0 ? '+' : '-'} ${(category.lastInterestRank - category.interestRank)}  )
                                                        </li>
                                                    </g:else>
                                                </g:else>
                                            </g:each>
                                        </ol>
                                    </div><br>
                                    <g:form name="updateHealthInterests" url="[action:'updateInterestTags',controller:'consumerProfile']">
                                        <fieldset>
                                            <legend>Your Health Interests</legend>
                                            <div>
                                                <span class="fieldset-sub-header">Add Health Interests</span><br>
                                                <span class="profileFormText">Type in the name of a category and select options to add.</span>
                                                <div id="addInterestBlock">
                                                    <g:textField id="interestTags" class="textField subjectField categoryPicker" name="interestCategories" default="enter topics here...">enter topics here...</g:textField>
                                                    <input id="addCategories" type="button" value="Add">
                                                </div>
                                            </div>
                                            <div id="review_categories">
                                                <span class="fieldset-sub-header">View / Edit Selected Interests</span><br>
                                                <span class="profileFormText">Review selected categories. Click <strong>Submit</strong> when done.</span>
                                                <div id="selected_categories">
                                                    <g:if test='${consumer.interestTags?.isEmpty()}'>
                                                        <h2 id="empty_block">(Empty)</h2>
                                                    </g:if>
                                                    <g:each var="category" in="${consumer.interestTags}">
                                                        <input type="checkbox" name="interests" value="${category}" checked>
                                                        ${category}<br>
                                                    </g:each>
                                                </div>
                                            </div>
                                            <g:if test='${consumer.interestTags?.isEmpty()}'>
                                                <button id="healthInterestUpdateSubmit" type="submit" role="button" style="display: none;">
                                                    Submit Profile
                                                </button>
                                            </g:if>
                                            <g:else>
                                                <button id="healthInterestUpdateSubmit" type="submit" role="button">
                                                    Submit Profile
                                                </button>
                                            </g:else>
                                        </fieldset>
                                    </g:form>
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
                <img class="avatar" src="${createLink(controller:'consumerProfile', action:'showAvatar')}" />
                <div class="username">
                    <sec:loggedInUserInfo field="username"/>
                </div>
                <g:if test="${false}">
                    <div class="usertab">
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
                    </div>
                </g:if>
            </div>
        </div>
        <jq:jquery>
            <g:if test='${firstLogin}'>
                $('#welcome_dialog').dialog({closeOnEscape:true,draggable:false,modal:true,resizable:false,height:400,hide:'fade',position:'center',show:'fade',closeText:'Close',title:'Wellcome to wellmia',width:350,buttons:{OK:function (event) { say('You clicked the ' + $(event.target).text() + ' button' ); }}});
            </g:if>
        </jq:jquery>
        <g:javascript>


        </g:javascript>
    </body>
</html>
