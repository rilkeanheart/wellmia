<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name='layout' content='registerSplash'/>
<title>Wellmia</title>
</head>

  <body>

      <div id="main">
          <div id="maintop"></div>
          <div id="mainmiddle">
              <table cellpadding="0" cellspacing="0" border="0">
                  <tr>
                      <td><img src="${resource(dir:'images',file:'getconnected.png')}" alt="" /></td>
                      <td><img src="${resource(dir:'images',file:'arrow.png')}" alt="" /></td>
                      <td><img src="${resource(dir:'images',file:'stayinformed.png')}" alt="" /></td>
                      <td><img src="${resource(dir:'images',file:'arrow.png')}" alt="" /></td>
                      <td><img src="${resource(dir:'images',file:'trackprogress.png')}" alt="" /></td>
                  </tr>
                  <tr class="title">
                      <td>Get Connected</td>
                      <td>&nbsp;</td>
                      <td>Stay Informed</td>
                      <td>&nbsp;</td>
                      <td>Track Progress</td>
                  </tr>
                  <tr>
                      <td>
                      <div class="icon">Find others and learn by sharing experiences</div>
                      </td>
                      <td>&nbsp;</td>
                      <td>
                      <div class="icon">Read news targeted towards your health care areas of interest</div>
                      </td>
                      <td>&nbsp;</td>
                      <td>
                      <div class="icon">Keep track of key outcomes for your health</div>
                      </td>
                  </tr>
              </table>
          </div>
          <div id="mainbottom"></div>
          <div id="mainfooter">
              <s2ui:linkButton elementId='register' controller='register' messageCode='wellmia.security.ui.login.register'/>
              It's<img src="${resource(dir:'images',file:'fee.png')}" alt="" />
          </div>
      </div>
  </div>


  <script>
  $(document).ready(function() {
      $('#username').focus();
  });

  <s2ui:initCheckboxes/>

  </script>

</body>
