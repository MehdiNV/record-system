package main;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import main.Student;

//Object Factory that generates a unique student ID

//Author: Mehdi Naderi Varandi

public final class StudentID {
	private static final Map<Student,StudentID> studentID=new HashMap<Student,StudentID>();
	
	private static String singleLetter; //Holds a single letter
	private static String fourDigits;	//Holds a random 4 digit number
	
	private final String uniqueID;
	
	private StudentID(String unique_ID){ //Private constructor for the method
		//Aim is to use the createNewID method to generate ID's for students; specifically, ID's that are...
		//... a random letter followed by a random 4 digit number i.e. a1234
		this.uniqueID=unique_ID;
	}
	
	
	
	
	
	public final static String getSingleLetter() { //Get method for singleLetter
		return singleLetter;
	}


	public final static String getFourDigits() {	  //Get method for four random digits
		return fourDigits;
	}
	
	private final static void generateSingleLetter() {
		Random generateRandom= new Random();
		singleLetter = String.valueOf((char)(generateRandom.nextInt(26)+'a'));
	}

	public final static void generateFourDigits() { //This method generates a 4 digit number between 1000-9999
		//Assumptions made is that the four digit number is positive (i.e. why have a student ID with a negative number?)
		//Hence, this is why the number is between 0000-9999 instead of etc -9999 to 9999
		Random generateRandom= new Random();
		
		int firstNumber=(generateRandom.nextInt(9)+0); //Generates first number between 0 & 9
		int secondNumber=(generateRandom.nextInt(9)+0); //Generates second number between 0 & 9
		int thirdNumber=(generateRandom.nextInt(9)+0); //Generates third number between 0 & 9
		int fourthNumber=(generateRandom.nextInt(9)+0); //Generates fourth number between 0 & 9

		//I did 4 separate variables that are between 0-9 since 0123 is technically a 4 digit number
		//i.e. it can't solely be between 1000-9999
		
		fourDigits = (String.valueOf(firstNumber)) + (String.valueOf(secondNumber)) + (String.valueOf(thirdNumber)) + (String.valueOf(fourthNumber)); 
		//Generates number between 0000 & 9999
	}

	@Override
	public String toString() {
		return (uniqueID);
	}
	
	public final static StudentID getID(Student student) throws NoSuchFieldException{
		//Method that gets the student ID, when a student object is passed into it
		if (studentID.containsKey(student)==false){
			throw new NoSuchFieldException ("This student has no ID!");
		} //Gets studentID object from the data structure in this class
		
		StudentID defensiveCopy=studentID.get(student);
		
		return defensiveCopy;
	}

	public final static void removeID(Student student) {
		studentID.remove(student); //Removes a record (i.e. useful if you're terminating the student)
	}


	public final static  StudentID createNewID(Student student, Boolean amend, Student oldStudent) throws IndexOutOfBoundsException, NoSuchFieldException{
		//Method that creates a new object/String and returns it - each student will be linked to a unique ID
		
		if (amend) {
			studentID.put(student, oldStudent.getStudentID());
			studentID.remove(oldStudent);
			return null; //This part of the program is solely for if you're amending a students data
			//The goal here is to replace the previous student with the new one (i.e. the one that may have the correct name...
			//...or birthday, as I'm assuming thats what the specification means with amending student data i.e. you typed their name...
			//...wrong and need to re-do it). 
			
			//The goal of this part is to ensure that the new student still retains their old ID, as well as ensuring that the...
			//...old object is removed from the records (otherwise they can still call getID method even though they shouldn't...
			//...have a ID anymore).
		}
		
		
		generateSingleLetter(); //Generates a random letter between a and z
		generateFourDigits();	//Generates 4 random integers i.e. 0123 or 8723
		
		String uniqueID= getSingleLetter() + (String.valueOf(getFourDigits())); 
		//Creates a unique ID via combining a random letter with a random 4 digit number
		StudentID uniqueIDObject= new StudentID(uniqueID);
		
		
		if (studentID.containsValue(uniqueIDObject)==true){
			if (!(studentID.size()==260000)){ //Number of permutations for unique ID's add up to 520,000
			//Since there is 26 (No. of letters) * 10 (No of possible numbers) * 10 * 10 * 10
			//Hence, if the array size is 260,000 then there can be no more unique ID's generated
				createNewID(student, false, null); //Uses recursion to make another ID until a unique ID is finally generated
			}
			else if (studentID.size()==260000){ //Throws new exception as too many unique ID's have been created
				throw new IndexOutOfBoundsException ("Too many unique ID's!");
			}
		}
		
		//StudentID newID= new StudentID(uniqueID);
		
		studentID.put(student, uniqueIDObject);
		return (uniqueIDObject);
	}
	
	
}
