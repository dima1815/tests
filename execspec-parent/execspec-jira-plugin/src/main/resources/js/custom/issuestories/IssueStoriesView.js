function IssueStoriesView(controller) {

    var $this = this;
    var $c = controller;

    this.init = function () {

        console.log("initializing IssueStoriesView");

    }

    this.showStories = function (stories) {

        console.log("showing stories: \n" + stories);

        AJS.$("#stories-container").html(execspec.viewissuepage.viewissuestories.issueStories(stories));

    }
}



