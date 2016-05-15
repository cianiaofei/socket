package com.shiyan;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Demo {
	Socket socket;
	public Demo() throws UnknownHostException, IOException{
		ServerSocket server = new ServerSocket(8888);
		socket = new Socket("127.0.0.1",8888);
		server.close();
	}
	public static void main(String[] args) throws UnknownHostException, IOException{
		while(true){
			Demo demo = new Demo();
		}
	}
}
