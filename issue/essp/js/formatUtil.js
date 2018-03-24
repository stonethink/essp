	//�ж��ǲ����������ַ���
	function isDateStr_temp( data ){
		var patern = /^\d{4}(\-)\d{1,2}(\-)\d{1,2}$/;
		if(!data.match(patern)){
			return false;
		}
		return true;
	}
	//�ж��ǲ����������ַ���
	function isDateString_temp( data ){
		var patern = /^\d{4}(\-|\\|\/)\d{1,2}(\-|\\|\/)\d{1,2}$/;
		if(!data.match(patern)){
			return false;
		}
		return true;
	}
	//�ж��ǲ����������ַ���(xxxx-xx-xx)
    function isDateStr( data ){
		var patern = /^\d{4}(\-)\d{2}(\-)\d{2}$/;
		if(!data.match(patern)){
			return false;
		}
		return true;
	}
	//�ж��ǲ����������ַ���(xxxx-xx-xx)
    function isDateString( data ){
		var patern = /^\d{4}(\-)\d{2}(\-)\d{2}$/;
		if(!data.match(patern)){
			return false;
		}
		return true;
    }
    //�ж��ǲ��������λ
    function isYear(data){
	var patern = /^\d{4}$/;
	if(!data.match(patern)){
			return false;
    }
    	return true;
	}
    //�ж��ǲ��ǷǸ�����(λ������)
    function isNumber(data){
		var patern = /^\d*(\.\d+)?$/;
		if(!data.match(patern)){
			return false;
   	 }
    	return true;
	}

	function checkDate(s){	//�O���ǂ��ؼ������f��v
		if ((s.value!='')&&(isDateString(s.value)==false)){	//�ҳ��ؼ���ԭ�������ڡ������ǲ��Ϸ���
			return true;
		}
		else{
			return false;
		}
	}


        /**
     * �ж��ǲ��ǽ���ַ���
     * ������data��Ҫ�жϵ��ַ�����
     ��       digits��ʾҪ���С����λ�������û�����ʾ��������
     ��             ����У���ʾҪ����������digitsλС����
     */
    function isMoneyString( data , digits){
        //����ǰ�������붺����ϣ��м���һ���㣬�������������붺�ŵ����
        var patern = /^(\d|\,)+\.((\d|\,)+)?$/;
        //var patern = new RegExp(strPattern);
        if(!data.match(patern)){
            return false;
        }
        if(digits != null){
            var arrDec = data.split(".");
            if(arrDec.length < 2){
                return false;
            }
            if(arrDec[arrDec.length - 1].length < digits){
                return false;
            }
        }
        return true;
    }
    
            /**
     * �ж��ǲ��ǽ���ַ���
     * ������data��Ҫ�жϵ��ַ�����
     ��       digits��ʾҪ���С����λ�������û�����ʾ��������
     ��             ����У���ʾҪ����������digitsλС����
     */
        function isMoneyStringSinge( data , digits){  
        var tmpStr = data;
        if ( tmpStr.substring(0,1) == "-" || tmpStr.substring(0,1)=="+" ){
        	tmpStr = data.substring(1, data.length);  
        	
        }       
       return isMoneyString(tmpStr,digits);
    }

//function isMoney( data ){
//        //����ǰ�������붺����ϣ��м���һ���㣬�������������붺�ŵ����(��λһ��,��С������������),����û��С������
//        var patern = /^\d{1,2,3}((\,){1}\d{3})*((\.){1}(\d{2}))?$/;
//        //var patern = new RegExp(strPattern);
//        if(!data.match(patern)){
//            return false;
//        }
//        return true;
//    }

//1. ʱ�䣬���� (13:04:06)
      function isTime(str)
      {
        var a = str.match(/^(\d{1,2})(:)?(\d{1,2})\2(\d{1,2})$/);
        var re;

        if (a == null) { return 0;}
        if (a[1]>24 || a[3]>60 || a[4]>60)
        {

          return 0;

        }

        re = toDD(a[1]) + ":" + toDD(a[3]) + ":" + toDD(a[4]);

        return re;
      }

//2. ���ڣ����� (2003-12-05)
      function isDate(str)
      {
         var r = str.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/);
         var re;
         if(r==null)return 0;
         var d= new Date(r[1], r[3]-1, r[4]);
         if  (d.getFullYear()==r[1]&&(d.getMonth()+1)==r[3]&&d.getDate()==r[4]) {

         	re = toDD(r[1]) + "-" + toDD(r[3]) + "-" + toDD(r[4]);
         	return re;

         } else {

         	return 0;

         }

      }


//3. ��ʱ�䣬���� (2003-12-05 13:04:06)
      function isLongDateTime(str)
      {
        var reg = /^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2}) (\d{1,2}):(\d{1,2}):(\d{1,2})$/;
        var r = str.match(reg);
        var re;

        if(r==null)return 0
        var d= new Date(r[1], r[3]-1,r[4],r[5],r[6],r[7]);
        if (d.getFullYear()==r[1]&&(d.getMonth()+1)==r[3]&&d.getDate()==r[4]&&d.getHours()==r[5]&&d.getMinutes()==r[6]&&d.getSeconds()==r[7]) {

        	re = toDD(r[1]) + "-" + toDD(r[3]) + "-" + toDD(r[4]);
        	re = re + " " + toDD(r[5]) + ":" + toDD(r[6]) + ":" + toDD(r[7])
         	return re;

         } else {

         	return 0;

         }
      }

//4. ��дʱ�䣬���� (13:04)
      function isShortTime(str)
      {
        var a = str.match(/^(\d{1,2})(:)?(\d{1,2})$/);
        var re;

        if (a == null) { return 0;}
        if (a[1]>24 || a[3]>60 )
        {

          return 0
        }

        re = toDD(a[1]) + ":" + toDD(a[3])
        return re;
      }

//5. ��д��ʱ�䣬���� (2003-12-05 13:04)
      function isShortDateTime(str)
      {
        var reg = /^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2}) (\d{1,2}):(\d{1,2})$/;
        var r = str.match(reg);
        var re;

        if(r==null)return 0;
        var d= new Date(r[1], r[3]-1,r[4],r[5],r[6],'00');
        if (d.getFullYear()==r[1]&&(d.getMonth()+1)==r[3]&&d.getDate()==r[4]&&d.getHours()==r[5]&&d.getMinutes()==r[6]) {

        	re = toDD(r[1]) + "-" + toDD(r[3]) + "-" + toDD(r[4]);
        	re = re + " " + toDD(r[5]) + ":" + toDD(r[6]);
         	return re;

         } else {

         	return 0;

         }


      }


     function toDD(str){

      	if ( str.length == 1){

      		str = "0" + str;

      	}

	return str;


      }

      function DefaultDate_NotDisplay(str){
      	if(str=="1970-01-01"){
      		str="";
      	}
      	return str;
	}
