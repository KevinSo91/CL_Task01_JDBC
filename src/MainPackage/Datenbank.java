package mainPackage;

import java.sql.*;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;

import org.bouncycastle.util.Properties;

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
			this.DB_URL = Properties.getPropertyValue("dbDefault.url");
			this.DB_USER = Properties.getPropertyValue("dbDefault.user");
			this.DB_PASS = Properties.getPropertyValue("dbDefault.password");
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
		
		public void schreibePersonenInDatenbank(ArrayList<Person> outputListePersonen) throws SQLException {
			
			verbindungHerstellen();
			
			System.out.println("\nSchreibe Personen in Datenbank...\n");
			
			String stmt = "INSERT INTO personen(p_id, p_vorname, p_nachname, p_alter) VALUES(?, ?, ?, ?)";
			PreparedStatement preparedStatement = conn.prepareStatement(stmt);
			int i_id = 1;
			
			for(Person person : outputListePersonen) {
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
		
		// Methode zum Übertragen der Daten aus einer Liste von Objekten in eine Datenbank (1. Normalform)
		public void schreibeTestPersonenInDatenbankNormalform1(ArrayList<TestPerson> inputListePersonen) throws SQLException, ParseException {
			
			verbindungHerstellen();
			
			System.out.println("\nSchreibe Personen in Datenbank...\n");
			
			String stmt = "INSERT INTO normalform1.table_seq(seq_id, p_first_name, p_last_name, p_age, p_street, p_city, p_state, p_zip, p_dollar, p_pick, p_date)"
					+ " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedStatement = conn.prepareStatement(stmt);
			
			for(TestPerson person : inputListePersonen) {
				preparedStatement.setInt(1, person.getSeq());
				preparedStatement.setString(2, person.getFirstName());
				preparedStatement.setString(3, person.getLastName());
				preparedStatement.setInt(4, person.getAge());
				preparedStatement.setString(5, person.getStreet());
				preparedStatement.setString(6, person.getCity());
				preparedStatement.setString(7, person.getState());
				preparedStatement.setInt(8, person.getZip());
				preparedStatement.setFloat(9, person.getDollarFloat());
				preparedStatement.setString(10, person.getPick());
				preparedStatement.setString(11, person.getDateUS());
			
				preparedStatement.executeUpdate();				
			}
			
			ResultSet rs = preparedStatement.executeQuery();
			
			System.out.println("\nPersonen erfolgreich in Datenbank geschrieben!\n");
			
			verbindungTrennen();
		}// ENDE schreibeTestPersonenInDatenbank()
		
		// Methode zum Übertragen der Daten aus einer Liste von Objekten in eine Datenbank (3. Normalform)
		public void schreibeTestPersonenInDatenbankNormalform3(ArrayList<TestPerson> inputListePersonen) throws SQLException {
			
			verbindungHerstellen();
			
			//---------------------------------------------- Tabelle: Sequenzen -----------------------------------------------------
			
				System.out.println("\nSchreibe Sequenzen (Vorgänge) in Datenbank...\n");				
				
				String stmt = "INSERT INTO normalform3.table_seq" + 		 // Schema voranstellen, Punktnotation für Tabelle
							  "(s_id, p_id, s_dollar, s_pick, s_date) VALUES(?, ?, ?, ?, ?)";
				
				PreparedStatement preparedStatement = conn.prepareStatement(stmt);
			
			try{				
				for(TestPerson person : inputListePersonen) {
					preparedStatement.setInt(1, person.getSeq());			// seq kann in DIESEM KONKRETEN FALL für p_id  übernommen werden,
					preparedStatement.setInt(2, person.getSeq());    		// da keine Person doppelt vorkommmt -> sonst z.B. über Zählervariable	
					preparedStatement.setString(3, person.getDollar());
					preparedStatement.setString(4, person.getPick());
					preparedStatement.setString(5, person.getDate());
				
					preparedStatement.executeUpdate();
				}
				
				preparedStatement.executeQuery();
				preparedStatement.close();
				
				System.out.println("\nSequenzen erfolgreich in Datenbank geschrieben!\n");
			}catch(SQLException e) {
				e.printStackTrace();
			}
			
			//---------------------------------------------- Tabelle: Personen -----------------------------------------------------
			
			System.out.println("\nSchreibe Testpersonen in Datenbank...\n");
			
			stmt = "INSERT INTO normalform3.table_persons" + 		 // Schema voranstellen, Punktnotation für Tabelle
				   "(p_id, p_first_name, p_last_name, p_age, p_street, p_zip) VALUES(?, ?, ?, ?, ?, ?)";
			
			preparedStatement = conn.prepareStatement(stmt);
			
			try{
				for(TestPerson person : inputListePersonen) {
					preparedStatement.setInt(1, person.getSeq());			// seq kann in DIESEM KONKRETEN FALL für p_id  übernommen werden,
					preparedStatement.setString(2, person.getFirstName());    		// da keine Person doppelt vorkommmt -> sonst über Zählervariable			
					preparedStatement.setString(3, person.getLastName());
					preparedStatement.setInt(4, person.getAge());
					preparedStatement.setString(5, person.getStreet());
					preparedStatement.setInt(6, person.getZip());
				
					preparedStatement.executeUpdate();
				}
				
				preparedStatement.executeQuery();
				//preparedStatement_persons.close();
			
				System.out.println("\nTestpersonen erfolgreich in Datenbank geschrieben!\n");
			}catch(SQLException e) {
				e.printStackTrace();
			}
			
			//---------------------------------------------- Tabelle: ZIP -----------------------------------------------------
			
			System.out.println("\nSchreibe zip in Datenbank...\n");
			
			stmt = "INSERT INTO normalform3.table_zip" + 		 // Schema voranstellen, Punktnotation für Tabelle
				   "(zip, z_city, z_state) VALUES(?, ?, ?)";
			
			preparedStatement = conn.prepareStatement(stmt);
			ArrayList<Integer> listeZip = new ArrayList<Integer>();
			try{
				for(TestPerson person : inputListePersonen) {
					if(!listeZip.contains(person.getZip())) {             	// Bedingung: zip wird als primary key verwendet und muss daher eindeutig sein
					preparedStatement.setInt(1, person.getZip());			// Mehrfaches Vorkommen wird in DIESEM FALL ignoriert und der Datensatz übersprungen
					preparedStatement.setString(2, person.getCity());    	// Folglich wird hier immer	die city und der state des ersten Vorkommens in der Liste übernommen
					preparedStatement.setString(3, person.getState());		// Logik: zip gehört immer eindeutig zu der selben city und state (Testdaten sind hier "fehlerhaft" vmtl. durch Zufallsgenerierung)
				
					preparedStatement.executeUpdate();
					
					listeZip.add(person.getZip());
					}
				}
				
				preparedStatement.executeQuery();
				//preparedStatement_persons.close();
			
			System.out.println("\nTestpersonen erfolgreich in Datenbank geschrieben!\n");
			}catch(SQLException e) {
				e.printStackTrace();
			}
			
			
			preparedStatement.close();			
			
			verbindungTrennen();			
		}// ENDE schreibeTestPersonenInDatenbankNormalform3()
		
		
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
		
		// Methode um TestPersonen aus Datenbank(Normalisiert) als Objekte in Liste zu schreiben
		public void schreibeTestPersonenNormalform3InObjekte(ArrayList<TestPerson> outputListeTestPersonen) throws SQLException {
			
			verbindungHerstellen();
			
			String stmt = "SELECT * FROM (normalform3.table_seq INNER JOIN normalform3.table_persons ON normalform3.table_seq.p_id = normalform3.table_persons.p_id) "
										+ "INNER JOIN normalform3.table_zip ON normalform3.table_persons.p_zip = normalform3.table_zip.zip";
			
			PreparedStatement preparedStmt = conn.prepareStatement(stmt);
			ResultSet rs = preparedStmt.executeQuery();			
					
			while(rs.next()) {
				
				int id = (rs.getInt("s_id"));
				String firstName = (rs.getString("p_first_name"));
				String lastName = (rs.getString("p_last_name"));
				int age = (rs.getInt("p_age"));
				String street = (rs.getString("p_street"));
				String city = (rs.getString("z_city"));
				String state = (rs.getString("z_state"));
				int zip = (rs.getInt("zip"));
				String dollar = (rs.getString("s_dollar"));
				String pick = (rs.getString("s_pick"));
				String date = (rs.getString("s_date"));
				
				outputListeTestPersonen.add(new TestPerson(id, firstName, lastName, age, street, city, state, zip, dollar, pick, date));
				
			}
						
			rs.close();
			preparedStmt.close();
			
			verbindungTrennen();
					
		} // ENDE schreibeTestPersonenNormalform3InObjekte()
		
		
		// Methode um einen String mit einfachen Anführungszeichen zu umgeben (benoetigt fuer SQL-Statements)
		public static String sqlString(String eingabe)
		{
			StringBuilder sb = new StringBuilder(eingabe);
			sb.insert(0, "'");
			sb.insert(eingabe.length()+1, "'");		
			
			return sb.toString();
		}

}// ENDE Klasse Datenbank
