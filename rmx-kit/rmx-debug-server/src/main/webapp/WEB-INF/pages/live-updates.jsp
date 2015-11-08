<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <title>
    </title>

    <jsp:include page="header.jsp"/>
    <style>
        #output {
            min-height: 200px;
            max-height: 800px;
            overflow-y: scroll;
            background-color: #000000;
            color: #ffffff;
            padding: 5px;
        }

        #customSocket {
            /*width: 100%;*/
        }
    </style>
</head>
<body>
<div class="navbar navbar-fixed-top navbar-inverse">
    <div class="navbar-inner">
        <div class="container">
            <a class="brand" href="/debug-server">
                Home
            </a>
            <ul class="nav">
            </ul>
        </div>
    </div>
</div>
<div class="container">
    <div class="hero-unit">
        <div class="row">
            <div class="right" id="output">

            </div>

        </div>
        <div class="row rmx-error-log">
        </div>
    </div>

    <div class="hero-unit">
        <textarea hidden="true" id="sendMessage" name="name" class="form-control">Send one message.</textarea>


        <div class="form-group">
            <label>
                To Socket
            </label>
            <select id="toSocket" onchange="updateUri()">
                <option> --custom-- </option>
                <option>ws://localhost:8080/debug-server/updates</option>
                <option>ws://repo.rmx.click/debug-server/updates</option>
            </select>
            <input id="customSocket" type="text" value="ws://" onchange="updateUri()" hidden="true">
        </div>

        <div class="form-group">
            <input hidden="true" id="username" value="root"/>

            <input hidden="true" id="password" value="password"/>
        </div>

        <div id="openSocket" class="btn btn-primary" onclick="connect()">Open Socket</div>
        <div id="sendButton" class="btn btn-primary" onclick="sendMessage()">Send</div>
        <div id="closeSocket" class="btn btn-primary" onclick="disconnect()">Disconnect</div>
    </div>
</div>


<script type="text/javascript" src="js/sockets.js"></script>

<%--ng-app="chatApp"--%>
<script src="https://cdn.socket.io/socket.io-1.3.7.js"></script>
<script src="libs/sockjs/sockjs.min.js" type="text/javascript"></script>
<script src="libs/stomp-websocket/lib/stomp.min.js" type="text/javascript"></script>
<script src="libs/angular/angular.min.js"></script>
<script src="libs/lodash/dist/lodash.min.js"></script>
<%--<script src="app/app.js" type="text/javascript"></script>--%>
<%--<script src="app/controllers.js" type="text/javascript"></script>--%>
<%--<script src="app/services.js" type="text/javascript"></script>--%>

<%--<script src="assets/js/bootstrap.js"></script> --%>
</body>
</html>
