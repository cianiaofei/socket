package com.FTT;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class EndClient {
	ServerSocket server;
	public EndClient(String addr) throws UnknownHostException, IOException{
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
	public static void main(String[] args) throws UnknownHostException, IOException{
		EndClient endClient = new EndClient("");
	}
}
