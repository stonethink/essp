<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="c2s.essp.common.user.DtoUser" %>
<%@page import="itf.hr.HrFactory"%>
<%@include file="/inc/pagedef.jsp"%>
<bean:define id="contextPath" value="<%=request.getContextPath()%>"/>
<bean:define id="Manager" name="Manager"/>
<bean:define id="LoginUser" name="LoginUser"/>
<bean:define id="imagePlus" value="<%=contextPath+"/image/lplus.gif"%>"/>
<bean:define id="imageMinus" value="<%=contextPath+"/image/lminus.gif"%>"/>
<bean:define id="image" value=""/>
<html>
<head>
<tiles:insert page="/layout/head.jsp">
  <tiles:put name="title" value="Discuss List"/>
  <tiles:put name="jspName" value=""/>
</tiles:insert>
<style type="text/css">
  .replyStyle {
  font-family: Arial, Helvetica, sans-serif;
  font-size: 12px;
  line-height: normal;
  background-color: #F0F0F0;
  text-align: left;
  height:15.5px;
  display: none;
  table-layout:fixed;
  word-wrap:break-word;
  word-break:break-all;
  }

  .tableDataEvenCustom{
  font-family:
  &quot;
  Arial, Helvetica, sans-serif
  &quot;

  ;
  font-size: 12px;
  line-height: normal;
  background-color: #fffffd;
  text-align: left;
  height:15.5px;
  table-layout:fixed;
  word-wrap:break-word;
  word-break:break-all;
  }

  .tableDataOddCustom {
  font-family: Arial, Helvetica, sans-serif;
  font-size: 12px;
  line-height: normal;
  background-color: #F0F0F0;
  text-align: left;
  height:15.5px;
  table-layout:fixed;
  word-wrap:break-word;
  word-break:break-all;
  }
</style>
<script language="javaScript" type="text/javascript">
    var currentRowObj;
    var title;
    var titleRid;
    var replyRid;
    var dataType;
    var t_filledBy;
    var r_filledBy;
    var t_right_flag;
    var r_right_flag;
    var firstRowObj = null;
    function onclickRow(rowObj){
        try{
            currentRowObj = rowObj;
            dataType=currentRowObj.datatype;
            titleRid=currentRowObj.titleRid;
            replyRid=currentRowObj.replyRid;
            t_filledBy=currentRowObj.t_filledBy;
            r_filledBy=currentRowObj.r_filledBy;
            t_right_flag="false";
            r_right_flag="false";
            if("<%=LoginUser%>"=="<%=Manager%>"){
                t_right_flag="true";
                r_right_flag="true";
            }else if(dataType=='title'){
                if(t_filledBy=="<%=LoginUser%>")
                t_right_flag="true";
            }else if(dataType=='reply'){
                if(r_filledBy=="<%=LoginUser%>")
                r_right_flag="true";
            }

        }catch(ex ){
        }
    }

    function onBodyLoad(){
        try{
            changeRowColorlistTable(firstRowObj);
            onclickRow(firstRowObj);
            image="<%=imagePlus%>";
        }catch(ex){}
    }
    function onDbClickRow(rowObj){
     try{
            onModifyData(dataType);
     }catch(ex ){}
    }

	function onAddData(param) {
        var issueRid=param;
         openWindow('<%=contextPath%>/issue/issue/discuss/issueDiscussTitleAddPre.do?issueRid='+issueRid);
         //var option = "Top=200,Left=300,Height=310,Width=500,toolbar=no,scrollbars=no,status=yes";
        // window.open("<%=contextPath%>/issue/issue/discuss/issueDiscussTitleAddPre.do?issueRid="+issueRid,"",option);
     }

    function onDeleteData(param){
        var issueRid=param;
        if(currentRowObj==null){
            alert("Please select a row first!");
        }else if(replyRid=='null'||replyRid==''||replyRid==null||replyRid==""){
            if(t_right_flag=="true" ){
                if( confirm("Do you want to delete this row?"))
                    window.location= "<%=contextPath%>/issue/issue/discuss/issueDiscussTitleDelete.do?titleRid="+titleRid+"&issueRid="+issueRid;
            }else{
             alert("you have not enough privilege to delete this record!");
            }
        }else if(r_right_flag=="true"){
            if(confirm("Do you want to delete this row?"))
                window.location="<%=contextPath%>/issue/issue/discuss/issueDiscussReplyDelete.do?replyRid="+replyRid+"&issueRid="+issueRid;
        }else{
                alert("you have not enough privilege to delete this record!");
            }
    }

    function onReplyData(param){
         if(currentRowObj==null){
            alert("Please select a row first!");
        }
        else
         openWindow('<%=contextPath%>/issue/issue/discuss/issueDiscussReplyAddPre.do?titleRid='+titleRid);
    }

    function onModifyData(param){
          var issueRid=param;
          if(currentRowObj==null){
             alert("Please select a row first!");
          } else if(dataType=="title" ){
            //call modify title action
                openWindow('<%=contextPath%>/issue/issue/discuss/issueDiscussTitleUpdatePre.do?titleRid='+titleRid+"&right_flag="+t_right_flag);
          } else if(dataType=="reply") {
              //call midify reply action
              openWindow('<%=contextPath%>/issue/issue/discuss/issueDiscussReplyUpdatePre.do?replyRid='+replyRid+"&right_flag="+r_right_flag);
          }
    }

    function openWindow(url, windowTitle) {
        var height = 500;
        var width = 760;
        var topis = (screen.height - height) / 2;
        var leftis = (screen.width - width) / 2;
        var option = "height=" + height + "px"  +", width=" + width + "px" +", top=" + topis + "px" +", left=" + leftis + "px"  +", toolbar=no, menubar=no, scrollbars=no, resizable=yes,location=no, status=no";
        window.open(url,windowTitle,option);
    }

    <logic:present name="webVo" scope="request">
		<%-- 执行循环，每次从list中取出一个VbIssueDiscussTitle类型的item，定义此item的名称为discussTitle --%>
		<logic:iterate id="discussTitle" name="webVo" scope="request">
		    <bean:define id="discussTitleRid" name="discussTitle" property="rid"/>
			var status_<%=discussTitleRid%>="close";
			function swicthImage_<%=discussTitleRid%>() {
				if(status_<%=discussTitleRid%>=="close") {
					status_<%=discussTitleRid%>="open";
					img<%=discussTitleRid%>.src="<%=imageMinus%>";
				<logic:iterate id="discussReply" name="discussTitle" property="replys">
				    <bean:define id="discussReplyRid" name="discussReply" property="rid"/>
					<%="reply"+discussReplyRid+"_"+discussTitleRid%>.style.display="";
				</logic:iterate>
				} else {
					status_<%=discussTitleRid%>="close";
					img<%=discussTitleRid%>.src="<%=imagePlus%>";
				<logic:iterate id="discussReply" name="discussTitle" property="replys">
				    <bean:define id="discussReplyRid" name="discussReply" property="rid"/>
					<%="reply"+discussReplyRid+"_"+discussTitleRid%>.style.display="none";
				</logic:iterate>
				}
			}
		</logic:iterate>
	</logic:present>


</script></head>
<body onload="onBodyLoad();">
<html:table styleId="tableStyle">
  <html:tablehead styleId="tableTitleStyle">
    <html:tabletitle width="80" styleId="oracolumntextheader" toolTip="Q/A">Q/A</html:tabletitle>
    <html:tabletitle width="300" styleId="oracolumntextheader" toolTip="Title">Title</html:tabletitle>
    <html:tabletitle width="100" styleId="oracolumntextheader" toolTip="Date">Date</html:tabletitle>
    <html:tabletitle width="80" styleId="oracolumntextheader" toolTip="By">By</html:tabletitle>
    <html:tabletitle width="*" styleId="oracolumntextheader" toolTip="Attachment">Attachment</html:tabletitle>
  </html:tablehead>
  <html:tablebody styleId="tableDataStyle" height="100%" id="listTable">
    <bean:define id="onClickEvent" value=""/>
    <bean:define id="onDblClickEvent" value=""/>
    <%-- 判断webVo是否存在，如果存在则显示数据  --%>
    <logic:present name="webVo" scope="request">
      <%-- 执行循环，每次从list中取出一个VbIssueDiscussTitle类型的item，定义此item的名称为discussTitle --%>
      <logic:iterate id="discussTitle" name="webVo" scope="request">
        <bean:define id="discussTitleRid" name="discussTitle" property="rid"/>
        <bean:define id="t_title" name="discussTitle" property="title"/>
        <bean:define id="t_filledDate" name="discussTitle" property="filledDate"/>
        <bean:define id="t_filledBy" name="discussTitle" property="filledBy"/>
        <bean:define id="t_filledByScope" name="discussTitle" property="filledByScope"/>
        <bean:define id="t_attachmentDesc" name="discussTitle" property="attachmentDesc"/>
      <%String extendProperty1 = "titleRid=" + discussTitleRid + "  t_filledBy=" + t_filledBy;
        String t_filledByName = "";
        if(DtoUser.USER_TYPE_EMPLOYEE.equals(t_filledByScope)) {
          t_filledByName = HrFactory.create().getName((String)t_filledBy);
        } else {
          t_filledByName = HrFactory.create().getCustomerName((String)t_filledBy);
        }

      %>
        <%-- 显示Title(议题)行 --%>
        <html:tablerow id="<%="topic"+discussTitleRid%>" oddClass="tableDataOddCustom" evenClass="tableDataOddCustom" height="18px" canSelected="true" selfProperty="<%=extendProperty1+" replyrid='' r_filledBy='' datatype='title'"%>" onclick="onclickRow(this);">
          <html:tablecolumn styleId="oracelltext" toolTip="">
            <img id="img<%=discussTitleRid%>" alt="" src="<%=imagePlus%>" onclick="<%="swicthImage_"+discussTitleRid+"()"%>"/>
          </html:tablecolumn>
          <html:tablecolumn styleId="oracelltext" toolTip="<%=String.valueOf(t_title)%>" ondblclick="onDbClickRow();">
            <bean:write name="discussTitle" property="title"/>
          </html:tablecolumn>
          <html:tablecolumn styleId="oracelltext" toolTip="<%=String.valueOf(t_filledDate)%>">
            <bean:write name="discussTitle" property="filledDate"/>
          </html:tablecolumn>
          <html:tablecolumn styleId="oracelltext" toolTip="<%=t_filledBy+"/"+t_filledByName%>">
            <%=t_filledByName%>
          </html:tablecolumn>
          <html:tablecolumn styleId="oracelltext" toolTip="<%=String.valueOf(t_attachmentDesc)%>">
            <bean:define id="downloadUrl" name="discussTitle" property="attachment"/>
            <logic:notEmpty name="downloadUrl">
              <a href="<%=downloadUrl%>" target="_blank">
                <img alt="" style="border:0;height:13.5" src="<%=contextPath+"/image/download.gif"%>"/>
              </a>
            </logic:notEmpty>
            <bean:write name="discussTitle" property="attachmentDesc"/>
          </html:tablecolumn>
        </html:tablerow>
        <%-- 显示回复行(回复是隶属于议题的，所以每个议题下可能有多条回复，这又是一个子循环) --%>
        <logic:iterate id="discussReply" name="discussTitle" property="replys">
          <bean:define id="discussReplyRid" name="discussReply" property="rid"/>
          <bean:define id="r_title" name="discussReply" property="title"/>
          <bean:define id="r_filledDate" name="discussReply" property="filledDate"/>
          <bean:define id="r_filledBy" name="discussReply" property="filledBy"/>
          <bean:define id="r_filledByScope" name="discussReply" property="filledByScope"/>
          <bean:define id="r_attachmentDesc" name="discussReply" property="attachmentDesc"/>
        <%String extendProperty2 = extendProperty1 + " replyRid=" + discussReplyRid + "  r_filledBy=" + r_filledBy;
          String r_filledByName = "";
          if(DtoUser.USER_TYPE_EMPLOYEE.equals(r_filledByScope)) {
            r_filledByName = HrFactory.create().getName((String)r_filledBy);
          } else {
            r_filledByName = HrFactory.create().getCustomerName((String)r_filledBy);
          }
        %>
        <html:tablerow id="<%="reply"+discussReplyRid+"_"+discussTitleRid%>" oddClass="tableDataOddCustom" evenClass="tableDataOddCustom" height="18px" canSelected="true" selfProperty="<%=extendProperty2+" datatype='reply'"%>" onclick="onclickRow(this);">
            <html:tablecolumn styleId="oracelltext" toolTip="">&nbsp;</html:tablecolumn>
            <html:tablecolumn styleId="oracelltext" toolTip="<%=String.valueOf(r_title) %>" ondblclick="onDbClickRow();">
              <bean:write name="discussReply" property="title"/>
            </html:tablecolumn>
            <html:tablecolumn styleId="oracelltext" toolTip="<%=String.valueOf(r_filledDate) %>">
              <bean:write name="discussReply" property="filledDate"/>
            </html:tablecolumn>
            <html:tablecolumn styleId="oracelltext" toolTip="<%=r_filledBy+"/"+r_filledByName%>">
              <%=r_filledByName%>
            </html:tablecolumn>
            <html:tablecolumn styleId="oracelltext" toolTip="<%=String.valueOf(r_attachmentDesc)%>">
              <bean:define id="downloadUrl" name="discussReply" property="attachment"/>
              <logic:notEmpty name="downloadUrl">
                <a href="<%=downloadUrl%>" target="_blank">
                  <img alt="" style="border:0;height:13.5" src="<%=contextPath+"/image/download.gif"%>"/>
                </a>
              </logic:notEmpty>
              <bean:write name="discussReply" property="attachmentDesc"/>
            </html:tablecolumn>
          </html:tablerow>
        </logic:iterate>
      </logic:iterate>
    </logic:present>
  </html:tablebody>
</html:table>
<script language="javascript">
  <logic:present name="webVo" scope="request">
            <%-- 执行循环，每次从list中取出一个VbIssueDiscussTitle类型的item，定义此item的名称为discussTitle --%>
		    <logic:iterate id="discussTitle" name="webVo" scope="request">
				<bean:define id="discussTitleRid" name="discussTitle" property="rid"/>
                if(firstRowObj == null)
                firstRowObj = topic<%=discussTitleRid%>;
				<logic:iterate id="discussReply" name="discussTitle" property="replys">
					<bean:define id="discussReplyRid" name="discussReply" property="rid"/>
					<%="reply"+discussReplyRid+"_"+discussTitleRid%>.style.display="none";
				</logic:iterate>
			</logic:iterate>
  </logic:present>
  </script></body>
</html>
