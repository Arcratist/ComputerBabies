package BrettDanielSmith.ComputerBabies;

import java.awt.Color;
import java.awt.Graphics;

import BrettDanielSmith.ComputerBabies.Interactor.InteractorType;

public class Cell {

	public int id;
	public Color color;
	public int x, y;

	public int[] dna;

	public int health = 100;
	public int hunger = 1000;
	private World world;
	private int cellSize;

	public Cell(World world, int segments, int x, int y) {
		this.world = world;
		this.cellSize = world.getCellSize();
		this.x = x;
		this.y = y;
		dna = new int[segments];

		for (int i = 0; i < dna.length; i++) {
			dna[i] = world.getRandom().nextInt();
		}

		color = new Color(dna[0]);
	}

	public void paint(Graphics g) {
		g.setColor(color);
		g.fillOval(x * cellSize, y * cellSize, cellSize, cellSize);
	}

	public void update() {

		if (world.getRandom().nextInt(10) == 0) {

			int xM = 0;
			int yM = 0;

			if (world.getRandom().nextBoolean())
				xM = 1;
			else
				xM = -1;

			if (world.getRandom().nextBoolean())
				yM = 1;
			else
				yM = -1;

			move(xM, yM);
		}

		if (world.containsBounds(x, y, InteractorType.DAMAGE)) {
			health--;
		}

		if (world.containsBounds(x, y, InteractorType.FOOD) && hunger <= 500) {
			world.getInteractor(x, y).setDead();
			hunger += 500;
			health += 5;
		}

		if (hunger <= 0) {
			hunger = 0;
			health--;
		}

		if (hunger > 1000)
			hunger = 1000;

		if (health > 100)
			health = 100;

		if (health <= 0)
			die();

	}

	public void sense() {

	}

	public void move(int xOff, int yOff) {

		int xxOff = x + xOff;
		int yyOff = y + yOff;

		if (xxOff <= world.getWidth() - 1 && xxOff >= 0 && yyOff <= world.getHeight() - 1 && yyOff >= 0
				&& !world.containsBounds(xxOff, yyOff, InteractorType.BLOCKING)) {
			if (world.getCells()[xxOff][yyOff] == null && world.getCells()[xxOff][yyOff] != this) {
				world.getCells()[x][y] = null;
				x = xxOff;
				y = yyOff;
				world.getCells()[x][y] = this;
				hunger -= 5;
				// System.out.println("Cell moved");
			}
		}

	}

	public void kill() {

	}

	public void die() {
		world.getCells().clone()[x][y] = null;
		System.out.println("Cell died");
	}

}
