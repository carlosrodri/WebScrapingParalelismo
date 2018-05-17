package views;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import javax.swing.JFrame;
import javax.swing.Timer;

public class MainWindow extends JFrame{
	
	private Table table;
	private Timer timer;
	
	private static final long serialVersionUID = 1L;

	public MainWindow() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
//		setExtendedState(MAXIMIZED_BOTH);
		setSize(400, 600);
		
		table = new Table();
		add(table, BorderLayout.CENTER);
		
		timer = new Timer(50, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				repaint();
			}
		});
		timer.start();
		setVisible(true);
	}

	public void refresh(HashSet<String> list) {
		table.refreshTable(list);
	}
}
