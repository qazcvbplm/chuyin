package com.wxutil;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xml.sax.SAXException;

public class XMLUtil {

    public static Map parseXML2map(String result){
        Map<String, String> map = new HashMap<String, String>();
        InputStream in = new ByteArrayInputStream(result.getBytes());
        // 读取输入
        SAXReader reader = new SAXReader();
        reader.setEncoding("UTF-8");
        org.dom4j.Document document = null;
        try {
            document = reader.read(in);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        // 得到xml根元素
        if (document == null){
            return map;
        }
        Element root = document.getRootElement();
        // 得到根元素的子节点
        List<Element> elementList = root.elements();
        for (Element element : elementList) {
            map.put(element.getName(), element.getText());
        }
        
        return map;
    }
    
    /**
   	 * 解析xml格式数据
   	 * 
   	 * @param req
   	 * @return
   	 * @throws IOException
        * @throws SAXException 
   	 */
   	public static Map<String, String> parseXML(HttpServletRequest req) throws IOException, SAXException {
   		// 解析结果存储在HashMap
   		Map<String, String> map = new HashMap<String, String>();
   		InputStream inputStream = req.getInputStream();
   		// 读取输入流
   		SAXReader reader = new SAXReader();
   		 String FEATURE = "http://apache.org/xml/features/disallow-doctype-decl";
   		 reader.setFeature(FEATURE, true);

   	        FEATURE = "http://xml.org/sax/features/external-general-entities";
   	        reader.setFeature(FEATURE, false);

   	        FEATURE = "http://xml.org/sax/features/external-parameter-entities";
   	        reader.setFeature(FEATURE, false);

   	        FEATURE = "http://apache.org/xml/features/nonvalidating/load-external-dtd";
   	        reader.setFeature(FEATURE, false);
   		Document document = null;
   		try {
   			document = reader.read(inputStream);
   		} catch (DocumentException e1) {
   			e1.printStackTrace();
   		}
   		// 得到xml根元素
   		Element root = document.getRootElement();
   		// 得到根元素的所有子节点
   		List<Element> elementList = root.elements();
   		// 遍历所有子节点
   		for (Element e : elementList) {
   			map.put(e.getName(), e.getText());
   		}
   		// 释放资源
   		inputStream.close();
   		inputStream = null;
   		return map;
   	}
}
