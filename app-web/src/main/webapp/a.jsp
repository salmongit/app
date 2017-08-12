<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String socPath="ws://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>websocketIndex</title>
    <script type="text/javascript">
        var wsuri = "<%=socPath%>webSocketServer";
        var ws = null;
        function startWebSocket() {
            if ('WebSocket' in window)
                ws = new WebSocket(wsuri);
            else if ('MozWebSocket' in window)
                ws = new MozWebSocket(wsuri);
            else
                console.error("not support WebSocket!");
            ws.onmessage = function(evt) {
                alert(evt.data);
                console.info(evt);
            };

            ws.onclose = function(evt) {

                alert("close");
                console.info(evt);
            };

            ws.onopen = function(evt) {
                alert("open");
                console.info(evt);
            };
        };

        function init(){
            startWebSocket();
        };
        init();


        function sendMsg(){
            ws.send(document.getElementById('writeMsg').value);
        }
    </script>
</head>
<body>
<input type="text" id="writeMsg"/>
<input type="button" value="sendSmgToServer" onclick="sendMsg()"/>
<br>
<span>
wait 8 second,server will send you a msg!
</span>
</body>
</html>