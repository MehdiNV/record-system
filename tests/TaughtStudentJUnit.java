package tests;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Test;

import main.Name;
import main.PGResearchStudent;
import main.PGTaughtStudent;
import main.Student;
import main.SystemManager;
import main.TaughtStudent;
import main.TypeOfStudent;
import main.UndergraduateStudent;

//JUnit that tests both the Abstract class (Taught student) as well as by extension, its implementations (UG & PGT implementations)
//Specifically, we check that the functionality of the class and its subclasses are performing as expected, such as by checking...
//...its methods i.e. testing whether the program can filter out invalid inputs/ages via its constructor (note that the constructor actually calls...
//...a method called isBadAge to check).

//Author: Mehdi Naderi Varandi
//Student ID: 170725695

public class TaughtStudentJUnit {
	
	@Test(expected = IllegalArgumentException.class)
	public void testMinimumAgeUGInputInvalid() throws FileNotFoundException, IOException {
		//Test method that checks whether UG class is correctly preventing students that are under 18 from being created
		//Type of input: Invalid

		SystemManager system=new SystemManager();
		
		Student mehdi=new UndergraduateStudent((new Name("Mehdi","Naderi")),"26/11/2001");
		
		
		fail ("This line should not be reached, because I expect to get an exception from line 20 (where student object is declared)"
				+ ". I expected to see this exception because the student is too young for a UG (born in 2001)");
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testMaxAgeUGInputInvalid() throws FileNotFoundException, IOException   {
		//Test method that checks whether UG class is correctly preventing students that are over 105 from being created
		//Type of input: Invalid
		
		SystemManager system=new SystemManager();
		
		Student mehdi=new UndergraduateStudent((new Name("Mehdi","Naderi")),"26/11/1911");
		
		
		fail ("This line should not be reached, because I expect to get an exception from line 20 (where student object is declared)"
				+ ". I expected to see this exception because the student is too young for a UG (born in 2001)");
		
	}
	
	@Test
	public void testAgeUGInputValid() throws FileNotFoundException, IOException   {
		//Test method that checks whether the TaughtStudent/Undergraduate class is getting the age of the Student correctly
		//Type of input: Valid
		
		SystemManager system=new SystemManager();
		
		Student mehdi=new UndergraduateStudent((new Name("Mehdi","Naderi")),"26/11/1998");
		
		assertEquals(19,((TaughtStudent) mehdi).getAge());
		
	}

	@Test
	public void testAgeUGInputValidExtreme() throws FileNotFoundException, IOException   {
		//Test method that checks whether UG class is letting this just-turned 18 year old to be created...
		//...in this method, the method should generate the students age as 18 (since they just turned 18 at this current date)
		//Type of input: Valid Extreme
		
		SystemManager system=new SystemManager();
		
		Student mehdi=new UndergraduateStudent((new Name("Mehdi","Naderi")),"06/05/2000");
		
		assertEquals(18,((TaughtStudent) mehdi).getAge());
		
	}

	@Test
	public void testAgeUGInputInvalidExtreme()   {
		//Test method that checks whether UG class is letting this just-turned 18 year old to be created...
		//...in this method, the method should generate the students age as 18 (since they just turned 18 at this current date)
		//Type of input: Invalid Extreme
		
		Student mehdi=new UndergraduateStudent((new Name("Mehdi","Naderi")),"07/05/1998");
		
		assertEquals(20,((TaughtStudent) mehdi).getAge());
		//Age of object should be 20 based on the date inputed (07/05/1998)
	}

	@Test
	public void getTypeUGTest()   {
		//Test method that checks whether the UG object is correctly returning its type as UG
		
		
		Student mehdi=new UndergraduateStudent((new Name("Mehdi","Naderi")),"07/05/1998");
		
		assertEquals(TypeOfStudent.UG,mehdi.getType());
		//getType method in UndergraduateStudent should return UG
	}

	@Test
	public void getTypePGTTest()   {
		//Test method that checks whether the PGT object is correctly returning its type as PGT
		
		
		Student mehdi=new PGTaughtStudent((new Name("Mehdi","Naderi")),"07/05/1990");
		
		assertEquals(TypeOfStudent.PGT,mehdi.getType());
		//getType method in Postgraduate taught should return PGT
	}

	@Test
	public void getEmptyModulesTest() throws FileNotFoundException, IOException, IllegalArgumentException, IndexOutOfBoundsException, NoSuchFieldException   {
		//Test method that checks whether the PGT object is correctly returning its type as PGT
		
		SystemManager system=new SystemManager();
		
		Student mehdi=new PGTaughtStudent((new Name("Mehdi","Naderi")),"07/05/1990");
		system.registerStudent(mehdi);
		
		assertEquals("This student is not registered for any modules",((TaughtStudent) mehdi).moduleList());
		//method should return that the student is not registered for any modules since we haven't added any modules to the object yet
	}

	@Test
	public void getModulesTest() throws FileNotFoundException, IOException, IllegalArgumentException, IndexOutOfBoundsException, NoSuchFieldException   {
		//Test method that checks whether the PGT object is correctly returning its type as PGT
		
		SystemManager system=new SystemManager();
		
		Student mehdi=new PGTaughtStudent((new Name("Mehdi","Naderi")),"07/05/1990");
		system.registerStudent(mehdi);
		
		system.addModules(mehdi, "CSC1022");
		//In this test, we'll register the student for Programming 1
		
		assertNotEquals("This student is not registered for any modules",((TaughtStudent) mehdi).moduleList());
		//method should return that the student is not registered for any modules since we haven't added any modules to the object yet
		//so in this test, we should see that the previous string isn't returned anymore, as the student actually has a module now
		//Note that I have not tested the output we get when you have modules registered, as it was difficult to replicate all the \n and...
		//...strings placed
	}
	
	@Test
	public void getCardTest() throws FileNotFoundException, IOException, IllegalArgumentException, IndexOutOfBoundsException, NoSuchFieldException   {
		//Test method that checks whether TaughtStudent is returning the correct smart card
		
		SystemManager system=new SystemManager();
		
		Student john=new UndergraduateStudent((new Name("John","Davidson")),"14/06/1994");
		system.registerStudent(john);
		
		assertEquals("JD-2018-0",String.valueOf(john.getCard()));
	}

	@Test
	public void getCheckUGTest() throws IllegalArgumentException, IndexOutOfBoundsException, NoSuchFieldException, FileNotFoundException, IOException   {
		//Test method that checks whether TaughtStudent is returning the correct smart card
		
		SystemManager system=new SystemManager();
		
		Student mehdi=new UndergraduateStudent((new Name("Mehdi","Naderi")),"26/11/1998");
		system.registerStudent(mehdi);
		
		system.addModules(mehdi, "CSC1021");
		//In this test, we'll register the student for Programming 1
		
		//Test 1: We should have 20 credits since we just added CSC1021 (which has 20 credits) to the student
		assertEquals(20,mehdi.getCheck());
		
		system.addModules(mehdi, "CSC1022");
		//Test 2: We should have 40 credits since we just added CSC1022 (which has 20 credits) to the student
		assertEquals(40,mehdi.getCheck());
		
		
		system.addModules(mehdi, "CSC1023");
		//Test 3: We should have 60 credits since we just added CSC1023 (which has 20 credits) to the student
		assertEquals(60,mehdi.getCheck());
		
		
		system.addModules(mehdi, "CSC1024");
		//Test 4: We should have 80 credits since we just added CSC1024 (which has 20 credits) to the student
		assertEquals(80,mehdi.getCheck());
		
		system.addModules(mehdi, "CSC1025");
		//Test 5: We should have 100 credits since we just added CSC1025 (which has 20 credits) to the student
		assertEquals(100,mehdi.getCheck());
		
		system.addModules(mehdi, "CSC1026");
		//Test 6: We should have 120 credits since we just added CSC1026 (which has 20 credits) to the student
		assertEquals(120,mehdi.getCheck());
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void getCheckTestUGInvalid() throws FileNotFoundException, IOException, IllegalArgumentException, IndexOutOfBoundsException, NoSuchFieldException   {
		//Test method that checks whether TaughtStudent is returning the correct smart card
		
		SystemManager system=new SystemManager();
		
		Student mehdi=new UndergraduateStudent((new Name("Mehdi","Naderi")),"26/11/1998");
		system.registerStudent(mehdi);
		
		system.addModules(mehdi, "CSC1021");
		system.addModules(mehdi, "CSC1022");
		system.addModules(mehdi, "CSC1023");
		system.addModules(mehdi, "CSC1024");
		system.addModules(mehdi, "CSC1025");
		system.addModules(mehdi, "CSC1026");
		system.addModules(mehdi, "CSC2021");
		
		fail("This line should not be reached - by adding CSC2021, we should have exceeded the maximum credits that a UG could have"
				+ " and hence get a exception");
	}

	@Test (expected = Exception.class)
	public void getCheckTestPGTInvalid() throws FileNotFoundException, IOException, IllegalArgumentException, IndexOutOfBoundsException, NoSuchFieldException   {
		//Test method that checks whether TaughtStudent is returning the correct smart card
		
		SystemManager system=new SystemManager();
		
		Student mehdi=new PGTaughtStudent((new Name("Mehdi","Varandi")),"26/11/1998");
		system.registerStudent(mehdi);
		
		system.addModules(mehdi, "CSC1021");
		system.addModules(mehdi, "CSC1022");
		system.addModules(mehdi, "CSC1023");
		system.addModules(mehdi, "CSC1024");
		system.addModules(mehdi, "CSC1025");
		system.addModules(mehdi, "CSC1026");
		system.addModules(mehdi, "CSC2021");
		system.addModules(mehdi, "CSC1022");
		system.addModules(mehdi, "CSC1023");
		system.addModules(mehdi, "CSC2024");
		
		fail("This line should not be reached - by adding CSC2024, we should have exceeded the maximum credits that a PGT could have"
				+ " and hence get a exception");
	}

	@Test
	public void getCheckPGTTest() throws FileNotFoundException, IOException, IllegalArgumentException, IndexOutOfBoundsException, NoSuchFieldException   {
		//Test method that checks whether TaughtStudent is returning the correct smart card
		
		SystemManager system=new SystemManager();
		
		Student john=new PGTaughtStudent((new Name("John","Smith")),"26/11/1998");
		system.registerStudent(john);
		
		system.addModules(john, "CSC1021");
		//In this test, we'll register the student for Programming 1
		
		//Test 1: We should have 20 credits since we just added CSC1021 (which has 20 credits) to the student
		assertEquals(20,john.getCheck());
		
		system.addModules(john, "CSC1022");
		//Test 2: We should have 40 credits since we just added CSC1022 (which has 20 credits) to the student
		assertEquals(40,john.getCheck());
		
		system.addModules(john, "CSC1023");
		//Test 3: We should have 60 credits since we just added CSC1023 (which has 20 credits) to the student
		assertEquals(60,john.getCheck());
		
		system.addModules(john, "CSC1024");
		//Test 4: We should have 80 credits since we just added CSC1024 (which has 20 credits) to the student
		assertEquals(80,john.getCheck());
		
		system.addModules(john, "CSC1025");
		//Test 5: We should have 100 credits since we just added CSC1025 (which has 20 credits) to the student
		assertEquals(100,john.getCheck());
		
		system.addModules(john, "CSC1026");
		//Test 6: We should have 120 credits since we just added CSC1026 (which has 20 credits) to the student
		assertEquals(120,john.getCheck());
		
		system.addModules(john, "CSC2021");
		//Test 7: We should have 140 credits since we just added CSC2021 (which has 20 credits) to the student
		assertEquals(140,john.getCheck());
		
		system.addModules(john, "CSC2022");
		//Test 8: We should have 160 credits since we just added CSC2022 (which has 20 credits) to the student
		assertEquals(160,john.getCheck());
		
		system.addModules(john, "CSC2023");
		//Test 9: We should have 180 credits since we just added CSC2023 (which has 20 credits) to the student
		assertEquals(180,john.getCheck());
		
	}
	

	@Test
	public void getNameTest()   {
		//Test method that checks whether the PGR object is correctly returning a defensive copy of Name
		
		
		Name name=new Name("Forrest","Gump");
		
		Student forrest=new UndergraduateStudent(name,"07/05/1987");
		
		assertEquals(String.valueOf(name),String.valueOf(forrest.getName()));
		//Tests that the defensive copy that is returned is correct - as in, both are "Forrest Gump"
		
	}
	
	
	
}
