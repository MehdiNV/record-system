package tests;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Test;

import main.Name;
import main.ResearchStudent;
import main.Student;
import main.SystemManager;
import main.TaughtStudent;
import main.TypeOfStudent;
import main.PGResearchStudent;

//JUnit that tests both the Abstract class (Research student) as well as by extension, its implementations (PGR implementations)
//Specifically, we check that the functionality of the class and its subclasses are performing as expected, such as by checking...
//...its methods i.e. testing whether the program can filter out invalid inputs/ages via its constructor (note that the constructor actually calls...
//...a method called isBadAge to check).

//Author: Mehdi Naderi Varandi

public class ResearchStudentJUnit {

	@Test(expected = IllegalArgumentException.class)
	public void testMinimumAgePGRInputInvalid() throws FileNotFoundException, IOException   {
		//Test method that checks whether ResearchStudent class is correctly preventing students that are under 20 from being created
		//Type of input: Invalid
		SystemManager system=new SystemManager();
		
		Student vito=new PGResearchStudent((new Name("Vito","Corleone")),"26/11/2001", system.addSupervisor("Jon Snow"));
		
		
		fail ("This line should not be reached, because I expect to get an exception from line 20 (where student object is declared)"
				+ ". I expected to see this exception because the student is too young for a PGR (born in 2001)");
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testMaxAgePGRInputInvalid() throws FileNotFoundException, IOException   {
		//Test method that checks whether ResearchStudent class is correctly preventing students that are over 105 from being created
		//Type of input: Invalid

		SystemManager system=new SystemManager();
		
		Student moby=new PGResearchStudent((new Name("Moby","Dick")),"26/11/1911", system.addSupervisor("Jon Snow"));
		
		
		fail ("This line should not be reached, because I expect to get an exception from line 20 (where student object is declared)"
				+ ". I expected to see this exception because the student is too young for a PGR (born in 2001)");
		
	}
	
	@Test
	public void testAgePGRInputValid() throws FileNotFoundException, IOException   {
		//Test method that checks whether the ResearchStudent class is getting the age of the Student correctly
		//Type of input: Valid

		SystemManager system=new SystemManager();
		Student atticus=new PGResearchStudent ((new Name("Atticus","Finch")),"18/06/1997", system.addSupervisor("Jon Snow"));
		
		assertEquals(20,((ResearchStudent) atticus).getAge());
		
	}

	@Test
	public void testAgePGRInputValidExtreme() throws FileNotFoundException, IOException   {
		//Test method that checks whether PGR class is letting this just-turned 20 year old to be created...
		//...in this method, the method should generate the students age as 20 (since they just turned 20 at this current date)
		//Type of input: Valid Extreme
		SystemManager system=new SystemManager();
		
		Student dorian=new PGResearchStudent((new Name("Dorian","Gray")),"07/05/1998", system.addSupervisor("Jon Snow"));
		
		assertEquals(20,((ResearchStudent) dorian).getAge());
		
	}

	@Test
	public void getTypePGRTest() throws FileNotFoundException, IOException   {
		//Test method that checks whether the PGR object is correctly returning its type as PGR

		SystemManager system=new SystemManager();
		
		Student indiana=new PGResearchStudent((new Name("Indiana","Jones")),"07/05/1994", system.addSupervisor("Jon Snow"));
		//Creates new postgrad research student and sets their supervisor to be Jon Snow
		
		assertEquals(TypeOfStudent.PGR,indiana.getType());
		//getType method in PGResearchStudent should return PGR
	}

	@Test
	public void getNameTest() throws FileNotFoundException, IOException   {
		//Test method that checks whether the PGR object is correctly returning a defensive copy of Name
		Name name=new Name("James","Bond");

		SystemManager system=new SystemManager();
		
		Student james=new PGResearchStudent(name,"07/05/1992", system.addSupervisor("Jon Snow"));
		
		assertEquals(String.valueOf(name),String.valueOf(james.getName()));
		//Tests that the defensive copy that is returned is correct - as in, both are "James Bond"
	}

	public void getCheck() throws FileNotFoundException, IOException   {
		SystemManager system=new SystemManager(); //Creates new SystemManager object for testing
		
		Student jules=new PGResearchStudent((new Name("Jules","Winnfield")),"07/05/1983", system.addSupervisor("Jon Snow"));
		//Creates a new postgrad research student and again sets their supervisor to Jon Snow
		
		assertEquals(1,((ResearchStudent) jules).getSupervisor());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void getSupervisorTest() throws FileNotFoundException, IOException   {
		//Test method that checks whether the program is returning the correct defensive copy of the students supervisor
		
		SystemManager system=new SystemManager();
		
		Student moby=new PGResearchStudent((new Name("Moby","Dick")),"26/11/1911", system.addSupervisor("Eddard Stark"));
		
		assertEquals("Eddard Stark",((ResearchStudent) moby).getSupervisor());
		
	}
}
