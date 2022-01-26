package mainPackage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.io.BufferedReader;
import java.util.ArrayList;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.text.*;



public class MAIN {	
	
	// main Methode
	public static void main(String[] args) throws FileNotFoundException, IOException, InvalidFormatException, SQLException {
		
		System.out.println("\nDas Programm startet...\n");
		
		// Erstelle ein Objekt für eine Verbindung mit einer Datenbank
		Datenbank PostgreJavaConn = new Datenbank(); // Leerer Konstruktor -> default Datenbank (Siehe Klasse 'Datenbank')
		
		// Lösche alle Daten aus der Tabelle 'personen'
		PostgreJavaConn.fuehre1StatementAus("DELETE FROM personen");
		
		// Erzeuge Testdaten zum einlesen
		File inputTestdaten = new File("C:\\Users\\user1\\eclipse-workspace\\Task01_JDBC\\src\\input_output_Text", "INPUT_Testdaten_Personen.txt");				
		TextDatei.erzeugeTestdatenTxtDatei(inputTestdaten, 10);		
		
		// ArrayList für die Objekten erstellen		
		ArrayList<Person> listePersonen = new ArrayList<Person>();
		
		// Methode zum schreiben der Daten als Objekte in ArrayList
		TextDatei.schreibePersonenInObjekte(inputTestdaten, listePersonen);
		
		listePersonen.forEach((person) -> person.infoAusgeben());
		
		
		
//		// Erstelle eine ArrayList mit den Statements zum einfügen der Objekte in die DB
//		int i_id = 1;
//		ArrayList<String> statements_DB_fuellen = new ArrayList<String>();
//		for (Person person : listePersonen) {
//			StringBuilder sb = new StringBuilder("INSERT INTO personen(p_id, p_vorname, p_nachname, p_alter) VALUES");
//			sb.append(MessageFormat.format("({0}, {1}, {2}, {3})", 
//					i_id, Datenbank.sqlString(person.getVorname()), Datenbank.sqlString(person.getNachname()), person.getAlter()));			
//			String statement = sb.toString();			
//			statements_DB_fuellen.add(statement);
//			i_id++;
//		}
//		
//		// Führe die Statements aus
//		PostgreJavaConn.fuehreXStatementsAus(statements_DB_fuellen);
//		
//		// Konsolenausgabe aller Daten in der Tabelle 'personen'
//		System.out.println("Ausgabe aller Daten in der Tabelle 'personen':\n");
//		PostgreJavaConn.ausgabeTabelle("personen");
//		
//		// 
//		ExcelDatei.schreibePersonenInExcel(listePersonen, 
//				"C:\\Users\\user1\\eclipse-workspace\\Task01_JDBC\\src\\input_output_Excel\\OUTPUT_Mappe_Personen.xlsx", "Tabelle1");
//		
//		ExcelDatei.erzeugeTestdaten_xlsxDatei("C:\\Users\\user1\\eclipse-workspace\\Task01_JDBC\\src\\input_output_Excel\\INPUT_Testdaten_Personen.xlsx",
//				50);		
//		
//		ExcelDatei.lesePersonenAusExcel("C:\\Users\\user1\\eclipse-workspace\\Task01_JDBC\\src\\input_output_Excel\\INPUT_Testdaten_Personen.xlsx",
//				"Tabelle1", listePersonen);
//		
//		listePersonen.forEach((person) -> person.infoAusgeben());
//		
//		PostgreJavaConn.ausgabePersonenMitAlterX(20);
	
		System.out.println("\nDas Programm wird beendet...");
		
	}// ENDE Methode main()
	
}// ENDE Klasse Main
