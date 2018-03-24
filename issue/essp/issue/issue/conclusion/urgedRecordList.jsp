<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="itf.hr.HrFactory,c2s.essp.common.user.DtoUser"%>
<%@include file="/inc/pagedef.jsp"%>
<bean:define id="contextPath" value="<%=request.getContextPath()%>"/>
<bean:define id="selectedRow" name="webVo" property="selectedRow" />



<html>
<head>
<tiles:insert page="/layout/head.jsp">
  <tiles:put name="title" value="Urged Record"/>
  <tiles:put name="jspName" value=""/>
</tiles:insert>

<style type="text/css">
.select_account {width:180;}
.select_type {width:150;}
.select_status {width:150;}
.oracolumntextheader {
	BACKGROUND-COLOR: #94aad6;
	color: #FFFFFF;
	font-family: Arial, Helvetica, sans-serif;
	font-size: 12px;
	font-style: normal;
	font-weight: bold;
	text-align: left;
    cursor:hand;
}

.oracolumnnumberheader {
	BACKGROUND-COLOR: #94aad6;
	color: #FFFFFF;
	font-family: Arial, Helvetica, sans-serif;
	font-size: 12px;
	font-style: normal;
	font-weight: bold;
	text-align: right;
    cursor:hand;
}

/* standard link behaviors */
a:visited, a:active
{
	color: #800080;
	text-decoration: underline;
}
a:hover,
{
	color: #ff6600;
	text-decoration: underline;
}

</style>
<script language="javaScript" type="text/javascript">
    var currentRow= ""+"<%=(String)selectedRow%>";
    var currentRowObj;
    var currentRowId = null;
    var urgeRid;


function onclickRow(rowObj){
    try{
        currentRowObj = rowObj;
        urgeRid= currentRowObj.rid;
        issueRid=currentRowObj.issueRid;
    }catch( ex ){
       // alert( "onclickRow " + ex );
    }
}

    function onDbClickRow(rowObj){
     try{
    	currentRowObj = rowObj;
    	//alert("doubleClick");
        onEditData();
        //var option = "Top=200,Left=300,Height=310,Width=500,toolbar=no,scrollbars=no,status=yes";
    	//window.open("<%=contextPath%>/issue/issue/conclusion/urgeUpdatePre.do?rid=<bean:write name="webVo" property="rid"/>","",option);
    }catch( ex ){
    }
    }

	function onAddData(){
    var option = "Top=200,Left=300,Height=310,Width=500,toolbar=no,scrollbars=no,status=yes";
    window.open("<%=contextPath%>/issue/issue/conclusion/urgeAddPre.do?rid=<bean:write name="webVo" property="rid"/>","",option);
}
    function onEditData(){

    	var option = "Top=200,Left=300,Height=310,Width=500,toolbar=no,scrollbars=no,status=yes";
        //var editUrl="<%=contextPath%>/issue/issue/conclusion/urgeUpdatePre.do?rid="+urgeRid;
        //alert(editUrl);
        <logic:equal name="isPrincipal" value="Principal">
         if(currentRowObj==null){
            alert("Please select a row first!");
        }
        else
    	window.open("<%=contextPath%>/issue/issue/conclusion/urgeUpdatePre.do?rid="+urgeRid,"",option);
        </logic:equal>

}

    function onDeleteData(){
        if(currentRowObj==null){
            alert("Please select a row first!");
        }
        else if(confirm("Do you want to delete this row?")){
        window.location="<%=contextPath%>/issue/issue/conclusion/IssueConclusionUrgeDelete.do?rid="+urgeRid+"&issueRid="+issueRid;
        }
    }




</script></head>
<body>
    <html:tabpanel id="UrgedRecord" width="100%">
  <html:tabtitles>
     <html:tabtitle selected="true" width="100">
      <center><span class="tabs_title">Urged Record</span></center>
    </html:tabtitle>
  </html:tabtitles>
        <html:tabbuttons>
    <logic:equal name="isPrincipal" value="Principal">
      <input type="button" name="addBtn" style="width:50px" value='Add' class="button" onClick="onAddData()"/>
      <input type="button" name="addBtn" style="width:50px" value='Edit' class="button" onClick="onEditData()"/>
      <input type="button" name="deleteBtn" style="width:50px" value='Delete' class="button" onClick="onDeleteData()"/>
    </logic:equal>
    <logic:notEqual name="isPrincipal" value="Principal">
        <logic:equal name="isexecutor" value="Assign TO">
               <input type="button" name="addBtn" style="width:50px" value='Add' class="button" onClick="onAddData()"/>
               <input type="button" name="addBtn" style="width:50px" value='Edit' class="button" onClick="onEditData()"/>
               <input type="button" name="deleteBtn" style="width:50px" value='Delete' class="button" onClick="onDeleteData()"/>
        </logic:equal>
    </logic:notEqual>

  </html:tabbuttons>
  <html:tabcontents>
    <html:tabcontent styleClass="wind">
  <html:outborder width="100%" height="280">
<html:table styleId="tableStyle">

  <html:tablehead styleId="tableTitleStyle">
    <html:tabletitle width="100" styleId="oracolumntextheader" toolTip="Urged by">Urged by</html:tabletitle>
    <html:tabletitle width="100" styleId="oracolumntextheader" toolTip="Urge to">Urge to</html:tabletitle>
    <html:tabletitle width="100" styleId="oracolumntextheader" toolTip="Date">Date</html:tabletitle>
    <html:tabletitle width="180" styleId="oracolumntextheader" toolTip="Description">Description</html:tabletitle>
    <html:tabletitle width="*" styleId="oracolumntextheader" toolTip="Attachment">Attachment</html:tabletitle>
  </html:tablehead>
  <html:tablebody styleId="tableDataStyle" height="100%" id="listTable">
    <bean:define id="onClickEvent" value=""/>
    <bean:define id="onDblClickEvent" value=""/>
  <%
    onClickEvent = "onclickRow(this);";
    onDblClickEvent = "onDbClickRow(this);";
  %>

    <logic:present name="webVo">
        <logic:notEmpty name="webVo">
        <logic:iterate name="webVo" property="urgeList" id="oneBean" indexId="indexId">
                  <bean:define id="dataCss" value=""/>
                  <bean:define id="selfProperty" value=""/>
                  <bean:define id="conclusionUrgeRid" name="oneBean" property="rid"/>
                  <bean:define id="issueRid" name="oneBean" property="issueRid"/>
                  <%
					selfProperty = " rid='" + conclusionUrgeRid + "' "+";"+ " issueRid='" + issueRid+ "' ";
                  %>
                  <% String sId = "tr"+indexId.intValue(); %>
                  <html:tablerow id="<%=sId%>"
                      oddClass="tableDataOdd"
                      evenClass="tableDataEven"
                      onclick="onclickRow(this);"
                      ondblclick="onDbClickRow(this);"
                      height="18px"
                      selfProperty = "<%=selfProperty%>"
                      canSelected="true"
                      >
                    <bean:define id="ttUrgedBy" name="oneBean" property="urgedBy"/>
                    <bean:define id="ttUrgedByScope" name="oneBean" property="urgedByScope"/>
                    <%
                       String ttUrgedByName = "";
                       if(DtoUser.USER_TYPE_EMPLOYEE.equals(ttUrgedByScope)) {
                         ttUrgedByName = HrFactory.create().getName((String)ttUrgedBy);
                       } else {
                         ttUrgedByName = HrFactory.create().getCustomerName((String)ttUrgedBy);
                       }
                    %>
                    <html:tablecolumn styleId="oracelltext" toolTip="<%=ttUrgedBy+"/"+ttUrgedByName%>">
                     <%=ttUrgedByName%>
                    </html:tablecolumn>
                    <bean:define id="ttUrgeTo" name="oneBean" property="urgeTo"/>
                    <bean:define id="ttUrgeToScope" name="oneBean" property="urgeToScope"/>
                    <%
                       String ttUrgedToName = "";
                       if(DtoUser.USER_TYPE_EMPLOYEE.equals(ttUrgeToScope)) {
                         ttUrgedToName = HrFactory.create().getName((String)ttUrgeTo);
                       } else {
                         ttUrgedToName = HrFactory.create().getCustomerName((String)ttUrgeTo);
                       }
                    %>
                    <html:tablecolumn styleId="oracelltext" toolTip="<%=ttUrgeTo+"/"+ttUrgedToName%>">
                      <%=ttUrgedToName%>
                    </html:tablecolumn>
                    <bean:define id="ttUrgDate" name="oneBean" property="urgDate"/>
                    <html:tablecolumn styleId="oracellnumber" toolTip="<%=String.valueOf(ttUrgDate)%>">
                      <bean:write name="oneBean" property="urgDate"/>
                    </html:tablecolumn>
                    <bean:define id="ttDescription" name="oneBean" property="description"/>
                    <html:tablecolumn styleId="oracelltext" toolTip="<%=String.valueOf(ttDescription)%>">
                      <bean:write name="oneBean" property="description"/>
                    </html:tablecolumn>
                     <bean:define id="ttAttachment" name="oneBean" property="attachmentdesc"/>
                    <html:tablecolumn styleId="oracelltext" toolTip="<%=String.valueOf(ttAttachment)%>">
                      <bean:define id="downloadUrl" name="oneBean" property="attachment"/>
              <logic:notEmpty name="downloadUrl"><a href="<%=downloadUrl%>"><img  alt="" style="border:0;height:14" src="<%=contextPath+"/image/download.gif"%>"/></a></logic:notEmpty><bean:write name="oneBean" property="attachmentdesc"/>
                    </html:tablecolumn>
                  </html:tablerow>

                </logic:iterate>
                </logic:notEmpty>
    </logic:present>

 </html:tablebody>

</html:table>
</html:outborder>
<table width="100%" cellpadding="0" cellspacing="0">
    <tr><td height="3"></td></tr>
    <tr><td align="right"><html:button value="Close" name="close" styleId="button"  onclick="window.close();"/></td></tr>
</table>
 </html:tabcontent>
  </html:tabcontents>
</html:tabpanel>

</body>
</html>
