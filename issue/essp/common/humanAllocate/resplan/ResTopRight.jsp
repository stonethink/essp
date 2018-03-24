<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>

<html>
<head>
	<tiles:insert page="/layout/head.jsp">
		        <tiles:put name="title" value="Resource Plan"/>
        <tiles:put name="jspName" value=""/>
</tiles:insert>
</head>
<body leftmargin="0" topmargin="0">
 <table  border="0" cellpadding="0" cellspacing="0">
   <tr >
    <td bgcolor="#999999"height="60">
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <%-- 遍历年 --%>
        <logic:iterate id="year" name="webVo" property="years">
          <td bgcolor="#999999" colspan="<bean:write name="year" property="months" />">
            <table width="100%" border="0" cellspacing="1" cellpadding="0">
              <tr>
                <td  background="../photo/alloc_tool/alloc_tool_back3.jpg" class="oracelltext"><bean:write name="year" property="year" />
                </td>
              </tr>
            </table>
          </td>
        </logic:iterate>
        </tr>

        <tr>
          <%-- 遍历月 --%>
        <logic:iterate id="month" name="webVo" property="months">
          <td bgcolor="#999999">
            <table width="100%" border="0" cellspacing="1" cellpadding="0">
              <tr>
                <td width="<bean:write name="month" property="width"/>"  background="../photo/alloc_tool/alloc_tool_back3.jpg" class="oracelltext">
                  <bean:write name="month" property="monthName"/>
                </td>
               </tr>
             </table>
           </td>
        </logic:iterate>
        </tr>

        <tr>
          <%-- 遍历周 --%>
        <logic:iterate id="month" name="webVo" property="months">
          <td width="<bean:write name="month" property="width"/>"  bgcolor="#999999" class="oracelltext">
              <table width="<bean:write name="month" property="width"/>" border="0" cellspacing="1" cellpadding="0">
                 <tr>
                 <logic:iterate id="week" name="month" property="weeks">
                   <td width="20" height="20" background="<bean:write name="week" property="background"/>" class="oracelltext">
                     <bean:write name="week" property="showDay"/>
                   </td>
                  </logic:iterate >
                  </tr>
               </table>
            </td>
          </logic:iterate>
        </tr>
      </table>
    </td>
  </tr>
</table>
</body>
</html>
