package main;

import java.util.Calendar;
import java.util.Date;

//Abstract students solely for those who are conducting research at the university
//This includes PGR students but excludes UG & PGT - the reason I've set up the inheritance hierarchy...
//...like this is to also ensure that future programmers can easily modify it for other PG students i.e. ...
//... maybe add PhD students as well, since they would also be conducting research

//Author: Mehdi Naderi Varandi
//Student ID: 170725695

public abstract class ResearchStudent implements Student{
	//FIELDS----------------------------------------------------------------
	private final int age;	//Every student has an age
	private final Name name;	//Every student has a name
	private final Date birthday;	//Every student should have a date of birth
	private final static String SPLIT_SLASH="/";
	private Supervisor supervisor;
	
	private final static long MILLISECONDS_IN_A_YEAR=(long)31556925000L; //Constant that holds the number of...
	//...milliseconds in a year
	//----------------------------------------------------------------------
		
	public ResearchStudent(Name name,String birthday, Supervisor supervisor) throws IllegalArgumentException{

		if (birthday==null){
			throw new NullPointerException("You cannot have your birthday as null!");
		}
		
		//Note for anyone reading: My initial idea was to add the supervisor the same way you can modules to the TaughtStudent part of the system
		//However, one thing I thought may be better instead was to declare and pass in the supervisor you want beforehand...
		//...since I believed this would make the class more immutable/defensive (as the object now cannot be changed by adding a supervisor...
		//...after its creation).
		
		String[] splitParts=birthday.split(SPLIT_SLASH);
		String day=splitParts[0].trim();
		String month=splitParts[1].trim();
		String year=splitParts[2].trim();
		//Constructor receives birthday in DD/MM/YYYY format (I'm assuming that the University is in the UK...
		//...and hence that the UK date format is being used
		//The code above then splits DD into day variable, month into its respective variable, and so on

		if (!(day.length()==2) || !(month.length()==2) || !(year.length()==4)){
			if (day.contains("00") || month.contains("00") || year.contains("0000")){
				throw new IllegalArgumentException ("You must have an actual date!");
			}
			
		}
		
		//The code below calculates the age of the student via utilisation of Calendar and Date classes
		//Essentially, I use the Calendar class to see how many milliseconds are between now and the date of birth
		//Then dividing that value by the amount of milliseconds per year to turn it into age
		//The milliseconds form of the age is stored in the Date variable, where as the int is stored as age
		Calendar birthDate=Calendar.getInstance();
		birthDate.set(Integer.parseInt(year), ((Integer.parseInt(month))-1), Integer.parseInt(day));
		//Sets Calendar to the time specific to the birthday received...
		//... i.e. 26/11/1998

		Calendar currentDate=Calendar.getInstance(); //Gets the time of the current date
		//For example, as of the time of writing, the year is 2018...
		//...hence this line of code will get the current date (i.e. 2018) only
		long ageInMilliseconds=currentDate.getTimeInMillis() - birthDate.getTimeInMillis();

		long longAge=(ageInMilliseconds/MILLISECONDS_IN_A_YEAR);
		int age=(int) longAge;
		//Gets Age as an integer - useful for checking whether the student is old enough to be a UG
		
		Date date=new Date();
		date.setTime((birthDate.getTimeInMillis()));
		

		this.age=age;
		this.name=name;
		this.birthday=date;
		this.supervisor=supervisor;

		//Calls method to verify the age of the student
		if (isBadAge(20)==false){ //Calls method to check the age of the student
			//Student is over 17 & 105
		}
		else if (isBadAge(20)==true){ //
			throw new IllegalArgumentException ("The student does not meet the age criteria!");
		}
	}
	
	@Override
	public final Boolean isBadAge(int minimumAge) {
		//Method that validates whether the student meets the age criteria
		//The implementations of this abstract class can pass in the minimum age (i.e. UG is 17, PGT is 20)...
		//...and check whether the student is meeting the age criteria
		
		if ((getAge()>=minimumAge) && (getAge()<=105)){ //Checks whether student is between ages 17-105
			return false; //Returns false; student is not violating the age criteria
		}
		else{
			return true;  //Returns true; the student is violating the age criteria
		}
	}
	
	public final int getAge() { //Getter method for age
		return age; //Returns the age of the student
		//As age is stored as an Integer, no defensive copy is returned as it's already immutable
	}
	
	@Override
	public final Name getName() {
		return new Name(name.getFirstName(),name.getSurname());
		//Returns a defensive copy of the students name
	}
	
	@Override
	public final Date getBirthday() {
		return new Date (birthday.getTime());
		//Returns a defensive copy of the students birthday/ Date
	}
	
	@Override
	public final StudentID getStudentID() throws NoSuchFieldException {
		StudentID defensiveCopy=(StudentID.getID(this));
		return defensiveCopy;	
		//Gets student ID when student object is passed into it (Returns defensive copy)
	}
	
	@Override
	public final int getCheck() {
		//Specific check method for PGR students
		//This method checks instead whether the PGR student has a supervisor...
		//...allocated to them or not
		if (getSupervisor()==null){
			return 0; //Returns a FALSE
		}
		else{
			return 1; //Returns a TRUE
		}
	}
	
	public final SmartCards getCard() throws NoSuchFieldException{
		SmartCards defensiveCopy= (SmartCards.getCard((Student) this));
		return defensiveCopy;	
		//Gets student ID when student object is passed into it (Returns defensive copy)
	}
	
	
	public final Supervisor getSupervisor() {
		return (Supervisor.getInstance(supervisor.getName())); 
		//Returns a defensive copy of Supervisor object
		
	}

	public abstract TypeOfStudent getType();
	//Returns type of Student to the caller
	//I left it for the implementations to expand on the getType method themselves...
	//...since I thought it would be beneficial for the system, as it will allow you...
	//...to add more students i.e. PhD or another type in the future instead

	
}
