$(document).ready(function(){
	var sid = setInterval(draw, 1000);
	var msgs = new Array();
	var cmds = new Array();
	var maxline = 1000;
	function draw() {
		$.ajax({
			type: "POST",
			url: "./msg.jsp",
			data: "",
			async: true,
			compelete: function(){
			},
			success: function(response){
				if(response != "") {
					$("#error").html("");
					
					var strs = response.split("\n");
					for(var i = 0; i < strs.length; i++) {
						if(strs[i] != "")
							msgs.push(strs[i]);
					}

					var all = msgs.join("\n");
					$("#show").html(all);
	
					$("#show").scrollTop($("#show").attr("scrollHeight"));

					while(msgs.length > maxline) {
						msgs.shift();
					}
					var from = all.lastIndexOf("(");
					var to = all.lastIndexOf(")");
					if (from < to && to - from <= 30 && from > 0 && to > 0) {
						var found = all.substring(from + 1, from + 2);
						$(".quick").text("k " + found);
					}
				}
			},
			error: function() {
				$("#error").html("ERROR");
				clearInterval(sid);
//				window.location.href="./logout.jsp";
			}
		});
	}
	var lastcmd = "";
	$("#typein").keypress(function(e){
		if(e.keyCode == 13 ){
			if($("#typein").val() == "") {
				$("#typein").val(lastcmd);
			}
			
			cmds.unshift($("#typein").val())
			while(cmds.length > 10) {
				cmds.pop();
			}
			$("#history").html(cmds.join("<br />"));
			$("#typein").focus().select();
			
			if($("#typein").val() == "quit") {
				setInterval(logout,3000);
			}
			
			$.ajax({
				type: "POST",
				url: "./send.jsp",
				data: "msg=" + encodeURI($("#typein").val()),
				async: true,
				dataType: "text",
				contentType: "application/x-www-form-urlencoded; charset=UTF-8",
				compelete: function(){
				},
				success: function(response){
				},
				error: function() {
					$("#error").html("ERROR");
				}
			});
			lastcmd = $("#typein").val();
			$("#typein").val("");
			draw();
		}		
	});
	$(".shortcuts").click(function(e){
		
		$.ajax({
			type: "POST",
			url: "./send.jsp",
			data: "msg=" + encodeURI($(this).text().toLowerCase()),
			async: true,
			dataType: "text",
			contentType: "application/x-www-form-urlencoded; charset=UTF-8",
			compelete: function(){
			},
			success: function(response){
			},
			error: function() {
				$("#error").html("ERROR");
			}
		});
		draw();
				
	});
	$("#typein").focus().select();
	function logout() {
		clearInterval(sid);
		window.location.href="./logout.jsp";
	}
});
