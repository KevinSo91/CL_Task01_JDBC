package MainPackage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.sql.*;
//import java.util.concurrent.ThreadLocalRandom;
import java.util.Random;



public class JDBC {
	
	// JDBC driver Name und Datenbank URL
	//static final String JDBC_Driver = "org.postgresql.Driver";
	static final String DB_URL = "jdbc:postgresql://localhost:5432/java_connection_DB";

	// Datenbank Zugangsdaten
	static final String DB_USER = "postgres";
	static final String DB_PASS = "passwort";
	
	
	// main Methode
	public static void main(String[] args) {
		
		System.out.println("\nDas Programm startet...\n");
		
		fuehre1StatementAus("DELETE FROM personen");
				
		//erzeugeTestdaten_txtDatei(500);												
		// ***************************************************************************************************// Testdaten erzeugen
		final String pathname = "C:\\Users\\user1\\eclipse-workspace\\Task01_JDBC\\src\\textdateien";
		final String dateiname = "Testdaten";
		
		// Erstelle eine TXT-Datei
		File testdaten = new File(pathname, dateiname + ".txt");
		try {
			//FileWriter writer = new FileWriter(testdaten, /*append:*/true);
			FileWriter writer = new FileWriter(testdaten);    // -> Datei überschreiben
			for(int i = 0; i < 200; i++) {
				// Zufälliges Alter erzeugen
				//int zufallAlter = ThreadLocalRandom.current().nextInt(18,68);
				Random random = new Random();
				int zufallAlter = random.nextInt(67-18)+18;  //(max-min)+min
				
				writer.write("Vorname" + i  + " Nachname" + i + " "+ zufallAlter + "\n");
			}
			writer.flush();
			writer.close();
		} catch (IOException e) {
			System.out.println("Fehlerhafte Eingabe");
			e.printStackTrace();
		}
		// ***************************************************************************************************// Testdaten erzeugen
		
		
		// erstelle Scanner Objekt
		Scanner scan = null;
		
		// lese Datei 'Testdaten'(Objekt 'testdaten') ein
		try {
			scan = new Scanner(testdaten);
			//scan.close();
		} catch (FileNotFoundException e) {			
			System.out.println("Datei nicht vorhanden");
		}
		
		// Array für die Objekten erstellen		
		ArrayList<Person> listePersonen = new ArrayList<Person>();				
		
		// Zählervariable für die zu erstellenden Objekte
		int i = 0;
		// erstelle für jede Zeile ein Objekt der Klasse 'Person' und füge es der Liste 'listePersonen' hinzu
		while (scan.hasNext()) {
			String vorname = scan.next();
			String nachname = scan.next();    // scan.next(String pattern) -> String auf Struktur prüfen
			int alter = scan.nextInt();
						
			listePersonen.add(new Person(vorname, nachname, alter));
			i++;
		}
		
		// Gib die Attribute der einzelnen Objekte in 'listePersonen' in der Konsole aus
		listePersonen.forEach((person) -> person.infoAusgeben());
		
		// Schreibe die Attribute der Objekte in die DB
		int i_id = 1;
		ArrayList<String> statements = new ArrayList<String>();
		for (Person person : listePersonen) {
			statements.add("INSERT INTO personen(p_id, p_vorname, p_nachname, p_alter) VALUES(" + i_id + ", '" + person.getVorname() + "', '" + person.getNachname() + "', " + person.getAlter() + ")");
			i_id++;
		}
		fuehreXStatementsAus(statements);
		
		
		
		System.out.println("\nDas Programm wird beendet...");
		
	}// Ende 'main'
					
	
		
	public static void erzeugeTestdaten_txtDatei(int anzahlZeilen) {
		
		// Gebe das Verzeichnis und den Namen der Datei an, die erstellt werden soll
		final String pathname = "C:\\Users\\user1\\eclipse-workspace\\Task01_JDBC\\src\\textdateien";
		final String dateiname = "Testdaten";
				
		// Erstelle eine TXT-Datei
		final File testdaten = new File(pathname, dateiname + ".txt");
		try {
			//FileWriter writer = new FileWriter(testdaten, /*append:*/true);
			FileWriter writer = new FileWriter(testdaten);    // -> Datei überschreiben
			// Schreibe in jede Zeile eine Person mit zufälligem Alter zwischen 18(inklusiv) und 68(exklusiv)
			for(int i = 0; i < anzahlZeilen; i++) {
				// Zufälliges Alter erzeugen
				Random random = new Random();
				int zufallAlter = random.nextInt(67-18);
				writer.write("Vorname" + i  + " Nachname" + zufallAlter + "\n");
			}
			writer.flush();
			writer.close();
		} catch (IOException e) {
			System.out.println("Fehlerhafte Eingabe");
			e.printStackTrace();
		}
				
	}// Ende 'erzeugeTestdaten_txtDatei' Methode

	public static void fuehre1StatementAus(String sqlBefehl) {
		Connection conn = null;
		Statement stmt = null;
		
		try {
			// 1. Verbindung herstellen
			System.out.println("Verbinde mit Datenbank...");
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
			System.out.println("Erfolgreich mit Datenbank verbunden!");
			
			// 2. Führe eine Statement aus
			System.out.println("Fuehre SQL Statement aus...");
			stmt = conn.createStatement();						
			stmt.execute(sqlBefehl);
			System.out.println("Statement erfolgreich ausgefuehrt!");
			
			// 3. Verbindung trennen
			stmt.close();
			conn.close();
			System.out.println("Verbindung mit Datenbank wurde getrennt!");
						
			
		} catch(Exception e) {
			System.out.println("Es ist ein Fehler aufgetreten");
		}
	}// Ende 'fuehre1StatementAus'
	
	public static void fuehreXStatementsAus(ArrayList<String> befehle) {
		Connection conn = null;
		Statement stmt = null;
		
		try{
			// 1. Verbindung herstellen
			System.out.println("Verbinde mit Datenbank...");
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
			System.out.println("Erfolgreich mit Datenbank verbunden!\n");
			
			// 2. Führe Statement aus
			System.out.println("Fuehre SQL Statement aus...");
			for (String befehl : befehle) {
				System.out.println(befehl);
				stmt = conn.createStatement();				
				stmt.execute(befehl);
				System.out.println("Statement erfolgreich ausgefuehrt!");				
			}
			
			// 3. Verbindung trennen
			stmt.close();
			conn.close();
			System.out.println("\nVerbindung mit Datenbank getrennt!");
			
		} catch(Exception e) {
			System.out.println("Es ist ein Fehler aufgetreten");
		}
			
	}// Ende 'fuehreXStatementsAus' 
	
	
}// Ende 'main' class
