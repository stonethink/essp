<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>
<%@ page import="c2s.essp.pms.activity.DtoMilestone" %>
<html>
<head>
<tiles:insert page="/layout/head.jsp">
  <tiles:put name="title" value=" MileStoneList"/>
  <tiles:put name="jspName" value=""/>
</tiles:insert>
<script language="javaScript" type="text/javascript">
var currentRowObj;
function onclickRow(rowObj){
    try{
    	currentRowObj = rowObj;
    	changeRowColor(rowObj);
        rowObj.scrollIntoView(true);
        var acntRid = rowObj.acntRid;
        var wbsRid = rowObj.wbsRid;
    	pass(acntRid,wbsRid);
    }catch( ex ){
    }
}

function pass(acntRid,wbsRid){
    if( acntRid != null && wbsRid != null){
        cardFrm.onRefreshData(acntRid,wbsRid);
    }
}
function onChangeAcnt(acntRid){
    window.location="<%=request.getContextPath()%>/pms/wbs/milestone/MileStoneReport.do?acntRid="+acntRid
    //alert(acntRid);
}

function onBodyLoad(){
	try{
              onclickRow( tr0 );
	}catch( ex ){
     		//alert( "onBodyLoad() " + ex  );
	}
}
</script>
</head>
<body onload="onBodyLoad()">
<html:tabpanel id="MileStoneList" width="98%">
    <html:tabtitles>
        <html:tabtitle selected="true" width="100">
            <center><a class="tabs_title">Milestone Report</a></center>
        </html:tabtitle>
    </html:tabtitles>

    <html:tabbuttons>
        <html:form action="">
        <html:select name="selectedAcnt" beanName="webVo" styleClass="project_select" styleId="" onchange="onChangeAcnt(this.value);" >
            <html:optionsCollection name="webVo" property="acntList" />
        </html:select>
        </html:form>
    </html:tabbuttons>

    <html:tabcontents>
        <html:tabcontent styleClass="wind">
            <html:outborder height="98%" width="98%">
                <html:table styleId="tableStyle">
                    <html:tablehead styleId="tableTitleStyle">
                        <html:tabletitle width="90" styleId="oracolumntextheader" toolTip="Name">Name</html:tabletitle>
                        <html:tabletitle width="90" styleId="oracolumntextheader" toolTip="Code">Code</html:tabletitle>
                        <html:tabletitle width="90" styleId="oracolumntextheader" toolTip="Milestone Type">Milestone Type</html:tabletitle>
                        <html:tabletitle width="80" styleId="oracolumntextheader" toolTip="Status">Status</html:tabletitle>
                        <html:tabletitle width="105" styleId="oracolumndateheader" toolTip="Anticipated Finish">Anticipated Finish</html:tabletitle>
                        <html:tabletitle width="90" styleId="oracolumndateheader" toolTip="Actual Finish">Actual Finish</html:tabletitle>
                        <html:tabletitle width="110" styleId="oracolumntextheader" toolTip="Reached condition">Reached condition</html:tabletitle>
                        <html:tabletitle width="*" styleId="oracolumntextheader" toolTip="Remark">Remark</html:tabletitle>
                    </html:tablehead>
                    <html:tablebody styleId="tableDataStyle" id="searchTable">
                        <%-- 閬嶅巻MileStone --%>
                        <logic:iterate id="element" name="webVo" property="mileStoneList" type="c2s.essp.pms.activity.DtoMilestone" indexId="index">
                            <%DtoMilestone ms = (DtoMilestone)element;
                            %>
                            <html:tablerow  id="<%="tr"+index.intValue()%>"
                                onclick="onclickRow(this);"
                                styleId="<%=index.intValue()%2==1?"tableDataEven":"tableDataOdd"%>"
                                >
                                <html:tablecolumn styleId="oracelltext" toolTip="<%=ms.getName()%>" >
                                    <bean:write name="element" property="name"/>
                                </html:tablecolumn>
                                <html:tablecolumn styleId="oracelltext" toolTip="<%=ms.getCode()%>" >
                                    <bean:write name="element" property="code"/>
                                </html:tablecolumn>
                                <html:tablecolumn styleId="oracelltext" toolTip="<%=ms.getType()%>" >
                                    <bean:write name="element" property="type"/>
                                </html:tablecolumn>
                                <html:tablecolumn styleId="oracelltext" toolTip="<%=ms.getStatus()%>" >
                                    <bean:write name="element" property="status"/>
                                </html:tablecolumn>
                                <html:tablecolumn styleId="oracelldate" >
                                    <bean:write name="element" property="antiFinish"/>
                                </html:tablecolumn>
                                <html:tablecolumn styleId="oracelldate" >
                                    <bean:write name="element" property="compeleteDate"/>
                                </html:tablecolumn>
                                <html:tablecolumn styleId="oracelltext" toolTip="<%=ms.getReachedCondition()%>" >
                                    <bean:write name="element" property="reachedCondition"/>
                                </html:tablecolumn>
                                <html:tablecolumn styleId="oracelltext" toolTip="<%=ms.getRemark()%>" >
                                    <bean:write name="element" property="remark"/>
                                </html:tablecolumn>
                            </html:tablerow>
                        </logic:iterate>
                    </html:tablebody>
                </html:table>
            </html:outborder>
        </html:tabcontent>
    </html:tabcontents>
</html:tabpanel>
</body>
