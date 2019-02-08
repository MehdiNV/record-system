package main.com.mehdinv.record_system.test;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Test;

import main.com.mehdinv.record_system.Name;
import main.com.mehdinv.record_system.students.PGResearchStudent;
import main.com.mehdinv.record_system.students.Student;
import main.com.mehdinv.record_system.SystemManager;
import main.com.mehdinv.record_system.students.UndergraduateStudent;

public class SmartcardsJUnit {

	@Test
	public void uniquenessTest() throws FileNotFoundException, IOException, IllegalArgumentException, IndexOutOfBoundsException, NoSuchFieldException {
		//This method main.com.mehdinv.record_system.test just tests that each smart card generated is unique
		//As per the specification, each student should have a unique smart card assigned to them (and hence, no other student should have the same smart card
		
		SystemManager system = new SystemManager();
		
		Student inigo=new PGResearchStudent (new Name("Inigo", "Montoya"),"13/02/1985",system.addSupervisor("Jon Snow"));
		system.registerStudent(inigo);
		
		Student tintin=new PGResearchStudent (new Name("Tin", "Tin"),"13/02/1981",system.addSupervisor("Jon Snow"));
		system.registerStudent(tintin);
		
		//Test 1:
		assertNotEquals(inigo.getCard(),tintin.getCard());
		System.out.println("Inigo card:" + inigo.getCard() + "\nTintin card:" + tintin.getCard());
		
		
		
		
		
		Student bianca=new PGResearchStudent (new Name("Bianca", "Castafiore"),"13/02/1972",system.addSupervisor("Jon Snow"));
		system.registerStudent(bianca);
		
		//Test 2:
		assertNotEquals(bianca.getCard(),tintin.getCard());
		System.out.println("Bianca card:" + bianca.getCard() + "\nTintin card:" + tintin.getCard());
		
		
		
		
		
		Student arthur=new PGResearchStudent (new Name("Arthur", "Pendragon"),"13/02/1985",system.addSupervisor("Jon Snow"));
		system.registerStudent(arthur);
		
		//Test 3: 
		assertNotEquals(arthur.getCard(),bianca.getCard());
		System.out.println("Inigo card:" + arthur.getCard() + "\nTintin card:" + bianca.getCard());

	}
	
	@Test
	public void getNameTest() throws FileNotFoundException, IOException, IllegalArgumentException, IndexOutOfBoundsException, NoSuchFieldException{
		SystemManager system=new SystemManager();
		Student harry=new UndergraduateStudent(new Name("Harry","Potter"),"21/07/1995");
		system.registerStudent(harry);
		//Tests that the smart card class is able to correctly return the students name
		
		assertEquals("Harry Potter",String.valueOf(harry.getCard().getName()));
	}


	
}
