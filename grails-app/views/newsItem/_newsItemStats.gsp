<!-- Begin _feedItemStats.gsp -->
<ul class="feedItemStats">
 	<li class="feedItemVotes">${newsItem.rating} <span>Votes</span></li>
    <li class="feedItemViews">${newsItem.numberOfViews} <span>Views</span></li>
    <li class="feedItemComments"><a href="/newsItem/showDetails/${newsItem.id}" title="view comments">${newsItem.comments.size()} <span>Comments</span></a></li>
</ul>
<!-- End _feedItemStats.gsp -->