<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="/inc/pagedef.jsp"%>
<%@page import="c2s.workflow.DtoRequestProcess,c2s.workflow.DtoWorkingProcess,itf.hr.HrFactory"%>
<html>
<head>
<tiles:insert page="/layout/head.jsp">
  <tiles:put name="title" value="Process"/>
  <tiles:put name="jspName" value="Process"/>
</tiles:insert>
</head>
<script language="javaScript" type="text/javascript">
function onAbortProcess(){
  if(confirm("Do you confirm to abort this process?")){
    window.location = "<%=request.getContextPath()%>/wf/AbortProcess.do?wkRid=<bean:write name="webVo" property="rid" />";
  }
}

function onCancel(){
      <logic:equal value="finish" name="webVo" property="status">
        opener.location.reload();
      </logic:equal>
      window.close();
}
</script>
<body>
<table bgcolor="ffffff" cellpadding="1" cellspacing="1" border="0" align="center" width="95%">
  <tr>
    <td class="orarowheader" width="100%">
      <bean:write name="webVo" property="subEmpLoginid" />
      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      <bean:write name="webVo" property="processName" />
    </td>
  </tr>
  <tr>
    <td width="100%" height="300" valign="top">
      <logic:iterate id="activity" name="webVo" property="currActivityList" indexId="indexId">
        <table align="center">
        <tr class="<%=indexId.intValue()%2==1?"tableDataOdd2":"tableDataEven2"%>" valign="top">
          <td class="oracelltext" width="5" height="20"></td>
          <bean:define id="loginId" name="activity" property="performerLoginid" />
          <%String userName=HrFactory.create().getName((String)loginId);%>
          <td class="oracelltext" width="20" title="<%=loginId+"/"+userName%>">
            <%=userName%>
          </td>
          <td class="oracelltext" width="80"><bean:write name="activity" property="activityName" /></td>
          <td class="oracelltext" width="20"><bean:write name="activity" property="status" /></td>
          <td class="oracelltext" width="350"><bean:write name="activity" property="description" /></td>
        </tr>
      </table>
    </logic:iterate>
  </td>
</tr>
  <tr align="right">
    <td>
      <logic:notEqual value="finish" name="webVo" property="status">
        <input type="button" name="abort" value="Abort" class="button" onclick="onAbortProcess();" />
        <input type="button" name="cancel" value="Cancel" class="button" onclick="onCancel();" />
      </logic:notEqual>
      <logic:equal value="finish" name="webVo" property="status">
        <input type="button" name="cancel" value="OK" class="button" onclick="onCancel();" />
      </logic:equal>
    </td>
  </tr>
</table>
</body>
