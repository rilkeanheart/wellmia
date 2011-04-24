<ul id="comments${commentableItem.id}" class="commentList">
  <g:each var="comment" in="${commentableItem.getComments()}">
      <g:render template="/comment/comment" model="[comment : comment]"/>
  </g:each>
</ul>
