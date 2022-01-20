package mainPackage;


//import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ExcelMain {
	
//	public static void main(String[] args) throws FileNotFoundException, IOException {
//		
//		System.out.println("\nProgramm startet...\n");
//		
//		ExcelMain excelMain = new ExcelMain();
//		excelMain.erstelleExcelDatei();
//		excelMain.schreibePersonenInExcel();
//		
//		System.out.println("\nProgramm endet...\n");
//		
//	}
	
//	private void erstelleExcelDatei() throws FileNotFoundException, IOException {
//		
//		Workbook mappe_Personen = new XSSFWorkbook();
//		Sheet tabelle_Personen = mappe_Personen.createSheet("Tabelle1");
//		Row kopfZeile = tabelle_Personen.createRow(0);
//		kopfZeile.createCell(0).setCellValue("id");
//		kopfZeile.createCell(1).setCellValue("Vorname");
//		kopfZeile.createCell(2).setCellValue("Nachname");
//		kopfZeile.createCell(3).setCellValue("Alter");
//		
//		mappe_Personen.write(new FileOutputStream("C:\\Users\\user1\\eclipse-workspace\\Task01_JDBC\\src\\input_output_Excel\\Mappe_Personen.xlsx"));
//		
//	}
	
	public static void schreibePersonenInExcel (ArrayList<Person> listePersonen) throws FileNotFoundException, IOException {
		
		System.out.println("Erstelle Excel-Datei...");
		
		Workbook mappe_Personen = new XSSFWorkbook();
		Sheet tabelle_Personen = mappe_Personen.createSheet("Tabelle1");
		// Erstelle Kopfzeile
		Row kopfZeile = tabelle_Personen.createRow(0);
		kopfZeile.createCell(0).setCellValue("id");
		kopfZeile.createCell(1).setCellValue("Vorname");
		kopfZeile.createCell(2).setCellValue("Nachname");
		kopfZeile.createCell(3).setCellValue("Alter");
		
		// Fuege Datensaetze in Tabelle ein
		int zeile = 1;
		for (Person person : listePersonen) {
			Row datensatz = tabelle_Personen.createRow(zeile);
			datensatz.createCell(0).setCellValue(zeile);
			datensatz.createCell(1).setCellValue(person.getVorname());
			datensatz.createCell(2).setCellValue(person.getNachname());
			datensatz.createCell(3).setCellValue(person.getAlter());
			
			zeile++;
		}
		
		// Erstelle Datei
		mappe_Personen.write(new FileOutputStream("C:\\Users\\user1\\eclipse-workspace\\Task01_JDBC\\src\\input_output_Excel\\Output_Mappe_Personen.xlsx"));
		
		System.out.println("Excel-Datei wurde erstellt!");
		
	}
	
}
