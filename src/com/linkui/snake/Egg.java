package com.linkui.snake;

import java.awt.*;
import java.util.Random;

public class Egg {
	int row, col;
	int w = Yard.BLOCK_SIZE;
	int h = Yard.BLOCK_SIZE;
	private static Random r = new Random();
	private Color eggC = Color.ORANGE;
	private Snake s;
	
	public Egg(int row, int col){
		this.row = row;
		this.col = col;
	}
	
	public Egg(Snake s){
		this(r.nextInt(Yard.ROWS-2)+2, r.nextInt(Yard.COLUMNS-2)+2);
		this.s = s;
	}
	
	public void draw(Graphics g){
		Color c = g.getColor();
		if(eggC == Color.ORANGE) eggC = Color.RED;
		else eggC = Color.ORANGE;
		g.setColor(eggC);
		g.fillOval(col * Yard.BLOCK_SIZE, row * Yard.BLOCK_SIZE, w, h);
		g.setColor(c);
	}
	
	/**
	 * Method to generate next egg, also needs to check to avoid egg generated in the snake.
	 */
	public void reAppear(){
		this.row = r.nextInt(Yard.ROWS-2-25)+2;
		this.col = r.nextInt(Yard.COLUMNS-2-35)+2;
		if(s.isInSnake(this)) 
			this.reAppear();
	}
	
	public Rectangle getRect(){
		return new Rectangle(col * Yard.BLOCK_SIZE, row * Yard.BLOCK_SIZE, w, h);
	}
	
	public int getRow(){
		return this.row;
	}
	
	public int getCol(){
		return this.col;
	}
}
