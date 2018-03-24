<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>
<%@ page import="server.framework.taglib.util.SelectOptionImpl,java.util.ArrayList" %>
<%
//	ArrayList optionList=new ArrayList();
//	SelectOptionImpl option=new SelectOptionImpl("NORMAL","NORMAL");
//	optionList.add(option);
//	option=new SelectOptionImpl("NORMAL2","NORMAL2");
//	optionList.add(option);
//	request.setAttribute("priorityOptions",optionList);
//	request.setAttribute("prioritySelectbox","testfrm.priority");
%>

<html>
<head>
<script language="javascript">
function updateOptionList() {
    <%-- ˢ��prioritySelectbox --%>
    <logic:present name="prioritySelectbox">
	    <bean:define id="priorityOptions" name="priorityOptions"/>
	    <bean:define id="prioritySelectbox" name="prioritySelectbox"/>
	    var labels=new Array();
	    var values=new Array();
		<logic:iterate id="optionItem" name="priorityOptions" indexId="currentIndex">
		labels[<%=currentIndex%>]="<bean:write name="optionItem" property="label"/>";
		values[<%=currentIndex%>]="<bean:write name="optionItem" property="value"/>";
		</logic:iterate>
        try{
            parent.updateOptionList("<%=prioritySelectbox%>",labels,values);
        } catch(e) {
        }
	</logic:present>

	<%-- scopeSelectbox --%>
	<logic:present name="scopeSelectbox">
	    <bean:define id="scopeOptions" name="scopeOptions"/>
	    <bean:define id="scopeSelectbox" name="scopeSelectbox"/>
	    var labels2=new Array();
	    var values2=new Array();
		<logic:iterate id="optionItem" name="scopeOptions" indexId="currentIndex">
		labels2[<%=currentIndex%>]="<bean:write name="optionItem" property="label"/>";
		values2[<%=currentIndex%>]="<bean:write name="optionItem" property="value"/>";
		</logic:iterate>
        try{
            parent.updateOptionList("<%=scopeSelectbox%>",labels2,values2);
        } catch(e) {
        }
	</logic:present>

	<%-- statusSelectbox --%>
	<logic:present name="statusSelectbox">
	    <bean:define id="statusOptions" name="statusOptions"/>
	    <bean:define id="statusSelectbox" name="statusSelectbox"/>
	    var labels3=new Array();
	    var values3=new Array();
		<logic:iterate id="optionItem" name="statusOptions" indexId="currentIndex">
		labels3[<%=currentIndex%>]="<bean:write name="optionItem" property="label"/>";
		values3[<%=currentIndex%>]="<bean:write name="optionItem" property="value"/>";
		</logic:iterate>
        try{
            parent.updateOptionList("<%=statusSelectbox%>",labels3,values3);
        } catch(e) {
        }
	</logic:present>

    try {
        parent.onScopeChanged();
    } catch(e) {
    }

    try {
        parent.onChangeDueDate();
    } catch(e) {
    }
}
</script>
</head>
<body onload="updateOptionList()">
</body>
</html>
