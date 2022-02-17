package mainPackage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;

public class CsvDatei {
	
	//******************************************** Attribute ************************************************
	
		private final String fileLocation;
		private final String fileName;
		//private final String fileLocationAndName;
		private final File csvFile;
		
		
		//***************************************** Konstruktoren ***********************************************
		
		public CsvDatei(String location, String name) {
			this.fileLocation = location;
			this.fileName = name + ".csv";
			//this.fileLocationAndName = fileLocation + "\\" + fileName;
			this.csvFile = new File(this.fileLocation, this.fileName);
		}
		
		//******************************************* Methoden ***************************************************
	
		
		public void schreibeTestPersonen(ArrayList<TestPerson> inputArrayList) throws IOException {
			
			FileWriter writer = new FileWriter(this.csvFile);
			BufferedWriter buffWriter = new BufferedWriter(writer);
			
			System.out.println("\nCSV-Datei wurde erfolgreich erstellt!\n");
			
			buffWriter.write("seq,first name,last name,age,street,city,state,zip,dollar,pick,date\n\n");
			
			for(TestPerson testperson: inputArrayList) {
				buffWriter.write(MessageFormat.format("{0},{1},{2},{3},{4},{5},{6},{7},{8},{9},{10}\n",
						Integer.toString(testperson.getSeq()), testperson.getFirstName(), testperson.getLastName(), testperson.getAge(), testperson.getStreet(), testperson.getCity(),
						testperson.getState(), Integer.toString(testperson.getZip()), testperson.getDollar(), testperson.getPick(), testperson.getDate()));
								
			}
			buffWriter.close();
			writer.close();
			
			
			System.out.println("\nDaten wurden erfolgreich in CSV-Datei geschrieben!\n");
		}
		
		public void leseTestPersonen(ArrayList<TestPerson> outputArrayList) throws IOException, NumberFormatException {
			
			FileReader reader = new FileReader(this.csvFile);
			BufferedReader buffReader = new BufferedReader(reader);
			
			ignoriereZeilen(buffReader, 2);
			
			String[] testPerson;
			String zeile;
			
			for(zeile = buffReader.readLine(); zeile != null; zeile = buffReader.readLine()) {
				try {
					testPerson = zeile.split(",");					
					
					outputArrayList.add(new TestPerson(Integer.parseInt(testPerson[0]),testPerson[1],testPerson[2],Integer.parseInt(testPerson[3]),testPerson[4],
														testPerson[5],testPerson[6],Integer.parseInt(testPerson[7]),testPerson[8],testPerson[9],testPerson[10]));
				}catch(Exception e) {
					e.printStackTrace();
				}
				
			}
			buffReader.close();
			reader.close();
			
			
			
		}
		
		// Methode um X um Zeilen am Anfang der Datei beim Lesen zu überspringen
		private static void ignoriereZeilen(BufferedReader buffReader, int anzahlZeilen) throws IOException {
			
			for(int i = 0 ; i < anzahlZeilen ; i++) {
				buffReader.readLine();
			}			
		}

}
