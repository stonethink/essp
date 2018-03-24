function showTip(objname,divname,event){

    var selobj=objname;
    var divobj=document.getElementById(divname);
    if(divobj == null) return;
    divobj.style.left=event.clientX+1;
    divobj.style.top=event.clientY+1;
    if(selobj.selectedIndex >= 0){
      var title=selobj.options[selobj.selectedIndex].innerText;
      var strWidth=100;
      var strHeight=15;

      var textarea='<TEXTAREA COLS=50 ROWS=5 id="textAreaForTooltip" style="font-family: Arial, Helvetica, sans-serif;font-size: 10px; font-style: normal;visibility:hidden;"></TEXTAREA>';
      var collTextarea = document.getElementById("textAreaForTooltip");


      if(collTextarea==null){
        collTextarea=document.createElement(textarea);
        document.body.insertBefore(collTextarea);
        collTextarea = document.getElementById("textAreaForTooltip");
      }

       if(collTextarea!=null){
        collTextarea.innerText=title;
          var oTextRange=collTextarea.createTextRange();
          strWidth=oTextRange.boundingWidth;
          if(strWidth==0){
            strHeight=0;
          }else{
            strWidth+=15;
            strHeight=oTextRange.boundingHeight+10;
          }
//          alert("width:"+strWidth+",Height:"+strHeight);
          }
        divobj.style.visibility="visible";
        divobj.style.width=strWidth<=0?strWidth:strWidth-2;
        var addIframe='<iframe id="ttaattaa" src="" style="position:absolute;top:-1px; left:-3px; width:'+strWidth+'px; height:'+strHeight+'px;filter=\'progid:DXImageTransform.Microsoft.Alpha(style=0,opacity=0)\';"></iframe>';

	divobj.innerHTML=title+'<br>'+addIframe;

    }
}

function CloseTip(divname){
	var divobj=document.getElementById(divname)
	if(divobj == null) return;
	divobj.style.visibility="hidden"
}


