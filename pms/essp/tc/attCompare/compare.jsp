<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="/inc/pagedef.jsp"%>
<%@page import="itf.hr.HrFactory"%>
<html>
<head>
<tiles:insert page="/layout/head.jsp">
  <tiles:put name="title" value="Attendance Report"/>
  <tiles:put name="jspName" value="."/>
</tiles:insert>
</head>
<script language="javascript" >
function sumbitCompare(){
    var endPeriod = document.compare.endDate.value;
    var beginPeriod = document.compare.beginDate.value;
    if(endPeriod < beginPeriod){
      alert("The end date must be larger than begin date!");
      return;

    }
      compare.submit();
}

function change(){
}
</script>
<body>
<center class="form_title">No Attendence Compare</center>
<br/>
<html:form id="compare" action="/tc/attCompare/Compare.do" method="POST">
  <table width="550" border="0" align="center" cellpadding="0" cellspacing="0">
    <tr>
      <td class="list_range" width="120">Compare Period</td>
      <td width="120" class="list_range">
        <html:text name="beginDate" beanName="compareForm" fieldtype="dateyyyymmdd" styleId="input_common" prev="reportDate" next="reportEnd" req="true" maxlength="100" ondblclick="getDATE(this);"/>
      </td>
      <td width="10" align="center">~</td>
      <td width="120" class="list_range">
        <html:text name="endDate" beanName="compareForm" fieldtype="dateyyyymmdd" styleId="input_common" prev="reportBegin" next="report" req="true" maxlength="100" ondblclick="getDATE(this);"/>
      </td>
      <td width="180" align="right" valign="bottom">



     <input type="button" class="button" name="Submit" value="Compare" onClick="sumbitCompare();">
        <input type="reset" value="Reset" name="reset" class="button">
      </td>
    </tr>
    <tr>    </tr>
  </table>
</html:form>
<br/>
<br/>
<center>
  <logic:present name="webVo">

<html:tabpanel id="compareResult" width="730">
 <form  id="bottom" action="/essp/tc/attCompare/compareResultDel.do">
  <html:tabtitles>
    <html:tabtitle selected="true" width="120" title="Compare Result">
    </html:tabtitle>
    </html:tabtitles>
    <html:tabbuttons>
    <input  type="submit" value="Delete" class="button" onclick="return confirm('Whether deletes?')"/>
    </html:tabbuttons>

    <html:outborder width="730" height="200">
      <html:table styleId="tableStyle">
        <html:tablehead styleId="tableTitleStyle" height="15">
          <html:tabletitle width="100">LoginId</html:tabletitle>
          <html:tabletitle width="60">Date</html:tabletitle>
          <html:tabletitle width="190">Leave Time</html:tabletitle>
          <html:tabletitle width="60" toolTip="Leave Hours">Lea-Hours</html:tabletitle>
          <html:tabletitle width="70">App Status</html:tabletitle>
          <html:tabletitle width="100" toolTip="No Attendence Time">No Att-Time</html:tabletitle>
          <html:tabletitle width="60" toolTip="No Attdence Hours">No Att-H</html:tabletitle>
          <html:tabletitle width="60">isDelete</html:tabletitle>
        </html:tablehead>
        <html:tablebody height="100%">


          <logic:iterate id="wv" name="webVo">
            <html:tablerow id="" oddClass="tableDataOdd" evenClass="tableDataEven" height="15">
              <bean:define id="loginId" name="wv" property="loginId" />
              <%String userName=HrFactory.create().getName((String)loginId);%>
              <html:tablecolumn styleId="oracelltext" toolTip="<%=loginId+"/"+userName%>">
                <%=userName%>
              </html:tablecolumn>
              <html:tablecolumn styleId="oracelltext">
                <bean:write name="wv" property="date"/>
              </html:tablecolumn>
              <html:tablecolumn styleId="oracelltext">
                <bean:write name="wv" property="appTime"/>
              </html:tablecolumn>
              <html:tablecolumn styleId="oracelltext">
                <bean:write name="wv" property="appHours"/>
              </html:tablecolumn>
              <html:tablecolumn styleId="oracelltext">
                <bean:write name="wv" property="appStatus"/>
              </html:tablecolumn>
              <html:tablecolumn styleId="oracelltext">
                <bean:write name="wv" property="actualTime"/>
              </html:tablecolumn>
              <html:tablecolumn styleId="oracelltext">
                <bean:write name="wv" property="actualHours"/>
              </html:tablecolumn>
              <html:tablecolumn styleId="oracelltext">
                <input name="noattRid" type="checkbox" value="<bean:write name="wv" property="noattRid"/>" <bean:write name="wv" property="isDelete"/>
                />
              </html:tablecolumn>
            </html:tablerow>
          </logic:iterate>


        </html:tablebody>

</html:table>
    </html:outborder>
    </form>
    </html:tabpanel>

  </logic:present>
</center>
</body>
</html>
