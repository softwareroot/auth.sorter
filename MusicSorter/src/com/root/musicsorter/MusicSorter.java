package com.root.musicsorter;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JFrame;

import org.apache.commons.io.FileUtils;

public class MusicSorter {
	
	private static JFrame frame;
	private static ArrayList<File> vsetkyPesnicky = new ArrayList<File>();
	
	public static void main(String[] args) {
		//initJFrame("Music Sorter v.Alpha 0.0001", 420, 260);
		
		Scanner scanner = new Scanner(System.in);
		System.out.println("Zadaj uplnu cestu k suboru s hudbou:");
		String zlozkaHudbaNeutriedena = scanner.nextLine();
		
		File[] listZloziek = new File(zlozkaHudbaNeutriedena).listFiles();
		ArrayList<String> menaAutorov = new ArrayList<String>();
		
		System.out.println("Zadaj uplnu cestu pre vytvorenie zlozky s utriedenou hudbou:");
		String cielovaZlozka = scanner.nextLine();
		scanner.close();
		
		vytvorZlozku(cielovaZlozka);
		vytvorPoleSoVsetkymiPesnickami(zlozkaHudbaNeutriedena, vsetkyPesnicky);
		vytvorCieloveZlozky(cielovaZlozka, menaAutorov);
		
		File[] listZloziekAutorov = new File(cielovaZlozka).listFiles();	
		skopirujPesnickyDoCielovychZloziek(listZloziekAutorov);
		System.out.println("Kopirovanie hudby skoncilo.");
	}
	
	private static void initJFrame(String nazov, int sirka, int vyska) {
		frame = new JFrame(nazov);
		frame.setPreferredSize(new Dimension(sirka, vyska));
		frame.setMinimumSize(new Dimension(sirka, vyska));
		frame.setMaximumSize(new Dimension(sirka, vyska));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setVisible(true);
		
		frame.setLocationRelativeTo(null);
		frame.pack();
	}
	
	private static void vytvorZlozku(String cesta) {
		File dir = new File(cesta);
		dir.mkdir();
	}
	
	public static void vytvorPoleSoVsetkymiPesnickami(String nazovSuboru, ArrayList<File> pesnicky) {
	    File directory = new File(nazovSuboru);

	    File[] fList = directory.listFiles();
	    
	    for (File file : fList) {
	        if (file.isFile()) {
	        	pesnicky.add(file);
	        } else if (file.isDirectory()) {
	        	vytvorPoleSoVsetkymiPesnickami(file.getAbsolutePath(), pesnicky);
	        }
	    }
	}
	
	private static void vytvorCieloveZlozky(String cielovaZlozka, ArrayList<String> menaAutorov) {
		
		for (int i = 0; i < vsetkyPesnicky.size(); i++) {
			String menoPesnicky = vsetkyPesnicky.get(i).getName();
			String menoAutora = "";
			
			// Zoskenuj meno autora z nazvu pesnicky
			Scanner scan = new Scanner(menoPesnicky);
			scan.useDelimiter("-");
			if (scan.hasNext()) menoAutora += scan.next();
			scan.close();
			
			// Pridaj meno autora do listu mien autorov
			menaAutorov.add(menoAutora);
			// Vytvor priecinok pre autora
			File zlozkaAutora = new File(cielovaZlozka + "\\" + menoAutora);
			zlozkaAutora.mkdir();
		}
	}
	
	private static void skopirujPesnickyDoCielovychZloziek(File[] listZloziekAutorov) {
		for (int i = 0; i < vsetkyPesnicky.size(); i++) {
			for (int j = 0; j < listZloziekAutorov.length; j++) {
				
				if (vsetkyPesnicky.get(i).getName().contains(listZloziekAutorov[j].getName())) {
					
					File fileToCopy = new File(vsetkyPesnicky.get(i).getAbsolutePath());
					File copyTo = new File(listZloziekAutorov[j].getAbsolutePath() + "\\" + vsetkyPesnicky.get(i).getName());
					
					try {
						System.out.println("Kopirujem... " + vsetkyPesnicky.get(i).getName());
						FileUtils.copyFile(fileToCopy, copyTo);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		}
	}
}
