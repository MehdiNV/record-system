package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import main.Module;
import main.SystemManager;

//JUnit that tests the getters & the getInstance method simultaneously 

//Normally, when the systemManager is created, it'll read from a file all the modules, and pass each line into the Module class...
//...the class then creates a new object for every line. In this JUnit, we'll instead forcefully create the modules via the getInstance method...
//...by passing in strings of some modules ourselves. Note that however in the actual system, this method is done via file reading; the test implementation below...
//...is to simply check that the class works

//Author: Mehdi Naderi Varandi

public class ModuleJUnit {

	@Test
	public void getModuleCodeTest(){
		Module testModule=Module.getInstance("CSC1021, Programming I, 20");
		
		assertEquals("CSC1021",testModule.getModuleCode());
	}

	@Test
	public void getModuleNameTest(){
		Module testModule=Module.getInstance("CSC1021, Programming I, 20");
		
		assertEquals("Programming I",testModule.getModuleName());
	}
	
	@Test
	public void getModuleCreditsTest(){
		Module testModule=Module.getInstance("CSC1021, Programming I, 20");
		
		assertEquals("20",testModule.getModuleCredits());	}
}
