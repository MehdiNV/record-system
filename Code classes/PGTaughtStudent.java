package main;

//Implementation/subclass of abstract class TaughtStudent - stores the constructor (which calls the super constructor)...
//...and a method that returns the type of student

//Author: Mehdi Naderi Varandi


public final class PGTaughtStudent extends TaughtStudent{
	private final static TypeOfStudent TYPE=TypeOfStudent.PGT;
	//Stores what type of student this class is 
	
	public PGTaughtStudent(Name name, String birthday) throws IllegalArgumentException {
		super(name, birthday);
	}

	@Override
	public final TypeOfStudent getType() {
		return TYPE;
	}

}
