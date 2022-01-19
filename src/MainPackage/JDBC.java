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
import java.text.*;



public class JDBC {
	
	// JDBC driver Name und Datenbank URL
	//static final String JDBC_Driver = "org.postgresql.Driver";
	static final String DB_URL = "jdbc:postgresql://localhost:5432/java_connection_DB";

	// Datenbank Zugangsdaten
	static final String DB_USER = "postgres";
	static final String DB_PASS = "passwort";
	
	static final String pattern = "000";
	static final DecimalFormat zahlenFormat = new DecimalFormat(pattern);	
	
	// main Methode
	public static void main(String[] args) {
		
		System.out.println("\nDas Programm startet...\n");
		
		// Lösche alle Daten aus 'personen'
		fuehre1StatementAus("DELETE FROM personen");
		
		// Erzeuge Testdaten
		File testdaten = new File("C:\\Users\\user1\\eclipse-workspace\\Task01_JDBC\\src\\textdateien", "testdaten.txt");				
		erzeugeTestdaten_txtDatei(testdaten, 500);		
		
		// erstelle Scanner Objekt
		Scanner scan = null;
		
		// lese Datei 'testdaten'(Objekt 'testdaten') ein
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
		System.out.println("Ausgabe der Objekte in 'listePersonen':\n");
		listePersonen.forEach((person) -> person.infoAusgeben());
		
		// Erstelle eine ArrayList mit den Statements zum einfügen der Objekte in die DB
		int i_id = 1;
		ArrayList<String> statements = new ArrayList<String>();
		for (Person person : listePersonen) {
			StringBuilder sb = new StringBuilder("INSERT INTO personen(p_id, p_vorname, p_nachname, p_alter) VALUES");
			sb.append(MessageFormat.format("({0}, {1}, {2}, {3})", i_id, sqlString(person.getVorname()), sqlString(person.getNachname()), person.getAlter()));			
			String statement = sb.toString();
			//String statement = MessageFormat.format("INSERT INTO personen(p_id, p_vorname, p_nachname, p_alter) VALUES({0},\'{1}\',\'{2}\',{3})",i_id, person.getVorname(), person.getNachname(), person.getAlter());
			statements.add(statement);
			i_id++;
		}
		// Führe die Statements aus
		fuehreXStatementsAus(statements);
		
		// Konsolenausgabe aller Daten in der Tabelle 'personen'
		System.out.println("Ausgabe aller Daten in der Tabelle 'personen':\n");
		ausgabeTabelle("personen");
		
		
		
		System.out.println("\nDas Programm wird beendet...");
		
	}// ENDE Methode main()
					
	
		
	public static void erzeugeTestdaten_txtDatei(File datei, int anzahlZeilen) {
		
		
		try {
			//FileWriter writer = new FileWriter(datei, /*append:*/true); // -> Datei erweitern
			FileWriter writer = new FileWriter(datei);    // -> Datei überschreiben
			// Schreibe in jede Zeile eine Person mit zufälligem Alter zwischen 18(inklusiv) und 68(exklusiv)
			for(int i = 0; i < anzahlZeilen; i++) {
				// Zufälliges Alter erzeugen
				Random random = new Random();
				int zufallAlter = random.nextInt(68-18)+18; // (max(exklusiv)-min(inklusiv))-min
				writer.write("Vorname" + i  + " Nachname" + i + " " + zufallAlter + "\n");
			}
			writer.flush();
			writer.close();
		} catch (IOException e) {
			System.out.println("Fehlerhafte Eingabe");
			e.printStackTrace();
		}
		
	}// ENDE Methode erzeugeTestdaten_txtDatei()

	public static void fuehre1StatementAus(String sqlBefehl) {
		Connection conn = null;
		Statement stmt = null;
		
		try {
			// 1. Verbindung herstellen
			System.out.println("\nVerbinde mit Datenbank...");
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
			System.out.println("\nVerbindung mit Datenbank wurde getrennt!\n");
						
			
		} catch(Exception e) {
			System.out.println("Es ist ein Fehler aufgetreten");
		}
		
	}// ENDE Methode fuehre1StatementAus()
	
	public static void fuehreXStatementsAus(ArrayList<String> befehle) {
		Connection conn = null;
		Statement stmt = null;
		
		try{
			// 1. Verbindung herstellen
			System.out.println("\nVerbinde mit Datenbank...");
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
			System.out.println("Erfolgreich mit Datenbank verbunden!\n");
			
			// 2. Führe Statement aus
			System.out.println("Fuehre SQL Statements aus...");
			for (String befehl : befehle) {
				System.out.println(befehl);
				stmt = conn.createStatement();				
				stmt.execute(befehl);
				System.out.println("Statement erfolgreich ausgefuehrt!");				
			}
			
			// 3. Verbindung trennen
			stmt.close();
			conn.close();
			System.out.println("\nVerbindung mit Datenbank getrennt!\n");
			
		} catch(Exception e) {
			System.out.println("Es ist ein Fehler aufgetreten");
		}
			
	}// ENDE Methode fuehreXStatementsAus() 
	
	public static void ausgabeTabelle(String tabelle) {
		Connection conn = null;
		Statement stmt = null;
		
		try{
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM " + tabelle + " ORDER BY p_alter"); // Sortierung nach Alter
			while (rs.next()) {
				int id = rs.getInt("p_id");
				String vorname = rs.getString("p_vorname");
				String nachname = rs.getString("p_nachname");
				int alter = rs.getInt("p_alter");
				System.out.println("ID: "+ zahlenFormat.format(id) + " | Vorname: " + vorname + " | Nachname: " + nachname + " | Alter: " + alter);
				System.out.println();
			}
			rs.close();
			stmt.close();
			conn.close();
			
		} catch (Exception e) {
			System.err.println( e.getClass().getName()+": "+ e.getMessage() );
	         System.exit(0);
		}
		
	}// ENDE Methode ausgabeTabelle()
	
	public static String sqlString(String eingabe)
	{
		StringBuilder sb = new StringBuilder(eingabe);
		sb.insert(0, "'");
		sb.insert(eingabe.length()+1, "'");		
		
		return sb.toString();
	}
	
}// ENDE Klasse 'main'
