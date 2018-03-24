<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="/inc/pagedef.jsp"%>
<html>
<head>
<tiles:insert page="/layout/head.jsp">
  <tiles:put name="title" value=" "/>
  <tiles:put name="jspName" value=""/>
</tiles:insert>
</head>
<body bgcolor="#ffffff">
<script language="javaScript">
    var checkValue="<bean:write name="issueDuplation"/>";
    try {
        var currentForm;
        var formName;
        if(parent.issueForm) {
            currentForm=parent.issueForm;
            formName="document.issueForm";
        } else {
            formName="document.issueUpdate";
            currentForm=parent.issueUpdate;
        }
        currentForm.duplationIssue.disabled=false;

        <logic:notEmpty  name="duplationIssueList">
            var labels=new Array();
	        var values=new Array();
            <logic:iterate id="optionItem" name="duplationIssueList" indexId="currentIndex">
            labels[<%=currentIndex%>]="<bean:write name="optionItem" property="label"/>";
            values[<%=currentIndex%>]="<bean:write name="optionItem" property="value"/>";
            </logic:iterate>
            parent.updateOptionList(formName+".duplationIssue",labels,values);
            parent.setSelectedValue(formName+".duplationIssue","");
        </logic:notEmpty>
        if(checkValue=="ENABLED") {
            currentForm.duplationIssue.disabled=false;
        } else {
            currentForm.duplationIssue.disabled=true;
        }

        try {
            if(checkValue=="ENABLED") {
               currentForm.btnOK.disabled=false;
            } else {
               currentForm.btnOK.disabled=true;
            }
        } catch(e1) {
        }
    } catch(e) {
        alert(e);
    }
    </script></body>
</html>
