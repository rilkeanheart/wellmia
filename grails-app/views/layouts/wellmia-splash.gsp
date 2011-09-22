<html>
    <head>

        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

        <title><g:layoutTitle default='wellmia'/></title>
        
        <%--<g:render template="/headerResources"/>--%>

        <link rel="shortcut icon" href="${resource(dir:'images',file:'favicon.ico')}" type="image/x-icon"/>
        <link rel="stylesheet" href="http://yui.yahooapis.com/2.8.0r4/build/reset-fonts-grids/reset-fonts-grids.css" type="text/css">
        <style type="text/css">#custom-doc { width:82.69em;*width:80.7em;min-width:1075px; margin:auto; text-align:left; }</style>
        <%--<g:javascript library='jquery' plugin='jquery' />--%>

        <!-- <link rel="stylesheet" media="screen" href="${resource(dir:'css',file:'reset.css')}"/>
        <link rel="stylesheet" media="screen" href="${resource(dir:'css',file:'spring-security-ui.css')}"/>
        <link rel="stylesheet" media="screen" href="${resource(dir:'css/smoothness',file:'jquery-ui-1.8.2.custom.css',plugin:'spring-security-ui')}"/>
        <link rel="stylesheet" media="screen" href="${resource(dir:'css',file:'jquery.jgrowl.css')}"/>
        <link rel="stylesheet" media="screen" href="${resource(dir:'css',file:'jquery.safari-checkbox.css')}"/>
        <link rel="stylesheet" media="screen" href="${resource(dir:'css',file:'auth.css')}"/>
        <link rel="stylesheet" media="screen" href="${resource(dir:'css',file:'style.css')}" /> -->

        <g:render template="/headerResources"/>

        <script type="text/javascript">

          /*var _gaq = _gaq || [];
          _gaq.push(['_setAccount', 'UA-21722315-1']);
          _gaq.push(['_trackPageview']);

          (function() {
            var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
            ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
            var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
          })();*/

        </script>
        <g:layoutHead/>

    </head>
    <body>
        <div id="custom-doc" class="yui-t7">
            <div id="wrapper">
                <div id="hd">
                    <div id="logo"><a href="/home"><img src="${resource(dir:'images',file:'logo.png')}" alt="Wellmia" /></a></div>
                    <div id="login">
                        <form action='${postUrl}' method='POST' id="loginForm" name="loginForm" autocomplete='off'>
                            <table cellpadding="0" cellspacing="0" border="0">
                                <tr>
                                    <td>User name</td>
                                    <td>Password</td>
                                    <td>&nbsp;</td>
                                </tr>
                                <tr>
                                    <td><input type="text" name="j_username" id="username" value="" /></td>
                                    <td><input type="password" name="j_password" id="password" value="" /></td>
                                    <td><a id="loginButton" href='javascript:void(0)'>Login</a></td>
                                </tr>
                                <tr id="loginErrorContainer" style="display: none">
                                    <td id="loginErrorMessage" colspan="3">User name and password not found</td>
                                </tr>
                                <tr>
                                    <td><input type="checkbox" class="checkbox" name="${rememberMeParameter}" id="remember_me" checked="checked" />Remember me</td>
                                    <td>
                                      <span class="forgot-link">
                                          <g:link controller='register' action='forgotPassword'><g:message code='wellmia.security.ui.login.forgotPassword'/></g:link>
                                      </span>
                                    </td>
                                    <td>&nbsp;</td>
                                </tr>
                            </table>
                        </form>
                    </div>
                <div class="clear"></div>
            </div>
            <div id="nav"></div>
            <%--<g:javascript src='jquery/jquery.jgrowl.js'/>
            <g:javascript src='jquery/jquery.checkbox.js'/>
            <g:javascript src='jquery/jquery.password_strength.js'/>
            <jqui:resources />
            <g:javascript src='spring-security-ui.js'/>--%>
            <g:layoutBody/>
            <div id="ft">
                <div id="footernav">
                    <div style="float: left">
                    &copy; 2011
                    <a href="#">Wellmia</a></div>
                    <div style="float: right">
                    <!--<a href="#">About Us</a>
                    <a href="#">Contact</a>
                    <a href="#">Blog</a>
                    <a href="#">Status</a>
                    <a href="#">Resources</a>
                    <a href="#">API</a>
                    <a href="#">Business</a>
                    <a href="#">Help</a>
                    <a href="#">Jobs</a>-->
                    <g:if test="${true}">
                        <a target="_blank" href="http://www.getsatisfaction.com/wellmia">Feedback</a>
                        <a href="/terms">Terms</a>
                        <a href="/privacy">Privacy</a>
                    </g:if>
                    </div>
                </div>
            </div>
        </div>
        <g:javascript>
            $(document).ready(function() {

                $('#loginButton').button().click(function(){
                    $('#loginErrorContainer').hide();
                    var dataString = $('#loginForm').serialize();
                    var errorMessage = $('#loginErrorMessage');
                    var errorMessageContainer = $('#loginErrorContainer');

                    $.ajax({
                        type: "POST",
                        url: "${postUrl}",
                        data: dataString,
                        dataType: "json",
                        success: function(jsonData, textStatus) {
                            if (jsonData.success) {
                                // data.redirect contains the string URL to redirect to
                                window.location.replace("${defaultTargetURL}");
                            }
                            else {
                                $('#loginErrorMessage').val(jsonData.error);
                                $('#loginErrorContainer').show();
                            }
                        }
                    });
                });

            });
        </g:javascript>

    </body>
</html>
