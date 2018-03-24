               var imgPrefix="img";
               var tdPrefix="td";
			   var layerPrefix="Layer";
               var imagesDir="../images";
               var invokeParam=null;
               var currentTabIndex=1;
               var systabButtonMap=null;

               function clearall(tabId,totalCount){

                        var i=1;
                        var prefixId="";

                        if(tabId!=null && tabId!="") {
							prefixId=tabId+"_";
						}

                        for(i=1;i<=totalCount;i++) {
                            var imgName=prefixId+imgPrefix+i;
                            var tdName=prefixId+tdPrefix+i;
                            var layerName=prefixId+layerPrefix+i;
                            //alert("imgName="+imgName+",tdName="+tdName+",layerName="+layerName);
                            if(i==1) {
                              document.getElementById(imgName).src=imagesDir+"/fun_left.jpg";
                            } else if(i==tabCount) {
                              document.getElementById(imgName).src=imagesDir+"/fun_right.jpg";
                            } else {
                              document.getElementById(imgName).src=imagesDir+"/fun_lr.jpg";
                            }
                            document.getElementById(tdName).background=imagesDir+"/fun_back.jpg";
                            //alert(document.getElementById(tdName).style.borderTopColor);
                            document.getElementById(tdName).style.borderTopColor="";
                            document.getElementById(tdName).style.borderTopWidth="";
                            //document.getElementById(tdName).class="Grid2";
                            document.getElementById(layerName).style.visibility="hidden";
                        }
                        document.getElementById(prefixId+imgPrefix+(totalCount+1)).src=imagesDir+"/fun_right.jpg";
                        if(totalCount>2) {
                            var imgId=prefixId+"img2";
                            document.getElementById(imgId).src=imagesDir+"/fun_lr.jpg";
                        }
                }



                function change(tabId,n,totalCount){
                    var prefixId="";

                    if(tabId!=null && tabId!="") {
						prefixId=tabId+"_";
					}
                    clearall(tabId,totalCount);
                    var imgleft=document.getElementById(prefixId+imgPrefix+n);
                    var imgright=document.getElementById(prefixId+imgPrefix+(n+1));
                    var imgback=document.getElementById(prefixId+tdPrefix+n);
                    var vLayer=document.getElementById(prefixId+layerPrefix+n);

                    imgleft.src=imagesDir+"/now_left.jpg";
                    imgright.src=imagesDir+"/now_right.jpg";
                    imgback.background=imagesDir+"/now_back.jpg";
                    vLayer.style.visibility="visible";

                    var tdName=prefixId+tdPrefix+n;
                    document.getElementById(tdName).style.borderTopWidth="1px";
                    document.getElementById(tdName).style.borderTopColor="#FFD700";

                    try {
                        document.getElementById(tabId).tabindex=n;
						var userFunction=document.getElementById(tabId).onchange;

                        if(userFunction!=null && userFunction!="") {
                            window.eval(userFunction);
                        }

                    } catch(e) {
                    }
                }

                function getTabIndex(tabId) {
                    //alert("in getTabIndex()");
                    var tabIndex=-1;
                    try {
                        tabIndex=document.getElementById(tabId).tabindex;
                    } catch(e) {
                        tabIndex=-1;
                        try {
                           tabIndex=tabId.tabindex;
                        } catch(e) {
                           tabIndex=-1;
                        }
                    }
                    return tabIndex;
                }

				function invokeMethod(methodName,param) {
					invokeParam=param;
					var allIFrame=document.getElementsByTagName("IFRAME");
					var i=0;
					for(i=0;i<allIFrame.length;i++) {
						try {
							var ifrmId=allIFrame[i].id;
							var ifrm=document.getElementById(allIFrame[i].id);
							var invokeStr=ifrmId+"."+methodName+"(invokeParam)";
							window.eval(invokeStr);
						} catch(e) {
						}
					}
				}

				/*
				     This function will only call one page function
				     Note: 1)mapOftabPanel is HashMap

				*/
				function invokeSingleMethod(methodName,param,mapOftabPanel) {
					try {
						invokeParam=param;

						var idOfTabPanel=mapOftabPanel.get("tabPanelId");
						var tabIndex=getTabIndex(idOfTabPanel);
						var idOfTab=mapOftabPanel.get(tabIndex+"");
						var invokeStr=idOfTab+"."+methodName+"(invokeParam)";
						window.eval(invokeStr);
					} catch(e) {
					    alert(e);
					}
				}


	function addTabButtonByIndex(tabpanelId,tabIndexNumber,buttonName,buttonCaption,buttonOnclickEvent,buttonStyle) {
		if(systabButtonMap==null) {
			systabButtonMap=new HashMap();
		}
        var tabId="";
        try {
			tabId=tabpanelId.id+"."+tabIndexNumber;
		} catch(e) {
			tabId=tabpanelId+"."+tabIndexNumber;
		}
        var buttonCount=0;
        var hasAdd=false;
        try {
			if(systabButtonMap.get(tabId+".buttonCount")==null || systabButtonMap.get(tabId+".buttonCount")=="") {
				buttonCount=0;
			} else {
				buttonCount=systabButtonMap.get(tabId+".buttonCount");
			}
		} catch(e) {

		}

		try {
			if(systabButtonMap.get(tabId+".button"+buttonCount+".name")!=null && systabButtonMap.get(tabId+".button"+buttonCount+".name")!="") {
				hasAdd=true;
			}
		} catch(e) {

		}

		systabButtonMap.put(tabId+".button"+buttonCount+".name",buttonName);
		systabButtonMap.put(tabId+".button"+buttonCount+".caption",buttonCaption);
		systabButtonMap.put(tabId+".button"+buttonCount+".onclick",buttonOnclickEvent);
		if(buttonStyle!=null && buttonStyle!="") {
			systabButtonMap.put(tabId+".button"+buttonCount+".style",buttonStyle);
		}

		if(hasAdd==false) {
			buttonCount++;
			systabButtonMap.put(tabId+".buttonCount",buttonCount);
		}
    }

    function clearTabButtonByIndex(tabpanelId,tabIndexNumber) {
		var tabId="";
        try {
			tabId=tabpanelId.id+"."+tabIndexNumber;
		} catch(e) {
			tabId=tabpanelId+"."+tabIndexNumber;
		}
		var buttonCount=0;
		try {
			buttonCount=systabButtonMap.get(tabId+".buttonCount");
			var i=0;
			for(i=0;i<buttonCount;i++) {
				systabButtonMap.remove(tabId+".button"+buttonCount+".name");
			}
			systabButtonMap.remove(tabId+".buttonCount");
		} catch(e) {

		}
    }

    function renderTabButton(tabpanelId,btnPanel) {
		var tabIndexNumber=getTabIndex(tabpanelId);
		var tabId="";
        try {
			tabId=tabpanelId.id+"."+tabIndexNumber;
		} catch(e) {
			tabId=tabpanelId+"."+tabIndexNumber;
		}
		var buttonCount=systabButtonMap.get(tabId+".buttonCount");
		var htmlStr="";
		if(buttonCount>0) {
			var i=0;
			for(i=0;i<buttonCount;i++) {
				var buttonName=systabButtonMap.get(tabId+".button"+i+".name");
				var buttonCaption=systabButtonMap.get(tabId+".button"+i+".caption");
				var buttonOnclick=systabButtonMap.get(tabId+".button"+i+".onclick");
				var buttonStyle=systabButtonMap.get(tabId+".button"+i+".style");
                htmlStr=htmlStr+"<input type='button' name='"+buttonName+"' id='"+buttonName+"' value='"+buttonCaption+"' onclick='"+buttonOnclick+"' class='button'";
				if(buttonStyle!=null && buttonStyle!="") {
					htmlStr=htmlStr+" style='"+buttonStyle+"'>";
				} else {
					htmlStr=htmlStr+" style='width:50px' >";
				}
				htmlStr=htmlStr+"&nbsp;";
			}
		}
		btnPanel.innerHTML=htmlStr;
    }

    var oldColor=null;
    var oldObj=null;
    function changeRowColor(obj) {
//        alert( "changeRowColor" );
        if(oldObj!=null) {
            oldObj.style.backgroundColor=oldColor;
        }
        oldColor=obj.style.backgroundColor;
        oldObj=obj;
        obj.style.backgroundColor="#ccccff";
    }


