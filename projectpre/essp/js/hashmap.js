
/*------------------------------------------------------------------------------ 
*   added by yery 2005/07/28
*   ģ���HashMap�࣬Ҫ��key��String��value�������κ�����
*   �����б� 
*     1��HashMap() : ���캯��
*     2, put(key, value) : void
*     3, get(key) : Object
*     4, keySet() : Array
*     5, values() : Array
*     6, size() : int
*     7, clear() : void 
*     8, isEmpty() : boolean
*     9, containsKey(key) : boolean
*    10, containsValue(value) : boolean //���value��Object���ͻ����������ͣ�����bug
*    11, putAll(map) : void
*    12, remove(key) : void
*    �÷���
*      var map = new HashMap();    
*      map.put("one","һֻС��");
*      map.put("two","��ֻС��");
*      map.put("three","��ֻС��");
*  
*      print("[ toString() ] : " + map);
*      print("[ get() ] :  " + map.get("one"));
*      print("[ keySet() ] :  " + map.keySet());
*      print("[ values() ] :  " + map.values());
*      print("[ size() ] :  " + map.size());
*      print("[ isEmpty() ] :  " + map.isEmpty());
*      print("[ containsKey() ] :  " + map.containsKey("one"));
*      print("[ containsValue() ] :  " + map.containsValue("��ֻС��"));
*   
*      //putAll
*      var mapTemp = new HashMap();
*      mapTemp.put("four","��ֻС��");
*      mapTemp.put("five","��ֻС��"); 
*      map.putAll(mapTemp);
*      print("[ putAll() ] :  " + map);
*   
*      //remove
*      map.remove("two");
*      map.remove("one");
*      print("[ remove() ] :  " + map);
*   
*      //clear
*      map.clear()
*      print("[ clear() ] :  " + map.size());
*      //��������
*      function print(msg)
*      {
*         document.write(msg + "");
*      }
*  �޸�������
-------------------------------------------------------------------------------*/
/**
 * HashMap���캯��
 */
function HashMap()
{
    this.length = 0;
    this.prefix = "hashmap_prefix_20040716_";
}
/**
 * ��HashMap����Ӽ�ֵ��
 */
HashMap.prototype.put = function (key, value)
{
    this[this.prefix + key] = value;
    this.length ++;
}
/**
 * ��HashMap�л�ȡvalueֵ
 */
HashMap.prototype.get = function(key)
{
    return typeof this[this.prefix + key] == "undefined" 
            ? null : this[this.prefix + key];
}
/**
 * ��HashMap�л�ȡ����key�ļ��ϣ���������ʽ����
 */
HashMap.prototype.keySet = function()
{
    var arrKeySet = new Array();
    var index = 0;
    for(var strKey in this)
    {
        if(strKey.substring(0,this.prefix.length) == this.prefix)
            arrKeySet[index ++] = strKey.substring(this.prefix.length);
    }
    return arrKeySet.length == 0 ? null : arrKeySet;
}
/**
 * ��HashMap�л�ȡvalue�ļ��ϣ���������ʽ����
 */
HashMap.prototype.values = function()
{
    var arrValues = new Array();
    var index = 0;
    for(var strKey in this)
    {
        if(strKey.substring(0,this.prefix.length) == this.prefix)
            arrValues[index ++] = this[strKey];
    }
    return arrValues.length == 0 ? null : arrValues;
}
/**
 * ��ȡHashMap��valueֵ����
 */
HashMap.prototype.size = function()
{
    return this.length;
}
/**
 * ɾ��ָ����ֵ
 */
HashMap.prototype.remove = function(key)
{
    delete this[this.prefix + key];
    this.length --;
}
/**
 * ���HashMap
 */
HashMap.prototype.clear = function()
{
    for(var strKey in this)
    {
        if(strKey.substring(0,this.prefix.length) == this.prefix)
            delete this[strKey];   
    }
    this.length = 0;
}
/**
 * �ж�HashMap�Ƿ�Ϊ��
 */
HashMap.prototype.isEmpty = function()
{
    return this.length == 0;
}
/**
 * �ж�HashMap�Ƿ����ĳ��key
 */
HashMap.prototype.containsKey = function(key)
{
    for(var strKey in this)
    {
       if(strKey == this.prefix + key)
          return true;  
    }
    return false;
}
/**
 * �ж�HashMap�Ƿ����ĳ��value
 */
HashMap.prototype.containsValue = function(value)
{
    for(var strKey in this)
    {
       if(this[strKey] == value)
          return true;  
    }
    return false;
}
/**
 * ��һ��HashMap��ֵ���뵽��һ��HashMap�У�����������HashMap
 */
HashMap.prototype.putAll = function(map)
{
    if(map == null)
        return;
    if(map.constructor != HashMap)
        return;
    var arrKey = map.keySet();
    var arrValue = map.values();
    for(var i in arrKey)
       this.put(arrKey[i],arrValue[i]);
}
//toString
HashMap.prototype.toString = function()
{
    var str = "";
    for(var strKey in this)
    {
        if(strKey.substring(0,this.prefix.length) == this.prefix)
              str += strKey.substring(this.prefix.length) 
                  + " : " + this[strKey] + "\r\n";
    }
    return str;
}