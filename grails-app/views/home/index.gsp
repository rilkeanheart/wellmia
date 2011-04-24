<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <meta name='layout' content='wellmia-splash'/>
  <title>Wellmia</title>
</head>
<body>
    <div id="bd" role="main">
        <div class="yui-g">
            <div id="main">
                <div id="maintop"></div>
                <div id="mainmiddle">
                    <g:if test="${true}">
                        <div class="title">You are an expert in your own health experiences... Meet other experts.</div>
                        <table cellpadding="0" cellspacing="0" border="0">
                            <tr>
                                <td><img src="${resource(dir:'images',file:'getconnected.png')}" alt="" /></td>
                                <td><img src="${resource(dir:'images',file:'arrow.png')}" alt="" /></td>
                                <td><img src="${resource(dir:'images',file:'stayinformed.png')}" alt="" /></td>
                                <td><img src="${resource(dir:'images',file:'arrow.png')}" alt="" /></td>
                                <!--<td><img src="${resource(dir:'images',file:'trackprogress.png')}" alt="" /></td>-->
                                <td><img src="${resource(dir:'images',file:'privatars.png')}" alt="" /></td>
                            </tr>
                            <tr class="title">
                                <td>Get Connected</td>
                                <td>&nbsp;</td>
                                <td>Stay Informed</td>
                                <td>&nbsp;</td>
                                <td>Keep Private</td>
                                <!--<td>&nbsp;</td>
                                <td>Track Progress</td>-->
                            </tr>
                            <tr>
                                <td>
                                <div class="icon">Find others and learn by sharing experiences</div>
                                </td>
                                <td>&nbsp;</td>
                                <td>
                                <div class="icon">Read news targeted towards your health care areas of interest</div>
                                </td>
                                <td>&nbsp;</td>
                                <td>
                                <div class="icon">Your info stays separate from friends, family, and co-workers</div>
                                </td>
                                <!--<div class="icon">Keep track of key outcomes for your health</div>
                                </td>-->
                            </tr>
                        </table>
                    </g:if>
                </div>
                <div id="mainbottom"></div>
                <div id="mainfooter">
                    <g:if test="${true}">
                        <s2ui:linkButton elementId='register' controller='register' messageCode='wellmia.security.ui.login.register'/>
                        It's<img src="${resource(dir:'images',file:'fee.png')}" alt="" />
                    </g:if>

                </div>
            </div>
        </div>
    </div>
    <script>
        $(document).ready(function() {
            $('#username').focus();
        });
    </script>
</body>