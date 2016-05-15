package com.shiyan;

import javax.swing.*;
import java.awt.Color;

public class TestScroll extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextArea jtextarea;
	private JScrollPane sp;
	public TestScroll()
	{
		super();
		this.setSize(700, 500);
		this.getContentPane().setLayout(null);
		this.add(getJTextArea(), null);
	}
	
	private JScrollPane getJTextArea(){
		sp=new JScrollPane(new JTextArea());
		sp.setBounds(5, 45, 650, 400);
		return sp;
	}
	
	public static void main(String[] args)
	{
		TestScroll w = new TestScroll();
		w.setVisible(true);
	}
}