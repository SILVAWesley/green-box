$('.summernote').summernote({
	height: 300,   //set editable area's height
	codemirror: { // codemirror options
		theme: 'monokai'
	},
});

$(document).ready(function() {
	$('#summernote').summernote('justifyLeft');
	$('#summernote').summernote('fontName', 'Arial');
	$('.note-toolbar .note-table, .note-toolbar .note-insert').remove();
	$('#summernote').summernote({
		  fontNames: ['Arial', 'Arial Black', 'Comic Sans MS', 'Courier New']
		});

});

// save texts of html page 
 var save = function() {
 	  var makrup = $('.click2edit').summernote('code');
 	  $('.click2edit').summernote('destroy');
 };

