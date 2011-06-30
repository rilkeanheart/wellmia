<div class="feedItem">
    <div class="feedItemHeader">
        <div class="feedItemInfo">
            <a class="news_title_dep headline" target="_blank" href="/newsItem/showLink/${newsItem.id}">${newsItem.title}</a>
          <span class="subheadline">
              <wellmia:dateFromNow date="${newsItem.publishDate}"/>
          </span>
        </div>
        <div class="clear"></div>
    </div>
    <div class="feedItemContent">
      <div class="news_description_dep">
          <a class="news_source_dep source" target="_blank" href="${newsItem.newsSource.sourceHomeURL}">
              <span class="news_source_border_dep"><strong>${newsItem.newsSource.name}</strong></span>
          </a>&mdash; ${newsItem.content}
          <a class="news_teaser_dep link" target="_blank" href="/newsItem/showLink/${newsItem.id}">
              (read more...)
          </a>
      </div>
    </div>
    <div class='feedItemFooter'>
        <span class="feedItemTag"><strong>Tags:</strong>
              <g:each status="i" var="category" in="${newsItem.category}">
                  ${ (i > 0) ? ',    ' : ''}
                  <a href="/topics/${category.replace(' ','_')}">${category}</a>
              </g:each>
        </span>
        <span class="feedItemCommentSummary">
            <a class="source responseLink" href="" name="comment" title="View or Make Comments">
              <g:if test="${newsItem.comments.size() > 0}">${newsItem.comments.size()} Comments</g:if>
              <g:else>Comment</g:else>
            </a>
        </span>
    </div>
    <div class="commentsbox">
        <g:form action="ajaxAdd">
            <input type="hidden" name="commentableItemId" value="${newsItem.id}"/>
            <input type="hidden" name="commentableItemType" value="${newsItem.class.name}"/>
            <ul class="uiList uiUfi focus_target wUfi">
                <li class="commentsList">
                    <ul id="comments${newsItem.id}" class="commentList">
                      <g:each var="comment" in="${newsItem.comments}">
                          <g:render template="/comment/comment" model="[comment : comment]"/>
                      </g:each>
                    </ul>
                </li>
                <g:if test="${newsItem.comments.size() > 0}">
                    <li class="addComment addCommentCollapsed displayItem">
                        <div class="commentContainer commentEditorContainer clear">
                            <img class="avatar_tiny" src="${createLink(controller:'consumerProfile', action:'showAvatar')}" />
                                <div class="textAreaEditorContainer">
                                    <div class="postCommentTextAreaWrapper">
                                        <textarea id="ta${newsItem.id}" class="textEditor" name="content" defaulttext="What do you think?">What do you think?</textarea>
                                    </div>
                                    <label class="mts commentBtn stat_elem uiButton uiButtonConfirm uiButtonMedium">
                                        <g:submitToRemote value="Comment"
                                                          url="[controller: 'newsItem', action: 'addCommentAjax']"
                                                          update="comments${newsItem.id}"
                                                          onSuccess="clearComment('ta${newsItem.id}')"
                                                          onLoading="showSpinner(true, 'spinner${newsItem.id}')"
                                                          onComplete="showSpinner(false, 'spinner${newsItem.id}')"
                                                          class="submitCommentButton"/>
                                    </label>
                                    <img id="spinner${newsItem.id}" class="spinner" style="display: none" src="<g:createLinkTo dir='/images' file='spinner.gif' alt=''/>"/>
                                    <br class="clear"/>
                                </div>
                        </div>
                    </li>
                </g:if>
                <g:else>
                    <li class="addComment addCommentCollapsed displayItem" style="display: none;">
                        <div class="commentContainer commentEditorContainer clear">
                          <img class="avatar_tiny" src="${createLink(controller:'consumerProfile', action:'showAvatar')}" />

                              <div class="textAreaEditorContainer">
                                  <div class="postCommentTextAreaWrapper">
                                      <textarea id="ta${newsItem.id}" class="textEditor" name="content" defaulttext="What do you think?">What do you think?</textarea>
                                  </div>

                                  <label class="mts commentBtn stat_elem uiButton uiButtonConfirm uiButtonMedium">
                                      <g:submitToRemote value="Comment"
                                                        url="[controller: 'newsItem', action: 'addCommentAjax']"
                                                        update="comments${newsItem.id}"
                                                        onSuccess="clearComment('ta${newsItem.id}')"
                                                        onLoading="showSpinner(true, 'spinner${newsItem.id}')"
                                                        onComplete="showSpinner(false, 'spinner${newsItem.id}')"
                                                        class="submitCommentButton"/>
                                  </label>
                                  <img id="spinner${newsItem.id}" class="spinner" style="display: none" src="<g:createLinkTo dir='/images' file='spinner.gif' alt=''/>"/>
                                  <br class="clear"/>
                              </div>

                        </div>
                    </li>
                </g:else>
            </ul>
        </g:form>
    </div>
    <div class="clear"></div>
</div>

