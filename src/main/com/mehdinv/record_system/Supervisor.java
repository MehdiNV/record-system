package main.com.mehdinv.record_system;

/**
 * What does this class do?
 * This class allows you to hold onto the names of the supervisor i.e. use it as an object
 *
 * @author Mehdi Naderi Varandi
 */

public class Supervisor {
	//FIELDS-----------------------
	private final String name; //Name of the supervisor
	//Once initialised, the name of the supervisor shouldn't change - to ensure that defensive programming is always followed, I usually just...
	//..re-create the required objects if their details need to be changed instead to preserve immutability
	
	private static final String FILE_DELIMITER=" "; //Used for splitting the name up
	//-----------------------------
		
	private Supervisor (String name){ //Constructor for the supervisor class
		this.name=name;
	}
	
	public final String getName() {
		return name;
	}
	
	public final static Supervisor getInstance(String supervisorLine){
		String[] fileParts=supervisorLine.split(FILE_DELIMITER);
		
		String firstName=fileParts[0].trim();
		String surnameName=fileParts[1].trim();

		return new Supervisor((firstName + " " + surnameName));
	}
	
	@Override
	public final String toString() {
		return getName();
	}
	
}
