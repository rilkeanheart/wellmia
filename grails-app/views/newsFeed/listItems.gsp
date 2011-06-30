<%--
  Created by IntelliJ IDEA.
  User: mike
  Date: 12/26/10
  Time: 5:34 AM
  To change this template use File | Settings | File Templates.
--%>

<%--<%@ page contentType="text/html;charset=UTF-8" %>  --%>
<html>
    <head>
        <title>Wellmia News:  </title>
        <meta name="layout" content="wellmia" />
        <%--<g:javascript library="jquery" plugin="jquery"/>--%>
    </head>
    <body>
        
                    <div id="postContent" style="display: none;">
                        <div class="topimage">
                            <input type="image" id="postQuestionAction" class="postContentActionImage" src="${resource(dir:'images',file:'question1.png')}" alt="question"/>
                            <g:if test="${false}">
                                <input type="image" id="postStatusAction" class="postContentActionImage" src="${resource(dir:'images',file:'quickthoughts.png')}" alt="status"/>
                            </g:if>
                        </div>
                        <div id="postQuestion" class="postContentInputArea">
                            <g:form>
                                <g:textField id="questionSubject" class="textField subjectField" name="subject" default="Give your question a headline e.g., Diabetics and desserts" value="Give your question a headline e.g., Diabetics and desserts"/>
                                <textarea id="questionContent" class="textEditor" name="content" default="What exactly would you like to know? e.g., How do other diabetics handle dessert time?  I take Metformin.  What tastes good to you that doesn't spike your blood sugar levels?">What
                                    exactly would you like to know? e.g., How do other diabetics handle dessert time? I
                                    take Metformin. What tastes good to you that doesn't spike your blood sugar
                                    levels?</textarea>
                                <textarea id="questionTags" class="textEditor" name="tags" default="add tags (categories) for your question...e.g., Diabetes Type II, Metformin">add
                                    tags (categories) for your question...e.g., Diabetes Type II, Metformin</textarea>
                                <g:submitToRemote value="Ask" url="[controller: 'question', action: 'saveQuestionAjax']" update="" onSuccess="postQuestionOK(data,textStatus)" onFailure="postQuestionFailure(XMLHttpRequest,textStatus,errorThrown)" onLoading="showSpinner(true, 'postQuestionSpinner')" onComplete="showSpinner(false, 'postQuestionSpinner')" id="askQuestionButton"/>

                            </g:form>
                            <img id="postQuestionSpinner" class="spinner" style="display: none" src="<g:createLinkTo dir='/images' file='spinner.gif' alt=''/>"/>
                            <br class="clear"/>
                        </div>
                        <!--<div id="postStatus" class="postContentInputArea">
                           <g:form>
                               <textarea class="textEditor" name="content">what is happening with your health?</textarea>
                               <g:submitToRemote value="Send"
                                                 url="[controller: 'post', action: 'addQuestionAjax']"
                                                 update=""
                                                 onSuccess=""
                                                 onLoading=""
                                                 onComplete=""
                                                 id = "submitStatusButton"/>
                           </g:form>
                           <br class="clear"/>
                        </div>-->
                    </div>
                    <h1>Top Items</h1>
                    <div id="pagelet_news_stream_dep" class="news_list_container_dep">
                        <g:each var="feedItem" in="${feedList}">
                            <g:if test="${com.wellmia.NewsItem.class.isInstance(feedItem)}">
                                <g:render template="/newsItem/newsItemSummary" model="[newsItem : feedItem]"/>
                            </g:if>
                            <g:if test="${com.wellmia.Question.class.isInstance(feedItem)}">
                                <g:render template="/question/questionSummary" model="[question : feedItem]"/>
                            </g:if>
                        </g:each>
                    </div><!--/#pagelet_news_stream_dep-->
               
    <%-- <g:javascript >
        function clearComment(e) {
            $("#"+e).val('');
        }

        function showSpinner(visible, e) {
        if(visible == true)
            $("#"+e).show();
        else
            $("#"+e).hide();
        }

        function postQuestionOK(data,textStatus) {
            $('#pagelet_news_stream_dep').prepend(data);
        }

        function postQuestionFailure(XMLHttpRequest,textStatus,errorThrown) {
            //TODO:  all error handling here
        }

        function split( val ) {
          return val.split( /,\s*/ );
        }

        function extractLast( term ) {
          return split( term ).pop();
    }
    </g:javascript> --%>
    </body>
</html>
