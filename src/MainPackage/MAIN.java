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
		Datenbank Postgre_java_conn = new Datenbank(); // Leerer Konstruktor -> default Datenbank (Siehe Klasse 'Datenbank')
		
		// Lösche alle Daten aus der Tabelle 'personen'
		Postgre_java_conn.fuehre1StatementAus("DELETE FROM personen");
		
		// Erzeuge Testdaten zum einlesen
		File input_Testdaten = new File("C:\\Users\\user1\\eclipse-workspace\\Task01_JDBC\\src\\input_output_Text", "INPUT_Testdaten_Personen.txt");				
		Text.erzeugeTestdatenTxtDatei(input_Testdaten, 200);		
		
		// ArrayList für die Objekten erstellen		
		ArrayList<Person> listePersonen = new ArrayList<Person>();		
		
		
		// lese Datei 'INPUT_testdaten.txt'(Objekt 'testdaten') ein und übertrage die Daten in Objekte
		try {
			// erstelle FileReader / Buffered Reader Objekt
			FileReader reader = new FileReader(input_Testdaten);
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
							
				listePersonen.add(new Person(vorname, nachname, alter));
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
		listePersonen.forEach((person) -> person.infoAusgeben());
		
		// Erstelle eine ArrayList mit den Statements zum einfügen der Objekte in die DB
		int i_id = 1;
		ArrayList<String> statements_DB_fuellen = new ArrayList<String>();
		for (Person person : listePersonen) {
			StringBuilder sb = new StringBuilder("INSERT INTO personen(p_id, p_vorname, p_nachname, p_alter) VALUES");
			sb.append(MessageFormat.format("({0}, {1}, {2}, {3})", 
					i_id, Datenbank.sqlString(person.getVorname()), Datenbank.sqlString(person.getNachname()), person.getAlter()));			
			String statement = sb.toString();			
			statements_DB_fuellen.add(statement);
			i_id++;
		}
		
		// Führe die Statements aus
		Postgre_java_conn.fuehreXStatementsAus(statements_DB_fuellen);
		
		// Konsolenausgabe aller Daten in der Tabelle 'personen'
		System.out.println("Ausgabe aller Daten in der Tabelle 'personen':\n");
		Postgre_java_conn.ausgabeTabelle("personen");
		
		// 
		Excel.schreibePersonenInExcel(listePersonen, 
				"C:\\Users\\user1\\eclipse-workspace\\Task01_JDBC\\src\\input_output_Excel\\OUTPUT_Mappe_Personen.xlsx", "Tabelle1");
		
		Excel.erzeugeTestdaten_xlsxDatei("C:\\Users\\user1\\eclipse-workspace\\Task01_JDBC\\src\\input_output_Excel\\INPUT_Testdaten_Personen.xlsx",
				50);		
		
		Excel.lesePersonenAusExcel("C:\\Users\\user1\\eclipse-workspace\\Task01_JDBC\\src\\input_output_Excel\\INPUT_Testdaten_Personen.xlsx",
				"Tabelle1", listePersonen);
		
		listePersonen.forEach((person) -> person.infoAusgeben());
		
		Postgre_java_conn.ausgabePersonenMitAlterX(20);
	
		System.out.println("\nDas Programm wird beendet...");
		
	}// ENDE Methode main()
	
}// ENDE Klasse Main
