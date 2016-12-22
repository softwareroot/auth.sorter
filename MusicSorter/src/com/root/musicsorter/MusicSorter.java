package com.root.musicsorter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;

public class MusicSorter {
	
	private static ArrayList<File> vsetkyPesnicky = new ArrayList<File>();
	private static ArrayList<String> menaAutorov = new ArrayList<String>();
	private static Scanner scanner = new Scanner(System.in);
	
	public static void main(String[] args) {
		System.out.println("Zadaj uplnu cestu k suboru s hudbou:");
		String zlozkaHudbaNeutriedena = scanner.nextLine();
		
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
	
	private static void vytvorZlozku(String cesta) {
		File dir = new File(cesta);
		dir.mkdir();
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
	}
	
	private static void vytvorCieloveZlozky(String cielovaZlozka, ArrayList<String> menaAutorov) {
		for (int i = 0; i < vsetkyPesnicky.size(); i++) {
			String menoPesnicky = vsetkyPesnicky.get(i).getName();
			String menoAutora = "";
			
			Scanner scan = new Scanner(menoPesnicky);
			scan.useDelimiter("-");
			if (scan.hasNext()) menoAutora += scan.next();
			scan.close();
			
			menaAutorov.add(menoAutora);
			vytvorZlozku(cielovaZlozka + "\\" + menoAutora);
		}
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
}
