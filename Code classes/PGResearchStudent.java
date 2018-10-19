package main;

//Implementation/subclass of abstract class ReserachStudent - stores the constructor (which calls the superconstructor)...
//...and a method that returns the type of student

//Author: Mehdi Naderi Varandi

public final class PGResearchStudent extends ResearchStudent{
	private final static TypeOfStudent TYPE=TypeOfStudent.PGR;
	//Holds the type of the student as PGR

	public PGResearchStudent(Name name, String birthday, Supervisor supervisor) throws IllegalArgumentException {
		super(name, birthday, supervisor); //Calls constructor of parent class (Research Student) to create...
		//...the object
	}
	
	
	@Override
	public final TypeOfStudent getType() { //Returns the Type of the student
		return TYPE;
	}
	

}
