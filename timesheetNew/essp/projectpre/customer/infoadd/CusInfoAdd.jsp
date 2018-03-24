<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/inc/pagedef.jsp" %>
<%
  String flag1 = "false";
  boolean flag2 = true;
%>

<bean:define id="contextPath" value="<%=request.getContextPath()%>"/>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
   <TITLE><bean:message bundle="projectpre" key="customer.CustomerInfoAdd.cardTitle.CustomerCodeList"/></TITLE>
    <tiles:insert page="/layout/head.jsp">
       <tiles:put name="title" value=" "/>
       <tiles:put name="jspName" value=""/>
     </tiles:insert>
    <style type="text/css">
      #input_common1{width:130}
      #input_common2{width:543}
      #input_common3{width:110}
      #input_common4{width:403}
      #input_field{width:60}
      #description{width:543}
    </style>
  <script type="text/JavaScript" src="<%=contextPath%>/js/queryCustomer.js"></script>
  <script language="JavaScript" type="text/JavaScript">
  //用于查询集团代号
    function allocateHr() {
      var form=document.forms[0];
      if(document.all.attribute[0].checked)return;
      var param = new HashMap();
      param.put("attribute",new Array("Group","Attribute='Group'"));
      var result = queryCustomer(param);
      if(result!=null){
      document.getElementsByName("groupId")[0].value = result.id+"---"+result.short_name;
 
      SelectGroupId();
      document.all.createDate.value=result.create_date;
      document.all.alterDate.value=result.alter_date;
      document.all.creator.value=result.creator;
      }
    }
    
    //集团代号选择与否会显示或隐藏建案日期，最近变更日期及建档人三个栏位
      function SelectGroupId(){
          if(document.all.attribute[0].checked){
          document.all.groupId.value="";
        }
        try{
         var hidTitle1=document.getElementById("hidTitle1");
         var hidTitle2=document.getElementById("hidTitle2");
         var hidTitle3=document.getElementById("hidTitle3");
         var hidContext1=document.getElementById("hidContext1");
         var hidContext2=document.getElementById("hidContext2");
         var hidContext3=document.getElementById("hidContext3");
         if(document.all.groupId.value!= null && document.all.groupId.value!=""){
            hidTitle1.style.visibility="visible";
            hidTitle2.style.visibility="visible";
            hidTitle3.style.visibility="visible";
            hidContext1.style.visibility="visible";
      	    hidContext2.style.visibility="visible";
      	    hidContext3.style.visibility="visible";     	    
        }else{
             hidTitle1.style.visibility="hidden";
             hidTitle2.style.visibility="hidden";
             hidTitle3.style.visibility="hidden";
      	     hidContext1.style.visibility="hidden";
      	     hidContext2.style.visibility="hidden";
             hidContext3.style.visibility="hidden";
        }
       } catch(e){}
       }
  //用于提交数据
    function onClickSubmit(){
      if(document.all.applicationStatus.value!="Submitted"){
       if(document.all.attribute[1].checked){        
          if(document.all.customerCountry.value != "" && document.all.customerCountry.value != null) {     
             var form=document.forms[0];
             if(!checkReq(form)){
             	return;
              }
            if(confirm("<bean:message bundle="application" key="global.confirm.submit"/>")){   
            form.applicationStatus.value="Submitted";
             form.submit();
            }else{
             submit_flug=false;
         }
           }else{
          alert("<bean:message bundle="application" key="global.confirm.require"/>");
          if( document.all.customerCountry.value == null||document.all.customerCountry.value==""){     
               document.all.customerCountry.focus();
            }
          return;
         }
       }else{
        var form=document.forms[0];
            if(!checkReq(form)){
           	return;
          }
       if(confirm("<bean:message bundle="application" key="global.confirm.submit"/>")){        
            form.applicationStatus.value="Submitted";
             form.submit();
           }else{
           submit_flug=false;
           }
       }
    }    
    }
    //取消操作
     function onClickCancel(){
      this.close();
     }

//用于控制选择集团或公司码时某些栏位的属性变化
 function onClickEvent(i){
    try{
       var hidTitle1=document.getElementById("hidTitle1");
       var hidTitle2=document.getElementById("hidTitle2");
       var hidTitle3=document.getElementById("hidTitle3");
       var hidTitleG=document.getElementById("hidTitleG");
     
       var hidContextG=document.getElementById("hidContextG");
       var hidContext1=document.getElementById("hidContext1");
       var hidContext2=document.getElementById("hidContext2");
       var hidContext3=document.getElementById("hidContext3");
      if(i==2){    
        flag1 = "true";
        flag2 = false;       
        document.forms[0].customerCountry.req=flag1;
        document.forms[0].customerCountry.focus(); 
        if(document.all.groupId.value!=null&&document.all.groupId.value!=""){  
         hidTitle1.style.visibility="visible";
        hidTitle2.style.visibility="visible";
        hidTitle3.style.visibility="visible";  
           
        hidContext1.style.visibility="visible";
        hidContext2.style.visibility="visible";
        hidContext3.style.visibility="visible";    
        }
         hidTitleG.style.visibility="visible";   
        hidContextG.style.visibility="visible";  
      }
      if(i==1){
        flag1 = "false";
        flag2 = true;
        hidTitle1.style.visibility="hidden";
        hidTitle2.style.visibility="hidden";
        hidTitle3.style.visibility="hidden";  
        hidTitleG.style.visibility="hidden";        
        hidContextG.style.visibility="hidden"; 
   
        hidContext1.style.visibility="hidden";
        hidContext2.style.visibility="hidden";
        hidContext3.style.visibility="hidden";      
        document.forms[0].customerCountry.req=flag1;
        document.forms[0].customerCountry.focus();     
        } 
        }catch(e){}
    }
    //检查必填栏位
     function checkReq(form){
	     if(!submitForm(form)){
    	 	return false;
	     } else {
	        return true;
	     }
	     
     }
     
     //检查选择集团码还是公司码
     function onCheck(){
    if(document.all.attribute[0].checked){
        onClickEvent(1);
     }
     else{
        onClickEvent(2);
    }
     }
     
     
      //此方法用于保存数据	
       function onSaveData(){
        if(document.all.applicationStatus.value!="Submitted"){
       if(document.all.attribute[1].checked){
          if(document.all.customerCountry.value != "" && document.all.customerCountry.value != null) {     
            var form=document.forms[0];
            if(!checkReq(form)){
           	return;
            }
            form.applicationStatus.value="UnSubmit";
             form.submit();
           }else{
          alert("<bean:message bundle="application" key="global.confirm.require"/>");
          if( document.all.customerCountry.value == null||document.all.customerCountry.value==""){       
               document.all.customerCountry.focus();
            }
          return;
         }
       }else{
        var form=document.forms[0];
            if(!checkReq(form)){
           	return;
            }
            form.applicationStatus.value="UnSubmit";
             form.submit();
       }     
    }  
    }
    //用于初始化页面
     function initial(){
       try{
         var hidTitle1=document.getElementById("hidTitle1");
         var hidTitle2=document.getElementById("hidTitle2");
         var hidTitle3=document.getElementById("hidTitle3");
          var hidTitleG=document.getElementById("hidTitleG");
          
         var hidContextG=document.getElementById("hidContextG");
         var hidContext1=document.getElementById("hidContext1");
         var hidContext2=document.getElementById("hidContext2");
         var hidContext3=document.getElementById("hidContext3");  
             hidTitle1.style.visibility="hidden";
             hidTitle2.style.visibility="hidden";
             hidTitle3.style.visibility="hidden"; 
             hidTitleG.style.visibility="hidden";            
      	     hidContextG.style.visibility="hidden";           
      	     hidContext1.style.visibility="hidden";
      	     hidContext2.style.visibility="hidden";
             hidContext3.style.visibility="hidden";  
       }catch(e){}
     }
     
  </script>
  </head>
  
  <body >
	<html:form id="CustomerApplication" method="post" action="/customer/add/addCustApp" onsubmit="return onClickSubmit(this)" initialForcus="regId" >
     <table align=center>
				<tr>
					<td class="list_range" width="120">
						<bean:message bundle="projectpre" key="customer.CustomerInfoAdd.cardTitle.CustomerCodeList" />
					</td>
				</tr>
	</table>	
 	<table width="260" border="0" style="padding-left:8px" >
 	<html:hidden name="rid" beanName="webVo" />
    <html:hidden name="applicationStatus" beanName="webVo" /><tr>
	  <td class="list_range">&nbsp;</td>
	  <td class="list_range">&nbsp;</td>
	 
	  </tr>
      <tr>
     
        <td class="list_range" width="">
        <input   name="attribute" type="radio" value="Group"  onclick="onClickEvent(1)"  />
        <bean:message bundle="projectpre" key="cusomer.Group"/>
        </td>
        <td class="list_range">
         <INPUT name="attribute" type="radio" value="Company" checked onclick="onClickEvent(2)">
		 
		<bean:message bundle="projectpre" key="customer.CompanyCode"/>
		</td>
		
      </table>
      <table width="700" border="0">
      <tr>
      <td class="list_range"  >&nbsp;</td>
        <td class="list_range" width="150" ><bean:message bundle="projectpre" key="customer.UniformCode/RegisteredNumberofLicence"/></td>
        <td class="list_range" width="150" > <html:text styleId="input_common1" name="regId" beanName="webVo" fieldtype="text" req="false" maxlength="25" />
        </td>
         <td width="100"></td>
         <td class="list_range" width="150">
         <div id="hidTitleG" ><bean:message bundle="projectpre" key="customer.GroupNo"/></div>
         </td>
       <td class="list_range" width="150" >
   
		<div id="hidContextG"><html:text name="groupId"
                      beanName="webVo"
                      fieldtype="text"
                      styleId="input_common1" 
                      prev="dueDate"
		              readonly="true"
                      imageSrc="<%=contextPath+"/image/qurey.gif"%>"
                      imageWidth="18"
                      imageOnclick="allocateHr()"
                      value=""
         />
         </div>     

	    </td>
      </tr>
     
        
      <tr>
      <td class="list_range" >&nbsp;</td>
        <td class="list_range"><bean:message bundle="projectpre" key="customer.ClientCountryCode"/>
        </td>
        <td>
        <html:select name="customerCountry" styleId="input_common1" beanName="webVo"  req="false" >          
            <html:optionsCollection name="webVo" property="customerCountryList"/>
        </html:select> 
		</td>
       <td width="20"></td>
       <td class="list_range">
         <bean:message bundle="projectpre" key="customer.BelongedSite"/>
       </td>
       <td>
		 <html:select name="belongSite" styleId="input_common1" beanName="webVo"  req="false" >          
            <html:optionsCollection name="webVo" property="belongSiteList"/>
        </html:select>
       </td>
      </tr>
      <tr>
      <td class="list_range" >&nbsp;</td>
        <td class="list_range"><bean:message bundle="projectpre" key="customer.ClientClassCode"/>
        </td>
        <td>
        <html:select name="customerClass" styleId="input_common1" beanName="webVo"  req="false" >          
            <html:optionsCollection name="webVo" property="customerClassList"/>
        </html:select>       
		</td>
	 <td width="20"></td>
		<td class="list_range"><bean:message bundle="projectpre" key="customer.BelongedBD"/>
        </td>

        <td class="list_range" >   
		<html:select name="belongBd" styleId="input_common1" beanName="webVo"  req="false" >          
            <html:optionsCollection name="webVo" property="belongBdList"/>
        </html:select>
        </td>
      </tr>
     
      <tr>
      <td class="list_range" >&nbsp;</td>
        <td class="list_range"><bean:message bundle="projectpre" key="customer.ClientShortName"/>
        </td>
        <td class="list_range" colspan="6">
		<html:text styleId="input_common2" name="customerShort" beanName="webVo" fieldtype="text" req="true" maxlength="25"/>
	</td>
      </tr>
      <tr>
      <td class="list_range" >&nbsp;</td>
        <td class="list_range"><bean:message bundle="projectpre" key="customer.ChineseFullName"/>
        </td>
        <td class="list_range" colspan="6">
		<html:text styleId="input_common2" name="customerNameCn" beanName="webVo" fieldtype="text" req="true" maxlength="250"/></td>
      </tr>
      <tr>
      <td class="list_range" >&nbsp;</td>
        <td class="list_range"><bean:message bundle="projectpre" key="customer.EnglishFullName"/>
        </td>
        <td class="list_range" colspan="6">
        <html:text styleId="input_common2" name="customerNameEn" beanName="webVo" fieldtype="text" req="true" maxlength="250"/></td>
      </tr>
      <tr>
      <td class="list_range" >&nbsp;</td>
       <td class="list_range">
          <bean:message bundle="projectpre" key="customer.CustomerDes"/>
        </td>
         <td class="list_range" colspan="6">
              <html:textarea name="custDescription" beanName="webVo" rows="3" styleId="description" req="false" maxlength="250" styleClass="TextAreaStyle" />
         </td>
      </tr>
	  <tr height="20">
	  <td class="list_range" >&nbsp;</td>
	  </tr>
	  </table>
<table width="700" border="0">
	   <tr>
	  	   <td class="list_range" width="5">&nbsp;</td>
	     <td class="list_range"  width="100">
	        <div id="hidTitle1" >
		     <bean:message bundle="projectpre" key="customer.CreateDate"/> 
		     </div>	</td>
		     <td>		   	     
		     <div id="hidContext1">
		     <html:text styleId="input_common3" name="createDate" beanName="webVo" fieldtype="text" readonly="true"/>
		     </div>
		     </td>
		     <td class="list_range" width="10">&nbsp;</td>
             <td class="list_range" width="90" >
		    <div id="hidTitle2">
		     <bean:message bundle="projectpre" key="customer.EditedDate"/>
		     </div></td>
		     <td>
		     <div id="hidContext2">
		      <html:text styleId="input_common3" name="alterDate" beanName="webVo" fieldtype="text" readonly="true"/> 
		   </div></td>
		<td class="list_range" width="20">&nbsp;</td>
              <td class="list_range" width="60" >
		    <div id="hidTitle3">
		     <bean:message bundle="projectpre" key="customer.Applicant"/>
		     </div></td>
		     <td>
		    <div id="hidContext3">
		        <html:text styleId="input_common3" name="creator" beanName="webVo" fieldtype="text" readonly="true"/>
            </div>
         </td>
	  </tr>
	  <tr></tr><tr></tr><tr></tr>
    </table>
	
	<table width="700" border="0" align="center">
	<tr></tr><tr></tr>
				<tr>
					<td class="list_range" width="400">&nbsp;</td>
					<td align="right">
					<html:button styleId="btnSave" name="btnSave" onclick="onSaveData()">
							<bean:message bundle="application" key="global.button.save" />
						</html:button>		
						<html:button styleId="btnSubmit" name="btnSubmit" onclick="onClickSubmit()">
							<bean:message bundle="application" key="global.button.submit" />
						</html:button>	
						
						<html:button styleId="btnCancel" name="btnCancel" onclick="onClickCancel()">
							<bean:message bundle="application" key="global.button.cancel" />
						</html:button>						
					</td>
					<td width="30"></td>
					
				</tr>
		</table>
		
  <script type="text/javascript" language="javascript">
     onCheck();
     //initial();
   </script>
    </html:form>
  </body>
</html>
