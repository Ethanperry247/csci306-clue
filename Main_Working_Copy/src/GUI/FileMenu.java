// Authors: Ethan Perry and Caleb Pan

package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class FileMenu extends JMenuBar {
	private DetectiveNotesDialog dialog; // The custom dialog to be shown when opened.

	public FileMenu() {
		dialog = new DetectiveNotesDialog();
		add(createFileMenu()); // Add the file menu.
	}
	
	private JMenu createFileMenu() {
		JMenu menu = new JMenu("File");
		menu.add(createDetectiveNotesItem()); 	// Open the detective notes.
		menu.add(createExitItem()); 			// Give the ability to exit.
		return menu;
	}
	
	private JMenuItem createDetectiveNotesItem() {
		JMenuItem item = new JMenuItem("Detective Notes");
		
		class MenuItemListener implements ActionListener {
			public void actionPerformed(ActionEvent e) { // Make the dialog visible when clicked.
				dialog.setVisible(true);
			}
		}
		
		item.addActionListener(new MenuItemListener());
		return item;
	}
	
	private JMenuItem createExitItem() {
		JMenuItem item = new JMenuItem("Exit Clue");
		
		class MenuItemListener implements ActionListener {
			public void actionPerformed(ActionEvent e) { // Exit on click.
				System.exit(0);
			}
		}
		
		item.addActionListener(new MenuItemListener());
		return item;
	}

}
