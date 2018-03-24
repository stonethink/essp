
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>
<bean:define id="contextPath" value="<%=request.getContextPath()%>"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
     <tiles:insert page="/layout/head.jsp">
            <tiles:put name="title" value="Blank Page"/>
            <tiles:put name="jspName" value=""/>
        </tiles:insert>
  </head>
  
  <body bgcolor="#FFFFFF">
    <TABLE>
    <tr>
    <td class="list_range">
    <bean:message bundle="projectpre" key="projectCode.upload.Comment1" />
    <IMG alt="" src="<%=contextPath+"/image/humanAllocate.gif"%>">
    <bean:message bundle="projectpre" key="projectCode.upload.Comment2" />
    </td>
    </tr>
    </TABLE>
  </body>
</html>
