package mainPackage;

import java.sql.*;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Datenbank {
	
		//******************************************** Attribute ************************************************
	
		// Datenbank URL
		private final String DB_URL;
		// Datenbank Zugangsdaten
		private final String DB_USER;
		private final String DB_PASS;
		
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
		
		// Methode um mit Datenbank zu verbinden
		private void verbindungHerstellen() {
			try {
				System.out.println("\nVerbinde mit Datenbank...");
				this.conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
				System.out.println("Erfolgreich mit Datenbank verbunden!");
			} catch (SQLException e) {				
				e.printStackTrace();
			}
		}
		
		private void verbindungTrennen() {
			try {
				conn.close();
				System.out.println("\nVerbindung mit Datenbank wurde getrennt!\n");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		public void schreibePersonenInDatenbank(ArrayList<Person> listePersonen) throws SQLException {
			
			verbindungHerstellen();
			
			System.out.println("\nSchreibe Personen in Datenbank...\n");
			
			String stmt = "INSERT INTO personen(p_id, p_vorname, p_nachname, p_alter) VALUES(?, ?, ?, ?)";
			PreparedStatement preparedStatement = conn.prepareStatement(stmt);
			int i_id = 1;
			
			for(Person person : listePersonen) {
				preparedStatement.setInt(1, i_id);
				preparedStatement.setString(2, person.getVorname());
				preparedStatement.setString(3, person.getNachname());
				preparedStatement.setInt(4, person.getAlter());
			
				preparedStatement.executeUpdate();
				i_id++;
			}
			
			ResultSet rs = preparedStatement.executeQuery();
			
			System.out.println("\nPersonen erfolgreich in Datenbank geschrieben!\n");
			
			verbindungTrennen();
		}
		
		
		public void schreibeTestPersonenInDatenbank(ArrayList<TestPerson> listePersonen) throws SQLException {
			
			verbindungHerstellen();
			
			System.out.println("\nSchreibe TestPersonen in Datenbank...\n");
			
			String stmt = "INSERT INTO testpersonen(p_seq, p_first_name, p_last_name, p_age, p_street, p_city, p_state, p_zip, p_dollar, p_pick, p_date)"
					+ " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedStatement = conn.prepareStatement(stmt);
						
			for(TestPerson person : listePersonen) {
				preparedStatement.setInt(1, person.getSeq());
				preparedStatement.setString(2, person.getFirstName());
				preparedStatement.setString(3, person.getLastName());
				preparedStatement.setInt(4, person.getAge());
				preparedStatement.setString(5, person.getStreet());
				preparedStatement.setString(6, person.getCity());
				preparedStatement.setString(7, person.getState());
				preparedStatement.setInt(8, person.getZip());
				preparedStatement.setString(9, person.getDollar());
				preparedStatement.setString(10, person.getPick());
				preparedStatement.setString(11, person.getDate());
			
				preparedStatement.executeUpdate();				
			}
			
			ResultSet rs = preparedStatement.executeQuery();
			
			System.out.println("\nPersonen erfolgreich in Datenbank geschrieben!\n");
			
			verbindungTrennen();
		}
		
		
		// Methode um genau 1 Statement auszuführen
		public void fuehre1StatementAus(String sqlBefehl) {
			
			verbindungHerstellen();
			
			try {
				System.out.println("Fuehre SQL Statement aus...");
				stmt = conn.createStatement();						
				stmt.execute(sqlBefehl);
				System.out.println("Statement erfolgreich ausgefuehrt!");
				stmt.close();
			
			} catch(Exception e) {
				System.out.println("Es ist ein Fehler aufgetreten");
			}
			
			verbindungTrennen();
			
		}// ENDE Methode fuehre1StatementAus()
		
		// Methode um beliebig viele Statements (aus ArrayList) auszuführen
		public void fuehreXStatementsAus(ArrayList<String> befehle) {
			
			verbindungHerstellen();
			
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
			
			verbindungTrennen();
				
		}// ENDE Methode fuehreXStatementsAus()
		
		// Methode um Tabelle auszugeben (Sortierung nach Alter)
		public void ausgabeTabelle(String tabelle) {
			
			final String pattern_id = "000";
			final DecimalFormat zahlenFormat = new DecimalFormat(pattern_id);	
			
			verbindungHerstellen();
			
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
			
			verbindungTrennen();
						
		}// ENDE Methode ausgabeTabelle()
		
		
		// Methode um ein SELECT nach Alter auszuführen (als PreparedStatement) -> Ausgabe in Konsole
		public void ausgabePersonenMitAlterX(int gesuchtesAlter) throws SQLException {
			
			verbindungHerstellen();
			
			// Erstelle Statement mit Parametern
			String statement = "SELECT p_vorname, p_nachname FROM personen WHERE p_alter=?";
			// Erstelle PraparedStatement
			PreparedStatement preparedStatement = conn.prepareStatement(statement);
			// Setze Parameter ein
			preparedStatement.setInt(1, gesuchtesAlter);
			// Führe preparedStatement aus
			ResultSet rs = preparedStatement.executeQuery();
			
			System.out.println("\nAlle Personen mit einem Alter von " + gesuchtesAlter + " Jahren:\n");
			// Gebe Ergebnisse in Konsole aus
			while(rs.next()) {
				System.out.println(rs.getString("p_vorname") + " " + rs.getString("p_nachname"));
			}
			
			
			verbindungTrennen();
		}
		
		// Methode um einen String mit einfachen Anführungszeichen zu umgeben (benoetigt fuer SQL-Statements)
		public static String sqlString(String eingabe)
		{
			StringBuilder sb = new StringBuilder(eingabe);
			sb.insert(0, "'");
			sb.insert(eingabe.length()+1, "'");		
			
			return sb.toString();
		}

}// ENDE Klasse Datenbank
