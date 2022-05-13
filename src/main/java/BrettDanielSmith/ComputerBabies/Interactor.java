package BrettDanielSmith.ComputerBabies;

import java.awt.Color;
import java.awt.Graphics;

public class Interactor {

	private int x, y;
	private int width, height;

	private InteractorType type;
	private boolean dead = false;
	private World world;

	private int cellSize;

	public Interactor(World world, int x, int y, int width, int height, InteractorType type) {
		this.world = world;
		this.cellSize = world.getCellSize();
		this.x = x;
		this.y = y;

		this.width = width;
		this.height = height;

		this.type = type;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void paint(Graphics g) {
		switch (type) {
		case BLOCKING:
			g.setColor(Color.BLACK);
			g.fillRect(x * cellSize, y * cellSize, width * cellSize, height * cellSize);
			break;

		case DAMAGE:
			g.setColor(Color.RED);
			g.fillRect(x * cellSize, y * cellSize, width * cellSize, height * cellSize);
			break;

		case FOOD:
			g.setColor(Color.GREEN);
			g.fillOval(x * cellSize, y * cellSize, width * cellSize, height * cellSize);
			break;

		case HOUSE:

			break;

		default:
			break;
		}

	}

	public void update(World world) {

		switch (type) {
		case BLOCKING:

			break;

		case DAMAGE:

			break;

		case FOOD:

			break;

		case HOUSE:

			break;

		default:
			break;
		}

	}

	enum InteractorType {
		BLOCKING, DAMAGE, FOOD, HOUSE
	}

	public InteractorType getType() {
		return type;
	}

	public boolean getDead() {
		return dead;
	}

	public void setDead() {
		this.dead = true;
	}
}
