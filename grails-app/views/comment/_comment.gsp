<li class="comment displayItem">
    <div class="commentContainer clear">
        <div class="clearfix uiUfiActorBlock">
            <img class="avatar_tiny" src="${createLink(controller:'consumerProfile', action:'showAvatar')}" />
            <div class="commentContent commentContent_dep">
                <a class="memberName" href="${resource(absolute:'true',dir:comment.memberCreatorUsername)}">${comment.memberCreatorUsername}:</a>
                <span>${comment.content}</span>
            </div>
            <div class="clear"></div>
        </div>
    </div>
</li>


