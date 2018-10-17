package main;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//What does this class do? 
//
//--> This class ensures that the university can use several administrative functions that were outlined in the specification, such as:
//
//	1) Register a new student: registerStudent allows you to register a new student and create a new student ID & smart card for them
//	2) Get the number of students in UG, PGT or PGR: noOfStudents gets the size of the HashMaps which are incremented when a new student is added...
//	... allowing you to see how many students are registered as UG/PGT/PGR
//	3) Remove a student: terminateStudent removes a student completely from the university i.e. in the case that they left the course
//	4) Amend data: amendStudentData allows you to change data about a student - in this case, the method recreates the object with the new information...
//	...and amends the appropriate data structures to contain the new object (the student will still retain their student ID (i.e. why change it due to small change)

//	Author: Mehdi Naderi Varandi


public final class SystemManager {
	
	//FIELDS-------------------------------------------------------------------------------------------------------------------
	final static List<Module> modules=new ArrayList<Module>();
	//Holds module objects that are available for Taught students (UG/PGT)
	final static List<Supervisor> supervisors=new ArrayList<Supervisor>();
	//Holds all supervisors objects that are available to all PGR students
	
	final static Map<Student,StudentID> studentsUG = new HashMap<Student,StudentID>();
	//Stores information about all undergraduates student & their ID i.e. the student object and their ID
	final static Map<Student,SmartCards> smartcardsUG = new HashMap<Student,SmartCards>();
	//Stores information about all undergraduate students and their smart cards
	
	final static Map<Student,StudentID> studentsPGT = new HashMap<Student,StudentID>();
	//Stores information about all postgraduate taught student i.e. the student object and their ID
	final static Map<Student,SmartCards> smartcardsPGT = new HashMap<Student,SmartCards>();
	//Stores information about all postgraduate students and their smart cards
	
	final static Map<Student,StudentID> studentsPGR = new HashMap<Student,StudentID>();
	//Stores information about all postgraduate research student i.e. the student object and their ID
	final static Map<Student,SmartCards> smartcardsPGR = new HashMap<Student,SmartCards>();
	//Stores information about all postgraduate students and their smart cards
	
	
	final static String MODULE_FILE_LOCATION="src/ModuleInformation.txt";
	//Holds file location of all Modules that are available
	final static String SUPERVISOR_FILE_LOCATION="src/SupervisorInformation.txt";
	//Holds file location of all supervisors that are available
	
	//Note for markers: Please note that file locations are hard coded in, hence it may be necessary to change the file locations for text files such as Module Information or...
	//...supervisor information files - the hard coded values may need to be changed
	
	//-------------------------------------------------------------------------------------------------------------------------
	
	public SystemManager() throws FileNotFoundException, IOException{
		readFiles(); //Exceptions that may be thrown are FileNotFoundException & IOException, since the method readFiles is being called immediately
		//Reads files that contain all modules and supervisors available
	}
	
	
	public final int noOfStudents (TypeOfStudent typeOfStudent){
		//This method does the following functions:
		//	1) Returns number of UG/PGT/PGR students based on the size of the respective Hash Map
		//	...since every student is registered with a specific HashMap, it's easy to simply get the number of the specific student
		
		
		if (typeOfStudent==TypeOfStudent.UG){
			return (studentsUG.size()); //Returns number of UG students
		}
		else if (typeOfStudent==TypeOfStudent.PGT){
			return (studentsPGT.size());	//Returns number of PGT students
		}
		else if (typeOfStudent==TypeOfStudent.PGR){
			return (studentsPGR.size());	//Returns number of PGR students
		}
		else {
			return 0; 
		}
	}
	
	public final void registerStudent (Student student) throws IllegalArgumentException, IndexOutOfBoundsException, NoSuchFieldException{
		//This method does the following functions:
		//	1) It first checks what type of student the actual object parameter is i.e. UG/PGT/PGR
		//	2) It then generates a studentID and smart card and places them in the respective/appropriate HashMaps...
		//	...this allows you to have a student object KEY that has smart card/student ID as values...
		// 	...this makes it easier to find info about a specific student later on i.e. just find it by the object...
		//	...finally, in order to follow defensive programming, the method also throws an exception in case there is no type for the student somehow...
		//	...i.e. the student isn't one of the enumerated types (UG/PGT/PGR)
		
		
		if (studentsUG.containsKey(student) || studentsPGT.containsKey(student) || studentsPGR.containsKey(student)){
			throw new IllegalArgumentException("This student has already registered! You cannot register a student again!");
		}
		
		
		
		if (student.getType()==TypeOfStudent.UG){
			studentsUG.put(student, StudentID.createNewID(student,false, null));
			smartcardsUG.put(student, SmartCards.createNewCard(student,student.getName(),student.getBirthday(),student.getStudentID(), false, null));
		}
		else if (student.getType()==TypeOfStudent.PGT){
			studentsPGT.put(student, StudentID.createNewID(student,false, null));
			smartcardsPGT.put(student, SmartCards.createNewCard(student,student.getName(),student.getBirthday(),student.getStudentID(), false, null));
		}
		else if (student.getType()==TypeOfStudent.PGR){
			studentsPGR.put(student, StudentID.createNewID(student,false, null));
			smartcardsPGR.put(student, SmartCards.createNewCard(student,student.getName(),student.getBirthday(),student.getStudentID(), false, null));
		}
		else{
			throw new IllegalArgumentException ("This student does not seem to have a type i.e. they're not UG/PGT/PGR");
		}
	}
	
	public final Student getHashMapKey(StudentID studentID, TypeOfStudent typeOfStudent) {
		//Code that iterates overall all key sets in the appropriate HashMaps until the value passed...
		//... in (studentID) is found - which in that case, return the Key it's mapped to
		
		if (typeOfStudent==TypeOfStudent.UG){ //Checks UG HashMap only
			for (Student i: studentsUG.keySet()) { //Iterates over all the keys in the HashMap in order to return the correct key to replace
				if (studentsUG.get(i)==studentID) {
					return i;	//Returns key - this key can then be used to replace a student object later on
				}
			}
		}
		else if (typeOfStudent==TypeOfStudent.PGT){ //Checks PGT HashMap only
			for (Student i: studentsPGT.keySet()) {
				if (studentsPGT.get(i)==studentID) {
					return i;
				}
			}
		}
		else if (typeOfStudent==TypeOfStudent.PGR){ //Checks PGR HashMap only
			for (Student i: studentsPGR.keySet()) {
				if (studentsPGR.get(i)==studentID) {
					return i;
				}
			}
		}
		
		throw new IllegalArgumentException ("This point should not be reached - something has gone wrong");
		
	}
	
	public final void amendStudentData (StudentID studentID, Student student) throws IllegalArgumentException, IndexOutOfBoundsException, NoSuchFieldException{
		//This method does the following functions:
		//	1) Amend student data...
		//	...i.e. change data previously held/associated by the Student
		
		//Method receives a student ID (i.e. as the identifier) as well as a new student which will replace the previous student object
		//Hence, you'll now have a new student object that has changed data, but still is linked to the same studentID and smart Card
		//The reasoning for not replacing smart card/Student ID is:
		//	1) Specification does not allow you to issue more than 1 smart card
		//	2) Even if say student record is changed, your physical smart card dosen't change unless you get a new one from Student services
		
		//Conditional statement that checks whether the StudentID that is passed in actually exists
		if (studentsUG.containsValue(studentID) || studentsUG.containsValue(studentID) || studentsUG.containsValue(studentID)) {
			if (studentsUG.containsValue(studentID)) {
				if (!(student.getType()==TypeOfStudent.UG)) {
					throw new IllegalArgumentException ("Error: This ID is issued to a UG - but this new data is not UG!");
				}
				
				Student oldStudent=(getHashMapKey(studentID,TypeOfStudent.UG));
				
				studentsUG.remove(oldStudent);
				studentsUG.put(student, studentID);
				
				StudentID.createNewID(student, true, oldStudent);
				SmartCards.createNewCard(student, student.getName(), student.getBirthday(), student.getStudentID(), true, oldStudent);

				smartcardsUG.put(student, student.getCard());
			}
			else if (studentsPGT.containsValue(studentID)) {
				if (studentsPGT.containsValue(studentID)) {
					if (!(student.getType()==TypeOfStudent.PGT)) {
						throw new IllegalArgumentException ("Error: This ID is issued to a PGT - but this new data is not UG!");
					}

					Student oldStudent=(getHashMapKey(studentID,TypeOfStudent.PGT));
					
					studentsPGT.remove(oldStudent);
					studentsPGT.put(student, studentID);
					
					StudentID.createNewID(student, true, oldStudent);
					SmartCards.createNewCard(student, student.getName(), student.getBirthday(), student.getStudentID(), true, oldStudent);

					smartcardsPGT.put(student, student.getCard());
				}
			}
			else if (studentsPGR.containsValue(studentID)) {
				if (studentsPGR.containsValue(studentID)) {
					if (!(student.getType()==TypeOfStudent.PGR)) {
						throw new IllegalArgumentException ("Error: This ID is issued to a PGR - but this new data is not UG!");
					}

					Student oldStudent=(getHashMapKey(studentID,TypeOfStudent.PGR));
					
					studentsPGR.remove(oldStudent);
					studentsPGR.put(student, studentID);
					
					StudentID.createNewID(student, true, oldStudent);
					SmartCards.createNewCard(student, student.getName(), student.getBirthday(), student.getStudentID(), true, oldStudent);

					smartcardsPGR.put(student, student.getCard());
				}
			}
			
		}
		else {
			throw new IllegalArgumentException ("This student ID is not in any records - this ID does not exist");
		}
		
	}
	
	public final String checkStudent(Student student){
		//This method checks whether the student is correctly registered - such as:
		//	1) Does the student have the correct number of modules (They need 120 credits for UG for example)
		//	2) Does the PG student have a supervisor allocated to them 
		if (student.getType()==TypeOfStudent.UG){
			if (student.getCheck()<120){
				return ("This student needs to be registered for modules! They only have " + student.getCheck() + " credits");
			}
		
		else if ((student.getType()==TypeOfStudent.PGT)){
			if (student.getCheck()<180){
				return ("This student needs to be registered for more modules! They only have" + 
			    student.getCheck() + "credits");
			}
		}
		else if ((student.getType()==TypeOfStudent.PGR)){
			if (student.getCheck()==1){
				return ("This student has a supervisor correctly allocated");
			}
			else{
				return ("This student needs a supervisor allocated to them!");
			}
		}
			
			
		}
		
		
		return null;
	}
	
	public final void terminateStudent (StudentID studentID) throws IllegalArgumentException{
		//This method does the following functions:
		//	1) Destroy specific student data from the HashMap data structures, since the student is i.e. leaving the university
	
		
		if (studentsUG.containsValue(studentID) || studentsUG.containsValue(studentID) || studentsUG.containsValue(studentID)) {
			Student holdStudent=(getHashMapKey(studentID,TypeOfStudent.UG));

			if (studentsUG.containsValue(studentID)) {
				studentsUG.remove((getHashMapKey(studentID,TypeOfStudent.UG)));
				smartcardsUG.remove((getHashMapKey(studentID,TypeOfStudent.UG)));
			}
			else if (studentsPGT.containsValue(studentID)) {
				studentsPGR.remove((getHashMapKey(studentID,TypeOfStudent.UG)));
				smartcardsPGR.remove((getHashMapKey(studentID,TypeOfStudent.UG)));
			}
			else if (studentsPGR.containsValue(studentID)) {
				studentsPGR.remove((getHashMapKey(studentID,TypeOfStudent.UG)));
				smartcardsPGR.remove((getHashMapKey(studentID,TypeOfStudent.UG)));
			}
			
			StudentID.removeID(holdStudent);
			SmartCards.removeCard(holdStudent);
			
		}
		else{
			throw new IllegalArgumentException ("This student ID does not unfortunately exist");
		}

	}
	
	private final void readFiles () throws FileNotFoundException, IOException{
		//The method does the following functions:
		//	1) Read module.txt file and return a object for each line...
		//	hence, each module will be a new object containing its own respective code, name and credits
		//	2) Read supervisor file and return a name object for each line...
		//	...hence each object will be the supervisor/their name to be allocated to a PG student
		
		
		
		//File reader to get names of all available modules
		BufferedReader moduleReader
	 	   = new BufferedReader(new FileReader(MODULE_FILE_LOCATION));
		//Creates a new BufferedReader which can often be more efficient
			
			
		String fileLine;
		//Reads first line of the file
			
		while (!(moduleReader==null)){ //Keeps checking whether the file is null or not
				//i.e. keep reading while there is data, but if you reach the end then close the file
			fileLine=moduleReader.readLine();
			
			if (fileLine==null){
				break;
			}
			
			modules.add(Module.getInstance(fileLine));
		}
			
		moduleReader.close(); //Closes the file
		//Note that there can also be a IO exception thrown here if anything goes wrong
		
		
		//File reader to get names of all available supervisors
		BufferedReader supervisorReader
	 	   = new BufferedReader(new FileReader(SUPERVISOR_FILE_LOCATION));
		//Creates a new BufferedReader which can often be more efficient
			
			
		String supervisorLine;
		//Reads first line of the file
			
		while (!(supervisorReader==null)){ //Keeps checking whether the file is null or not
				//i.e. keep reading while there is data, but if you reach the end then close the file
			supervisorLine=supervisorReader.readLine();
			
			if (supervisorLine==null){
				break;
			}
			
			supervisors.add(Supervisor.getInstance(supervisorLine));
		}
			
		supervisorReader.close(); //Closes the file
	}
	
	public final void addModules(Student student,String moduleCode) throws IllegalArgumentException{
		//The method does the following functions:
		// 1) Receives the student (which we'll be adding the module to) & the module code
		// 2) Checks that the student is actually UG or PGT (PGR do not sign up for modules)
		// 3) Checks that the module code that is received actually exists
		
		int index=0;
		
		for (int loopCounter=0; loopCounter<modules.size();loopCounter++){
			
			if (student.getType()==TypeOfStudent.PGR){
				throw new IllegalArgumentException ("PGR students do not sign up for modules!");
			}
			else if (modules.get(loopCounter).getModuleCode().equals(moduleCode)){
				index=loopCounter;
				break;
			}
			
			else if(modules.size()<(loopCounter+1)){
				throw new IllegalArgumentException ("This module code does not exist!");
			}
			
		}
		((TaughtStudent) student).addModule(modules.get(index));
		
	}
	
	public final Supervisor addSupervisor(String name) throws IllegalArgumentException{
		//Validation method that checks whether the supervisor string passed in actually even exists in the first place
		//This allows you to ensure that supervisors that do not exist are not linked to reserach students by accidents
		
		int index=0;
		
		for (int loopCounter=0; loopCounter<supervisors.size();loopCounter++){
			if (supervisors.get(loopCounter).getName().equals(name)){
				index=loopCounter;
				break;
			}
			
			else if(supervisors.size()<(loopCounter+1)){
				throw new IllegalArgumentException ("This supervisor does not exist!");
			}
		}
		
		return (supervisors.get(index));
	}
	
	public final String studentSummary(Student student) throws IllegalArgumentException, NoSuchFieldException{
		//This method allows you to get the summary of all the students stats such as name, date of birth, student ID, smartcard and so on
		
		if (student.getCard()==null || student.getStudentID()==null) {
			return (student + "\n This student has not been registered; as a result, they do not have any student ID or smart card");
		}
		else {
			return (student + "\n Student ID: " + student.getStudentID() + "\n Smartcard: " + student.getCard() + "\n ----------------------------------");
		}
		
	}
	
}
	
	

	
	

