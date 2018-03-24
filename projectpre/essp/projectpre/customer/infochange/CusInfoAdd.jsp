<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/inc/pagedef.jsp"%>

<bean:define id="contextPath" value="<%=request.getContextPath()%>" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<TITLE><bean:message bundle="projectpre" key="customer.CustomerInfoChange.cardTitle.AppliedCustomerCode" /></TITLE>
		<tiles:insert page="/layout/head.jsp">
			<tiles:put name="title" value=" " />
			<tiles:put name="jspName" value="" />
		</tiles:insert>
		<style type="text/css">
      #input_common1{width:120}
      #input_common2{width:505}
      #input_common3{width:180}
      #input_common4{width:270}
      #input_field{width:60}
      #description{width:505}
    </style>
    <script type="text/JavaScript" src="<%=contextPath%>/js/queryCustomer.js"></script>
	<script language="JavaScript" type="text/JavaScript">
	 //用于查询集团代号
	function allocateHr() {
	if( document.all.attribute.value=="Group"){
	  alert("<bean:message bundle="application" key="global.is.groupId"/>");
	  return;
	  }
      var param = new HashMap();
      param.put("attribute",new Array("Group","Attribute='Group'"));
      var result = queryCustomer(param);        
      if(result!=null){  
      document.getElementsByName("groupId")[0].value = result.id;
      var table = document.getElementById("gId");
      var rows = table.rows;
      rows[0].cells[1].innerText= result.short_name;  
      SelectGroupId();
      document.all.createDate.value=result.create_date;
      document.all.creator.value=result.creator;
     
      }
    }
     //集团代号选择与否会显示或隐藏建案日期，最近变更日期及建档人三个栏位
    function SelectGroupId(){
     try{
         var hidTitle1=document.getElementById("hidTitle1");       
         var hidTitle3=document.getElementById("hidTitle3");
         var hidContext1=document.getElementById("hidContext1");
         var hidContext3=document.getElementById("hidContext3");    
         if(document.all.groupId.value!= null && document.all.groupId.value!=""){
            hidTitle1.style.visibility="visible";
            hidTitle3.style.visibility="visible";
            hidContext1.style.visibility="visible";
      	    hidContext3.style.visibility="visible";

        }else{
             hidTitle1.style.visibility="hidden";
             hidTitle3.style.visibility="hidden";
      	     hidContext1.style.visibility="hidden";
             hidContext3.style.visibility="hidden";
        }   
        }catch(e){} 
     }
  
    //用于提交数据
     function onClickSubmit(){
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
 
  //此方法用于保存数据	
       function onSaveData(){
        var form=document.forms[0];
         if(!checkReq(form)){
         	return;
          }
          form.applicationStatus.value="UnSubmit";
          form.submit();
    }
    //用于检查必填项
  function checkReq(form){
	     if(!submitForm(form)){
    	 	return false;
	     } else {
	        return true;
	     }
	     
     }
        
 //用于取消操作
     function onClickCancel(){
       this.close();
     }
     //选择客户编号时会显示相应的数据
     function SelectCustomerId(){
      if(document.all.customerId.value==null ||document.all.customerId.value==""){
       document.forms[0].action="<%=contextPath%>/customer/change/initialCust.do";
       document.forms[0].submit();
      }else{
       document.forms[0].action="<%=contextPath%>/customer/change/selectCustId.do";
       document.forms[0].submit();
     }
    } 
  //初始化页面
     function initial(){
     try{
         var hidTitle1=document.getElementById("hidTitle1");
         var hidTitle3=document.getElementById("hidTitle3");
         var hidContext1=document.getElementById("hidContext1");
         var hidContext3=document.getElementById("hidContext3");  
             hidTitle1.style.visibility="hidden";
             hidTitle3.style.visibility="hidden";
      	     hidContext1.style.visibility="hidden";
             hidContext3.style.visibility="hidden";  
             var table=document.getElementById("gId");
             var rows=table.rows;     
             rows[0].cells[1].innerText=document.all.shortName.value;   
      	 } catch(e){} 
       
     }
  </script>
	</head>

	<body>
		<html:form id="CustomerApplication" method="post" action="/customer/change/addCustApp" onsubmit="return onClickSubmit(this)" initialForcus="regId">
			<html:hidden name="applicationStatus" beanName="webVo"/>
			<html:hidden name="attribute" beanName="webVo"/>
			<html:hidden name="rid" beanName="webVo"/>
			<html:hidden name="applicantName" beanName="webVo"/>
			<html:hidden name="countryCode" beanName="webVo"/>
			<table align=center>
				<tr>
					<td class="list_range" width="120">
						<bean:message bundle="projectpre" key="customer.CustomerInfoChange.cardTitle.AppliedCustomerCode" />
					</td>
				</tr>
			</table>
			<table width="780" align="center">			
				<tr>
				
				
			        <td class="list_range" width="120">
						<bean:message bundle="projectpre" key="customer.ClientNo" /></td>
			       <td class="list_range" width="180">
						<html:select name="customerId" styleId="input_common3" beanName="webVo" req="true" onchange="SelectCustomerId();">
							<html:optionsCollection name="webVo" property="customerIdList" />
						</html:select>
					</td>
					
					<td class="list_range" width="150">			
						<bean:message bundle="projectpre" key="customer.UniformCode/RegisteredNumberofLicence" /></td>
						<td class="list_range" width="80">	
						<html:text styleId="input_common1" name="regId" beanName="webVo" fieldtype="text" readonly="true" />
					</td>
			 
				</tr>

				<tr>
					
					<td class="list_range" width="120">
					<bean:message bundle="projectpre" key="customer.BelongedSite" /></td>
					<td class="list_range"><html:select name="belongSite" styleId="input_common3" beanName="webVo" req="false">							
						<html:optionsCollection name="webVo" property="belongSiteList" />
						</html:select>
					</td>
					
			    <td class="list_range"  width="150">
			      <bean:message bundle="projectpre" key="customer.ClientCountryCode" /></td>
             	<td class="list_range" width="80">	
				   <html:select name="customerCountry" styleId="input_common1" beanName="webVo"  req="false" >          
                      <html:optionsCollection name="webVo" property="customerCountryList"/>
                   </html:select>
                   </td>            	  				
		</tr>
				<tr>
				
					<td class="list_range" width="120">
						<bean:message bundle="projectpre" key="customer.BelongedBD" />
					</td>
					<td class="list_range" >
						<html:select name="belongBd" styleId="input_common3" beanName="webVo" req="false">
							<html:optionsCollection name="webVo" property="belongBdList" />
						</html:select>					
					</td>
					
					<td class="list_range"  width="150">
						<bean:message bundle="projectpre" key="customer.ClientClassCode" /></td>
						<td class="list_range" width="80">	
				   <html:select name="customerClass" styleId="input_common1" beanName="webVo"  req="false" >          
                      <html:optionsCollection name="webVo" property="customerClassList"/>
                   </html:select>
                   </td>  	  			
				</tr>
				<logic:equal name="company" value="true">
				<tr>
			
				<td class="list_range" width="120">
						<bean:message bundle="projectpre" key="customer.GroupNo" />
				</td>						
				<td class="list_range" colspan="4">
		        <TABLE id="gId" width="500">
                <TR>
                <TD>
	       	   <html:text name="groupId"
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
          </TD>
         <TD  class="list_range" width="400">
         </TD >    
         </TR>
         </TABLE>
		</td>	
	
				</tr>	
				</logic:equal>		
				<tr>
					
					<td class="list_range">
						<bean:message bundle="projectpre" key="customer.ClientShortName" />
					</td>
					<td class="list_range" colspan="4">
						<html:text styleId="input_common2" name="customerShort" beanName="webVo" fieldtype="text" req="true" maxlength="25" />
					</td>															
				</tr>
         <tr>
					
					<td class="list_range" width="120">
						<bean:message bundle="projectpre" key="customer.ChineseFullName" />
					</td>
					<td class="list_range" colspan="4">
						<html:text styleId="input_common2" name="customerNameCn" beanName="webVo" fieldtype="text" req="true" maxlength="250" />
					</td>
				</tr>
				<tr>
					
					<td class="list_range" width="120">
						<bean:message bundle="projectpre" key="customer.EnglishFullName" />
					</td>
					<td class="list_range" colspan="3">
						<html:text styleId="input_common2" name="customerNameEn" beanName="webVo" fieldtype="text" req="true"  maxlength="250"/>
					</td>
				</tr>
				<tr>
				
					<td class="list_range" width="120">
						<bean:message bundle="projectpre" key="customer.CustomerDes" />
					</td>
					<td class="list_range" colspan="3">
						<html:textarea name="custDescription" beanName="webVo" rows="3" styleId="description" req="false" maxlength="250" styleClass="TextAreaStyle" />
					</td>
				</tr>
				<tr height="10">
					<td class="list_range">
						 &nbsp;
					</td>
		  </tr>
	      <tr>
	  	 	
	     <td class="list_range"  width="120">
	        <div id="hidTitle1" >
		     <bean:message bundle="projectpre" key="customer.CreateDate"/> 
		     </div>	</td>
		     <td>		   	     
		     <div id="hidContext1">
		     <html:text styleId="input_common1" name="createDate" beanName="webVo" fieldtype="text" readonly="true"/>
		     </div> 
		     </td>
		     <td width="80"></td>
             <td class="list_range" width="60" >
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
			<table width="680" border="0" align="center">
			<tr height="10">
					<td class="list_range">
						 &nbsp;
					</td>
		  </tr>
				<tr>
					<td width="400"></td>
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
			        <td width="62"></td>
				</tr>
			</table>
	<script type="text/javascript" language="javascript">
     initial();
     SelectGroupId();
     </script>
  </html:form>
	
	</body>
</html>
