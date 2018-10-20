package tests;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Test;

import main.Name;
import main.PGResearchStudent;
import main.Student;
import main.SystemManager;

//Author: Mehdi Naderi Varandi

public class StudentIDJUnit {

	@Test
	public void uniquenessTest() throws FileNotFoundException, IOException, IllegalArgumentException, IndexOutOfBoundsException, NoSuchFieldException {
		//This method test just tests that each student ID generated is unique
		//As per the specification, each student should have a unique student ID assigned to them (and hence, no other student should have the same student ID)
		
		//Note to readers: I assumed that each studentID's prefix should be a random letter unlike smart card, because the specification did not mention if it had to...
		//...be an initial (hence, names such as james smith may have a0123 instead of j0123 as student ID).
		
		SystemManager system = new SystemManager();
		
		Student optimus=new PGResearchStudent (new Name("Optimus", "Prime"),"13/02/1985",system.addSupervisor("Jon Snow"));
		system.registerStudent(optimus);
		
		Student maximus=new PGResearchStudent (new Name("Maximus", "Decimus"),"13/02/1981",system.addSupervisor("Jon Snow"));
		system.registerStudent(maximus);
		
		//Test 1:
		assertNotEquals(optimus.getStudentID(),maximus.getStudentID());
		System.out.println("Optimus card: " + optimus.getStudentID() + "\nMaximus card: " + maximus.getStudentID());
		
		
		
		
		
		Student marcus=new PGResearchStudent (new Name("Marcus", "Aurelius"),"13/02/1972",system.addSupervisor("Jon Snow"));
		system.registerStudent(marcus);
		
		//Test 2:
		assertNotEquals(marcus.getStudentID(),maximus.getStudentID());
		System.out.println("Marcus card: " + marcus.getStudentID() + "\nMaximus card: " + maximus.getStudentID());
		
		
		
		
		
		Student arthur=new PGResearchStudent (new Name("Arthur", "Pendragon"),"13/02/1985",system.addSupervisor("Jon Snow"));
		system.registerStudent(arthur);
		
		//Test 3: 
		assertNotEquals(arthur.getStudentID(),maximus.getStudentID());
		System.out.println("Arthur card: " + arthur.getStudentID() + "\nMaximus card: " + marcus.getStudentID());
	}
		
		

}
