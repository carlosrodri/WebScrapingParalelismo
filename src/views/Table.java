package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.HashSet;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class Table extends JPanel{

	private static final long serialVersionUID = 1L;
	private static DefaultTableModel model;
	private JTable table;
	private JScrollPane scroll;
	private static final Color HEADER_COLOR = Color.decode("#6a85b1");
	private static final Font FONT_HEADER = new Font("Comic Sans Ms", Font.BOLD, 10);
	private static final int ROW_HEIGTH = 25;
	/**
	 * 
	 * @author Carlos
	 */
	public Table() {
		setLayout(new BorderLayout());

		model = new DefaultTableModel();

		table = new JTable(model);
		table.setRowHeight(ROW_HEIGTH);
		table.setEnabled(true);
		model.setColumnIdentifiers(new Object[] {"Name", "Status"});

		JTableHeader header = table.getTableHeader();
		header.setBackground(HEADER_COLOR);
		header.setForeground(Color.WHITE);
		header.setFont(FONT_HEADER);

		scroll = new JScrollPane(table);

		add(scroll, BorderLayout.CENTER);
	}

	public void setIdentifiers(Object[] header) {
		model.setColumnIdentifiers(header);
		repaint();
	}

	public void refreshTable(HashSet<String> list) {
		model.setRowCount(0);
		for (String string : list) {
			model.addRow(new Object[] {string, "In progress"});
		}
	}

	public static void changeDownload(String name) {
		for (int j = 0; j < model.getRowCount(); j++) {
			//.println(j);
			if(model.getValueAt(j, 0).equals(name)) {
//				//.println("coincidencia   " + model.getValueAt(j, 0));
				model.setValueAt("Downloaded", j, 1);
			}
		}
	}

	public static void changeBlack(String img) {
		for (int j = 0; j < model.getRowCount(); j++) {
			//.println("blanco y negro");
			if(model.getValueAt(j, 0).equals(img)) {
				model.setValueAt("Black&White", j, 1);
			}
		}
	}
}