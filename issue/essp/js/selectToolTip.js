function showTip(objname,divname,event){

	var selobj=objname;
	var divobj=document.getElementById(divname);
	divobj.style.left=selobj.offsetLeft;
	divobj.style.top=event.clientY + 20;
    var title = selobj.options[selobj.selectedIndex].title;

    //对select标签的属性值进行判断
    //当title的值存在时，输出其title值，否则判断其text的值


    if(title!=null && title.length>0){
         divobj.style.visibility="visible";
         divobj.innerText=title;
    }
}

function CloseTip(divname){
	var divobj=document.getElementById(divname)
	divobj.style.visibility="hidden"
}

