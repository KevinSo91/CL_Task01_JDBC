package mainPackage;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ExcelDatei {
	
	//******************************************** Attribute ************************************************
	
	private final String fileLocation;
	private final String fileName;
	//private final String fileLocationAndName;
	private final File xlsxFile;
	private Workbook workbook;
		
		
	//***************************************** Konstruktoren ***********************************************
		
	public ExcelDatei(String fileLocation, String fileName, String mappeName) {
		this.fileLocation = fileLocation;
		this.fileName = fileName + ".xlsx";
		//this.fileLocationAndName = fileLocation + "\\" + fileName;
		this.xlsxFile = new File(this.fileLocation, this.fileName);
	}
	
	//******************************************* Methoden ***************************************************
	
	
	public static void schreibePersonen (ArrayList<Person> inputListePersonen, String outputExcelDateiPfad, String outputExcelDateiName, String tabelle) throws FileNotFoundException, IOException {
		
		System.out.println("Erstelle Excel-Datei...");
		
		Workbook mappe_Personen = new XSSFWorkbook(); // xlsx-Format (ab 2003)
		Sheet tabelle_Personen = mappe_Personen.createSheet(tabelle);
		// Erstelle Kopfzeile
		Row kopfZeile = tabelle_Personen.createRow(0);
		kopfZeile.createCell(0).setCellValue("id");
		kopfZeile.createCell(1).setCellValue("Vorname");
		kopfZeile.createCell(2).setCellValue("Nachname");
		kopfZeile.createCell(3).setCellValue("Alter");
		
		// Fuege Datensaetze in Tabelle ein
		int zeile = 1;
		for (Person person : inputListePersonen) {
			Row datensatz = tabelle_Personen.createRow(zeile);
			datensatz.createCell(0).setCellValue(zeile);
			datensatz.createCell(1).setCellValue(person.getVorname());
			datensatz.createCell(2).setCellValue(person.getNachname());
			datensatz.createCell(3).setCellValue(person.getAlter());
			
			zeile++;
		}
		
		// Erstelle Datei
		mappe_Personen.write(new FileOutputStream(outputExcelDateiPfad + "/" + outputExcelDateiName));
		
		mappe_Personen.close();
		
		System.out.println("Excel-Datei wurde erstellt!");
		
	}// ENDE schreibePersonenInExcel()
	
	
	public static void schreibeTestPersonen (ArrayList<TestPerson> inputListeTestPersonen, String outputExcelDateiPfad, String outputExcelDateiName, String tabelleName) throws FileNotFoundException, IOException {
		
		System.out.println("Erstelle Excel-Datei...");
		
		Workbook mappe_TestPersonen = new XSSFWorkbook(); // xlsx-Format (ab 2003)
		Sheet tabelle_Personen = mappe_TestPersonen.createSheet(tabelleName);
		// Erstelle Kopfzeile
		Row kopfZeile = tabelle_Personen.createRow(0);
		kopfZeile.createCell(0).setCellValue("seq");
		kopfZeile.createCell(1).setCellValue("first name");
		kopfZeile.createCell(2).setCellValue("last name");
		kopfZeile.createCell(3).setCellValue("age");
		kopfZeile.createCell(4).setCellValue("street");
		kopfZeile.createCell(5).setCellValue("city");
		kopfZeile.createCell(6).setCellValue("state");
		kopfZeile.createCell(7).setCellValue("zip");
		kopfZeile.createCell(8).setCellValue("dollar");
		kopfZeile.createCell(9).setCellValue("pick");
		kopfZeile.createCell(10).setCellValue("date");
		
		// Fuege Datensaetze in Tabelle ein
		int zeile = 1;
		for (TestPerson testPerson : inputListeTestPersonen) {
			Row datensatz = tabelle_Personen.createRow(zeile);
			datensatz.createCell(0).setCellValue(testPerson.getSeq());
			datensatz.createCell(1).setCellValue(testPerson.getFirstName());
			datensatz.createCell(2).setCellValue(testPerson.getLastName());
			datensatz.createCell(3).setCellValue(testPerson.getAge());
			datensatz.createCell(4).setCellValue(testPerson.getStreet());
			datensatz.createCell(5).setCellValue(testPerson.getCity());
			datensatz.createCell(6).setCellValue(testPerson.getState());
			datensatz.createCell(7).setCellValue(testPerson.getZip());
			datensatz.createCell(8).setCellValue(testPerson.getDollar());
			datensatz.createCell(9).setCellValue(testPerson.getPick());
			datensatz.createCell(10).setCellValue(testPerson.getDate());
			
			zeile++;
		}
		
		// Erstelle Datei
		mappe_TestPersonen.write(new FileOutputStream(outputExcelDateiPfad + "/" + outputExcelDateiName + ".xlsx"));
		
		mappe_TestPersonen.close();
		
		System.out.println("Excel-Datei wurde erstellt!");
		
	}// ENDE schreibeTestPersonenInExcel()
	
	
	public void schreibeTestPersonenNormalform3 (ArrayList<TestPerson> inputListeTestPersonen) throws FileNotFoundException, IOException {
		
		System.out.println("Erstelle Excel-Datei...");
		
		Workbook mappe_Sequenzen = new XSSFWorkbook(); // xlsx-Format (ab 2003)
		
		// Erstelle Tabelle 'Sequenzen'
		Sheet Sequenzen = mappe_Sequenzen.createSheet("sequenzen");
		// Erstelle Kopfzeile
		Row kopfZeile = Sequenzen.createRow(0);
		kopfZeile.createCell(0).setCellValue("seq id");
		kopfZeile.createCell(1).setCellValue("person id");		
		kopfZeile.createCell(2).setCellValue("seq dollar");
		kopfZeile.createCell(3).setCellValue("seq pick");
		kopfZeile.createCell(4).setCellValue("seq date");		
		// Fuege Sequenzen in Tabelle 'Sequenzen' ein
		int zeile = 1;
		for (TestPerson person : inputListeTestPersonen) {
			Row datensatz = Sequenzen.createRow(zeile);
			datensatz.createCell(0).setCellValue(person.getSeq());
			datensatz.createCell(1).setCellValue(person.getSeq());      // Nur in DIESEM FALL, da keine Personen doppelt: seq_id -> person_id	
			
			float dollarFloat = Float.parseFloat(person.getDollar().substring(1));  // Entfert das erste Zeichen des Strings und speichert den Wert als Float
			datensatz.createCell(2).setCellValue(dollarFloat);
			
			datensatz.createCell(3).setCellValue(person.getPick());
			datensatz.createCell(4).setCellValue(person.getDate());
			
			zeile++;
		}
		
		//Erstelle Tabelle 'personen'
		Sheet Personen = mappe_Sequenzen.createSheet("personen");
		// Erstelle Kopfzeile
		kopfZeile = Personen.createRow(0);
		kopfZeile.createCell(0).setCellValue("person id");
		kopfZeile.createCell(1).setCellValue("person fist name");		
		kopfZeile.createCell(2).setCellValue("person last name");
		kopfZeile.createCell(3).setCellValue("person age");
		kopfZeile.createCell(4).setCellValue("person street");
		kopfZeile.createCell(5).setCellValue("person zip");
		// Füge Personen in Tabelle 'personen'ein
		zeile = 1;
		for (TestPerson person : inputListeTestPersonen) {
			Row datensatz = Personen.createRow(zeile);
			datensatz.createCell(0).setCellValue(person.getSeq());
			datensatz.createCell(1).setCellValue(person.getFirstName());	
			datensatz.createCell(2).setCellValue(person.getLastName());
			datensatz.createCell(3).setCellValue(person.getAge());
			datensatz.createCell(4).setCellValue(person.getStreet());
			datensatz.createCell(5).setCellValue(person.getZip());
			
			zeile++;			
		}
		
		// Erstelle Tabelle 'zips'
		Sheet zip = mappe_Sequenzen.createSheet("zips");
		// Erstelle Kopfzeile
		kopfZeile = zip.createRow(0);
		kopfZeile.createCell(0).setCellValue("zip");
		kopfZeile.createCell(1).setCellValue("zip city");		
		kopfZeile.createCell(2).setCellValue("zip state");				
		// Fuege ZIPs in Tabelle 'zips' ein
		zeile = 1;
		for (TestPerson person : inputListeTestPersonen) {
			Row datensatz = zip.createRow(zeile);
			datensatz.createCell(0).setCellValue(person.getZip());
			datensatz.createCell(1).setCellValue(person.getCity());
			datensatz.createCell(2).setCellValue(person.getState());			
			
			zeile++;
		}
						
		// Erstelle Datei
		mappe_Sequenzen.write(new FileOutputStream(this.fileLocation + "/" + this.fileName ));
		
		mappe_Sequenzen.close();
		
		System.out.println("Excel-Datei wurde erstellt!");
		
	}// ENDE schreibeTestPersonenInExcel()
	
	
	public static void lesePersonen (String inputExcelDateiPfad, String inputExcelDateiName, String inputTabelle , ArrayList<Person> outputListePersonen) throws InvalidFormatException, IOException {
		
		// Erstelle Objekt für die Datei
		File excelDatei = new File(inputExcelDateiPfad + inputExcelDateiName);
		// Lese Datei ein
		FileInputStream excelInput = new FileInputStream(excelDatei);
		Workbook mappe = new XSSFWorkbook(excelInput);
		Sheet tabelle = mappe.getSheet(inputTabelle);
		
		// Schleife fuer die Zeilen
		for (Row zeile : tabelle) {			
			String vorname = null;
			String nachname = null;
			int alter = 0;
			
			// Schleife fuer die Zellen
			int i_zelle = 0;
			for (Cell zelle : zeile) {
				
				// Unterscheide nach Datentyp
				switch(i_zelle) {
				
				case 0:
					vorname = zelle.getStringCellValue();
					break;
				case 1:
					nachname = zelle.getStringCellValue();
					break;
				case 2:
					alter = (int) zelle.getNumericCellValue();
					break;
				default:
					System.out.println("Fehler beim einlesen der Daten");
					break;
				
				}// ENDE switch-case				
				i_zelle++;
			}// ENDE for-Schleife für Zelle
			
			i_zelle = 0;
			
			outputListePersonen.add(new Person(vorname, nachname, alter));
			
		}// ENDE for-Schleife für Zeile
		
		
		
		mappe.close();
		
	}// ENDE lesePersonenAusExcel()
	
	public static void erzeugeTestdaten_xlsxDatei(String outputDateiPfad, String outputDateiName, int anzahlZeilen) throws FileNotFoundException, IOException {
		
		System.out.println("Erstelle Excel-Testdatei...");
		
		Workbook mappe_Personen = new XSSFWorkbook();
		Sheet tabelle_Personen = mappe_Personen.createSheet("Tabelle1");
				
		// Fuege Testdaten in Tabelle ein
		for (int i = 0; i < anzahlZeilen; i++) {
			Row datensatz = tabelle_Personen.createRow(i);
			datensatz.createCell(0).setCellValue("Vorname" + i);
			datensatz.createCell(1).setCellValue("Nachname" +i);
			datensatz.createCell(2).setCellValue(i);			
		}
		
		// Erstelle Datei
		mappe_Personen.write(new FileOutputStream(outputDateiPfad + "/" + outputDateiName));
		
		mappe_Personen.close();
		
		System.out.println("Excel-Testdatei wurde erstellt!");
		
		
		
		
	}
	
	
}// ENDE Klasse ExcelMain
