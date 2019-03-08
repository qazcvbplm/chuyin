/*     */ package com.wx.towallet;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.StringReader;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ import java.util.TreeMap;
/*     */ import org.jdom2.Document;
/*     */ import org.jdom2.Element;
/*     */ import org.jdom2.JDOMException;
/*     */ import org.jdom2.input.SAXBuilder;
/*     */ import org.xml.sax.InputSource;
/*     */ 
/*     */ public class XMLParse
/*     */ {
/*     */   public static Map doXMLParse(String strxml)
/*     */   {
/*  24 */     Map<String, Object> map = new HashMap();
/*  25 */     InputStream in = null;
/*     */     try
/*     */     {
/*  27 */       strxml = strxml.replaceFirst("encoding=\".*\"", "encoding=\"UTF-8\"");
/*  29 */       if ((strxml == null) || ("".equals(strxml))) {
/*  30 */         return null;
/*     */       }
/*  33 */       in = new ByteArrayInputStream(strxml.getBytes("UTF-8"));
/*  34 */       SAXBuilder builder = new SAXBuilder();
/*  35 */       Document doc = builder.build(in);
/*  36 */       Element root = doc.getRootElement();
/*  37 */       List list = root.getChildren();
/*  38 */       Iterator it = list.iterator();
/*  39 */       while (it.hasNext())
/*     */       {
/*  40 */         Element e = (Element)it.next();
/*  41 */         String k = e.getName();
/*  42 */         String v = "";
/*  43 */         List children = e.getChildren();
/*  44 */         if (children.isEmpty()) {
/*  45 */           v = e.getTextNormalize();
/*     */         } else {
/*  47 */           v = getChildrenText(children);
/*     */         }
/*  49 */         map.put(k, v);
/*     */       }
/*     */     }
/*     */     catch (JDOMException je)
/*     */     {
/*  52 */       je.printStackTrace();
/*     */       try
/*     */       {
/*  58 */         in.close();
/*     */       }
/*     */       catch (IOException e)
/*     */       {
/*  60 */         e.printStackTrace();
/*     */       }
/*     */     }
/*     */     catch (IOException e1)
/*     */     {
/*  54 */       e1.printStackTrace();
/*     */       try
/*     */       {
/*  58 */         in.close();
/*     */       }
/*     */       catch (IOException e)
/*     */       {
/*  60 */         e.printStackTrace();
/*     */       }
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/*  58 */         in.close();
/*     */       }
/*     */       catch (IOException e)
/*     */       {
/*  60 */         e.printStackTrace();
/*     */       }
/*     */     }
/*  63 */     return map;
/*     */   }
/*     */   
/*     */   public static String getChildrenText(List children)
/*     */   {
/*  68 */     StringBuffer sb = new StringBuffer();
/*  69 */     if (!children.isEmpty())
/*     */     {
/*  70 */       Iterator it = children.iterator();
/*  71 */       while (it.hasNext())
/*     */       {
/*  72 */         Element e = (Element)it.next();
/*  73 */         String name = e.getName();
/*  74 */         String value = e.getTextNormalize();
/*  75 */         List list = e.getChildren();
/*  76 */         sb.append("<" + name + ">");
/*  77 */         if (!list.isEmpty()) {
/*  78 */           sb.append(getChildrenText(list));
/*     */         }
/*  80 */         sb.append(value);
/*  81 */         sb.append("</" + name + ">");
/*     */       }
/*     */     }
/*  84 */     return sb.toString();
/*     */   }
/*     */   
/*     */   public static String getRequestXml(TreeMap<String, String> parameters)
/*     */     throws Exception
/*     */   {
/*  89 */     StringBuffer sb = new StringBuffer();
/*  90 */     sb.append("<xml>");
/*  91 */     Set es = parameters.entrySet();
/*  92 */     Iterator it = es.iterator();
/*  93 */     while (it.hasNext())
/*     */     {
/*  94 */       Map.Entry entry = (Map.Entry)it.next();
/*  95 */       String k = (String)entry.getKey();
/*  96 */       String v = (String)entry.getValue();
/*  97 */       if (("attach".equalsIgnoreCase(k)) || ("body".equalsIgnoreCase(k)) || ("sign".equalsIgnoreCase(k))) {
/*  98 */         sb.append("<" + k + ">" + "<![CDATA[" + v + "]]></" + k + ">");
/*     */       } else {
/* 100 */         sb.append("<" + k + ">" + v + "</" + k + ">");
/*     */       }
/*     */     }
/* 103 */     sb.append("</xml>");
/*     */     
/* 105 */     return sb.toString();
/*     */   }
/*     */   
/*     */   private static Map parseXmlToList2(String xml)
/*     */   {
/* 112 */     Map retMap = new HashMap();
/*     */     try
/*     */     {
/* 114 */       StringReader read = new StringReader(xml);
/* 115 */       InputSource source = new InputSource(read);
/* 116 */       SAXBuilder sb = new SAXBuilder();
/* 117 */       Document doc = sb.build(source);
/* 118 */       Element root = doc.getRootElement();
/* 119 */       List<Element> es = root.getChildren();
/* 120 */       if ((es != null) && (es.size() != 0)) {
/* 121 */         for (Element element : es) {
/* 122 */           retMap.put(element.getName(), element.getValue());
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 126 */       e.printStackTrace();
/*     */     }
/* 128 */     return retMap;
/*     */   }
/*     */ }


/* Location:           D:\j2eework2\BOOT-INF\classes\
 * Qualified Name:     com.wx.towallet.XMLParse
 * JD-Core Version:    0.7.0.1
 */