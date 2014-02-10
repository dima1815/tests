function StoryService() {

    var baseUrl = "http://ideapad:2990/jira/rest/story-res/1.0/story";

    this.init = function () {

        console.log("initializing StoryService");
    }

    this.createNewStory = function (storyModel) {

        console.log("Saving story via ajax, storyModel =\n" + storyModel);

        var urlString = baseUrl + "/add";

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

    this.findForIssue = function (issueKey, callBack) {

        console.log("Finding story for issue key = " + issueKey);
        var urlString = baseUrl + "/find-for-issue/" + issueKey;

        var jqxhr = AJS.$.getJSON(urlString);
        jqxhr.done(callBack);
        jqxhr.fail(function (json) {
            console.log("error occurred during ajax call - " + json);
        });
        jqxhr.always(function () {
            console.log("ajax request completed successfully");
        });
    }

    this.deleteStory = function (storyId) {

        console.log("calling delete story with id - " + storyId);

        var urlString = baseUrl + "/delete/" + storyId;

        var callBack = function () {
            console.log("Story with id - " + storyId + " deleted successfully");
        };

        AJS.$.ajax({
            type: "DELETE",
            url: urlString,
            success: callBack
        });

    }


}