<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8" />
        <title><g:layoutTitle default="Wellmia" /></title>
        
        <g:render template="/headResources"/>

        
        <g:layoutHead />
    </head>
    <body>
    <g:render template="/header"/>
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


        </div> <!-- /#contentWrapper -->
    <g:render template="/footer" />
    </body>
</html>

