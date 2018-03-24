<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>
<%@page import="db.essp.issue.IssueCategoryType,db.essp.issue.IssueCategoryValue"%>
<bean:define  id="selectedRowObj"  name="webVo" property="selectedRowObj" />
<bean:define  id="typeName"  name="webVo" property="typeName" />
<bean:define  id="contextPath" value="<%=request.getContextPath()%>" />
<html>
    <head>
<tiles:insert page="/layout/head.jsp">
  <tiles:put name="title" value="Issue Category"/>
  <tiles:put name="jspName" value="IssueCategoryList"/>
</tiles:insert>
<script language="javascript" type="text/javascript">
var oldColor=null;
var oldObj=null;
var currentRowId = null;
var currentCategoryName = null;
var currentCategoryValue = null;
function changeRowColor(obj) {
	if(oldObj!=null) {
		oldObj.style.backgroundColor=oldColor;
	}
    	oldColor=obj.style.backgroundColor;
    	oldObj=obj;
    	obj.style.backgroundColor="#ccccff";
}

function onclickRow(rowObj){
    try{
        currentRowId = rowObj.id
    	currentCategoryName = rowObj.categoryName;
    	if(rowObj.categoryValue != null)
            currentCategoryValue = rowObj.categoryValue;
        else
            currentCategoryValue = null;
       // alert("category:" + currentCategoryName + "\n" + "value:" + currentCategoryValue);
    	changeRowColor(rowObj);
    }catch( ex ){
        alert( "onclickRow " + ex );
    }
}

function ondbClick(rowObj){
  onclickRow(rowObj);
  onEditData(rowObj);
}
function onAddData(){
    //alert('<%=typeName%>');
    var option = "Top=200,Left=300,Height=250,Width=480,toolbar=no, resizable=yes,scrollbars=no,status=yes";
    window.open(encodeURI("<%=request.getContextPath()%>/issue/typedefine/category/categoryAddPreAction.do?typeName=<%=typeName%>"),"",option);
}

function onAddCategoryValue(){
    if(currentCategoryName == null){
        alert("Please choose a Category to add value");
        return;
    }
    var option = "Top=200,Left=300,Height=250,Width=480,toolbar=no,resizable=yes,scrollbars=no,status=yes";
    var url="<%=request.getContextPath()%>/issue/typedefine/category/categoryValueAddPreAction.do?typeName=<%=typeName%>&categoryName=" + currentCategoryName;
    //alert("url="+url);
    window.open(encodeURI(url),"",option);
}

function onDeleteData(){
	//删除CategoryValue
    if(currentCategoryValue != null && confirm('<bean:message  bundle="issue" key="issue.category.value.delete.confirm" />')){
    	//window.location="<%=request.getContextPath()%>/issue/typedefine/category/categoryValueDeleteAction.do?typeName=<%=typeName%>&categoryName=" + currentCategoryName + "&categoryValue=" +currentCategoryValue;
        dataForm.action="<%=request.getContextPath()%>/issue/typedefine/category/categoryValueDeleteAction.do";
        dataForm.typeName.value="<%=typeName%>";
        dataForm.categoryName.value=currentCategoryName;
        dataForm.categoryValue.value=currentCategoryValue;
        dataForm.submit();
        currentCategoryValue = null;
        return;
    }
    	//删除IssueCategory
    if(currentCategoryName != null && currentCategoryValue == null && confirm('<bean:message bundle="issue" key="issue.category.delete.confirm" />') ){
        //window.location="<%=request.getContextPath()%>/issue/typedefine/category/categoryDeleteAction.do?typeName=<%=typeName%>&categoryName=" + currentCategoryName;
    	dataForm.action="<%=request.getContextPath()%>/issue/typedefine/category/categoryDeleteAction.do";
        dataForm.typeName.value="<%=typeName%>";
        dataForm.categoryName.value=currentCategoryName;
        dataForm.submit();
        return;
    }
}
function onEditData(){
	//编辑CategoryValue
    if(currentCategoryValue != null){
    	var option = "Top=200,Left=300,Height=250,Width=480,toolbar=no,resizable=yes,scrollbars=no,status=yes";
    	window.open(encodeURI("<%=request.getContextPath()%>/issue/typedefine/category/categoryValueUpdatePreAction.do?typeName=<%=typeName%>&categoryName=" + currentCategoryName + "&categoryValue=" +currentCategoryValue),
    			"",option);
    	return;
    }
    	//编辑IssueCategory
    if(currentCategoryName != null ){
    	var option = "Top=200,Left=300,Height=250,Width=480,toolbar=no,resizable=yes,scrollbars=no,status=yes";
        window.open(encodeURI("<%=request.getContextPath()%>/issue/typedefine/category/categoryUpdatePreAction.do?typeName=<%=typeName%>&categoryName=" + currentCategoryName),
        		"",option);
    	return;
    }
}
function onBodyLoad(){
	try{
          if(document.getElementById("<%=selectedRowObj%>")==null)
                onclickRow( tr0 );
          else
                onclickRow( <%=selectedRowObj%> );
	}catch( ex ){
     		//alert( "onBodyLoad() " + ex  );
	}
}
function refreshSelf(){
        if( currentRowId != null )
    		currentStatusForm.selectedRowObj.value=currentRowId;
        currentStatusForm.submit();

}
function onRefreshData(url,typeName) {
    currentStatusForm.action=url;
    currentStatusForm.typeName.value=typeName;
    currentStatusForm.submit();
}
</script>
</head>
<body onload="onBodyLoad()">
<form name="dataForm" action="" method="POST">
<input type="hidden" name="typeName">
<input type="hidden" name="categoryName">
<input type="hidden" name="categoryValue">
<input type="hidden" name="sequence">
<input type="hidden" name="description">
<input type="hidden" name="selectedRow">
</form>
              <html:table  styleId="tableStyle" >
                  <html:tablehead styleId="tableTitleStyle">
                      <html:tabletitle width="100" styleId="oracolumntextheader" toolTip="Category">Category</html:tabletitle>
                      <html:tabletitle width="150" styleId="oracolumntextheader" toolTip="Category Value">Category Value</html:tabletitle>
                      <html:tabletitle width="100" styleId="oracolumnnumberheader" toolTip="Sequence">Sequence</html:tabletitle>
                      <html:tabletitle width="*" styleId="oracolumntextheader" toolTip="Description">Description</html:tabletitle>
                  </html:tablehead>

                  <html:tablebody styleId="tableDataStyle" height="150" >
                    	<bean:define id="categoryCss" value=""/>
                        <%-- 遍历IssueCategory --%>
                        <%int rowIndex = -1;%>
                        <logic:iterate id="element" name="webVo" property="detail" type="db.essp.issue.IssueCategoryType" indexId="index">
                        	<%IssueCategoryType category = (IssueCategoryType)element;
                                  String categoryName = category.getComp_id().getCategoryName();
				  Long seq = category.getSequence();
                                  String des = category.getDescription();
                                String selfProperty="categoryName='"+categoryName +"'";
                                rowIndex ++;
			  	%>

                            <% String sId = "tr" + rowIndex; %>
                            <% String styleId=index.intValue()%2==1?"tableDataEven":"tableDataOdd";%>

                            <html:tablerow onclick="onclickRow(this);"
                            		    id="<%=sId%>"
                                            ondblclick="ondbClick(this);"
                                            styleId="<%=styleId%>"
                            		    selfProperty="<%=selfProperty%>">
                                <html:tablecolumn styleId="oracelltext" toolTip="<%=categoryName%>">
                                    <bean:write name="element" property="comp_id.categoryName" />
                                </html:tablecolumn>
                                <html:tablecolumn styleId="oracelltext">

                                </html:tablecolumn>
                                <html:tablecolumn styleId="oracellnumber" toolTip="<%=seq.toString()%>">
                                    <bean:write name="element" property="sequence" />
                                </html:tablecolumn>
                                <html:tablecolumn styleId="oracelltext"  toolTip="<%=des%>">
                                    <bean:write name="element" property="description" />
                                </html:tablecolumn>
                            </html:tablerow>

                            <%-- 遍历每个IssueCategory的CategoryValue --%>
                            <logic:iterate id="value" name="element"  property="categoryValues" type="db.essp.issue.IssueCategoryValue" indexId="index2">
                                <bean:define id="valueCss" value=""/>
                                <%
                                IssueCategoryValue issueCatValue = (IssueCategoryValue)value;
                                String categoryValue = issueCatValue.getComp_id().getCategoryValue();
                                seq = issueCatValue.getSequence();
                                des = issueCatValue.getDescription();
                                String selfProperty2="categoryName='" + categoryName + "' categoryValue='" + categoryValue + "'";
                                rowIndex ++;
                                %>
                                 <% String sId2 = "tr" + rowIndex; %>
                                 <% String sStyleId = index2.intValue()%2==1?"rowEven":"rowOdd"; %>
                                <html:tablerow onclick="onclickRow(this);"
                                                ondblclick="ondbClick(this);"
                                		id="<%=sId2 %>"
                                        selfProperty="<%=selfProperty2%>"
                                        styleId="<%=sStyleId%>"
                                        >
                                    <html:tablecolumn styleId="oracelltext">

                                    </html:tablecolumn>
                                    <html:tablecolumn styleId="oracelltext" toolTip="<%=categoryValue%>">
                                        <bean:write name="value" property="comp_id.categoryValue" />
                                    </html:tablecolumn>
                                    <html:tablecolumn styleId="oracellnumber" toolTip="<%=seq.toString()%>">
                                        <bean:write name="value" property="sequence" />
                                    </html:tablecolumn>
                                    <html:tablecolumn styleId="oracelltext" toolTip="<%=des%>">
                                        <bean:write name="value" property="description" />
                                    </html:tablecolumn>
                                </html:tablerow>
                            </logic:iterate >
                      </logic:iterate >
                  </html:tablebody>
              </html:table>

<% String sAction = contextPath+"/issue/typedefine/category/categoryListAction.do"; %>
<html:form name="currentStatusForm" method="POST" action="<%=sAction%>" target="_self">
    <html:hidden name="selectedRowObj" value=""/>
    <input  type="hidden" name="typeName" value="<%=typeName%>"/>
</html:form>
</body>
</html>
