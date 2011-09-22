<!-- Begin /newsItem/_newsItemTags.gsp -->
<ul class='feedItemTag'>
	<g:each status="i" var="category" in="${newsItem.category}">
	    <li><a href="/topics/${category.replace(' ','_')}">${category}</a></li>
	</g:each>
</ul>
<!-- End /newsItem/_newsItemTags.gsp -->