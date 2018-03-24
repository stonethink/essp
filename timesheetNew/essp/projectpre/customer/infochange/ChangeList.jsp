
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/inc/pagedef.jsp"%>
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
  //新增  
   function onAddData(){    
       var height = 420;
       var width = 700;
       var topis = (screen.height - height) / 2;
       var leftis = (screen.width - width) / 2;
       var option = "height=" + height + "px"  +", width=" + width + "px" +", top=" + topis + "px" +", left=" + leftis + "px"  +", toolbar=no, menubar=no, scrollbars=no, resizable=yes,location=no, status=no";
	   var url='<%=contextPath%>/customer/change/initialCust.do';
	   scrolling="yes";
	   var windowTitle="";
       window.open(url,windowTitle,option);

    }
    
    //删除
      function onDeleteData() {   
      var status=currentRow.cells[4].innerText; 
      if(currentRow==null) {
        alert("<bean:message bundle="application" key="global.confirm.selectRow"/>");
        return;
      }  
       if(confirm("<bean:message bundle="application" key="global.confirm.delete"/>")) {
    	window.location="<%=contextPath%>/customer/change/deleteCustApp.do?CODE="+currentRow.selfproperty;          
    }
    }
    
    
     //单击行时的动作，更新下帧中的数据
   function rowOnClick(rowObj) {
	if(rowObj==null){
	 window.parent.onRefreshData("");
	  var obj = window.parent;
	   obj.parent.deleteBtn.disabled=true;
	}else{
	currentRow=rowObj;
	var obj = window.parent;
	var rid=rowObj.selfproperty;
	window.parent.onRefreshData(rid);
	var status = rowObj.cells[5].innerText;

	if(status=="Submitted"){  
	   obj.parent.deleteBtn.disabled=true;
	} else {
	   obj.parent.deleteBtn.disabled=false;
	 }
   }
}

     //当进入此页时，自动选中第一行，并国际化表头
	function autoClickFirstRow(){
	 try{
	    var table=document.getElementById("changeList_table");    
        var str0="<bean:message bundle="projectpre" key="projectCode.ApplyRecordList.ApplyNo"/>";
		var str1="<bean:message bundle="projectpre" key="customer.RegisterNo"/>";
		var str2="<bean:message bundle="projectpre" key="customer.ClientNo"/>";
		var str3="<bean:message bundle="projectpre" key="customer.GroupNo"/>";
		var str4="<bean:message bundle="projectpre" key="projectCode.ApplyRecordList.ApplyDate"/>";
		var str5="<bean:message bundle="projectpre" key="projectCode.MasterData.StatusCode"/>";
		var thead_length=table.tHead.rows.length;
		var tds=table.rows[thead_length-1];
		var cells=tds.cells;
		//使EC标签的表头国际化
		cells[0].innerHTML=cells[0].innerHTML.replace("Apply No",str0);
		cells[1].innerHTML=cells[1].innerHTML.replace("Register No",str1);
		cells[2].innerHTML=cells[2].innerHTML.replace("Customer No",str2);
		cells[3].innerHTML=cells[3].innerHTML.replace("GroupCustomer No",str3);
		cells[4].innerHTML=cells[4].innerHTML.replace("Apply Date",str4);
		cells[5].innerHTML=cells[5].innerHTML.replace("Status Code",str5);
		cells[0].title=cells[0].title.replace("Apply No",str0);
		cells[1].title=cells[1].title.replace("Register No",str1);
		cells[2].title=cells[2].title.replace("Customer No",str2);
		cells[3].title=cells[3].title.replace("GroupCustomer No",str3);
		cells[4].title=cells[4].title.replace("Apply Date",str4);
		cells[5].title=cells[5].title.replace("Status Code",str5);
	    var firstRow=table.rows[thead_length];
	    disableBtn();
	    rowOnClick(firstRow);
	    if(table.rows.length>thead_length){
	       //如果有数据      
		   onChangeBackgroundColor(firstRow);
	    }
	    }catch(e){}
	}
	
	 function disableBtn(){
	  try{
	  var rowObj = currentRow;
	   var table=document.getElementById("changeList_table");
	   var thead_length=table.tHead.rows.length;
	   var firstRow=table.rows[thead_length];
	    var cell="";
	   if(firstRow!=null){
	      cell=rowObj.cells[5].innerText;
	   }
	   if(firstRow==null||cell=="Submitted"){
	     window.parent.parent.deleteBtn.disabled=true;
	     window.parent.saveBtn.disabled=true;
	     window.parent.submitBtn.disabled=true;

	   }else{
	     window.parent.parent.deleteBtn.disabled=false;
	     window.parent.saveBtn.disabled=false;
	     window.parent.submitBtn.disabled=false;
	   }
	   }catch(e){}
	}
	</script>

	</head>
	<body >		
	<%
	java.util.Locale locale = (java.util.Locale)session.getAttribute(org.apache.struts.Globals.LOCALE_KEY); 	
  	String _language = locale.toString();	
  %>
   <ec:table 
       tableId="changeList"
       items="webVo"
       var="aCode" scope="request" 
       action="${pageContext.request.contextPath}/customer/change/listAllCustApp.do"  
       imagePath="${pageContext.request.contextPath}/image/table/compact/*.gif"
       sortable="true"
        locale="<%=_language%>"
       >

        <ec:row onclick="rowOnClick(this);" selfproperty="${aCode.rid}">
             
             <ec:column style="width:18%" property="rid" tooltip="${aCode.rid}" title="Apply No" />
             <ec:column style="width:18%" property="regId" tooltip="${aCode.regId}" title="Register No" />              
             <ec:column style="width:18%" property="customerId" tooltip="${aCode.customerId}" title="Customer No" />
             <ec:column style="width:18%" property="groupId" tooltip="${aCode.groupId}" title="GroupCustomer No" />
             <ec:column style="width:15%" property="applicationDate" tooltip="${aCode.applicationDate}" title="Apply Date" />
             <ec:column style="width:16%" property="applicationStatus" tooltip="${aCode.applicationStatus}" title="Status Code" />
         </ec:row>        
     </ec:table>
     
     <script type="text/javascript" language="javascript">
     autoClickFirstRow();
     </script>
	</body>
	
</html>
