<ul id="testcomments${newsItem.id}" class="commentList">
  <g:each var="comment" in="${newsItem.getComments()}">
      <g:render template="/comment/comment" model="[comment : comment]"/>
  </g:each>
</ul>
