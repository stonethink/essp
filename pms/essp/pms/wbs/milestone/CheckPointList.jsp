<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>
<%@ page import="server.essp.pms.wbs.viewbean.VbCheckPoint" %>
<html>
<head>
<tiles:insert page="/layout/head.jsp">
  <tiles:put name="title" value=" IssueTypeDefine"/>
  <tiles:put name="jspName" value=""/>
</tiles:insert>
<script language="javaScript" type="text/javascript">
var currentRowObj;
function onclickRow(rowObj){
    try{
    	currentRowObj = rowObj;
    	changeRowColor(rowObj);
        rowObj.scrollIntoView(true);
    }catch( ex ){
    }
}

function onRefreshData(acntRid,wbsRid){
   //alert(acntRid +"  "+wbsRid );
   window.location="<%=request.getContextPath()%>/pms/wbs/milestone/CheckPointList.do?acntRid="+acntRid+"&wbsRid="+wbsRid
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
            <center><a class="tabs_title">CheckPoints</a></center>
        </html:tabtitle>
    </html:tabtitles>

    <html:tabbuttons></html:tabbuttons>
    <html:tabcontents>
        <html:tabcontent styleClass="wind">
            <html:outborder height="40%" width="98%">
                <html:table styleId="tableStyle">
                    <html:tablehead styleId="tableTitleStyle">
                        <html:tabletitle width="100" styleId="oracolumntextheader" toolTip="Checkpoint">Checkpoint</html:tabletitle>
                        <html:tabletitle width="80" styleId="oracolumnnumberheader" toolTip="Weight">Weight</html:tabletitle>
                        <html:tabletitle width="80" styleId="oracolumnDateheader" toolTip="Due Date">Due Date</html:tabletitle>
                        <html:tabletitle width="90" styleId="oracolumnDateheader" toolTip="Finish Date">Finish Date</html:tabletitle>
                        <html:tabletitle width="80" styleId="oracolumntextheader" toolTip="Completed">Completed</html:tabletitle>
                        <html:tabletitle width="*" styleId="oracolumntextheader" toolTip="Remark">Remark</html:tabletitle>
                    </html:tablehead>
                    <html:tablebody styleId="tableDataStyle" height="186" id="searchTable">
                        <%-- 遍历CheckPoint --%>
                        <logic:present name="webVo">
                        <logic:iterate id="element" name="webVo" type="server.essp.pms.wbs.viewbean.VbCheckPoint" indexId="index">
                            <%VbCheckPoint checkPoint = (VbCheckPoint)element;
                            %>
                            <html:tablerow  id="<%="tr"+index.intValue()%>"
                                onclick="onclickRow(this);"
                                styleId="<%=index.intValue()%2==1?"tableDataEven":"tableDataOdd"%>"
                                height="18px"
                                >
                                <html:tablecolumn styleId="oracelltext" toolTip="<%=checkPoint.getName()%>">
                                    <bean:write name="element" property="name"/>
                                </html:tablecolumn>
                                <html:tablecolumn styleId="oracellnumber" toolTip="<%=checkPoint.getWeight()%>">
                                    <bean:write name="element" property="weight"/>
                                </html:tablecolumn>
                                <html:tablecolumn styleId="oracelldate" toolTip="<%=checkPoint.getDueDate()%>">
                                    <bean:write name="element" property="dueDate"/>
                                </html:tablecolumn>
                                <html:tablecolumn styleId="oracelldate" toolTip="<%=checkPoint.getFinishDate()%>">
                                    <bean:write name="element" property="finishDate"/>
                                </html:tablecolumn>
                                <html:tablecolumn styleId="oracelltext" >
                                    <logic:equal name="element" property="completed" value="Y">
                                        <input type="checkbox" checked="checked" disabled/>
                                    </logic:equal>
                                    <logic:notEqual name="element" property="completed" value="Y">
                                        <input type="checkbox" disabled/>
                                    </logic:notEqual>
                                </html:tablecolumn>
                                <html:tablecolumn styleId="oracelltext" toolTip="<%=checkPoint.getRemark()%>">
                                    <bean:write name="element" property="remark"/>
                                </html:tablecolumn>
                            </html:tablerow>
                        </logic:iterate>
                        </logic:present>
                    </html:tablebody>
                </html:table>
            </html:outborder>
        </html:tabcontent>
    </html:tabcontents>
</html:tabpanel>
</body>
