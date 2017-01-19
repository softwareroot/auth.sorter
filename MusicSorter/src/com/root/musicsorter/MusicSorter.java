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
import javax.swing.JFileChooser;

import java.awt.BorderLayout;
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
	private static JButton btnOpen;
	
	static JFrame popupFrame;
	
	private static JFileChooser fileChooser;
	
	private static void initJFrame(String title, int width, int height) {
		frmAuthsorter = new JFrame(title);
		frmAuthsorter.setTitle("auth.sorter 1.21");
		frmAuthsorter.setIconImage(Toolkit.getDefaultToolkit().getImage(
				MusicSorter.class.getResource("/com/root/musicsorter/gallery-icon-active.png"
		)));
		frmAuthsorter.setSize(477, 282);
		frmAuthsorter.setResizable(false);
		frmAuthsorter.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmAuthsorter.setLocationRelativeTo(null);
		frmAuthsorter.getContentPane().setLayout(null);
		
		initJLables();
		initJTextFields();
		initJButtons();
		initFileChooser();
		
		popupFrame = new JFrame();
		
		frmAuthsorter.setVisible(true);
	}
	
	private static void initFileChooser() {
		fileChooser = new JFileChooser();
		frmAuthsorter.getContentPane().add(fileChooser, BorderLayout.CENTER);
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(MusicSorter.class.getResource("/com/root/musicsorter/authsorter-logo-release121.png")));
		lblNewLabel_1.setBounds(13, 10, 323, 55);
		frmAuthsorter.getContentPane().add(lblNewLabel_1);
		absPathSortedText = new JTextField();
		absPathSortedText.setColumns(10);
		absPathSortedText.setBounds(10, 161, 362, 25);
		frmAuthsorter.getContentPane().add(absPathSortedText);
		
		lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setIcon(new ImageIcon(MusicSorter.class.getResource("/com/root/musicsorter/shade1.png")));
		lblNewLabel_3.setBounds(382, 0, 103, 259);
		frmAuthsorter.getContentPane().add(lblNewLabel_3);
		
		lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(MusicSorter.class.getResource("/com/root/musicsorter/shade.png")));
		lblNewLabel_2.setBounds(0, -1, 408, 77);
		frmAuthsorter.getContentPane().add(lblNewLabel_2);
	}
	
	private static void getFileSelected() {
		if (fileChooser.showOpenDialog(fileChooser) == JFileChooser.APPROVE_OPTION) {
			//return fileChooser.getSelectedFile().getAbsolutePath();
			
			for (int i = menaAutorov.size() - 1; i >= 0; i--) {
				menaAutorov.remove(i);
			}
			
			for (int i = vsetkyPesnicky.size() - 1; i >= 0; i--) {
				vsetkyPesnicky.remove(i);
			}
			
			absPathUnsortedText.setText(fileChooser.getSelectedFile().getAbsolutePath());
			absPathSortedText.setText(fileChooser.getSelectedFile().getAbsolutePath() + " - Sorted");
			
			zlozkaHudbaNeutriedena = absPathUnsortedText.getText();//absPathUnsortedText.getText();
			cielovaZlozka = absPathSortedText.getText();
		}
	}
	
	public void closeWindow() {
		//frame.setVisible(false);
		//frame.dispose();
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
		
		btnOpen = new JButton("Browse");
		btnOpen.addActionListener(new ActionListener() {
			//String sortedText = "";
			
			public void actionPerformed(ActionEvent arg0) {
				//FileChooser fc = new FileChooser("Choose a file", 640, 480);
				//sortedText = getFileSelected();
				//absPathUnsortedText.setText(sortedText);
				//absPathSortedText.setText(sortedText + " - Sorted");
				
				getFileSelected();
				
				System.out.println(sortedText);
				//System.out.println(absPathUnsortedText.getText());
				//System.out.println(absPathSortedText.getText());
				System.out.println(fileChooser.getSelectedFile().getAbsolutePath());
			}
		});
		btnStartSorting = new JButton("Sort");
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
		
		lblInfo = new JLabel("Info");
		lblInfo.setFont(new Font("Tahoma", Font.ITALIC, 11));
		lblInfo.setBounds(417, 11, 31, 14);
		frmAuthsorter.getContentPane().add(lblInfo);
		absPathUnsortedText = new JTextField();
		absPathUnsortedText.setBounds(11, 111, 361, 25);
		frmAuthsorter.getContentPane().add(absPathUnsortedText);
		absPathUnsortedText.setColumns(10);
		btnStartSorting.setBounds(392, 161, 69, 25);
		frmAuthsorter.getContentPane().add(btnStartSorting);
		btnOpen.setBounds(392, 111, 69, 25);
		frmAuthsorter.getContentPane().add(btnOpen);
		lblEnterTheAbsolute = new JLabel("Directory with unsorted music");
		lblEnterTheAbsolute.setFont(new Font("Tahoma", Font.ITALIC, 15));
		lblEnterTheAbsolute.setBounds(10, 84, 200, 19);
		frmAuthsorter.getContentPane().add(lblEnterTheAbsolute);
		
		/*JTextFiled Absolute path Sorted*/
		lblAbsolutePathOf = new JLabel("Directory with sorted music");
		lblAbsolutePathOf.setFont(new Font("Tahoma", Font.ITALIC, 15));
		lblAbsolutePathOf.setBounds(11, 138, 189, 19);
		frmAuthsorter.getContentPane().add(lblAbsolutePathOf);
		
		lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(MusicSorter.class.getResource("/com/root/musicsorter/root-logo_small.png")));
		lblNewLabel.setBounds(383, 221, 90, 25);
		frmAuthsorter.getContentPane().add(lblNewLabel);
		
		lblDevelopedBy = new JLabel("Developed by");
		lblDevelopedBy.setBounds(308, 228, 73, 14);
		frmAuthsorter.getContentPane().add(lblDevelopedBy);
		
		JLabel lblVisitOurWebsite = new JLabel("Visit my website!");
		lblVisitOurWebsite.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		lblVisitOurWebsite.setBounds(9, 223, 104, 25);
		frmAuthsorter.getContentPane().add(lblVisitOurWebsite);
		
		log_info_label = new JLabel("");
		log_info_label.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		log_info_label.setBounds(10, 202, 177, 14);
		frmAuthsorter.getContentPane().add(log_info_label);
	}
	
	private static void initJTextFields() {
		/*JTextFiled Absolute path Unsorted*/
		
		/*JTextFiled Absolute path Sorted*/
	}
	
	private static String sortedText;
	private static JLabel lblNewLabel_1;
	private static JLabel lblNewLabel_2;
	private static JLabel lblNewLabel_3;
	private static JLabel lblInfo;
	
	private static void initJButtons() {
		/*JButton Start Sorting*/
		
		JButton button = new JButton("");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(popupFrame, "Contact: rootgam3s@gmail.com\n"
						+ "Website: http://www.rootgam3s.com/\n"
						+ "Copyright: Copyright 2016 ©  Martin Gajdos, all rights reserved.\n"
						+ "Libraries used: Commons IO");
			}
		});
		button.setIcon(new ImageIcon(MusicSorter.class.getResource("/com/root/musicsorter/info.gif")));
		button.setBounds(415, 29, 25, 23);
		frmAuthsorter.getContentPane().add(button);
	}
}
