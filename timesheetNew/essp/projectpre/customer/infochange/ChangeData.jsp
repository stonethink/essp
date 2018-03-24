<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/inc/pagedef.jsp" %>
<bean:define id="rid" name="webVo" property="rid" scope="request" />
<bean:define id="contextPath" value="<%=request.getContextPath()%>"/>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <tiles:insert page="/layout/head.jsp">
       <tiles:put name="title" value=" "/>
       <tiles:put name="jspName" value=""/>
     </tiles:insert>
    <style type="text/css">
    
      #input_common1{width:200}
      #input_common2{width:635}
      #input_common3{width:160}
      #input_common4{width:210}
      #input_common5{width:115}
      #input_common6{width:120}
      #input_field{width:60}
      #rewt{width:635}
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
    //集团代号选择与否会显示或隐藏建案日期，最近变更日期及建档人三个栏位
    function SelectGroupId(){
       try{
         var hidTitle1=document.getElementById("hidTitle1");
         var hidTitle2=document.getElementById("hidTitle2");
         var hidTitle3=document.getElementById("hidTitle3");
         var hidContext1=document.getElementById("hidContext1");
         var hidContext2=document.getElementById("hidContext2");
         var hidContext3=document.getElementById("hidContext3");   
            
        if(document.all.attribute.value=="Company"){       
         if(document.all.groupId.value!= null && document.all.groupId.value!=""){        
            hidTitle1.style.visibility="visible";
            hidTitle2.style.visibility="visible";
            hidTitle3.style.visibility="visible";
            hidContext1.style.visibility="visible";
      	    hidContext2.style.visibility="visible";
      	    hidContext3.style.visibility="visible";
      	    document.forms[0].customerCountry.req="true";       
             document.forms[0].customerCountry.focus();    
             document.forms[0].customerCountry.blur(); 	 
             document.forms[0].regId.focus();
             document.forms[0].regId.blur();
        }else{
             hidTitle1.style.visibility="hidden";
             hidTitle2.style.visibility="hidden";
             hidTitle3.style.visibility="hidden";
      	     hidContext1.style.visibility="hidden";
      	     hidContext2.style.visibility="hidden";
             hidContext3.style.visibility="hidden";
         }  
        } 
         if(document.all.attribute.value=="Group"){
            var hidTitle1=document.getElementById("hidTitle1");
            var hidTitle2=document.getElementById("hidTitle2");
            var hidTitle3=document.getElementById("hidTitle3");
            var hidContext1=document.getElementById("hidContext1");
            var hidContext2=document.getElementById("hidContext2");
            var hidContext3=document.getElementById("hidContext3");    
           hidTitle1.style.visibility="hidden";
           hidTitle2.style.visibility="hidden";
           hidTitle3.style.visibility="hidden";
           hidContext1.style.visibility="hidden";
      	   hidContext2.style.visibility="hidden";
           hidContext3.style.visibility="hidden";
           document.forms[0].customerCountry.req="false";       
           document.forms[0].customerCountry.focus();    
           document.forms[0].customerCountry.blur();
           document.forms[0].regId.focus();
           document.forms[0].regId.blur(); 
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
        var form=document.forms[0];      
         if(!checkReq(form)){
         	return;
          }
          form.applicationStatus.value="UnSubmit";
          form.submit();
   
  }
  //用于判断状态值，如果是提交状态则各个栏位都灰掉
   function onInit(){
      try{  
      var status="<bean:write name="webVo" property="applicationStatus" scope="request" />"
      if(status=="Submitted"){  
       if(document.all.attribute.value=="Company"){
        document.all.groupId.disabled="ture";
        }
        window.parent.saveBtn.disabled="true";
        window.parent.submitBtn.disabled="true";
        document.all.customerId.disabled="ture";
        document.all.rid.disabled="ture";
      	document.all.regId.disabled="ture";   	
      	document.all.applicantName.disabled="ture";
      	document.all.customerShort.disabled="ture";
      	document.all.applicationStatus.disabled="ture";
      	document.all.customerNameCn.disabled="ture";
      	document.all.customerNameEn.disabled="ture";
      	document.all.belongBd.disabled="ture";
      	document.all.belongSite.disabled="ture";
      	document.all.customerClass.disabled="ture";
      	document.all.customerCountry.disabled="ture";
      	document.all.custDescription.disabled="ture";  
      } 
      
      var table = document.getElementById("gId");
      var rows = table.rows;
      rows[0].cells[1].innerText= document.all.shortName.value; 
 
       }catch(e){} 
   }
  //此方法用于提交数据	
    function onSubmitData(){ 
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
    
    //此方法用于当下帧中的数据发生改变时同步上帧中的数据
      function refreshUp(){
         try{     
        	var regId=document.getElementById("regId").value;        	
        	var customerId=document.getElementById("customerId").value; 
        	if(document.all.attribute.value=="Company"){     	
        	  var groupId=document.getElementById("groupId").value;   
        	} 	
        	var applicationStatus=document.getElementById("applicationStatus").value;
            var rid="<%=rid%>";         
        	var upFrame=window.parent.frames[0];
            var table=upFrame.document.getElementById("changeList_table");
        	for(i=1;i<table.rows.length;i++){        	
        		if(table.rows[i].selfproperty==rid){    		
        			var tds=table.rows[i].cells;    			
        			tds[1].innerText=regId;       			
        			tds[1].title=regId;
        			tds[2].innerText=customerId;       			
        			tds[2].title=customerId;   			
        			tds[5].innerText=applicationStatus;
        			tds[5].title=applicationStatus;
        			if(document.all.attribute.value=="Company"){  
        		      tds[3].innerText=groupId;
        			  tds[3].title=groupId;
        		 	}
        		}
        		if(applicationStatus=="Submitted") {
        	      var obj = window.parent;
        	      obj.parent.deleteBtn.disabled=true;
        	}
        	}
       	}catch(e){
    	}
        }
        
    
  </script>
  </head>
  
  <body onload="onInit();SelectGroupId();">
	<html:form id="CustomerApplication" method="post" action="/customer/change/updateCustApp" onsubmit="return onClickSubmit(this)" initialForcus="regId">
 	<table width="780" border="0" style="padding-left:8px">
 	<html:hidden name="attribute" beanName="webVo"/>
	
      <tr>
       <td class="list_range" >&nbsp;</td>
		<td class="list_range" width="200">
			<bean:message bundle="projectpre" key="customer.ApplyNo"/>
			</td>
			<td class="list_range" width="120">
		    <html:text styleId="input_common6" name="rid" beanName="webVo" fieldtype="text" readonly="true"/>
		</td>       
       <td class="list_range" width="120">
        <bean:message bundle="projectpre" key="deptCode.Applicant" /></td>
        <td width="100">
       <html:text styleId="input_common6" name="applicantName" beanName="webVo" fieldtype="text" readonly="true"/>
       </td>
       <td class="list_range" width="120">
		  <bean:message bundle="projectpre" key="projectCode.MasterData.StatusCode"/></td>
		  <td width="120">
		  <html:text styleId="input_common6" name="applicationStatus" beanName="webVo" fieldtype="text" readonly="true"  /></td>
      </tr>
    
      <tr>
      <td class="list_range" >&nbsp;</td>
        <td class="list_range"><bean:message bundle="projectpre" key="customer.ClientClassCode"/></td>
        <td width="100">
         <html:select name="customerClass" styleId="input_common6" beanName="webVo"  req="false" >          
            <html:optionsCollection name="webVo" property="customerClassList"/>
        </html:select> 
     </td>    
        <td class="list_range" >
         <bean:message bundle="projectpre" key="customer.ClientNo"/></td>
         <td>
         <html:text styleId="input_common6" name="customerId" beanName="webVo" fieldtype="text" readonly="true" />
       </td>
       <td class="list_range"><bean:message bundle="projectpre" key="customer.BelongedBD"/></td>
       <td>
		<html:select name="belongBd" styleId="input_common6" beanName="webVo"  req="false" >          
            <html:optionsCollection name="webVo" property="belongBdList"/>
        </html:select>
  
		</td>
      </tr>
      <tr>
      <td class="list_range" >&nbsp;</td>
         <td class="list_range"><bean:message bundle="projectpre" key="customer.ClientCountryCode"/>
        </td>
        <td width="120">
         <html:select name="customerCountry" styleId="input_common6" beanName="webVo"  req="false" >          
            <html:optionsCollection name="webVo" property="customerCountryList"/>
        </html:select> 
     </td>    
		 <td class="list_range"  ><bean:message bundle="projectpre" key="customer.UniformCode/RegisteredNumberofLicence"/></td>
		 <td>
        <html:text styleId="input_common6" name="regId" beanName="webVo" fieldtype="text" readonly="true" maxlength="25" />
        </td> 
     
     <td class="list_range" >
        <bean:message bundle="projectpre" key="customer.BelongedSite"/></td>
        <td>
		<html:select name="belongSite" styleId="input_common6" beanName="webVo"  req="false" >          
            <html:optionsCollection name="webVo" property="belongSiteList"/>
        </html:select></td>
      </tr> 
      <logic:equal name="company" value="true">  
      <tr>   
      <td class="list_range" >&nbsp;</td>
      <td class="list_range" width="200">
         <bean:message bundle="projectpre" key="customer.GroupNo"/>
         </td>
       <td class="list_range" width="120" colspan="2">
		<html:text name="groupId"
                      beanName="webVo"
                      fieldtype="text"
                      styleId="input_common6"
                      prev="dueDate"                    
		              readonly="true"	
                      imageSrc="<%=contextPath+"/image/qurey.gif"%>"
                      imageWidth="18"
                      imageOnclick="allocateHr()"
                      value=""
         />
        
	    </td>
         
    
      </tr>
      </logic:equal>
      <tr>
      <td class="list_range" >&nbsp;</td>
        <td class="list_range"><bean:message bundle="projectpre" key="customer.ClientShortName"/>
        </td>
        <td class="list_range"  colspan="5">
        <html:text styleId="input_common2" name="customerShort" beanName="webVo" fieldtype="text" req="true" maxlength="25"/>
        </td>  
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
        <html:text styleId="input_common2" name="customerNameEn" beanName="webVo" fieldtype="text" req="true"  maxlength="250"/></td>
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
       <tr>
         <logic:equal name="Desc" value="true">
      <td class="list_range" >&nbsp;</td>
    
        <td class="list_range">
          <bean:message bundle="projectpre" key="customer.ClientRemark"/>
          </td>
         <td class="list_range" colspan="5">
              <html:textarea name="description" beanName="webVo" rows="3" styleId="rewt" readonly="true" maxlength="250" styleClass="TextAreaStyle" />
         </td>
        </logic:equal>
      </tr>
	   <tr>
	  	   <td class="list_range" >&nbsp;</td>
	     <td class="list_range"  width="200">
	        <div id="hidTitle1" >
		     <bean:message bundle="projectpre" key="customer.CreateDate"/> 
		     </div>	</td>
		     <td width="120">		   	     
		     <div id="hidContext1"  > 
		     <html:text styleId="input_common6" name="createDate" beanName="webVo" fieldtype="text" readonly="true"/>
		     </div> 
		     </td>

             <td class="list_range" width="120" >
		    <div id="hidTitle2">
		     <bean:message bundle="projectpre" key="customer.EditedDate"/>
		     </div></td>
		     <td width="100">
		     <div id="hidContext2">
		     <html:text styleId="input_common6" name="alterDate" beanName="webVo" fieldtype="text" readonly="true"/>		     
		   </div></td>
           <td class="list_range" width="120" >
		    <div id="hidTitle3">
		     <bean:message bundle="projectpre" key="customer.Applicant"/>
		     </div></td>
		     <td width="120">
		    <div id="hidContext3">
		    <html:text styleId="input_common6" name="creator" beanName="webVo" fieldtype="text" readonly="true"/>	     
            </div>
         </td>
	  </tr>
    </table>
    <script type="text/javascript" language="javascript">
     refreshUp();
     </script>
   </html:form>
  </body>
</html>
