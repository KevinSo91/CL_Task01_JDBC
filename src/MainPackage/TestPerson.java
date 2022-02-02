package mainPackage;

import java.text.MessageFormat;
import java.util.ArrayList;

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
	public String getPick() {
		return this.pick;
	}
	public String getDate() {
		return this.date;
	}
	
	
	
	
	public void infoAusgeben() {
		String ausgabe = MessageFormat.format("SEQ: {0} - FIRSTNAME: {1} - LASTNAME: {2} - AGE: {3} - STREET: {4} - CITY: {5} - STATE: {6}"
										+ " - ZIP: {7} - DOLLAR: {8} - PICK {9} - DATE {10}",
										seq, firstName, lastName, age, street, city, state, zip, dollar, pick, date );
		System.out.println(ausgabe);
	}
	
	
	

}
