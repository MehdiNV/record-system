package main;

//Implementation of the TaughtStudent abstract class- stores the constructor (which calls the super constructor)...
//...and a method that returns the type of student

//Author: Mehdi Naderi Varandi


public final class UndergraduateStudent extends TaughtStudent{
	private final static TypeOfStudent TYPE=TypeOfStudent.UG;

	public UndergraduateStudent(Name name,String birthday) throws IllegalArgumentException{
		super(name,birthday);	
	}
	
	
	@Override
	public final TypeOfStudent getType() {
		// Returns the type of student (e.g. this student is a Undergraduate)
		return TYPE;
	}

}
