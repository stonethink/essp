<%@page import="server.essp.common.excelUtil.AcExcel" %>
<%
	System.out.println( request.getQueryString());
	System.out.println( request.getContextPath());
	System.out.println( request.getRequestURL());
	
	response.sendRedirect("/essp/ExcelExport?"+request.getQueryString());
%>