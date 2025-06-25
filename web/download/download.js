function download() {
	if (document.getElementById("fileId").value == "") {
		$("#result").text("Error: File id is empty.");
		return false;
	}
	return true;
}