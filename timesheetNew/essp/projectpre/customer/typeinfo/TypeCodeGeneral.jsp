
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
       #input_field {width:60}
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
     function  onSaveData(){
       
          var form=document.forms[0];
         if (!checkReq(form)){
        	return;
           }
           form.submit();
        }
        
      //此方法用于当下帧中的数据发生改变时同步上帧中的数据
      function refreshUp(){
       try{
        	var code=document.getElementById("id.code").value;
        	var name=document.getElementById("name").value;
        	var status=document.getElementById("status").checked;
        	//alert(status);
        	var code="<bean:write name="webVo" property="id.code" scope="request" />";
        	var upFrame=window.parent.frames[0];
        	//alert(upFrame);
        	var table=upFrame.document.getElementById("TypeCodeListTable_table");
        	//alert(table);
        	for(i=1;i<table.rows.length;i++){
        		if(table.rows[i].selfproperty==code){
        			var tds=table.rows[i].cells;
        			
        			tds[1].innerText=name;
        			tds[1].title=name;
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
  
   <body style="padding-left:8px" onload="refreshUp();">
 <html:form id="ClassCode" method="post" action="/pc/updateClassCode" onsubmit="return onClickSubmit(this)" initialForcus="id.code">
  <table id="Table1" cellSpacing="0" cellPadding="0" width="100%" border="0" style="padding-left:8px" >
	
  <tr>
    <td class="list_range" width="120"><bean:message bundle="projectpre" key="customer.BusinessType"/></td>
    <td class="list_range" width="60"><html:text styleId="input_field" readonly="true" fieldtype="text" name="id.code" beanName="webVo"  maxlength="50"/></td>
    <td class="list_range" width="*">&nbsp;</td>
  </tr>
  <tr>
    <td class="list_range"><bean:message bundle="projectpre" key="customer.ChineseInstruction"/></td>
    <td class="list_range"><html:text styleId="input_field" fieldtype="text" name="name" beanName="webVo"/></td>
    <td class="list_range">&nbsp;</td>
  </tr>
   <tr>
     <td class="list_range"><bean:message bundle="projectpre" key="customer.Enable"/></td>
     <td class="list_range"><html:multibox styleId="width:100px" name="status" beanName="webVo" value="true"/></td>
    <td class="list_range">&nbsp;</td>
   </tr>
</table>

</html:form>
  </body>
</html>
