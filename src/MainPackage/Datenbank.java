package mainPackage;

import java.sql.*;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Datenbank {
	
		//******************************************** Attribute ************************************************
	
		// Datenbank URL
		private String DB_URL;
		// Datenbank Zugangsdaten
		private String DB_USER;
		private String DB_PASS;
		
		private Connection conn = null;
		private Statement stmt = null;
		
		//***************************************** Konstruktoren ***********************************************
		
		// default Datenbank
		public Datenbank() {
			this.DB_URL = "jdbc:postgresql://localhost:5432/java_connection_DB";
			this.DB_USER = "postgres";
			this.DB_PASS = "passwort";
		}
		
		// explizite Datenbank
		public Datenbank(String DB_URL, String DB_USER, String DB_PASS) {
			this.DB_URL = DB_URL;
			this.DB_USER = DB_USER;
			this.DB_PASS = DB_PASS;
		}
		
		//******************************************* Methoden ****************************************************
		
		private void verbindung_herstellen() {
			try {
				System.out.println("\nVerbinde mit Datenbank...");
				this.conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
				System.out.println("Erfolgreich mit Datenbank verbunden!");
			} catch (SQLException e) {				
				e.printStackTrace();
			}
		}
		
		private void verbindung_trennen() {
			try {
				conn.close();
				System.out.println("\nVerbindung mit Datenbank wurde getrennt!\n");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		public void fuehre1StatementAus(String sqlBefehl) {
			
			verbindung_herstellen();
			
			try {
				System.out.println("Fuehre SQL Statement aus...");
				stmt = conn.createStatement();						
				stmt.execute(sqlBefehl);
				System.out.println("Statement erfolgreich ausgefuehrt!");
				stmt.close();
			
			} catch(Exception e) {
				System.out.println("Es ist ein Fehler aufgetreten");
			}
			
			verbindung_trennen();
			
		}// ENDE Methode fuehre1StatementAus()
		
		public void fuehreXStatementsAus(ArrayList<String> befehle) {
			
			verbindung_herstellen();
			
			try{
				System.out.println("Fuehre SQL Statements aus...");
				for (String befehl : befehle) {
					System.out.println(befehl);
					stmt = conn.createStatement();				
					stmt.execute(befehl);
					System.out.println("Statement erfolgreich ausgefuehrt!");
					stmt.close();
				}
			} catch(Exception e) {
				System.out.println("Es ist ein Fehler aufgetreten");
			}
			
			verbindung_trennen();
				
		}// ENDE Methode fuehreXStatementsAus()
		
		
		public void ausgabeTabelle(String tabelle) {
			
			final String pattern_id = "000";
			final DecimalFormat zahlenFormat = new DecimalFormat(pattern_id);	
			
			verbindung_herstellen();
			
			try{				
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
				
			} catch (Exception e) {
				System.err.println( e.getClass().getName()+": "+ e.getMessage() );
		         System.exit(0);
			}
			
			verbindung_trennen();
			
		}// ENDE Methode ausgabeTabelle()
		
		// Methode um einen String mit einfachen Anführungszeichen zu umgeben (benoetigt fuer SQL-Statements)
		public static String sqlString(String eingabe)
		{
			StringBuilder sb = new StringBuilder(eingabe);
			sb.insert(0, "'");
			sb.insert(eingabe.length()+1, "'");		
			
			return sb.toString();
		}

}// ENDE Klasse Datenbank
