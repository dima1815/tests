AJS.$(function() {

    console.log("Hello story world in console!");
    // alert("Hello story world");

    var dialog = new AJS.Dialog({
        width: 400,
        height: 300,
        id: "example-dialog",
        closeOnOutsideClick: true
    });

    // PAGE 0 (first page)
    // adds header for first page
    dialog.addHeader("Add new story");
    // add panel 1
    dialog.addPanel("Panel 1", "<p>Some content for panel 1.</p>", "panel-body");
    // You can remove padding with:
    // dialog.get("panel:0").setPadding(0);

    // add panel 2 (this will create a menu on the left side for selecting panels within page 0)
    var panel2 = dialog.addPanel("Panel 2", "<p>Some content for panel 2.</p><div style='height: 2000px;'>(forced-height element to demonstrate scrolling content)</div><p>End.</p>", "panel-body");

    dialog.addButton("Next", function (dialog) {
        dialog.nextPage();
    });
    dialog.addLink("Cancel", function (dialog) {
        dialog.hide();
    }, "#");

    // Add events to dialog trigger elements
    AJS.$("#addNewStoryButton").click(function() {
        // PREPARE FOR DISPLAY
        // start first page, first panel
        dialog.gotoPage(0);
        dialog.gotoPanel(0);
        dialog.show();
    });

    console.log("Hello story world in console at END.");
});


