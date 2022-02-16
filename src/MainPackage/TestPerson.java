package mainPackage;

import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
//import java.sql.Date;
import java.util.Locale;

import org.postgresql.core.Parser;

public class TestPerson {
	
	//******************************************** Attribute ************************************************
	
	private int seq;
	private String firstName;
	private String lastName;
	private int age;
	private String street;
	private String city;
	private String state;
	private int zip;
	private String dollar;
	private String pick;
	private String date;
	
	//***************************************** Konstruktoren ***********************************************

	public TestPerson(int seq, String firstName, String lastName, int age, String street, String city,
			String state, int zip, String dollar, String pick, String date) {
		this.seq = seq;
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.street = street;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.dollar = dollar;
		this.pick = pick;
		this.date = date;
	}
	
	public TestPerson() {
		
	}
	
	//******************************************* Methoden ***************************************************
	
	public int getSeq() {
		return this.seq;
	}
	public String getFirstName() {
		return this.firstName;
	}
	public String getLastName() {
		return this.lastName;
	}
	public int getAge() {
		return this.age;
	}
	public String getStreet() {
		return this.street;
	}
	public String getCity() {
		return this.city;
	}
	public String getState() {
		return this.state;
	}
	public int getZip() {
		return this.zip;
	}
	
	public String getDollar() {
		return this.dollar;
	}
	public Float getDollarFloat() {
		return Float.parseFloat(this.dollar.substring(1));
	}
	
	public String getPick() {
		return this.pick;
	}
	
	public String getDate() {
		return this.date;
	}
	public String getDateUS() {
		String[] dateArr = this.date.split("/");
		String dateUS = dateArr[2] + "-" + dateArr[1] + "-" + dateArr[0];
		return dateUS;		
	}
	public LocalDate getDateDateFormat() throws ParseException {
		
		String[] dateArr = this.date.split("/");
		String dateFormat = dateArr[2] + "-" + dateArr[1] + "-" + dateArr[0];
		LocalDate localDate = LocalDate.parse(dateFormat);
		
//		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
//		String dateInString = this.date;
//		Date date = formatter.parse(dateInString);
		
		return localDate;
	}
	
	public void setSeq(int seq) {
		this.seq = seq ;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public void setState(String state) {
		this.state = state;
	}
	public void setZip(int zip) {
		this.zip = zip;
	}
	public void setDollar(String dollar) {
		this.dollar = dollar;
	}
	public void setPick(String pick) {
		this.pick = pick;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	
	
	
	public String infoAusgebenString() {
		String ausgabe = MessageFormat.format("SEQ: {0} - FIRSTNAME: {1} - LASTNAME: {2} - AGE: {3} - STREET: {4} - CITY: {5} - STATE: {6}"
										+ " - ZIP: {7} - DOLLAR: {8} - PICK {9} - DATE {10}",
										Integer.toString(seq), firstName, lastName, age, street, city, state, Integer.toString(zip), dollar, pick, date );
		return ausgabe;
	}
	
	
	

}
