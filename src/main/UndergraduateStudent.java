package main;

//Implementation of the TaughtStudent abstract class- stores the constructor (which calls the super constructor)...
//...and a method that returns the type of student

//Author: Mehdi Naderi Varandi
//Student ID: 170725695

public final class UndergraduateStudent extends TaughtStudent{
	private final static TypeOfStudent TYPE=TypeOfStudent.UG;

	public UndergraduateStudent(Name name,String birthday) throws IllegalArgumentException{
		super(name,birthday);	
	}
	
	
	@Override
	public final TypeOfStudent getType() {
		// TODO Auto-generated method stub
		return TYPE;
	}

}
