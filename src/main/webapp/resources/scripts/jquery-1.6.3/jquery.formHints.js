/***
@title:
Form Hints

@version:
1.0

@author:
Andreas Lagerkvist

@date:
2008-09-16

@url:
http://andreaslagerkvist.com/jquery/form-hints/

@license:
http://creativecommons.org/licenses/by/3.0/

@copyright:
2008 Andreas Lagerkvist (andreaslagerkvist.com)

@requires:
jquery

@does:
Using this plug-in you can add a descriptive hint to any form-control you may have on your site.  
Add a hint by giving the form-control a title-attribute. The hint will disappear and reappear as the user clicks the control.

@howto:
jQuery(document.body).formHints(); would give every form-control with a title-attribute a hint.

Run the plug-in on a parent-element of the form-controls you want to affect. If you run it on document.body every form-control with a title-attribute will get a hint.

@exampleHTML:
<form method="post" action="">

	<p>
		<label>
			Dummy<br />
			<input type="text" name="dummy" title="E.G. Dummy" />
		</label>
	</p>

	<p><input type="submit" value="Ok" /></p>

</form>

@exampleJS:
// I'm not actually running it because my site already uses formHints
// jQuery('#jquery-form-hints-example').formHints();
***/
jQuery.fn.formHints = function (conf) {
	var config = jQuery.extend({
		formControls:	'input[title], textarea[title]',
		className:		'default-value'
	}, conf);

	return this.each(function () {
		jQuery(config.formControls, this).each(function () {
			var t = jQuery(this);

			t.formHint = t.attr('title');
			t.attr('title', '');

			if (t.val() === '' || t.val() == t.formHint) {
				t.addClass(config.className).val(t.formHint);
			}

			t.focus(function () {
				if (t.val() == t.formHint) {
					t.val('').removeClass(config.className);
				}
			});
			
			t.blur(function () {
				if (t.val() === '' || t.val() == t.formHint) {
					t.addClass(config.className).val(t.formHint);
				}
			});
		});

		// Remove hints on form submission
		jQuery('form', this).submit(function () {
			jQuery('.' + config.className, this).removeClass(config.className).val('');
		});
	});
};
