package com.arttraining.commons.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Set;

public class FilterUid {

	private static Set<String> uidSet = new HashSet<String>();
	
	/**
	 * @description 立存的需求，查询出指定的UID
	 */
	public static Set<String> getFilterUids(){
		if(uidSet.size()>0){
			return uidSet;
		}
		String[] fileArr = {"filter1.txt"};
		for(String fileName:fileArr){
			String encoding="UTF-8";
	        try {
	        	File file = new File(FilterUid.class.getClassLoader().getResource("").getPath()+fileName);
	        	InputStream in = new FileInputStream(file);
	            InputStreamReader read = new InputStreamReader(in,encoding);//考虑到编码格式
	            
	            BufferedReader bufferedReader = new BufferedReader(read);
	            String fileContent;
	            while((fileContent = bufferedReader.readLine()) != null){
	            	uidSet.add(fileContent);
	            }
	            read.close();
	        } catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return uidSet;
	}
	
	public static void main(String[] args) {
		getFilterUids();
//		System.out.println(FilterUid.class.getClassLoader().getResource("").getPath());
	}
}
