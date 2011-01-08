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
            <a href="${newsItem.link}">
                <img class="thumbnail-inner" alt="${newsItem.title}" src="${resource(dir:'images',file:'mediaimage.png')}"/>
            </a>
        </div>
    </div>
    <div class="news_details">
        <div class="news_headline">
            <a class="news_title" target="_blank" href="${newsItem.link}">${newsItem.title}</a>
        </div>
        <div class="news_age">
            <wellmia:dateFromNow date="${newsItem.publishDate}"/>
        </div>
        <div class="news_description">
            <a class="news_source" href="${newsItem.newsSource.sourceHomeURL}">
                <span class="news_source_border">${newsItem.newsSource.name}</span>
            </a>
            <a class="news_teaser" href="${newsItem.link}">
                &mdash; ${newsItem.content}
            </a>
        </div>
        <div class="health_tab_list">
            <div class="news_health_theme_box">
                <span class="news_health_theme">
                    ${newsItem.category}
                </span>
            </div>
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
                        <strong>SAVE-TEST</strong>
                    </button>
                </span>
            </span>
            <ul class="uiList uiUfi focus_target wUfi">
                <li class="ufiNub uiListItem uiListVerticalItemBorder">
                    <i></i>
                    <input type="hidden" value="1" name="xhp_ufi" autocomplete="off">
                </li>
                <li class="hidden_elem uiFiLike uiListItem uiListVerticalItemBorder"></li>
                <li class="uiUfiComments uiListItem uiListVerticalItemBorder">
                    <ul id="comment${newsItem.id}" class="commentList">
                      <g:each var="comment" in="${newsItem.comments}">
                          <g:render template="/comment/comment" model="[comment : comment]"/>
                      </g:each>
                    </ul>
                </li>
                <li class="uiUfiAddComment clearfix ufiItem uiListItem uiListVerticalItemBorder uiUfiAddCommentCollapsed">
                    <textarea class="uiTextareaNoResize uiTextareaAutogrow textBox textBoxContainer" defaulttext="What do you think?">What do you think?</textarea>
                    <label class="mts commentBtn stat_elem uiButton uiButtonConfirm uiButtonMedium">
                      <g:submitToRemote value="Comment"
                                        url="[controller: 'comment', action: 'addCommentAjax']"
                                        onSuccess="addComment(comment${commentListId},e)"
                                        onLoading="showSpinner(true)"
                                        onComplete="showSpinner(false)"
                                        action="submit" update="[success:'message',failure:'error']" />
                    </label>
                    <img id="spinner" style="display: none" src="<g:createLinkTo dir='/images' file='spinner.gif' alt=''/>"/>
                </li>
            </ul>
        </g:form>
    </div>
</div>
                <g:if test="${newsItem.comments.size() > 0}">
                  <g:render template="/comment/comments" model="[comments : newsItem.comments, commentsListId : newsItem.id]"/>
                </g:if>