package mainPackage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Random;
import java.util.ArrayList;

public class TextDatei {
	
	//******************************************** Attribute ************************************************
	
	private final String fileLocation;
	private final String fileName;
	
	
	//***************************************** Konstruktoren ***********************************************
	
	public TextDatei(String location, String name) {
		this.fileLocation = location;
		this.fileName = name;		
	}
	
	//******************************************* Methoden ****************************************************

	
	public static void erzeugeTestdatenTxtDatei(File datei, int anzahlZeilen) {
		
		
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
	
	public static void schreibePersonenInObjekte(File inputTxtDatei, ArrayList<Person> outputArrayList) {
	// lese TXT Datei ein und (Objekt 'testdaten') ein und übertrage die Daten in ein ArrayListe von Objekte
			try {
				// erstelle FileReader / Buffered Reader Objekt
				FileReader reader = new FileReader(inputTxtDatei);
				BufferedReader bufferedReader = new BufferedReader(reader);
				
				// Zählervariable für die zu erstellenden Objekte
				int i = 0;
				// erstelle für jede Zeile ein Objekt der Klasse 'Person' und füge es der Liste 'listePersonen' hinzu
				String zeile;
				while ((zeile = bufferedReader.readLine()) != null) {
					String[] datensatz = zeile.split(" ");
					String vorname = datensatz[0];
					String nachname = datensatz[1];
					int alter = Integer.parseInt(datensatz[2]);
								
					outputArrayList.add(new Person(vorname, nachname, alter));
					i++;
				}
				reader.close();
				bufferedReader.close();
				
			} catch (FileNotFoundException e) {			
				System.out.println("Datei nicht vorhanden");
			} catch (NumberFormatException e) {			
				e.printStackTrace();
			} catch (IOException e) {			
				e.printStackTrace();
			}
			
			
			// Gib die Attribute der einzelnen Objekte in 'listePersonen' in der Konsole aus
			System.out.println("Ausgabe der Objekte in 'listePersonen':\n");
			outputArrayList.forEach((person) -> person.infoAusgeben());
	
			}// ENDE schreibeDatenInObjekte()
	

}// ENDE Klasse Input_Output_txt