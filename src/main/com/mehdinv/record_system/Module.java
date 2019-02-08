package main.com.mehdinv.record_system;

/**
 * Author: Mehdi Naderi Varandi
 * Purpose of Project: University coursework
 *
 * What does this class do?
 * This class creates module objects that can be associated with student objects
 *
 * Each module will have its code, name and the number of credits associated with it
 *
 * Assumptions:
 * 	1) All modules used from the associated text files are unique
**/


public final class Module {
	//FIELDS--------------------------------------------------------
	private final String moduleCode; //String holding mode code
	private final String moduleName; //String holding module name
	private final String moduleCredits; //String holding module credits
	
	private static final String FILE_DELIMITER=","; //Constant that stores the string value used to split the module strings up
	//Essentially, every line is passed into this class i.e. CSC1022, Programming II, 20, which is then split up by its commas...
	//...and turned into a new object
	//--------------------------------------------------------------
	
	private Module(String moduleCode,String moduleName, String moduleCredits){
		this.moduleCode=moduleCode; //Sets method code
		this.moduleName=moduleName;	//Sets module name
		this.moduleCredits=moduleCredits;	//Sets module credits
		
	}

	//The 3 get methods just return module code, name & credits
	//There's no defensive copying occurring below as they're returning a String, which is already immutable
	public final String getModuleCode() {
		return moduleCode; //Returns module code i.e. CSC1022
	}

	public final String getModuleName() {
		return moduleName; //Returns module name i.e. Programming 2
	}

	public final String getModuleCredits() {
		return moduleCredits; //Returns module credits i.e. 20
	}
	
	
	public final static Module getInstance(String fileLine){
		//When the SystemManager is created, it immediately reads all files i.e. a file containing modules & another containing supervisors
		//Each line is passed onto the getInstance method in module, which separates the line into 3 distinct parts: Code, name & credits
		//It then creates a new Module object from each line - the method is then called repeatedly until the SystemManager class reads all lines
		
		//My assumptions for this method are:
		//	The file is in the format described in the specification, as in...
		//	(Module Code), (Module Name), (Module Credits)
		
		String[] fileParts=fileLine.split(FILE_DELIMITER);
		//Splits the received string into 3 parts:
		//	Part 1: Contains the module code
		//	Part 2: Contains the actual name of the module
		//	Part 3: Contains the number of credits assigned to that module
		
		
		String codeLine=fileParts[0].trim(); //Gets the first part of the string line (the code) and trims any white spaces remaining
		String nameLine=fileParts[1].trim(); //Gets the second part of the string line (the name) and trims any white spaces remaining
		String creditLine=fileParts[2].trim(); //Gets the third part of the string line (the credits) and trims any white spaces remaining
		
		
		return new Module(codeLine,nameLine,creditLine); //Creates new Module
	}

	@Override
	public final String toString() { //String method that returns the module objects code, name & credit
		return "Module: " + getModuleCode() + ", " + getModuleName() + ", "
				+ getModuleCredits();
	}
	
}