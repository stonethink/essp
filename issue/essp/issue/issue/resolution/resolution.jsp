<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.*,c2s.essp.common.user.DtoUser"%>
<%@page import="itf.hr.HrFactory"%>
<%@include file="/inc/pagedef.jsp"%>
<bean:define id="contextPath" value="<%=request.getContextPath()%>"/>

<bean:define id="principalFlag" name="webVo" property="principalFlag"/>
<bean:define id="pmFlag" name="webVo" property="pmFlag"/>
<bean:define id="customerFlag" name="webVo" property="customerFlag"/>
<%
boolean readonlyFlag=true;
if(pmFlag.equals("true") || principalFlag.equals("true") || customerFlag.equals("true")) {
    readonlyFlag=false;
}

DtoUser user=(DtoUser)session.getAttribute(DtoUser.SESSION_USER);
//判断是否为客�?若为客户不能A&A和Solution卡片
boolean isCustomer = false;
isCustomer = DtoUser.USER_TYPE_CUST.equals(user.getUserType());
%>
<%java.util.List categoryList = new java.util.ArrayList();%>
<logic:notEmpty name="webVo" property="categories">
  <logic:iterate id="categoryItem" name="webVo" property="categories" indexId="currentIndex">
  <%
    int iIndex = ((Integer) currentIndex).intValue();
    java.util.HashMap groupMap = null;
    if (iIndex % 2 == 0) {
      groupMap = new java.util.HashMap();
      categoryList.add(groupMap);
    }
    else {
      int size = categoryList.size();
      groupMap = (java.util.HashMap) categoryList.get(size - 1);
    }
    groupMap.put("item" + (iIndex % 2), categoryItem);
  %>
  </logic:iterate>
</logic:notEmpty>

<html>
<head>
<tiles:insert page="/layout/head.jsp">
  <tiles:put name="title" value=" Issue Resolution"/>
  <tiles:put name="jspName" value=""/>
</tiles:insert>
<style type="text/css">
  #probability {width:40;}
  #resolutionBy {width:118;}
  #resolutionByCustomer {width:118;}
  #input_field {width:100%;word-wrap: break-word;}
  #date_field {width:100;word-wrap: break-word;}
  .table_title {
  FONT-SIZE: 13px; COLOR: Black; FONT-STYLE: normal; FONT-FAMILY: &quot;Arial, Helvetica, sans-serif&quot;; TEXT-ALIGN: left;
  font-weight: bold;
  border-bottom-color: Black;
  border-bottom-style: inset;
  border-bottom-width: 1px;
  border-collapse: separate;
  }
  .textAreaStyle {
        width:100%;
        font-family: Arial, Helvetica, sans-serif;
	    font-size: 12px; }
  #input_text{width:150}
</style>
<script language="JavaScript" src="<%=contextPath%>/js/humanAllocate.js"></script>
<script language="javascript" src="<%=contextPath%>/issue/issue/resolution/changeTip.js"></script>
<script language="javascript">


function allocateHr(){
  var resBy_id=document.getElementsByName("resolutionBy")[0];
  var oldValue = resBy_id.value;
  var resBy_name = document.resolutionForm.resolutionByName;
  var oldName = resBy_name.value;
  var acntRid=document.resolutionForm.accountId.value;
  //sType="single";
  sType="multi";
  var result=oldValue;
  if(acntRid==null || acntRid=="") {
      alert("Please select a account");
      return;
  }
//  var result = window.showModalDialog("/essp/common/humanAllocate/alloc/UserAlloc.do?type="+sType+"&oldValue="+oldValue+"&acntRid="+acntRid+"&requestStr=" , "","dialogHeight:500px;dialogWidth:740px;dialogLeft:200; dialogTop:100");
    var param = new HashMap();
    var oldValues = oldValue.split(",");
    var oldNames = oldName.split(",");
    for(var i=0; i < oldValues.length; i++) {
      var oldUser = new allocUser(oldValues[i],oldNames[i],"","");
      param.put(oldValues[i],oldUser);
    }
    var result = allocMultipleInALL(param,acntRid,"","");
    var results = param.values();
    resBy_id.value = results[0].loginId;
    resBy_name.value = results[0].name;
    resBy_name.title = results[0].loginId+"/"+results[0].name;
    for(var j=1; j < results.length; j++) {
      resBy_id.value = resBy_id.value+"," + results[j].loginId;
      resBy_name.value = resBy_name.value + "," + results[j].name;
      resBy_name.title = resBy_name.title + "," + results[j].loginId+"/"+results[j].name;
    }
}

function computeRisk() {
    <logic:notEmpty name="webVo" property="influences">
            var riskLevelValue=0;
            var sum_impactLevel=0;
            var sum_weight=0;
            var currentImpactLevel=0;
            var currentWeight=0;
            var currentProbability;
            <bean:define id="influences" name="webVo" property="influences"/>
            <% int influenceSize=((java.util.List)influences).size(); %>
            <% if(influenceSize>1) { %>
            <logic:iterate id="influenceItem" name="webVo" property="influences" indexId="currentIndex">
            currentImpactLevel=document.resolutionForm.impactLevels[<%=currentIndex%>].value;
            if(currentImpactLevel==""){
                currentImpactLevel=0;
            }
            currentWeight=document.resolutionForm.weights[<%=currentIndex%>].value;
            if(currentWeight==""){
                currentWeight=0;
            }
            currentImpactLevel=parseFloat(currentImpactLevel);
            currentWeight=parseFloat(currentWeight);
            sum_weight=sum_weight+currentWeight;
            sum_impactLevel=sum_impactLevel+currentImpactLevel*currentWeight;
            </logic:iterate>
            currentProbability=document.resolutionForm.probability.value;
            currentProbability=parseFloat(currentProbability);
            riskLevelValue=(sum_impactLevel/sum_weight)*currentProbability/100;
            document.resolutionForm.riskLevel.value=jsFormatNumberSys(riskLevelValue,document.resolutionForm.riskLevel.fmt);
            <% } else if(influenceSize==1) { %>
            currentImpactLevel=document.resolutionForm.impactLevels.value;
            currentWeight=document.resolutionForm.weights.value;
            currentImpactLevel=parseFloat(currentImpactLevel);
            currentWeight=parseFloat(currentWeight);
            sum_weight=sum_weight+currentWeight;
            sum_impactLevel=sum_impactLevel+currentImpactLevel*currentWeight;
            currentProbability=document.resolutionForm.probability.value;
            currentProbability=parseFloat(currentProbability);
            riskLevelValue=(sum_impactLevel/sum_weight)*currentProbability/100;
            document.resolutionForm.riskLevel.value=jsFormatNumberSys(riskLevelValue,document.resolutionForm.riskLevel.fmt);
            <% } %>
    </logic:notEmpty>
}

function onSaveData(){
    <%
    if(readonlyFlag) {
        %>
        return;
        <%
    }
    %>
    <logic:notEmpty name="webVo" property="influences">
    if(resolutionForm.probability.value=="") {
        alert("Please input probability");
        document.resolutionForm.probability.focus();
        return;
    }
    </logic:notEmpty>
    if(resolutionForm.resolution.value=="") {
        alert("Please input resolution");
        document.resolutionForm.resolution.focus();
        return;
    }
    if(resolutionForm.resolutionBy.value=="") {
        alert("Please input resolution by");
        document.resolutionForm.resolutionBy.focus();
        return;
    }
    resolutionForm.submit();
    return true;
}
function onSaveAndSendData(param){

    resolutionForm.isMail.value="true";
    onSaveData();
}

function onBodyLoad(){
 <logic:equal value="true" name="webVo" property="isMail">
    var height = 500;
    var width = 680;
    var topis = (screen.height - height) / 2;
    var leftis = (screen.width - width) / 2;
    var option = "height=" + height + "px"  +", width=" + width + "px" +", top=" + topis + "px" +", left=" + leftis + "px"  +", toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no";
    var id = '<bean:write name="webVo" property="rid" />';
    window.open("<%=contextPath%>/issue/issue/GeneralResolution.do?id="+id,"",option);
</logic:equal>
}
</script></head>
<body onload="onBodyLoad();computeRisk()">

<html:form name="resolutionForm" action="<%=contextPath+"/issue/issue/issueResolution.do"%>" enctype="multipart/form-data">
  <input type="hidden" name="isMail" value="false" />
  <html:hidden name="rid" beanName="webVo"/>
  <html:hidden name="accountId" beanName="webVo"/>

  <bean:define id="probabilityFlag1" name="webVo" property="probabilityFlag"/>
  <bean:define id="impactLevelFlag1" name="webVo" property="impactLevelFlag"/>
  <bean:define id="weightLevelFlag1" name="webVo" property="weightLevelFlag"/>
  <bean:define id="remarkFlag1" name="webVo" property="remarkFlag"/>
  <bean:define id="categoryValueFlag1" name="webVo" property="categoryValueFlag"/>
  <bean:define id="resolutionFlag1" name="webVo" property="resolutionFlag"/>
  <bean:define id="planFinishDateFlag1" name="webVo" property="planFinishDateFlag"/>
  <bean:define id="resolutionByFlag1" name="webVo" property="resolutionByFlag"/>
  <bean:define id="assignedDateFlag1" name="webVo" property="assignedDateFlag"/>
  <bean:define id="attachmentFlag1" value="<%="true"%>"/>
  <bean:define id="attachmentDescFlag1" value="<%="true"%>"/>
  <%
  boolean probabilityFlag=!(Boolean.valueOf((String)probabilityFlag1).booleanValue());
  boolean impactLevelFlag=!(Boolean.valueOf((String)impactLevelFlag1).booleanValue());
  boolean weightLevelFlag=!(Boolean.valueOf((String)weightLevelFlag1).booleanValue());
  boolean remarkFlag=!(Boolean.valueOf((String)remarkFlag1).booleanValue());
  boolean categoryValueFlag=!(Boolean.valueOf((String)categoryValueFlag1).booleanValue());
  boolean resolutionFlag=!(Boolean.valueOf((String)resolutionFlag1).booleanValue());
  boolean planFinishDateFlag=!(Boolean.valueOf((String)planFinishDateFlag1).booleanValue());
  boolean resolutionByFlag=!(Boolean.valueOf((String)resolutionByFlag1).booleanValue());
  boolean assignedDateFlag=!(Boolean.valueOf((String)assignedDateFlag1).booleanValue());
  boolean attachmentFlag=!(Boolean.valueOf((String)attachmentFlag1).booleanValue());
  boolean attachmentDescFlag=!(Boolean.valueOf((String)attachmentDescFlag1).booleanValue());
  %>

  <TABLE id="Table1" cellSpacing="0" cellPadding="0" width="98%" border="0" align="center">
    <logic:notEmpty name="webVo" property="influences">
    <tr>
      <td class="list_range" width="80">Probability</td>
      <td class="list_range">
        <html:text name="probability" beanName="webVo" fieldtype="number" fmt="3.1" styleId="probability" next="" prev="" req="true" onblur="computeRisk()" readonly="<%=probabilityFlag%>"/>%
      </td>
      <td class="list_range" width="*" align="left" colspan="4">&nbsp;</td>
      <td class="list_range">Impact Level</td>
      <td class="list_range" width="*">
        <html:text name="riskLevel" beanName="webVo" fieldtype="number" fmt="3.1" styleId="input_field" readonly="true" next="" prev=""/>
      </td>
    </tr>
     </logic:notEmpty>
     <logic:notEmpty name="webVo" property="influences">
    <tr>
      <td colspan="8" height="3">
        <hr style="color:#336699;height:2px"/>
      </td>
    </tr>
     </logic:notEmpty>
    <tr>
      <td colspan="8">
        <table width="100%" cellSpacing="0" cellPadding="0" align="right">
           <logic:notEmpty name="webVo" property="influences">
            <tr>
            <td class="table_title" width="120">Influence</td>
            <td class="table_title" width="150">Impact Level</td>
            <td class="table_title" width="120">Weight</td>
            <td class="table_title" width="180">Remark</td>
          </tr>
           </logic:notEmpty>
          <logic:notEmpty name="webVo" property="influences">
            <logic:iterate id="influenceItem" name="webVo" property="influences">
              <tr>
                <bean:define id="influence" name="influenceItem" property="influence"/>
                <bean:define id="impactLevel" name="influenceItem" property="impactLevel"/>
                <bean:define id="weight" name="influenceItem" property="weight"/>
                <bean:define id="remark" name="influenceItem" property="remark"/>
                <td class="list_range">
                  <bean:write name="influenceItem" property="influence"/>
                  <input type="hidden" name="influences" value="<%=influence%>">
                </td>
                <td>
                  <html:select name="impactLevels" value="<%=String.valueOf(impactLevel)%>" styleId="input_field" onchange="computeRisk()" readonly="<%=impactLevelFlag%>" >
                    <html:optionsCollection name="influenceItem" property="impactLevelOptions"/>
                  </html:select>
                </td>
                <td>
                  <html:text name="weights" beanName="webVo_notExist" value="<%=String.valueOf(weight)%>" fieldtype="number" fmt="3.2" styleId="input_field" onblur="computeRisk()" readonly="true"/>
                </td>
                <td>
                  <html:text name="remarks" beanName="webVo_notExist" value="<%=String.valueOf(remark)%>" fieldtype="text" styleId="input_field" maxlength="100" readonly="<%=remarkFlag%>"/>
                </td>
              </tr>
            </logic:iterate>
          </logic:notEmpty>
          <logic:notEmpty name="webVo" property="categories">
          <tr>
            <td colspan="4">
              <hr style="color:#336699;height:2px"/>
            </td>
          </tr>
          <tr>
            <td class="table_title">Category</td>
            <td class="table_title">Value</td>
            <td class="table_title">Category</td>
            <td class="table_title">Value</td>
          </tr>
          </logic:notEmpty>
          <logic:notEmpty name="webVo" property="categories">
          <%
            for (int i = 0; i < categoryList.size(); i++) {
              java.util.HashMap group = (java.util.HashMap) categoryList.get(i);
              Object item0 = group.get("item0");
              Object item1 = group.get("item1");
              request.setAttribute("categoryItem1", group.get("item0"));
              if (item1 != null) {
                request.setAttribute("categoryItem2", group.get("item1"));
              }
              else {
                request.removeAttribute("categoryItem2");
              }
          %>
            <tr>
              <bean:define id="category1" name="categoryItem1" property="category"/>
              <bean:define id="value1" name="categoryItem1" property="value"/>
              <bean:define id="valueOptions1" name="categoryItem1" property="valueOptions"/>
              <bean:define id="description" name="categoryItem1" property="description"/>
              <td class="list_range" width="120" title="<%=description%>">
                <bean:write name="category1"/>
                <input type="hidden" name="categories" value="<%=category1%>">
              </td>
              <td class="list_range" width="240">

                <html:select name="categoryValues" styleId="input_field"  onchange="computeRisk();" value="<%=String.valueOf(value1)%>" readonly="<%=categoryValueFlag%>" >
                  <html:optionsCollection name="valueOptions1" />
                </html:select>
              </td>
              <logic:present name="categoryItem2">
                <logic:notEmpty name="categoryItem2">
                  <bean:define id="category2" name="categoryItem2" property="category"/>
                  <bean:define id="value2" name="categoryItem2" property="value"/>
                  <bean:define id="valueOptions2" name="categoryItem2" property="valueOptions"/>
                  <bean:define id="description2" name="categoryItem2" property="description"/>
                  <td class="list_range" title="<%=description2%>">                    &nbsp;&nbsp;
                    <bean:write name="category2"/>
                    <input type="hidden" name="categories" value="<%=category2%>">
                  </td>
                  <td class="list_range" >
                    <html:select name="categoryValues" styleId="input_field" onchange="computeRisk()" value="<%=String.valueOf(value2)%>" readonly="<%=categoryValueFlag%>">
                      <html:optionsCollection name="valueOptions2"/>
                    </html:select>
                  </td>
                </logic:notEmpty>
                <logic:empty name="categoryItem2">
                  <td class="list_range">                  </td>
                  <td class="list_range">&nbsp;</td>
                </logic:empty>
              </logic:present>
              <logic:notPresent name="categoryItem2">
                <td class="list_range">                </td>
                <td class="list_range">&nbsp;</td>
              </logic:notPresent>
            </tr>
          <%}          %>
          </logic:notEmpty>
        </table>
      </td>
    </tr>
    <logic:notEmpty name="webVo" property="categories">
    <tr>
      <td colspan="8" height="3">
        <hr style="color:#336699;height:2px"/>
      </td>
    </tr>
    </logic:notEmpty>
    <tr>
      <td class="list_range"valign="top">Resolution</td>
      <td colspan="7">
        <html:textarea name="resolution" beanName="webVo" styleId="resolution" rows="4" req="true" styleClass="textAreaStyle Xtext Req" readonly="<%=resolutionFlag%>" maxlength="1000"/>
      </td>
    </tr>
    <tr>
      <td class="list_range">Plan Finish</td>
      <td class="list_range" width="180">
        <html:text name="planFinishDate" beanName="webVo" fieldtype="dateyyyymmdd" styleId="input_field" ondblclick="getDATE(this)" readonly="<%=planFinishDateFlag%>"/>
      </td>
      <td class="list_range" width="10">&nbsp;</td>
      <td class="list_range">Assign Date</td>
      <td class="list_range" width="200">
        <html:text name="assignedDate" beanName="webVo" fieldtype="dateyyyymmdd" styleId="input_field" ondblclick="getDATE(this)" readonly="<%=assignedDateFlag%>"/>
      </td>
      <td class="list_range" width="10">&nbsp;</td>
      <td class="list_range" width="80">Assign To</td>
      <bean:define id="resolutionBy_id" name="webVo" property="resolutionBy" />
      <%if(isCustomer) {%>
      <td class="list_range" width="100">
        <html:select name="resolutionByCustomer"
                     beanName="webVo"
                     styleId="resolutionByCustomer"
                     prev="attachmentDesc"
                     next=""
                     req="false">
         <html:optionsCollection name="webVo" property="resolutionByCustomerList"/>
        </html:select>
        </td>
        <%} else {
           String resolutionByName=HrFactory.create().getName((String)resolutionBy_id);
        %>
      <td class="list_range" width="100" title="<%=resolutionBy_id+"/"+resolutionByName%>">
        <input type="hidden" name="resolutionBy" value="<%=resolutionBy_id%>">
        <html:text name="resolutionByName"
                   beanName="webVo"
                   fieldtype="text"
                   styleId="resolutionBy"
                   value="<%=resolutionByName%>"
                   imageSrc="<%=(resolutionByFlag || user.getUserType().equals(DtoUser.USER_TYPE_CUST))?"":contextPath+"/image/humanAllocate.gif"%>"
                   imageWidth="18" imageOnclick="allocateHr()"
                   readonly="true" maxlength="100"/>
      </td>
      <%}%>
    </tr>
    <tr>
      <td class="list_range">Attachment</td>
      <td class="list_range">
        <logic:notEmpty name="webVo" property="attachment">
          <bean:define id="downloadUrl" name="webVo" property="attachment"/>
          <html:file name="attachment" styleId="input_field" imageSrc="<%=contextPath+"/image/download.gif"%>" imageHref="<%=String.valueOf(downloadUrl)%>" imageStyle="border:0" imageWidth="18" readonly="<%=readonlyFlag%>"/>
        </logic:notEmpty>
        <logic:empty name="webVo" property="attachment">
          <html:file name="attachment" styleId="input_field" readonly="<%=readonlyFlag%>"/>
        </logic:empty>

      </td>
      <td class="list_range" width="10">&nbsp;</td>
      <td class="list_range">Description</td>
      <td class="list_range">
        <html:text name="attachmentDesc" beanName="webVo" fieldtype="text" styleId="input_field" readonly="<%=readonlyFlag%>" maxlength="100"/>
      </td>
      <td class="list_range">&nbsp;</td>
      <td class="list_range"></td>
      <td class="list_range" width="100">
      <%if(!isCustomer) {%>
        <html:select name="resolutionByCustomer"
                     beanName="webVo"
                     styleId="resolutionByCustomer"
                     prev="attachmentDesc"
                     next=""
                     req="false">
         <html:optionsCollection name="webVo" property="resolutionByCustomerList"/>
        </html:select>
        <%}%>
      </td>
    </tr>
  </TABLE>
</html:form>
</body>
</html>
