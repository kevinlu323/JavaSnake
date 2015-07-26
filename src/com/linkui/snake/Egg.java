package com.linkui.snake;

import java.awt.*;
import java.util.Random;

public class Egg {
	int row, col;
	int w = Yard.BLOCK_SIZE;
	int h = Yard.BLOCK_SIZE;
	private static Random r = new Random();
	
	public Egg(int row, int col){
		this.row = row;
		this.col = col;
	}
	
	public Egg(){
		this(r.nextInt(Yard.ROWS), r.nextInt(Yard.COLUMNS));
	}
	
	public void draw(Graphics g){
		Color c = g.getColor();
		g.setColor(Color.ORANGE);
		g.fillOval(col * Yard.BLOCK_SIZE, row * Yard.BLOCK_SIZE, w, h);
		g.setColor(c);
	}
	
	public void reAppear(){
		this.row = r.nextInt(Yard.ROWS);
		this.col = r.nextInt(Yard.COLUMNS);
	}
	
	public Rectangle getRect(){
		return new Rectangle(col * Yard.BLOCK_SIZE, row * Yard.BLOCK_SIZE, w, h);
	}
}
