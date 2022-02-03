package mainPackage;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyReader {
	
	private final String dbTestPersonenUrl = "";
	// Datenbank Zugangsdaten
	private final String dbTestPersonenUser = "";
	private final String dbTestPersonenPassword = "";
	
	
	public static void main(String[] args) {
		
		try (InputStream input = new FileInputStream("C:\\Users\\user1\\eclipse-workspace\\Task01_JDBC\\src\\config\\config.properties")){
			
			Properties prop = new Properties();
			
			//Lade Property Datei
			prop.load(input);
			
			System.out.println(prop.getProperty("dbTestPersonen.url"));
			System.out.println(prop.getProperty("dbTestPersonen.user"));
			System.out.println(prop.getProperty("dbTestPersonen.password"));
			
		}catch (IOException ex) {
			ex.printStackTrace();
		}
		
	}
	
	public String getDB_URL() {
		return this.dbTestPersonenUrl;
	}
	public String getDB_USER() {
		return this.dbTestPersonenUser;
	}
	public String getDB_PASS() {
		return this.dbTestPersonenPassword;
	}

}
