function joinCheck(f) {
	var idField = f.id;
	var pwwField = f.pw;
	var pwckField = f.pwCk;
	var nameField = f.name;
	var add1Field = f.add1;
	var add2Field = f.add2;
	var add3Field = f.add3;
	var photoField = f.photo2;
	
	if (isEmpty(idField) || containsHS(idField) || $("#joinInputIDChecker").text() == "중복 아이디입니다"){
		alert("ID ?");
		idField.value = "";
		idField.focus();
		return false;
	}

	if (isEmpty(pwwField) || notEqual(pwwField, pwckField) || notContains(pwwField, "1234567890")){
		alert("PW ?");
		pwwField.value="";
		pwckField.value="";
		pwwField.focus();
		return false;
	}
	
	if (isEmpty(nameField)){
		alert("name ?");
		nameField.value = "";
		nameField.focus();
		return false;
	}
	if (isEmpty(add1Field) || isEmpty(add2Field) || isEmpty(add3Field)){
		alert("address ?");
		add1Field.value = "";
		add2Field.value = "";
		add3Field.value = "";
		return false;
	}
	
	if (isEmpty(photoField) || 
			(isNotType(photoField, "png") && isNotType(photoField, "gif") && isNotType(photoField, "jpg"))) {
			alert("photo ?");
			photoField.value="";
			return false;
	}
	return true;
}
function loginCheck(f){
	var idField = f.id;
	var pwField = f.pw;
	
	if (isEmpty(idField) || isEmpty(pwField)){
			alert("?");
			idField.value = "";
			pwField.value = "";
			idField.focus();
			return false;
		}
	return true;
}

function updateCheck(f) {
	var pwField = f.pw;
	var pwckField = f.pwchk;
	var nameField = f.name;
	var add1Field = f.add1;
	var add2Field = f.add2;
	var add3Field = f.add3;
	var photoField = f.photo2;

	if (isEmpty(pwField) || notEqual(pwField, pwckField) || notContains(pwField, "1234567890")) {
			alert("pw?");
			pwField.value = "";
			pwckField.value = "";
			pwField.focus();
			return false;
		}
	if (isEmpty(nameField)){
		alert("name ?");
		nameField.value = "";
		nameField.focus();
		return false;
	}
	if (isEmpty(add1Field)){
		alert("addr ?");
		nameField.value = "";
		nameField.focus();
		return false;
	}
	if (isEmpty(add2Field)){
		alert("addr ?");
		nameField.value = "";
		nameField.focus();
		return false;
	}
	if (isEmpty(add3Field)){
		alert("addr ?");
		nameField.value = "";
		nameField.focus();
		return false;
	}
	if(isEmpty(photoField)) { 
		return true;
	}
	
	if (isNotType(photoField, "png") && isNotType(photoField, "gif") && isNotType(photoField, "jpg")) {
			alert("photo ?");
			photoField.value="";
			return false;
	}
	
	return true;
}
function photoUpload(f) {
	var titleField = f.title;
	var fileField = f.file;
	
	if(isEmpty(titleField)){
		alert("title ?");
		titleField.value="";
		titleField.focus();
		return false;
	}
	
	if (isEmpty(fileField) || 
			(isNotType(fileField, "png") && isNotType(fileField, "gif") && isNotType(fileField, "jpg"))) {
			alert("photo ?");
			fileField.value="";
			fileField.focus();
			return false;
	}
	return true;
}

function snsWriteCheck(f) {
	if(isEmpty(f.color) || lessThan(f.color, 6)){
		alert("color ?");
		return false;
	}
	if(isEmpty(f.txt)){
			alert("txt ?");
			return false;
	}
	return true;
}

function noteRegCheck(f){
	if(isEmpty(f.title)){
		alert("title ?");
		return false;
	}
	if(isEmpty(f.color) || lessThan(f.color, 6)){
		alert("color ?");
		return false;
	}
	if (isEmpty(f.icon2) || 
			(isNotType(f.icon2, "png") && isNotType(f.icon2, "gif") && isNotType(f.icon2, "jpg"))) {
		alert("photo ?");
		f.icon2.value="";
		f.icon2.focus();
		return false;
	}
	if(isEmpty(f.txt)){
		alert("txt ?");
		return false;
	}
	return true;
}