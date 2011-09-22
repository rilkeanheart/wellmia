<article class="feedItem">
    <!--<div class="feedItemImage">
        <img src="${resource(dir:'images',file:'news.png')}" alt="news"/>
    </div>-->
	<g:render template="/newsItem/newsItemStats"  model="[newsItem : newsItem]" />
	<section class="feedItemContent">
        <h2 class="articleTitle"><a href="/newsItem/showDetails/${newsItem.id}">${newsItem.title}</a></h2>
        <g:render template="/newsItem/newsItemSourceDate"  model="[newsItem : newsItem]" />
    	<g:render template="/newsItem/newsItemTags" model="[newsItem : newsItem]" />
	</section>
</article>

