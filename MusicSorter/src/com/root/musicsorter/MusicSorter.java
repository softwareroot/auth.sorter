package com.root.musicsorter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFrame;

import org.apache.commons.io.FileUtils;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JProgressBar;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JTextPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MusicSorter {
	
	private static ArrayList<File> vsetkyPesnicky = new ArrayList<File>();
	private static ArrayList<String> menaAutorov = new ArrayList<String>();
	private static Scanner scanner = new Scanner(System.in);
	
	private static String zlozkaHudbaNeutriedena;
	private static String cielovaZlozka;
	
	/*******************************FRAME VARIABLES***********************************/
	private static JFrame frame;
	
	private static JTextPane textPanelLog;
	
	private static JTextField absPathUnsortedText;
	private static JTextField absPathSortedText;
	
	private static JLabel lblEnterTheAbsolute;
	private static JLabel lblAbsolutePathOf;
	private static JLabel lblLog;
	private static JButton btnStartSorting;
	
	private static JProgressBar progressBarSorting;
	
	private static void initJFrame(String title, int width, int height) {
		frame = new JFrame(title);
		frame.setSize(600, 240);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setLayout(null);
		
		initJLables();
		initJTextFields();
		initJButtons();
		initProgressBar();
		initLogPanel();
		
		frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		initJFrame("Music Sorter", 600, 322);
		
		/*
		String cielovaZlozka = "";
		
		boolean spravnaCesta = false;
		while (!spravnaCesta) {
		    try {
		    	System.out.println("Zadaj uplnu cestu k suboru s hudbou:");
				String zlozkaHudbaNeutriedena = scanner.nextLine();
				
				System.out.println("Zadaj uplnu cestu pre vytvorenie zlozky s utriedenou hudbou:");
				cielovaZlozka = scanner.nextLine();
				
				vytvorZlozku(cielovaZlozka);
		    	vytvorPoleSoVsetkymiPesnickami(zlozkaHudbaNeutriedena, vsetkyPesnicky);
				vytvorCieloveZlozky(cielovaZlozka, menaAutorov);
		    	spravnaCesta = true;
		    } catch (NullPointerException e) {
		    	spravnaCesta = false;
		    	System.out.println("Zadana cesta k suboru s hudbou neexistuje!");
		    }
		}
		
		scanner.close();
		
		File[] listZloziekAutorov = new File(cielovaZlozka).listFiles();
		skopirujPesnickyDoCielovychZloziek(listZloziekAutorov); */
		System.out.println("Kopirovanie hudby skoncilo.");
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
						textPanelLog.setText("Kopirujem... " + vsetkyPesnicky.get(i).getName() + "\n");
						FileUtils.copyFile(suborNaSkopirovanie, kamSkopirovat);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		}
	}
	
	private static void initJLables() {
		/*JLabel 0*/
		lblEnterTheAbsolute = new JLabel("Absolute path of the music folder");
		lblEnterTheAbsolute.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblEnterTheAbsolute.setBounds(10, 11, 222, 19);
		frame.getContentPane().add(lblEnterTheAbsolute);
		
		/*JTextFiled Absolute path Sorted*/
		lblAbsolutePathOf = new JLabel("Absolute path of the sorted music");
		lblAbsolutePathOf.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblAbsolutePathOf.setBounds(10, 74, 233, 19);
		frame.getContentPane().add(lblAbsolutePathOf);
	}
	
	private static void initJTextFields() {
		/*JTextFiled Absolute path Unsorted*/
		absPathUnsortedText = new JTextField();
		absPathUnsortedText.setBounds(10, 31, 320, 20);
		frame.getContentPane().add(absPathUnsortedText);
		absPathUnsortedText.setColumns(10);
		
		/*JTextFiled Absolute path Sorted*/
		absPathSortedText = new JTextField();
		absPathSortedText.setColumns(10);
		absPathSortedText.setBounds(10, 93, 320, 20);
		frame.getContentPane().add(absPathSortedText);
	}
	
	private static void initJButtons() {
		
		/*JButton Start Sorting*/
		btnStartSorting = new JButton("Start Sorting");
		btnStartSorting.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				zlozkaHudbaNeutriedena = absPathUnsortedText.getText();
				cielovaZlozka = absPathSortedText.getText();
				
				vytvorZlozku(cielovaZlozka);
		    	vytvorPoleSoVsetkymiPesnickami(zlozkaHudbaNeutriedena, vsetkyPesnicky);
				vytvorCieloveZlozky(cielovaZlozka, menaAutorov);
				File[] listZloziekAutorov = new File(cielovaZlozka).listFiles();
				skopirujPesnickyDoCielovychZloziek(listZloziekAutorov);
			}
		});
		btnStartSorting.setBounds(10, 146, 109, 23);
		frame.getContentPane().add(btnStartSorting);
	}
	
	private static void initProgressBar() {
		progressBarSorting = new JProgressBar();
		progressBarSorting.setBounds(129, 146, 201, 24);
		frame.getContentPane().add(progressBarSorting);
	}
	
	private static void initLogPanel() {
		textPanelLog = new JTextPane();
		textPanelLog.setBounds(340, 31, 244, 138);
		frame.getContentPane().add(textPanelLog);
		
		lblLog = new JLabel("Log");
		lblLog.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblLog.setBounds(340, 12, 30, 19);
		frame.getContentPane().add(lblLog);
	}
}
