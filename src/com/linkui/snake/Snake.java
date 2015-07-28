package com.linkui.snake;

import java.awt.*;
import java.awt.event.KeyEvent;

import com.linkui.snake.Yard.YardCanvas;

public class Snake {
	Node head;
	Node tail;
	int size;
	YardCanvas y;
	private boolean keyFlag = true; // use this flag to avoid 2 keys pressed at the same time.
	
	Snake (YardCanvas y, Direction dir){
		Node n = new Node(20, 3, dir);
		head = n;
		tail = n;
		size = 1;
		this.y = y;
		this.addToTail();
		this.addToTail();
	}
	
	public void addToTail(){
		Node n = null;
		switch(tail.dir){
		case L:
			n = new Node(tail.rows, tail.cols + 1, tail.dir);
			break;
		case U:
			n = new Node(tail.rows + 1 , tail.cols, tail.dir);
			break;
		case R:
			n = new Node(tail.rows, tail.cols - 1, tail.dir);
			break;
		case D:
			n = new Node(tail.rows - 1, tail.cols, tail.dir);
			break;
		}
		tail.next = n;
		n.prev = tail;
		tail = n;
		tail.next = null;
		size++;
	}
	
	public void addToHead(){
		Node n = null;
		switch(head.dir){
		case L:
			n = new Node(head.rows, head.cols - 1, head.dir);
			break;
		case U:
			n = new Node(head.rows - 1 , head.cols, head.dir);
			break;
		case R:
			n = new Node(head.rows, head.cols + 1, head.dir);
			break;
		case D:
			n = new Node(head.rows + 1, head.cols, head.dir);
			break;
		}
		n.next = head;
		head.prev = n;
		head = n;
		head.prev = null;
		size++;
	}
	
	public void removeTail(){
		if(size == 0) return;
		tail = tail.prev;
		tail.next = null;
		
	}
	
	public void draw(Graphics g){
		if(size<=0) return;
		this.move();
		for(Node n = head; n != null; n=n.next){
			n.draw(g);
		}
	}
	
	/**
	 *  This method is used to move the body of snake
	 */
	public void move(){
		if(isDead()) return;
		this.addToHead();
		this.removeTail();
		keyFlag = true;//do not allow keyEvent until snake make a move
	}
	
	/**
	 * This method is used to check if snake body collides with yard bounds or itself
	 */
	public boolean isDead(){
		if(head.rows<0){
			head.rows = Yard.ROWS-1;
		}
		if(head.cols<0){
			head.cols = Yard.COLUMNS-1;
		}
		if(head.rows>Yard.ROWS-1){
			head.rows = 0;
		}
		if(head.cols>Yard.COLUMNS-1){
			head.cols = 0;
		}
		
		for(Node n = head.next; n != null; n = n.next){
			if(head.rows == n.rows && head.cols == n.cols){
				y.stop();
				return true;
			}
		}
		return false;
	}
	
	public boolean isInSnake(Egg e){
		int eggRow = e.getRow();
		int eggCol = e.getCol();
		for(Node n = head; n != tail; n = n.next){
			if(eggRow == n.rows && eggCol == n.cols){
				return true;
			}
		}
		return false;
	}
	
	public Rectangle getRect(){
		return new Rectangle(head.cols * Yard.BLOCK_SIZE, head.rows * Yard.BLOCK_SIZE, head.w, head.h);
	}
	
	public void eat(Egg e){
		if(this.getRect().intersects(e.getRect())){		
			this.addToHead();
			this.addToTail();
			e.reAppear();
			y.updateScore();
		}
	}
	
	private class Node{
		int w = Yard.BLOCK_SIZE, h = Yard.BLOCK_SIZE;
		int rows, cols;
		Direction dir;
		Node next = null;
		Node prev = null;
		
		Node(int rows, int cols, Direction dir){
			this.rows = rows;
			this.cols = cols;
			this.dir = dir;
		}
		
		public void draw(Graphics g){
			Color c = g.getColor();
			g.setColor(Color.BLACK);
			g.fillRect(cols * Yard.BLOCK_SIZE, rows * Yard.BLOCK_SIZE, w, h);
			g.setColor(c);
		}
	}
	
	/**
	 * Used to change snake direction (head direction) when key is pressed.
	 * @param e KeyEvent from Yard class
	 */
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if(!keyFlag) return; //If a key is pressed but snake doesn't move yet, will ignore next key press.
		switch(key) {
		case KeyEvent.VK_LEFT:
			if(head.dir != Direction.R){
				head.dir = Direction.L;
				keyFlag = false;
			}
			break;
		case KeyEvent.VK_UP:
			if(head.dir != Direction.D){
				head.dir = Direction.U;
				keyFlag = false;
			}
			break;
		case KeyEvent.VK_RIGHT:
			if(head.dir != Direction.L){
				head.dir = Direction.R;
				keyFlag = false;
			}
			break;
		case KeyEvent.VK_DOWN:
			if(head.dir != Direction.U){
				head.dir = Direction.D;
				keyFlag = false;
			}
			break;
		case KeyEvent.VK_Z:
			int i = 1;
			break;
		}
	}
}
