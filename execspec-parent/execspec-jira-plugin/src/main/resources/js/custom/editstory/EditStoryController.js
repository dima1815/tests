function EditStoryController(){

    var $this = this;

    this.init = function () {
        console.log("-> EditStoryController.init");
        var model = new EditStoryModel();
        model.init();
        var view = new EditStoryView($this);
        view.init();
        console.log("<- EditStoryController.init");
    }
}

AJS.$(function() {
    var ctr = new EditStoryController()
    ctr.init();
});



