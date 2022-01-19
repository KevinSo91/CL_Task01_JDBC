package mainPackage;

public class Person {
	
	// Attribute
	private String vorname;
	private String nachname;
	private int alter;
	
	// Konstruktoren 
	public Person(String vorname, String nachname, int alter) {
		this.vorname = vorname;
		this.nachname = nachname;
		this.alter = alter;
		
	}
	// Methoden
	public String getVorname() {
		return this.vorname;
	}	
	public void setVorname(String vorname) {
		this.vorname = vorname;
	}
	
	public String getNachname() {
		return this.nachname;
	}	
	public void setNachname(String nachname) {
		this.nachname = nachname;
	}
	
	public int getAlter() {
		return this.alter;
	}
	public void setAlter(int alter) {
		this.alter = alter;
	}
	
	public String infoAusgebenString(){
		return ("Vorname: " + this.vorname + " | Nachname: " + this.nachname + " | Alter: " + this.alter);
	}
	
	public void infoAusgeben() {
		System.out.println(infoAusgebenString());
	}	
	
	
}// ENDE Klasse Person
