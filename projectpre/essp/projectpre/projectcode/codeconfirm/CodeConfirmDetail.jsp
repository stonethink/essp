
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>
<%@ page import="c2s.essp.common.account.IDtoAccount" %>
<%@ page import="org.apache.struts.util.MessageResources" %>
<%@ page import="server.framework.common.Constant" %>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="server.essp.projectpre.ui.project.confirm.VbProjectConfirm" %>
<bean:define id="contextPath" value="<%=request.getContextPath()%>"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
   
    <tiles:insert page="/layout/head.jsp">
            <tiles:put name="title" value="Project Code Confirm"/>
            <tiles:put name="jspName" value=""/>
        </tiles:insert>
   
  </head>
  <%
      String master =  null;
      String sub = null;
      String related = null;
      String finance = null;
				  try{
						 MessageResources resources = (MessageResources) pageContext.getAttribute("projectpre",PageContext.APPLICATION_SCOPE);
						 master = resources.getMessage(request.getLocale(), "projectCode.MasterData.MasterProject");
						 sub = resources.getMessage(request.getLocale(), "projectCode.MasterData.SubProject");
						 related = resources.getMessage(request.getLocale(), "projectCode.MasterData.RelProject");
						 finance = resources.getMessage(request.getLocale(), "projectCode.MasterData.FinProject");
					 }catch(Exception e){
					     e.printStackTrace();
					 }
	  List newViewList = new ArrayList();
	  List viewBean = (List)request.getAttribute(Constant.VIEW_BEAN_KEY);
	  for(int i =0;i<viewBean.size();i++){
	       VbProjectConfirm vb = (VbProjectConfirm)viewBean.get(i);
	      if (vb.getAcntType().equals(IDtoAccount.PROJECT_RELATION_MASTER)){
	               vb.setAcntType(master);
	      } else if(vb.getAcntType().equals(IDtoAccount.PROJECT_RELATION_SUB)){
	               vb.setAcntType(sub);
	              
	      } else if(vb.getAcntType().equals(IDtoAccount.PROJECT_RELATION_RELATED)){
	               vb.setAcntType(related);
	      
	      } else if(vb.getAcntType().equals(IDtoAccount.PROJECT_RELATION_FINANCE)){
	               vb.setAcntType(finance);
	      
	      }
	      newViewList.add(vb);

	  }
	  request.setAttribute(Constant.VIEW_BEAN_KEY, newViewList);
				 
  %>
  <script language="JavaScript" type="text/javascript">
    

  
  var currentRow;
 
    function onSubmitData(){
       try{
        var acntId=currentRow.selfproperty;
	    var status=currentRow.cells[6].innerText;
	    var height = 450;
        var width = 850;
        var topis = (screen.height - height) / 2;
        var leftis = (screen.width - width) / 2;
        var option = "height=" + height + "px"  +", width=" + width + "px" +", top=" + topis + "px" +", left=" + leftis + "px"  +", toolbar=no, menubar=no, scrollbars=no, resizable=yes,location=no, status=no";
		var url="<%=request.getContextPath()%>/projectpre/projectcode/codeapply/PopCodeApply.jsp?appType=confirm&acntId="+acntId+"&status="+status;
		var windowTitle="";
        window.open(url,windowTitle,option);
      }catch(e){}
    }
 
    function onPopCodeConfirm(rowObj){
        try{
	    var acntId=rowObj.selfproperty;
	    var status=rowObj.cells[6].innerText;
	    var height = 450;
        var width = 850;
        var topis = (screen.height - height) / 2;
        var leftis = (screen.width - width) / 2;
        var option = "height=" + height + "px"  +", width=" + width + "px" +", top=" + topis + "px" +", left=" + leftis + "px"  +", toolbar=no, menubar=no, scrollbars=no, resizable=yes,location=no, status=no";
		var url="<%=request.getContextPath()%>/projectpre/projectcode/codeapply/PopCodeApply.jsp?appType=confirm&acntId="+acntId+"&status="+status;
		var windowTitle="";
        window.open(url,windowTitle,option);
        }catch(e){}
    }
    function disableBtn(){
	
	  try{
	  
	   var table=document.getElementById("confirmList_table");
	   var thead_length=table.tHead.rows.length;
	   var firstRow=table.rows[thead_length];
	   //alert(firstRow);
	   //alert(cell);
	   if(firstRow==null){
	     window.parent.submitBtn.disabled=true;
	   }else{
	     window.parent.submitBtn.disabled=false;
	     
	   }
	   }catch(e){}
	}

    //单击行时的动作，更新下帧中的数据
    function rowOnClick(rowObj) {
    try{
    if(rowObj==null){
    window.parent.submitBtn.disabled=true;
    return;
    }
    currentRow=rowObj;

	}catch(e){}
	}
   
     //当进入此页时，自动选中第一行，并国际化表头
	function autoClickFirstRow(){
	   try{
	    var table=document.getElementById("confirmList_table");
	    
        var str0="<bean:message bundle="projectpre" key="projectCode.MasterData.ProjectCode"/>";
		var str1="<bean:message bundle="projectpre" key="projectCode.PopCodeConfirm.Applicant"/>";
		var str2="<bean:message bundle="projectpre" key="projectCode.CodeChangeDetail.CodeType"/>";
        var str3="<bean:message bundle="projectpre" key="projectCode.CodeChangeDetail.CustomerShort"/>";
        var str4="<bean:message bundle="projectpre" key="projectCode.CodeChangeDetail.BeginDate"/>";
        var str5="<bean:message bundle="projectpre" key="projectCode.CodeChangeDetail.EndDate"/>";
        var str6="<bean:message bundle="projectpre" key="projectCode.MasterData.StatusCode"/>";
        
		var thead_length=table.tHead.rows.length;
		var tds=table.rows[thead_length-1];
		var cells=tds.cells;
		//使EC标签的表头国际化
		cells[0].innerHTML=cells[0].innerHTML.replace("Project Id",str0);
		cells[0].title = cells[0].title.replace("Project Id",str0);
		cells[1].innerHTML=cells[1].innerHTML.replace("Applicant",str1);
		cells[1].title = cells[1].title.replace("Applicant",str1);
		cells[2].innerHTML=cells[2].innerHTML.replace("Code Type",str2);
		cells[2].title = cells[2].title.replace("Code Type",str2);
		cells[3].innerHTML=cells[3].innerHTML.replace("Customer Short",str3);
		cells[3].title = cells[3].title.replace("Customer Short",str3);
		cells[4].innerHTML=cells[4].innerHTML.replace("Begin Date",str4);
		cells[4].title = cells[4].title.replace("Begin Date",str4);
		cells[5].innerHTML=cells[5].innerHTML.replace("End Date",str5);
		cells[5].title = cells[5].title.replace("End Date",str5);
		cells[6].innerHTML=cells[6].innerHTML.replace("Application Status",str6);
		cells[6].title = cells[6].title.replace("Application Status",str6);
	
	     var firstRow=table.rows[thead_length];
	     
	    rowOnClick(firstRow);
	    disableBtn();
	    if(table.rows.length>thead_length){
	       //如果有数据
		   onChangeBackgroundColor(firstRow);
	    }
	    }catch(e){}
	}
    </script>
  <body>
   
     
  <%
	java.util.Locale locale = (java.util.Locale)session.getAttribute(org.apache.struts.Globals.LOCALE_KEY); 	
  	String _language = locale.toString();	
  %>
    <ec:table 
       tableId="confirmList"
       items="webVo"
       var="aConfirm" scope="request"
       action="${pageContext.request.contextPath}/project/confirm/listProjectConfirm.do"  
       imagePath="${pageContext.request.contextPath}/image/table/compact/*.gif"
       filterable="true"
       view="compact"
       locale="<%=_language%>"
       >
        <ec:row  onclick="rowOnClick(this);" ondbclick="onPopCodeConfirm(this);" selfproperty="${aConfirm.acntId}">             
             <ec:column style="width:10%x" property="acntId" tooltip="${aConfirm.acntId}" title="Project Id"/>
             <ec:column style="width:20%" property="applicant"  tooltip="${aConfirm.applicant}" title="Applicant"/>
             <ec:column style="width:20%" property="acntType"  tooltip="${aConfirm.acntType}" title="Code Type"/>
             <ec:column style="width:15%" property="customerShort" tooltip="${aConfirm.customerShort}" title="Customer Short"/>
             <ec:column style="width:10%" property="acntPlannedStart"  tooltip="${aConfirm.acntPlannedStart}" title="Begin Date"/>
             <ec:column style="width:10%" property="acntPlannedFinish"  tooltip="${aConfirm.acntPlannedFinish}" title="End Date"/>  
             <ec:column style="width:10%" property="acntStatus"  tooltip="${aConfirm.acntStatus}" title="Application Status"/>        
             
         </ec:row>        
     </ec:table>
   
  </body>
 <script language="javascript" type="text/javascript">
        autoClickFirstRow();
     </script>
</html>
