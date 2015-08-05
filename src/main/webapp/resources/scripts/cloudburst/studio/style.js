function Style(id) {

	jQuery.get('resources/templates/studio/' + id + '.html', function(template) {
		jQuery("#styles").empty();
		jQuery("#styles").append(template);

		$('#styleselect').change(
				function() {

					var option = $('#styleselect').val();
					alert(option);
					jQuery.get('resources/templates/studio/style/' + option
							+ 'style.html', function(template1) {
						jQuery("#styleform").empty();
						jQuery("#styleform").append(template1);
					});

				});
	});
}