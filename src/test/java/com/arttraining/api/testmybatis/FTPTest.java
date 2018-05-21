package com.arttraining.api.testmybatis;

import java.io.File;
import java.io.FileInputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.junit.Test;

import com.arttraining.commons.util.FtpUtil;

//依赖的jar包   ftp可以需要依赖commons-net-3.3.jar包
public class FTPTest {
	
	//方式一
	@Test
	public void testFtpClient() throws Exception{
		//创建一个FtpClient对象
		FTPClient ftpClient = new FTPClient();
		//创建ftp连接,端口号默认是21
		ftpClient.connect("118.178.136.110", 21);
		//登入ftp服务器，使用用户名密码
		ftpClient.login("cpaftp", "Stone1212");
		//上传文件
		//读取本地文件
		FileInputStream inputStream = new FileInputStream(new File("C:\\Users\\Administrator\\Desktop\\hello.png"));
		//设置上传的路径
		ftpClient.changeWorkingDirectory("/default/img");
		//修改上传文件的格式
		ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
		//第一个参数：服务器文档名
		//第二个参数：上传文档的inputStream
		ftpClient.storeFile("hello.jpg",inputStream);
		//关闭连接
		ftpClient.logout();
		
	}
	
	//方式二
	@Test
	public void testFtpUtils() throws Exception{
		FileInputStream inputStream = new FileInputStream(new File("C:\\Users\\Administrator\\Desktop\\hello.png"));
		FtpUtil.uploadFile("118.178.136.110", 21, "cpaftp", "Stone1212", "/default", "/img", "word.jpg", inputStream);
	}
	
	
	
	
	
	
	
	
	
	
}
