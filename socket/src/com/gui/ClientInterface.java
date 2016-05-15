package com.gui;
/**
 * 当今版本实现 文件拖入传输 但是暂时不支持   动态选择目标文件夹
 * 
 */
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.FT.EndClient;
import com.FT.ReceiveSocket;
import com.file.transport.Win10Socket;

import java.awt.Color;
import javax.swing.JButton;

public class ClientInterface {
	
	//private final int FILE_TRANS_COMPLETE = 1;
	private JFrame frame;
	private JTextField textField;
	private  JTextArea textArea;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		ClientInterface window = new ClientInterface();
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					window.frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
		Thread acceptThread = window.new MyThread();
		acceptThread.start();
		System.out.println("mainxiancheng");
		Thread fileReceiver = window.new FileReceiver();
		fileReceiver.start();
	}

	/**
	 * Create the application.
	 */
	public ClientInterface(){
		initialize();
	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(142, 146, 175, 21);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		new DropTarget(textField, DnDConstants.ACTION_COPY_OR_MOVE,
	            new MyDropTargetAdapter());
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		textArea.setForeground(Color.ORANGE);
		JScrollPane jsp = new JScrollPane(textArea);
		jsp.setBounds(142, 34, 175, 112);
		frame.getContentPane().add(jsp);
		
		//frame.getContentPane().add(new JScrollPane(textArea));
		
		JButton btnSend = new JButton("send");
		btnSend.addMouseListener(new MyMouseListener());;
		btnSend.setBounds(318, 145, 93, 23);
		frame.getContentPane().add(btnSend);
		frame.setVisible(true);
	}
	class MyMouseListener implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			try {
				Date date =  new Date();
				String st = new String(new SimpleDateFormat("yyyy-MM-dd.HH:mm:ss ").format(date));
				textArea.append("win10:"+st+"\r\n");
				textArea.append(textField.getText()+"\r\n");
				textField.setText("");
				EndClient client = new EndClient("192.168.1.121");
				client.send(textField.getText()+"\r\n");
				textField.setText("");
			} catch (UnknownHostException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {}

		@Override
		public void mouseEntered(MouseEvent e) {}
		@Override
		public void mouseExited(MouseEvent e) {}
		
	}
	
	/**
	 *inner class 
	 *receiver the file sent by friend 
	 *port:9999
	 */
	class FileReceiver extends Thread implements Runnable{
		ServerSocket serverSocket;
		@Override
		public void run(){
			try {
				serverSocket = new ServerSocket(9999);
				while(true){
					receiverFile();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		/**
		 *write the received file to the location specified by code  
		 */
		public void receiverFile() throws IOException{
			Socket socket = serverSocket.accept();//
			BufferedInputStream bis = new BufferedInputStream(socket.getInputStream());
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File("d:/1.pdf")));
			//DataOutputStream response = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
			byte[] bytes = new byte[1024];
			int len = 0;
			while((len=bis.read(bytes,0,bytes.length))>0){
				bos.write(bytes,0,len);
				bos.flush();
			}
			textArea.setText("completed ffdfffff:"+socket.getPort());
//			response.writeInt(FILE_TRANS_COMPLETE);
//			response.close();
			bos.close();
			bis.close();
			socket.close();
		}
	}
	
	/**
	 *receiver the data information and display it in the textArea 
	 *port:8888 
	 */
	class MyThread extends Thread implements Runnable{
		@Override
		public void run() {
			ReceiveSocket recSocket = null;
			try {
				recSocket = new ReceiveSocket("192.168.1.121");
				while(true){
					String result = recSocket.accept();
					Date date =  new Date();
					String st = new String(new SimpleDateFormat("yyyy-MM-dd.HH:mm:ss ").format(date));
					textArea.append("win10:"+st+"\r\n");
					textArea.append(result);
				}
			} catch (UnknownHostException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}finally{
				recSocket.close();
			}
		}
		
	}
	class MyDropTargetAdapter extends DropTargetAdapter{

		@Override
		public void drop(DropTargetDropEvent dtde) {
			// TODO Auto-generated method stub
			  try {
				  dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
				  @SuppressWarnings("unchecked")
				List<File> list = (List<File>) (dtde.getTransferable()
				          .getTransferData(DataFlavor.javaFileListFlavor));
				  
				  for(File file:list){
					Win10Socket win10socket = new Win10Socket("192.168.1.121");
					win10socket.sendFile(file.getAbsolutePath());
					textArea.append(file.getAbsolutePath()+"\r\n");
					textArea.append("\r\n");
				  }
				  
			} catch (UnsupportedFlavorException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				dtde.dropComplete(true);
			}
		}
		
	}
} 

