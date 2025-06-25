function disDrag() {
	document.addEventListener("drop", function(e) { //拖离
		e.preventDefault();
	})

	document.addEventListener("dragleave", function(e) { //拖后放   
		e.preventDefault();
	})

	document.addEventListener("dragenter", function(e) { //拖进  
		e.preventDefault();
	})

	document.addEventListener("dragover", function(e) { //拖来拖去    
		e.preventDefault();
	})

	return;
}



function drag() {
	box.addEventListener("drop", function(e) {
		var fileList = e.dataTransfer.files; //获取文件对象    
		if (fileList.length == 0) {
			return false;
		}
		res = fileList[0];
		fileBtn.innerText = fileList[0].name;
		fileInput.value = '';
	}, false);
	return;
}
