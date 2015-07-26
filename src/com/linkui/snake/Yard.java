package com.linkui.snake;

import java.awt.*;
import java.awt.event.*;

public class Yard extends Frame{
	public static final int ROWS = 30;
	public static final int COLUMNS = 50;
	public static final int BLOCK_SIZE = 15;
	
	private boolean gameOver = false;
	private int score = 0;
	
	Image offScreenImage = null;
	private Snake s = new Snake(this, Direction.R);
	private Egg e = new Egg();
	//YardCanvas yc = new YardCanvas();
	
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
		this.addKeyListener(new KeyMonitor());
	}
	
	public void launchYard(){
		s.addToHead();
		s.addToHead();
		this.setVisible(true);
	}
	
	public void updateScore(){
		this.score += 5;
	}
	public void stop(){
		gameOver = true;
	}
	
	private class YardCanvas extends Canvas{
		PaintThread pt = new PaintThread();
		
		YardCanvas(){
			this.setSize(COLUMNS * BLOCK_SIZE, ROWS * BLOCK_SIZE);
			pt.start();
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
			g.drawString("Score: " + score, 10, 30);
			if(gameOver){
				Font f = new Font ("Harlow Solid",Font.BOLD,50);
				g.setFont(f);
				g.setColor(Color.RED);
				g.drawString("Game Over!", 225, 200);
				pt.gameOver();
			}
			g.setColor(c);
			s.draw(g);
			e.draw(g);
			s.eat(e);
		}
		
		/**
		 * Override update() method to use double buffer to avoid blinking issue.
		 */
		public void update(Graphics g) {
			if(offScreenImage == null){
				offScreenImage = this.createImage(BLOCK_SIZE * COLUMNS, BLOCK_SIZE * ROWS);
			}
			Graphics gOff = offScreenImage.getGraphics();
			paint(gOff);
			g.drawImage(offScreenImage, 0, 0, null);
		}
		
		private class PaintThread extends Thread{
			private boolean flag = true;
			public void run(){
				while (flag){
					repaint();
					try{
						Thread.sleep(100);
					} catch (InterruptedException e){
						e.printStackTrace();
					}
				}		
			}
			
			public void gameOver(){
				flag = false;
			}
			
			public void gameRestart(){
				flag = true;
			}
		}
	}
	private class KeyMonitor extends KeyAdapter{
		public void keyPressed(KeyEvent e) {
			/*int key = e.getKeyCode();
			if (key == KeyEvent.VK_F2){
				new Yard().launchYard();
			}*/
			s.keyPressed(e);
		}
	}
	
	public static void main(String[] args){
		new Yard().launchYard();
	}
}

