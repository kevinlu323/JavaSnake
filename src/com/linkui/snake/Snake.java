package com.linkui.snake;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Snake {
	Node head;
	Node tail;
	int size;
	Yard y;
	
	Snake (Yard y, Direction dir){
		Node n = new Node(20, 3, dir);
		head = n;
		tail = n;
		size = 1;
		this.y = y;
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
		this.addToHead();
		this.removeTail();
		isDead();
	}
	
	/**
	 * This method is used to check if snake body collides with yard bounds or itself
	 */
	public void isDead(){
		if(head.rows<0){
			head.rows = Yard.ROWS;
		}
		if(head.cols<0){
			head.cols = Yard.COLUMNS;
		}
		if(head.rows>Yard.ROWS){
			head.rows = 0;
		}
		if(head.cols>Yard.COLUMNS){
			head.cols = 0;
		}
		
		for(Node n = head.next; n != null; n = n.next){
			if(head.rows == n.rows && head.cols == n.cols){
				y.stop();
			}
		}
	}
	
	public Rectangle getRect(){
		return new Rectangle(head.cols * Yard.BLOCK_SIZE, head.rows * Yard.BLOCK_SIZE, head.w, head.h);
	}
	
	public void eat(Egg e){
		if(this.getRect().intersects(e.getRect())){
			e.reAppear();
			this.addToHead();
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
		switch(key) {
		case KeyEvent.VK_LEFT:
			if(head.dir != Direction.R)
				head.dir = Direction.L;
			break;
		case KeyEvent.VK_UP:
			if(head.dir != Direction.D)
				head.dir = Direction.U;
			break;
		case KeyEvent.VK_RIGHT:
			if(head.dir != Direction.L)
				head.dir = Direction.R;
			break;
		case KeyEvent.VK_DOWN:
			if(head.dir != Direction.U)
				head.dir = Direction.D;
			break;
		}
	}
}
