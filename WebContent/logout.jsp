<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
	import="evanshsu.webmud.*,evanshsu.webmud.telnet.*,evanshsu.webmud.model.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>WEBMUD</title>
</head>
<body>
	<%
		UserSession.destroy(session);
		response.sendRedirect("./");
	%>
</body>
</html>