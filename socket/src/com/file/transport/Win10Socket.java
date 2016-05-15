package com.file.transport;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Win10Socket {
	private static final int FILE_TRANS_COMPLETE = 1;
	Socket socket;
	public Win10Socket(String ip) throws UnknownHostException, IOException{
		socket = new Socket(ip,9999);
	}
	public void sendFile(String filePath) throws IOException{
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(new File(filePath)));
		BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());
		DataInputStream recResponse = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
		byte[] bytes = new byte[1024];
		int len = 0;
		while((len=bis.read(bytes,0,bytes.length))>0){
			bos.write(bytes,0,len);
		}
		bis.close();
		bos.close();
//		while(true){//这个while循环为什么会造成问题呢   
//			if(recResponse.readInt()==FILE_TRANS_COMPLETE){  
//				break;
//			}
//		}
		recResponse.close();
		socket.close();
	}
	public static void main(String[] args) throws UnknownHostException, IOException{
		Win10Socket ws = new Win10Socket("127.0.0.1");
		ws.sendFile("C:/Users/zhangsan/Desktop/chp%3A10.1007%2F978-94-017-9927-0_8.pdf");
	}
}
