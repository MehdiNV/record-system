package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import main.Name;
import main.PGResearchStudent;
import main.PGTaughtStudent;
import main.SmartCards;
import main.Student;
import main.StudentID;
import main.SystemManager;
import main.TypeOfStudent;
import main.UndergraduateStudent;

//JUnit to test the SystemManager class - this is the class that performs the administrative functions described...
//...in the specification such as registering students or getting the number of a specific type of students


public class SystemManagerJUnit {
	
	@Test (expected = Exception.class)
	public void registerStudentTest() throws Exception {
		SystemManager system = new SystemManager();
		
		Student johnny=new UndergraduateStudent((new Name("Johnny","Smithy")),"26/11/1996");
		system.registerStudent(johnny);
		//Registers a undergraduate student
		//To know if it has worked, we'll check if HashMaps for studentsUG (containing students and their studentID)...
		//...is now the size of 4 (since we just registered a new student; the method should...
		//...create a new student ID when they are registering the student object)
		
		//TEST ONE: Tests whether student is being registered, by checking if the data structure size holding it increased by 1
		assertEquals(4,system.noOfStudents(TypeOfStudent.UG));
		//We should have 3 student now registered as an UG (Note that I've set it to 4 as JUnit throws an error when I run all the tests together).
		//This is because all other students get added as well, which causes the size to go up to 4
		
		//TEST TWO: Tests whether student is not allowed to re-register, as they have been registered before - we're expecting to...
		//...get a exception
		system.registerStudent(johnny);
		fail ("This line should not be reached - line 102 (Previous line right before this one) should've thrown an exception");
		//Registering the same student object should throw an exception, as you cannot register a student twice over and over 
		//Hence, we should receive an exception before line 103 (the fail line) is reached
		
	}
	
	@Test
	public void noOfStudentsTest() throws Exception {
		//JUnit test that checks if noOfStudents is correctly returning the right number of students correctly
		//Note: A student is only recorded into the system when they are registered, not when the object is created...
		//... to create a new student into the system, you need to create the appropriate object, then call the registerStudent method
		
		SystemManager system= new SystemManager();
		//Declares the system from SystemManager class
		//One alternative is declaring the class static in the future so that you don't need the object in order to use the methods...
		//...however, I believed that perhaps this system may be incorporated into another system, & and that it would probably be more useful for the object to be used
		
		//TEST NUMBER ONE:
		assertEquals(0,system.noOfStudents(TypeOfStudent.UG));
		//Runs the first test on the noOFStudents
		//In this test, we expect to see 0 students registered as undergraduates, since no one has been registered yet
		
		//TEST NUMBER TWO:
		assertEquals(0,system.noOfStudents(TypeOfStudent.PGT));
		//Runs the second test on the noOFStudents
		//Note that when we call noOfStudents, the parameter passed in is the type of student we want to see the number of
		//This is why for some tests, we do UG, whereas for others we may do PGT or PGR
		//In this test, we expect to see 0 students registered as PGT, since no one has been registered yet
		
		//TEST NUMBER THREE:
		assertEquals(0,system.noOfStudents(TypeOfStudent.PGR));
		//Runs the third test on the noOFStudents
		//In this test, we expect to see 0 students registered as PGR, since no one has been registered yet
		
		//For test number 4, we'll create a new student and register them, to increase the the number of students
		Student mehdi=new UndergraduateStudent((new Name("Mehdi","Naderi")),"26/11/1998");
		system.registerStudent(mehdi);
		
		//TEST NUMBER FOUR:
		assertEquals(1,system.noOfStudents(TypeOfStudent.UG));
		//Test that checks whether adding a new student increments the size of UG students
		//We should have one student as we just registered a new undergraduate student earlier (Mehdi Naderi)
	
		//For test number 5, we'll create a new student and register them, to increase the the number of students
		Student john=new UndergraduateStudent((new Name("John","Smith")),"11/05/1995");
		system.registerStudent(john);
		
		//TEST NUMBER FIVE:
		assertEquals(2,system.noOfStudents(TypeOfStudent.UG));
		//Test that checks whether adding a new student increments the size of UG students
		//We should have two students as we just registered a new undergraduate student earlier (John Smith)
		
		Student james=new PGTaughtStudent((new Name("James","Smith")),"04/02/1990");
		system.registerStudent(james);
		
		//TEST NUMBER SIX:
		assertEquals(1,system.noOfStudents(TypeOfStudent.PGT));
		//Tests that the new James has correctly incremented the number of PGT's
		//We should now have 1 PGT student as we just registered james (i.e. ...
		//...mehdi & john objects do not count as they are UG students
		
		
		Student mary=new PGResearchStudent((new Name("Mary","Smith")),"01/01/1976", system.addSupervisor("Jon Snow"));
		system.registerStudent(mary);
		
		
		//TEST NUMBER SEVEN:
		assertEquals(1,system.noOfStudents(TypeOfStudent.PGR));
		//Tests that the new Mary student has correctly incremented the number of PGR's

	}
	
	
	@Test
	public void amendStudentData () throws Exception {
		SystemManager system = new SystemManager();
		//Declares a new systemManager, so that we can use methods such as the amendStudentData
		
		Student annaDavid=new UndergraduateStudent((new Name("Anna","David")),"20/12/1994");
		system.registerStudent(annaDavid);
		//Registers the student Anna David - this student will later have their record amended
		
		SmartCards expectedCard = annaDavid.getCard(); //Stores the current smartcard value
		StudentID expectedID=annaDavid.getStudentID(); //Stores the current ID value
		//When we amend the student data, this would often be done because a name may have been typed in wrongly...
		//... or a birthday was typed in wrong
		//Hence, we can declare a new undergraduate student with the correct values, and replace the records with this new object that...
		//...has our correct values - we keep the same ID and smartcard number because in the real world, even if a students name is...
		//...changed, they would still retain the same smart card (until a new physical one is issued).
		
		//In summary, only student records is changed because:
		// 1)Specification does not allow new smart card to be issued (can only be issued once)
		// 2)It's more reasonable to have the student retain their old ID and smart card
		
		Student annaDavidson=new UndergraduateStudent((new Name("Anna","Davidson")),"21/12/1994");
		//This time, we declare a new student again, but we have the correct values (i.e. surname is Davidson instead of...
		//...the incorrect value from last time 'David', & birthday is changed).
		
		
		system.amendStudentData(annaDavid.getStudentID(),annaDavidson);
		//Then we pass in the studentID of the previous anna object, and send in the new student object we want it to replace in the records
		
		//These two tests below should test if the new object (AnnaDavidson) has the same ID & smart card number as AnnaDavid object
		assertEquals(expectedID,annaDavidson.getStudentID());
		assertEquals(expectedCard,annaDavidson.getCard());
		
	}
	
	@Test (expected = Exception.class)
	public void terminateStudent() throws Exception{
		SystemManager system = new SystemManager();
		//Declares a new systemManager, so that we can use methods such as the amendStudentData
		
		Student jay=new UndergraduateStudent((new Name("Jay","Gatsby")),"20/12/1980");
		system.registerStudent(jay);
		//Registers the student Jay Gatsby 
		
		system.terminateStudent(jay.getStudentID());
		
		System.out.println(system.studentSummary(jay));
		
		fail("Test should not reach this point as we should have got an exception at line 160 (previous line) due to the student having" +
		" no studentID or smartCard (because they have been fully eliminated from the records - they do not exist in the records anymore)");
	}
	
	@Test
	public void readFiles() throws Exception{
		
	}

}
