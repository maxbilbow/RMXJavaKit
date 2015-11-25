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
        .wc-output {
            min-height: 200px;
            max-height: 400px;
            overflow-y: scroll;
            background-color: #000000;
            color: #ffffff;
            padding: 5px;
            font-size: 75%;
            font-family: "Courier New";
            line-height: 1;
            margin-bottom: 10px;
        }

        #wc-output p {
            line-height: 1;
        }

        #uri {
            /*width: 100%;*/
        }
        #uri-options {
         width: 100%;
        }
    </style>
</head>
<body>
<div class="navbar navbar-fixed-top navbar-inverse">
    <div class="navbar-inner">
        <div class="container">
            <a class="brand" href="${contextPath}/">
                Home
            </a>
            <ul class="nav">
            </ul>
        </div>
    </div>
</div>
<div class="container">


    <div class="web-console"></div>

    <div class="ws-config">
        <div class="row-fluid">
            <div class="span6 socket-config ">

                <label>
                    On Socket:
                </label>
                <select id="uri-options">
                    <c:forEach items="${hostNames}" var="host">
                        <option>${host}</option>
                    </c:forEach>
                </select>
            </div>

            <div class="span6 debug-level">
                <label>Debug Level</label>
                <input type="radio" name="debug-level" value="debug-info">Info
                <input type="radio" name="debug-level" value="debug-warning" checked>Warn
                <input type="radio" name="debug-level" value="debug-error">Error
                <input type="radio" name="debug-level" value="debug-none">None
            </div>
        </div>
    </div>

</div>


<script src="https://cdn.socket.io/socket.io-1.3.7.js"></script>
<script data-main="${contextPath}/assets/js/live-updates-v2" src="${contextPath}/assets/js/require.min.js"></script>
</body>
</html>
