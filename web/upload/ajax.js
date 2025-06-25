function ajax() {
	var xhr = new XMLHttpRequest(); //第一步  
	//新建一个FormData对象  
	var formData = new FormData(); //++++++++++  
	//追加文件数据  
	formData.append('file', res);
	formData.append('password', $("#password").val());
	//post方式  
	xhr.open('POST', '/upload'); //第二步骤  
	//发送请求  
	xhr.send(formData); //第三步骤  
	//ajax返回  
	xhr.onreadystatechange = function() { //第四步
		// alert(xhr.responseText);
		// console.log(xhr.responseText);
		if (xhr.readyState == 4 && xhr.status == 200 && xhr.responseText != 0) {
			$("#result").text("Success. File Id : " + xhr.responseText);
		} else {
			$("#result").text("Upload Error.");
		}
	};
	//设置超时时间  
	/*    xhr.timeout = 10000;  
	    xhr.ontimeout = function(event){  
	　　　　alert('请求超时！');  
	　　}
	*/
}
