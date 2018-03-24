<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/inc/pagedef.jsp"%>
<%@ page import="server.essp.projectpre.service.constant.Constant" %>
<bean:define id="contextPath" value="<%=request.getContextPath()%>" />



<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<tiles:insert page="/layout/head.jsp">
			<tiles:put name="title" value="Project Code Check" />
			<tiles:put name="jspName" value="" />
		</tiles:insert>
	</head>
	
	
	<script language="JavaScript" type="text/javascript">
     var masterProject ="<bean:message bundle="projectpre" key="projectCode.MasterApp"/>";
     var subProject ="<bean:message bundle="projectpre" key="projectCode.SubApp"/>";
     var relatedProject ="<bean:message bundle="projectpre" key="projectCode.RelationAp"/>";
     var finaceProject = "<bean:message bundle="projectpre" key="projectCode.FinanceApp"/>";
     var projectChange ="<bean:message bundle="projectpre" key="projectCode.ProjectAlter"/>";
     var projectClose ="<bean:message bundle="projectpre" key="projectCode.projectClose"/>";
     var currentRow;
     //var contractProject ="<bean:message bundle="projectpre" key="projectCode.ContractAlter"/>";
      function setTypeName(){
      try{
      var rows=document.getElementById("appCheckList_table").rows;
      for(i=1;i<rows.length;i++){
          var tempText=rows[i].cells[2].innerHTML;
          if(tempText.indexOf("<%=Constant.MASTERPROJECTADDCONFIRM%>")>=0){
             rows[i].cells[2].innerHTML=rows[i].cells[2].innerHTML.replace("<%=Constant.MASTERPROJECTADDCONFIRM%>",masterProject);
             rows[i].cells[2].title=rows[i].cells[2].title.replace("<%=Constant.MASTERPROJECTADDCONFIRM%>",masterProject);
          } else if(tempText.indexOf("<%=Constant.SUBPROJECTADDCONFIRM%>")>=0){
             rows[i].cells[2].innerHTML=rows[i].cells[2].innerHTML.replace("<%=Constant.SUBPROJECTADDCONFIRM%>",subProject);
             rows[i].cells[2].title=rows[i].cells[2].title.replace("<%=Constant.SUBPROJECTADDCONFIRM%>",subProject);
          } else if(tempText.indexOf("<%=Constant.RELATEDPROJECTADDCONFIRM%>")>=0){
             rows[i].cells[2].innerHTML=rows[i].cells[2].innerHTML.replace("<%=Constant.RELATEDPROJECTADDCONFIRM%>",relatedProject);
             rows[i].cells[2].title=rows[i].cells[2].title.replace("<%=Constant.RELATEDPROJECTADDCONFIRM%>",relatedProject);
          } else if(tempText.indexOf("<%=Constant.FINANCEPROJECTADDCONFIRM%>")>=0){
              rows[i].cells[2].innerHTML=rows[i].cells[2].innerHTML.replace("<%=Constant.FINANCEPROJECTADDCONFIRM%>",finaceProject);
             rows[i].cells[2].title=rows[i].cells[2].title.replace("<%=Constant.FINANCEPROJECTADDCONFIRM%>",finaceProject);
          } else if(tempText.indexOf("<%=Constant.PROJECTCHANGECONFIRM%>")>=0){
             rows[i].cells[2].innerHTML=rows[i].cells[2].innerHTML.replace("<%=Constant.PROJECTCHANGECONFIRM%>",projectChange);
             rows[i].cells[2].title=rows[i].cells[2].title.replace("<%=Constant.PROJECTCHANGECONFIRM%>",projectChange);
          } else if(tempText.indexOf("<%=Constant.PROJECTCLOSECONFIRM%>")>=0){
             rows[i].cells[2].innerHTML=rows[i].cells[2].innerHTML.replace("<%=Constant.PROJECTCLOSECONFIRM%>",projectClose);
             rows[i].cells[2].title=rows[i].cells[2].title.replace("<%=Constant.PROJECTCLOSECONFIRM%>",projectClose);
          } 
      
      }
      }catch(e){}
   }
   function onCheckData(){
    try{
   //alert(currentRow);
    var type = currentRow.cells[2].innerHTML;
    var rid = currentRow.cells[0].innerText;
    var acntId = currentRow.cells[3].innerText;
     if(type==masterProject || type==relatedProject || type==subProject || type==finaceProject){
        var height = 510;
        var width = 850;
        var topis = (screen.height - height) / 2;
        var leftis = (screen.width - width) / 2;
        var option = "height=" + height + "px"  +", width=" + width + "px" +", top=" + topis + "px" +", left=" + leftis + "px"  +", toolbar=no, menubar=no, scrollbars=no, resizable=yes,location=no, status=no";
		var url="<%=request.getContextPath()%>/projectpre/projectcode/codeapply/PopCodeApply.jsp?appType=check&rid="+rid;
		var windowTitle="";
        window.open(url,windowTitle,option);
       }
 
       if(type==projectChange){
	    var height = 550;
        var width = 788;
        var topis = (screen.height - height) / 2;
        var leftis = (screen.width - width) / 2;
        var option = "height=" + height + "px"  +", width=" + width + "px" +", top=" + topis + "px" +", left=" + leftis + "px"  +", toolbar=no, menubar=no, scrollbars=yes, resizable=yes,location=no, status=no";
		var url="<%=request.getContextPath()%>/project/check/previewCheckProjectChange.do?rid="+rid;
		var windowTitle="";
        window.open(url,windowTitle,option);
        }
         if(type==projectClose){
	    var height = 450;
        var width = 850; 
        var topis = (screen.height - height) / 2;
        var leftis = (screen.width - width) / 2;
        var option = "height=" + height + "px"  +", width=" + width + "px" +", top=" + topis + "px" +", left=" + leftis + "px"  +", toolbar=no, menubar=no, scrollbars=no, resizable=yes,location=no, status=no";
		var url="<%=request.getContextPath()%>/projectpre/projectcode/codeapply/PopCodeApply.jsp?appType=check&confirmCheck=true&acntId="+acntId;
		var windowTitle="";
        window.open(url,windowTitle,option);
        }  
        }catch(e){} 
   }
     function onCodeCompare(obj){
      try{
       var i = obj.rowIndex;
       //alert(i);
       var cells=document.getElementById("appCheckList_table").rows[i].cells;
       var type = cells[2].innerHTML;
       var rid = cells[0].innerText;
       var acntId = cells[3].innerText;
       //alert(type);
   
        if(type==masterProject || type==relatedProject || type==subProject || type==finaceProject){
        var height = 510;
        var width = 850;
        var topis = (screen.height - height) / 2;
        var leftis = (screen.width - width) / 2;
        var option = "height=" + height + "px"  +", width=" + width + "px" +", top=" + topis + "px" +", left=" + leftis + "px"  +", toolbar=no, menubar=no, scrollbars=no, resizable=yes,location=no, status=no";
		var url="<%=request.getContextPath()%>/projectpre/projectcode/codeapply/PopCodeApply.jsp?appType=check&rid="+rid;
		var windowTitle="";
        window.open(url,windowTitle,option);
       }
 
       if(type==projectChange){
	    var height = 550;
        var width = 788;
        var topis = (screen.height - height) / 2;
        var leftis = (screen.width - width) / 2;
        var option = "height=" + height + "px"  +", width=" + width + "px" +", top=" + topis + "px" +", left=" + leftis + "px"  +", toolbar=no, menubar=no, scrollbars=yes, resizable=yes,location=no, status=no";
		var url="<%=request.getContextPath()%>/project/check/previewCheckProjectChange.do?rid="+rid;
		var windowTitle="";
        window.open(url,windowTitle,option);
        }
         if(type==projectClose){
	    var height = 450;
        var width = 850; 
        var topis = (screen.height - height) / 2;
        var leftis = (screen.width - width) / 2;
        var option = "height=" + height + "px"  +", width=" + width + "px" +", top=" + topis + "px" +", left=" + leftis + "px"  +", toolbar=no, menubar=no, scrollbars=no, resizable=yes,location=no, status=no";
		var url="<%=request.getContextPath()%>/projectpre/projectcode/codeapply/PopCodeApply.jsp?appType=check&confirmCheck=true&acntId="+acntId;
		var windowTitle="";
        window.open(url,windowTitle,option);
        }   
        
        }catch(e){}  
    }
     function disableBtn(){
	
	  try{
	  
	   var table=document.getElementById("appCheckList_table");
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
    //alert("ok");
    window.parent.checkBtn.disabled=true;
    return;
    }
	currentRow=rowObj;
	}catch(e){}
	}
    //当进入此页时，自动选中第一行，并国际化表头
	function autoClickFirstRow(){
	   try{
	    var table=document.getElementById("appCheckList_table");
	    
        var str0="<bean:message bundle="projectpre" key="projectCode.MasterData.ApplyNo"/>";
		var str1="<bean:message bundle="projectpre" key="projectCode.CodeCheckDetail.Applicant"/>";
		var str2="<bean:message bundle="projectpre" key="projectCode.CodeCheckDetail.ProcessType"/>";
		var str3="<bean:message bundle="projectpre" key="projectCode.MasterData.ProjectCode"/>";
        var str4="<bean:message bundle="projectpre" key="projectCode.CodeCheckDetail.Discription"/>";
        var str5="<bean:message bundle="projectpre" key="projectCode.ApplyRecordList.ApplyDate"/>";
        
        
		var thead_length=table.tHead.rows.length;
		var tds=table.rows[thead_length-1];
		var cells=tds.cells;
		//使EC标签的表头国际化
		cells[0].innerHTML=cells[0].innerHTML.replace("Apply No",str0);
		cells[0].title = cells[0].title.replace("Apply No",str0);
		cells[1].innerHTML=cells[1].innerHTML.replace("Applicant",str1);
		cells[1].title = cells[1].title.replace("Applicant",str1);
		cells[2].innerHTML=cells[2].innerHTML.replace("Process Type",str2);
		cells[2].title = cells[2].title.replace("Process Type",str2);
		cells[3].innerHTML=cells[3].innerHTML.replace("Acnt Id",str3);
		cells[3].title = cells[3].title.replace("Acnt Id",str3);
		cells[4].innerHTML=cells[4].innerHTML.replace("Acnt Name",str4);
		cells[4].title = cells[4].title.replace("Acnt Name",str4);
		cells[5].innerHTML=cells[5].innerHTML.replace("Submit Date",str5);
		cells[5].title = cells[5].title.replace("Submit Date",str5);
		
		 var firstRow=table.rows[thead_length];
		rowOnClick(firstRow);
	    if(table.rows.length>thead_length){
	       //如果有数据
	      
		   onChangeBackgroundColor(firstRow);
	    }
	    }catch(e){}
	}
	
  
   </script>
	<body >
  <%
	java.util.Locale locale = (java.util.Locale)session.getAttribute(org.apache.struts.Globals.LOCALE_KEY); 	
  	String _language = locale.toString();	
  %>
			<ec:table 
			    tableId="appCheckList"
			    items="webVo" 
			    var="aCheck" scope="request" 
			    action="${pageContext.request.contextPath}/project/check/listProjectAppCheck.do" 
				imagePath="${pageContext.request.contextPath}/image/table/compact/*.gif" 
			    locale="<%=_language%>"
			>
				<ec:row onclick="rowOnClick(this);" ondbclick="onCodeCompare(this)" selfproperty="${aCheck.rid}">
				    <ec:column style="width:15%" property="rid" tooltip="${aCheck.rid}" title="Apply No"/>	
					<ec:column style="width:15%" property="applicantName" tooltip="${aCheck.applicantName}"  title="Applicant"/>			
					<ec:column style="width:20%" property="acntType" tooltip="${aCheck.acntType}" title="Process Type"/>
					<ec:column style="width:20%" property="acntId" tooltip="${aCheck.acntId}" title="Acnt Id"/>			
					<ec:column style="width:30%" property="acntName" tooltip="${aCheck.acntName}" title="Acnt Name"/>							
					<ec:column style="width:20%" property="applicationDate" tooltip="${aCheck.applicationDate}" title="Submit Date"/>
				</ec:row>
			</ec:table>
	
	</body>
	 <script language="javascript" type="text/javascript">
        autoClickFirstRow();
        setTypeName();
     </script>
</html>
