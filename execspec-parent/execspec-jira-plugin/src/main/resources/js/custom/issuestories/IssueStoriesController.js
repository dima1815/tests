function IssueStoriesController() {

    var $this = this;
    var $view = new IssueStoriesView($this);
    this.storyService = new StoryService();
    this.utils = new PageUtils();

    this.init = function () {
        console.log("-> IssueStoriesController.init");
        $view.init();

        var issueKey = this.utils.getIssueKey();
        console.log("issueKey == " + issueKey);

        var successFunction = function (stories) {
            console.log("Request submitted successfully, receivedData: \n" + stories);
            $view.showStories(stories);
        };

        this.storyService.findAllForIssue(issueKey, successFunction);

        console.log("<- IssueStoriesController.init");
    }
}

AJS.$(function () {
    var ctr = new IssueStoriesController()
    ctr.init();
})



