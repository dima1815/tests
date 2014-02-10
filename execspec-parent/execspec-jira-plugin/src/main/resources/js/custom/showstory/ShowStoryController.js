function ShowStoryController() {

    var $this = this;
    var $view = new ShowStoryView($this);
    this.storyService = new StoryService();
    this.utils = new PageUtils();

    this.init = function () {
        console.log("-> ShowStoryController.init");
        $view.init();

        var issueKey = this.utils.getIssueKey();
        console.log("issueKey == " + issueKey);

        var successFunction = function (story) {
            console.log("Request submitted successfully, receivedData: \n" + story);
            $view.showStory(story);
        };

        this.storyService.findForIssue(issueKey, successFunction);

        console.log("<- ShowStoryController.init");
    }
}

AJS.$(function () {
    var ctr = new ShowStoryController()
    ctr.init();
})



