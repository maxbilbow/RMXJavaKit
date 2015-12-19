<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>
    </title>

    <%--<jsp:include page="header.jsp"/>--%>
    <link rel="stylesheet" href="${contextPath}/assets/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="${contextPath}/assets/css/bootstrap-responsive.min.css"/>
    <link rel="stylesheet" href="${contextPath}/assets/css/web-console.css"/>
    <link href="${contextPath}/assets/css/error.css" rel="stylesheet"/>
    <style>
        body {
            padding-top: 60px;
            /* 60px to make the container go all the way
                 to the bottom of the topbar */
        }

        #log-message {
            color: darkgreen;
        }

        #log-error {
            color: darkred;
        }

        #log-warning {
            color: darkorange;
        }
        .server-status h2, .server-status form {
            display: inline;
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
                <li><a href="${contextPath}/live/">Live Updates</a> </li>
                <li><a href="${contextPath}/pages/?p=console">Console Module</a> </li>
            </ul>
        </div>
    </div>
</div>

<div class="container">


    <div class="hero-unit server-status">
        <h2>${status}</h2>
        <form action="#" name="f" method="post">
            <input class="btn btn-primary" type="submit" value="${connect}"/>
        </form>
    </div>

    <div class="rmx-error-log row-fluid">
        <div class="span4 fjwa-log">
            <ul>
                <c:forEach items="${logs}" var="log">
                    <li><span id="log-message"> [${log.shortTime}] LOG >> </span>${log.html} (Sender: ${log.sender})</li>
                </c:forEach>
            </ul>
        </div>

        <div class="span4 fjwa-log">
            <ul>
                <c:forEach items="${warnings}" var="log">
                    <li><span id="log-warning"> [${log.shortTime}] WARNING >> </span>${log.html}(Sender: ${log.sender})</li>
                </c:forEach>
            </ul>
        </div>

        <div class="fjwa-errors span4">
            <ul>
                <c:forEach items="${errors}" var="log">
                    <li><span id="log-error"> [${log.shortTime}] ERR >> </span>${log.html} (Sender: ${log.sender})</li>
                </c:forEach>
            </ul>
        </div>
    </div>
</div>

</body>
</html>