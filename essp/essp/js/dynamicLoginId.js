//default ajax service
//var END_POINT="<%=request.getContextPath()%>/xmlhttp";
//var buffalo = new Buffalo(END_POINT);
var getArrayLength = 0;
var tempResultArray;
var nameElementArray;
var loginIdElementArray;
var domainElementArray = null;
var typeElementArray = null;
var highColor = "00FFFF";
var lowColor = "E6D296";
var currentSpanIndex = null;
var lastSearchText = "";
var currentIndex;
var oldNameValue = "";
var oldLoginIdValue = "";
var oldDomainValue = "";
var oldTypeValue = "";
var checkOkFlag;
var mouseOnFlag = false;
var searchFlag = false;

window.onload = function(){
        var elemSpan = document.createElement("div");
        elemSpan.id = "spanOutput";
        document.body.appendChild(elemSpan);
        var elementArray = null;
        try {
        	elementArray = getQueryUserElementArray();
      	} catch(e) {
      		return;
      	}
        if(elementArray == null) {
        	return;
        }
        nameElementArray = elementArray[0];
        for(var i = 0; i < nameElementArray.length; i++) {
        	var oneNameElement = nameElementArray[i];
        	oneNameElement.setAttribute("onkeyup",function(){onInputNameTextKeyUp(this);});
        	oneNameElement.setAttribute("onkeydown",function(){onInputNameTextKeyDown();});
        	oneNameElement.setAttribute("onblur",function(){onInputNameTextBlur(this);});
        	oneNameElement.setAttribute("onfocus",function(){onInputNameTextFocus(this);});
      	}
        loginIdElementArray = elementArray[1];
        if(elementArray.length >= 3) {
        	domainElementArray = elementArray[2];
      	}
      	if(elementArray.length >= 4) {
        	typeElementArray = elementArray[3];
        }
        //Listing 10.7
          //SetProperties(document.loginForm.txtUserInput,
            //document.loginForm.txtUserValue,//'typeAheadXML.js',
            //true,true,true,true,"No matching Data",false,null);
      }
      
			function SetElementPosition(theTextBoxInt){
        //alert("***********");
        var selectedPosX = 0;
        var selectedPosY = 0;
        var theElement = theTextBoxInt;
        if (!theElement) return;
        var theElemHeight = theElement.offsetHeight;
        var theElemWidth = theElement.offsetWidth;
        while(theElement != null){
          selectedPosX += theElement.offsetLeft;
          selectedPosY += theElement.offsetTop;
          theElement = theElement.offsetParent;
        }
        xPosElement = document.getElementById("spanOutput");
        xPosElement.style.left = selectedPosX;
        xPosElement.style.width = theElemWidth;
        xPosElement.style.top = selectedPosY + theElemHeight
        xPosElement.style.display = "none";
        xPosElement.style.position = "absolute";
        xPosElement.style.backgroundColor = lowColor;
}     
      
      
      
      var theTextBox;
      var elementSrc = '';
      var arrOptions = new Array();
      var strLastValue = "";
      var bMadeRequest;
      var objLastActive;
      var currentValueSelected = -1;
      var bNoResults = false;
      var isTiming = false;
      var undeStart = "<span class='spanMatchText'>";
      var undeEnd = "</span>";
      var selectSpanStart = "<span style='width:100%;display:block; font:11pt;white-space: nowrap;' class='spanNormalElement' onmouseover='onSpanMouseOn(this)' onmouseleave='onSpanMouseLeave(this)' ";
      var selectSpanEnd ="</span>";
 
       var flag = false;
       function listResult(reply) {
           //alert("reply="+reply);
           var ct =document.getElementById("spanOutput");
           //alert("ct= "+ct.className);
           if(flag){
               //var len = ct.rows.length ;
               //for( var i = 0 ; i < len; i ++ ){
               //    ct.deleteRow();
               //}
           }
           flag = true;
           currentSpanIndex = null;
           var theMatches = '';
           var responseArray = reply;
            
           if (responseArray == null || responseArray.length == 0) {
               getArrayLength = 0;
               tempResultArray = new Array();
               selectSpanMid = selectSpanStart+" onclick='SetText(" + 0 + ")'" +
                     " id='OptionsList_" +
                     0 + "' theArrayNumber='"+ 0 +"' " +"code='No Result"+ "'>";
               selectSpanValue = undeStart+"No Result"+undeEnd+undeEnd;
               theMatches += selectSpanMid + selectSpanValue ;
                
           } else {
            	tempResultArray = responseArray;
            	getArrayLength = responseArray.length;
              for(var i=0;i<responseArray.length;i++) {
                  var doc = responseArray[i];
                  selectSpanMid = selectSpanStart+" onclick='SetTextOnClick(" + i + ")'" +
                    " id='OptionsList_" +
                    i + "' theArrayNumber='"+ i +"' " +"code='"+ doc.userName +"' title='"+doc.userLoginId+"'>";
                  selectSpanValue = undeStart+doc.userName+undeEnd+undeEnd;
                  theMatches += selectSpanMid + selectSpanValue ;
              }
                
            }
              
            if(theMatches.length > 0){
               //alert("theMatches="+theMatches);
               document.getElementById("spanOutput").innerHTML = theMatches;
               document.getElementById("OptionsList_0").className="spanHighElement";
               currentValueSelected = 0;
               bNoResults = false;
            } else {
               currentValueSelected = -1;
               bNoResults = true;
               if(theTextBox.obj.showNoMatchMessage) {
                  document.getElementById("spanOutput").innerHTML =
                      "<span class='noMatchData'>" + theTextBox.obj .noMatchingDataMessage + "</span>"; 
               } else {
                	HideTheBox();
               }
            }
              checkValueOk();
              ShowSpan("spanOutput");
              searchFlag = false;
            }
            
        
        
        //hide box spanOutput
        function HideTheBox(){
            document.getElementById("spanOutput").style.display = "none";
            currentValueSelected = -1;
            //EraseTimeout();
        }
        
        //selected element
        function GrabHighlighted(){
            if(currentValueSelected >= 0){
                  //xVal is the value of array[i]
                  var xVal = document.getElementById("OptionsList_" +
                              currentValueSelected);
                  //alert("currentValueSelected= "+currentValueSelected);
                              
                  //alert("xVal= "+xVal.code);
                              
                  SetText(xVal);
                  HideTheBox();
            }
        }
        
        function SetText(xVal){
        
            var value = tempResultArray[currentValueSelected];
            //alert(value.code);
            //add for firefox
            theTextBox.value = value.code ;//set text value
        
            //theTextBox.value = xVal.code; //set text value
            //alert("theTextBox.value= "+theTextBox.value);
            //theTextBox.obj.hidden.value = arrOptions[xVal][1];
            document.getElementById("spanOutput").style.display = "none";
            currentValueSelected = -1; //remove the selected index
        }
        
        function SetTextOnClick(elem){
        	if(getArrayLength == 0) {
      			return;
      		}
      		var name = tempResultArray[elem].userName;
      		var loginId = tempResultArray[elem].userLoginId;
      		var domain = tempResultArray[elem].domain;
      		var type = tempResultArray[elem].userType;
          nameElementArray[currentIndex].value = name;
          nameElementArray[currentIndex].title = loginId + "/" + name;
          loginIdElementArray[currentIndex].value = loginId;
          if(domainElementArray != null) {
          	domainElementArray[currentIndex].value = domain;
          	oldDomainValue = domain;
          }
          if(typeElementArray != null) {
          	typeElementArray[currentIndex].value = type;
          	oldTypeValue = type;
          }
          oldNameValue = name;
          oldLoginIdValue = loginId;
          HideTheBox();
          lastSearchText = "";
          checkOkFlag = true;
        }
        
        function MoveHighlight(xDir){
            if(currentValueSelected >= 0){
                  newValue = parseInt(currentValueSelected) + parseInt(xDir);
                  if(newValue > -1 && newValue < getArrayLength){
                        currentValueSelected = newValue;
                        SetHighColor (null);
                  }
            }
       }
       
      function onSpanMouseOn(myText) {
      	if(getArrayLength == 0 || myText == null) return;
      	mouseOnFlag = true;
      	SetHighColor(myText);
      }
      function onSpanMouseLeave(myText) {
      	if(getArrayLength == 0 || myText == null) return;
      	SetLowColor(myText);
      	mouseOnFlag = false;
      }
      
      function SetHighColor(theTextBox){
       	//if(getArrayLength == 0 || theTextBox == null) return;
        theTextBox.style.backgroundColor=highColor;
      }
      function SetLowColor(theTextBox) {
      	//if(getArrayLength == 0 || theTextBox == null) return;
        theTextBox.style.backgroundColor=lowColor;
      }
      
      function ShowSpan(divid){
            var xPosElement = document.getElementById(divid);
            var addIframe = '<iframe src="javascript:false" style="position:absolute; visibility:inherit; top:0px; left:0px; width:200px; height:200px; z-index:-1; filter=\'progid:DXImageTransform.Microsoft.Alpha(style=0,opacity=0)\';"></iframe>';
            xPosElement.innerHTML = xPosElement.innerHTML + addIframe;
            xPosElement.style.display = "block";
       }
       
      function selectNextSpan(offsetV) {
      	if(getArrayLength == 0) {
      		return;
      	}
      	if(currentSpanIndex == null) {
      		currentSpanIndex = 0;
      	} else {
      		unSelectSpan(currentSpanIndex);
      		currentSpanIndex += offsetV;
      		if(currentSpanIndex >= getArrayLength) {
      			currentSpanIndex = 0;
      		} else if(currentSpanIndex < 0) {
      			currentSpanIndex = getArrayLength -1;
      		}
      	}
      	var cSpan = document.getElementById("OptionsList_"+currentSpanIndex);
      	SetHighColor(cSpan);
      }
      function unSelectSpan(spanIndex) {
      	var cSpan = document.getElementById("OptionsList_"+spanIndex);
      	SetLowColor(cSpan);
      }
      function showCurrentSpan() {
      	if(currentSpanIndex == null) return;
      	SetTextOnClick(currentSpanIndex);
      }
      function onInputNameTextKeyDown() {
    		if(event.keyCode == 40) {
					selectNextSpan(1);
				} else if(event.keyCode == 38) {
					selectNextSpan(-1);
				} else if(event.keyCode == 13) {
					showCurrentSpan();
				}
    	}
    function onInputNameTextKeyUp(myText) {

			var att = myText.value;
			if(event.keyCode == 40 || event.keyCode == 38 || event.keyCode == 13) {
					return;
				}
			SetElementPosition(myText);
			if(att == "") {
				lastSearchText = "";
				checkOkFlag = true;
				loginIdElementArray[currentIndex].value = "";
				if(domainElementArray != null) {
          	domainElementArray[currentIndex].value = "";
        }
        if(typeElementArray != null) {
          typeElementArray[currentIndex].value = "";
        }
				return false;
			}
			if(lastSearchText != att) {
				QueryPerson.query(att, listResult);
				lastSearchText = att;
			}
			return false;
		}
		
		function onInputNameTextBlur(myText) {
			if(checkOkFlag == false) {
				nameElementArray[currentIndex].value = oldNameValue;
      	loginIdElementArray[currentIndex].value = oldLoginIdValue;
      	if(domainElementArray != null) {
          domainElementArray[currentIndex].value = oldDomainValue;
        }
        if(typeElementArray != null) {
         	typeElementArray[currentIndex].value = oldTypeValue;
        }
			}
			if(mouseOnFlag == false) {
			  HideTheBox();
			}
			lastSearchText = "";
		}
		
		function onInputNameTextFocus(myText) {
			oldValue = myText
			for(var i = 0; i < nameElementArray.length; i++) {
        if(myText == nameElementArray[i]) {
        	currentIndex = i;
        	oldNameValue = nameElementArray[currentIndex].value;
        	oldLoginIdValue = loginIdElementArray[currentIndex].value;
        	if(domainElementArray != null) {
          	oldDomainValue = domainElementArray[currentIndex].value;
          }
          if(typeElementArray != null) {
          	oldTypeValue = typeElementArray[currentIndex].value;
          }
        	break;
        }
      }
		}
		
		function checkValueOk() {
			var cNameValue = nameElementArray[currentIndex].value;
			for(var i = 0; i < tempResultArray.length; i++) {
        if(cNameValue == tempResultArray[i].userName) {
        	loginIdElementArray[currentIndex].value = tempResultArray[i].userLoginId;
        	if(domainElementArray != null) {
          	domainElementArray[currentIndex].value = tempResultArray[i].domain;
        	}
        	if(typeElementArray != null) {
         		typeElementArray[currentIndex].value = tempResultArray[i].userType;
        	}
        	checkOkFlag = true;
        	return;
        }
      }
      checkOkFlag = false;
		}

