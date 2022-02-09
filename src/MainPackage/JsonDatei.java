package mainPackage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
//import java.io.FileNotFoundException;
//import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;


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
			
			JSONObject jsonObjectName = jsonObjectPerson.getJSONObject("name");
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
		
		
	}// ENDE schreibeTestPersonenInObjekte()
	
	public static void schreibeTestPersonenInJsonDatei(ArrayList<TestPerson> inputArrayList, String outputDateiPfad, String outputDateiName) throws IOException {
		
		// Erstelle Output-Datei (Objekt) und BufferedWriter
		File outputDatei = new File(outputDateiPfad + "\\" + outputDateiName + ".json");
		FileWriter filewriter = new FileWriter(outputDatei);
		BufferedWriter buffWriter = new BufferedWriter(filewriter);
		
		// Erstelle Array für alle Testpersonen
		JSONArray jsonArrayTestPersonen = new JSONArray();
		
		// Lese Testpersonen aus Input Liste und erstelle jeweils ein JSON-Objekt
		for(TestPerson testperson: inputArrayList) {
			
			// Erstelle JSON-Objekt für die Person
			JSONObject jsonObjectTestPerson = new JSONObject();
			
			// Füge Wertpaare zum JSON-Objekt hinzu
			jsonObjectTestPerson.put("seq", testperson.getSeq());
						
			// Erstelle JSON-Objekt für Vor- und Nachname
			JSONObject jsonObjectName = new JSONObject();
			jsonObjectName.put("first", testperson.getFirstName());			
			jsonObjectName.put("last", testperson.getLastName());			
			
			jsonObjectTestPerson.put("name", jsonObjectName);
			jsonObjectTestPerson.put("age", testperson.getAge());
			jsonObjectTestPerson.put("street", testperson.getStreet());
			jsonObjectTestPerson.put("city", testperson.getCity());
			jsonObjectTestPerson.put("state", testperson.getState());
			jsonObjectTestPerson.put("zip", testperson.getZip());
			jsonObjectTestPerson.put("dollar", testperson.getDollar());
			jsonObjectTestPerson.put("pick", testperson.getPick());
			jsonObjectTestPerson.put("date", testperson.getDate());			
			
			System.out.println(jsonObjectTestPerson);
			
			// Füge JSON-Objekt einer Person in das JSON-Array für alle Testpersonen ein
			jsonArrayTestPersonen.put(jsonObjectTestPerson);						
		}
		
		// Schreibe alle Elemente des JSON-Arrays in eine JSON-Datei. 1 Zeile = 1 Datensatz(Objekt). (Füge [] hinzu, um die Objekte als Array darzustellen)
		buffWriter.write("[\n");		
		int i = 0;		
		while(i < (jsonArrayTestPersonen.length()-1)) {
			
			buffWriter.write(jsonArrayTestPersonen.getJSONObject(i).toString() + ",\n");
			i++;
		}
		buffWriter.write(jsonArrayTestPersonen.getJSONObject(i).toString() + "\n]");
		
		buffWriter.close();	
		
		// Test: Name als JSON-Objekt
		System.out.println(jsonArrayTestPersonen.getJSONObject(0).getJSONObject("name").getString("first"));
		System.out.println(jsonArrayTestPersonen.getJSONObject(1).getJSONObject("name").getString("last"));
		
				
	}// ENDE schreibeTestPersonenInJsonDatei()
	
	public static void schreibeTestPersonenInJsonDateiGsonGeordnet(ArrayList<TestPerson> inputArrayList, String outputDateiPfad, String outputDateiName) throws IOException {
		
		// Erstelle Output-Datei (Objekt) und BufferedWriter
		File outputDatei = new File(outputDateiPfad + "\\" + outputDateiName + ".json");
		FileWriter filewriter = new FileWriter(outputDatei);
		BufferedWriter buffWriter = new BufferedWriter(filewriter);
		
		// Erstelle Array für alle Testpersonen
		JSONArray jsonArrayTestPersonen = new JSONArray();
		
		// Lese Testpersonen aus Input Liste und erstelle jeweils ein JSON-Objekt
		for(TestPerson testperson: inputArrayList) {
			
			// Erstelle JSON-Objekt für die Person
			JsonObject jsonObjectTestPerson = new JsonObject();
			
			// Füge Wertpaare zum JSON-Objekt hinzu
			jsonObjectTestPerson.addProperty("seq", testperson.getSeq());			
			
			// Erstelle JSON-Objekt für Vor- und Nachname
			JsonObject jsonObjectName = new JsonObject();
			jsonObjectName.addProperty("first", testperson.getFirstName());			
			jsonObjectName.addProperty("last", testperson.getLastName());			
			
			jsonObjectTestPerson.add("name", new Gson().toJsonTree(jsonObjectName));
			
			//jsonObjectTestPerson.add("name", jsonObjectName);
			jsonObjectTestPerson.addProperty("age", testperson.getAge());
			jsonObjectTestPerson.addProperty("street", testperson.getStreet());
			jsonObjectTestPerson.addProperty("city", testperson.getCity());
			jsonObjectTestPerson.addProperty("state", testperson.getState());
			jsonObjectTestPerson.addProperty("zip", testperson.getZip());
			jsonObjectTestPerson.addProperty("dollar", testperson.getDollar());
			jsonObjectTestPerson.addProperty("pick", testperson.getPick());
			jsonObjectTestPerson.addProperty("date", testperson.getDate());
			
			// Überprüfung in der Konsole:
			System.out.println(jsonObjectTestPerson);
			
			// Füge JSON-Objekt einer Person in das JSON-Array für alle Testpersonen ein
			jsonArrayTestPersonen.put(jsonObjectTestPerson);						
		}
		
		buffWriter.write("[\n");		
		int i = 0;		
		while(i < (jsonArrayTestPersonen.length()-1)) {
			
			buffWriter.write(jsonArrayTestPersonen.get(i).toString() + ",\n");
			i++;
		}
		buffWriter.write(jsonArrayTestPersonen.get(i).toString() + "\n]");
		
		buffWriter.close();	
				
	}// ENDE schreibeTestPersonenInJsonDatei()
	
	
}// ENDE KlasseJsonDatei
