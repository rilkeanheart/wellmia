<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8" />
        <title><g:layoutTitle default="Wellmia" /></title>

        <link rel="shortcut icon" href="${resource(dir:'images',file:'favicon.ico')}" type="image/x-icon"/>
        <!--CSS.  It's better to load CSS conventionally b/c if I load it asynchronously the page load works weirdly-->
        <!--<link rel="stylesheet" type="text/css" href="http://yui.yahooapis.com/2.8.0r4/build/reset-fonts-grids/reset-fonts-grids.css" />-->
        <link rel="stylesheet" type="text/css" href="${createLinkTo(dir:'css', file:'960-reset-text-12col.css')}" />
        <link rel="stylesheet" type="text/less" href="${createLinkTo(dir:'css', file:'style.css')}" />
        <link rel="stylesheet" type="text/css" href="${createLinkTo(dir='css', file='jquery.multiselect.css')}" />
        <link href="/plugins/jquery-ui-1.8.7/jquery-ui/themes/ui-lightness/jquery-ui-1.8.7.custom.css" type="text/css" rel="stylesheet" media="screen, projection" id="jquery-ui-theme" />
        <%-- Loading Modernizr with yepnope.js to serve as the foundation of JS/CSS loading.  Everything else is loaded using their loading functions, as it allows asynchronous loading, which doesn't hold up page load AK--%>
		<script type="text/javascript" src="${createLinkTo(dir:'js/', file:'less-1.1.0.min.js')}"></script>
        <script type="text/javascript" src="${createLinkTo(dir:'js', file:'modernizr.custom.yepnope.js')}"></script>
        <script type="text/javascript">
            //Modernizr.load is the same as yepnope().  It prohibits use of document.write in any of the scripts we load
            Modernizr.load({
                load: {//naming each resource isn't necessary, but it seems like a good idea. AK
                    jQ: "${createLinkTo(dir:'js/jquery', file:'jquery-1.4.4.min.js')}",
                    jgrowl: "${createLinkTo(dir:'js/jquery', file:'jquery.jgrowl.js')}",
                    jQCheckbox: "${createLinkTo(dir:'js/jquery', file:'jquery.checkbox.js')}",
                    jQPassword: "${createLinkTo(dir:'js/jquery', file:'jquery.password_strength.js')}",
                    jQUI: "/plugins/jquery-ui-1.8.7/jquery-ui/js/jquery-ui-1.8.7.custom.min.js",
                    springSecurity: "${createLinkTo(dir:'js', file:'spring-security-ui.js')}",
                    jqMultiSelect: "${createLinkTo(dir:'js', file:'jquery.multiselect.js')}",
                    wmJS: "${createLinkTo(dir:'js/wellmia', file:'wellmia-scripts.js')}"

                }
            });
        </script>
        <%--<g:javascript library="jquery" plugin="jquery"/>--%>
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
        <div id="wrapper" class="container_12">
                <!--<div id="hd" role="banner">-->
                <header role="banner">
                    <a href="/home" id="logo"><img src="${resource(dir:'images',file:'logo.png')}" alt="Wellmia" /></a>
                    <sec:ifLoggedIn>
                        <div id="logout">
                            <a href="/home"><sec:username/></a>
                            <g:link controller="consumerProfile" action="editAccount">my account</g:link>
                            <g:link controller="logout">logout</g:link>
                        </div>
                        <!--<div class="clear"></div>-->
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
                </header>
				<div id="contentWrapper">
		            <div id="content" class="grid_9">
		                <div id="maincenter" class="mystory">
			
							<%-- main content --%>
                			<g:layoutBody/>

						 </div><!--/#maincenter-->

		                <div role="complementary" class="yui-u">
		                    <g:if test="${false}">
		                        <g:render template="/addsection"/>
		                    </g:if>
		                </div>

		            </div><!--/#content-->
		            <g:render template="/userNav" />


		        </div><!--/#bd-->
                <!--<div id="ft" role="contentinfo">-->
                <footer role="contentinfo">
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
                </footer>
        </div>
        <%--<g:javascript src='jquery/jquery.jgrowl.js'/>
        <g:javascript src='jquery/jquery.checkbox.js'/>
        <g:javascript src='jquery/jquery.password_strength.js'/>
        <jqui:resources />
        <g:javascript src='spring-security-ui.js'/>
        <g:javascript src='jquery.multiselect.js'/>
        <g:javascript src='wellmia/wellmia-styles.js'/>--%>
    </body>
</html>

