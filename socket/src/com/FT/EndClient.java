package com.FT;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;
import java.net.UnknownHostException;

public class EndClient {
	Socket socket;
	public EndClient(String addr) throws UnknownHostException, IOException{
		this.socket = new Socket(addr,8888);
	}
	public void send(String content) throws IOException{
		Writer writer = new OutputStreamWriter(socket.getOutputStream());
		writer.write(content);
		writer.close();
		socket.close();
	}
//	public static void main(String[] args) throws UnknownHostException, IOException{
//		EndClient endClient = new EndClient("127.0.0.1");
//	}
}
