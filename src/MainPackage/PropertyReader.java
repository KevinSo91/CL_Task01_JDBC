package mainPackage;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyReader {
	
	private String dbDefaultUrl;
	private String dbDefaultUser;
	private String dbDefaultPassword;
	
	private String dbTestPersonenUrl;
	private String dbTestPersonenUser;
	private String dbTestPersonenPassword;
	
	private String dbPersonenUrl;
	private String dbPersonenUser;
	private String dbPersonenPassword;
	
	private String defaultIO_txt;
	private String defaultIO_json;
	private String defaultIO_xlsx;
	private String defaultIO_csv;
	
	
	public PropertyReader() {
		try (InputStream input = new FileInputStream("C:\\Users\\user1\\eclipse-workspace\\Task01_JDBC\\src\\config\\config.properties")){
			
			Properties prop = new Properties();
			
			//Lade Property Datei
			prop.load(input);
			
			this.dbDefaultUrl=prop.getProperty("dbDefult.url");
			this.dbDefaultUser=prop.getProperty("dbDefault.user");
			this.dbDefaultPassword=prop.getProperty("dbDefault.password");
			
			this.dbTestPersonenUrl = prop.getProperty("dbTestPersonen.url");
			this.dbTestPersonenUser = prop.getProperty("dbTestPersonen.user");
			this.dbTestPersonenPassword = prop.getProperty("dbTestPersonen.password");
			
			this.dbPersonenUrl = prop.getProperty("dbPersonen.url");
			this.dbPersonenUser = prop.getProperty("dbPersonen.user");
			this.dbPersonenPassword = prop.getProperty("dbPersonen.password");
			
			this.defaultIO_txt = prop.getProperty("DefaultIOPath.txt");
			this.defaultIO_json = prop.getProperty("DefaultIOPath.json");
			this.defaultIO_xlsx = prop.getProperty("DefaultIOPath.xlsx");
			this.defaultIO_csv = prop.getProperty("DefaultIOPath.csv");
			
		}catch (IOException ex) {
			ex.printStackTrace();
		}
		
		
	}
	
	public String getDB_URL_Defult_DB() {
		return this.dbDefaultUrl;
	}
	public String getDB_USER_Default_DB() {
		return this.dbDefaultUser;
	}
	public String getDB_PASS_Default_DB() {
		return this.dbDefaultPassword;
	}
	
	public String getDB_URL_testpersonen_DB() {
		return this.dbTestPersonenUrl;
	}
	public String getDB_USER_testpersonen_DB() {
		return this.dbTestPersonenUser;
	}
	public String getDB_PASS_testpersonen_DB() {
		return this.dbTestPersonenPassword;
	}
	
	public String getDB_URL_java_connection_DB() {
		return this.dbPersonenUrl;
	}
	public String getDB_USER_java_connection_DB() {
		return this.dbPersonenUser;
	}
	public String getDB_PASS_java_connection_DB() {
		return this.dbPersonenPassword;
	}
	
	public String getDefaultIO_txt() {
		return this.defaultIO_txt;
	}
	public String getDefaultIO_json() {
		return this.defaultIO_json;
	}
	public String getDefaultIO_xlsx() {
		return this.defaultIO_xlsx;
	}
	public String getDefaultIO_csv() {
		return this.defaultIO_csv;
	}

}
