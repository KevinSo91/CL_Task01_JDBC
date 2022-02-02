package mainPackage;

import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;


public class JsonDatei {
	
	//******************************************** Attribute ************************************************
	
	private final String fileLocation;
	private final String fileName;
	//private final String fileLocationAndName;
	private final File jsonFile;
	
	//***************************************** Konstruktoren ***********************************************
	
	public JsonDatei(String location, String name) {
		this.fileLocation = location;
		this.fileName = name + ".json";
		//this.fileLocationAndName = fileLocation + "\\" + fileName;
		this.jsonFile = new File(this.fileLocation, this.fileName);
	}
	
	//******************************************* Methoden ***************************************************
	
	public File getJsonFile() {
		return this.jsonFile;
	}
	
	
	public void schreibeTestPersonenInObjekte(ArrayList<TestPerson> outputArrayList) throws UnsupportedEncodingException, IOException {
		
		System.out.println("\nSchreibe Test-Personen in Objekte...\n");
		
		String Inhalt = new String(Files.readAllBytes(Paths.get(jsonFile.toURI())), "UTF-8");
		
		JSONObject jsonObjectPersonen = new JSONObject(Inhalt);		
		System.out.println("\nJSON-Objekt erstellt!\n");
		
		JSONArray jsonArrayPersonen = jsonObjectPersonen.getJSONArray("testpersonen");
		
		for(int i = 0; i < jsonArrayPersonen.length(); i++) {
			
			JSONObject jsonObjectPerson = jsonArrayPersonen.getJSONObject(i);
			int seq = jsonObjectPerson.getInt("seq");
			
			JSONObject jsonObjectName = jsonObjectPerson.getJSONObject("name");			// Ohne Array lösen!!!
			String firstName = jsonObjectName.getString("first");
			String lastName = jsonObjectName.getString("last");
			
			int age = jsonObjectPerson.getInt("age");
			String street = jsonObjectPerson.getString("street");
			String city = jsonObjectPerson.getString("city");
			String state = jsonObjectPerson.getString("state");
			int zip = jsonObjectPerson.getInt("zip");
			String dollar = jsonObjectPerson.getString("dollar");
			String pick = jsonObjectPerson.getString("pick");
			String date = jsonObjectPerson.getString("date");
						
			outputArrayList.add(new TestPerson(seq, firstName, lastName, age, street, city, state, zip, dollar, pick, date));
			
		}
		
		System.out.println("\nListe erfolgreich erstellt!\n");
		
		
	}
	
	
}
