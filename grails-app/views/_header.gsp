<!-- template _header.gsp -->
<div id="headHorizBar"></div>
<div id="wrapper" class="container_12">
        <!--<div id="hd" role="banner">-->
        <header role="banner">
            <a href="/home" id="logo"><img src="${resource(dir:'images',file:'logo-header.png')}" alt="Wellmia" /></a>
            <sec:ifLoggedIn>
                <div id="menu">
                    <nav:render group="mainTabs"/>
                    <ul id="logout">
                        <li><g:link controller="consumerProfile" action="editAccount"><sec:username/></g:link></li>
                        <li><g:link controller="logout">logout</g:link></li>
                    </ul>
                </div>
                <!--<div class="search">
                    <form action="#" method="post">
                        <div class="searchinput"><input type="text" /></div>
                        <input type="image" src="${resource(dir:'images',file:'search.png')}" />
                    </form>
                </div>-->
            </sec:ifLoggedIn>
            <sec:ifNotLoggedIn>
                <form action='${postUrl}' method='POST' id="loginForm" name="loginForm" autocomplete='off'>
                    <p id="loginErrorMessage">User name or password was incorrect</p>
                    <div>
                        <input type="text" name="j_username" id="username" value="" tabindex="1"/>
                        <input type="checkbox" class="checkbox" name="${rememberMeParameter}" id="remember_me" checked="checked" tabindex="3"/><label for="remember_me">Remember me</label>
                    </div>
                    <div>
                        <input type="password" name="j_password" id="password" value="" tabindex="2"/>
                        <span class="forgot-link">
                            <g:link controller='register' action='forgotPassword'><g:message code='wellmia.security.ui.login.forgotPassword'/></g:link>
                        </span>
                    </div>
                    <input type="submit" value="Login" id="loginButton" tabindex="4" />                            
                </form>
            </sec:ifNotLoggedIn>
        </header>
<!-- End _header.gsp.  Note: div#wrapper is open-ended in this template -->