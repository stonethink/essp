function updateOptionList(selectboxId,labels,values) {
	var selectbox=window.eval(selectboxId);
	selectbox.length=0;
	for(var i=0;i<labels.length;i++) {
		selectbox.options[i]=new Option(labels[i],values[i]);
	}
}

function setSelectedValue(selectboxId,defaultValue) {
	var selectbox=window.eval(selectboxId);
	for(var i=0;i<selectbox.options.length;i++) {
           if(selectbox.options[i].value==defaultValue) {
             selectbox.options[i].selected=true;
             //alert("equal: compare ["+selectbox.options[i].value+"] and ["+defaultValue+"]");
           } else {
             selectbox.options[i].selected=false;
             //alert("not equal: compare ["+selectbox.options[i].value+"] and ["+defaultValue+"]");
           }
	}
}
