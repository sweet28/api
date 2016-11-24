package com.arttraining.commons.util.pay.WXPay;



import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class XMLUtil {
	/**
	 * 解析xml
	 * @param strxml
	 * @return
	 */
	public static Map<String, String> doXMLParse(String strxml) {
		if (null == strxml || "".equals(strxml)) {
			return null;
		}
		Map<String, String> map = new HashMap<String, String>();
		try {
			InputStream inputStream = String2Inputstream(strxml);
			// 读取输入流
			SAXReader reader = new SAXReader();
			Document document = reader.read(inputStream);
			// 得到xml根元素
			Element root = document.getRootElement();
			// 得到根元素的所有子节点
			List<Element> elementList = root.elements();
			// 遍历所有子节点
			for (Element e : elementList)
				map.put(e.getName(), e.getText());
			// 关闭流
			inputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	public static InputStream String2Inputstream(String str) {
		return new ByteArrayInputStream(str.getBytes());
	}
}