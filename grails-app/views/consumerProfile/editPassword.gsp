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
        <title><sec:loggedInUserInfo field="username"/> Password Settings</title>
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
                                <div>
                                    <h1>
                                      <a href="/consumerProfile/editAccount"><img alt="<sec:loggedInUserInfo field="username"/>" height="48" class="avatar_tiny" src="/consumerProfile/showAvatar" width="48" /></a><sec:loggedInUserInfo field="username"/>&rsquo;s settings
                                    </h1>
                                    <ul id="settings_nav" class="tabs clearfix" role="navigation">
                                      <li><a href="/consumerProfile/editAccount" id="account_tab">Account</a></li>
                                      <li><a href="/consumerProfile/editPassword" id="password_tab">Password</a></li>
                                      <li><a href="/consumerProfile/editProfile"  id="profile_tab">Profile</a></li>
                                      <li><a href="/consumerProfile/editPrivacy"  id="privacy_tab">Privacy</a></li>
                                      <li><a href="/consumerProfile/editNotifications" id="notifications_tab">Notifications</a></li>
                                    </ul>
                                </div>
                                <div div id="profileMain">
                                    <g:form name="updateHealthInterests" url="[action:'updateInterestTags',controller:'consumerProfile']">
                                        <div class="clearfix">
                                            <label>Username</label>
                                        <div class="input">
                                            <p class="uneditable-input"><sec:loggedInUserInfo field="username"/></p>
                                        </div>
                                        <div class="clearfix form-swap user-email">
                                            <label for="user_email">Email</label>
                                            <div class="input">
                                                <input id="user_email" name="user[email]" size="30" type="text" value="user.email" />
                                                <small class="availability" style="display:none">Checking&hellip;</small>
                                                <small class="msg"></small>
                                                <p class="help-block instruction">Note: email will never be publicly displayed.</p>
                                            </div>
                                        </div>
                                        <div class="actions">
                                            <button id="settings_save" class="btn btn-m btn-blue" type="button">Save</button>
                                        </div>
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