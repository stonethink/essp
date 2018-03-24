
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
            <tiles:put name="title" value="Log Query"/>
            <tiles:put name="jspName" value=""/>
        </tiles:insert>
   
  </head>
  <script language="JavaScript" type="text/javascript">
  var currentRow;
 
    //单击行时的动作，更新下帧中的数据
    function rowOnClick(rowObj) {
  try{
   if(rowObj==null){
  //  window.parent.submitBtn.disabled=true;
    return;
    }
    currentRow=rowObj;

	}catch(e){}
	}
   
     //当进入此页时，自动选中第一行，并国际化表头
	function autoClickFirstRow(){
	   try{
	    var table=document.getElementById("resultList_table");
	    
        var str0="<bean:message bundle="projectpre" key="log.acntId"/>";
		var str1="<bean:message bundle="projectpre" key="log.dataType"/>";
		var str2="<bean:message bundle="projectpre" key="log.applicationType"/>";
        var str3="<bean:message bundle="projectpre" key="log.operation"/>";
        var str4="<bean:message bundle="projectpre" key="log.operator"/>";
        var str5="<bean:message bundle="projectpre" key="log.mailSender"/>";
        var str6="<bean:message bundle="projectpre" key="log.operationDate"/>";
        
		var thead_length=table.tHead.rows.length;
		var tds=table.rows[thead_length-1];
		var cells=tds.cells;
		//使EC标签的表头国际化
		cells[0].innerHTML=cells[0].innerHTML.replace("ProjectId",str0);
		cells[0].title = cells[0].title.replace("ProjectId",str0);
		cells[1].innerHTML=cells[1].innerHTML.replace("dataType",str1);
		cells[1].title = cells[1].title.replace("dataType",str1);
		cells[2].innerHTML=cells[2].innerHTML.replace("applicationType",str2);
		cells[2].title = cells[2].title.replace("applicationType",str2);
		cells[3].innerHTML=cells[3].innerHTML.replace("operation",str3);
		cells[3].title = cells[3].title.replace("operation",str3);
		cells[4].innerHTML=cells[4].innerHTML.replace("operator",str4);
		cells[4].title = cells[4].title.replace("operator",str4);
		cells[5].innerHTML=cells[5].innerHTML.replace("mailSender",str5);
		cells[5].title = cells[5].title.replace("mailSender",str5);
		cells[6].innerHTML=cells[6].innerHTML.replace("operationDate",str6);
		cells[6].title = cells[6].title.replace("operationDate",str6);
	
	     var firstRow=table.rows[thead_length];
	     
	    rowOnClick(firstRow);
	 //   disableBtn();
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
       tableId="resultList"
       items="webVo"
       var="aConfirm" scope="request"
       action="${pageContext.request.contextPath}/log/resultList.do"  
       imagePath="${pageContext.request.contextPath}/image/table/compact/*.gif"
       view="compact"
       locale="<%=_language%>"
       >
        <ec:row  onclick="rowOnClick(this);" selfproperty="${aConfirm.acntId}">             
             <ec:column style="width:10%" property="acntId" tooltip="${aConfirm.acntId}" title="ProjectId"/>
             <ec:column style="width:20%" property="dataType"  tooltip="${aConfirm.dataType}" title="dataType"/>
             <ec:column style="width:20%" property="applicationType"  tooltip="${aConfirm.applicationType}" title="applicationType"/>
             <ec:column style="width:15%" property="operation" tooltip="${aConfirm.operation}" title="operation"/>
             <ec:column style="width:10%" property="operator"  tooltip="${aConfirm.operator}" title="operator"/>
             <ec:column style="width:10%" property="mailSender"  tooltip="${aConfirm.mailSender}" title="mailSender"/>  
             <ec:column style="width:10%" property="operationDate"  tooltip="${aConfirm.operationDate}" title="operationDate"/>        
             
         </ec:row>        
     </ec:table>
   
  </body>
 <script language="javascript" type="text/javascript">
        autoClickFirstRow();
     </script>
</html>
