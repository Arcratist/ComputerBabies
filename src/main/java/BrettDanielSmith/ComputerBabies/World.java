package BrettDanielSmith.ComputerBabies;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import BrettDanielSmith.ComputerBabies.Interactor.InteractorType;

public class World {

	private Cell[][] cells;
	private List<Interactor> interactors;
	private int width, height;
	private long seed;
	private Random random;
	private int cellSize;

	public World(long seed, int width, int height, int cellSize) {
		this.width = width;
		this.height = height;
		this.seed = seed;
		this.cellSize = cellSize;
		random = new Random(seed);
		cells = new Cell[width][height];
		interactors = new ArrayList<Interactor>();
	}

	public void update() {

		for (int i = 0; i < interactors.size(); i++) {
			Interactor bound = interactors.get(i);
			bound.update(this);

			if (bound.getDead())
				getInteractors().remove(bound);
		}

		for (int yy = 0; yy < getHeight(); yy++) {
			for (int xx = 0; xx < getWidth(); xx++) {
				Cell cell = cells[xx][yy];
				if (cell != null) {
					cell.update();
				}
			}
		}
	}

	public int getCellSize() {
		return cellSize;
	}

	public void paint(Graphics g) {
		
		g.setColor(Color.RED);
		g.drawRect(0, 0, width * cellSize, height * cellSize);
		
		////// PAINT CELLS
		for (int yy = 0; yy < getHeight(); yy++) {
			for (int xx = 0; xx < getWidth(); xx++) {
				Cell cell = cells[xx][yy];
				if (cell != null) {
					cell.paint(g);
				}
			}
		}

		////// PAINT WORLD FOREGROUND
		for (int i = 0; i < interactors.size(); i++) {
			Interactor bound = interactors.get(i);
			bound.paint(g);
		}
	}

	public void populate(int amount) {
		for (int i = 0; i < amount;) {

			int randomXPos = getRandom().nextInt(getWidth());
			int randomYPos = getRandom().nextInt(getHeight());

			Cell preCell = cells[randomXPos][randomYPos];

			if (preCell == null && !containsBounds(randomXPos, randomYPos, InteractorType.BLOCKING)) {
				Cell cell = new Cell(this, 32, randomXPos, randomYPos);

				cells[randomXPos][randomYPos] = cell;
				i++;
			}
		}

	}

	public void addInteractor(int x, int y, int width, int height, InteractorType type) {
		interactors.add(new Interactor(this, x, y, width, height, type));
	}

	public List<Interactor> getInteractors() {
		return interactors;
	}

	public Interactor getInteractor(int x, int y) {
		for (Interactor bound : interactors) {
			int xW = bound.getX() + bound.getWidth();
			int yH = bound.getY() + bound.getHeight();

			if (x >= bound.getX() && x < xW && y >= bound.getY() && y < yH) {
				return bound;
			}
		}
		return null;
	}

	public boolean containsBounds(int x, int y, InteractorType type) {
		for (Interactor bound : interactors) {
			int xW = bound.getX() + bound.getWidth();
			int yH = bound.getY() + bound.getHeight();

			if (x >= bound.getX() && x < xW && y >= bound.getY() && y < yH && bound.getType().equals(type)) {
				return true;
			}
		}
		return false;
	}

	public Cell[][] getCells() {
		return cells;
	}

	public int getCellAmounts() {
		int total = 0;
		for (int yy = 0; yy < getHeight(); yy++) {
			for (int xx = 0; xx < getWidth(); xx++) {
				if (cells[xx][yy] != null)
					total++;
			}
		}
		return total;
	}

	public Random getRandom() {
		return random;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void clearCells() {
		cells = new Cell[width][height];
	}

}
