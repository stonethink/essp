
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <tiles:insert page="/layout/head.jsp">
      <tiles:put name="title" value=" "/>
      <tiles:put name="jspName" value=""/>
    </tiles:insert>
   
     <STYLE type="text/css">
       #input_field {width:210}
       #input_text {width:60}  
   
     </STYLE>
     <script language="javascript" type="text/javascript">
      //此方法用于检查必填项
     function checkReq(form){
	     if(!submitForm(form)){
    	 	return false;
	     }else{
	     	return true;
	     }
	     
     }
      //此方法用于提交数据	
     function onSaveData(){
          var form=document.forms[0];
          if(!checkReq(form)){
          	return;
          }
          form.submit();
        }
        //此方法用于当下帧中的数据发生改变时同步上帧中的数据
      function refreshUp(){
        try{
        	var bdName=document.getElementById("bdName").value;
        	
        	var status=document.getElementById("status").checked;
        	//alert(status);
        	var code="<bean:write name="webVo" property="bdCode" scope="request" />";
        	var upFrame=window.parent.frames[0];
        	//alert(upFrame);
        	var table=upFrame.document.getElementById("BdCodeList_table");
        	//alert(table);
        	for(i=1;i<table.rows.length;i++){
        		if(table.rows[i].selfproperty==code){
        			var tds=table.rows[i].cells;
        			tds[1].innerText=bdName;
        			tds[1].title=bdName;
        			
        			if(status){
        				tds[2].innerHTML='<img src="/essp/image/yes1.gif" />';
        			}else{
        				tds[2].innerHTML='<img src="/essp/image/no1.gif" />';
        			}
        			break;
        		}
        	}
        }catch(e){}
        }
        
     </script>
   
  </head>
  
  <body >
  <html:form id="BdCode" method="post" action="/project/maintenance/updateBdCode" onsubmit="return onClickSubmit(this)" initialForcus="bdCode">
  <table id="Table1" cellSpacing="0" cellPadding="0" width="100%" border="0" style="padding-left:8px">
	
  <tr>
    <td class="list_range" width="150"><bean:message bundle="projectpre" key="projectCode.BDCode"/></td>
    <td class="list_range" width="60"><html:text styleId="input_field" fieldtype="text" req="true" readonly="true" name="bdCode" beanName="webVo"  maxlength="25"/></td>
    <td class="list_range" width="*">&nbsp;</td>
  </tr>
  <tr>
    <td class="list_range"><bean:message bundle="projectpre" key="projectCode.Name"/></td>
    <td class="list_range"><html:text styleId="input_field" fieldtype="text" req="true" name="bdName" beanName="webVo" maxlength="25"/></td>
    <td class="list_range">&nbsp;</td>
  </tr>
  <tr>
    <td class="list_range"><bean:message bundle="projectpre" key="projectCode.Desc"/></td>
    <td class="list_range" colspan="2">
    <html:textarea name="description" beanName="webVo" rows="4" styleId="input_field" req="false" maxlength="250"  />
    </td>
  </tr>
  <tr>
     <td class="list_range"><bean:message bundle="projectpre" key="customer.Enable"/></td>
  <td class="list_range" colspan="2"><html:multibox styleId="width:100px" name="status" beanName="webVo" value="true"/>
   </tr>
   <tr>
     <td class="list_range"><bean:message bundle="projectpre" key="projectCode.isAchieveBelongUnit"/></td>
     <td class="list_range" colspan="2"><html:multibox styleId="width:100px" name="achieveBelong" beanName="webVo" value="true"/>
   </tr>
</table>

</html:form>
   <SCRIPT language="JavaScript" type="Text/JavaScript">
        refreshUp();
   </SCRIPT>
  </body>
</html>
