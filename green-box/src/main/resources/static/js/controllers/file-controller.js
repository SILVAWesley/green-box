$('.summernote').summernote({
	height: 300,   //set editable area's height
	codemirror: { // codemirror options
	theme: 'monokai'
	}
	});

$(document).ready(function() {
$('#summernote').summernote('justifyLeft');});
  