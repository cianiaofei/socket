package com.FT;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class ReceiveSocket {
	ServerSocket server;
	
	public ReceiveSocket(String addr) throws UnknownHostException, IOException{
		server = new ServerSocket(8888);
	}
	
	public String accept() throws IOException{
		Socket socket = server.accept();
		InputStream input = socket.getInputStream();
		BufferedReader buf = new BufferedReader(new InputStreamReader(input));
		String result = buf.readLine();
		buf.close();
		socket.close();
		return result;
	}
	
	public void close(){
		try {
			server.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
