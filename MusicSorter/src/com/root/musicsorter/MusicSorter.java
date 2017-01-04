package com.root.musicsorter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFrame;

import org.apache.commons.io.FileUtils;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.UIManager;

public class MusicSorter {
	
	private static ArrayList<File> vsetkyPesnicky = new ArrayList<File>();
	private static ArrayList<String> menaAutorov = new ArrayList<String>();
	
	private static String zlozkaHudbaNeutriedena;
	private static String cielovaZlozka;
	
	/*******************************FRAME VARIABLES***********************************/
	private static JFrame frmAuthsorter;
	
	private static JTextField absPathUnsortedText;
	private static JTextField absPathSortedText;
	
	private static JLabel lblEnterTheAbsolute;
	private static JLabel lblAbsolutePathOf;
	private static JButton btnStartSorting;
	private static JLabel log_info_label;
	private static JLabel lblNewLabel;
	private static JLabel lblDevelopedBy;
	private static JLabel label;
	private static JButton btnOpen;
	
	static JFrame popupFrame;
	private static JLabel lblFirstRelease;
	
	private static void initJFrame(String title, int width, int height) {
		frmAuthsorter = new JFrame(title);
		frmAuthsorter.setTitle("Authsorter 1.20");
		frmAuthsorter.setIconImage(Toolkit.getDefaultToolkit().getImage(
				MusicSorter.class.getResource("/com/root/musicsorter/gallery-icon-active.png"
		)));
		frmAuthsorter.setSize(476, 235);
		frmAuthsorter.setResizable(false);
		frmAuthsorter.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmAuthsorter.setLocationRelativeTo(null);
		frmAuthsorter.getContentPane().setLayout(null);
		
		initJLables();
		initJTextFields();
		initJButtons();
		
		popupFrame = new JFrame();
		
		frmAuthsorter.setVisible(true);
	}
	
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		initJFrame("Authsorter 1.0", 600, 322);
	}
	
	private static void vytvorZlozku(String cesta) {
		File dir = new File(cesta);
		dir.mkdir();
	}
	
	private static void vytvorPoleSoVsetkymiPesnickami(String nazovSuboru, ArrayList<File> pesnicky) {
	    File[] fList = new File(nazovSuboru).listFiles();
	    
	    for (int i = 0; i < fList.length; i++) {
	    	if (fList[i].isFile()) {
	    		pesnicky.add(fList[i]);
	    	} else if (fList[i].isDirectory()) {
	    		// Rekurzia
	    		vytvorPoleSoVsetkymiPesnickami(fList[i].getAbsolutePath(), pesnicky);
	    	}
	    }
	}
	
	private static void vyhodZbytocnePriecinky(String cielovaZlozka, ArrayList<String> menaAutorov) {
		for (int i = menaAutorov.size() - 1; i >= 0; i--) {
			String konkretnyAutor = menaAutorov.get(i).toLowerCase();
			
			if (konkretnyAutor.contains("&") || konkretnyAutor.contains("feat") || konkretnyAutor.contains("ft"))
				menaAutorov.remove(i);
		}
		
		for (int i = 0; i < menaAutorov.size(); i++) vytvorZlozku(cielovaZlozka + "\\" + menaAutorov.get(i));
	}
	
	private static void skopirujPesnickyDoCielovychZloziek(File[] listZloziekAutorov) {
		for (int i = 0; i < vsetkyPesnicky.size(); i++) {
			for (int j = 0; j < listZloziekAutorov.length; j++) {
				
				if (vsetkyPesnicky.get(i).getName().contains(listZloziekAutorov[j].getName())) {
					File suborNaSkopirovanie = new File(vsetkyPesnicky.get(i).getAbsolutePath());
					File kamSkopirovat = new File(listZloziekAutorov[j].getAbsolutePath() + "\\" + vsetkyPesnicky.get(i).getName());
					
					try {
						System.out.println("Kopirujem... " + vsetkyPesnicky.get(i).getName());
						FileUtils.copyFile(suborNaSkopirovanie, kamSkopirovat);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		}
		log_info_label.setText("Done Sorting and Copying!");
	}
	
	private static void vytvorCieloveZlozky(String cielovaZlozka, ArrayList<String> menaAutorov) {
		for (int i = vsetkyPesnicky.size() - 1; i >= 0; i--) {
			if (vsetkyPesnicky.get(i).getName().contains(".mp3")) {
				String menoPesnicky = vsetkyPesnicky.get(i).getName();
				String menoAutora = "";
				
				Scanner scan = new Scanner(menoPesnicky);
				scan.useDelimiter("-");
				if (scan.hasNext()) menoAutora += scan.next();
				scan.close();
				
				menaAutorov.add(menoAutora);
			}
		}
		vyhodZbytocnePriecinky(cielovaZlozka, menaAutorov);
	}
	
	private static void initJLables() {
		/*JLabel 0*/
		lblEnterTheAbsolute = new JLabel("Select the directory with your unsorted music");
		lblEnterTheAbsolute.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblEnterTheAbsolute.setBounds(9, 53, 298, 19);
		frmAuthsorter.getContentPane().add(lblEnterTheAbsolute);
		
		/*JTextFiled Absolute path Sorted*/
		lblAbsolutePathOf = new JLabel("Where to save the folder with sorted music");
		lblAbsolutePathOf.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblAbsolutePathOf.setBounds(9, 106, 323, 19);
		frmAuthsorter.getContentPane().add(lblAbsolutePathOf);
		
		lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(MusicSorter.class.getResource("/com/root/musicsorter/root-logo_small.png")));
		lblNewLabel.setBounds(340, 174, 89, 25);
		frmAuthsorter.getContentPane().add(lblNewLabel);
		
		lblDevelopedBy = new JLabel("Developed by");
		lblDevelopedBy.setBounds(267, 182, 89, 14);
		frmAuthsorter.getContentPane().add(lblDevelopedBy);
		
		label = new JLabel("");
		label.setIcon(new ImageIcon(MusicSorter.class.getResource("/com/root/musicsorter/authsorter-logo0.png")));
		label.setBounds(166, 8, 167, 34);
		frmAuthsorter.getContentPane().add(label);
		
		JLabel lblVisitOurWebsite = new JLabel("Visit our ~ website ~");
		lblVisitOurWebsite.setBounds(12, 182, 124, 14);
		frmAuthsorter.getContentPane().add(lblVisitOurWebsite);
		
		log_info_label = new JLabel("");
		log_info_label.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		log_info_label.setBounds(12, 157, 184, 14);
		frmAuthsorter.getContentPane().add(log_info_label);
	}
	
	private static void initJTextFields() {
		/*JTextFiled Absolute path Unsorted*/
		absPathUnsortedText = new JTextField();
		absPathUnsortedText.setBounds(9, 76, 409, 19);
		frmAuthsorter.getContentPane().add(absPathUnsortedText);
		absPathUnsortedText.setColumns(10);
		
		/*JTextFiled Absolute path Sorted*/
		absPathSortedText = new JTextField();
		absPathSortedText.setColumns(10);
		absPathSortedText.setBounds(8, 129, 324, 19);
		frmAuthsorter.getContentPane().add(absPathSortedText);
	}
	
	private static void initJButtons() {
		/*JButton Start Sorting*/
		btnStartSorting = new JButton("Begin Sorting");
		btnStartSorting.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (absPathSortedText.getText() != "")
					JOptionPane.showMessageDialog(popupFrame, "Authsorter is creating directories and copying your music."
							+ "\nThis may take some time.");
				
				log_info_label.setText("Enter a valid path!");
				zlozkaHudbaNeutriedena = absPathUnsortedText.getText();
				cielovaZlozka = absPathSortedText.getText();
				
				vytvorZlozku(cielovaZlozka);
		    	vytvorPoleSoVsetkymiPesnickami(zlozkaHudbaNeutriedena, vsetkyPesnicky);
				vytvorCieloveZlozky(cielovaZlozka, menaAutorov);
				File[] listZloziekAutorov = new File(cielovaZlozka).listFiles();
				skopirujPesnickyDoCielovychZloziek(listZloziekAutorov);
			}
		});
		btnStartSorting.setBounds(344, 129, 116, 18);
		frmAuthsorter.getContentPane().add(btnStartSorting);
		
		btnOpen = new JButton("...");
		btnOpen.addActionListener(new ActionListener() {
			String sortedText = "";
			
			public void actionPerformed(ActionEvent arg0) {
				FileChooser fc = new FileChooser("Choose a file", 640, 480);
				sortedText = fc.getFileSelected();
				absPathUnsortedText.setText(sortedText);
				fc.closeWindow();
				absPathSortedText.setText(sortedText + " - Sorted");
			}
		});
		btnOpen.setBounds(429, 76, 32, 18);
		frmAuthsorter.getContentPane().add(btnOpen);
		
		JButton button = new JButton("");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(popupFrame, "Contact: rootgam3s@gmail.com\n"
						+ "Website: http://www.rootgam3s.com/\n"
						+ "Copyright: Copyright 2016 ©  Martin Gajdos, all rights reserved.");
			}
		});
		button.setIcon(new ImageIcon(MusicSorter.class.getResource("/com/root/musicsorter/info.gif")));
		button.setBounds(435, 175, 25, 23);
		frmAuthsorter.getContentPane().add(button);
		
		lblFirstRelease = new JLabel("First Release 1.2.0");
		lblFirstRelease.setFont(new Font("Tahoma", Font.ITALIC, 11));
		lblFirstRelease.setBounds(10, 8, 98, 14);
		frmAuthsorter.getContentPane().add(lblFirstRelease);
	}
}
