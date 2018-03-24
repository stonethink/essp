function getParameterMap(form) {
    var p = document.forms[form].elements;
    var map = new Object();
    for(var x=0; x < p.length; x++) {
        var key = p[x].name;
        var val = p[x].value;
        
        //Check if this field name is unique.
        //If the field name is repeated more than once
        //add it to the current array.
        var curVal = map[key]; 
        if (curVal) { // more than one field so append value to array
        	curVal[curVal.length] = val;
        } else { // add field and value
        	map[key]= [val];
        }
    }
    return map;
}

function setFormAction(form, action, method) {
	if (action) {
		document.forms[form].setAttribute('action', action);
	}
	
	if (method) {
		document.forms[form].setAttribute('method', method);
	}
	
	document.forms[form].ec_eti.value='';
}


var oldRowObj;
var oldRowBgColor;
function onChangeBackgroundColor(obj){
    //alert(obj.className);
	//alert(obj.style.backgroundColor);
     if(oldRowObj!=null && oldRowBackgroundColor!=null){
	      // var rowIndex=oldRowObj.rowIndex;
	       //if(rowIndex%2==0){
	         // oldRowObj.style.background="#F0F0F0";
	       //}else{
	        //  oldRowObj.style.background="#fffffd";
	       //}
	      // alert("oldRowObj:"+oldRowObj.innerHTML);
	    //   alert("oldRowBackground:"+oldRowBackground);
	    //   alert("oldRowObj.style.background:"+oldRowObj.style.background);
	     oldRowObj.style.backgroundColor=oldRowBackgroundColor;
	      //oldRowObj.className= oldRowBackground;

	    }
//	    alert("oldRowBackground 2:"+oldRowBackground);
        oldRowBackgroundColor=obj.style.backgroundColor;
	    oldRowObj=obj;
	    //alert(obj.style);
//	    oldRowBackground=obj.className
//		alert(oldRowBackground);
		obj.style.backgroundColor='#ccccff';

}
