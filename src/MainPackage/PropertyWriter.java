package mainPackage;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

public class PropertyWriter {

	public static void main(String[] args) {
		
		try (OutputStream output = new FileOutputStream("C:\\Users\\user1\\eclipse-workspace\\Task01_JDBC\\src\\config\\config.properties")){
			
			Properties prop = new Properties();
			
			// Setze die Property Werte
			prop.setProperty("dbPersonen.url", "jdbc:postgresql://localhost:5432/java_connection_DB");
			prop.setProperty("dbPersonen.user", "postgres");
			prop.setProperty("dbPersonen.password", "passwort");
			
			// Speichere Properties
			prop.store(output, null);
			
			System.out.println(prop);
			
			
		}catch (IOException io) {
			io.printStackTrace();
		}

	}

}
