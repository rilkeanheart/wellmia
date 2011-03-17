<ul id="answers${question.id}" class="commentList">
  <g:each var="answer" in="${question.getAnswers()}">
      <g:render template="/answer/answer" model="[answer : answer]"/>
  </g:each>
</ul>
