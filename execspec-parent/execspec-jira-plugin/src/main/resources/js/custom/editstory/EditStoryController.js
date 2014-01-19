function EditStoryController(){

    console.log("-> EditStoryController.init");

    var $this = this;

    this.hello = function () {
        alert("hello from controller");
    }

    this.init = function () {
        var model = new EditStoryModel();
        model.init();
        var view = new EditStoryView($this);
        view.init();
        view.hello();
        console.log("<- EditStoryController.init");
    }
}

AJS.$(function() {
    var editStoryController = new EditStoryController()
    editStoryController.init();
});



