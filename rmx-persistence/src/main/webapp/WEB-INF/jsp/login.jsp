<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
	<title>Fitness Tracker Custom Login Page</title>

	<%--<spring:url value="/assets/css/error.css" var="errCss"/>--%>
	<%--<link href="${errCss}" rel="stylesheet">--%>
    <style type="text/css">
        .error {
            color: #ff0000;
        }

        .errorblock {
            color: #000;
            background-color: #ffEEEE;
            border: 3px solid #ff0000;
            padding: 8px;
            margin: 16px;
        }

    </style>
</head>

<body onload='document.f.j_username.focus();'>
	<h3>Fitness Tracker Custom Login Page</h3>


	<c:url var="loginUrl" value="/login.html"/>
	<form action="${loginUrl}" name="f" method="post">
		<c:if test="${param.error != null}">
			<div class="errorblock">
				Your login was unsuccessful. <br />
				Caused: ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message } <br/>
			</div>
		</c:if>
		<table>
			<tr> 
				<td>User:</td>
				<td><input type="text" name="j_username" value=""></td>
			</tr>
			<tr>
				<td>Password:</td>
				<td><input type="password" name="j_password" ></td>
			</tr>
			<tr>

				<td colspan="2">
					<input type="hidden"
									   name="${_csrf.parameterName}"
									   value="${_csrf.token}"/>
					<input type="submit" name="Submit" value="Submit"></td>
			</tr>
			<tr>
				<td colspan="2"><input type="reset" name="reset" > </td>
			</tr>	
		</table>
		
	
	</form>
</body>


</html>