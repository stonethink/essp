<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/inc/pagedef.jsp" %>
<bean:define id="rid" name="webVo" property="rid" scope="request" />
<%
  String flag1 = "false";
  boolean flag2 = true;
%>
<bean:define id="contextPath" value="<%=request.getContextPath()%>"/>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <tiles:insert page="/layout/head.jsp">
       <tiles:put name="title" value=" "/>
       <tiles:put name="jspName" value=""/>
     </tiles:insert>
    <style type="text/css">
      #input_common1{width:120}
      #input_common2{width:613}
      #input_common3{width:230}
      #input_common4{width:115}
      #input_common5{width:360}
      #input_field{width:60}
      #rewt{width:613}
    </style>
  <script type="text/JavaScript" src="<%=contextPath%>/js/queryCustomer.js"></script>
  <script language="JavaScript" type="text/JavaScript">

  //用于查询集团代号
   function allocateHr() {
      var form=document.forms[0];
      if(form.applicationStatus.value=="Submitted") return;
      if( document.all.attribute.value=="Group"){
	    alert("<bean:message bundle="application" key="global.is.groupId"/>");
	    return;
	  } 
     
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
    //用于提交数据
    function onSubmitData(){
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
    
     
     //用于控制选择集团或公司码时某些栏位的属性变化
    function onClickEvent(i){   
        try{
         var hidTitle1=document.getElementById("hidTitle1");
         var hidTitle2=document.getElementById("hidTitle2");
         var hidTitle3=document.getElementById("hidTitle3");
         var hidTitleG=document.getElementById("hidTitleG");       
         var hidContext1=document.getElementById("hidContext1");
         var hidContext2=document.getElementById("hidContext2");
         var hidContext3=document.getElementById("hidContext3");
         var hidContextG=document.getElementById("hidContextG");

      if(i==2){
        flag1 = "true";
        flag2 = false;               
        
        hidTitleG.style.visibility="visible";
        hidContextG.style.visibility="visible";
        if(document.all.groupId.value!= null && document.all.groupId.value!=""){
        hidTitle1.style.visibility="visible";
        hidTitle2.style.visibility="visible";
        hidTitle3.style.visibility="visible";
        hidContext1.style.visibility="visible";
        hidContext2.style.visibility="visible";
        hidContext3.style.visibility="visible";
       }
        document.forms[0].customerCountry.req=flag1;      
        document.forms[0].customerCountry.focus();
        document.forms[0].customerCountry.blur();
      
      }
      if(i==1){
    
        flag1 = "false";
        flag2 = true; 
        hidTitle1.style.visibility="hidden";
        hidTitle2.style.visibility="hidden";
        hidTitle3.style.visibility="hidden";
        hidTitleG.style.visibility="hidden";
        hidContext1.style.visibility="hidden";
        hidContext2.style.visibility="hidden";
        hidContext3.style.visibility="hidden";
        hidContextG.style.visibility="hidden";     
        document.forms[0].customerCountry.req=flag1;
        document.forms[0].customerCountry.focus();       
        } 
        }catch(e){}
    }
     
     //用于初始化页面
     function onInit(){
      try{   
      if(document.all.attribute[0].checked){
      	    onClickEvent(1);
      	 } else{
      	    onClickEvent(2);
      	 }
      var obj = window.parent; 	
   	
       var status="<bean:write name="webVo" property="applicationStatus" scope="request" />"
       if(status=="Submitted"){
        window.parent.saveBtn.disabled="true";
        window.parent.submitBtn.disabled="true";
      	document.all.attribute[0].disabled="ture";
      	document.all.attribute[1].disabled="ture";
      	document.all.rid.disabled="ture";
      	document.all.regId.disabled="ture";
      	document.all.groupId.disabled="ture";
      	document.all.customerShort.disabled="ture";
      	document.all.applicationStatus.disabled="ture";
      	document.all.customerNameCn.disabled="ture";
      	document.all.customerNameEn.disabled="ture";
      	document.all.belongBd.disabled="ture";
      	document.all.belongSite.disabled="ture";
      	document.all.customerClass.disabled="ture";
      	document.all.customerCountry.disabled="ture";
      	document.all.custDescription.disabled="ture";
      	document.all.createDate.disabled="ture";
      	document.all.alterDate.disabled="ture";
      	document.all.applicantName.disabled="ture";
      }  
       }catch(e){} 
   }
    

     
     //此方法用于检查必填项
    function checkReq(form){
     if(!submitForm(form)){
	 	return false;
      }else{
     	return true;
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
      //此方法用于当下帧中的数据发生改变时同步上帧中的数据
      function refreshUp(){
     
         try{
        	var regId=document.getElementById("regId").value;
        	var groupId=document.getElementById("groupId").value;
        	var customerShort=document.getElementById("customerShort").value;
        	var applicationStatus=document.getElementById("applicationStatus").value;
            var rid="<%=rid%>";
        	var upFrame=window.parent.frames[0];
            var table=upFrame.document.getElementById("customerCodeList_table");
        	for(i=1;i<table.rows.length;i++){
        	
        		if(table.rows[i].selfproperty==rid){       		    
        			var tds=table.rows[i].cells; 			
        			tds[1].innerText=regId;
        			tds[1].title=regId;
        			tds[2].innerText=customerShort;
        			tds[2].title=customerShort;
        		    tds[3].innerText=groupId;
        			tds[3].title=groupId;
        			tds[5].innerText=applicationStatus;
        			tds[5].title=applicationStatus;   			
        		}
        	}
        	if(applicationStatus=="Submitted") {
        	      var obj = window.parent;
        	      obj.parent.deleteBtn.disabled=true;
        	}
       	}catch(e){
     	}
        }
        //集团代号选择与否会显示或隐藏建案日期，最近变更日期及建档人三个栏位
        function SelectGroupId(){
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
       }catch(e){}
        }
  </script>
  </head>
  
  <body onload="onInit();">
    <html:form id="CustomerApplication" action="/customer/add/updateCustApp" onsubmit="return onClickSubmit(this)" initialForcus="regId">
 	<table width="300" border="0" style="padding-left:8px">
 
      <tr>
       <td class="list_range" >&nbsp;</td>
         <td class="list_range" width="120">
        <html:radiobutton styleId=""  name="attribute"  beanName="webVo" value="Group" onclick="onClickEvent(1)"  />
          <bean:message bundle="projectpre" key="cusomer.Group"/>
        </td>
      <td class="list_range" width="20">&nbsp;</td>
        <td class="list_range" width="120">
        <html:radiobutton styleId=""  name="attribute"  beanName="webVo" value="Company" onclick="onClickEvent(2)"  />
	     <bean:message bundle="projectpre" key="customer.CompanyCode"/>
	     </td>
	     <td class="list_range" width="30"></td>
	
      </tr>
      </table>
      <table width="800" border="0">
      <tr>
       <td class="list_range" >&nbsp;</td>
       <td class="list_range" width="180" >
        <bean:message bundle="projectpre" key="customer.ApplyNo"/></td>
        <td> 
        <html:text styleId="input_common1" name="rid" beanName="webVo" fieldtype="text" readonly="true"  />  
        </td>
          <td class="list_range">
		   <bean:message bundle="projectpre" key="customer.StatusCode"/>
		   </td>
		   <td>
		  <html:text styleId="input_common1" name="applicationStatus" beanName="webVo" fieldtype="text" readonly="true"  maxlength="4"/>
		  </td>
        <td class="list_range" width="80"><bean:message bundle="projectpre" key="customer.UniformCode/RegisteredNumberofLicence"/>
        </td>
        <td class="list_range" >
        <html:text styleId="input_common1" name="regId" beanName="webVo" fieldtype="text" req="false" maxlength="25" />
        </td>
	   
      </tr>
    
      
      <tr>
      <td class="list_range" >&nbsp;</td>
        <td class="list_range" width="180"><bean:message bundle="projectpre" key="customer.ClientCountryCode"/>
        </td>
        <td class="list_range" >
          <html:select name="customerCountry" styleId="input_common1" beanName="webVo"  req="false" >          
            <html:optionsCollection name="webVo" property="customerCountryList"/>
        </html:select> 
		</td>
        <td class="list_range" width="100" > 
         <bean:message bundle="projectpre" key="customer.BelongedSite"/></td>
         <td>
		 <html:select name="belongSite" styleId="input_common1" beanName="webVo"  req="false" >          
            <html:optionsCollection name="webVo" property="belongSiteList"/>
        </html:select>
       </td>
       <td class="list_range" ><bean:message bundle="projectpre" key="customer.BelongedBD"/>
        </td>
        <td class="list_range" >   
		<html:select name="belongBd" styleId="input_common1" beanName="webVo"  req="false" >          
            <html:optionsCollection name="webVo" property="belongBdList"/>
        </html:select></td>
      </tr>
      <tr>
      <td class="list_range" >&nbsp;</td>
        <td class="list_range"><bean:message bundle="projectpre" key="customer.ClientClassCode"/>
        </td>
        <td class="list_range" >
        <html:select name="customerClass" styleId="input_common1" beanName="webVo"  req="false" >          
            <html:optionsCollection name="webVo" property="customerClassList"/>
        </html:select> 
		</td>
         <td class="list_range" width="100">
         <div id="hidTitleG" ><bean:message bundle="projectpre" key="customer.GroupNo"/></div>
         </td>
 
       <td class="list_range" width="10" colspan="3">
		<div id="hidContextG" ><html:text name="groupId"
                      beanName="webVo"
                      fieldtype="text"
                      styleId="input_common1"
                      prev="dueDate"                    
		              readonly="true"	
                      imageSrc="<%=contextPath+"/image/qurey.gif"%>"
                      imageWidth="18"
                      imageOnclick="allocateHr()"
                      value=""
         /></div>
  
	    </td>
	    <td class="list_range" >&nbsp;</td>
	    <td class="list_range" >&nbsp;</td>
      </tr>

      <tr>
      <td class="list_range" >&nbsp;</td>
        <td class="list_range"><bean:message bundle="projectpre" key="customer.ClientShortName"/>
        </td>
        <td class="list_range" colspan="5">
		<html:text styleId="input_common2" name="customerShort" beanName="webVo" fieldtype="text" req="true" maxlength="25"/></td>
      </tr>
      <tr>
      <td class="list_range" >&nbsp;</td>
        <td class="list_range"><bean:message bundle="projectpre" key="customer.ChineseFullName"/>
        </td>
        <td class="list_range" colspan="5">
		<html:text styleId="input_common2" name="customerNameCn" beanName="webVo" fieldtype="text" req="true" maxlength="250"/></td>
      </tr>
      <tr>
      <td class="list_range" >&nbsp;</td>
        <td class="list_range"><bean:message bundle="projectpre" key="customer.EnglishFullName"/>
        </td>
        <td class="list_range" colspan="5">
        <html:text styleId="input_common2" name="customerNameEn" beanName="webVo" fieldtype="text" req="true" maxlength="250"/></td>
      </tr>
      <tr>
      <td class="list_range" >&nbsp;</td>
        <td class="list_range">
          <bean:message bundle="projectpre" key="customer.CustomerDes"/>
        </td>
         <td class="list_range" colspan="5">
              <html:textarea name="custDescription" beanName="webVo" rows="3" styleId="rewt" req="false" maxlength="250" styleClass="TextAreaStyle" />
         </td>
      </tr>
       <logic:equal name="Desc" value="true">
      <tr>
      <td class="list_range" >&nbsp;</td>
        <td class="list_range">
        
          <bean:message bundle="projectpre" key="customer.ClientRemark"/>
          </td>
         <td class="list_range" colspan="5">
      
              <html:textarea name="description" beanName="webVo" rows="3" styleId="rewt" readonly="true" maxlength="250" styleClass="TextAreaStyle" />
        </td>
      </tr>
      </logic:equal>
   
	   <tr>
	  	   <td class="list_range" width="5">&nbsp;</td>
	     <td class="list_range"  width="120">
	        <div id="hidTitle1" >
		     <bean:message bundle="projectpre" key="customer.CreateDate"/> 
		     </div>	</td>
		     <td>		   	     
		     <div id="hidContext1">
		     <html:text styleId="input_common1" name="createDate" beanName="webVo" fieldtype="text" readonly="true"/>
		     </div> 

             <td class="list_range" width="100" >
		    <div id="hidTitle2">
		     <bean:message bundle="projectpre" key="customer.EditedDate"/>
		     </div></td>
		     <td>
		     <div id="hidContext2">
		     <html:text styleId="input_common1" name="alterDate" beanName="webVo" fieldtype="text" readonly="true"/>
		     
		   </div></td>
              <td class="list_range" width="70" >
		    <div id="hidTitle3">
		     <bean:message bundle="projectpre" key="customer.Applicant"/>
		     </div></td>
		     <td>
		    <div id="hidContext3">
		    <html:text styleId="input_common1" name="creator" beanName="webVo" fieldtype="text" readonly="true"/>
            </div>
         </td>
	  </tr>
    </table>
    <script type="text/javascript" language="javascript">
     refreshUp();   
     SelectGroupId();
    </script>
    </html:form>
  </body>
</html>
