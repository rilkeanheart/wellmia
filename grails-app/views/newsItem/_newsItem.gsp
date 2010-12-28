<div class="news_item clearfix">
    <a class="hidable hide_link has_tooltip" title="Hide News Item"></a>
    <div class="news_context group">
        <div class="news_popularity_box">
            <span class="news_popularity_count">
                45
            </span>
            <span class="news_popularity_label">LIKED</span>
        </div>
        <div class="thumbnail">
            <a href="${newsItem.urlLink}">
                <img class="thumbnail-inner" alt="${newsItem.title}" src="${resource(dir:'images',file:'mediaimage.png')}"/>
            </a>
        </div>
    </div>
    <div class="news_details">
        <div class="news_headline">
            <a class="news_title" target="_blank" href="${newsItem.urlLink}">${newsItem.title}</a>
        </div>
        <div class="news_age">
            <wellmia:dateFromNow date="${newsItem.publishDate}"/>
        </div>
        <div class="news_description">
            <a class="news_source" href="${newsItem.newsSource.sourceHomeURL}">
                <span class="news_source_border">${newsItem.newsSource.name}</span>
            </a>
            <a class="news_teaser" href="${newsItem.urlLink}">
                &mdash; ${newsItem.content}
            </a>
        </div>
        <div class="health_tab_list">
            <g:each var="category" in="${newsItem.category}">
              <div class="news_health_theme_box">
                  <span class="news_health_theme">
                      ${category}
                  </span>
              </div>
            </g:each>
        </div>
        <g:form onSubmit="" action="/ajax/crap/" method="post">
            <span class="news_actions uiActionLinks uiActionLinks_bottom">
                <span class="commentAction">
                    <i class="comments_icon"></i>
                    <button class="news_actions_comment isaLink" name="comment" type="button" title="View or Make Comments">
                      <g:if test="${newsItem.comments.size() > 0}">
                        <strong>${newsItem.comments.size()} Comments</strong>
                      </g:if>
                      <g:else>
                        <strong>Comment</strong>
                      </g:else>
                    </button>
                </span>
                <span class="voteAction">
                    | <span class="news_actions_helpful">Helpful?</span>
                    <button class="news_actions_like isaLink" name="like" type="button" title="Was this helpful?">
                        <strong>YES</strong>
                    </button>
                     or
                    <button class="news_actions_dislike isaLink" name="dislike" type="button" title="Was this not helpful?">
                        <strong>NO</strong>
                    </button>
                </span>
                <span class="saveAction">
                    | <button class="news_actions_save isaLink" name="save" type="button" title="Save this item?">
                        <strong>SAVE</strong>
                    </button>
                </span>
            </span>
                <g:if test="${newsItem.comments.size() > 0}">
                  <g:render template="/comment/comments" model="[comments : newsItem.comments, commentsListId : newsItem.id]"/>
                </g:if>
        </g:form>
    </div>
</div>
