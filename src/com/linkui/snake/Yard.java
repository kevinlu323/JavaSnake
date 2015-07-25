package com.linkui.snake;

import java.awt.*;
import java.awt.event.*;

public class Yard extends Frame{
	public static final int ROWS = 30;
	public static final int COLUMNS = 50;
	public static final int BLOCK_SIZE = 10;
	
	//YardCanvas yc = new YardCanvas();
	private Snake s = new Snake(Direction.L);
	
	public Yard(){
		YardCanvas yc = new YardCanvas();
		this.setLocation(600,300);
		this.add(yc);
		this.pack();
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}	
		});
	}
	
	public void launchYard(){
		s.addToHead();
		this.setVisible(true);
	}
	
	private class YardCanvas extends Canvas{
		YardCanvas(){
			this.setSize(COLUMNS * BLOCK_SIZE, ROWS * BLOCK_SIZE);
		}

		public void paint(Graphics g) {
			Color c = g.getColor();
			g.setColor(Color.GREEN);
			g.fillRect(0, 0, COLUMNS * BLOCK_SIZE, ROWS * BLOCK_SIZE);
			g.setColor(Color.GRAY);
			for(int i =1; i< ROWS; i++){
				g.drawLine(0, i*BLOCK_SIZE, COLUMNS*BLOCK_SIZE, i*BLOCK_SIZE);
			}
			for(int i =1; i<COLUMNS; i++){
				g.drawLine(i*BLOCK_SIZE, 0, i*BLOCK_SIZE, ROWS*BLOCK_SIZE);
			}
			g.setColor(c);
			s.draw(g);
		}
	}
	
	public static void main(String[] args){
		new Yard().launchYard();
	}
}

