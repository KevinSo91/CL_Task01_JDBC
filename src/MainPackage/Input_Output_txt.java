package mainPackage;

import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Random;

public class Input_Output_txt {
	
	public static void erzeugeTestdaten_txtDatei(File datei, int anzahlZeilen) {
		
		
		try {
			//FileWriter writer = new FileWriter(datei, /*append:*/true); // -> Datei erweitern
			FileWriter writer = new FileWriter(datei);    // -> Datei überschreiben
			BufferedWriter bufferedWriter = new BufferedWriter(writer);
			// Schreibe in jede Zeile eine Person mit zufälligem Alter zwischen 18(inklusiv) und 68(exklusiv)
			for(int i = 0; i < anzahlZeilen; i++) {
				// Zufälliges Alter erzeugen
				Random random = new Random();
				int zufallAlter = random.nextInt(68-18)+18; // (max(exklusiv)-min(inklusiv))-min
				String zeile = "Vorname" + i  + " Nachname" + i + " " + zufallAlter + "\n";
				bufferedWriter.write(zeile);
			}
			bufferedWriter.close();
			writer.close();
			
		} catch (IOException e) {
			System.out.println("Es ist ein Fehler bei der Erzeugung der Testdatei aufgetreten");
			e.printStackTrace();
		}
		
	}// ENDE Methode erzeugeTestdaten_txtDatei()
	

}// ENDE Klasse Input_Output_txt