package com.file.transport;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class TCPClientSocket {

	public static final String fileDir = "Z:/研一课程/分布式数据处理/";
	static String fileName = "L3-design.pdf";
	public static void main(String[] args) {
		String filePath = fileDir + fileName;
		System.out.println("send filePath = " + filePath);
		Socket socket = null;
		try {
			socket = new Socket("127.0.0.1", 8888);
			if(socket != null){
				System.out.println("Connection succcessed !");
				sendFile(socket, filePath);
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void sendFile(Socket socket, String filePath){
		DataOutputStream dos = null;
		BufferedInputStream bis = null; //源头 
		byte[] bytes = new byte[1024];
		
		try {
			try {
				/*
				 * new a File with the filePath
				 * new a FileInputStream with the File to read the file by byte
				 * new a BufferedInputStream with the FileInputStream to use buffered stream
				 */
				bis = new BufferedInputStream(new FileInputStream(new File(filePath)));
				dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));//出口
				//首先发送文件名     客户端发送使用writeUTF方法，服务器端应该使用readUTF方法
				dos.writeUTF(fileName);
				//之后再发送文件的内容
				int length = 0;
				while((length = bis.read(bytes, 0, bytes.length)) > 0){
					dos.write(bytes, 0, length);
					dos.flush();
				}
				
				
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				//使用完毕后，应关闭输入、输出流和socket
				if(bis != null) bis.close();
				if(dos != null) dos.close();
				if(socket != null) socket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
	