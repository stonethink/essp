
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/inc/pagedef.jsp"%>
<%@ page import="server.essp.projectpre.service.constant.Constant" %>
<bean:define id="contextPath" value="<%=request.getContextPath()%>" />
 

<script language="JavaScript" type="text/JavaScript">
var currentRow=null;
  var deptAdd ="<bean:message bundle="projectpre" key="deptCode.DeptAdd"/>";
  var deptChange ="<bean:message bundle="projectpre" key="deptCode.DeptChange"/>";
  function setTypeName(){
   try{
   var rows=document.getElementById("deptListCheck_table").rows;
   for(i=1;i<rows.length;i++){
          var tempText=rows[i].cells[2].innerHTML;
          if(tempText.indexOf("<%=Constant.DEPARTMENTADDAPP%>")>=0){
             rows[i].cells[2].innerHTML=rows[i].cells[2].innerHTML.replace("<%=Constant.DEPARTMENTADDAPP%>",deptAdd);
             rows[i].cells[2].title=rows[i].cells[2].title.replace("<%=Constant.DEPARTMENTADDAPP%>",deptAdd);
          } else if(tempText.indexOf("<%=Constant.DEPARTMENTCHANGEAPP%>")>=0){
             rows[i].cells[2].innerHTML=rows[i].cells[2].innerHTML.replace("<%=Constant.DEPARTMENTCHANGEAPP%>",deptChange);
             rows[i].cells[2].title=rows[i].cells[2].title.replace("<%=Constant.DEPARTMENTCHANGEAPP%>",deptChange);
          }
   }
   }catch(e){}
  }
  
  function onCheckData(){
      try{
       
       var type = currentRow.cells[2].innerText;
       if(type==deptAdd){
        var height = 360;
        var width = 750;
        var topis = (screen.height - height) / 2;
        var leftis = (screen.width - width) / 2;
        var option = "height=" + height + "px"  +", width=" + width + "px" +", top=" + topis + "px" +", left=" + leftis + "px"  +", toolbar=no, menubar=no, scrollbars=no, resizable=yes,location=no, status=no";
		var url="<%=request.getContextPath()%>/dept/check/initialAddApp.do?rid="+currentRow.selfproperty;
		var windowTitle="";
        window.open(url,windowTitle,option);
       }
        if(type==deptChange){
        var height = 500;
        var width = 760;
        var topis = (screen.height - height) / 2;
        var leftis = (screen.width - width) / 2;
        var option = "height=" + height + "px"  +", width=" + width + "px" +", top=" + topis + "px" +", left=" + leftis + "px"  +", toolbar=no, menubar=no, scrollbars=no, resizable=yes,location=no, status=no";
		var url="<%=request.getContextPath()%>/dept/change/initialAlterApp.do?rid="+currentRow.selfproperty;
		var windowTitle="";
        window.open(url,windowTitle,option);
       }
       }catch(e){}
  }
  function disableBtn(){
	
	  try{
	  
	   var table=document.getElementById("deptListCheck_table");
	   var thead_length=table.tHead.rows.length;
	   var firstRow=table.rows[thead_length];
	  // alert(table);
	   if(firstRow==null){
	     window.parent.checkBtn.disabled=true;
	     
	   }else{
	     window.parent.checkBtn.disabled=false;
	     
	   }
	   }catch(e){}
	}
     //单击行时的动作，更新下帧中的数据
   function rowOnClick(rowObj) {
    try{
	if(rowObj==null){
	  window.parent.checkBtn.disabled=true;
		return;
	}
	currentRow=rowObj;
	}catch(e){}
   }
  
  
  function showInfomation(obj){
     try{
       var i = obj.rowIndex;
       var cells=document.getElementById("deptListCheck_table").rows[i].cells;
       var type = cells[2].innerText;
       if(type==deptAdd){
        var height = 360;
        var width = 750;
        var topis = (screen.height - height) / 2;
        var leftis = (screen.width - width) / 2;
        var option = "height=" + height + "px"  +", width=" + width + "px" +", top=" + topis + "px" +", left=" + leftis + "px"  +", toolbar=no, menubar=no, scrollbars=no, resizable=yes,location=no, status=no";
		var url="<%=request.getContextPath()%>/dept/check/initialAddApp.do?rid="+currentRow.selfproperty;
		var windowTitle="";
        window.open(url,windowTitle,option);
       }
        if(type==deptChange){
        var height = 500;
        var width = 760;
        var topis = (screen.height - height) / 2;
        var leftis = (screen.width - width) / 2;
        var option = "height=" + height + "px"  +", width=" + width + "px" +", top=" + topis + "px" +", left=" + leftis + "px"  +", toolbar=no, menubar=no, scrollbars=no, resizable=yes,location=no, status=no";
		var url="<%=request.getContextPath()%>/dept/change/initialAlterApp.do?rid="+currentRow.selfproperty;
		var windowTitle="";
        window.open(url,windowTitle,option);
       }
       }catch(e){}
  }
  


     //当进入此页时，自动选中第一行，并国际化表头
	function autoClickFirstRow(){
	   try{
	    var table=document.getElementById("deptListCheck_table");
	    
        var str0="<bean:message bundle="projectpre" key="projectCode.ApplyRecordList.ApplyNo"/>";
		var str1="<bean:message bundle="projectpre" key="deptCode.Applicant"/>";
		var str2="<bean:message bundle="projectpre" key="deptCode.ProcessType"/>";
		var str3="<bean:message bundle="projectpre" key="projectCode.NickName"/>";
		var str4="<bean:message bundle="projectpre" key="projectCode.ApplyRecordList.ApplyDate"/>";
		
		var thead_length=table.tHead.rows.length;
		var tds=table.rows[thead_length-1];
		var cells=tds.cells;
		//使EC标签的表头国际化
		cells[0].innerHTML=cells[0].innerHTML.replace("Apply No",str0);
		cells[1].innerHTML=cells[1].innerHTML.replace("From",str1);
		cells[2].innerHTML=cells[2].innerHTML.replace("Application Type",str2);
		cells[3].innerHTML=cells[3].innerHTML.replace("Dept Short",str3);
		cells[4].innerHTML=cells[4].innerHTML.replace("Application Date",str4);
		cells[0].title=cells[0].title.replace("Apply No",str0);
		cells[1].title=cells[1].title.replace("From",str1);
		cells[2].title=cells[2].title.replace("Application Type",str2);
		cells[3].title=cells[3].title.replace("Dept Short",str3);
		cells[4].title=cells[4].title.replace("Application Date",str4);
		var firstRow=table.rows[thead_length];
		rowOnClick(firstRow);
	    if(table.rows.length>thead_length){
	       //如果有数据
	     
		   
		   onChangeBackgroundColor(firstRow);
	    }
	    }catch(e){}
	}
</script>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<tiles:insert page="/layout/head.jsp">
			<tiles:put name="title" value=" " />
			<tiles:put name="jspName" value="" />
		</tiles:insert>
	</head>

	<body >
	<%
	java.util.Locale locale = (java.util.Locale)session.getAttribute(org.apache.struts.Globals.LOCALE_KEY); 	
  	String _language = locale.toString();	
  %>
		<ec:table tableId="deptListCheck"
	     items="webVo"
		 var="aCode" scope="request"
		 action="${pageContext.request.contextPath}/dept/check/listAllDeptApp.do" 			
	   	 imagePath="${pageContext.request.contextPath}/image/table/compact/*.gif" 
	   	  locale="<%=_language%>"
	    >
			<ec:row  onclick="rowOnClick(this);" selfproperty="${aCode.rid}" ondbclick="showInfomation(this);">
				<ec:column style="width:20%" property="rid" tooltip="$(aCode.rid)" title="Apply No"/>
				<ec:column style="width:20%" property="applicantName" tooltip="${aCode.applicantName}" title="From" />
				<ec:column style="width:20%" property="applicationType" tooltip="${aCode.applicationType}" title="Application Type"/>
				<ec:column style="width:20%" property="acntName" tooltip="${aCode.acntName}" title="Dept Short" />
				<ec:column style="width:20%" property="applicationDate" tooltip="${aCode.applicationDate}" title="Application Date"/>
			</ec:row>
		</ec:table>
	   <script language="javascript" type="text/javascript">
	         autoClickFirstRow();
             setTypeName();
       </script>
	</body>
	
</html>
