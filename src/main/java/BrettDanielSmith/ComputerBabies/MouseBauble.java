package BrettDanielSmith.ComputerBabies;

import java.awt.Color;
import java.awt.Graphics;

public class MouseBauble {

	private int x, y;
	private int cellSize = 8;
	private int x2;
	private int y2;

	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void paint(Graphics g) {
		g.setColor(Color.RED);
		g.fillOval(x - (cellSize / 2), y - (cellSize / 2), cellSize, cellSize);
		if (x2 != -1 && y2 != -1) {
			g.setColor(Color.GREEN);
			g.fillOval(x2 - (cellSize / 2), y2 - (cellSize / 2), cellSize, cellSize);
			g.drawRect(x, y, -x+x2, -y+y2);
		}
	}

	public void setCellSize(int cellSize) {
		this.cellSize = cellSize;
	}

	public void setPosition2(int x, int y) {
		this.x2 = x;
		this.y2 = y;
	}

}
