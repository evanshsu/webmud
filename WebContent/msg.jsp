<%@ page language="java" contentType="text/plain; charset=UTF-8" pageEncoding="UTF-8"
	import="evanshsu.webmud.*, evanshsu.webmud.telnet.*, evanshsu.webmud.model.*"%><%
	session.setMaxInactiveInterval(20 * 60);
	UserSession user = UserSession.getInstance(session);
	for(String text:user.getView().pop())
		out.println(text);
%>