AJS.$(function() {

    console.log("Hello story world in console!");
    // alert("Hello story world");

    var dialog = new AJS.Dialog({
        width: 960,
        height: 500,
        id: "add-story-dialog",
        closeOnOutsideClick: true
    });


    // PAGE 0 (first page)
    // adds header for first page
    dialog.addHeader("Add New Story");
    // add panel 1
    dialog.addPanel("Narrative: ", "<div id='narrativePanel'>Some content for panel 1.</div>", "panel-body");
    AJS.$('#narrativePanel').html(execspec.view.addStoryForm.narrative());

    dialog.addPanel("Scenario: 1", "<div id='scenarioOnePanel'>Some content for panel 1.</div>", "panel-body");
    // You can remove padding with:
    // dialog.get("panel:0").setPadding(0);
    AJS.$('#scenarioOnePanel').html(execspec.view.addStoryForm.render());

    // add panel 2 (this will create a menu on the left side for selecting panels within page 0)
    var panel2 = dialog.addPanel("Scenario: 2", "<p>Some content for panel 2.</p><div style='height: 2000px;'>(forced-height element to demonstrate scrolling content)</div><p>End.</p>", "panel-body");

    var panel3 = dialog.addPanel("Scenario: 3", "<p>Some content for panel 2.</p><div style='height: 2000px;'>(forced-height element to demonstrate scrolling content)</div><p>End.</p>", "panel-body");

    dialog.addButton("Edit", function (dialog) {
        alert("Edit clicked");
    }, "aui-button aui-button-subtle");

    dialog.addButton("Save", function (dialog) {
        dialog.hide();
    });
    dialog.addLink("Cancel", function (dialog) {
        dialog.hide();
    }, "#");


    // Add events to dialog trigger elements
    AJS.$("a.add-new-story-button").click(function() {

        // PREPARE FOR DISPLAY
        // start first page, first panel
        dialog.gotoPage(0);
        dialog.gotoPanel(0);
        dialog.show();
        return false;
    });

    console.log("Hello story world in console at END.");
});


