<head>
    <meta name='layout' content='wellmia-splash'/>
	<title><g:message code='wellmia.security.ui.register.title'/></title>
    <g:javascript library="jquery" plugin="jquery"/>
</head>
<body>
<div id="bd" role="main">
    <div class="yui-g">
        <div id="main">
            <div id="maintop"></div>
            <div id="mainmiddle">
                <div id="registration">
                    <g:if test='${emailSent}'>
                      <br/>
                      <div id="wellmia-welcome" class="title"><g:message code='wellmia.security.ui.register.welcome'/></div>
                      <div class="sub-title"><g:message code='wellmia.security.ui.register.sent'/> to ${email}</div>
                      <div class="sub-title-light"><g:message code='wellmia.security.ui.register.sent.sub'/></div>
                        <g:if test='${grails.util.Environment.getCurrent() == grails.util.Environment.DEVELOPMENT}'>
                            <div class="sub-title">${body}</div>
                        </g:if>
                    </g:if>
                    <g:else>
                        <div class="title">SHARE IN THE HEALTH WISDOM OF CROWDS...</div>
                        <div class="sub-title">Please fill in all of these fields. Thanks!!</div>
                        <g:form action='register' name='registerForm'>
                            <table cellpadding="5" cellspacing="0" border="0">
                                <s2ui:textFieldRegistrationRow   name='email' bean="${command}" value="${command.email}"
                                                                 id="email1" labelCode='user.email.label' labelCodeDefault='E-mail'
                                                                 infoMessage='we will use this to send a confirmation'
                                                                 goodMessage='OK'
                                                                 errorMessage='invalid e-mail address'/>
                                <s2ui:textFieldRegistrationRow name='email2' bean="${command}" value="${command.email2}"
                                                                 id='email2' labelCode='user.email2.label' labelCodeDefault='Confirm E-mail'
                                                                 infoMessage='re-type address above'
                                                                 goodMessage='OK'
                                                                 errorMessage='e-mail does not match'/>
                                <tr>
                                    <td></td>
                                    <td colspan="2">Note:  Your e-mail address will not be displayed on any page within wellmia</td>
                                </tr>
                                <s2ui:textFieldRegistrationRow name='username' labelCode='user.username.label' bean="${command}"
                                                                 id='username1' labelCodeDefault='Username' value="${command.username}"
                                                                  infoMessage='be unique AND anonymous'
                                                                  goodMessage='username is not taken'
                                                                  errorMessage='username already taken'/>
                                <tr>
                                    <td></td>
                                    <td colspan="2">Good: heartsmarts23 ---- Not Good: johnjdoe</td>
                                </tr>
                                </tr class="prop">
                                <s2ui:passwordRegistrationRow name='password' labelCode='user.password.label' bean="${command}"
                                                                     id='password1' labelCodeDefault='Password' value="${command.password}"
                                                                     infoMessage='make it difficult to guess'
                                                                     goodMessage='OK'
                                                                     errorMessage='invalid password'/>
                                </tr class="prop">
                                <s2ui:passwordRegistrationRow name='password2' labelCode='user.password2.label' bean="${command}"
                                                                     id='password2' labelCodeDefault='Confirm Password' value="${command.password2}"
                                                                     infoMessage='renter your password'
                                                                     goodMessage='OK'
                                                                     errorMessage='password does not match'/>
                                <tr>
                                    <td></td>
                                    <td colspan="2">At least 6 characters </td>
                                </tr>
                                <tr>
                                    <th class="title">Gender</th>
                                    <td>
                                        <select id='gender1' name="gender">
                                            <option value="Male">Male</option>
                                            <option value="Female">Female</option>
                                        </select>
                                    </td>
                                </tr>
                                <tr>
                                    <th class="title">Country</th>
                                    <td colspan="2">
                                        <g:countrySelect id="country" name="country" default="usa"/>
                                  </td>
                                </tr>
                                <tr>
                                    <td></td>
                                    <td colspan="2">
                                         <input type="checkbox" name="accept" value="1" checked="checked"/>I have read and agree to the wellmia.com <g:link controller="privacy" target="_blank">Privacy Policy</g:link> and <g:link controller="terms" target="_blank">Terms and Conditions</g:link>.
                                    </td>
                                </tr>
                                <tr>
                                    <td></td>
                                    <td colspan="2">
                                         <input type="checkbox" name="emailOptIn" value="1" checked="checked"/>Please send me updates about wellmia.com services
                                    </td>
                                </tr>
                                <tr>
                                    <td></td>
                                    <td colspan="2">
                                      <button id="captcha_modal_button" type="button" role="button">
                                        Create your account
                                      </button>
                                    </td>
                                </tr>
                          </table>
                          <div id="captcha_dialog">
                              <div id="captcha" class="window">
                                  <div id="captcha_outer">
                                      <div id="captcha_inner">
                                          <h2>
                                                <span>Wellmia is for humans only</span>
                                                <a id="captcha_close" href="#">x</a>
                                          </h2>
                                          <div id="captcha_content">
                                              <p class="instructions">One last step...to make sure that only people get accounts</p>
                                              <label class="sub-title">Type the words above</label>
                                              <recaptcha:ifEnabled>
                                                  <recaptcha:recaptcha theme="clean"/>
                                              </recaptcha:ifEnabled>
                                              <div class="footer-buttons">
                                                  <s2ui:submitButton elementId='create' form='registerForm' messageCode='wellmia.security.ui.register.submit'/>
                                              </div>
                                          </div>
                                      </div>
                                  </div>

                              </div>
                              <div id="mask"></div>
                          </div>
                    </g:form>
                </g:else>

                </div>
            </div>
            <div id="mainbottom"></div>
        </div>
    </div>
</div>

<g:javascript>
$(document).ready(function() {

    $('#email1').focus(function() {
      $(this).addClass("selected");
      if(!$(this).hasClass("validated") && !$(this).hasClass("errors")) {
        $(this).parent().parent().find('div.error').css('display', 'none');
        $(this).parent().parent().find('div.good').css('display', 'none');
        $(this).parent().parent().find('div.info').css('display', 'block');
      }
    }).blur(function() {
      $(this).removeClass("selected");
      if(validateEmail($(this).val())) {
        //Now check to ensure email is not currently being used
        validateEmailUniqueness($(this));
      } else {
        $(this).parent().parent().find('div.error').css('display', 'block').val('you must enter a valid e-mail address');
        $(this).parent().parent().find('div.good').css('display', 'none');
        $(this).parent().parent().find('div.info').css('display', 'none');
      }

      $(this).addClass("validated");
    });

    $('#email2').focus(function() {
      $(this).addClass("selected");
      if(!$(this).hasClass("validated")) {
        $(this).parent().parent().find('div.error').css('display', 'none');
        $(this).parent().parent().find('div.good').css('display', 'none');
        $(this).parent().parent().find('div.info').css('display', 'block');
      }
    }).blur(function() {
      $(this).removeClass("selected");
      $(this).addClass("validated");
      if($(this).val() == $('#email1').val()) {
        $(this).parent().parent().find('div.error').css('display', 'none');
        $(this).parent().parent().find('div.good').css('display', 'block');
        $(this).parent().parent().find('div.info').css('display', 'none');
      } else {
        $(this).parent().parent().find('div.error').css('display', 'block').val("e-mails do not match");
        $(this).parent().parent().find('div.good').css('display', 'none');
        $(this).parent().parent().find('div.info').css('display', 'none');
      }
    });

    $('#username1').focus(function() {
      $(this).addClass("selected");
      if(!$(this).hasClass("validated")) {
        $(this).parent().parent().find('div.error').css('display', 'none');
        $(this).parent().parent().find('div.good').css('display', 'none');
        $(this).parent().parent().find('div.info').css('display', 'block');
      }
    }).keyup(function() {
      if($(this).val().length > 3) {
        if(validateUsernameCharacters($(this)))
            validateUsernameUniqueness($(this));
      }
    }).blur(function() {
      $(this).removeClass("selected");
      if($(this).val().length > 3) {
        if(validateUsernameCharacters($(this)))
            validateUsernameUniqueness($(this));
      } else {
        $(this).parent().parent().find('div.error').val("username too short");
        $(this).parent().parent().find('div.error').css('display', 'block');
        $(this).parent().parent().find('div.good').css('display', 'none');
        $(this).parent().parent().find('div.info').css('display', 'none');
      }
      $(this).addClass("validated");

    });

    $('#password1').focus(function() {
      $(this).addClass("selected");
      if(!$(this).hasClass("validated")) {
        $(this).parent().parent().find('div.error').css('display', 'none');
        $(this).parent().parent().find('div.good').css('display', 'none');
        $(this).parent().parent().find('div.info').css('display', 'block');
      }
    }).blur(function() {
      $(this).removeClass("selected");
      validatePassword($(this));
      $(this).addClass("validated");
    });

      $('#password2').focus(function() {
      $(this).addClass("selected");
      if(!$(this).hasClass("validated")) {
        $(this).parent().parent().find('div.error').css('display', 'none');
        $(this).parent().parent().find('div.good').css('display', 'none');
        $(this).parent().parent().find('div.info').css('display', 'block');
      }
    }).blur(function() {
      $(this).removeClass("selected");
      $(this).addClass("validated");
      if($(this).val() == $('#password1').val()) {
        $(this).parent().parent().find('div.error').css('display', 'none');
        $(this).parent().parent().find('div.good').css('display', 'block');
        $(this).parent().parent().find('div.info').css('display', 'none');
      } else {
        $(this).parent().parent().find('div.error').css('display', 'block').val("passwords do not match");
        $(this).parent().parent().find('div.good').css('display', 'none');
        $(this).parent().parent().find('div.info').css('display', 'none');
      }
    });

    $('#email1').focus();

    var options = {'container': $('#password1').parent().find('div.good')};


    $('#password1').password_strength(options);

    //select the a tag of the captcha modal button
    $('#captcha_modal_button').button({
        icons: {primary: 'ui-icon-triangle-1-e'}
    }).click(function(e) {
        //Cancel the link behavior
        e.preventDefault();
        var id = "#captcha";

        //Get the screen height and width
        var maskHeight = $(document).height();
        var maskWidth = $(window).width();

        //Set height and width of mask to fill up whole screen
        $('#mask').css({'width':maskWidth,'height':maskHeight});

        //transition effect
        $('#mask').fadeIn(1000);
        $('#mask').fadeTo("slow",0.8);

        //get the window height and width
        var winH = $(window).height();
        var winW = $(window).width();

        // Set the popup window to center
        $(id).css('top',  winH/2-$(id).height()/2);
        $(id).css('left', winW/2-$(id).width()/2);

        //transition effect
        $(id).fadeIn(2000);
    });

    //if close button is clicked
    $('#captcha_close').click(function (e) {
        //Cancel the link behavior
        e.preventDefault();
        $('#mask, .window').hide();
    });

    //if mask is clicked
    $('#mask').click(function() {
        $(this).hide();
        $('.window').hide();
    });

});

</g:javascript>

<g:javascript>
  function validateEmail(inputValue) {
    var isValid = 0;
    // Checking if the input field contains text.
    var emailRegexp = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
    if (emailRegexp.test(inputValue))
      isValid = 1;

    return isValid;
  }

  function validateEmailUniqueness(inputValue) {
    var isUnique = false;
    $.getJSON(
      '/secUser/ajaxEmailUniqueSearch',
      {term: $(inputValue).val()},
      function(response) {
        if(response[0].isUnique == "true") {
            $(inputValue).parent().parent().find('div.error').css('display', 'none');
            $(inputValue).parent().parent().find('div.good').css('display', 'block');
            $(inputValue).parent().parent().find('div.info').css('display', 'none');
            $(inputValue).removeClass("errors");
            isUnique = true;
        } else {
            //$(inputValue).parent().parent().find('div.error').val("email already taken");
            //alert($(inputValue).parent().parent().find('div.error').val());
            $(inputValue).parent().parent().find('div.error').css('display', 'block').text("email already taken");
            $(inputValue).parent().parent().find('div.good').css('display', 'none');
            $(inputValue).parent().parent().find('div.info').css('display', 'none');
        }
      }

    );

    return isUnique;
  }

  function validateUsernameUniqueness(inputValue) {
    $.getJSON(
      '/secUser/ajaxUserUniqueSearch',
      {term: $(inputValue).val()},
      function(response) {
        if(response[0].isUnique == "true") {
          $(inputValue).parent().parent().find('div.error').css('display', 'none');
          $(inputValue).parent().parent().find('div.good').css('display', 'block');
          $(inputValue).parent().parent().find('div.info').css('display', 'none');
        } else {
          //$(inputValue).parent().parent().find('div.error').val("username already taken");
          $(inputValue).parent().parent().find('div.error').css('display', 'block').text("username already taken");
          $(inputValue).parent().parent().find('div.good').css('display', 'none');
          $(inputValue).parent().parent().find('div.info').css('display', 'none');
        }
      }

    );
  }

  function validateUsernameCharacters(inputValue) {
    var isValid = false;
    var match = inputValue.val().match(/[;?', ]/g);
    if(!match) {
        $(inputValue).parent().parent().find('div.error').css('display', 'none');
        $(inputValue).parent().parent().find('div.good').css('display', 'block');
        $(inputValue).parent().parent().find('div.info').css('display', 'none');
        isValid = true;
    } else {
        //$(inputValue).parent().parent().find('div.error').val("username already taken");
        $(inputValue).parent().parent().find('div.error').css('display', 'block').text("username contains an invalid character");
        $(inputValue).parent().parent().find('div.good').css('display', 'none');
        $(inputValue).parent().parent().find('div.info').css('display', 'none');
    }
    return isValid;
  }

  function validatePassword(inputValue) {
    var val = inputValue.val();
    // Check to make sure password meets minium requirements
    var isValid = false;
    var totalLength = val.length;
    if(totalLength > 5) {
    /*  var numbers = val.match(/\d/g);
      var numbersLength = (numbers) ? numbers.length : 0;
      if(numbersLength > 0) {
        var letters = val.match(/[a-z]/g) + val.match(/[A-Z]/g);
        var lettersLength = (letters) ? letters.length : 0;
        if((lettersLength) > 0) {
          var specials = val.match(/[!@#$%^&]/g);
          if(specials)  */
            isValid = true;
    //    }
    //  }
    }

    if(isValid) {
      //Assign password strength to "good" div and activate[optional use of css class to change color]
      $(inputValue).parent().parent().find('div.error').css('display', 'none');
      $(inputValue).parent().parent().find('div.info').css('display', 'none');
      var useContainer = $(inputValue).parent().parent().find('div.good');
      //var classes = useContainer.attr('class').split(/\s+/);
      var options = {'container': useContainer};
      $(inputValue).password_strength(options);
      //var newClasses = useContainer.attr('class').split(/\s+/);
      //useContainer.addClass(classes + newClasses);
      $(inputValue).parent().parent().find('div.good').css('display', 'block');
    } else {
      //Display "error" div
      $(inputValue).parent().parent().find('div.error').css('display', 'block');
      $(inputValue).parent().parent().find('div.good').css('display', 'none');
      $(inputValue).parent().parent().find('div.info').css('display', 'none');
    }
  }
</g:javascript>
</body>
