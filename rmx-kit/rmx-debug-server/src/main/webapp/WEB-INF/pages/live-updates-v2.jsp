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
    <link rel="stylesheet" href="${contextPath}/assets/css/web-console.css"/>

    <style>

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
                <li><a href="${contextPath}/live/">Live Updates</a> </li>
                <li><a href="${contextPath}/pages/?p=console">Console Module</a> </li>
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



<script data-main="${contextPath}/assets/js/live-updates-v2" src="${contextPath}/assets/js/require.min.js"></script>
</body>
</html>
