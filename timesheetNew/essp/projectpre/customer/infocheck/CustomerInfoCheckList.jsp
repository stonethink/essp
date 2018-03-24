
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/inc/pagedef.jsp"%>
<%@ page import="server.essp.projectpre.service.constant.Constant" %>
<%
  String contextPath = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
         <tiles:insert page="/layout/head.jsp">
            <tiles:put name="title" value=" "/>
            <tiles:put name="jspName" value=""/>
        </tiles:insert>
		


    <script language="JavaScript" type="text/javascript">
    var currentRow=null;
     var custAdd ="<bean:message bundle="projectpre" key="customer.addApp"/>";
     var custAlter="<bean:message bundle="projectpre" key="customer.alterApp"/>";
    
      function setTypeName(){
      try{
      var rows=document.getElementById("CustInfoCheckList_table").rows;
      for(i=1;i<rows.length;i++){
          var tempText=rows[i].cells[2].innerHTML;
          if(tempText.indexOf("<%=Constant.CUSTOMERADDAPP%>")>=0){
             rows[i].cells[2].innerHTML=rows[i].cells[2].innerHTML.replace("<%=Constant.CUSTOMERADDAPP%>",custAdd);
             rows[i].cells[2].title=rows[i].cells[2].title.replace("<%=Constant.CUSTOMERADDAPP%>",custAdd);
          } else if(tempText.indexOf("<%=Constant.CUSTOMERCHANGEAPP%>")>=0){
             rows[i].cells[2].innerHTML=rows[i].cells[2].innerHTML.replace("<%=Constant.CUSTOMERCHANGEAPP%>",custAlter);
             rows[i].cells[2].title=rows[i].cells[2].title.replace("<%=Constant.CUSTOMERCHANGEAPP%>",custAlter);
          } 
      
      }
      }catch(e){}
   }
    function onOpenWindow(obj){
    try{
        var i = obj.rowIndex;
       var cells=document.getElementById("CustInfoCheckList_table").rows[i].cells;
       var type = cells[2].innerText;
       if(type==custAdd){
        var height = 500;
        var width = 750;
        var topis = (screen.height - height) / 2;
        var leftis = (screen.width - width) / 2;
        var option = "height=" + height + "px"  +", width=" + width + "px" +", top=" + topis + "px" +", left=" + leftis + "px"  +", toolbar=no, menubar=no, scrollbars=yes, resizable=yes,location=no, status=no";
		var url="<%=request.getContextPath()%>/customer/check/checkAdd.do?rid="+currentRow.selfproperty;
		var windowTitle="";
        window.open(url,windowTitle,option);
       }
        if(type==custAlter){
        var height = 530;
        var width = 760;
        var topis = (screen.height - height) / 2;
        var leftis = (screen.width - width) / 2;
        var option = "height=" + height + "px"  +", width=" + width + "px" +", top=" + topis + "px" +", left=" + leftis + "px"  +", toolbar=no, menubar=no, scrollbars=yes, resizable=yes,location=no, status=no";
		var url="<%=request.getContextPath()%>/customer/check/checkChange.do?rid="+currentRow.selfproperty;
		var windowTitle="";
        window.open(url,windowTitle,option);
       }
       }catch(e){}
    }
    
    function onCheckData(){
     
       var type = currentRow.cells[2].innerText;
       if(type==custAdd){
        var height = 500;
        var width = 750;
        var topis = (screen.height - height) / 2;
        var leftis = (screen.width - width) / 2;
        var option = "height=" + height + "px"  +", width=" + width + "px" +", top=" + topis + "px" +", left=" + leftis + "px"  +", toolbar=no, menubar=no, scrollbars=yes, resizable=yes,location=no, status=no";
		var url="<%=request.getContextPath()%>/customer/check/checkAdd.do?rid="+currentRow.selfproperty;
		var windowTitle="";
        window.open(url,windowTitle,option);
       }
        if(type==custAlter){
        var height = 530;
        var width = 760;
        var topis = (screen.height - height) / 2;
        var leftis = (screen.width - width) / 2;
        var option = "height=" + height + "px"  +", width=" + width + "px" +", top=" + topis + "px" +", left=" + leftis + "px"  +", toolbar=no, menubar=no, scrollbars=yes, resizable=yes,location=no, status=no";
		var url="<%=request.getContextPath()%>/customer/check/checkChange.do?rid="+currentRow.selfproperty;
		var windowTitle="";
        window.open(url,windowTitle,option);
       }
    }
  function disableBtn(){
	
	  try{
	  
	   var table=document.getElementById("CustInfoCheckList_table");
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
	if(rowObj==null){
	    window.parent.checkBtn.disabled=true;
		return;
	}
	currentRow=rowObj;
	var code=rowObj.selfproperty;
	window.parent.onRefreshData(code);
   }

     //当进入此页时，自动选中第一行，并国际化表头
	function autoClickFirstRow(){
	    var table=document.getElementById("CustInfoCheckList_table");
	    
        var str0="<bean:message bundle="projectpre" key="projectCode.ApplyRecordList.ApplyNo"/>";
		var str1="<bean:message bundle="projectpre" key="deptCode.Applicant"/>";
		var str2="<bean:message bundle="projectpre" key="deptCode.ProcessType"/>";
		var str3="<bean:message bundle="projectpre" key="customer.ClientShortName"/>";
		var str4="<bean:message bundle="projectpre" key="customer.RegisterNo"/>";
		var str5="<bean:message bundle="projectpre" key="projectCode.ApplyRecordList.ApplyDate"/>";

		var thead_length=table.tHead.rows.length;
		var tds=table.rows[thead_length-1];
		var cells=tds.cells;
		//使EC标签的表头国际化
		cells[0].innerHTML=cells[0].innerHTML.replace("Apply No",str0);
		cells[1].innerHTML=cells[1].innerHTML.replace("From",str1);
		cells[2].innerHTML=cells[2].innerHTML.replace("Process Type",str2);
		cells[3].innerHTML=cells[3].innerHTML.replace("Cust Short Name",str3);
		cells[4].innerHTML=cells[4].innerHTML.replace("Register No",str4);
		cells[5].innerHTML=cells[5].innerHTML.replace("Application Date",str5);
		cells[0].title=cells[0].title.replace("Apply No",str0);
		cells[1].title=cells[1].title.replace("From",str1);
		cells[2].title=cells[2].title.replace("Process Type",str2);
		cells[3].title=cells[3].title.replace("Cust Short Name",str3);
		cells[4].title=cells[4].title.replace("Register No",str4);
		cells[5].title=cells[5].title.replace("Application Date",str5);
		 var firstRow=table.rows[thead_length];
		   rowOnClick(firstRow);
	    if(table.rows.length>thead_length){
	       //如果有数据
	      
		   onChangeBackgroundColor(firstRow);
	    }
	}
	</script>

	</head>
	<body>
	<%
	java.util.Locale locale = (java.util.Locale)session.getAttribute(org.apache.struts.Globals.LOCALE_KEY); 	
  	String _language = locale.toString();	
   %>	
   <ec:table 
        tableId="CustInfoCheckList"
       items="webVo"
       var="aCode"  scope="request"
       action="${pageContext.request.contextPath}/customer/check/listAllApp.do"  
       imagePath="${pageContext.request.contextPath}/image/table/compact/*.gif"
        locale="<%=_language%>"
       >
        <ec:row onclick="rowOnClick(this);" selfproperty="${aCode.rid}" ondbclick="onOpenWindow(this);">
             <ec:column style="width:15%" property="rid" tooltip="${aCode.rid}" title="Apply No"/>
             <ec:column style="width:15%" property="applicantName" tooltip="${aCode.applicantName}" title="From"/>
             <ec:column style="width:20%" property="applicationType" tooltip="${aCode.applicationType}" title="Process Type"/>
             <ec:column style="width:15%" property="customerShort" tooltip="${aCode.customerShort}" title="Cust Short Name"/>
             <ec:column style="width:15%" property="regId" tooltip="${aCode.regId}" title="Register No"/>
             <ec:column style="width:20%" property="applicationDate" tooltip="${aCode.applicationDate}" title="Application Date"/>      
         </ec:row>        
     </ec:table>
	</body>
	<script language="javascript" type="text/javascript">        
        autoClickFirstRow();
        setTypeName();
     </script>
</html>
