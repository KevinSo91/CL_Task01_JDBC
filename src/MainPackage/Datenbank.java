package mainPackage;

import java.sql.*;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Datenbank {
	
		// Datenbank URL
		private String DB_URL;
		// Datenbank Zugangsdaten
		private String DB_USER;
		private String DB_PASS;
		
		private Connection conn = null;
		private Statement stmt = null;
		
		public Datenbank() {
			this.DB_URL = "jdbc:postgresql://localhost:5432/java_connection_DB";
			this.DB_USER = "postgres";
			this.DB_PASS = "passwort";
		}
		
		public Datenbank(String DB_URL, String DB_USER, String DB_PASS) {
			this.DB_URL = DB_URL;
			this.DB_USER = DB_USER;
			this.DB_PASS = DB_PASS;
		}
		
//		public void verbindung_herrstellen() {
//			try {
//				this.conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
//			
//			} catch (SQLException e) {				
//				e.printStackTrace();
//			}
//		}
		
			
		public void fuehre1StatementAus(String sqlBefehl) {
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
		
		public void fuehreXStatementsAus(ArrayList<String> befehle) {
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
		
		public void ausgabeTabelle(String tabelle) {
			
			final String pattern = "000";
			final DecimalFormat zahlenFormat = new DecimalFormat(pattern);	
			
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

}// ENDE Klasse DB_Verbindung
