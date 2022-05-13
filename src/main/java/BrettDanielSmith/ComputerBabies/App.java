package BrettDanielSmith.ComputerBabies;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import BrettDanielSmith.ComputerBabies.Interactor.InteractorType;

/**
 * Hello world!
 *
 */
public class App {
	public static int WIDTH = 1280, HEIGHT = 720;

	private World world;
	private JPanel contentPane;
	private JFrame jFrame;

	private int startingPopulation = 2000;

	private Thread updateThread;

	private MouseBauble mouseBauble;

	public App() {

		mouseBauble = new MouseBauble();

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			ControlDialog dialog = new ControlDialog(this);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}

		createWindow();

	}

	public World getWorld() {
		return world;
	}

	public JFrame getjFrame() {
		return jFrame;
	}

	public void createWindow() {
		jFrame = new JFrame();
		contentPane = new JPanel() {
			private static final long serialVersionUID = -6176795980012380080L;

			@Override
			public void paint(Graphics g) {
				super.paint(g);

				////// PAINT WORLD BACKGROUND
				g.setColor(Color.WHITE);
				g.fillRect(0, 0, getWidth(), getHeight());

				world.paint(g);

				mouseBauble.paint(g);
			}
		};
		contentPane.setSize(new Dimension(WIDTH, HEIGHT));
		contentPane.setPreferredSize(new Dimension(WIDTH, HEIGHT));

		jFrame.setTitle("Computer Babies");
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.add(contentPane);
		jFrame.pack();
		jFrame.setLocationRelativeTo(null);
		jFrame.setVisible(true);

		MouseAdapter mA = new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				super.mouseMoved(e);

				mouseBauble.setPosition((int) e.getPoint().getX() - 8, (int) e.getPoint().getY() - 32);
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				super.mouseDragged(e);
				mouseBauble.setPosition2((int) e.getPoint().getX() - 8, (int) e.getPoint().getY() - 32);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				super.mouseReleased(e);
				mouseBauble.setPosition2(-1, -1);
			}
		};

		jFrame.addMouseMotionListener(mA);
		jFrame.addMouseListener(mA);
	}

	public void start(long seed, int cols, int rows, int cellSize, int population, JSpinner updateRate) {

		mouseBauble.setCellSize(cellSize);

		this.startingPopulation = population;

		world = new World(seed, cols, rows, cellSize);

		world.addInteractor(10, 20, 1, 60, InteractorType.BLOCKING);
		world.addInteractor(20, 10, 60, 1, InteractorType.BLOCKING);
		world.addInteractor(20, 90, 60, 1, InteractorType.BLOCKING);
		world.addInteractor(90, 20, 1, 60, InteractorType.BLOCKING);

		world.addInteractor(0, 0, 5, 5, InteractorType.DAMAGE);
		world.addInteractor(0, 95, 5, 5, InteractorType.DAMAGE);

		world.addInteractor(95, 0, 5, 5, InteractorType.DAMAGE);
		world.addInteractor(95, 95, 5, 5, InteractorType.DAMAGE);

		for (int i = 11; i < 89; i++) {
			for (int j = 11; j < 89; j++) {
				if (world.getRandom().nextBoolean())
					world.addInteractor(i, j, 1, 1, InteractorType.FOOD);
			}
		}

		world.populate(startingPopulation);

		if (updateThread != null)
			stop();

		updateThread = new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					if (world != null) {
						world.update();
						contentPane.repaint();

						jFrame.setTitle("Computer Babies - Starting population: " + startingPopulation
								+ " | Current population: " + world.getCellAmounts());
						try {
							Thread.sleep((long) updateRate.getValue());
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
		});
		updateThread.start();

		pack();
	}

	public static void main(String[] args) {
		App app = new App();
	}

	public void stop() {
		updateThread.stop();
	}

	public void pack() {
		jFrame.setSize((getWorld().getWidth() * getWorld().getCellSize()) + 17,
				(getWorld().getHeight() * getWorld().getCellSize()) + 40);
	}
}
