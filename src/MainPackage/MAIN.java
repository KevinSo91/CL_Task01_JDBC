package mainPackage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;

import org.apache.logging.log4j.message.Message;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.postgresql.util.PSQLException;




public class Main {	
	
	// main Methode
	public static void main(String[] args) throws FileNotFoundException, IOException, InvalidFormatException, SQLException {
		
		System.out.println("\nDas Programm startet...\n");	
		
		
		// Erstelle Objekt für die Properties
		PropertyReader properties = new PropertyReader();
		
		// Erstelle ArrayList für die Testpersonen
		ArrayList<TestPerson> listeTestPersonen = new ArrayList<TestPerson>();		
		
		//Erstelle ein JSON-Objekt
		JsonDatei testPersonen = new JsonDatei(properties.getDefaultIO_json(), "INPUT_testdata");
		// Schreibe Test-Personen in Liste 'listeTestpersonen'
		testPersonen.schreibeTestPersonenInObjekte(listeTestPersonen);
		
		//************************************** Liste mit Testpersonen erstellt **********************************************************
		
		
		
		ExcelDatei.schreibeTestPersonenInExcel(listeTestPersonen, properties.getDefaultIO_xlsx(), "OUTPUT_Testpersonen", "Testpersonen");
		
		
		
		
//		CsvDatei csvInput = new CsvDatei(properties.getDefaultIO_csv(), "INPUT_Testpersonen");
//		
//		csvInput.schreibeTestPersonenInObjekte(listeTestPersonen);
//		
//		CsvDatei csvOutput = new CsvDatei(properties.getDefaultIO_csv(), "OUTPUT_Testpersonen");
//		
//		csvOutput.schreibeTestPersonenInCsvDatei(listeTestPersonen);
//		
//		listeTestPersonen.forEach((person) -> System.out.println(person.infoAusgebenString()));
		
				
//		// Erstelle ein JSON-Objekt
//		JsonDatei testPersonen = new JsonDatei(properties.getDefaultIO_json(), "INPUT_testdata");
//		// Schreibe Test-Personen in Liste 'listeTestpersonen'
//		testPersonen.schreibeTestPersonenInObjekte(listeTestPersonen);
				
		
		
//		// Übertragung in DB in 3. Normalform
//		Datenbank testpersonenDB = new Datenbank(properties.getDB_URL_testpersonen_DB(), properties.getDB_USER_testpersonen_DB(), properties.getDB_PASS_testpersonen_DB());
//		testpersonenDB.fuehre1StatementAus("DELETE FROM testpersonen_normalform3.table_seq");
//		testpersonenDB.fuehre1StatementAus("DELETE FROM testpersonen_normalform3.table_persons");
//		testpersonenDB.fuehre1StatementAus("DELETE FROM testpersonen_normalform3.table_zip");
//		
//		testpersonenDB.schreibeTestPersonenInDatenbankNormalform3(listeTestPersonen);
		
//		JsonDatei.schreibeTestPersonenInJsonDateiGsonGeordnet(listeTestPersonen, properties.getDefaultIO_json(), "OUTPUT_testdataSorted");
//		JsonDatei.schreibeTestPersonenInJsonDatei(listeTestPersonen, properties.getDefaultIO_json(), "OUTPUT_testdata");
		
			
		
		
		
		
		
		//*********************************************** ANFANG TEST ************************************************************
		
		
		
//		// Überprüfe ob zip doppelt vorkommen (Redundanzen/Anomalien vermeiden)
//		ArrayList<Integer> listeZip = new ArrayList<Integer>();
//		ArrayList<Integer> listeZipMehrfach = new ArrayList<Integer>();
//		
//		for(TestPerson person: listeTestPersonen) {		
//			int zip = person.getZip();
//			
//			if (listeZip.contains(zip)) {
//				System.out.println("Die zip " + zip + " kommt mehrfach vor");
//				listeZipMehrfach.add(zip);
//			}
//			else{
//				listeZip.add(zip);
//			}		
//		}		
//		if (listeZipMehrfach.size() == 0){
//			System.out.println("Es kommt kein zip doppelt vor");
//		}
//		else {
//			System.out.println("Es kommen " + listeZipMehrfach.size() + " zips mehrfach vor");
//		}
		
		
		
		
		//************************************************* ENDE TEST *************************************************************

		
		
//		Datenbank testPersonenDB = new Datenbank(properties.getDB_URL_testpersonen_DB(), properties.getDB_USER_testpersonen_DB(), properties.getDB_PASS_testpersonen_DB());
//		// Lösche alle Daten aus der Tabelle 'testpersonen'
//		testPersonenDB.fuehre1StatementAus("DELETE FROM testpersonen");
//		// Schreibe Test-Personen in Datenbank
//		try{
//			testPersonenDB.schreibeTestPersonenInDatenbankNormalform1(listeTestPersonen);
//		}catch(PSQLException e) {
//			e.printStackTrace();
//		}


		
				
		// Erzeuge Datei mit Testdaten
		TextDatei testdatei = new TextDatei(properties.getDefaultIO_txt(), "OUTPUT_Personen");
		testdatei.schreibeTestdaten(300);
//		
//		// ArrayList für die Objekten deklarieren		
//		ArrayList<Person> listePersonen = new ArrayList<Person>();
//		
//		// Daten aus Testdatei als Objekte in 'listePersonen' instanziieren
//		testdatei.schreibePersonenInObjekte(listePersonen);		
//				
//		// Erstelle ein Objekt für eine Verbindung zu einer Datenbank
//		Datenbank PostgreJavaConn = new Datenbank(); // Leerer Konstruktor -> default Datenbank (Siehe Klasse 'Datenbank')
//		
//		// Lösche alle Daten aus der Tabelle 'personen'
//		PostgreJavaConn.fuehre1StatementAus("DELETE FROM personen");
//		
//		// Alle Objekte aus 'listePersonen' in Datenbank einfügen
//		try{
//			PostgreJavaConn.schreibePersonenInDatenbank(listePersonen);
//		}catch(PSQLException p) {
//			p.printStackTrace();
//		}
//		
//		
//		
//		
//		listePersonen.forEach((person) -> person.infoAusgeben());
//		
//		//		
//		// Konsolenausgabe aller Daten in der Tabelle 'personen'
//		System.out.println("Ausgabe aller Daten in der Tabelle 'personen':\n");
//		PostgreJavaConn.ausgabeTabelle("personen");
//		
//		// 
//		ExcelDatei.schreibePersonenInExcel(listePersonen, 
//				properties.getDefaultIO_xlsx(), "OUTPUT_Mappe_Personen.xlsx", "Tabelle1");
//		
//		ExcelDatei.erzeugeTestdaten_xlsxDatei(properties.getDefaultIO_xlsx(),"INPUT_Testdaten_Personen.xlsx", 200);		
//		
//		ExcelDatei.lesePersonenAusExcel(properties.getDefaultIO_xlsx(),"INPUT_Testdaten_Personen.xlsx", "Tabelle1", listePersonen);
//		
//		listePersonen.forEach((person) -> person.infoAusgeben());
//		
//		PostgreJavaConn.ausgabePersonenMitAlterX(20);
//	
		
		
		System.out.println("\nDas Programm wird beendet...");
		
	}// ENDE Methode main()
	
}// ENDE Klasse Main
