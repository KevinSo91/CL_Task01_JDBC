package mainPackage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.text.*;



public class MAIN {	
	
	// main Methode
	public static void main(String[] args) {
		
		System.out.println("\nDas Programm startet...\n");
		
		// Lösche alle Daten aus 'personen'
		DB_Verbindung.fuehre1StatementAus("DELETE FROM personen");
		
		// Erzeuge Testdaten
		File testdaten = new File("C:\\Users\\user1\\eclipse-workspace\\Task01_JDBC\\src\\textdateien", "testdaten.txt");				
		Input_Output_txt.erzeugeTestdaten_txtDatei(testdaten, 100);		
		
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
			sb.append(MessageFormat.format("({0}, {1}, {2}, {3})", i_id, DB_Verbindung.sqlString(person.getVorname()), DB_Verbindung.sqlString(person.getNachname()), person.getAlter()));			
			String statement = sb.toString();
			//String statement = MessageFormat.format("INSERT INTO personen(p_id, p_vorname, p_nachname, p_alter) VALUES({0},\'{1}\',\'{2}\',{3})",i_id, person.getVorname(), person.getNachname(), person.getAlter());
			statements.add(statement);
			i_id++;
		}
		// Führe die Statements aus
		DB_Verbindung.fuehreXStatementsAus(statements);
		
		// Konsolenausgabe aller Daten in der Tabelle 'personen'
		System.out.println("Ausgabe aller Daten in der Tabelle 'personen':\n");
		DB_Verbindung.ausgabeTabelle("personen");
		
		
		
		System.out.println("\nDas Programm wird beendet...");
		
	}// ENDE Methode main()
	
}// ENDE Klasse Main
