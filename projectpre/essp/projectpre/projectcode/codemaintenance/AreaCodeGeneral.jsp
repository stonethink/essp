
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  <title><bean:message bundle="projectpre" key="projectCode.AreaCodeGeneral.Title"/></title>
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
        	var siteName=document.getElementById("siteName").value;
        	var siteLoading=document.getElementById("siteLoading").value;
        	var status=document.getElementById("status").checked;
        	//alert(status);
        	var code="<bean:write name="webVo" property="siteCode" scope="request" />";
        	var upFrame=window.parent.frames[0];
        	//alert(upFrame);
        	var table=upFrame.document.getElementById("areaCode_table");
        	//alert(table);
        	for(i=1;i<table.rows.length;i++){
        		if(table.rows[i].selfproperty==code){
        			var tds=table.rows[i].cells;
        			tds[0].innerText=siteName;
        			tds[0].title=siteName;
        			tds[2].innerText=siteLoading;
        			tds[2].title=siteLoading;
        			if(status){
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
  
  <body style="padding-left:8px" onload="refreshUp();">
  <html:form id="AreaCode" method="post" action="/project/maintenance/updateAreaCode" onsubmit="return onClickSubmit(this)" initialForcus="siteName">
  <table id="Table1" cellSpacing="0" cellPadding="0" width="100%" border="0" >

  <tr>
    <td class="list_range"><bean:message bundle="projectpre" key="projectCode.AreaCodeList.ProjectCodeTrailCode"/></td>
    <td class="list_range"><html:text styleId="input_field" fieldtype="text" name="siteCode" req="true" beanName="webVo" maxlength="25" readonly="true"/></td>
    <td class="list_range">&nbsp;</td>
  </tr>
  	
  <tr>
    <td class="list_range" width="20%"><bean:message bundle="projectpre" key="projectCode.AreaCodeList.ProjectExecuteCity"/></td>
    <td class="list_range" width="100px"><html:text styleId="input_field" fieldtype="text" req="true" name="siteName" beanName="webVo" maxlength="25"/></td>
    <td class="list_range" width="*">&nbsp;</td>
  </tr>
  <tr>
    <td class="list_range" width="20%"><bean:message bundle="projectpre" key="projectCode.AreaCodeList.SiteLoading"/></td>
    <td class="list_range" width="100px"><html:text styleId="input_field" fieldtype="text" req="true" name="siteLoading" beanName="webVo" maxlength="10"/></td>
    <td class="list_range" width="*">&nbsp;</td>
  </tr>
  <tr>
    <td class="list_range"><bean:message bundle="projectpre" key="projectCode.Desc"/></td>
    <td class="list_range" colspan="2">
    <html:textarea name="description" beanName="webVo" rows="4" styleId="input_field" req="false" maxlength="250"  />
    </td>
  </tr>
  <tr>
    <td class="list_range"><bean:message bundle="projectpre" key="projectCode.AreaCodeList.Enable"/></td>
    <td class="list_range"><html:multibox styleId="width:100px" name="status" beanName="webVo" value="true"/></td>
    <td class="list_range">&nbsp;</td>
  </tr>
</table>

</html:form>
  </body>
</html>
