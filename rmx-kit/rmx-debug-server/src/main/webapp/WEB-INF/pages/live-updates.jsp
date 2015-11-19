<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
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
            max-height: 600px;
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
            <a class="brand" href="index.html">
                Home
            </a>
            <ul class="nav">
            </ul>
        </div>
    </div>
</div>
<div class="container">
    <div class="hero-unit">

            <div class="right" id="output">

            </div>

        <br/>
        <div class="form-group">
            <label>
                Message:
            </label>
            <input type="text" id="sendMessage" onkeydown="processKey(event)" value="/help">
            <label>
                 On Socket:
            </label>
            <select id="toSocket" onchange="updateUri()">
                <c:forEach items="${hostNames}" var="host">
                    <option>${host}</option>
                </c:forEach>
                <option> --custom-- </option>
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




<script src="https://cdn.socket.io/socket.io-1.3.7.js"></script>
<script type="text/javascript" src="js/sockets.js"></script>
</body>
</html>
