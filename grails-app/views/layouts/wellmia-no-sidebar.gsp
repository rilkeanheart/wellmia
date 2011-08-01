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
	
			<%-- main content --%>
			<g:layoutBody/>


        </div> <!-- /#contentWrapper -->
    <g:render template="/footer" />
    </body>
</html>

