<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<style type="text/css" media="screen">
canvas#gl {
   width: 100%;
   height: auto;
}
</style>
<script src="assets/js/gl.js"></script>

</head>
<body  onload="glrun('triangles',true)">
<h1>${greeting}</h1>

 <canvas id="gl">
            
        </canvas>
        <br/>
<form>
<button type="button" onclick="glrun('triangles',false)">GL_TRIANGLES</button>
<button type="button" onclick="glrun('wireframe',false)">GL_LINE_STRIP</button>
<button type="button" onclick="glrun('points',false)">GL_POINTS</button>
<button type="button" onclick="toggleBackground()">Background</button>
<button type="button" onclick="showLog()">See Log</button>
</form>



</body>
</html>
