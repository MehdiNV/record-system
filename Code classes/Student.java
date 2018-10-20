package main;

import java.util.Date;

//Author: Mehdi Naderi Varandi

//Interface for Students:
//Assumptions made about students:
//1) Each student has a name - specifically first name & and a surname
//2) All students are going to Newcastle University / same University
//3) Each student has a DOB
//4) Each student will be taking various modules (except Post-graduate researchers)
//5) The MAXIMUM age of the student will be 105 years old - this is 24 years older than life expectancy, + from my research, the oldest student seemed to have been 102

public interface Student {
	
	Boolean isBadAge(int minimumAge); //Every student has a age criteria
	//UG have to be at least 17 whereas PG have to be at least 20
	//This method can return whether or not the student/person meets those requirements
	//Furthermore, the idea is that it can also throw an exception/return false....
	//if person has an abnormally high age - for this project, I will assume that each person is at least...
	//below 105 years old (Apparently, the most oldest student has been 102, hence I'm setting it at 105 just to be safe).
	
	Name getName(); //Every student has a name, and hence they should have a get method for it
	//Each student shares characteristics such as birthdays, student ID, a name, and a type (i.e. UG/PG)...
	//Hence why the interface contains these methods
	
	Date getBirthday  (); //Gets DOB of student (useful for getting age)

    StudentID getStudentID() throws NoSuchFieldException; //Gets ID of a student

    TypeOfStudent getType(); //Every student is either a UG/PGT/PGR
	//Essentially, they are a 'type' of possible values
	
    SmartCards getCard() throws NoSuchFieldException;
    
    int getCheck(); //Checks whether the student is registered correctly - this can differ for the type of student. For example:
    //	1) If the student is UG or PGT, it'll check the credits they are allocated
    //	2) If the student is PGR, it checks whether they have a supervisor allocated to them
	
}
