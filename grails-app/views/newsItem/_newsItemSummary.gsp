<article class="feedItem">
    <!--<div class="feedItemImage">
        <img src="${resource(dir:'images',file:'news.png')}" alt="news"/>
    </div>-->
	<ul class="feedItemStats">
   	 	<li class="feedItemVotes">${newsItem.rating} <span>Votes</span></li>
	    <li class="feedItemViews">${newsItem.numberOfViews} <span>Views</span></li>
	    <li class="feedItemComments"><a href="/newsItem/showDetails/${newsItem.id}" title="view comments">${newsItem.comments.size()}<span>Comments</span></a></li>
	</ul>
	<section class="feedItemContent">
        <h2><a href="/newsItem/showDetails/${newsItem.id}">${newsItem.title}</a></h2>
        <h3 class="subheadline"><wellmia:dateFromNow date="${newsItem.publishDate}"/> &mdash; <a class="source" target="_blank" href="${newsItem.newsSource.sourceHomeURL}">${newsItem.newsSource.name}</a></h3>
    	<ul class='feedItemTag'>
	        <g:each status="i" var="category" in="${newsItem.category}">
	            <li><a href="/topics/${category.replace(' ','_')}">${category}</a></li>
	        </g:each>
	    </ul>
	</section>
</article>

