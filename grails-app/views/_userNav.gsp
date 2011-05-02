<sec:ifLoggedIn>
<img class="avatar" src="${createLink(controller:'consumerProfile', action:'showAvatar')}" />
<div class="username">
    <sec:loggedInUserInfo field="username"/>
</div>
<!--<div class="usertab">
  Connect with others
</div>
   <ul>
       <li><a href="#">View My News</a></li>
       <li><a href="#">View My Messages</a></li>
       <li><a href="#">See who is on-line</a></li>
       <li><a href="#">View Groups</a></li>
       <li><a href="#">Search/Read Questions</a></li>
   </ul>
<div class="usertab">
  Manage My Wellness
</div>
   <ul>
       <li><a href="#">Manage My Wellness</a></li>
       <li><a href="#"> Manage My Conditions</a></li>
       <li><a href="#">Obesity</a></li>
       <li><a href="#">Hypertension</a></li>
       <li><a href="#">High Cholesterol</a></li>
   </ul>
<div class="usertab">
  <a href="#">Manage My Medications</a>
</div>-->
</sec:ifLoggedIn>
<sec:ifNotLoggedIn>
</sec:ifNotLoggedIn>