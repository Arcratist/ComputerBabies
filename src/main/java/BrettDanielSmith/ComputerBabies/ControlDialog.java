package BrettDanielSmith.ComputerBabies;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;

public class ControlDialog extends JDialog {
	private static final long serialVersionUID = 1054201369363225392L;
	private final JPanel contentPanel = new JPanel();
	private JSpinner spinnerCol;
	private JSpinner spinnerRow;
	private JSpinner spinnerPop;
	private JSpinner spinnerCelSiz;
	private JTextField textField;

	public ControlDialog(final App app) {
		setResizable(false);
		setTitle("Computer Babies - Control Panel");
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lblNewLabel = new JLabel("Collums: ");
		lblNewLabel.setBounds(10, 10, 45, 20);
		contentPanel.add(lblNewLabel);

		JLabel lblRows = new JLabel("Rows: ");
		lblRows.setBounds(10, 30, 45, 20);
		contentPanel.add(lblRows);

		spinnerCol = new JSpinner();
		spinnerCol.setModel(new SpinnerNumberModel(256, 16, 1024, 1));
		spinnerCol.setBounds(60, 10, 50, 20);
		contentPanel.add(spinnerCol);

		spinnerRow = new JSpinner();
		spinnerRow.setModel(new SpinnerNumberModel(128, 16, 1024, 1));
		spinnerRow.setBounds(60, 30, 50, 20);
		contentPanel.add(spinnerRow);

		spinnerPop = new JSpinner();
		spinnerPop.setModel(new SpinnerNumberModel(1024, 1, 4096, 1));
		spinnerPop.setBounds(380, 10, 50, 20);
		contentPanel.add(spinnerPop);

		JLabel lblPopulation = new JLabel("Population: ");
		lblPopulation.setBounds(315, 10, 60, 20);
		contentPanel.add(lblPopulation);

		JLabel lblCellSize = new JLabel("Cell Size:");
		lblCellSize.setBounds(10, 50, 45, 20);
		contentPanel.add(lblCellSize);

		spinnerCelSiz = new JSpinner();
		spinnerCelSiz.setModel(new SpinnerNumberModel(4, 2, 32, 1));
		spinnerCelSiz.setBounds(60, 50, 50, 20);
		contentPanel.add(spinnerCelSiz);

		JLabel lblUpdaterate = new JLabel("Rate: ");
		lblUpdaterate.setBounds(10, 70, 45, 20);
		contentPanel.add(lblUpdaterate);

		JSpinner spinnerRate = new JSpinner();
		spinnerRate.setModel(new SpinnerNumberModel(new Long(10), new Long(0), new Long(500), new Long(1)));
		spinnerRate.setBounds(60, 70, 50, 20);
		contentPanel.add(spinnerRate);
		
		JLabel lblSeed = new JLabel("Seed: ");
		lblSeed.setBounds(315, 30, 35, 20);
		contentPanel.add(lblSeed);
		
		textField = new JTextField();
		textField.setToolTipText("Random if empty");
		textField.setBounds(350, 30, 80, 20);
		contentPanel.add(textField);
		textField.setColumns(10);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);

			JButton btnNewButton_1 = new JButton("Kill Interactors");
			btnNewButton_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					app.getWorld().getInteractors().clear();
				}
			});

			JButton btnNewButton_3 = new JButton("Pack");
			btnNewButton_3.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					app.pack();
				}
			});
			buttonPane.add(btnNewButton_3);
			buttonPane.add(btnNewButton_1);

			JButton btnNewButton = new JButton("Kill Entities");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					app.getWorld().clearCells();
				}
			});
			buttonPane.add(btnNewButton);
			{
				JButton okButton = new JButton("Start");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
				okButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						app.start(stringToSeed(textField.getText().trim()), (int) spinnerCol.getValue(), (int) spinnerRow.getValue(),
								(int) spinnerCelSiz.getValue(), (int) spinnerPop.getValue(), spinnerRate);
					}
				});
			}
			{
				JButton cancelButton = new JButton("Exit");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						System.exit(0);
					}
				});

				JButton btnNewButton_2 = new JButton("Stop");
				btnNewButton_2.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						app.stop();
					}
				});
				buttonPane.add(btnNewButton_2);
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	
	public static long stringToSeed(String s) {
		if (s == null || s.isBlank()) {
			return new Random().nextLong();
		}
		long hash = 0;
		for (char c : s.toCharArray()) {
			hash = 31L * hash + c;
		}
		return hash;
	}
}
