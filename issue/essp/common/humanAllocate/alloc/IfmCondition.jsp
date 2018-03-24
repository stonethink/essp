<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>
<html>
<head>
	<tiles:insert page="/layout/head.jsp">
		        <tiles:put name="title" value="Allocate User Iframe OBS"/>
        <tiles:put name="jspName" value="Function"/>
</tiles:insert>
</head>
<script language="JavaScript">

var Skill1 = new Array();
<logic:iterate id="element" name="webVo" property="skillLevel1" indexId="index">
  Skill1["<bean:write name="element" property="value" />"] = "<bean:write name="element" property="label" />";
</logic:iterate>

var Skill2 = new Array();
<logic:iterate id="element" name="webVo" property="skillLevel2" indexId="index">
  Skill2["<bean:write name="element" property="value" />"] = "<bean:write name="element" property="label" />";
</logic:iterate>

var opera1 = new Array();
<logic:iterate id="element" name="webVo" property="opera1" indexId="index">
  opera1["<bean:write name="element" property="value" />"] = "<bean:write name="element" property="label" />";
</logic:iterate>

var languages = new Array();
<logic:iterate id="element" name="webVo" property="languages" indexId="index">
  languages["<bean:write name="element" property="value" />"] = "<bean:write name="element" property="label" />";
</logic:iterate>

var managements = new Array();
<logic:iterate id="element" name="webVo" property="managements" indexId="index">
  managements["<bean:write name="element" property="value" />"] = "<bean:write name="element" property="label" />";
</logic:iterate>

var generalRanks = new Array();
<logic:iterate id="element" name="webVo" property="generalRanks" indexId="index">
  generalRanks["<bean:write name="element" property="value" />"] = "<bean:write name="element" property="label" />";
</logic:iterate>

var postRanks = new Array();
<logic:iterate id="element" name="webVo" property="postRanks" indexId="index">
  postRanks["<bean:write name="element" property="value" />"] = "<bean:write name="element" property="label" />";
</logic:iterate>
</script>
<logic:present name="webVo" >
    <logic:equal value="" name="webVo" property="typeList" >
      <logic:iterate id="element" name="webVo"  property="detail" type="server.essp.common.humanAllocate.viewbean.VbDetailUser">
        <script language="JavaScript">
          //add(prm_loginid,prm_name,prm_code,prm_post)
          window.parent.parent.alloc_tool_result.add('<bean:write name="element" property="loginid"/>',
                                                 '<bean:write name="element" property="name"/>',
                                                 '<bean:write name="element" property="code"/>',
                                                 '<bean:write name="element" property="sex"/>');
        </script>
      </logic:iterate>
    </logic:equal>
    <logic:notEqual value="" name="webVo" property="typeList" >
      <logic:iterate id="element" name="webVo"  property="detail" type="server.essp.common.humanAllocate.viewbean.VbDetailUser">
        <script language="JavaScript">
          //alert('<bean:write name="element" property="loginid"/>');
          //window.parent.parent.parent.alloc_tool_result.add_select("Jun.Hu","hujun","UUUU0","Mail","PG","Java","C++","PG","DB","Admin","C","Rank3","sManage","sRank4","34");
          window.parent.parent.alloc_tool_result.add_select('<bean:write name="element" property="loginid"/>',
                                                     '<bean:write name="element" property="name"/>',
                                                     '<bean:write name="element" property="code"/>',
                                                     '<bean:write name="element" property="sex"/>',
                                                     postRanks['<bean:write name="element" property="postrank"/>'],
                                                     Skill1['<bean:write name="element" property="skill1"/>'],
                                                     Skill2['<bean:write name="element" property="skill2"/>'],
                                                     generalRanks['<bean:write name="element" property="skillrank"/>'],
                                                     opera1['<bean:write name="element" property="industry"/>'],
                                                     generalRanks['<bean:write name="element" property="industryrank"/>'],
                                                     languages['<bean:write name="element" property="language"/>'],
                                                     generalRanks['<bean:write name="element" property="languagerank"/>'],
                                                     managements['<bean:write name="element" property="management"/>'],
                                                     generalRanks['<bean:write name="element" property="managementrank"/>'],
                                                     '<bean:write name="webVo" property="typeList"/>');

        </script>
      </logic:iterate>
    </logic:notEqual>
</logic:present>
<body class="subbody" leftmargin="0" topmargin="0">
<html:form action="/common/humanAllocate/alloc/UserAllocByCondition" id="alloc_tool_condition_frm" onsubmit="return searchit();">
  <table border="0" cellspacing="1" cellpadding="1">
    <tr>
      <td width="460" background="../photo/alloc_tool/alloc_tool_back6.jpg" class="allocname">&nbsp;&nbsp;Allocate
        under conditions: </td>
    </tr>
  </table>
  <table width="443" border="0" cellspacing="1" cellpadding="1">
    <tr>
      <td height="5"></td>
    </tr>
  </table>
  <table width="460" border="0" cellspacing="1" cellpadding="1">
    <tr>
      <td width="8"></td>
      <td width="90" height="20" class="list_desc">PostRank</td>
      <td height="20" colspan="2" class="list_desc">
        <html:select name="postRank" styleId="sele">
                  <html:optionsCollection property="postRanks" name="webVo">
                  </html:optionsCollection>
        </html:select>
      </td>
    </tr>
    <tr>
      <td></td>
      <td height="20" class="list_desc">Skill</td>
      <td height="20" colspan="2">
        <html:select name="skill1" styleId="sele" onchange="redirect(this.options.selectedIndex)">
                   
                  <html:optionsCollection property="skillLevel1" name="webVo">
                  </html:optionsCollection>
        </html:select>

        <html:select name="skill2" styleId="sele">
           <option value="title"></option>
        </html:select>

        <html:select name="skillRank" styleId="sele">
                  
                  <html:optionsCollection property="generalRanks" name="webVo">
                  </html:optionsCollection>
        </html:select>
        </td>
    </tr>
    <tr>
      <td></td>
      <td height="20" class="list_desc">Industry</td>
      <td height="20" colspan="2">
        <html:select name="industry" styleId="sele">
           
                  <html:optionsCollection property="opera1" name="webVo">
                  </html:optionsCollection>
        </html:select>
        <html:select name="industryRank" styleId="sele">
           
                  <html:optionsCollection property="generalRanks" name="webVo">
                  </html:optionsCollection>
        </html:select>
        </td>
    </tr>
    <tr>
      <td></td>
      <td height="20" class="list_desc">Language</td>
      <td height="20" colspan="2" class="list_desc">
        <html:select name="language" styleId="sele">
         
                  <html:optionsCollection property="languages" name="webVo">
                  </html:optionsCollection>
        </html:select>
        <html:select name="languageRank" styleId="sele">
           
                  <html:optionsCollection property="generalRanks" name="webVo">
                  </html:optionsCollection>
        </html:select>
      </td>
    </tr>
    <tr>
      <td></td>
      <td height="20" class="list_desc">Management</td>
      <td height="20" class="list_desc">
        <html:select name="management" styleId="sele">
          
                  <html:optionsCollection property="managements" name="webVo">
                  </html:optionsCollection>
        </html:select>
        <html:select name="managementRank" styleId="sele">
          <option value="title"></option>
                  <html:optionsCollection property="generalRanks" name="webVo">
                  </html:optionsCollection>
        </html:select>
      </td>
      <td valign="bottom" class="list_desc">
        <div align="right">
          <input type="submit" value="Search" style="width:60" class="button" >
        </div></td>
    </tr>
  </table>
</html:form>
<script language="JavaScript">
var Skill2Opt = new Array();
//生成和skill2数组，用于下拉列表的关联
<logic:iterate id="element" name="webVo" property="skillLevel2" indexId="index">
  Skill2Opt[<%=index%>] = new Option("<bean:write name="element" property="label" />","<bean:write name="element" property="value" />");
</logic:iterate>
function redirect(y) {
  for(i=document.alloc_tool_condition_frm.skill2.options.length-1;i>=0;i--) {
    document.alloc_tool_condition_frm.skill2.options[i] = null;
  }
  var skill1Code = document.alloc_tool_condition_frm.skill1.options[y].value;
  if(y>0){
    var count = 0;
    for(var i = 0;i < Skill2Opt.length; i++){
      if(Skill2Opt[i].value.substring(0,1) == skill1Code){
        document.alloc_tool_condition_frm.skill2.options[count]=Skill2Opt[i];
        count ++;
      }
    }
  }else{
    document.alloc_tool_condition_frm.skill2.options[0] = new Option("","title");;
  }
}
function searchit() {
    //将查询结果添加到下面的Result中
    //add_select(prm_loginid,prm_name,prm_code,prm_sex,sPostRank,sProSkill1,sProSkill2,sRank1,sOperate1,sRank2,sLanguage,sRank3,sManage,sRank4,type_list) {
    //window.parent.parent.parent.alloc_tool_result.add_select("Jun.Hu","hujun","UUUU0","Mail","PG","Java","C++","PG","DB","Admin","C","Rank3","sManage","sRank4","34");
    //window.parent.parent.parent.alloc_tool_result.add_select("Run","run","UUUU2","PG","Java","C++","PG","DB","Admin","C","Rank3","sManage","sRank4","235");
    //document.alloc_obs.submit();
    return true;
}
</script>
</body>
</html>
