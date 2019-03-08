package com.util;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wxutil.HttpRequest;



public class BaiduUtil {

	private final static String zbzh = "http://api.map.baidu.com/geoconv/v1/?coords=";    //百度坐标转化api
    private final static String ak = "oRYkfitFSo3DxFdy8aI9y2PiQ6Km2DjC";                  //app key
    private final static String distance = "http://api.map.baidu.com/routematrix/v2/walking"; // 百度测距离api
    private final static String locationurl="http://api.map.baidu.com/geocoder/v2/?ak=qFbGsVhASUXTGeLaz4H243dpqjlDyg6W";
    
  /*  *//**
   	 * 百度定位
   	 * latitude x,longitude y  经纬度
   	 *//*
   	@RequestMapping("wx/getlocation")
   	public static JsonObject getlocation(String latitude,String longitude)
   	{
   		String baidu=wgs84tobaidu(latitude+","+longitude);
   		JsonObject rt = null;
   		String url=locationurl+"&location=" + baidu.split(",")[1] + "," + baidu.split(",")[0] + "&output=json&pois=0";
   		String rs=HttpUtil.doGet(url);
   		rt=gson.fromJson(rs, JsonObject.class);
   		return rt;
   	}
   	
   	"
    *//**
     * wgs84坐标转成百度坐标
     * @param wgs x,y
     * @return
     *//*
    public static String wgs84tobaidu(String wgs) {
	        String zb = zbzh +  wgs.split(",")[1] +","+wgs.split(",")[0] +"&from=1&to=5&ak=" + ak;
	        Gson gson = new Gson();
            String r =HttpUtil.doGet(zb);
            JsonObject json = gson.fromJson(r, JsonObject.class);
            JsonArray arr = (JsonArray) json.get("result");
            JsonObject nzb = (JsonObject) arr.get(0);
            String nlatitude = nzb.get("x").toString();
            String nlongitude = nzb.get("y").toString();
            return nlatitude + "," + nlongitude;
    }
    
    
    
    
    /**
     * 计算两个点距离
     * @param origins   x,y  起点
     * @param destinations x1,y1|x2,y2.......
     * @return
     */
    public static int DistanceAll(String origins, String destinations) {
    		String url = distance ;
        	String r =HttpRequest.sendGet(url,"&output=json&origins=" + origins + "&destinations=" + destinations + "&ak=" + ak);
            JSONObject json = JSON.parseObject(r, JSONObject.class);
            if(json.getInteger("status")!=0) {
            	throw new RuntimeException(json.getString("message"));
            }else {
            	return json.getJSONArray("result").getJSONObject(0).getJSONObject("distance").getInteger("value");
            }
    }
    
}
