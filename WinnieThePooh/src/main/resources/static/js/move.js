function joinGo(){
	location.href="join.go";
}
function logoutGo(){
	location.href="logout.go";
}
function infoGo(){
	location.href="info.go";
}
function goPlayer1(){
	location.href="player1.go";
}
function goPlayer2(){
	location.href="player2.go";
}
function bye() {
	var t = prompt("탈퇴하려면 ㅂㅇ 입력")
	if (t == "ㅂㅇ"){
		location.href = "member.delete.do";
	}
}

function galleryPostDelete(no) {
	location.href = "gallery.post.delete?no=" + no;
}
function snsPostDelete(no, s_page) {
	location.href = "sns.post.delete?no=" + no + "&s_page=" + s_page;
}
function snsPostUpdate(no, txt, color, writer, s_page) {
	txt = txt.replace(/<br>/g, "\r\n"); // 그냥 <br>이면 처음 <br>만 바뀜
	$("#updatePopUpArea").css("top", "0px");
	$("#updatePopUpTable #snsPostID").css("color", "#" +color);
	$("#updateBtn").css("background-color", "#" + color);
	$("#updatePopUpTable").css("border", "#" + color + " solid 4px");
	$("#updatePopUpTable #snsPostIDtd").css("border-bottom", "#" +color + " solid 4px");
	
	$("#updatePopUpTable #snsPostID").text(writer);
	$("#updatePopUpTable #updateContentTxt").val(txt);
	$("#updatePopUpArea #snsPostUpdateNo").val(no);
	$("#updatePopUpArea #snsPostUpdatePage").val(s_page);
}
function snsPostUpdateCancle() {
	$("#updatePopUpArea").css("top", "-100%");
}

function snsPageChange(s_page) {
	location.href = "sns.postPage.go?s_page=" + s_page;
}

function snsReplyDelete(no, s_page) {
	location.href = "sns.reply.delete?no=" + no + "&s_page=" + s_page;
}
function snsReplyUpdate(no, txt, s_page) {
	txt = prompt("수정", txt);
	if(txt != null && txt.length > 0 && txt.length <= 450){
		location.href = "sns.reply.update?txt=" + txt + "&no=" + no + "&s_page=" + s_page;
	}
}
function goFolder(folder) {
	location.href = "note.folder.go?folder=" + folder;
}
function noteOpen(no) {
	location.href = "note.update.page.go?no=" + no;
}