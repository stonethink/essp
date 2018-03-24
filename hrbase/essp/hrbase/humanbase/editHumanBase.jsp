<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/inc/pagedef.jsp"%>
<html>
	<head>
		<tiles:insert page="/layout/head.jsp">
			<tiles:put name="title" value="Edit Human Base Info" />
			<tiles:put name="jspName" value="editHumanBase" />
		</tiles:insert>
		<style>
			#input_common {
				width:155;
			}
		</style>
		<script type="text/javascript" language="JavaScript">
   function getMyDATE(dateName){
     try{
    	var date = document.getElementById(dateName);
    	date.focus();
    	getDATE(date);
    	} catch(e){}
     }
		
		function allocate(){
		    param = new HashMap();
		    var result = allocSingleInAD(param,"","",document.getElementsByName("resManagerId").value,"")
		    if(param!="") {
		       document.getElementsByName("resManagerId")[0].value=param.values()[0].loginId;
		       document.getElementsByName("resManagerName")[0].value=param.values()[0].name;
	       	}
    	}
    	
    	function sumbitUpdate(){
			if (editForm.employeeId.value==""){
		        showRequired();
		        editForm.employeeId.focus();
		        return;
		    }
			if (editForm.englishName.value==""){
		        showRequired();
		        editForm.englishName.focus();
		        return;
		    }
			if (editForm.chineseName.value==""){
		        showRequired();
		        editForm.chineseName.focus();
		        return;
		    }
		    
		    if (editForm.resManagerName.value==""){
		        showRequired();
		        editForm.resManagerName.focus();
		        return;
		    }
		
		    if (editForm.site.value=="" || editForm.site.value=="-1"){
		        showRequired();
		        editForm.site.focus();
		        return;
		    }
		
		    if (editForm.unitCode.value=="" || editForm.unitCode.value=="-1"){
		        showRequired();
		        editForm.unitCode.focus();
		        return;
		    }
		
		    if (editForm.attributeGroupRid.value=="" || editForm.attributeGroupRid.value=="-1"){
		        showRequired();
		        editForm.attributeGroupRid.focus();
		        return;
		    }
		
			if (editForm.inDate.value==""){
		        showRequired();
		        editForm.inDate.focus();
		        return;
		  	}
		  editForm.submit();
		}
		
		function showRequired() {
			alert('<bean:message bundle="application" key="global.confirm.require"/>');
		}
		
		function formalChanged(formal) {
			if(formal.value == "") {
				document.getElementById("formal_div").innerText = "";
			} else {
				document.getElementById("formal_div").innerText = "   " + formal.innerText;
			}
		}
		</script>
	</head>
	<body bgcolor="#ffffff" onload="this.focus();">
		<center class="form_title">
			<bean:message bundle="hrbase" key="hrbase.humanBase.title.edit"/>
		</center>
		<center>
			<html:form id="editForm" action="/hrbase/humanbase/editHumanBase" method="POST">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr height="20">
						<td height="20"><html:hidden name="rid" property="rid" beanName="webVo" /></td>
					</tr>
					<tr>
						<td width="20"></td>
						<td width="120" align="left" class="list_range">
							<bean:message bundle="hrbase" key="hrbase.humanBase.employeeId"/>
						</td>
						<td>
							<html:text name="employeeId" beanName="webVo" styleId="" readonly="true" fieldtype="text" prev="" next="englishName" req="true" />
						</td>
						<td width="60"></td>
						<td align="left" class="list_range" width="120">
							<bean:message bundle="hrbase" key="hrbase.humanBase.englishName"/>
						</td>
						<td>
							<html:text name="englishName" beanName="webVo" styleId="" fieldtype="text" prev="employeeId" next="chineseName" req="true" />
						</td>
						<td width="60"></td>
						<td align="left" class="list_range" width="120">
							<bean:message bundle="hrbase" key="hrbase.humanBase.chineseName"/>
						</td>
						<td>
							<html:text name="chineseName" beanName="webVo" styleId="" fieldtype="text" prev="englishName" next="title" req="true" />
						</td>
					</tr>

					<tr height="20">
						<td height="20"></td>
					</tr>
					<tr>
						<td width="20"></td>
						<td width="120" align="left" class="list_range">
							<bean:message bundle="hrbase" key="hrbase.humanBase.title"/>
						</td>
						<td>
							<html:text name="title" beanName="webVo" styleId="" fieldtype="text" prev="chineseName" next="rank" />
						</td>
						<td width="60"></td>
						<td align="left" class="list_range" width="120">
							<bean:message bundle="hrbase" key="hrbase.humanBase.rank"/>
						</td>
						<td>
							<html:text name="rank" beanName="webVo" styleId="" fieldtype="text" prev="title" next="resManagerName" />
						</td>
						<td width="60"></td>
						<td align="left" class="list_range" width="120">
							<bean:message bundle="hrbase" key="hrbase.humanBase.resManager"/>
						</td>
						<td>
						<html:hidden name="resManagerId" property="resManagerId" beanName="webVo"/>
							<html:text name="resManagerName"
		                      beanName="webVo"
		                      fieldtype="text"
		                      styleId="input_common"
		                      prev="rank"
		                      req="true"
		                      next="site"
		                      readonly="true"
		                      imageSrc="<%=request.getContextPath() + "/image/humanAllocate.gif"%>"
		                      imageWidth="18"
		                      imageOnclick="allocate()"
		                      />
						</td>
					</tr>
					<tr height="20">
						<td height="20"></td>
					</tr>
					<tr>
						<td width="20"></td>
						<td class="list_range" width="120">
							<bean:message bundle="hrbase" key="hrbase.humanBase.site"/>
						</td>
						<td width="120" class="list_range">
							<bean:define id="site" name="webVo" property="site"/>
							<ws:select id="input_common" property="site" default="<%=site.toString()%>" type="server.essp.hrbase.site.logic.SiteSelectImpl" req="true" onchange="siteSelectChangeEventunit_code('null');" orgIds="" style="background-color:#FFFACD;width:155"/>
						</td>
						<td width="60"></td>
						<td class="list_range" width="120">
							<bean:message bundle="hrbase" key="hrbase.dept.deptcode"/>
						</td>
						<td width="120">
							<bean:define id="unitCode" name="webVo" property="unitCode"/>
							<ws:upselect id="unit_code" property="unitCode" default="<%=unitCode.toString()%>" type="server.essp.hrbase.dept.logic.DeptSelectImpl" req="true" onchange="unitCodeSelectChangeEventaccount('null');" orgIds="" up="site" style="background-color:#FFFACD;width:155" />
						</td>
						<td width="60"></td>
						<td class="list_range" width="120">
							<bean:message bundle="hrbase" key="hrbase.humanBase.direct"/>
						</td>
						<td width="120" class="list_range">
							<html:select name="isDirect" value="Contract" styleId="input_common" beanName="webVo" prev="unitCode" next="inDate" req="true">
								<option value="D">
									Direct
								</option>
								<option value="I">
									Indirect
								</option>
							</html:select>
							<script type="text/javascript" language="JavaScript">
								document.all.isDirect.value = '<bean:write name="webVo" property="isDirect" />';
							</script>
						</td>
					</tr>
					<tr height="20">
						<td height="20"></td>
					</tr>

					<tr>
						<td width="20"></td>
						<td class="list_range" width="120">
							<bean:message bundle="hrbase" key="hrbase.humanBase.inDate"/>
						</td>
						<td>
						    <html:text name="inDate"
                      		beanName="webVo"
                      		fieldtype="dateyyyymmdd"
                      		styleId="narrow_date"
                      		imageSrc="<%=request.getContextPath()+"/image/cal.png"%>"
                      		imageWidth="18"
                      		imageOnclick="getMyDATE('inDate')"
                      		maxlength="10" 
                      		req="true"
                      		prev="attribute" 
                      		next="outDate"
                      		ondblclick="getDATE(this)"
                        />
						</td>
						<td width="20"></td>
						<td width="60" class="list_range">
							<bean:message bundle="hrbase" key="hrbase.humanBase.outDate"/>
						</td>
						<td>
						    <html:text name="outDate"
                      		beanName="webVo"
                      		fieldtype="dateyyyymmdd"
                      		styleId="narrow_date"
                      		imageSrc="<%=request.getContextPath()+"/image/cal.png"%>"
                      		imageWidth="18"
                      		imageOnclick="getMyDATE('outDate')"
                      		maxlength="10" 
                      		prev="inDate" 
                      		next="effectiveDate"
                      		ondblclick="getDATE(this)"
                            />
						</td>
						<td width="20"></td>
						<td width="60" class="list_range">
							<bean:message bundle="hrbase" key="hrbase.humanBase.effectiveDate"/>
						</td>
						<td>
						     <html:text name="effectiveDate"
                      		beanName="webVo"
                      		fieldtype="dateyyyymmdd"
                      		styleId="narrow_date"
                      		imageSrc="<%=request.getContextPath()+"/image/cal.png"%>"
                      		imageWidth="18"
                      		imageOnclick="getMyDATE('effectiveDate')"
                      		maxlength="10" 
                      		prev="outDate" 
                      		next="email"
                      		ondblclick="getDATE(this)"
                            />
						</td>
					</tr>

					<tr height="20">
						<td height="20"></td>
					</tr>
					
					<tr>
						<td width="20"></td>
						<td class="list_range" width="120" >
							<bean:message bundle="hrbase" key="hrbase.humanBase.hrAttribute" />
						</td>
						<td width="120" class="list_range">
							<bean:define id="attributeGroupRid" name="webVo" property="attributeGroupRid"/>
							<ws:upselect id="hr_att" property="attributeGroupRid" default="<%=attributeGroupRid.toString()%>" up="site" type="server.essp.hrbase.attributegroup.logic.HrAttSelectImpl" req="true" onchange="attributeGroupRidSelectChangeEventessp_attribute('null');attributeGroupRidSelectChangeEventatt_formal('null');" orgIds="" style="background-color:#FFFACD;width:155" />
						</td>
						<td width="60"></td>
						<td class="list_range" width="120">
							<bean:message bundle="hrbase" key="hrbase.humanBase.attribute" />
						</td>
						<td width="120">
							<ws:upselect id="essp_attribute" property="attribute" up="attributeGroupRid" type="server.essp.hrbase.attributegroup.logic.AttSelectImpl" orgIds="" style="width:155" disabled="true"/>
						</td>
						<td width="60"></td>
						<td class="list_range" width="120">
							<bean:message bundle="hrbase" key="hrbase.humanBase.formal" />
						</td>
						<td width="120" class="list_range">
							<div id="formal_div" style="font-size:14;"></div>
						</td>
					</tr>
					<tr height="20">
						<td height="20"></td><td width="60"></td>
						<td><ws:upselect id="att_formal" property="formal" up="attributeGroupRid" onchange="formalChanged(this);" type="server.essp.hrbase.attributegroup.logic.FormalSelectImpl" orgIds="" style="visibility:hidden;" disabled="true"/></td>
					</tr>

					<tr>
						<td width="20"></td>
						<td class="list_range" width="120">
							<bean:message bundle="hrbase" key="hrbase.humanBase.email"/>
						</td>
						<td>
							<html:text name="email" beanName="webVo" styleId="" fieldtype="text" prev="effectiveDate" next="Update" />
						</td>
						<td width="20"></td>
						<td width="60" class="list_range">
						</td>
						<td>
						</td>
						<td width="20"></td>
						<td width="60" class="list_range">
						</td>
						<td>
						</td>
					</tr>

				</table>
				<input type="button" class="button" name="Update" value='<bean:message bundle="application" key="global.button.ok"/>' onClick="sumbitUpdate();">
					    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" value='<bean:message bundle="application" key="global.button.close"/>' name="close" class="button" onClick="window.close();">
			</html:form>
		</center>
	</body>
</html>