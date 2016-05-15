package com.file.transport;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.ServerSocket;

public class TCPServerSocket {

	public static final int PORT = 8888;				//port
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ServerSocket mServerSocket = null;
		Socket socket = null;
		try {
			//������������
			mServerSocket = new ServerSocket(PORT);//�����˿�
			System.out.println("Server is already created now ! Waiting for client to connect ...");

			//�ͻ������ӷ�������	
			socket = mServerSocket.accept();
			System.out.println("One client connected to this server successfully !");

			//���ӳɹ�����ʼ�����ļ�
			receiveFile(socket);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void receiveFile(Socket socket) {

		FileOutputStream fos = null;
		DataInputStream dis = null;
		
		//buffer�𻺳����ã�һ�ζ�ȡ��д�����ֽڵ�����
		byte[] buffer = new byte[1024];

		try {
			try {
				//����ʹ��DataInputStream�����Ե�������readUTF��������ȡҪ������ļ������ͻ���ʹ��writeUTF�������ļ����ȴ������
				dis = new DataInputStream(socket.getInputStream());
				
				//���ȶ�ȡ�ļ���
				String oldFileName = dis.readUTF();
				System.out.println(oldFileName);
				//�ļ�·��������ͻ�����ͬ��·�����ļ�����������
				String filePath = "D:/1.pdf";
				System.out.println("receive filePath = " + filePath);
				
				//����FileOutputStream�������ļ������
				fos = new FileOutputStream(new File(filePath));
				int length = 0;
				
				/*
				 * length = dis.read(buffer, 0, buffer.length) һ�ζ���1024���ֽڵ����ݵ�buffer�У�length����ʵ�ʶ�����ֽ���
				 * fos.write(buffer, 0, length) һ�δ�buffer�е�length���ֽڵ�����д�뵽�ļ��� 
				 * ��ע���ļ���С����1024Bʱ��lengthһ��Ϊ1024�����һ�ζ�ȡ����С��1024��
				 */
				while((length = dis.read(buffer, 0, buffer.length)) > 0){
					fos.write(buffer, 0, length);
					fos.flush();
				}
				
			} finally {
				//ʹ����Ϻ�Ӧ�ر����롢�������socket
				if(dis != null) dis.close();
				if(fos != null)	fos.close();
				if(socket != null) socket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * example :
	 * oldName = "Java_TCPIP_Socket.pdf"
	 * newName = "Java_TCPIP_Socket-2.pdf"
	 */
	private static String genereateFileName(String oldName){
		String newName = null;
		newName = oldName.substring(0, oldName.lastIndexOf(".")) + "-2" + oldName.substring(oldName.lastIndexOf("."));
		return newName;
	}

}