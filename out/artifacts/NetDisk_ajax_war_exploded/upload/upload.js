function upload() {
	if (fileBtn.innerText == '') {
		$("#result").text("Error: File is empty.");
		return false;
	}
	ajax();
	return false;
}
