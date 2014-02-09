function StoryService() {

    this.init = function () {

        console.log("initializing StoryService");
    }

    this.createNewStory = function (storyModel) {

        console.log("Saving story via ajax, storyModel =\n" + storyModel);

        var urlString = "http://ideapad:2990/jira/rest/story-res/1.0/story/add";
//        var url = "http://ideapad:2990/jira/rest/story-res/1.0/story/delete/1";
//        url = "http://ideapad:2990/jira/rest/story-res/1.0/story/list-for-issue/" + issueKey;

        var successFunction = new function (data) {
            console.log("Request submitted successfully, receivedData: \n" + data);
        };

        var jsonInput = JSON.stringify(storyModel);

        console.log("sending jsonData = \n" + jsonInput);

        AJS.$.ajax({
            type: "POST",
            url: urlString,
            contentType: "application/json; charset=utf-8",
            success: successFunction,
            data: jsonInput,
            dataType: "json"
        });

    }

    this.findAllForIssue = function (issueKey, callBack) {

        console.log("Finding all stories for issue key = " + issueKey);
        var urlString = "http://ideapad:2990/jira/rest/story-res/1.0/story/find-for-issue/" + issueKey;

        var jqxhr = AJS.$.getJSON(urlString);
        jqxhr.done(callBack);
        jqxhr.fail(function (json) {
            console.log("error occurred during ajax call - " + json);
        });
        jqxhr.always(function () {
            console.log("ajax request completed successfully");
        });
    }


}