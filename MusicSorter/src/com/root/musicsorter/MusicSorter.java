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
	
	public static void main(String[] args) {
		
		initJFrame("Music Sorter v.Alpha 0.0001", 320, 240);
		
		Scanner scanner = new Scanner(System.in);
		System.out.println("Zadaj uplnu cestu k suboru s hudbou:");
		String zlozkaHudbaNeutriedena = scanner.nextLine();
		
		File[] listZloziek = new File(zlozkaHudbaNeutriedena).listFiles();
		ArrayList<String> menaAutorov = new ArrayList<String>();
		
		System.out.println("Zadaj uplnu cestu pre vytvorenie zlozky s utriedenou kokot:");
		System.out.println("Jebem ti mater.");
		String cielovaZlozka = scanner.nextLine();
		
		scanner.close();
		
		vytvorZlozku(cielovaZlozka);
		vytvorCieloveZlozky(cielovaZlozka, listZloziek, menaAutorov);
		
		File[] listZloziekAutorov = new File(cielovaZlozka).listFiles();	
		skopirujPesnickyDoCielovychZloziek(listZloziek, listZloziekAutorov);
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
	
	private static void vytvorCieloveZlozky(String cielovaZlozka, File[] listZloziek, ArrayList<String> menaAutorov) {
		File[] listSuborov;
		for (int i = 0; i < listZloziek.length; i++) {
			if (listZloziek[i].isDirectory()) {
				listSuborov = listZloziek[i].listFiles();
				
				for (int j = 0; j < listSuborov.length; j++) {
					String menoPesnicky = listSuborov[j].getName();
					String menoAutora = "";
					
					Scanner scan = new Scanner(menoPesnicky);
					scan.useDelimiter("-");
					
					if (scan.hasNext()) {
				        menoAutora += scan.next();
				    }
					scan.close();
					
					menaAutorov.add(menoAutora);
					File zlozkaAutora = new File(cielovaZlozka + "\\" + menoAutora);
					zlozkaAutora.mkdir();
					
				}
			} else if (listZloziek[i].isFile()) {
				listSuborov = listZloziek;
				for (int j = 0; j < listSuborov.length; j++) {
					if (listSuborov[j].isDirectory())
						break;
					
					String menoPesnicky = listSuborov[j].getName();
					String menoAutora = "";
					
					Scanner scan = new Scanner(menoPesnicky);
					scan.useDelimiter("-");
					
					if (scan.hasNext()) {
				        menoAutora += scan.next();
				    }
					scan.close();
					
					menaAutorov.add(menoAutora);
					File zlozkaAutora = new File(cielovaZlozka + "\\" + menoAutora);
					zlozkaAutora.mkdir();
					
				}
			}
		}
	}
	
	private static void skopirujPesnickyDoCielovychZloziek(File[] listZloziek, File[] listZloziekAutorov) {
		File[] listPesniciek;
		
		for (int i = 0; i < listZloziek.length; i++) {
			if (listZloziek[i].isDirectory()) {
				listPesniciek = listZloziek[i].listFiles();
			}
			else {
				listPesniciek = listZloziek;
			}
			
			for (int j = 0; j < listPesniciek.length; j++) {
				for (int k = 0; k < listZloziekAutorov.length; k++) {
					
					if (listPesniciek[j].getName().contains(listZloziekAutorov[k].getName())) {
						
						File fileToCopy = new File(listPesniciek[j].getAbsolutePath());
						File copyTo = new File(listZloziekAutorov[k].getAbsolutePath() + "\\" + listPesniciek[j].getName());
						
						if (!listPesniciek[j].isDirectory()) {
							try {
								System.out.println("Kopirujem... " + listPesniciek[j].getName());
								FileUtils.copyFile(fileToCopy, copyTo);
							} catch (IOException e1) {
								e1.printStackTrace();
							}
						}
					}
				}
			}
		}
	}
}
