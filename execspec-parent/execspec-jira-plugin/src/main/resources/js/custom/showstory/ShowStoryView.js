function ShowStoryView(controller) {

    var $this = this;
    var $c = controller;

    this.init = function () {
        console.log("initializing ShowStoryView");
    }

    this.showStory = function (story) {

        console.log("showing story: \n" + JSON.stringify(story));

        AJS.$("#story-container").html(execspec.viewissuepage.showstory.renderStory(story));

    }
}



