function showTip(objname,divname,event){

	var selobj=objname;
	var divobj=document.getElementById(divname);
	divobj.style.left=selobj.offsetLeft;
	divobj.style.top=event.clientY + 20;
    var title = selobj.options[selobj.selectedIndex].title;

    //��select��ǩ������ֵ�����ж�
    //��title��ֵ����ʱ�������titleֵ�������ж���text��ֵ


    if(title!=null && title.length>0){
         divobj.style.visibility="visible";
         divobj.innerText=title;
    }
}

function CloseTip(divname){
	var divobj=document.getElementById(divname)
	divobj.style.visibility="hidden"
}

