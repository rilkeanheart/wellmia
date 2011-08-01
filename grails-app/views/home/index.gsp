<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <meta name='layout' content='wellmia-no-sidebar'/>
  <title>Wellmia</title>
</head>
<body>
    <div id="wellmiaHome">
        <g:if test="${true}">
            <div id="homeBanner">
                <h1>You are an expert in your own health experiences... Meet other experts.</h1>
                <div id="bannerCol1" class="grid_4 prefix_1">
                    <img src="${resource(dir:'images',file:'arrow.png')}" alt="" class="homeBannerArrow" />
                    <div class="innerCol grid_2">
                        <h4>Get Connected</h4>
                        <p class="icon">Find others and learn by sharing experiences</p>
                    </div>
                </div>
                <div id="bannerCol2" class="grid_4">
                    <img src="${resource(dir:'images',file:'arrow.png')}" alt="" class="homeBannerArrow"  />
                    <div class="innerCol grid_2">
                        <h4>Stay Informed</h4>
                        <p class="icon">Read news targeted towards your health care areas of interest</p>
                    </div>
                </div>
                <div id="bannerCol3" class="grid_2">
                    <div class="innerCol grid_2">
                        <h4>Keep Private</h4>
                        <p class="icon">Your info stays separate from friends, family, and co-workers</p>
                    </div>
                </div>                    
                                <!-- <td></td> -->
            </div> <!-- /#homeBanner -->
        </g:if>
        <footer id="homeFooter">
            <g:if test="${true}">
                <s2ui:linkButton elementId='register' controller='register' messageCode='wellmia.security.ui.login.register'/>
                It's<img src="${resource(dir:'images',file:'fee.png')}" alt="" />
            </g:if>
        </footer>
    </div> <!-- /#wellmiaHome -->

</body>