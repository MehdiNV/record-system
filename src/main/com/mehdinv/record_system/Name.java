package main.com.mehdinv.record_system;

/**
 * What does this class do?
 * Used to create Name objects that can be associated with student objects
 * Each student will have one assigned to them, since all students should have a name
 *
 * Assumptions made:
 * 	1) I'm assuming that there is only a first name & surname - this class will solely focus on those two components
 *
 * Area for improvement:
 * 	1) Expand the class to include other components such as middle names
 *
 * @author Mehdi Naderi Varandi
 */

public final class Name {
	
	//FIELDS--------------------------------------------------------
	private final String firstName; //Stores first Name 
	private final String surname;	//Stores surname
	//--------------------------------------------------------------
	
	public Name (String firstName, String surname){ //Constructor for the Name class
		
		if (firstName==null || surname==null){ //Throws an exception if the user tries entering a name that's null
			throw new NullPointerException ("You need to have a actual name & surname, instead of a null!");
		}
		
		this.firstName=firstName; //Constructor also does the function of the setter methods
		this.surname=surname;	  //This is a practice derived from defensive programming
	}
	
	
	//No defensive copies are used with the two getter methods below as they are only returning Strings (which is already immutable)
	public final String getFirstName() { //Getter for First names
		return firstName; 
	}
	
	public final String getSurname() { //Getter for surname
		return surname;
	}
	
	@Override
	public String toString() { //Overridden toString method that returns full name instead of object hash value
		return firstName + " " + surname;
	}
	
}
