function getId(id){
	return document.getElementById(id);
}

var data = {};//전송 데이터(JSON)

var ws ;
var mid = getId('mid');
var btnSend = getId('btnSend');
btnLogin.onclick = function(){
	 ws = new WebSocket("ws://" + location.host + "/signal");
     ws.onopen = function () {
                  console.log("connection opened");
                 };

      ws.onclose = function (event) {
                  console.log("connection closed");

                };

	ws.onmessage = function(msg){
	    console.log(msg.data);
	    if(msg != null && msg.data.trim() != ''){
	            alert(msg.data);
				$("#div1").html("<alret>" + msg.data + "</alret>");
		}
	}
}
btnSend.onclick = function(){
	send();
}

function send(){
	if(getId('mid').value.trim() != ''){
	    var msg = getId('mid').value+"가 곡을 업로드 했습니다.";
	    data.cmd = "alarm"
		data.mid = getId('mid').value;
		data.msg = msg;
		data.date = new Date().toLocaleString();
		var temp = JSON.stringify(data);
		ws.send(temp);
	}
	msg.value ='';

}