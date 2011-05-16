/**
 * Created by IntelliJ IDEA.
 * User: mike
 * Date: 2/21/11
 * Time: 7:00 AM
 * To change this template use File | Settings | File Templates.
 */

$(function(){

    var el = $("#selected_categories"),
             categoryPicker = $("#interestTags"),
             submitButton = $("#healthInterestUpdateSubmit"),
             emptyTag = $("#empty_block");

    $("#addCategories").click(function(){
        var categories = split(categoryPicker.val());
        for (var i = 0; i < (categories.length - 1); i++) {
           item = $('<input/>')
                   .attr("name","interests")
                   .attr("type","checkbox")
                   .attr("value",categories[i])
                   .attr("checked", true);
           el.append(item);
           el.append(" " + categories[i]);
           el.append($('<br>'));
        }
        categoryPicker.val("");
        submitButton.show();
        emptyTag.hide();
    });

    //$("#category_multiselect").multiselect({
    //    header: "View / Edit Selected Interest Categories",
    //    autoOpen: "true"
    //});

    $( ".categoryPicker" )
        // don't navigate away from the field on tab when selecting an item
        .bind( "keydown", function( event ) {
            if ( event.keyCode === $.ui.keyCode.TAB &&
                    $( this ).data( "autocomplete" ).menu.active ) {
                event.preventDefault();
            }
        })
        .autocomplete({
            minLength: 3,
            source: function( request, response ) {
                // extract the last term
                var lastTerm = extractLast( request.term );
                var matchingTags = [];
                $.ajax({
                    type: "POST",
                    url: "/question/ajaxQuestionTagAutocomplete",
                    data: { "term" : lastTerm},
                    dataType: "json",
                    success: function(jsonData, textStatus) {
                        //Process json data for matching tags
                        response(jsonData);
                        //$.each(jsonData, function(i, item){
                        //    var entry = [item.label, item.value];
                        //    matchingTags.push(entry);
                        //});
                    }
                });
            },
            focus: function() {
                // prevent value inserted on focus
                return false;
            },
            select: function( event, ui ) {
                var terms = split( this.value );
                // remove the current input
                terms.pop();
                // add the selected item
                terms.push( ui.item.value );
                // add placeholder to get the comma-and-space at the end
                terms.push( "" );
                this.value = terms.join( ", " );
                return false;
            }
        });

    //select the a tag of the captcha modal button
    $('#healthInterestUpdateSubmit').button({
        icons: {primary: 'ui-icon-triangle-1-e'}
    });

    $('#addCategories').button({
        icons: {primary: 'ui-icon-tag'}
    });

    function split( val ) {
        return val.split( /,\s*/ );
    }

    function extractLast( term ) {
        return split( term ).pop();
    }


    //Below here for newsFeed Page



    $(".addComment textarea").focus(function () {
      $(this).parent().parent().parent().parent().removeClass("addCommentCollapsed");
      $(this).addClass("addCommentExpanded");
      if($(this).val() == $(this).attr('defaulttext'))
        $(this).val("");
    });

    $(".addComment textarea").blur(function () {
      if($(this).val() == "") {
        $(this).val($(this).attr('defaulttext'));
        $(this).parent().parent().parent().parent().addClass("addCommentCollapsed");
        $(this).removeClass("addCommentExpanded");
      }
    });

    $('.feedItemCommentSummary .responseLink').click(function (e) {
      e.preventDefault();
      $(this).parent().parent().parent().find(".addComment").show();
    });

    $('#askQuestionButton').button(function (){
      icons: {primary: 'ui-icon-pencil'}
    });

    $('#submitStatusButton').button(function (){
      icons: {primary: 'ui-icon-pencil'}
    });

    $('.submitCommentButton').button(function (){
      icons: {primary: 'ui-icon-pencil'}
    });

    $("#postQuestion #title").focus(function () {
        if($(this).val() == $(this).attr("default")) {
            $(this).val("");
        }
    });

    $("#postQuestionAction").click(function (e){
       e.preventDefault();
       $("#postQuestion").show();
       $("#postStatus").hide();
    });

    $('#postStatusAction').click(function (e){
       e.preventDefault();
       $("#postStatus").show();
       $("#postQuestion").hide();
    });

    $("#questionSubject").focus(function (){
        if($( this ).val() == $( this ).attr("default")) {
            $( this ).val("");
            $( this ).css({'color':'#222222'});
        }
    }).blur(function () {
        if($( this ).val() == "") {
            $( this ).css({'color':'#DBDBDB'});
            $( this ).val($( this ).attr("default"))
        }
    });


    $("#questionContent").focus(function (){
        if($( this ).val() == $( this ).attr("default")) {
            $( this ).val("");
            $( this ).css({'color':'#222222'});
        }
    }).blur(function () {
        if($( this ).val() == "") {
            $( this ).css({'color':'#DBDBDB'});
            $( this ).val($( this ).attr("default"))
        }
    });

    $( "#questionTags" )
        // don't navigate away from the field on tab when selecting an item
        .bind( "keydown", function( event ) {
            if ( event.keyCode === $.ui.keyCode.TAB &&
                    $( this ).data( "autocomplete" ).menu.active ) {
                event.preventDefault();
            }
        })
        .autocomplete({
            minLength: 3,
            source: function( request, response ) {
                // extract the last term
                var lastTerm = extractLast( request.term );
                var matchingTags = [];
                $.ajax({
                    type: "POST",
                    url: "/question/ajaxQuestionTagAutocomplete",
                    data: { "term" : lastTerm},
                    dataType: "json",
                    success: function(jsonData, textStatus) {
                        //Process json data for matching tags
                        response(jsonData);
                        //$.each(jsonData, function(i, item){
                        //    var entry = [item.label, item.value];
                        //    matchingTags.push(entry);
                        //});
                    }
                });
            },
            focus: function() {
                // prevent value inserted on focus
                return false;
            },
            select: function( event, ui ) {
                var terms = split( this.value );
                // remove the current input
                terms.pop();
                // add the selected item
                terms.push( ui.item.value );
                // add placeholder to get the comma-and-space at the end
                terms.push( "" );
                this.value = terms.join( ", " );
                return false;
            }
        }).focus(function (){
            if($( this ).val() == $( this ).attr("default")) {
                $( this ).val("");
                $( this ).css({'color':'#222222'});
            }
        }).blur(function () {
            if($( this ).val() == "") {
                $( this ).css({'color':'#DBDBDB'});
                $( this ).val($( this ).attr("default"))
            }
        });

        //Below here for newsItem/question showDetails Page
        $('.follow_button').button().click(function () {
           //Call get feedItemId
            var feedItemId = $(this).parent().find("#itemId").val();
            var bShouldFollow = $(this).attr('bShouldFollow');
            var followButton = $(this);
           //Call ajax function on ConsumerProfile
            $.ajax({
                type: "POST",
                url: "/consumerProfile/followFeedItemAjax",
                data: { "feedItemId" : feedItemId, "bShouldFollow" : bShouldFollow},
                dataType: "json",
                success: function(jsonData, textStatus) {
                    //Change Button Color & Text & next toggle value
                    if(bShouldFollow == "false") {
                        $(followButton).attr('bShouldFollow','true');
                        $(followButton).removeClass("followed");
                        $(followButton).addClass("not_followed");
                        $(followButton).button( "option", "label", "Follow This" );
                    } else {
                        $(followButton).attr('bShouldFollow','false');
                        $(followButton).removeClass("followed");
                        $(followButton).addClass("not_followed");
                        $(followButton).button( "option", "label", "Followed" );
                    }
                }
            });
        });

});


