<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>
<%@page import="server.essp.issue.typedefine.viewbean.VbType"%>

<bean:define id="typeName" name="oneVB" property="typeName"/>
<bean:define id="status" name="oneVB" property="status"/>
<bean:define id="sequence" name="oneVB" property="sequence"/>
<bean:define id="description" name="oneVB" property="description"/>
<bean:define id="contextPath" value="<%=request.getContextPath()%>"/>

<html>
<head>
     <tiles:insert page="/layout/head.jsp">
        <tiles:put name="title" value=" Quality Activity " />
        <% String jspName = contextPath + "/issue/typedefine/TypeDefineCSS"; %>
        <tiles:put name="jspName" value="<%=jspName%>" />
      </tiles:insert>
      <script type="text/javascript">
        function onSaveData(){
          //alert("Issue Type Update!");
          document.forms[0].submit();
          //alert("issueTypeForm.saveStatusHistory.value="+issueTypeForm.saveStatusHistory.value);
          //alert("issueTypeForm.saveInfluenceHistory.value="+issueTypeForm.saveInfluenceHistory.value);
        }

        function onRefreshData(url,typeName) {
            issueTypeForm.action=url;
            issueTypeForm.typeName.value=typeName;
            issueTypeForm.submit();
        }
      </script>
</head>
<body bgcolor="#ffffff">
<html:form name="issueTypeForm" action="<%=contextPath+"/issue/typedefine/issueTypeUpdateAction.do"%>" method="post">
  <table bgcolor="ffffff" cellpadding="1" cellspacing="1" border="0" align="left" width="30%" style="padding-left:20px">
        <tr valign="middle">
          <td class="list_range" width="25%" align="right" valign="top" height="20" >&nbsp;Issue Type</td>
          <td class="list_range" width="35%" align="left" valign="top">
                <html:text beanName="oneVB" name="typeName" fieldtype="text" styleId="typeName" next="sequence" req="true" readonly="true" maxlength="100"/>
          </td>
          <td class="list_range" valign="top" width="10%" align="right">Sequence</td>
          <td class="list_range" valign="top" width="5%">
                <html:text beanName="oneVB" name="sequence" fieldtype="number" fmt="4.0" styleId="sequence" next="status"  prev="typeName"  req="true"  />
          </td>
          <!--
          <td class="list_range" valign="top" width="25%">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
          -->
      </tr>
        <tr>
            <td class="list_range" width="25%" valign="top" align="right" height="20">&nbsp;Status</td>
            <td class="list_range" colspan="4" valign="top" align="left">
             <logic:equal name="status" value="">
             <input type="radio" name="rst" value="N">Enable
             &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="rst" value="X">Disable
             </logic:equal>
             <logic:equal name="status" value="N">
             <input type="radio" name="rst" value="N" checked="checked">Enable
             &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="rst" value="X">Disable
             </logic:equal>
             <logic:equal name="status" value="X">
             <input type="radio" name="rst" value="X">Enable
             &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="rst" value="X" checked="checked">Disable
             </logic:equal>
            </td>
        </tr>
        <tr>
            <td colspan="5">
                <table width="100%" cellpadding="0" cellspacing="0">
                    <tr>
                        <td class="list_range" width="130" valign="top" align="right">&nbsp;Save Status History</td>
                        <td class="list_range" valign="top" align="left">
                            <html:checkbox name="saveStatusHistory" beanName="oneVB" checkedValue="Y" defaultValue="Y" uncheckedValue="N"/>
                        </td>
                        <td class="list_range" width="130" valign="top" align="right">Save Influence History</td>
                        <td class="list_range" valign="top" align="left">
                            <html:checkbox name="saveInfluenceHistory" beanName="oneVB" checkedValue="Y" defaultValue="Y" uncheckedValue="N"/>
                        </td>
                    </tr>
                    <!--
                    <tr>
                        <td class="list_range" valign="top" align="right">&nbsp;&nbsp;&nbsp;</td>
                        <td class="list_range" valign="top" align="left">
                           &nbsp;&nbsp;&nbsp;
                        </td>
                    </tr>
                    -->
                </table>
            </td>
        </tr>

        <tr>
           <td class="list_range" valign="top" height="40">&nbsp;Description</td>
           <td colspan="3" valign="top" class="list_range" >
                <html:textarea beanName="oneVB" name="description" styleId="remark" rows="3" maxlength="500" prev="status" cols="44" value=""></html:textarea>
          </td>
          <td class="list_range" valign="top" width="25%">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
        </tr>

</table>
</html:form>
</body>
</html>
