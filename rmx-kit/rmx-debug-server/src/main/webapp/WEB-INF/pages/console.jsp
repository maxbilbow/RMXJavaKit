<%--
  Created by IntelliJ IDEA.
  User: bilbowm
  Date: 25/11/2015
  Time: 15:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
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

<script data-main="${contextPath}/assets/js/console" src="${contextPath}/assets/js/require.min.js"></script>
</body>
</html>
