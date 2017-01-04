package com.root.musicsorter;


import javax.swing.JFrame;
import javax.swing.JFileChooser;
import java.awt.BorderLayout;

public class FileChooser {
	
	private JFrame frame;
	private JFileChooser fileChooser;
	
	public FileChooser(String nazov, int sirka, int vyska) {
		vytvorOkno(nazov, sirka, vyska);
	}
	
	private void vytvorOkno(String nazov, int sirka, int vyska) {
		frame = new JFrame(nazov);
		frame.setSize(sirka, vyska);
		frame.setResizable(false);
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		
		fileChooser = new JFileChooser();
		frame.getContentPane().add(fileChooser, BorderLayout.CENTER);
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		//frame.getContentPane().setLayout(null);
		frame.setVisible(false);
	}
	
	public String getFileSelected() {
		if (fileChooser.showOpenDialog(fileChooser) == JFileChooser.APPROVE_OPTION) {
			return fileChooser.getSelectedFile().getAbsolutePath();
		} else {
			return "No selected file!";
		}
	}
	
	public void closeWindow() {
		frame.setVisible(false);
		frame.dispose();
	}
	
}
