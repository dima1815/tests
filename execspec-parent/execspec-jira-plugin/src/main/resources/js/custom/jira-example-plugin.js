Plugin = {};
Plugin.PluginView = {};

Plugin.init = function () {
	Plugin.PluginView.init();
	Plugin.PluginView.showAddStoryForm();
}

Plugin.PluginView.init = function () {
	AJS.$('#main-container').html(plugin.tpl.pluginview.render());
}

Plugin.PluginView.showAddStoryForm = function () {
	AJS.$('#add-new-story-container').html(execspec.view.addStoryForm.render());
}

AJS.$(Plugin.init);
