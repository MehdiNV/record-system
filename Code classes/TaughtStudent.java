package main;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

//Abstract class that implements the Student interface
//This class carries out most of the functions that's already expected of the UG or PGT class

//Author: Mehdi Naderi Varandi

public abstract class TaughtStudent implements Student{
	//FIELDS----------------------------------------------------------------
	private final int age;	//Every student has an age
	private final Name name;	//Every student has a name
	private final Date birthday;	//Every student should have a date of birth
	private final String stringBirthday;
	
	private List<Module> modules=new ArrayList<Module>();
	//Holds the modules that the student is registered for
	//Note for markers: Most of the class is already close to being as immutable as possible - one aspect however is the modules list just above...
	//...my reasoning was that it would be much easier to add new modules after the student is created since you would have to keep adding the modules one at a time
	//This however is not the same for the ResearchStudent abstract class as the supervisor object is declared beforehand and added in when the reserach student object is created...
	//...making that alternative class more immutable
	
	private final static String SPLIT_SLASH="/";
	
	private final static long MILLISECONDS_IN_A_YEAR=(long)31556925000L; //Constant that holds the number of...
	//...milliseconds in a year
	//----------------------------------------------------------------------

	public TaughtStudent(Name name,String birthday) throws IllegalArgumentException{
		
		if (birthday==null){
			throw new NullPointerException("You cannot have your birthday as null!");
		}
		
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
		this.stringBirthday=birthday;
		
		//Calls method to verify the age of the student
		if (isBadAge(17)==false){ //Calls method to check the age of the student
			//Student is over 17 & 105
		}
		else if (isBadAge(17)==true){ //
			throw new IllegalArgumentException ("The student does not meet the age criteria!");
		}
	}
	
	
	public final int getAge() { //Getter method for age
		return age;
	}
	
	public final String getStringBirthday() { //Getter method for age
		return stringBirthday;
	}
	
	public final SmartCards getCard() throws NoSuchFieldException{
		SmartCards defensiveCopy= (SmartCards.getCard((Student) this));
		return defensiveCopy;	
		//Gets student ID when student object is passed into it (Defensive copy)
	}
	
	@Override
	public String toString() {
		return (" ----------------------------------------------- \n" + " " + String.valueOf(getName()) + " is a " + getType() + " student at the university"
				+ "\n Birthday: " + getBirthday() + "\n Age: " + getAge() + moduleList() 
				+ "------------------------------------------------ \n");
	}
	
	public final Date getBirthday() { //Getter method to return the DOB of the student (Returns defensive copy)					
		return new Date (birthday.getTime());
	}
	
	public final Name getName() { //Getter method for name (Returns defensive copy)
		return new Name(name.getFirstName(),name.getSurname());
	}
	
	public final Boolean isBadAge(int minimumAge){ //Method that validates whether the student meets the age critera
		//The implementations of this abstract class can pass in the minimum age (i.e. UG is 17, PGT is 20)...
		//...and check whether the student is meeting the age criteria
		
		if ((getAge()>=minimumAge) && (getAge()<=105)){ //Checks whether student is between ages 17-105
			return false; //Returns false; student is not violating the age criteria
		}
		else{
			return true;  //Returns true; the student is violating the age criteria
		}
	}
	
	public final String moduleList() { //Returns the modules that the student is registered for
		String output=" "; //Output that will be appended/concatenated to
		//This allows me to minimise mutability by returning a defensive copy of the module list - since I'm not actually returning the actual...
		//...module list itself
		
		//Furthermore, I also expanded on the 'not returning null' part of defensive programming by instead appending each value to an output string...
		//...as I thought this is a good solution that looks really good in the Console as well as at the same time following the defensive...
		//...programming approach
		
		for (int loopCounter=0; loopCounter<modules.size();loopCounter++){
			output+=(modules.get(loopCounter) + "\n ");
			//Creates an output string that contains all the modules that the student is currently...
			//...registered for
		}
		
		if (output.equals(" ")){
			return  "This student is not registered for any modules";
		}
		else{
			return " \n \n " + getName() + " is currently registered for the following modules: \n \n" + output;
			//Returns a defensive copy of all the modules that the student is registered for
		}
		
	}
	
	public final StudentID getStudentID() throws NoSuchFieldException{
		StudentID defensiveCopy=(StudentID.getID(this));	
		return defensiveCopy;
		//Gets student ID when student object is passed into it (Returns a defensive copy)
	}
	
	public final void addModule(Module module) throws IllegalArgumentException{ //Method for taught students - allows them to have modules associated with them
		int creditCheck=0; //Output that will be appended/concatenated to
		
		//The for loop below does the following:
		//	1) Checks if the number of credits will exceed 120 - this is due to the fact that UG can only take 120 credits per year
		
		for (int loopCounter=0; loopCounter<modules.size();loopCounter++){
			creditCheck+=Integer.parseInt((modules.get(loopCounter).getModuleCredits()));
			
			if (this.getType()==TypeOfStudent.UG && creditCheck==120){ //Checks whether the student already has 120 credits
				//If so, then they can't have another module added! It'll be more than 120 credits
				throw new IllegalArgumentException ("This student cannot have more than 120 credits!"); 
				//Throws a new exception as you cannot have another module - doing so would make the credits exceed 120
				}
			else if (this.getType()==TypeOfStudent.PGT && creditCheck==180){ //Checks whether the student already has 180 credits (Only for PGT)
				//If so, then they can't have another module added! It'll be more than 180 credits
				throw new IllegalArgumentException ("This student cannot have more than 180 credits!"); 
				//Throws a new exception as you cannot have another module - doing so would make the credits exceed 180
				}
			}
		modules.add(module);
		
		}
	
	public final int getCheck(){
		int creditCheck=0; //Output that will be incremented to
		
		//The for loop below does the following functions:
		//	1) Adds up all the credits the student has from their modules and returns it
		
		for (int loopCounter=0; loopCounter<modules.size();loopCounter++){
			creditCheck+=Integer.parseInt((modules.get(loopCounter).getModuleCredits()));
		}
		
		return creditCheck; //Returns the overall credit value thats derived from all of the students modules
		
	} 
	
	public abstract TypeOfStudent getType();
	//Returns type of Student to the caller
	//I left it for the implementations to expand on the getType method themselves...
	//...since I thought it would be beneficial for the system, as it will allow you...
	//...to add more students i.e. PhD or another type in the future instead
}
