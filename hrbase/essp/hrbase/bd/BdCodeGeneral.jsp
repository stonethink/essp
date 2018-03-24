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
       #input_text {width:20}  
       #input_description{width:420}
   
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
        	var achieveBelong=document.getElementById("achieveBelong").checked;
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
        			if(achieveBelong){
        				tds[3].innerHTML='<img src="/essp/image/yes1.gif" />';
        			}else{
        				tds[3].innerHTML='<img src="/essp/image/no1.gif" />';
        			}
        			break;
        		}
        	}
        }catch(e){}
        }
        
     </script>
   
  </head>
  
  <body >
  <html:form id="bdCode" method="post" action="/hrb/bd/updateBdCode" onsubmit="return onClickSubmit(this)" initialForcus="bdCode">
  <table id="Table1" cellSpacing="0" cellPadding="0" width="100%" border="0" style="padding-left:8px">
	<tr height="10"><td height="10"></td></tr>
  <tr>
    <td class="list_range" width="120"><bean:message bundle="hrbase" key="hrbase.bd.bdcode"/></td>
    <td class="list_range" width="60"><html:text styleId="input_field" fieldtype="text" req="true" readonly="true" name="bdCode" beanName="webVo"  maxlength="25"/></td>
    <td class="list_range" width="20">&nbsp;</td>
    <td class="list_range">&nbsp;</td>
    <td class="list_range">&nbsp;</td>
  </tr>
  <tr height="10"><td height="10"></td></tr>
  <tr>
    <td class="list_range" width="120"><bean:message bundle="hrbase" key="hrbase.bd.bdname"/></td>
    <td class="list_range" width="60"><html:text styleId="input_field" fieldtype="text" req="true" name="bdName" beanName="webVo" maxlength="25"/></td>
    <td class="list_range" width="20">&nbsp;</td>
    <td class="list_range">&nbsp;</td>
    <td class="list_range">&nbsp;</td>
  </tr>
  <tr height="10"><td height="10"></td></tr>
    <tr>
      <td class="list_range" width="120"><bean:message bundle="hrbase" key="hrbase.enable"/></td>
      <td class="list_range" width="20"><html:multibox styleId="input_text" name="status" beanName="webVo" value="true"/>
      <td class="list_range" width="20">&nbsp;</td>
      <td class="list_range" width="150"><bean:message bundle="hrbase" key="hrbase.bd.isAchieveBelong"/></td>
      <td class="list_range" ><html:multibox styleId="input_text" name="achieveBelong" beanName="webVo" value="true"/>
   </tr>
  <tr height="10"><td height="10"></td></tr>
  <tr>
    <td class="list_range"><bean:message bundle="hrbase" key="hrbase.bd.description"/></td>
    <td class="list_range" colspan="4">
    <html:textarea name="description" beanName="webVo" rows="4" styleId="input_description" req="false" maxlength="250"  />
    </td>
  </tr>

</table>

</html:form>
   <SCRIPT language="JavaScript" type="Text/JavaScript">
        refreshUp();
   </SCRIPT>
  </body>
</html>
