package main;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//What does this class do? 
//
//--> This class acts as an object factory for Smart cards - it's used to ensure that each object is unique
//	  Smart cards are generated via:
//	  1) First component: The initials of the student is used to make the first part of the card...
//	  ...for example, if our user were to be Neil Speirs, then the output would be NS as the initials
//	  2) Second component: The milliseconds since January 1st, 1970 (using Date class) are held in a variable...
//	  ... then calendar class is used to convert the value held in the variable into the current year (i.e. currently 2018)
//	  3) Third component: Serial number is assumed to be sequential (i.e. increases continuously from 1 to 100)...
//	  ...this is used to ensure that each smart card is unique in case there are multiple students with the same initials entering in the same year...
//	  ...however, I'm assuming that the max this can be is 100 students (i.e. non-existent chance that 100 students with the same initials, enter the...
//	  ...same department at the university in the same year).

//Author: Mehdi Naderi Varandi

//Note for readers: Please note that the SmartCard is one class altogether instead of separated into 2
//The reason I've done this is because demonstrators were ok with this, hence my decision to keep it as 1 class instead...
//...of separating it into 2 separate classes

public final class SmartCards {
	//private static final List<String> smartCards=new ArrayList<String>();
	
	private static final Map<Student,SmartCards> smartCards= new HashMap<Student,SmartCards>();
	private static final Map<Student,String> checkExistingCards=new HashMap<Student,String>();
	 
	private String firstName; //Holds first name of the student
	private String surname;  //Holds surname of the student
	private Date birthday; //Holds the birthday of the student
	
	private Name name;
	private String smartCard;
	
	private static int yearOfIssue; //Second component for the card - the year of the issue
	private String serialNumber; //Final component for the card - the arbitrary serial number
	private StudentID studentID;
	
	
	private SmartCards(Name name, Date birthday, int yearOfIssue, String serialNumber, String smartCard){
		this.firstName=name.getFirstName();
		this.surname=name.getSurname();
		this.name=name;
		this.birthday=birthday;
		SmartCards.yearOfIssue=yearOfIssue;
		this.serialNumber=serialNumber;
		//Sets all variables to appropriate values
		
		
		this.smartCard=smartCard;
	}
	
	
	
	public final String getFirstName() { //Gets first name of the student
		return firstName;
	}

	public final String getSurname() { //Gets surname of the student
		return surname;
	}
	
	public final Name getName() { //Gets name of the student (A defensive copy)
		return new Name(name.getFirstName(),name.getSurname());
	}
	
	public final StudentID getStudentID() { //Gets first name of the student
		return studentID;
	}

	public final Date getBirthday() { //Gets birthday of the student
		return new Date (birthday.getTime());
	}

	public static final int getyearOfIssue() {//Gets the year that the smart card was issued in
		return yearOfIssue;
	}

	public final String getSerialNumber() {//Gets serial number of the smart card
		return serialNumber;
	}
	
	public final String getSmartCard() {
		return smartCard;
	}

	private final static void setYearOfIssue() {
		Date milliseconds=new Date();
		//Gets the milliseconds since 1st January 1970
		
		long noOfSeconds=milliseconds.getTime();
		//Stores the milliseconds
		
		Calendar calendar=Calendar.getInstance();
		calendar.setTimeInMillis(noOfSeconds);
		
		yearOfIssue=calendar.get(Calendar.YEAR);
		//Changes 
		
	}


	public final static String getInitials(String firstName, String surname){ //Method that returns the initials of the student
		//i.e. if the name was 'Neil Speirs' as shown in the specification, then the initials would be NS
		return String.valueOf(firstName.substring(0,1) + surname.substring(0,1));
	}
	
	public final static SmartCards getCard(Student student) throws NoSuchFieldException{
		//Method that gets the student ID, when a student object is passed into it
		if (smartCards.containsKey(student)==false){
			throw new NoSuchFieldException ("This student has no card!");
		}
		SmartCards defensiveCopy=smartCards.get(student);
		return defensiveCopy;
	}
	
	public final static void removeCard(Student student) {
		smartCards.remove(student);
		checkExistingCards.remove(student);
	}
	
	public final static SmartCards createNewCard(Student student, Name name, Date birthday, StudentID studentID, Boolean amend, Student oldStudent) throws IndexOutOfBoundsException, NoSuchFieldException{
		if (amend) {
			smartCards.put(student, oldStudent.getCard());
			smartCards.remove(oldStudent);
			
			checkExistingCards.put(student, checkExistingCards.get(oldStudent));
			checkExistingCards.remove(oldStudent);
			return null;
		}
		
		
		String initials=getInitials(name.getFirstName(),name.getSurname());
		
		int serial=0; //Initial serial number for the smart card - if there are students with the same initial and year, then this will be incremented to reflect that
		//I'm assuming that serial number can be at least one digit i.e. 0 instead of 00, and that the maximum it can be is 99
		//The reason for the maximum being 99 is that it's extremely unlikely for 100 students entering the same university at the same year, with the same initials
		
		setYearOfIssue();
		
		String smartCard = initials + "-" + getyearOfIssue() + "-" + serial;
		//The following code does several functions, such as:
		//	1) Check if smartCard is already in the ArrayList - if so, keep increasing the serial number (via a for loop) until a unique smartCard is generated...
		//	...note that however the maximum serial number is 99 - I'm assuming that 100 students with the same exact initials will not enter the university...
		//	...in the same year; this is my interpretation of the serial number from the specification.
		//	2) If the smartCard generated is unique, then add it to the ArrayList! Now there's a new unique object/card created
		
		if (checkExistingCards.containsValue(smartCard)==true){ //Checks whether smartCard already exists/ is not unique
			for (int loopCounter=1; loopCounter<100; loopCounter++ ){ //If so, starts a loop that increments the serial number until a unique card is created
				serial=loopCounter; //Sets the serial number to the loopCounter, which continuously increases per loop
				smartCard = initials + "-" + getyearOfIssue() + "-" + serial; //Creates a new smartCard 

				if (checkExistingCards.containsValue(smartCard)==false){ //Checks whether the new generated card is now unique
					break;	//If so, then it breaks the loop
				}
				else if(serial==100){ //However, if 100 cards have been generated and there's still no unique one, then it throws an exception 
					throw new IndexOutOfBoundsException("Too many students with the same initials have entered the year in the same year");
				}
			}
				
			SmartCards card = new SmartCards (name, birthday, yearOfIssue, String.valueOf(serial), smartCard);
			
			checkExistingCards.put(student, smartCard);
			smartCards.put(student,card);
			return card;
		}
		else if (checkExistingCards.containsValue(smartCard)==false){
			SmartCards card = new SmartCards (name, birthday, yearOfIssue, String.valueOf(serial), smartCard);

			checkExistingCards.put(student, smartCard);
			smartCards.put(student,card);
			return card;
		}
		
		
		throw new IllegalArgumentException ("Something has gone wrong - this point should not be reached");
	
	}



	@Override
	public String toString() {
		return smartCard;
	}




}
