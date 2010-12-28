<ul class="uiList uiUfi focus_target wUfi">
    <li class="ufiNub uiListItem uiListVerticalItemBorder">
        <i></i>
        <input type="hidden" value="1" name="xhp_ufi" autocomplete="off">
    </li>
    <li class="hidden_elem uiFiLike uiListItem uiListVerticalItemBorder"></li>
    <li class="uiUfiComments uiListItem uiListVerticalItemBorder">
        <ul id="comment${commentListId}" class="commentList">
          <g:each var="comment" in="${comments}">
            <li class="uiUfiComment ufiItem">
                <div class="clearfix uiUfiActorBlock">
                    <div class="commentContent UIImageBlock_Content UIIMageBlock_SmallContent_Content">
                        <a class="memberName" href="${resource(absolute:'true',dir:comment.member.userId)}">${comment.member.userId}:</a>
                        <span>${comment.content}</span>
                    </div>
                </div>
            </li>
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


