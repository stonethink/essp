//

function changetypetolayer4(){
	window.parent.document.all.t1.className = "tab";
	window.parent.document.all.t2.className = "tab";
	window.parent.document.all.t3.className = "tab";
	window.parent.document.all.t4.className = "selTab";
	window.parent.document.all.Layer1.style.visibility="visible";
	window.parent.document.all.Layer2.style.visibility="hidden";
	window.parent.document.all.Layer3.style.visibility="hidden";
	window.parent.document.all.Layer4.style.visibility="hidden";

}

function centerWindow(title,requester,person,action) {
if (document.all){
	var xMax = screen.width, yMax = screen.height;
} else {
	if (document.layers){
		var xMax = window.outerWidth, yMax = window.outerHeight;
	} else {
		var xMax = 640, yMax=480;
	}
}
var xOffset = (xMax - 200)/2;
var yOffset = (yMax - 200)/2;
window.open('CheckDialog.jsp?title='+title+'&requester='+requester+'&person='+person+'&action='+action,'myExample', 'width=300,height=100,screenX='+xOffset+',screenY='+yOffset+', top='+yOffset+',left='+xOffset+'');
//window.open('CheckDialog.jsp?title=outgoing','', 'width=300,height=100,screenX='+xOffset+',screenY='+yOffset+', top='+yOffset+',left='+xOffset+'');
}

function showDialog(person,action,requester,title){
vReturnValue = window.showModalDialog('CheckDialog.jsp?title='+title+'&requester='+requester+'&person='+person+'&action='+action,'myExample', 'dialogHeight:150px;dialogWidth:350px;center:yes;status:no;edge:raised');
if(vReturnValue=="Submit")
  form.submit();
else if(vReturnValue=="Cancel")
  changetypetolayer4();
}

function showProblemDialog(){
vReturnValue = window.showModalDialog('../CheckDialog.jsp','myExample', 'dialogHeight:150px;dialogWidth:400px;center:yes;status:no;edge:raised');
if(vReturnValue=="Submit")
  form.submit();
else if(vReturnValue=="Cancel")
  changetypetolayer4();
}

//判断是不是日期型字符串
    function isDateString( data ){
        //alert("Seq No. format error:" + data);
        var patern = /^\d{4}(\-|\\|\/)\d{1,2}(\-|\\|\/)\d{1,2}$/;
        //var patern = /^\d{4}-\d{1,2}-\d{1,2}$/;
        if(!data.match(patern)){
            return false;
        }
        return true;
    }
    function CheckNo(data){
    	var patern = /^\d{4}-\d{4}(\-|\\|\/)\d{1,2}(\-|\\|\/)\d{1,2}-\d{1,4}$/;
        //var patern = /^\d{4}-\d{1,2}-\d{1,2}$/;
        if(!data.match(patern)){
            return false;
        }
        return true;

    	}
//判断是不是时间型字符串
    function isTimeString(data){
        //alert("Seq No. format error:" + data);
        var patern1 = /^\d{1,2}:\d{2}:\d{2}$/;
        var patern2 = /^\d{1,2}:\d{2}$/;
        if((!data.match(patern1))&&(!data.match(patern2))){
            return false;
        }
        return true;
    }



    //判断是否数字
    function CheckNoHour(data){
    	var patern = /^\d{1,4}$/;
        //var patern = /^\d{4}-\d{1,2}-\d{1,2}$/;
        if(!data.match(patern)){
            return false;
        }
        return true;

    	}

//判断是不是日期时间型字符串
    function isDateTimeString( data ){
        //alert("Seq No. format error:" + data);
        var patern = /^\d{4}(\-|\\|\/)\d{1,2}(\-|\\|\/)\d{1,2} \d{1,2}:\d{2}$/;
        //var patern = /^\d{4}-\d{1,2}-\d{1,2}$/;
        if(!data.match(patern)){
            return false;
        }
        return true;
    }




         //根据起始日期时间，得出间隔的小时数
    function getLTolHours( data1,data2)
    {

        var aintf= (data1.substring(0,4))-1900;
        var bintf= (data1.substring(5,7))-1;
        var cintf= (data1.substring(8,10));
        var dintf= (data1.substring(11,13));
        var eintf= (data1.substring(14,16));
        var fintf=0;
        var aintt= (data2.substring(0,4))-1900;
        var bintt= (data2.substring(5,7))-1;
        var cintt= (data2.substring(8,10));
        var dintt= (data2.substring(11,13));
        var eintt= (data2.substring(14,16));
        var fintt=0;

         var fDate=new  Date(aintf,bintf,cintf,dintf,eintf,fintf);
         var tDate=new  Date(aintt,bintt,cintt,dintt,eintt,fintt);
        var time=tDate.getTime()-fDate.getTime();
        var surplusLong=time/3600000;
       // var surplusLong=24;
        return surplusLong;
    }

    //根据ACSII码，获取任意可见字符串的长度
    function getStringLength(str){
		var stringLength = 0;
		if(str!=null){
			var strLength = str.length;
			for(var i=0;i<strLength;i++){
				var charAtI = str.charAt(i);
				if(charAtI >= '!' && charAtI <= '~'){
					stringLength++;
				}else{
					stringLength += 2;
				}
			}
		}
		return stringLength;
	}

