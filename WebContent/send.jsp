<%@ page language="java" contentType="text/plain; charset=UTF-8" pageEncoding="UTF-8" 
	import="evanshsu.webmud.*, evanshsu.webmud.telnet.*, evanshsu.webmud.model.*"%><%
	
	UserSession user = UserSession.getInstance(session);
	String msg = request.getParameter("msg");
	user.getController().getConnection().send(msg);
	out.print(msg);
%>