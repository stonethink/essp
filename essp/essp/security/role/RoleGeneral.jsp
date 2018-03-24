<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title><bean:message bundle="application"  key="RoleDefine.RoleGeneral.title"/></title>
    <tiles:insert page="/layout/head.jsp">
      <tiles:put name="title" value=" "/>
      <tiles:put name="jspName" value=""/>
    </tiles:insert>

     <STYLE type="text/css">
       input_field {width:60}
       #input_text {width:156}
       #description {width:100%}
     </STYLE>
     <script language="javascript" type="text/javascript">

     function checkReq(form){
	     if(!submitForm(form)){
    	 	return false;
	     }else{
	     	return true;
	     }

     }


     function onSaveData(){
          var form=document.forms[0];
          if(!checkReq(form)){
          	return;
          }
          form.submit();
          refreshUp();
        }


      function refreshUp(){

        	var roleName=document.getElementById("roleName").value;
        	var parentId=document.getElementById("parentId").value;
                if(parentId=="默认无父角色"){
                  parentId = "";
                }
                var description=document.getElementById("description").value;
                var status=document.getElementById("status").checked;
                var visible=document.getElementById("visible").checked;
        	//alert(status);
        	var roleId="<bean:write name="webVo" property="roleId" scope="request"/>";
                //alert(roleId);
        	var upFrame=window.parent.RoleList;
        	//alert(upFrame);
        	var table=upFrame.document.getElementById("Role_table");
        	//alert(table);
        	for(i=1;i<table.rows.length;i++){
        		if(table.rows[i].selfproperty==roleId){
        			var tds=table.rows[i].cells;
        			tds[0].innerText=roleId;
        			tds[0].title=roleId;
        			tds[1].innerText=roleName;
        			tds[1].title=roleName;
        		        tds[2].innerText=parentId;
        			tds[2].title=parentId;
        			if(status){
        				tds[3].innerHTML='<img src="/essp/image/yes1.gif" />';
        			}else{
        				tds[3].innerHTML='<img src="/essp/image/no1.gif" />';
        			}
                                if(visible){
                                  tds[4].innerHTML='<img src="/essp/image/yes1.gif" />';
                                }else{
                                  tds[4].innerHTML='<img src="/essp/image/no1.gif" />';
                                }
        			break;
        		}
        	}

        }
     </script>

  </head>

  <body>
  <html:form  id="Role" method="post" action="/security/updateRole" onsubmit="return onSaveData(this)">
  <table id="Table1" cellSpacing="0" cellPadding="0" width="100%" border="0" style="padding-left:8px">
  <tr>
    <td class="list_range" width="120"><bean:message bundle="application"  key="RoleDefine.Role.roleId"/></td>
    <td class="list_range" width="60"><html:text styleId="input_field" fieldtype="text" name="roleId" beanName="webVo"  maxlength="25" readonly="true"/></td>
    <td class="list_range" width="100">&nbsp;</td>
    <td class="list_range" width="120"><bean:message bundle="application"  key="RoleDefine.Role.roleName"/></td>
    <td class="list_range" width="60"><html:text styleId="input_field" fieldtype="text" name="roleName" beanName="webVo" maxlength="25"/></td>
    <td class="list_range" width="100">&nbsp;</td>
  </tr>
  <tr>
    <td class="list_range"><bean:message bundle="application"  key="RoleMaintenace.parentRoleId"/></td>
    <td class="list_range" width="60">
      <html:select name="parentId" styleId="input_text" beanName="webVo">
         <html:optionsCollection name="webVo" property="parentIdList"/>
      </html:select>
    </td>
    <td class="list_range">&nbsp;</td>
    <td class="list_range"><bean:message bundle="application"  key="RoleMaintenace.sequence"/></td>
    <td class="list_range"><html:text styleId="input_field" fieldtype="text" name="seq" beanName="webVo"/></td>
    <td class="list_range">&nbsp;</td>
  </tr>
  <tr>
    <td class="list_range"><bean:message bundle="application"  key="RoleDefine.Role.description"/></td>
    <td class="list_range" colspan="4"><html:textarea styleId="description" name="description" beanName="webVo"></html:textarea></td>
    <td class="list_range">&nbsp;</td>
  </tr>
  <tr>
    <td class="list_range"><bean:message bundle="application"  key="RoleDefine.Role.status"/></td>
    <td class="list_range"><html:multibox name="status" beanName="webVo" styleId="input_field" value="true" ></html:multibox></td>
    <td class="list_range">&nbsp;</td>
    <td class="list_range"><bean:message bundle="application"  key="RoleDefine.Role.isVisiable"/></td>
    <td class="list_range"><html:multibox name="visible" beanName="webVo" styleId="input_field" value="true" ></html:multibox></td>
    <td class="list_range">&nbsp;</td>
  </tr>
</table>

</html:form>
  </body>
</html>
