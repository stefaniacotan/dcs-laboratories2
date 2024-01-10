package lab5.Components;

import javax.swing.JFrame;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;

import javax.swing.JList;
import javax.swing.JScrollPane;

import java.awt.Component;
import java.awt.Font;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

public class PetriNetWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public PetriNet petriNet = null;
	Thread networkThread;
	boolean AutoStart = false;

	public PetriNetWindow(boolean autoStart) {
		this.AutoStart = autoStart;
		setBounds(100, 100, 805, 470);
		JTextPane txtName = new JTextPane();
		txtName.setFont(new Font("Consolas", Font.BOLD, 12));

		MyCellRenderer cellRenderer = new MyCellRenderer(800);
		DefaultListModel<String> model = new DefaultListModel<String>();
		JList<String> lstMsg = new JList<String>(model);
		lstMsg.setCellRenderer(cellRenderer);
		lstMsg.setFont(new Font("Consolas", Font.BOLD, 12));

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(lstMsg);

		JButton btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (!petriNet.PauseFlag) {
					networkThread = new Thread(petriNet);
					networkThread.start();

					txtName.setText(petriNet.PetriNetName + " [Network Port:" + petriNet.NetworkPort + "]");
					petriNet.setDataLoadFinishedListener(new PetriNet.DataLoadFinishedListener() {
						@Override
						public void onDataLoadFinishedListener(String data_type) {
							addString(model, scrollPane, data_type, lstMsg);
						}
					});
				} else {
					addString(model, scrollPane, "Continued....", lstMsg);
					petriNet.PauseFlag = false;
				}
			}
		});

		JButton btnPause = new JButton("Pause");
		btnPause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				petriNet.PauseFlag = true;
				addString(model, scrollPane, "Paused....", lstMsg);
			}
		});
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
				.createSequentialGroup().addGap(10)
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
								.addComponent(btnStart, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
								.addGap(10)
								.addComponent(btnPause, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE))
						.addComponent(txtName, GroupLayout.DEFAULT_SIZE, 769, Short.MAX_VALUE)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 769, Short.MAX_VALUE))
				.addContainerGap()));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addGap(11)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(btnStart)
								.addComponent(btnPause))
						.addGap(11).addComponent(txtName, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
						.addGap(11).addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 341, Short.MAX_VALUE)
						.addGap(11)));
		getContentPane().setLayout(groupLayout);

		this.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				petriNet.Stop();
				System.exit(0);
			}

			@Override
			public void windowOpened(java.awt.event.WindowEvent e) {
				txtName.setText(petriNet.PetriNetName + " [Network Port:" + petriNet.NetworkPort + "]");
				if (autoStart == true) {
					if (!petriNet.PauseFlag) {
						networkThread = new Thread();
						networkThread = new Thread(petriNet);
						networkThread.start();

						txtName.setText(petriNet.PetriNetName + " [Network Port:" + petriNet.NetworkPort + "]");
						petriNet.setDataLoadFinishedListener(new PetriNet.DataLoadFinishedListener() {
							@Override
							public void onDataLoadFinishedListener(String data_type) {
								addString(model, scrollPane, data_type, lstMsg);
							}
						});
					} else {
						addString(model, scrollPane, "Continued....", lstMsg);
						petriNet.PauseFlag = false;
					}
				}
			}

		});
	}

	public void addString(DefaultListModel<String> model, JScrollPane scrollPane, String msg, JList<String> lstMsg) {

		java.awt.EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				model.add(0, msg);
				scrollPane.updateUI();
				lstMsg.updateUI();
			}
		});

	}

	class MyCellRenderer extends DefaultListCellRenderer {
		/**
		 * 
		 */

		private static final long serialVersionUID = 1L;
		private int width;

		public MyCellRenderer(int width) {
			this.width = width;
		}

		@Override
		public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
				boolean cellHasFocus) {

			String col = "";
			if (value.toString().contains("ExecutionList")) {
				col = "color:Black";
			}
			
			if (value.toString().contains("PlaceList")) {
				col = "color:Blue";
			}
			
			if (value.toString().contains("I got an Input From NetWork")) {
				col = "color:Green";
			}
			
			if (value.toString().contains("conditions are false")) {
				col = "color:Red";
			}
		
			String text = String.format("<html><body style='width:%dpx; %s'>%s</html>", width, col, value.toString());
			return super.getListCellRendererComponent(list, text, index, isSelected, cellHasFocus);
		}

	}

}
