var fileInput;
var fileBtn;
var box;
var res;

window.onload = function() {
	fileInput = document.getElementById("file");
	fileBtn = document.getElementById('fileBtn');
	box = document.getElementById('fileBtn');
	disDrag();
	drag();
	return;
}
