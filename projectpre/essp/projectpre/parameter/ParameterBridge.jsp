<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>
<bean:define id="kindType" value="<%=request.getParameter("kind")%>"/>
<html>
  <body >
	<script type="text/javascript">
		window.location="<%=request.getContextPath()%>/parameter/listAllParameter.do?kind=<%=kindType%>";
	</script>
  </body>
</html>
