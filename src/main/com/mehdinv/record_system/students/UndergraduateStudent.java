package main.com.mehdinv.record_system.students;

//Implementation of the TaughtStudent abstract class- stores the constructor (which calls the super constructor)...
//...and a method that returns the type of student

//Author: Mehdi Naderi Varandi
//Student ID: 170725695

import main.com.mehdinv.record_system.Name;
import main.com.mehdinv.record_system.student_identifiers.TypeOfStudent;

public final class UndergraduateStudent extends TaughtStudent{
	private final static TypeOfStudent TYPE=TypeOfStudent.UG;

	public UndergraduateStudent(Name name, String birthday) throws IllegalArgumentException{
		super(name,birthday);	
	}
	
	
	@Override
	public final TypeOfStudent getType() {
		// TODO Auto-generated method stub
		return TYPE;
	}

}