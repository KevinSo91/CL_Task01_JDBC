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
	//private final String fileLocationAndName;
	private final File txtFile;
	
	
	//***************************************** Konstruktoren ***********************************************
	
	public TextDatei(String location, String name) {
		this.fileLocation = location;
		this.fileName = name + ".txt";
		//this.fileLocationAndName = fileLocation + "\\" + fileName;
		this.txtFile = new File(this.fileLocation, this.fileName);
	}
	
	//******************************************* Methoden ***************************************************

	
	public File getTxtFile() {
		return this.txtFile;
	}
	
	public void erzeugeTestdatenPersonen(int anzahlZeilen) {
		FileWriter fileWriter;
		BufferedWriter bufferedWriter;
		try {
			fileWriter = new FileWriter(this.txtFile);
			bufferedWriter = new BufferedWriter(fileWriter);
			for(int i = 0; i < anzahlZeilen; i++ ) {
				Random random = new Random();
				int zufallAlter = random.nextInt(68-18)+18; // (max(exklusiv)-min(inklusiv))-min
				String zeile = "Vorname" + i  + " Nachname" + i + " " + zufallAlter + "\n";
				bufferedWriter.write(zeile);
			}
			bufferedWriter.close();
			fileWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
//	public static void erzeugeTestdatenPersonenInDatei(File datei, int anzahlZeilen) {		
//		
//		try {
//			//FileWriter writer = new FileWriter(datei, /*append:*/true); // -> Datei erweitern
//			FileWriter writer = new FileWriter(datei);    // -> Datei überschreiben
//			BufferedWriter bufferedWriter = new BufferedWriter(writer);
//			// Schreibe in jede Zeile eine Person mit zufälligem Alter zwischen 18(inklusiv) und 68(exklusiv)
//			for(int i = 0; i < anzahlZeilen; i++) {
//				// Zufälliges Alter erzeugen
//				Random random = new Random();
//				int zufallAlter = random.nextInt(68-18)+18; // (max(exklusiv)-min(inklusiv))-min
//				String zeile = "Vorname" + i  + " Nachname" + i + " " + zufallAlter + "\n";
//				bufferedWriter.write(zeile);
//			}
//			bufferedWriter.close();
//			writer.close();
//			
//		} catch (IOException e) {
//			System.out.println("Es ist ein Fehler bei der Erzeugung der Testdatei aufgetreten");
//			e.printStackTrace();
//		}
//		
//	}// ENDE Methode erzeugeTestdaten_txtDatei()
	
	public void lesePersonen(ArrayList<Person> outputArrayList) {
	// lese TXT Datei ein und (Objekt 'testdaten') ein und übertrage die Daten in ein ArrayListe von Objekte
			try {
				// erstelle FileReader / Buffered Reader Objekt
				FileReader fileReader = new FileReader(this.txtFile);
				BufferedReader bufferedReader = new BufferedReader(fileReader);
				
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
				
				fileReader.close();
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
	
			}// ENDE schreibePersonenInObjekte()
	
	
	public void schreibeTestPersonen(ArrayList<TestPerson> inputListeTestPersonen) throws IOException {
		try {
			FileWriter writer = new FileWriter(this.txtFile);
			BufferedWriter buffWriter = new BufferedWriter(writer);
			
			for(TestPerson person : inputListeTestPersonen) {				
				buffWriter.write(person.infoAusgebenString() + "\n");
			}
			
			buffWriter.close();
			writer.close();			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();
		}			
	}

}// ENDE Klasse TextDatei