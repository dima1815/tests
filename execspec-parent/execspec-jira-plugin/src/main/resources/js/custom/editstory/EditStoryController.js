function EditStoryController() {

    var $this = this;
    this.model = new EditStoryModel();
    this.view = new EditStoryView($this);

    this.init = function () {
        console.log("-> EditStoryController.init");
        this.model.init();
        this.view.init();
        console.log("<- EditStoryController.init");
    }

    this.saveStoryInput = function () {
        console.log("-> saveStoryInput ");
        var narrative = this.view.getNarrative();
        console.log("Saving narrative - " + narrative);
    }
}

AJS.$(function () {
    var ctr = new EditStoryController()
    ctr.init();
});



