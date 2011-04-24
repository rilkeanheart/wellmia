<div class="feedItem">
    <div class="feedItemHeader">
        <div class="feedItemImage">
            <img src="${resource(dir:'images',file:'news.png')}" alt="news"/>
        </div>
        <div>
            ${newsItem.rating} Votes
        </div>
        <div>
            ${newsItem.numberOfViews} Views
        </div>
        <div>
            ${newsItem.comments.size()} Comments
        </div>
        <div class="feedItemInfo">
            <a class="news_title_dep headline" href="/newsItem/showDetails/${newsItem.id}">${newsItem.title}</a>
          <span class="subheadline">
              <wellmia:dateFromNow date="${newsItem.publishDate}"/>&mdash;
              <a class="news_source_dep source" target="_blank" href="${newsItem.newsSource.sourceHomeURL}">
                    <span class="news_source_border_dep"><strong>${newsItem.newsSource.name}</strong></span>
              </a>
          </span>
        </div>
        <div class="clear"></div>
    </div>
    <div class='feedItemFooter'>
        <span class="feedItemTag"><strong>Tags:</strong>
              <g:each status="i" var="category" in="${newsItem.category}">
                  ${ (i > 0) ? ',    ' : ''}
                  <a href="/topics/${category.replace(' ','_')}">${category}</a>
              </g:each>
        </span>
    </div>
    <div class="clear"></div>
</div>

