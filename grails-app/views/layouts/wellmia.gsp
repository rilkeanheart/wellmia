<html>
    <head>
        <title><g:layoutTitle default="Wellmia Newpage" /></title>
        <link rel="stylesheet" href="<g:createLinkTo dir='css' file='wellmia.css' />" />
        <g:layoutHead />
        <g:javascript library="jquery" plugin="jquery"/>
    </head>
    <body>
        <div class="wrapper">
            <!-- <div id="topbar" class=""></div> -->
            <div class="header">
                <div id="pageLogo">
                   <a title="Home" href="http://www.wellmia.com/?ref=logo">Home logo</a>
                </div>
            </div>
            <div class="content">
                <div class="primary">
                    <div class="primary">
                      <g:layoutBody />
                    </div>
                    <div class="secondary">
                        <!-- your secondary primary goes here -->
                        <h1>primary secondary content</h1>
                    </div>
                </div>
                <div class="secondary">
                    <!-- your secondary content goes here -->
                    <div class="userIDBlock">
                        <div class="userIcon">
                        </div>
                        <div class="userName">
                            <h1>rilkeanheart</h1>
                        </div>
                    </div>
                    <div class="leftNavSection">
                        <h2>Connect with others</h2>
                        <ul>
                            <li>View My News</li>
                            <li>View My Messages</li>
                            <li>See who is on-line</li>
                            <li>View Groups</li>
                            <li>Search / read questions</li>
                        </ul>
                    </div>
                    <div class="leftNavSection">
                        <h2>Manage My Wellness</h2>
                        <ul>
                            <li>Manage My Wellness</li>
                            <li>Manage My Conditions</li>
                            <li>Obesity</li>
                            <li>Hypertension</li>
                            <li>High Cholesterol</li>
                        </ul>
                    </div>
                    <div class="leftNavSection">
                        <h2>Manage My Medications</h2>
                        <ul>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="footer">
                <!-- your footer content here-->
            </div>
        </div>
        <g:javascript>
              $(".uiUfiAddComment textarea").focus(function () {
                $(this).parent().removeClass('uiUfiAddCommentCollapsed');
                if($(this).val() == $(this).attr('defaulttext'))
                  $(this).val("");
              });
         </g:javascript>
        <g:javascript>
              $(".uiUfiAddComment textarea").blur(function () {
                if($(this).val() == "") {
                  $(this).val($(this).attr('defaulttext'));
                  $(this).parent().addClass('uiUfiAddCommentCollapsed');
                }
              });
         </g:javascript>


    </body>
</html>