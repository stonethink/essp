
/*------------------------------------------------------------------------------ 
*   added by yery 2005/07/28
*   模拟简单HashMap类，要求key是String，value可以是任何类型
*   方法列表： 
*     1，HashMap() : 构造函数
*     2, put(key, value) : void
*     3, get(key) : Object
*     4, keySet() : Array
*     5, values() : Array
*     6, size() : int
*     7, clear() : void 
*     8, isEmpty() : boolean
*     9, containsKey(key) : boolean
*    10, containsValue(value) : boolean //如果value是Object类型或者数组类型，存在bug
*    11, putAll(map) : void
*    12, remove(key) : void
*    用法：
*      var map = new HashMap();    
*      map.put("one","一只小猪");
*      map.put("two","两只小猪");
*      map.put("three","三只小猪");
*  
*      print("[ toString() ] : " + map);
*      print("[ get() ] :  " + map.get("one"));
*      print("[ keySet() ] :  " + map.keySet());
*      print("[ values() ] :  " + map.values());
*      print("[ size() ] :  " + map.size());
*      print("[ isEmpty() ] :  " + map.isEmpty());
*      print("[ containsKey() ] :  " + map.containsKey("one"));
*      print("[ containsValue() ] :  " + map.containsValue("三只小猪"));
*   
*      //putAll
*      var mapTemp = new HashMap();
*      mapTemp.put("four","四只小猪");
*      mapTemp.put("five","五只小猪"); 
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
*      //辅助方法
*      function print(msg)
*      {
*         document.write(msg + "");
*      }
*  修改履历：
-------------------------------------------------------------------------------*/
/**
 * HashMap构造函数
 */
function HashMap()
{
    this.length = 0;
    this.prefix = "hashmap_prefix_20040716_";
}
/**
 * 向HashMap中添加键值对
 */
HashMap.prototype.put = function (key, value)
{
    this[this.prefix + key] = value;
    this.length ++;
}
/**
 * 从HashMap中获取value值
 */
HashMap.prototype.get = function(key)
{
    return typeof this[this.prefix + key] == "undefined" 
            ? null : this[this.prefix + key];
}
/**
 * 从HashMap中获取所有key的集合，以数组形式返回
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
 * 从HashMap中获取value的集合，以数组形式返回
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
 * 获取HashMap的value值数量
 */
HashMap.prototype.size = function()
{
    return this.length;
}
/**
 * 删除指定的值
 */
HashMap.prototype.remove = function(key)
{
    delete this[this.prefix + key];
    this.length --;
}
/**
 * 清空HashMap
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
 * 判断HashMap是否为空
 */
HashMap.prototype.isEmpty = function()
{
    return this.length == 0;
}
/**
 * 判断HashMap是否存在某个key
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
 * 判断HashMap是否存在某个value
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
 * 把一个HashMap的值加入到另一个HashMap中，参数必须是HashMap
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