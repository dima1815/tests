#editStoryButtons {
    margin-top: 20px;
    margin-left: 0px;
}

.story-description .rich-story-editor-field {
    border: none;
    color: forestgreen;
    width: 100%;
    resize: none;
}

.narrative .rich-story-editor-field {
    border: none;
    color: #00F;
    width: 100%;
    font-family: "Lucida Grande", "Arial", "Helvetica", "Verdana", sans-serif;
    font-size: 1em;
    white-space: nowrap;
}

.rich-story-editor-field {
    /*overflow: auto;*/
    overflow: hidden;
    /*overflow-y: hidden;*/
    /*overflow-x: auto;*/
    resize: none;
    /*display:inline;*/
    /*float:left;*/
}

.rich-story-editor-field.focused {
    border: solid 1px #00cc00;
}

/*#editStoryButtons {*/
/*max-height: 10px;*/
/*}*/

#richStoryEditContent {
    margin-top: 20px;
    margin-left: 20px;
}

.insert-element-div {
    position: relative;
    margin-top: 0px;
    margin-bottom: 0px;
    /*border: 1px solid #008000;*/

}

.insert-element-trigger-div {
    position: absolute;
    top: 0px;
    left: -23px;
    width: 100%;
    /*border: 1px solid #006699;*/
}

.insert-element-link {
    /*position: absolute;*/
    /*top: 0px;*/
    /*left: 0px;*/
    margin-left: 3px;
}

.insert-element-link {
    color: #00cc00;
}

/*.aui-button.aui-button-link.insert-element-link, .aui-button.aui-button-link.insert-element-link:visited {*/
/*color: #ff0000;*/
/*}*/

.insert-element-link:focus, .insert-element-link:active .insert-element-link:visited {
    color: #00cc00;
}

.story-element-container {
    position: relative;
    /*border: solid 1px #815b3a;*/
}

.element-operations-container {
    position: absolute;
    top: 0px;
    left: 0px;
    border: solid 1px yellow;
}

.element-content-container {
    margin-left: 23px;
}

.remove-element-link {
    color: red;
}

.remove-element-link:focus, .remove-element-link:active .remove-element-link:visited {
    color: red;
}

===

var editButtonHandler;

function StoryEditHandler() {

    editButtonHandler = this;

    this.debugOn = true;

    this.debug = function (msg) {
        if (this.debugOn) {
            console.log("[DEBUG StoryEditHandler] " + msg);
        }
    }

    this.init = function () {
        this.debug("initialized");
    }


    this.assignRichEditorHandlers = function (story) {

        this.debug("> assignRichEditorHandlers");

        this.addInsertLinks(story);

        this.assignInsertLinkHandlers(story);

        this.assignShowElementOperationsOnHover(story);

        this.assignAutoHeightForTextAreas(story);


        this.debug("# assignRichEditorHandlers");
    }

    this.assignAutoHeightForTextAreas = function (story) {

        // set height dynamically on key press
        AJS.$('.rich-story-editor-field').keydown(function (event) {
            editButtonHandler.debug('keydown, event.target - ' + event.target);
            var key = event.which;
            editButtonHandler.debug('keydown, event.which - ' + key);
            if (key == 13) {
                editButtonHandler.debug('return pressed, resizing text area');
                editButtonHandler.resizeTextArea(event);
            }
        });

        AJS.$('.rich-story-editor-field').keyup(function (event) {
            editButtonHandler.resizeTextArea(event);
        });

        this.resizeTextArea = function (event) {
            var textArea = event.target;

            editButtonHandler.debug('keypressed, event.target - ' + textArea);
            var value = AJS.$(event.target).val();
            editButtonHandler.debug('value - ' + value)
            var lines = value.split(/\r*\n/);
            editButtonHandler.debug('lines - ' + lines)
            var lineCount = lines.length;
            editButtonHandler.debug('lineCount - ' + lineCount);
            var currentRows = AJS.$(event.target).attr("rows");
            editButtonHandler.debug('currentRows - ' + currentRows);
            if (Number(currentRows) != lineCount) {
                AJS.$(event.target).attr("rows", lineCount);
            }
        }

        // add border on focus
        AJS.$('.rich-story-editor-field').focus(function (event) {
            editButtonHandler.debug('focused, event.target - ' + event.target);
            AJS.$(event.target).addClass("focused");

        });
        AJS.$('.rich-story-editor-field').blur(function (event) {
            editButtonHandler.debug('blurred, event.target - ' + event.target);
            AJS.$(event.target).removeClass("focused");
        });

    }

    this.assignInsertLinkHandlers = function (story) {

        AJS.$(".insert-element-trigger-div").mouseenter(function (event) {
            var div = event.target;
            editButtonHandler.debug("mouse enter on - divBeforeNarrative");
            editButtonHandler.debug("hiding all insert links");
            AJS.$(".insert-element-link").hide();
            editButtonHandler.debug("div - " + div);
            AJS.$(div).children(".insert-element-link").show();
        });

        AJS.$(".insert-element-trigger-div").mouseleave(function (event) {

            var div = event.target;
            editButtonHandler.debug("> mouse leave on - divBeforeNarrative");
            editButtonHandler.debug("div - " + div);

//            var isPressed = AJS.$(".insert-element-link").attr("pressed");
            var insertLinkElement = AJS.$(div).children(".insert-element-link");
            var isPressed = insertLinkElement.attr("pressed");
            if (isPressed == "true") {
                editButtonHandler.debug("Not hiding the insert button as is currently pressed");
            } else {
                editButtonHandler.debug("Hiding the insert button as pressed is - " + isPressed);
                insertLinkElement.hide();
            }

            editButtonHandler.debug("# mouse leave on - divBeforeNarrative");
        });

        AJS.$(".insert-dropdown-content").on({

            "aui-dropdown2-show": function (event) {
                var target = event.target;
                editButtonHandler.debug("showing dropdown, event.target.id - " + AJS.$(target).attr("id"));
                var triggerDivId = AJS.$(target).attr("trigger-div-id");
                editButtonHandler.debug("event.target.attr(\"trigger-div-id\") - " + triggerDivId);

                var triggerDiv = AJS.$("#" + triggerDivId);
                var insertLink = triggerDiv.children(".insert-element-link");
                insertLink.attr("pressed", "true");
            },

            "aui-dropdown2-hide": function (event) {

                var target = event.target;
                editButtonHandler.debug("hiding dropdown, event.target.id - " + AJS.$(target).attr("id"));

                var triggerDivId = AJS.$(target).attr("trigger-div-id");
                editButtonHandler.debug("event.target.attr(\"trigger-div-id\") - " + triggerDivId);
                var triggerDiv = AJS.$("#" + triggerDivId);
                var insertLink = triggerDiv.children(".insert-element-link");

                insertLink.attr("pressed", "false");

                var isHovered = triggerDiv.is(":hover");
                if (isHovered) {
                    editButtonHandler.debug("NOT hiding the insert link");
                } else {
                    editButtonHandler.debug("hiding the insert link");
                    insertLink.hide();
                }
            }

        });

    }

    this.assignShowElementOperationsOnHover = function (story) {

        // description
        {
            AJS.$(".story-element-container").mouseenter(function (event) {
                editButtonHandler.debug("> mouse enter on - story-element-container");
                editButtonHandler.debug("event.target - " + event.target);

//                editButtonHandler.debug("hiding all insert links");
//                AJS.$(".insert-element-link").hide();

                var target = event.target;
                if (AJS.$(target).hasClass("story-element-container")){
                    AJS.$(target).children(".element-operations-container").show();
                } else {
                    // this is the case of hover over text area input field
                    AJS.$(target)
                        .closest(".story-element-container")
                        .children(".element-operations-container")
                        .show();
                }

                editButtonHandler.debug("# mouse enter on - story-element-container");
            });

            AJS.$(".story-element-container").mouseleave(function (event) {

                editButtonHandler.debug("> mouse leave on - story-element-container");
                editButtonHandler.debug("event.target - " + event.target);

                var target = event.target;
                if (AJS.$(target).hasClass("story-element-container")){
                    AJS.$(target).children(".element-operations-container").hide();
                } else {
                    // this is the case of hover over text area input field
                    AJS.$(target)
                        .closest(".story-element-container")
                        .children(".element-operations-container")
                        .hide();
                }

                editButtonHandler.debug("# mouse leave on - story-element-container");
            });
        }

    }

    this.addInsertLinks = function (story) {

        // before Meta
        {
            var templateObj = new Object();
            templateObj.triggerDivId = "insertTriggerDivBeforeMeta";
            templateObj.dropdownDivId = "insertDropdownDivBeforeMeta";

            var insertDescriptionLinkInfo = new Object();
            insertDescriptionLinkInfo.text = "Description";
            insertDescriptionLinkInfo.onClickFunction = "insertDescription";

            templateObj.dropdownItems = [];
            if (story.description == null && story.meta != null) {
                templateObj.dropdownItems.push(insertDescriptionLinkInfo);
            }

            if (templateObj.dropdownItems.length > 0) {
                var insertBeforeNarrativeHtml = execspec.viewissuepage.editstory.rich.renderInsertLinkDiv(templateObj);
                AJS.$('#insertLinkContainerBeforeMeta').html(insertBeforeNarrativeHtml);
            } else {
                AJS.$('#insertLinkContainerBeforeMeta').html("");
            }
        }

        // before Narrative
        {
            var templateObj = new Object();
            templateObj.triggerDivId = "insertBeforeNarrativeTriggerDiv";
            templateObj.dropdownDivId = "insertBeforeNarrativeDropdownDiv";

            var insertDescriptionLinkInfo = new Object();
            insertDescriptionLinkInfo.text = "Description";
            insertDescriptionLinkInfo.onClickFunction = "insertDescription";
            var insertMetaLinkInfo = new Object();
            insertMetaLinkInfo.text = "Meta";
            insertMetaLinkInfo.onClickFunction = "insertMeta";

            templateObj.dropdownItems = [];
            if (story.description == null && story.meta == null) {
                templateObj.dropdownItems.push(insertDescriptionLinkInfo);
                templateObj.dropdownItems.push(insertMetaLinkInfo);
            } else if (story.meta == null) {
                templateObj.dropdownItems.push(insertMetaLinkInfo);
            }

            if (templateObj.dropdownItems.length > 0) {
                var insertBeforeNarrativeHtml = execspec.viewissuepage.editstory.rich.renderInsertLinkDiv(templateObj);
                AJS.$('#insertLinkContainerBeforeNarrative').html(insertBeforeNarrativeHtml);
            } else {
                AJS.$('#insertLinkContainerBeforeNarrative').html("");
            }
        }

    }

    this.richTextEditorClicked = function (event) {

        this.debug("> richTextEditorClicked");

        var richEditorButtons = execspec.viewissuepage.editstory.rich.renderRichEditorOperationButtons();
        AJS.$("#editorOperationsButtons").html(richEditorButtons);

        AJS.$("#richTextEditorButton").attr("aria-pressed", "true");
        AJS.$("#rawTextEditorButton").attr("aria-pressed", "false");

        AJS.$("#rawEditStoryContainer").hide();
        AJS.$("#richEditStoryContainer").show();

        event.preventDefault();
        this.debug("# richTextEditorClicked");
    }

    this.rawTextEditorClicked = function (event) {

        this.debug("> rawTextEditorClicked");

        var rawEditorButtons = execspec.viewissuepage.editstory.renderRawEditorOperationButtons();
        AJS.$("#editorOperationsButtons").html(rawEditorButtons);

        AJS.$("#richTextEditorButton").attr("aria-pressed", "false");
        AJS.$("#rawTextEditorButton").attr("aria-pressed", "true");

        AJS.$("#richEditStoryContainer").hide();
        AJS.$("#rawEditStoryContainer").show();

        event.preventDefault();
        this.debug("# rawTextEditorClicked");
    }

    this.insertDescription = function (event) {

        this.debug("> insertDescription");

        storyController.currentStory.description = "";

        var descriptionHtml = execspec.viewissuepage.editstory.rich.renderStoryDescriptionField(storyController.currentStory);
        AJS.$("#storyDescriptionContainer").html(descriptionHtml);

        this.assignRichEditorHandlers(storyController.currentStory);
        this.assignShowElementOperationsOnHover(storyController.currentStory);

        event.preventDefault();
        this.debug("# insertDescription");
    }

    this.removeElement = function (event, elementName) {

        this.debug("> removeElement");
        this.debug("elementName - " + elementName);

        if (elementName == "description") {
            this.removeDescription(event);
        }

        this.debug("# removeElement");
    }

    this.removeDescription = function (event) {

        this.debug("> removeDescription");

        storyController.currentStory.description = null;
        AJS.$("#storyDescriptionContainer").html("");
        this.assignRichEditorHandlers(storyController.currentStory);
        this.assignShowElementOperationsOnHover(storyController.currentStory);

        event.preventDefault();
        this.debug("# removeDescription");
    }

    this.insertMeta = function (event) {

        this.debug("> insertMeta");

        var meta = new Object();
        meta.keyword = "Meta:";
        storyController.currentStory.meta = meta;

        var metaHtml = execspec.viewissuepage.editstory.rich.renderStoryMetaField(storyController.currentStory);
        AJS.$("#storyMetaContainer").html(metaHtml);

        this.assignRichEditorHandlers(storyController.currentStory);

        event.preventDefault();
        this.debug("# insertMeta");
    }

    this.insertGivenStories = function (event) {

        this.debug("> insertGivenStories");

        var givenStories = new Object();
        givenStories.keyword = "GivenStories:";
        storyController.currentStory.givenStories = givenStories;

        storyView.editStory(storyController.currentStory);

        event.preventDefault();
        this.debug("# insertGivenStories");
    }

    this.insertLifecycle = function (event) {

        this.debug("> insertLifecycle");

        var lifecycle = new Object();
        lifecycle.keyword = "Lifecycle:";
        storyController.currentStory.lifecycle = lifecycle;

        storyView.editStory(storyController.currentStory);

        event.preventDefault();
        this.debug("# insertLifecycle");
    }

    this.insertScenario = function (event) {

        this.debug("> insertScenario");

        var scenario = new Object();
        scenario.keyword = "Scenario:";
        if (storyController.currentStory.scenarios == null) {
            storyController.currentStory.scenarios = [];
        }
        storyController.currentStory.scenarios.push(scenario);

        storyView.editStory(storyController.currentStory);

        event.preventDefault();
        this.debug("# insertScenario");
    }

}

===

{namespace execspec.viewissuepage.editstory.rich}

/**
 * Renders story edit buttons.
 */
{template .renderRichEditorOperationButtons}
//    <div class="aui-toolbar2-inner">
//        <div class="aui-toolbar2-primary">
//            <div id="editorTypeButtons" class="aui-buttons">
//                <button id="richTextEditorButton" class="aui-button"
//                    onclick="editButtonHandler.richTextEditorClicked(event)">
//                    <b>Rich</b>
//                </button>
//                <button id="rawTextEditorButton" class="aui-button"
//                    onclick="editButtonHandler.rawTextEditorClicked(event)">
//                    <b>Text</b>
//                </button>
//            </div>
//            <div id="editOperationsButtons" class="aui-buttons">
//                <button id="insertTableButton" class="aui-button">
//                    <span class="aui-icon aui-icon-small aui-iconfont-appswitcher">Table Parameter</span>
//                </button>
//                <button id="insertTableButton" class="aui-button">
//                    <span class="aui-icon aui-icon-small aui-iconfont-arrows-left">Table Parameter</span>
//                </button>

//            </div>
//        </div>
//    </div>
{/template}

/**
 * Renders the '+' insert icon link to add story elements.
 * @param triggerDivId
 * @param dropdownDivId
 * @param dropdownItems
 */
{template .renderInsertLinkDiv}
    {let $addIconClass: 'aui-icon aui-icon-small aui-iconfont-add' /}
    <div class="insert-element-div">&nbsp;
        <div id="{$triggerDivId}" class="insert-element-trigger-div">
            <a aria-controls="dropdown2-more" href="#{$dropdownDivId}" aria-owns="{$dropdownDivId}"
                aria-haspopup="true"
                class="aui-dropdown2-trigger aui-style-default aui-dropdown2-trigger-arrowless insert-element-link"
                style="display: none"
                pressed="false">
                <span class="{$addIconClass} insert-element-icon"></span>
            </a>&nbsp;
        </div>
        <div id="{$dropdownDivId}" trigger-div-id="{$triggerDivId}"
            class="aui-dropdown2 aui-style-default insert-dropdown-content"
            aria-hidden="true" data-dropdown2-alignment="left"
            style="left: 280.6px; top: 801.3px; display: none;">
            <ul class="aui-list-truncate">
                {foreach $item in $dropdownItems}
                    <li><a href="#"
                        onclick="editButtonHandler.{$item.onClickFunction}(event)">{$item.text}</a></li>
                {/foreach}
            </ul>
        </div>
    </div>
{/template}

/**
 * Renders the text area type editable field.
 * @param fieldName
 * @param fieldValue
 */
{template .renderMultiLineField}
    <textarea class="textarea rich-story-editor-field"
        name="comment"
        rows="1"
        wrap="off"
        placeholder="enter '{$fieldName}' here">
        {if $fieldValue != null}
            {$fieldValue}
        {/if}
    </textarea>
{/template}

/**
 * Renders the text field type editable field.
 * @param fieldName
 * @param fieldValue
 */
{template .renderSingleLineField}
    {if $fieldValue != null}
        <input style="width: 100%;"
            class="text long-field rich-story-editor-field"
            name="{$fieldName}"
            title="{$fieldName}" type="text"
            value="{$fieldValue}"/>
    {else}
        <input style="width: 100%;"
            class="text long-field rich-story-editor-field"
            name="{$fieldName}"
            title="{$fieldName}" type="text"
            placeholder="enter '{$fieldName}' here"/>
    {/if}
{/template}

/**
 * Renders story description input field.
 * @param description
 */
{template .renderStoryDescriptionField}
        <div class="element-operations-container"
            style="display: none">
            <a class="remove-element-link"
                  href="#"
                  onclick="editButtonHandler.removeElement(event, 'description')"
                >
                <span class="aui-icon aui-icon-small aui-iconfont-close-dialog"></span>
            </a>
        </div>
        <div class="element-content-container story-description">
            {call .renderMultiLineField}
                {param fieldName: 'Description' /}
                {param fieldValue: $description /}
            {/call}
        </div>
{/template}

/**
 * Renders story meta input field.
 * @param meta
 */
{template .renderStoryMetaField}
    <div class="story-meta">
        <div class="story-meta-keyword">
            {$meta.keyword}
        </div>
    </div>
{/template}

/**
 * Renders story.
 * @param story
 * @param insertGivenStoriesLinkInfo
 * @param insertLifecycleLinkInfo
 * @param insertScenarioLinkInfo
 */
{template .renderRichEditStoryContent}
<div id="richStoryEditContent">
    {let $clickHere: '[click here to edit]' /}

    <div id="storyDescriptionContainer" class="story-element-container">
        {if $story.description != null}
            {call .renderStoryDescriptionField}
                {param description: $story.description /}
            {/call}
        {/if}
    </div>

    <div id="insertLinkContainerBeforeMeta"/>

    <div id="storyMetaContainer">
        {if $story.meta != null}
            {call .renderStoryMetaField}
                {param meta: $story.meta /}
            {/call}
        {/if}
    </div>

    <div id="insertLinkContainerBeforeNarrative"/>

    {if $story.narrative != null}
        <div class="narrative">
            <div class="title">{$story.narrative.keyword}</div>
            <div class="inOrderTo">
                <table>
                    <tr>
                        <td style="vertical-align: top; text-align: left; width: 1%; white-space:nowrap;">
                            <span class="keyword">{$story.narrative.inOrderTo.keyword}</span>
                        </td>
                        <td style="text-align: left;">
                            {if $story.narrative.inOrderTo.value != null}
                                {call .renderSingleLineField}
                                    {param fieldName: 'In order to' /}
                                    {param fieldValue: $story.narrative.inOrderTo.value /}
                                {/call}
                            {else}
                                {call .renderSingleLineField}
                                    {param fieldName: 'In order to' /}
                                    {param fieldValue: null /}
                                {/call}
                            {/if}
                        </td>
                    </tr>
                </table>
            </div>
            <div class="asA">
//                <span class="keyword">{$story.narrative.asA.keyword}</span>
//                {if $story.narrative.asA.value != null}
//                    <span class="value">{$story.narrative.asA.value}</span>
//                {else}
//                    <span class="value">{sp}{$clickHere}</span>
//                {/if}
                <table>
                    <tr>
                        <td style="vertical-align: top; text-align: left; width: 1%; white-space:nowrap;">
                            <span class="keyword">{$story.narrative.asA.keyword}</span>
                        </td>
                        <td style="text-align: left;">
                            {if $story.narrative.asA.value != null}
                                {call .renderSingleLineField}
                                    {param fieldName: 'As a' /}
                                    {param fieldValue: $story.narrative.asA.value /}
                                {/call}
                            {else}
                                {call .renderSingleLineField}
                                    {param fieldName: 'As a' /}
                                    {param fieldValue: null /}
                                {/call}
                            {/if}
                        </td>
                    </tr>
                </table>

            </div>
            <div class="iWantTo">
                <span class="keyword">{$story.narrative.iWantTo.keyword}</span>
                {if $story.narrative.iWantTo.value != null}
                    <span class="value">{$story.narrative.iWantTo.value}</span>
                {else}
                    <span class="value">{sp}{$clickHere}</span>
                {/if}
            </div>
            {if $story.soThat != null}
                <div class="soThat">
                    <span class="keyword">{$story.narrative.soThat.keyword}</span>
                    {if $story.narrative.soThat.value != null}
                        <span class="value">{$story.narrative.soThat.value}</span>
                    {else}
                        <span class="value">{sp}{$clickHere}</span>
                    {/if}
                </div>
            {/if}
        </div>
    {/if}

    {if $story.givenStories != null}
        <div class="story-given-stories">
            <div class="story-given-stories-keyword">
                {$story.givenStories.keyword}
            </div>
        </div>
    {/if}
    {if $story.lifecycle != null}
        <div class="story-lifecycle">
            <div class="story-lifecycle-keyword">
                {$story.lifecycle.keyword}
            </div>
        </div>
    {/if}

//    {call .renderInsertLinkDiv}
//        {param triggerDivId: 'anotherTriggerDivId' /}
//        {param dropdownDivId: 'anotherDropdownDivId' /}
//        {param dropdownItems: [
//            $insertGivenStoriesLinkInfo,
//            $insertLifecycleLinkInfo,
//            $insertScenarioLinkInfo
//            ]
//         /}
//    {/call}

    {if $story.givenStories == null and $story.lifecycle == null and $story.scenarios.length == 0}
        {call .renderInsertLinkDiv}
            {param triggerDivId: 'insertAfterNarrativeTriggerDiv' /}
            {param dropdownDivId: 'insertAfterNarrativeDropdownDiv' /}
            {param dropdownItems: [
                $insertGivenStoriesLinkInfo,
                $insertLifecycleLinkInfo,
                $insertScenarioLinkInfo
            ] /}
        {/call}
    {elseif $story.givenStories == null and $story.lifecycle == null}
        {call .renderInsertLinkDiv}
            {param triggerDivId: 'insertAfterNarrativeTriggerDiv' /}
            {param dropdownDivId: 'insertAfterNarrativeDropdownDiv' /}
            {param dropdownItems: [
                $insertGivenStoriesLinkInfo,
                $insertLifecycleLinkInfo
            ] /}
        {/call}
    {elseif $story.givenStories == null and $story.scenarios.length == 0}
            {call .renderInsertLinkDiv}
                {param triggerDivId: 'insertAfterNarrativeTriggerDiv' /}
                {param dropdownDivId: 'insertAfterNarrativeDropdownDiv' /}
                {param dropdownItems: [$insertGivenStoriesLinkInfo, $insertScenarioLinkInfo] /}
            {/call}
    {elseif $story.givenStories == null}
        {call .renderInsertLinkDiv}
            {param triggerDivId: 'insertAfterNarrativeTriggerDiv' /}
            {param dropdownDivId: 'insertAfterNarrativeDropdownDiv' /}
            {param dropdownItems: [$insertGivenStoriesLinkInfo,] /}
        {/call}
    {elseif $story.lifecycle == null and $story.scenarios.length == 0}
            {call .renderInsertLinkDiv}
                {param triggerDivId: 'insertAfterNarrativeTriggerDiv' /}
                {param dropdownDivId: 'insertAfterNarrativeDropdownDiv' /}
                {param dropdownItems: [$insertLifecycleLinkInfo, $insertScenarioLinkInfo] /}
            {/call}
    {elseif $story.lifecycle == null}
        {call .renderInsertLinkDiv}
            {param triggerDivId: 'insertAfterNarrativeTriggerDiv' /}
            {param dropdownDivId: 'insertAfterNarrativeDropdownDiv' /}
            {param dropdownItems: [$insertLifecycleLinkInfo,] /}
        {/call}
    {elseif $story.scenarios.length == 0}
            {call .renderInsertLinkDiv}
                {param triggerDivId: 'insertAfterNarrativeTriggerDiv' /}
                {param dropdownDivId: 'insertAfterNarrativeDropdownDiv' /}
                {param dropdownItems: [$insertScenarioLinkInfo,] /}
            {/call}
    {/if}

    {if $story.scenarios.length != 0}
        <div class="story-scenarios">
            {foreach $scenario in $story.scenarios}
                <div class="story-scenario">
                    <span class="story-scenario-keyword">{$scenario.keyword}</span>
                </div>
            {/foreach}
        </div>
    {/if}

</div>
{/template}


