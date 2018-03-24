<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="/inc/pagedef.jsp"%>
<%@page import="itf.hr.HrFactory"%>
<%@page import="c2s.essp.common.user.DtoUser"%>
<bean:define id="contextPath" value="<%=request.getContextPath()%>"/>
<bean:define id="issue" name="webVo" property="issue" />
<bean:define id="issueResolution" name="webVo" property="issueResolution" />
<bean:define id="issueConclusion" name="webVo" property="issueConclusion" />
<bean:define id="issueStatusHistoryList" name="webVo" property="issueStatusHistoryList"/>
<bean:define id="issueRiskHistoryList" name="webVo" property="issueRiskHistoryList"/>
<%java.util.List categoryList = new java.util.ArrayList();%>
<logic:notEmpty name="issueResolution" property="categories">
  <logic:iterate id="categoryItem" name="issueResolution" property="categories" indexId="currentIndex">
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
  <tiles:put name="title" value="allDetailOfIssue"/>
  <tiles:put name="jspName" value=""/>
</tiles:insert>
<title>allDetailOfIssue</title>
<style type="text/css">
  #input_field {width:100%;word-wrap: break-word;}
  .table_title {
  FONT-SIZE: 13px; COLOR: Black; FONT-STYLE: normal; FONT-FAMILY:
  &quot;
  Arial, Helvetica, sans-serif
  &quot;

  ; TEXT-ALIGN: left;
  font-weight: bold;
  border-bottom-color: Black;
  border-bottom-style: inset;
  border-bottom-width: 1px;
  border-collapse: separate;
  }
  .textAreaStyle { width:690 }
  #input_text{width:150}
</style>
</head>
<body bgcolor="#ffffff">
<%--General--%>
<table>
    <tr>
        <td valign="bottom" height="5" colspan="10" class="orarowheader" width="100%">General</td>
        <td ></td>
    </tr>
</table>
<TABLE id="General" cellSpacing="0" cellPadding="0" width="100%" border="1">
  <TR>
    <TD class="list_range" width="80" >Issue Type</TD>
    <TD class="list_range" width="180" ><font color="gray"><bean:write name="issue" property="issueType"/> &nbsp;</TD>
    <TD class="list_range" width="80">Priority</TD>
    <TD class="list_range" width="180"><font color="gray"><bean:write name="issue" property="priority"/>&nbsp; </TD>
    <td class="list_range" width="80">Scope</td>
    <TD class="list_range" width="200"> <font color="gray"><bean:write name="issue" property="scope"/>&nbsp; </td>
  </TR>
  <TR>
    <TD class="list_range" width="80">Issue Id</TD>
    <TD class="list_range" width="180"><font color="gray"><bean:write name="issue" property="issueId"/>&nbsp;</TD>
    <TD class="list_range" width="80">Issue Title</TD>
    <TD class="list_range" width="180"><font color="gray"><bean:write name="issue" property="issueName"/>&nbsp;</TD>
    <td class="list_range" width="80">Filled Date</td>
    <td class="list_range" width="200"><font color="gray"><bean:write name="issue" property="filleDate"/>&nbsp;</td>
  </TR>
  <!--
  <TR>
    <TD class="list_range" width="80">Phone</TD>
    <TD class="list_range" width="180"><font color="gray"><bean:write name="issue" property="phone"/>&nbsp;</TD>
    <TD class="list_range" width="80">Email</TD>
    <TD class="list_range" width="180"><font color="gray"><bean:write name="issue" property="email"/>&nbsp;</TD>
    <td class="list_range" width="80">Fax</td>
    <td class="list_range" width="200"><font color="gray"><bean:write name="issue" property="fax"/>&nbsp;</td>
  </TR>
  -->
  <TR>
    <TD class="list_range">Filled By</TD>
    <bean:define id="filleBy" name="issue" property="filleBy"/>
    <bean:define id="filleByScope" name="issue" property="filleByScope" />
      <%String filleByName="";
        if(DtoUser.USER_TYPE_EMPLOYEE.equals(filleByScope)) {
          filleByName = HrFactory.create().getName((String)filleBy);
        } else {
          filleByName = HrFactory.create().getCustomerName((String)filleBy);
        }
     %>
    <TD class="list_range" width="180" title="<%=filleBy+"/"+filleByName%>"><font color="gray"><%=filleByName%>&nbsp;</TD>
    <TD class="list_range" width="80">Due Date</TD>
    <TD class="list_range" colspan="3" width="540"><font color="gray"><bean:write name="issue" property="dueDate"/>&nbsp;</TD>
  </TR>
  <TR>
    <TD class="list_range" width="80">Issue Desc</TD>
    <TD class="list_range" colspan="7" width="720"><font color="gray"><bean:write name="issue" property="description"/>&nbsp;</TD>
  </TR>
  <TR>
    <TD class="list_range" width="80">Attachment</TD>
    <TD class="list_range" width="720" colspan="5"><font color="gray">
       <bean:define id="downloadUrl" name="issue" property="attachment"/>
            <logic:notEmpty name="downloadUrl">
              <a href="<%=downloadUrl%>" target="_blank">
                <img alt="" style="border:0;height:13.5" src="<%=contextPath+"/image/download.gif"%>"/>
              </a>
            </logic:notEmpty>
            <font color="gray">&nbsp;<bean:write name="issue" property="attachmentdesc"/>
            <logic:empty name="downloadUrl">
                &nbsp;
            </logic:empty>
    </TD>
  </TR>
  <TR>
    <TD class="list_range" width="80">Principal</TD>
    <bean:define id="principal" name="issue" property="principal"/>
    <bean:define id="principalScope" name="issue" property="principalScope" />
      <%String principalName="";
        if(DtoUser.USER_TYPE_EMPLOYEE.equals(principalScope)) {
          principalName = HrFactory.create().getName((String)principal);
        } else {
          principalName = HrFactory.create().getCustomerName((String)principal);
        }
     %>
    <TD class="list_range" width="180" title="<%=principal+"/"+principalName%>"><font color="gray">&nbsp;<%=principalName%></TD>
    <TD class="list_range" width="80">Status</TD>
    <TD class="list_range" colspan="3" width="460"><font color="gray">&nbsp;<bean:write name="issue" property="issueStatus"/></TD>

  </TR>
  <tr>
  </tr>
</TABLE>
<%--Analysis&Assign--%>
<table>
    <tr>
        <td valign="bottom" height="5" colspan="10" class="orarowheader" width="100%">Analysis&Assign</td>
        <td ></td>
    </tr>
</table>
<TABLE  cellSpacing="0" cellPadding="0" width="100%" border="1">
  <tr>
    <td class="list_range" width="80">Probability</td>
    <td class="list_range" width="120"><font color="gray"><bean:write name="issueResolution" property="probability"/>&nbsp;</td>
    <td class="list_range" width="80">Risk Level</td>
    <td class="list_range" width="520" colspan="3"><font color="gray"><bean:write name="issueResolution" property="riskLevel"/>&nbsp;</td>
  </tr>
  <%--
  <tr>
    <td colspan="6" height="3">
      <hr style="color:#336699;height:2px"/>
    </td>
  </tr>
  --%>
  <tr>
    <td colspan="6">
      <html:table styleId="tableStyle">
        <html:tablehead styleId="tableTitleStyle">
          <html:tabletitle  styleId="oracolumntextheader" width="120">Influence</html:tabletitle>
          <html:tabletitle  styleId="oracolumntextheader" width="120">Impact Level</html:tabletitle>
          <html:tabletitle  styleId="oracolumntextheader" width="120">Weight</html:tabletitle>
          <html:tabletitle  styleId="oracolumntextheader" width="440">Remark</html:tabletitle>
        </html:tablehead>
        <html:tablebody styleId="tableDataStyle" height="100%" id="listTable">
         <logic:notEmpty name="issueResolution" property="influences">
            <logic:iterate id="influenceItem" name="issueResolution" property="influences">
              <html:tablerow>
                <html:tablecolumn styleId="list_range">
                  <bean:write name="influenceItem" property="influence"/>
                </html:tablecolumn>
                <html:tablecolumn styleId="list_range">
                  <font color="gray"><bean:write name="influenceItem" property="impactLevel"/>
                </html:tablecolumn>
                <html:tablecolumn styleId="list_range">
                  <font color="gray"><bean:write name="influenceItem" property="weight"/>
                </html:tablecolumn>
                <html:tablecolumn styleId="list_range">
                  <font color="gray"><bean:write name="influenceItem" property="remark"/>
                </html:tablecolumn>
              </html:tablerow>
            </logic:iterate>
          </logic:notEmpty>
          <logic:empty name="issueResolution" property="influences">
              <html:tablerow>
                <html:tablecolumn></html:tablecolumn>
                <html:tablecolumn></html:tablecolumn>
                <html:tablecolumn></html:tablecolumn>
                <html:tablecolumn></html:tablecolumn>
              </html:tablerow>
          </logic:empty>
        </html:tablebody>
      </html:table>
      <html:table styleId="tableStyle">
        <html:tablehead styleId="tableTitleStyle">
           <html:tabletitle   styleId="oracolumntextheader" width="120">Category</html:tabletitle>
           <html:tabletitle   styleId="oracolumntextheader" width="280">Value</html:tabletitle>
           <html:tabletitle   styleId="oracolumntextheader" width="120">Category</html:tabletitle>
           <html:tabletitle   styleId="oracolumntextheader" width="280">Value</html:tabletitle>
         </html:tablehead>
        <html:tablebody styleId="tableDataStyle" height="100%" id="listTable">
         <logic:notEmpty name="issueResolution" property="categories">
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
            <html:tablerow>
              <bean:define id="category1" name="categoryItem1" property="category"/>
              <bean:define id="value1" name="categoryItem1" property="value"/>
              <bean:define id="valueOptions1" name="categoryItem1" property="valueOptions"/>
              <html:tablecolumn styleId="list_range">
                <bean:write name="category1"/>
              </html:tablecolumn>
              <html:tablecolumn styleId="list_range">
               <font color="gray"><bean:write name="value1"/>
              </html:tablecolumn>
              <logic:present name="categoryItem2">
                <logic:notEmpty name="categoryItem2">
                  <bean:define id="category2" name="categoryItem2" property="category"/>
                  <bean:define id="value2" name="categoryItem2" property="value"/>
                  <bean:define id="valueOptions2" name="categoryItem2" property="valueOptions"/>
                  <html:tablecolumn styleId="list_range">                    &nbsp;&nbsp;
                    <bean:write name="category2"/>
                  </html:tablecolumn>
                  <html:tablecolumn styleId="list_range">
                  <font color="gray"> <bean:write name="value2"/>
                  </html:tablecolumn>
                </logic:notEmpty>
                <logic:empty name="categoryItem2">
                  <html:tablecolumn styleId="list_range"> &nbsp; </html:tablecolumn>
                  <html:tablecolumn styleId="list_range">&nbsp;</html:tablecolumn>
                </logic:empty>
              </logic:present>
              <logic:notPresent name="categoryItem2">
                <html:tablecolumn styleId="list_range"> &nbsp; </html:tablecolumn>
                <html:tablecolumn styleId="list_range">&nbsp;</html:tablecolumn>
              </logic:notPresent>
            </html:tablerow>
          <%}          %>
          </logic:notEmpty>
          <logic:empty name="issueResolution" property="categories">
              <html:tablerow>
                  <html:tablecolumn></html:tablecolumn>
                  <html:tablecolumn></html:tablecolumn>
                  <html:tablecolumn></html:tablecolumn>
                  <html:tablecolumn></html:tablecolumn>
              </html:tablerow>
          </logic:empty>
       </html:tablebody>
      </html:table>
    </td>
  </tr>
  <tr>
    <td class="list_range"  valign="top" width="80">Resolution</td>
    <td colspan="5" class="list_range" width="720"><font color="gray">&nbsp;<bean:write name="issueResolution" property="resolution"/></td>
  </tr>
  <tr>
    <td class="list_range" width="80">Plan Finish</td>
    <td class="list_range" width="80">&nbsp;<font color="gray"><bean:write name="issueResolution" property="planFinishDate"/></td>
    <td class="list_range" width="80">Resolution By</td>
    <td class="list_range" width="120">&nbsp;<font color="gray"><bean:write name="issueResolution" property="resolutionBy"/></td>
    <td class="list_range" width="80">Assign Date</td>
    <td class="list_range" width="360">&nbsp;<font color="gray"><bean:write name="issueResolution" property="assignedDate"/></td>
  </tr>
  <tr>
    <td class="list_range" width="80">Attachment</td>
    <td class="list_range" width="720" colspan="5">
       <bean:define id="downloadUrl" name="issueResolution" property="attachment"/>
            <logic:notEmpty name="downloadUrl">
              <a href="<%=downloadUrl%>" target="_blank">
                <img alt="" style="border:0;height:13.5" src="<%=contextPath+"/image/download.gif"%>"/>
              </a>
            </logic:notEmpty>
            <font color="gray"><bean:write name="issueResolution" property="attachmentDesc"/>&nbsp;
            <logic:empty name="downloadUrl">
                &nbsp;
            </logic:empty>
    </td>
  </tr>
  <tr>
  </tr>
</TABLE>
<%--Conclusion--%>
<table>
    <tr>
        <td valign="bottom" height="5" colspan="10" class="orarowheader" width="100%">Conclusion</td>
        <td ></td>
     </tr>
</table>
<table bgcolor="ffffff" cellpadding="0" cellspacing="1" border="1" width="100%" >
  <tr>
    <td class="list_range" width="130">Finished Date</td>
    <td class="list_range" width="120"><font color="gray"><bean:write name="issueConclusion" property="finishedDate"/>&nbsp;</td>
    <td class="list_range" width="120"> Delivered Date</td>
    <td class="list_range" width="430"><font color="gray"><bean:write name="issueConclusion" property="deliveredDate"/>&nbsp;</td>
  </tr>
  <tr>
    <td class="list_range" width="130">Attachment</td>
    <td class="list_range" width="670" colspan="5">
        <bean:define id="downloadUrl" name="issueConclusion" property="attachment"/>
            <logic:notEmpty name="downloadUrl">
              <a href="<%=downloadUrl%>" target="_blank">
                <img alt="" style="border:0;height:13.5" src="<%=contextPath+"/image/download.gif"%>"/>
              </a>
            </logic:notEmpty>
            <font color="gray"><bean:write name="issueConclusion" property="attachmentDesc"/>&nbsp;
            <logic:empty name="downloadUrl">
                &nbsp;
            </logic:empty>
    </td>
  </tr>
  <tr>
    <td class="list_range" width="130">Closure Status</td>
    <td class="list_range" align="right" width="120"><font color="gray"><bean:write name="issueConclusion" property="closureStatus"/>&nbsp;</td>
    <td class="list_range" width="150">
      Waiting (Days)
    </td>
    <td class="list_range" width="400"><font color="gray"><bean:write name="issueConclusion" property="waiting"/>&nbsp;</td>
  </tr>
  <tr>
    <td class="list_range" width="130">Confirm Date</td>
    <td class="list_range" width="120"><font color="gray"><bean:write name="issueConclusion" property="confirmDate"/>&nbsp;</td>
    <td class="list_range" width="150">Confirm By</td>
    <bean:define id="confirmByLoginId" name="issueConclusion" property="confirmBy"/>
    <bean:define id="confirmByScope" name="issueConclusion" property="confirmByScope"/>
    <%String confirmByName="";
        if(DtoUser.USER_TYPE_EMPLOYEE.equals(confirmByScope)) {
          confirmByName = HrFactory.create().getName((String)confirmByLoginId);
        } else {
          confirmByName = HrFactory.create().getCustomerName((String)confirmByLoginId);
        }
     %>
    <td class="list_range" width="500" title="<%=confirmByLoginId+"/"+confirmByName%>"><font color="gray"><%=confirmByName%>&nbsp;</td>
  </tr>
    <tr>
    <td class="list_range" width="130">Actual Influence</td>
    <td class="list_range" colspan="3" width="670"><font color="gray"><bean:write name="issueConclusion" property="actualInfluence"/>&nbsp;</td>
  </tr>
  <tr>
    <td class="list_range" width="130">Solved Desc</td>
    <td class="list_range" colspan="3" width="670"><font color="gray"><bean:write name="issueConclusion" property="solvedDescription"/>&nbsp;</td>
  </tr>
  <tr>
    <td class="list_range" width="130">Instruction of closure</td>
    <td class="list_range" colspan="3" width="670"><font color="gray"><bean:write name="issueConclusion" property="instructionClosure"/>&nbsp;</td>
  </tr>
</table>
<%-- Urged Record --%>
<table>
    <tr>
        <td valign="bottom" height="5" colspan="10" class="orarowheader" width="100%">Urged Record</td>
        <td ></td>
    </tr>
</table>
<html:table styleId="tableStyle">

  <html:tablehead styleId="tableTitleStyle">
    <html:tabletitle width="100" styleId="oracolumntextheader" >Urged by</html:tabletitle>
    <html:tabletitle width="100" styleId="oracolumntextheader" >Urge to</html:tabletitle>
    <html:tabletitle width="100" styleId="oracolumntextheader" >Date</html:tabletitle>
    <html:tabletitle width="320" styleId="oracolumntextheader" >Description</html:tabletitle>
    <html:tabletitle width="180" styleId="oracolumntextheader" >Attachment</html:tabletitle>
  </html:tablehead>
  <html:tablebody styleId="tableDataStyle" height="100%" id="listTable">
    <logic:present name="issueConclusion">
        <logic:notEmpty name="issueConclusion" property="urgeList">
        <logic:iterate name="issueConclusion" property="urgeList" id="oneBean" indexId="indexId">
                  <html:tablerow >
                  <bean:define id="ttUrgedBy" name="oneBean" property="urgedBy"/>
                    <bean:define id="ttUrgedByScope" name="oneBean" property="urgedByScope"/>
                    <%
                       String urgedByName = "";
                       if(DtoUser.USER_TYPE_EMPLOYEE.equals(ttUrgedByScope)) {
                         urgedByName = HrFactory.create().getName((String)ttUrgedBy);
                       } else {
                         urgedByName = HrFactory.create().getCustomerName((String)ttUrgedBy);
                       }
                    %>
                    <html:tablecolumn styleId="list_range" toolTip="<%=ttUrgedBy+"/"+urgedByName%>">
                     <font color="gray"><%=urgedByName%>
                    </html:tablecolumn>
                    <bean:define id="ttUrgeTo" name="oneBean" property="urgeTo"/>
                    <bean:define id="ttUrgeToScope" name="oneBean" property="urgeToScope"/>
                    <%
                       String urgeToName = "";
                       if(DtoUser.USER_TYPE_EMPLOYEE.equals(ttUrgeToScope)) {
                         urgeToName = HrFactory.create().getName((String)ttUrgeTo);
                       } else {
                         urgeToName = HrFactory.create().getCustomerName((String)ttUrgeTo);
                       }
                    %>
                    <html:tablecolumn styleId="list_range" toolTip="<%=ttUrgeTo+"/"+urgeToName%>">
                      <font color="gray"><%=urgeToName%>
                    </html:tablecolumn>
                    <html:tablecolumn styleId="list_range">
                      <font color="gray"><bean:write name="oneBean" property="urgDate"/>
                    </html:tablecolumn>
                    <html:tablecolumn styleId="list_range">
                      <font color="gray"><bean:write name="oneBean" property="description"/>
                    </html:tablecolumn>
                    <html:tablecolumn styleId="list_range">
                      <bean:define id="downloadUrl" name="oneBean" property="attachment"/>
                      <logic:notEmpty name="downloadUrl">
                          <a href="<%=downloadUrl%>" target="_blank"><img  alt="" style="border:0;height:13.5" src="<%=contextPath+"/image/download.gif"%>"/></a>
                      </logic:notEmpty>
                      <font color="gray"><bean:write name="oneBean" property="attachmentdesc"/>&nbsp;
                    </html:tablecolumn>
                  </html:tablerow>
                </logic:iterate>
                </logic:notEmpty>
    </logic:present>
    <logic:notPresent name="issueConclusion">
        <html:tablerow>
       <html:tablecolumn><font color="gray"></html:tablecolumn>
       <html:tablecolumn><font color="gray"></html:tablecolumn>
       <html:tablecolumn><font color="gray"></html:tablecolumn>
       <html:tablecolumn><font color="gray"></html:tablecolumn>
       <html:tablecolumn></html:tablecolumn>
   </html:tablerow>
    </logic:notPresent>
 </html:tablebody>
</html:table>
<%--History of Issue Status --%>
<table>
    <tr>
        <td valign="bottom" height="5" colspan="10" class="orarowheader" width="100%">History of Issue Status</td>
        <td ></td>
    </tr>
</table>
<html:table styleId="tableStyle">
    <html:tablehead styleId="tableTitleStyle">
         <html:tabletitle width="110" styleId="oracolumntextheader">Time</html:tabletitle>
         <html:tabletitle width="90" styleId="oracolumntextheader">Who</html:tabletitle>
         <html:tabletitle width="600" styleId="oracolumntextheader">State</html:tabletitle>
  </html:tablehead>
  <html:tablebody styleId="tableDataStyle" height="100%" id="listTable">
      <logic:notEmpty name="issueStatusHistoryList">
        <logic:iterate id="issueStatusHistory" name="issueStatusHistoryList">
          <html:tablerow >
            <html:tablecolumn styleId="list_range"><font color="gray">
                <bean:write name="issueStatusHistory" property="updateDate"/>
            </html:tablecolumn>
            <bean:define id="updateBy" name="issueStatusHistory" property="updateBy"/>
            <bean:define id="updateByScope" name="issueStatusHistory" property="updateByScope"/>
            <%String updateByName="";
            if(DtoUser.USER_TYPE_EMPLOYEE.equals(updateByScope)) {
              updateByName = HrFactory.create().getName((String)updateBy);
            } else {
              updateByName = HrFactory.create().getCustomerName((String)updateBy);
            }
            %>
            <html:tablecolumn styleId="list_range" toolTip="<%=updateBy+"/"+updateByName%>"><font color="gray">
                <%=updateByName%>
            </html:tablecolumn>
            <html:tablecolumn styleId="list_range"><font color="gray">
                <bean:write name="issueStatusHistory" property="status"/>
            </html:tablecolumn>
       </html:tablerow>
        </logic:iterate>
      </logic:notEmpty>
      <logic:empty name="issueStatusHistoryList">
       <html:tablerow >
           <html:tablecolumn styleId="list_range"><font color="gray"></html:tablecolumn>
           <html:tablecolumn styleId="list_range"><font color="gray"></html:tablecolumn>
           <html:tablecolumn styleId="list_range"><font color="gray"></html:tablecolumn>
       </html:tablerow>
       </logic:empty>
    </html:tablebody>
</html:table>
<%--History Of Analysis&Assign --%>
<table>
    <tr>
        <td valign="bottom" height="5" colspan="10" class="orarowheader" width="100%">History Of Analysis&amp; Assign</td>
        <td ></td>
    </tr>
</table>
<table class="tableStyle">
     <tr class="tableTitleStyle">
        <td width="110" class="oracolumntextheader">Time</td>
        <td width="90" class="oracolumntextheader">Who</td>
        <td width="65" class="oracolumntextheader">Risk level </td>
        <td width="75" class="oracolumntextheader">Probability </td>
        <td width="460" class="oracolumntextheader">Influence </td>
    </tr>

        <logic:iterate id="VbIssueRiskHistoryList" name="issueRiskHistoryList">
          <tr>
            <td class="list_range">
                <font color="gray"><bean:write name="VbIssueRiskHistoryList" property="time"/>&nbsp;
            </td>
            <bean:define id="whoLoginId" name="VbIssueRiskHistoryList" property="who"/>
            <bean:define id="whoScope" name="VbIssueRiskHistoryList" property="whoScope"/>
            <%String whoName="";
            if(DtoUser.USER_TYPE_EMPLOYEE.equals(whoScope)) {
              whoName = HrFactory.create().getName((String)whoLoginId);
            } else {
              whoName = HrFactory.create().getCustomerName((String)whoLoginId);
            }
            %>
            <td class="list_range" title="<%=whoLoginId+"/"+whoName%>">
                <font color="gray"><%=whoName%>&nbsp;
            </td>
            <td class="list_range" align="right">
                <font color="gray"><bean:write name="VbIssueRiskHistoryList" property="riskLevel"/>&nbsp;
            </td>
            <td class="list_range" align="right">
                <font color="gray"><bean:write name="VbIssueRiskHistoryList" property="probability"/>&nbsp;
            </td>
            <td class="list_range">
                <table class="tableStyle" width="100%">
                        <logic:iterate id="influenceItem" name="VbIssueRiskHistoryList" property="influence">
                          <tr class="list_range">
                              <td class="list_range" width="140">
                                 <font color="gray"><bean:write name="influenceItem" property="influenceName"/>&nbsp;
                              </td>
                              <td class="list_range" width="60">
                                 <font color="gray"><bean:write name="influenceItem" property="impactLevel"/>&nbsp;
                              </td>
                              <td class="list_range" width="60">
                                 <font color="gray"><bean:write name="influenceItem" property="weight"/>&nbsp;
                              </td>
                              <td class="list_range" width="200">
                                 <font color="gray"><bean:write name="influenceItem" property="remark"/>&nbsp;
                              </td>
                          </tr>
                        </logic:iterate>
                </table>
            </td>
      </tr>
     </logic:iterate>
 </table>
</body>
</html>
