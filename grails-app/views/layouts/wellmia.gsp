<html>
    <head>
        <title><g:layoutTitle default="Wellmia" /></title>

        <link rel="shortcut icon" href="${resource(dir:'images',file:'favicon.ico')}" type="image/x-icon"/>
        <link rel="stylesheet" href="http://yui.yahooapis.com/2.8.0r4/build/reset-fonts-grids/reset-fonts-grids.css" type="text/css">
        <style type="text/css">#custom-doc { width:82.69em;*width:80.7em;min-width:1075px; margin:auto; text-align:left; }</style>
        <link rel="stylesheet" href="<g:createLinkTo dir='css' file='style.css' />" />
        <link rel="stylesheet" href="<g:createLinkTo dir='css' file='jquery.multiselect.css' />" />
        <g:javascript library="jquery" plugin="jquery"/>
        <nav:resources override="true"/>
        <script type="text/javascript">

          var _gaq = _gaq || [];
          _gaq.push(['_setAccount', 'UA-21722315-1']);
          _gaq.push(['_trackPageview']);

          (function() {
            var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
            ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
            var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
          })();

        </script>
        <g:layoutHead />
    </head>
    <body>
        <div id="custom-doc" class="yui-t2">
            <div id="wrapper">
                <div id="hd" role="banner">
                    <div id="logo"><a href="/home"><img src="${resource(dir:'images',file:'logo.png')}" alt="Wellmia" /></a></div>
                    <sec:ifLoggedIn>
                        <div id="logout">
                            <a href="#"><sec:username/></a><a href="#">my account</a><g:link controller="logout">logout</g:link>
                        </div>
                        <div class="clear"></div>
                        <div id="nav">
                            <div id="menu">
                                <nav:render group="mainTabs"/>
                            </div>
                            <!--<div class="search">
                                <form action="#" method="post">
                                    <div class="searchinput"><input type="text" /></div>
                                    <input type="image" src="${resource(dir:'images',file:'search.png')}" />
                                </form>
                            </div>-->
                        </div>
                    </sec:ifLoggedIn>
                    <sec:ifNotLoggedIn>
                        <g:link controller='login' action='auth'>Login</g:link>
                    </sec:ifNotLoggedIn>
                </div>
                <g:layoutBody/>
                <div id="ft" role="contentinfo">
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
                            <a target="_blank" href="http://www.getsatisfaction.com/wellmia">Feedback</a>
                            <a href="/terms">Terms</a>
                            <a href="/privacy">Privacy</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <g:javascript src='jquery/jquery.jgrowl.js'/>
        <g:javascript src='jquery/jquery.checkbox.js'/>
        <g:javascript src='jquery/jquery.password_strength.js'/>
        <jqui:resources />
        <g:javascript src='spring-security-ui.js'/>
        <g:javascript src='jquery.multiselect.js'/>
        <g:javascript src='wellmia/wellmia-styles.js'/>
    </body>
</html>
