var socket = io.connect("http://192.168.0.56:9999");
function connectBoardEvent() {
	var board = document.getElementById("board");
	if (board != null) {
		var color;
		$("#boardColorInput").keyup(function() {
			color = "#" + $(this).val();
			$(this).css("color", color);
			$(this).parent().css("color", color);
		});
		var boardAreaOffset = $("#board").offset();

		var pen = board.getContext("2d");
		pen.strokeStyle = "white";
		pen.lineWidth = 3;
		var canvasX = boardAreaOffset.left + 10;
		var canvasY = boardAreaOffset.top + 10;

		var startX = 0;
		var startY = 0;
		var endX = 0;
		var endY = 0;
		var click = false;
		$("#board").mousedown(function(e) {
			click = true;
			startX = e.pageX - canvasX;
			startY = e.pageY - canvasY;
		});
		$("#board").mousemove(function(e) {
			if (click) {
				endX = e.pageX - canvasX;
				endY = e.pageY - canvasY;

				var xy = { c: color, sx: startX, sy: startY, ex: endX, ey: endY }
				socket.emit("msg", xy);

				startX = endX;
				startY = endY;
			}
		});
		$("#board").mouseup(function() {
			click = false;
		});

		socket.on("srvMsg", function(data) {
			pen.strokeStyle = data.c;
			pen.beginPath();
			pen.moveTo(data.sx, data.sy);
			pen.lineTo(data.ex, data.ey);
			pen.closePath();
			pen.stroke();
		});
	}
}
function connectGalleryControllerAreaSummonEvent() {
	var upload = $("#uploadTbl");
	$("#galleryTbl #goUpload").click(function() {
		if (upload.css("right") == "-300px") {
			upload.css("right", "120px")
		} else {
			upload.css("right", "-300px")
		}
	});
}

function connectMemberAddaressInputEvent() {
	$(".postNumber, .address").click(function() {
		new daum.Postcode({
			oncomplete: function(data) {
				/*
					건물명 무시하면 두줄이 끝
					$("#postNumber").val(data.zonecode);
					$("#address").val(roadAddr);
				*/
				var roadAddr = data.roadAddress; // 도로명 주소 변수
				var extraRoadAddr = ''; // 참고 항목 변수
				// 법정동명이 있을 경우 추가한다. (법정리는 제외)
				// 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
				if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
					extraRoadAddr += data.bname;
				}
				// 건물명이 있고, 공동주택일 경우 추가한다.
				if (data.buildingName !== '' && data.apartment === 'Y') {
					extraRoadAddr += (extraRoadAddr !== '' ? ', ' + data.buildingName : data.buildingName);
				}
				// 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
				if (extraRoadAddr !== '') {
					extraRoadAddr = ' (' + extraRoadAddr + ')';
				}
				// 우편번호와 주소 정보를 해당 필드에 넣는다.
				$(".postNumber").val(data.zonecode);
				$(".address").val(roadAddr);
				// 참고항목 문자열이 있을 경우 해당 필드에 넣는다.
				if (roadAddr !== '') {
					$(".address").val(roadAddr + extraRoadAddr);
				}
			}
		}).open();
	});
}
function connectSNSControllerAreaSummonEvent() {
	var writeTbl = $("#writerTable");
	$("#snsTbl #goWrite").click(function() {
		if (writeTbl.css("right") == "-300px") {
			writeTbl.css("right", "120px")
		} else {
			writeTbl.css("right", "-300px")
		}
	});
}

function webTitleEffectEvent() {
	$("#siteTitle #title").text("Winnie");
	setTimeout(function() {
		$("#siteTitle #title").text("Winnie The");
		setTimeout(function() {
			$("#siteTitle #title").text("Winnie The Pooh");
		}, 1000);
	}, 1000);
}

function webRightClickEvent() {
	$(document).contextmenu(function() { // 우클릭해서 그 메뉴 나올 때
		return false;
	});
	$("html").mouseup(function(e) {
		if (e.button == 2) {
			$("#rightClickImg").css("opacity", "1");
			$("#rightClickImg").css("top", e.pageY - 45);
			$("#rightClickImg").css("left", e.pageX);
			setTimeout(function() {
				$("#rightClickImg").css("top", "-100px");
			}, 1000);
		}
	});
}
function followWeatherEvent() {
	$("html").mousemove(function(e) {
		$("#mouseWeather").css("left", e.pageX + 10);
		$("#mouseWeather").css("top", e.pageY);
	});
}
function connectJoinIdInputEvent() {
	$("#joinInputID").keyup(function(e) {
		var id = $(this).val();
		$.getJSON("member.get?id=" + id, function(memberData) {
			if (memberData.member[0] == null) {
				$("#joinInputIDChecker").css("color", "red");
				$("#joinInputIDChecker").text("사용가능한 아이디입니다");
			} else {
				$("#joinInputIDChecker").css("color", "red");
				$("#joinInputIDChecker").text("중복 아이디입니다");
			}
		});
	});
}

var map = null;
var marker = null;
var roadview = null;
var roadviewClient = new kakao.maps.RoadviewClient();
function moveMap(lat, lng) {
	var position = new kakao.maps.LatLng(lat, lng);
	map.panTo(position);
	roadviewClient.getNearestPanoId(position, 50, function(panoId) {
		roadview.setPanoId(panoId, position);
	});
	marker.setPosition(position);
}
function connectMapController() {
	var container = document.getElementById("map");
	navigator.geolocation.getCurrentPosition(function(location) {
		var lat = location.coords.latitude;
		var lon = location.coords.longitude;
		var position = new kakao.maps.LatLng(lat, lon);
		var container = document.getElementById("map");
		var roadviewContainer = document.getElementById('roadview'); //로드뷰를 표시할 div

		var options = { center: position, level: 3 };
		map = new kakao.maps.Map(container, options);
		marker = new kakao.maps.Marker({ map: map, position: position });
		roadview = new kakao.maps.Roadview(roadviewContainer); //로드뷰 객체

		moveMap(lat, lon);
	});

	var locSearch = document.getElementById("localSearch");
	$(locSearch).keyup(function(e) {
		var searchWord = $(locSearch).val();
		$.ajax({
			url: "https://dapi.kakao.com/v2/local/search/address.json",
			data: { "query": searchWord },
			beforeSend: function(request) {
				request.setRequestHeader("Authorization", "KakaoAK e9f4bd4e350432ceafa6a59e824954d8");
			},
			success: function(data) {
				$.each(data.documents, function(a, b) {
					var loc = $(b)[0];
					moveMap(loc.y, loc.x);
				});
			}
		});
	});
	$("#searchWord").keyup(function(e) {
		if (locSearch != null) {
			var searchWord = $("#searchWord").val();
			var position = map.getCenter();

			$.ajax({
				url: "https://dapi.kakao.com/v2/local/search/keyword.json",
				data: {
					"query": searchWord, "sort": "distance", "radius": 1000,
					"y": position.getLat() + "", "x": position.getLng() + "", "size": 10
				},
				beforeSend: function(request) {
					request.setRequestHeader("Authorization", "KakaoAK e9f4bd4e350432ceafa6a59e824954d8");
				},
				success: function(data) {
					$.each(data.documents, function(a, b) {
						var loc = $(b)[0];
						var nameTd = $("<td></td>").text(loc.place_name).attr("class", "name");
						var addressTd = $("<td></td>").text(loc.road_address_name + " (" + loc.distance + "m)").attr("class", "info");
						var phoneTd = $("<td></td>").text(loc.phone).attr("class", "info");
						var urlTd = $("<td></td>").append($("<a></a>").text(loc.place_url).attr("href", loc.place_url)).attr("class", "info");
						var table = $("<table></table>").append(
							$("<tr></tr>").append(nameTd),
							$("<tr></tr>").append(addressTd),
							$("<tr></tr>").append(phoneTd),
							$("<tr></tr>").append(urlTd))
							.attr("class", "aLoc")
							.attr("onclick", "moveMap(" + loc.y + "," + loc.x + ");");
						$("#searchResult").append(table);
					});
				}
			});
		}
	});
	$("#searchWord").keyup(function(e) {
		$("#searchResult").empty();
	});
}
function connectChatEvent() {
	var w = $("#chatWriter").val();
	var name = w;
	$("#chatWriteColorInput").keyup(function() {
		$(this).css("color", "#" + $(this).val() + "BB");
		$(this).parent().css("color", "#" + $(this).val() + "BB");
	});
	$("#chatWriteBtn").click(function() {
		var t = $("#chatMsgTA").val().replace(/\n/g, "<br>");
		var c = $("#chatWriteColorInput").val();
		var msg = { writer: w, txt: t, color: c };
		if (c != "" && t != null) {
			socket.emit("chatMyMsg", msg);
			$("#chatMsgTA").val("");
		}
	});

	socket.on("srvChatMsg", function(msg) {
		var br = $("<br>"); var br2 = $("<br>");
		var td = $("<td></td>").append(msg.writer, br, br2, msg.txt);
		var tr = $("<tr></tr>").append(td);
		var table = $("<table></table>").attr("class", "msg").attr("style", "background-color:#" + msg.color).append(tr);
		var td2 = $("<td></td>").append(table);
		var tr2 = $("<tr></tr>").append(td2);
		var table2 = $("<table></table>").attr("class", "table2").append(tr2);
		if (name == msg.writer) {
			td2 = td2.attr("align", "right");
		}
		$("#chatArea").append(table2);
		$(window).scrollTop($(document).height());
	});
}
function connectFoodControllerAreaSummonEvent() {
	var upload = $("#foodUploadTbl");
	var btn = $("#foodRegBtn");
	$("#foodTbl #goUpload").click(function() {
		if (upload.css("right") == "-300px") {
			upload.css("right", "120px")
			btn.css("right", "135px")
		} else {
			upload.css("right", "-300px")
			btn.css("right", "-300px")
		}
	});
}
function getFoodReview() {
	$.getJSON("food.post.get", function(foodPosts) {
		$("#foodPostArea").empty();
		$.each(foodPosts.post, function(a, b) {
			var img = $("<img>").attr("src", "food.photo.folder/" + b.photo).attr("class", "foodPostImg");
			var imgTd = $("<td></<td>").prepend(img);
			var nameTd = $("<td></<td>").text(b.name);
			var scoreTd = $("<td></<td>").text(b.score);
			var imgTr = $("<tr></tr>").prepend(imgTd);
			var nameTr = $("<tr></tr>").prepend(nameTd);
			var scoreTr = $("<tr></tr>").prepend(scoreTd);
			var table = $("<table></table>").prepend(imgTr, nameTr, scoreTr).attr("class", "foodPostTbl");
			$("#foodPostArea").prepend(table);
		});
	});
}
function regFoodReview() {
	$("#foodRegBtn").click(function() {
		var f = $("#foodUploadForm")[0];
		var data = new FormData(f);
		$.ajax({
			url: "food.post.reg",
			type: "POST",
			data: data,
			contentType: false,
			processData: false,
			success: function(result) {
				alert(result.result);
				getFoodReview();
				socket.emit("foodMsg", "success");
			}
		});
	});
	socket.on("srvfoodMsg", function(msg) {
		getFoodReview();
	});
}
$(function() {
	connectGalleryControllerAreaSummonEvent();
	connectMemberAddaressInputEvent();
	connectSNSControllerAreaSummonEvent();
	connectJoinIdInputEvent();
	connectMapController();
	webTitleEffectEvent();
	webRightClickEvent();
	connectBoardEvent();
	followWeatherEvent();
	connectChatEvent();
	connectFoodControllerAreaSummonEvent();
	getFoodReview();
	regFoodReview(); 
});