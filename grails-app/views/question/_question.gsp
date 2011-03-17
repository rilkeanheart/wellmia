<%--
  Created by IntelliJ IDEA.
  User: mike
  Date: 2/6/11
  Time: 2:38 PM
  To change this template use File | Settings | File Templates.
--%>
<div class="feedItem">
    <div class="feedItemHeader">
        <div class="feedItemImage">
            <img src="${resource(dir:'images',file:'question.png')}" alt="question"/>
        </div>
        <div class="feedItemInfo">
            <span class="news_title_dep headline">${question.subject}</span>
          <span class="subheadline">
              <wellmia:dateFromNow date="${question.dateCreated}"/>
          </span>
        </div>
        <div class="clear"></div>
    </div>
    <div class="feedItemContent">
      <div class="news_description_dep">
          <a class="news_source_dep source" href="${resource(absolute:'true',dir:question.memberCreatorUsername)}">
              <span class="news_source_border_dep"><strong>${question.memberCreatorUsername}</strong></span>
          </a>
          <span class="news_teaser_dep teaser" > &mdash; ${question.content} </span>
      </div>
    </div>
    <div class='feedItemFooter'>
        <span class="feedItemTag"><strong>Tags:</strong>
              <g:each status="i" var="category" in="${question.category}">
                  ${ (i > 0) ? ',    ' : ''}
                  ${category}
              </g:each>
        </span>
        <span class="feedItemCommentSummary">
            <a class="source" href="" name="comment" title="View or Make Comments">
              <g:if test="${question.comments.size() > 0}">${question.comments.size()} Comments</g:if>
              <g:else>Comment</g:else>
            </a>
        </span>
    </div>
    <div class="commentsbox">
        <g:form action="ajaxAdd">
            <input type="hidden" name="commentableItemId" value="${question.id}"/>
            <input type="hidden" name="commentableItemType" value="${question.class.name}"/>
            <ul class="uiList uiUfi focus_target wUfi">
                <li class="commentsList">
                    <ul id="comments${question.id}" class="commentList">
                      <g:each var="comment" in="${question.comments}">
                          <g:render template="/comment/comment" model="[comment : comment]"/>
                      </g:each>
                    </ul>
                </li>
                <g:if test="${question.comments.size() > 0}">
                    <li class="addComment addCommentCollapsed displayItem">
                        <div class="commentContainer commentEditorContainer clear">
                            <img class="postCommentAvatar" src="${resource(dir:'images',file:'suer.png')}" alt="avatar"/>
                                <div class="textAreaEditorContainer">
                                    <div class="postCommentTextAreaWrapper">
                                        <textarea id="ta${question.id}" class="textEditor" name="content" defaulttext="What do you think?">What do you think?</textarea>
                                    </div>
                                    <label class="mts commentBtn stat_elem uiButton uiButtonConfirm uiButtonMedium">
                                        <g:submitToRemote value="Comment"
                                                          url="[controller: 'question', action: 'addCommentAjax']"
                                                          update="comments${question.id}"
                                                          onSuccess="clearComment('ta${question.id}')"
                                                          onLoading="showSpinner(true, 'spinner${question.id}')"
                                                          onComplete="showSpinner(false, 'spinner${question.id}')"
                                                          class="submitCommentButton"/>
                                    </label>
                                    <img id="spinner${question.id}" class="spinner" style="display: none" src="<g:createLinkTo dir='/images' file='spinner.gif' alt=''/>"/>
                                    <br class="clear"/>
                                </div>
                        </div>
                    </li>
                </g:if>
                <g:else>
                    <li class="addComment addCommentCollapsed displayItem" style="display: none;">
                        <div class="commentContainer commentEditorContainer clear">
                          <img class="postCommentAvatar" src="${resource(dir:'images',file:'suer.png')}" alt="avatar"/>

                              <div class="textAreaEditorContainer">
                                  <div class="postCommentTextAreaWrapper">
                                      <textarea id="ta${question.id}" class="textEditor" name="content" defaulttext="What do you think?">What do you think?</textarea>
                                  </div>

                                  <label class="mts commentBtn stat_elem uiButton uiButtonConfirm uiButtonMedium">
                                      <g:submitToRemote value="Comment"
                                                        url="[controller: 'question', action: 'addCommentAjax']"
                                                        update="comments${question.id}"
                                                        onSuccess="clearComment('ta${question.id}')"
                                                        onLoading="showSpinner(true, 'spinner${question.id}')"
                                                        onComplete="showSpinner(false, 'spinner${question.id}')"
                                                        class="submitCommentButton"/>
                                  </label>
                                  <img id="spinner${question.id}" class="spinner" style="display: none" src="<g:createLinkTo dir='/images' file='spinner.gif' alt=''/>"/>
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