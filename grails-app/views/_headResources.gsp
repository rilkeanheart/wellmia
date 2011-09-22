<!-- template _headResources.gsp -->
<link rel="shortcut icon" href="${resource(dir:'images',file:'favicon.ico')}" type="image/x-icon"/>

<!--CSS.  It's better to load CSS conventionally b/c if I load it asynchronously the page load works weirdly-->
        <!--<link rel="stylesheet" type="text/css" href="http://yui.yahooapis.com/2.8.0r4/build/reset-fonts-grids/reset-fonts-grids.css" />-->
        <link rel="stylesheet" type="text/css" href="${createLinkTo(dir:'css', file:'960-reset-text-12col.css')}" />
        <link rel="stylesheet" type="text/less" href="${createLinkTo(dir:'css', file:'style.css')}" />
        <link rel="stylesheet" type="text/css" href="${createLinkTo(dir:'css', file:'jquery.multiselect.css')}" />
        <link href="/plugins/jquery-ui-1.8.7/jquery-ui/themes/ui-lightness/jquery-ui-1.8.7.custom.css" type="text/css" rel="stylesheet" media="screen, projection" id="jquery-ui-theme" />

<%-- Loading Modernizr with yepnope.js to serve as the foundation of JS/CSS loading.  Everything else is loaded using their loading functions, as it allows asynchronous loading, which doesn't hold up page load AK--%>
<script type="text/javascript" src="${createLinkTo(dir:'js/', file:'less-1.1.0.min.js')}"></script>
<script type="text/javascript" src="${createLinkTo(dir:'js', file:'modernizr.custom.yepnope.js')}"></script>
<script type="text/javascript" src="${createLinkTo(dir:'js/jquery', file:'jquery-1.4.4.min.js')}"></script>
<script type="text/javascript" src="${createLinkTo(dir:'js/jquery', file:'jquery.jgrowl.js')}"></script>
<script type="text/javascript" src="${createLinkTo(dir:'js/jquery', file:'jquery.checkbox.js')}"></script>
<script type="text/javascript" src="${createLinkTo(dir:'js/jquery', file:'jquery.password_strength.js')}"></script>
<script type="text/javascript" src="/plugins/jquery-ui-1.8.7/jquery-ui/js/jquery-ui-1.8.7.custom.min.js"></script>
<script type="text/javascript" src="${createLinkTo(dir:'js', file:'spring-security-ui.js')}"></script>

<script type="text/javascript" src="${createLinkTo(dir:'js', file:'jquery.multiselect.js')}"></script>
<script type="text/javascript" src="${createLinkTo(dir:'js/wellmia', file:'wellmia-styles.js')}"></script>

<script type="text/javascript">
    //Modernizr.load is the same as yepnope().  It prohibits use of document.write in any of the scripts we load
    //I'm going to shelf the idea for now -- scalability is not a priority now
    /*Modernizr.load({
        load: {//naming each resource isn't necessary, but it seems like a good idea. AK
            jgrowl: "",
            jQCheckbox: "",
            jQPassword: "",
            jQUI: "",
            springSecurity: "",
            wmJS: ""

        },
        complete: function() {
            //code to run if there are page-specific code
            if (typeof pageFunctions === 'function'){
                pageFunctions();
            }
        }
    });*/
</script>
<nav:resources override="true"/>
<script type="text/javascript">
//login script

<sec:ifNotLoggedIn>
     $(document).ready(function() {
        //login scripts

            //user name
            $('#username').focus(function(){
                if ($(this).val() == 'User Name') {
                    $(this).val('');
                }
            }).blur(function(){
                var fieldValue = $(this).val();
                if (fieldValue === '' || !fieldValue.match(/\w/)) {
                    $(this).val('User Name');
                }
            });

            if (!$('#username').val().match(/\w/)){
                $('#username').val('User Name');
            }

            //password.  It shows/hides itself and a "password" label field
            $('#password').after('<input type="text" value="Password" id="passwordLabelField" tabindex="2" style="display: none;" />').blur(function(){
                var fieldValue = $(this).val();
                if (fieldValue === '' || !fieldValue.match(/\w/)) {
                    $(this).hide();
                    $('#passwordLabelField').show();
                } 
            });
            $('#passwordLabelField').focus(function(){
                $(this).hide();
                $('#password').show().focus(); 
            });

            if (!$('#password').val().match(/\w/)) {
                $('#password').hide().next().show();
            }

            //login button
            $('#loginButton').button();

            //form submission
            $('form#loginForm').submit(function(e){
                e.preventDefault();
                $('#loginErrorContainer').hide();

                //TODO: validation needs to happen here!

                var dataString = $('#loginForm').serialize(),
                    postURL = $('#loginForm').attr('action');

                $.ajax({
                    type: "POST",
                    url: postURL,
                    data: dataString,
                    dataType: "json",
                    success: function(jsonData, textStatus) {
                        if (jsonData.success) {
                            // data.redirect contains the string URL to redirect to
                            window.location.replace("${defaultTargetURL}");
                        }
                        else {
                            $('#loginErrorMessage').val(jsonData.error).show();
                        }
                    }//success
                });// ajax
            }); //form submit

        });//document ready

</sec:ifNotLoggedIn>
//google analytics
  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-21722315-1']);
  _gaq.push(['_trackPageview']);

  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();


</script>
<!-- End _headResources.gsp -->