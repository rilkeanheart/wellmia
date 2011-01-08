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
        <g:form action="ajaxAdd">
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
            <input type="hidden" name="commentableItemId" value="${newsItem.id}"/>
            <input type="hidden" name="commentableItemType" value="${newsItem.class.name}"/>
            <ul class="uiList uiUfi focus_target wUfi">
                <li class="ufiNub uiListItem uiListVerticalItemBorder">
                    <i></i>
                    <input type="hidden" value="1" name="xhp_ufi" autocomplete="off">
                </li>
                <li class="hidden_elem uiFiLike uiListItem uiListVerticalItemBorder"></li>
                <li class="uiUfiComments uiListItem uiListVerticalItemBorder">
                    <ul id="comments${newsItem.id}" class="commentList">
                      <g:each var="comment" in="${newsItem.comments}">
                          <g:render template="/comment/comment" model="[comment : comment]"/>
                      </g:each>
                    </ul>
                </li>
                <g:if test="${newsItem.comments.size() > 0}">
                  <li class="uiUfiAddComment clearfix ufiItem uiListItem uiListVerticalItemBorder uiUfiAddCommentCollapsed">
                      <textarea id="ta${newsItem.id}" class="uiTextareaNoResize uiTextareaAutogrow textBox textBoxContainer" name="content" defaulttext="What do you think?">What do you think?</textarea>
                      <label class="mts commentBtn stat_elem uiButton uiButtonConfirm uiButtonMedium">
                        <g:submitToRemote value="Comment"
                                          url="[controller: 'newsItem', action: 'addCommentAjax']"
                                          update="comments${newsItem.id}"
                                          onSuccess="clearComment('ta${newsItem.id}')"
                                          onLoading="showSpinner(true, 'spinner${newsItem.id}')"
                                          onComplete="showSpinner(false, 'spinner${newsItem.id}')"/>
                      </label>
                      <img id="spinner${newsItem.id}" class="spinner" style="display: none" src="<g:createLinkTo dir='/images' file='spinner.gif' alt=''/>"/>
                  </li>
                </g:if>
                <g:else>
                  <li class="uiUfiAddComment clearfix ufiItem uiListItem uiListVerticalItemBorder uiUfiAddCommentCollapsed hide-with-script">
                      <textarea id="ta${newsItem.id}" class="uiTextareaNoResize uiTextareaAutogrow textBox textBoxContainer" name="content" defaulttext="What do you think?">What do you think?</textarea>
                      <label class="mts commentBtn stat_elem uiButton uiButtonConfirm uiButtonMedium">
                        <g:submitToRemote value="Comment"
                                          url="[controller: 'newsItem', action: 'addCommentAjax']"
                                          update="comments${newsItem.id}"
                                          onSuccess="clearComment('ta${newsItem.id}')"
                                          onLoading="showSpinner(true, 'spinner${newsItem.id}')"
                                          onComplete="showSpinner(false, 'spinner${newsItem.id}')"/>
                      </label>
                      <img id="spinner${newsItem.id}" class="spinner" style="display: none" src="<g:createLinkTo dir='/images' file='spinner.gif' alt=''/>"/>
                  </li>
                </g:else>
            </ul>
        </g:form>
    </div>
</div>
