package com.root.musicsorter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFrame;

import org.apache.commons.io.FileUtils;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;


public class MusicSorter {
	
	private static ArrayList<File> vsetkyPesnicky = new ArrayList<File>();
	private static ArrayList<String> menaAutorov = new ArrayList<String>();
	
	private static String zlozkaHudbaNeutriedena;
	private static String cielovaZlozka;
	
	/*******************************FRAME VARIABLES***********************************/
	private static JFrame frame;
	
	private static JTextField absPathUnsortedText;
	private static JTextField absPathSortedText;
	
	private static JLabel lblEnterTheAbsolute;
	private static JLabel lblAbsolutePathOf;
	private static JButton btnStartSorting;
	private static JLabel log_info_label;
	private static JButton btnNewButton;
	private static JLabel lblNewLabel;
	private static JLabel lblDevelopedBy;
	private static JLabel label;
	
	private static void initJFrame(String title, int width, int height) {
		frame = new JFrame(title);
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(
				MusicSorter.class.getResource("/com/root/musicsorter/gallery-icon-active.png"
		)));
		frame.setSize(501, 235);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setLayout(null);
		
		initJLables();
		initJTextFields();
		initJButtons();
		initProgressBar();
		
		frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		initJFrame("Authsorter 1.0", 600, 322);
		
		btnNewButton = new JButton("Add Path");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				log_info_label.setText("");
				
				if (absPathUnsortedText.getText().isEmpty()) {
					absPathUnsortedText.setText("Enter a valid path!");
				} else {
					if (!absPathUnsortedText.getText().contains("Enter a valid path!"))
						absPathSortedText.setText(absPathUnsortedText.getText() + " - Sorted");
				}
			}
		});
		btnNewButton.setBounds(369, 69, 116, 23);
		frame.getContentPane().add(btnNewButton);
		
		lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(MusicSorter.class.getResource("/com/root/musicsorter/root-logo_small.png")));
		lblNewLabel.setBounds(396, 174, 89, 25);
		frame.getContentPane().add(lblNewLabel);
		
		lblDevelopedBy = new JLabel("Developed by");
		lblDevelopedBy.setBounds(315, 181, 89, 14);
		frame.getContentPane().add(lblDevelopedBy);
		
		label = new JLabel("");
		label.setIcon(new ImageIcon(MusicSorter.class.getResource("/com/root/musicsorter/authsorter-logo0.png")));
		label.setBounds(166, 8, 167, 34);
		frame.getContentPane().add(label);
		
		JLabel lblVisitOurWebsite = new JLabel("Visit our ~ website ~");
		lblVisitOurWebsite.setBounds(12, 182, 126, 14);
		frame.getContentPane().add(lblVisitOurWebsite);
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
	
	private static void initJLables() {
		/*JLabel 0*/
		lblEnterTheAbsolute = new JLabel("Enter the path of the music folder");
		lblEnterTheAbsolute.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblEnterTheAbsolute.setBounds(10, 50, 233, 19);
		frame.getContentPane().add(lblEnterTheAbsolute);
		
		/*JTextFiled Absolute path Sorted*/
		lblAbsolutePathOf = new JLabel("Where to save the sorted music folder");
		lblAbsolutePathOf.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblAbsolutePathOf.setBounds(10, 102, 270, 19);
		frame.getContentPane().add(lblAbsolutePathOf);
	}
	
	private static void initJTextFields() {
		/*JTextFiled Absolute path Unsorted*/
		absPathUnsortedText = new JTextField();
		absPathUnsortedText.setBounds(10, 70, 349, 21);
		frame.getContentPane().add(absPathUnsortedText);
		absPathUnsortedText.setColumns(10);
		
		/*JTextFiled Absolute path Sorted*/
		absPathSortedText = new JTextField();
		absPathSortedText.setColumns(10);
		absPathSortedText.setBounds(10, 121, 349, 21);
		frame.getContentPane().add(absPathSortedText);
	}
	
	private static void initJButtons() {
		/*JButton Start Sorting*/
		btnStartSorting = new JButton("Begin Sorting");
		btnStartSorting.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
		btnStartSorting.setBounds(369, 120, 116, 23);
		frame.getContentPane().add(btnStartSorting);
	}
	
	private static void initProgressBar() {
		log_info_label = new JLabel("");
		log_info_label.setBounds(10, 152, 184, 14);
		frame.getContentPane().add(log_info_label);
	}
}
