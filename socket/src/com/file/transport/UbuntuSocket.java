package com.file.transport;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class UbuntuSocket {
	private ServerSocket serverSocket;
	public UbuntuSocket() throws IOException{
		serverSocket = new ServerSocket(8888);
		this.receiverFile();
	}
	public void receiverFile() throws IOException{
		Socket socket = serverSocket.accept();//
		BufferedInputStream bis = new BufferedInputStream(socket.getInputStream());
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File("d:/1.pdf")));
		byte[] bytes = new byte[1024];
		int len = 0;
		while((len=bis.read(bytes,0,bytes.length))>0){
			bos.write(bytes,0,len);
			bos.flush();
		}
		bos.close();
		bis.close();
		socket.close();
	}
	public static void main(String[] args) throws IOException{
		UbuntuSocket us = new UbuntuSocket();
	}
}
