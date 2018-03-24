<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>
<html>
<head>
<tiles:insert page="/layout/head.jsp">
  <tiles:put name="title" value=" IssueTypeDefine"/>
  <tiles:put name="jspName" value="Result"/>
</tiles:insert>
</head>

<body class="subbody" leftmargin="0" topmargin="0" onscroll="scrollit()">
<form name="form1" method="post" action="">
  <table width="500" border="0" cellpadding="0" cellspacing="0">
    <tr>
      <td bgcolor="#cccccc"> <table width="100%" border="0" cellspacing="1" cellpadding="0">
	  <tbody id="tbodyresult">

        </tbody>
        </table></td>
    </tr>
  </table>
  </form>
</body>
<script language="JavaScript">
function showlength(str,x){
      var tstr="";
      if(str==null||str==""||str=='null'){
         return tstr;
      }
      var i=str.length;
      tstr=str;
      if(i<=x)return tstr;
      tstr=str.substring(0,x-2)+"...";
      return tstr;
}
<%-- 滚动条事件 --%>
function scrollit(){
    parent.frm12.scrollTo(document.body.scrollLeft,0);
	parent.frm21.scrollTo(0,document.body.scrollTop);
}
<%-- 点击每行事件 --%>
function onClickRow(prm_id) {
    var prm_tr = eval("tr"+prm_id);
    //对应左边的记录
    var prm_bro_tr = eval("window.parent.frm21.tr"+prm_id);
    var j = prm_id-1;

    if(prm_tr.className == "line2") {
      prm_tr.className = "line3";
    } else {
      prm_tr.className = "line2";
    }
    <%-- 点击左边相应的那行记录 --%>
    window.parent.frm21.onClickRow(prm_id);
}

var num = 0;
function insert_select(parent,brother,type_list){
    num++;
    var p=document.getElementById(parent);
    var b=document.getElementById(brother);

    var tr=document.createElement('<tr id="tr'+num+'" onClick="onClickRow('+num+')" class="line2">' );
    var tdcode=document.createElement('<td width="100" class="oracelltext" id="tdcode'+num+'" height="20" title=""></td>');
    var tdname=document.createElement('<td width="100" class="oracelltext" id="tdname'+num+'" height="20" title=""></td>');
    var tdsex=document.createElement('<td width="100" class="oracelltext" id="tdsex'+num+'" height="20" title=""></td>');
    var tdpostrank=document.createElement('<td width="100" class="oracelltext" id="tdpostrank'+num+'" height="20" title=""></td>');
    var tditskill = document.createElement('<td width="100" class="oracelltext" id="tditskill'+num+'" height="20" title=""></td>');
    var tdindustry = document.createElement('<td width="100" class="oracelltext" id="tdindustry'+num+'" height="20" title=""></td>');
    var tdlanguage = document.createElement('<td width="100" class="oracelltext" id="tdlanguage'+num+'" height="20" title=""></td>');
    var tdmanagement = document.createElement('<td width="100" class="oracelltext" id="tdmanagement'+num+'" height="20" title=""></td>');
    p.insertBefore(tr,b);
    tr.appendChild(tdcode);
    tr.appendChild(tdname);
    tr.appendChild(tdsex);
    var num_sort = parseInt(type_list);
    if(num_sort!=0){
	do{
		var x = num_sort%10;
		num_sort = (num_sort-x)/10;
		if(x==1){
			tr.appendChild(tdpostrank);
		}else if(x==2){
			tr.appendChild(tditskill);
		}else if(x==3){
			tr.appendChild(tdindustry);
		}else if(x==4){
			tr.appendChild(tdlanguage);
		}else if(x==5){
			tr.appendChild(tdmanagement);
		}
	}while(num_sort>0);
    }
}
<%-- 插入新记录 --%>
function insert(parent,brother){
    num++;
    var p=document.getElementById(parent);
    var b=document.getElementById(brother);
    var tr=document.createElement('<tr id="tr'+num+'" onClick="onClickRow('+num+')" class="line2">' );
    var tdcode=document.createElement('<td width="100" class="oracelltext" id="tdcode'+num+'" height="20" title=""></td>');
    var tdsex=document.createElement('<td width="100" class="oracelltext" id="tdsex'+num+'" height="20" title=""></td>');
    var tdname = document.createElement('<td width="100" class="oracelltext" id="tdname'+num+'" height="20" title=""></td>');
    p.insertBefore(tr,b);
    tr.appendChild(tdcode);
    tr.appendChild(tdname);
    tr.appendChild(tdsex);
}
<%-- 添加记录，只包含必须信息  --%>
function add(prm_code,prm_name,prm_sex){
	insert('tbodyresult','tr'+num);
	var tdcode = eval("tdcode"+num);
	var tdsex = eval("tdsex"+num);
	var tdname = eval("tdname"+num);

	tdcode.innerText = prm_code;
	tdsex.innerText = showlength(prm_sex,20);
	tdsex.title = prm_sex;
	tdname.innerText = prm_name;
}
<%-- 添加记录，包含其他信息  --%>
function add_select(prm_code,prm_name,prm_sex,prm_rank,prm_skill,prm_industry,prm_language,prm_management,type_list){
        var num_sort = parseInt(type_list);
	if(num_sort==0){
		var tdpostrank = eval("tdpostrank"+num);
		tdpostrank.innerText = showlength(prm_rank,15);
		tdpostrank.title = prm_rank;
		var tditskill = eval("tditskill"+num);
		tditskill.innerText = showlength(prm_skill,15);
		tditskill.title = prm_skill;
		var tdindustry = eval("tdindustry"+num);
		tdindustry.innerText = showlength(prm_industry,15);
		tdindustry.title = prm_industry;
	}else{
          insert_select('tbodyresult','tr'+num,type_list);
          var tdcode = eval("tdcode"+num);
          var tdsex = eval("tdsex"+num);
          var tdname = eval("tdname"+num);
          tdcode.innerText = prm_code;
          tdsex.innerText = showlength(prm_sex,20);
          tdsex.title = prm_sex;
          tdname.innerText = prm_name;
	do{
		var x = num_sort%10;
		num_sort = (num_sort-x)/10;
		if(x==1){
		var tdpostrank = eval("tdpostrank"+num);
		tdpostrank.innerText = showlength(prm_rank,15);
		tdpostrank.title = prm_rank;
		}else if(x==2){
		var tditskill = eval("tditskill"+num);
		tditskill.innerText = showlength(prm_skill,15);
		tditskill.title = prm_skill;
		}else if(x==3){
		var tdindustry = eval("tdindustry"+num);
		tdindustry.innerText = showlength(prm_industry,15);
		tdindustry.title = prm_industry;
		}else if(x==4){
		var tdlanguage = eval("tdlanguage"+num);
		tdlanguage.innerText = showlength(prm_language,15);
		tdlanguage.title = prm_language;
		}else if(x==5){
		var tdmanagement = eval("tdmanagement"+num);
		tdmanagement.innerText = showlength(prm_management,15);
		tdmanagement.title = prm_management;
		}
	}while(num_sort>0);
	}

}

</script>
</html>
