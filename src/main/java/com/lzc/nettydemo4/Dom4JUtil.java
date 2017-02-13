package com.lzc.nettydemo4;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class Dom4JUtil {
	
	/**
	 * 取得报文头元素内容
	 * @param xml
	 * @param key
	 * @return
	 */
	public static String header(String xml, String key){
		// 1、将传入的字符串流化
		InputStream in = null;
		try {
			in = new ByteArrayInputStream(xml.toLowerCase().getBytes("UTF-8"));
			SAXReader reader = new SAXReader();
			// 2、将流转成document对象
			Document document = reader.read(in);
			Element e = document.getRootElement().element("header").element(key);
			if(e!=null){
				return e.getTextTrim();				
			}

		} catch (UnsupportedEncodingException e) {
			System.out.println("流操作异常");
			e.printStackTrace();
		} catch (DocumentException e) {
			System.out.println("Document对象操作异常");
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				System.out.println("流关闭异常");
				e.printStackTrace();
			}
		}
		return "";	
	}
	
	

	/**
	 * 取得报文头部分内容
	 * @param xml
	 * @return
	 */
	public static Map<String, String> header(String xml) {
		Map<String, String> headMap = new HashMap<String, String>();
		// 1、将传入的字符串流化
		InputStream in = null;
		try {
			in = new ByteArrayInputStream(xml.toLowerCase()
					.getBytes("UTF-8"));
			SAXReader reader = new SAXReader();
			// 2、将流转成document对象
			Document document = reader.read(in);
			Element rootElement = document.getRootElement(); // 拿到根节点
			//遍历header部分
			for (Iterator hd = rootElement.element("header").elementIterator(); hd
					.hasNext();) {
				Element e = (Element) hd.next();
				headMap.put(e.getName(), e.getTextTrim());
			}

		} catch (UnsupportedEncodingException e) {
			System.out.println("流操作异常");
			e.printStackTrace();
		} catch (DocumentException e) {
			System.out.println("Document对象操作异常");
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				System.out.println("流关闭异常");
				e.printStackTrace();
			}
		}
		return headMap;
	}
	
	/**
	 * 取得报文请求内容
	 * @param xml
	 * @return
	 */
	public static Map<String, String> request(String xml) {
		Map<String, String> reqMap = new HashMap<String, String>();
		// 1、将传入的字符串流化
		InputStream in = null;
		try {
			in = new ByteArrayInputStream(xml.toLowerCase().getBytes("UTF-8"));
			SAXReader reader = new SAXReader();
			// 2、将流转成document对象
			Document document = reader.read(in);
			Element rootElement = document.getRootElement(); // 拿到根节点
			//遍历request部分
			Element reqElement = rootElement.element("body").element("request");
			if(reqElement!=null && !reqElement.elements().isEmpty()){					
				for (Iterator it = reqElement.elementIterator(); it.hasNext();) {
					Element e = (Element) it.next();
					reqMap.put(e.getName(), e.getTextTrim());
				}
			}

		} catch (UnsupportedEncodingException e) {
			System.out.println("流操作异常");
			e.printStackTrace();
		} catch (DocumentException e) {
			System.out.println("Document对象操作异常");
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				System.out.println("流关闭异常");
				e.printStackTrace();
			}
		}
		return reqMap;
	}
	
	/**
	 * 取得报文请求内容
	 * @param xml
	 * @param key
	 * @return
	 */
	public static String request(String xml, String key) {
		// 1、将传入的字符串流化
		InputStream in = null;
		try {
			in = new ByteArrayInputStream(xml.toLowerCase().getBytes("UTF-8"));
			SAXReader reader = new SAXReader();
			// 2、将流转成document对象
			Document document = reader.read(in);
			Element rootElement = document.getRootElement(); // 拿到根节点
			Element e = rootElement.element("body").element("request").element(key);
			if(e!=null){
				return e.getTextTrim();
			}

		} catch (UnsupportedEncodingException e) {
			System.out.println("流操作异常");
			e.printStackTrace();
		} catch (DocumentException e) {
			System.out.println("Document对象操作异常");
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				System.out.println("流关闭异常");
				e.printStackTrace();
			}
		}
		return "";
	}
	
	/**
	 * 取得报文响应内容
	 * @param xml
	 * @return
	 */
	public static Map<String, String> response(String xml) {
		Map<String, String> respMap = new HashMap<String, String>();
		// 1、将传入的字符串流化
		InputStream in = null;
		try {
			in = new ByteArrayInputStream(xml.toLowerCase().getBytes("UTF-8"));
			SAXReader reader = new SAXReader();
			// 2、将流转成document对象
			Document document = reader.read(in);
			Element rootElement = document.getRootElement(); // 拿到根节点
			//遍历response部分
			Element respElement = rootElement.element("body").element("response");
			if(respElement!=null && !respElement.elements().isEmpty()){				
				for (Iterator it = respElement.elementIterator(); it
						.hasNext();) {
					Element e = (Element) it.next();
					respMap.put(e.getName(), e.getTextTrim());
				}
			}

		} catch (UnsupportedEncodingException e) {
			System.out.println("流操作异常");
			e.printStackTrace();
		} catch (DocumentException e) {
			System.out.println("Document对象操作异常");
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				System.out.println("流关闭异常");
				e.printStackTrace();
			}
		}
		return respMap;
	}
	
	/**
	 * 取得报文响应内容
	 * @param xml
	 * @return
	 */
	public static String response(String xml, String key) {
		// 1、将传入的字符串流化
		InputStream in = null;
		try {
			in = new ByteArrayInputStream(xml.toLowerCase().getBytes("UTF-8"));
			SAXReader reader = new SAXReader();
			// 2、将流转成document对象
			Document document = reader.read(in);
			Element rootElement = document.getRootElement(); // 拿到根节点
			//遍历response部分
			Element e = rootElement.element("body").element("response").element(key);
			if(e!=null ){				
				return e.getTextTrim();
			}

		} catch (UnsupportedEncodingException e) {
			System.out.println("流操作异常");
			e.printStackTrace();
		} catch (DocumentException e) {
			System.out.println("Document对象操作异常");
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				System.out.println("流关闭异常");
				e.printStackTrace();
			}
		}
		return "";
	}
	
	
	public static void main(String[] args) throws Exception {
		String xml = FileUtils.readFileToString(new File("D:\\开户请求报文.xml"));
		Dom4JUtil pfd = new Dom4JUtil();
		Map<String, String> header =  pfd.header(xml);
		Map<String, String> request =  pfd.request(xml);
		
		System.out.println("request content:");
		Iterator it=request.keySet().iterator(); 
		while(it.hasNext()){ 
		    String key=it.next().toString(); 
		    String value=request.get(key); 
		    System.out.println(key+":"+value); 
		} 
		
		Map<String, String> response = pfd.response(xml);
		System.out.println("");
		
		System.out.println(pfd.request(xml, "address"));
		System.out.println("");
		
		
		System.out.println("response content:");
		Iterator it2=response.keySet().iterator(); 
		while(it2.hasNext()){ 
		    String key=it.next().toString(); 
		    String value=request.get(key); 
		    System.out.println(key+":"+value); 
		} 
		
	}
}
