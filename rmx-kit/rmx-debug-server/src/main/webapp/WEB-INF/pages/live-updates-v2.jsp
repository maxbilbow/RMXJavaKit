<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <title>
    </title>

    <%--<jsp:include page="header.jsp"/>--%>
    <link rel="stylesheet" href="${contextPath}/assets/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="${contextPath}/assets/css/bootstrap-responsive.min.css"/>

    <style>
        #wc-output {
            min-height: 200px;
            max-height: 600px;
            overflow-y: scroll;
            background-color: #000000;
            color: #ffffff;
            padding: 5px;
            font-size: 75%;
            font-family:"Courier New";
            line-height: 1;
        }
        #wc-output p {
            line-height: 1;
        }

        #uri {
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

            <div id="wc-output">

            </div>

        <br/>

        <form class="form-group socket-config row-fluid" action="">
            <div class="span6">
            <label>
                Message:
            </label>
            <input type="text" id="wc-input" value="/help">
            <label>
                 On Socket:
            </label>
            <select id="uri-options">
                <c:forEach items="${hostNames}" var="host">
                    <option>${host}</option>
                </c:forEach>
            </select>
            <input id="get-uri" type="text" value="ws://">
            </div>

            <div class="span6 debug-level">
                <label>Debug Level</label>
                <input type="radio" name="debug-level" value="pubsub-info" checked>Info<br/>
                <input type="radio" name="debug-level" value="pubsub-warn">Warn<br/>
                <input type="radio" name="debug-level" value="pubsub-error">Error<br/>
                <input type="radio" name="debug-level" value="false">None<br/>
            </div>
        </form>

        <div class="form-group">
            <input hidden="true" id="username" value="root"/>

            <input hidden="true" id="password" value="password"/>
        </div>

        <div id="openSocket" class="btn btn-primary" >Open Socket</div>
        <div id="sendButton" class="btn btn-primary">Send</div>
        <div id="closeSocket" class="btn btn-primary">Disconnect</div>
    </div>
</div>




<script src="https://cdn.socket.io/socket.io-1.3.7.js"></script>
<script data-main="${contextPath}/assets/js/live-updates-v2" src="${contextPath}/assets/js/require.min.js"></script>
</body>
</html>
