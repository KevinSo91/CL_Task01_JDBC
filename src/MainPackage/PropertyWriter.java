package mainPackage;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

public class PropertyWriter {

	public static void main(String[] args) {
		
		try (OutputStream output = new FileOutputStream("")){
			
			Properties prop = new Properties();
			
			// Setze die Property Werte
			prop.setProperty("dbPersonen.url", "");
			prop.setProperty("dbPersonen.user", "");
			prop.setProperty("dbPersonen.password", "");
			
			// Speichere Properties
			prop.store(output, null);
			
			System.out.println(prop);
			
			
		}catch (IOException io) {
			io.printStackTrace();
		}

	}

}
