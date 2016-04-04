import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JOptionPane;

public class ImplementationTestMethods {

  public static void main(String[] args) throws Exception {
	  
	// All counselors at the school
  	HashMap counselors = new HashMap();
  	
  	// All classes available at the school
  	List classes = new ArrayList();
  	
  	importCounselors(counselors);
  	importClasses(classes);
  	
  	login(counselors);
  	
  	searchForClass(classes);
  }
  
	public static void importCounselors(HashMap counselors) throws IOException{
	    BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Kevin Zuiker\\workspace\\TestImport\\src\\CounselorsTest.txt"));
	    String line = null;
	    while ((line = br.readLine()) != null) {
	      String[] values = line.split(", ");
	      
	      // Create counselor (username, hashed password, name, age, gender, phone number)
	      Counselor aCounselor  = new Counselor(values[0],values[1],values[2],values[3],values[4],values[5]);
	      
	      // Add counselor to collection of counselors
	      counselors.put(aCounselor.getUsername(), aCounselor);

	    }
	    br.close();

	}
	
	public static void importClasses(List classes) throws IOException{
	    BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Kevin Zuiker\\workspace\\TestImport\\src\\Classes.txt"));
	    String line = null;
	    while ((line = br.readLine()) != null) {
	      String[] values = line.split(", ");
	      
	      // Creates Instructor using last three values (first name, middle name, last name)
	      Instructor aInstructor =  new Instructor(values[4],values[5],values[6]);
	      
	      // Creates class using id, section, name, available seats, Instructor
	      Class aClass = new Class(values[0], values[1], values[2], Integer.parseInt(values[3]),aInstructor);
	      
	      // Adds the class to the collection of classes
	      classes.add(aClass);
	    }
	    br.close();
	}
	
	public static Counselor login(HashMap counselors) throws Exception {
		boolean valid = false;
		
		String username = JOptionPane.showInputDialog("Enter username: ");
		String password = JOptionPane.showInputDialog("Enter password: ");
		
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(password.getBytes());
		byte[] digest = md.digest();
		StringBuffer sb = new StringBuffer();
		for (byte b : digest) {
			sb.append(String.format("%02x", b & 0xff));
		}

		Counselor aCounselor = null;
		
		if (counselors.containsKey(username)){
			aCounselor = (Counselor) counselors.get(username);
			
			if (aCounselor.getPassword().equals(sb.toString())){
				valid = true;
			}

		}
		
		/*
		if(valid){
			System.out.println("Successfully logged in");
		}
		else {
			System.out.println("Incorrect login");
		}
		*/
		
		return aCounselor;
	}
	
	public static List<Class> searchForClass(List<Class> classes){
		List<Class> searchResults = new ArrayList<>();

		String search = JOptionPane.showInputDialog("Enter class to search for: ");
		
		for (Class course : classes){
			if (course.getId().equalsIgnoreCase(search)){
				searchResults.add(course);
			}
		}
		
		return searchResults;
	}
	
	public static Student searchForStudent(HashMap<Student> students){
		// Stores search results (e.g. a Student or null)
		Student searchResult = new Student();
		
		String search = JOptionPane.showInputDialog("Enter student username to search for: ");
		
		// Converts username to all lowercase letters
		search = search.toLowerCase();
		
		// 
		if (students.containsKey(search)){
			searchResult = students.get(search);
		}
		
		return searchResult;
	}
	
	public static void addStudent(HashMap<Student> students, HashMap<Counselor> counselors){
		boolean valid = false;
		
		String username = promptForUsername();
		String password = promptForPassword();
		String name = promptForName();
		int age = promptForAge();
		char gender = promptForGender();
		String phoneNumber = promptForPhoneNumber();
			
		Student aStudent = new Student(username, password, name, age, gender, phoneNumber);
		students.put(username, aStudent);
	}
	
	public static String promptForUsername(){
		boolean valid = false;
		String username = "";
		
		do{
			username = JOptionPane.showInputDialog("Enter username: ");
			username = username.toLowerCase();
			if (!students.containsKey() && !counselors.containsKey()){
				valid = true;
			}
		}
		while (!valid);
		
		return username;
	}

	public static String promptForPassword() throws Exception{
		boolean valid = false;
		String password = "";
		
		do{
			password = JOptionPane.showInputDialog("Enter password: ");
			
			if (!password.equals("")){
				valid = true;
				
				MessageDigest md = MessageDigest.getInstance("MD5");
				md.update(password.getBytes());
				byte[] digest = md.digest();
				StringBuffer sb = new StringBuffer();
				for (byte b : digest) {
					sb.append(String.format("%02x", b & 0xff));
				}
				
				password = sb.toString();
			}
			if (!valid){
				JOptionPane.showMessageDialog(null, "Invalid password. Password cannot"
						+ "be empty! Please try again.");
			}
		}
		while (!valid);
		
		return password;
	}
	
	public static String promptForName(){
		boolean valid = false;
		String name = "";
		
		do{
			name = JOptionPane.showInputDialog("Enter name: ");
			if (!name.equals("")){
				valid = true;
			}
		}
		while (!valid);
		
		return name;
	}
	public static int promptForAge(){
		boolean valid = false;
		int age = 0;
		
		do{
			try {
				age = Integer.parseInt(JOptionPane.showInputDialog("Enter age: "));
				valid = true;
			}
			catch (NumberFormatException e){
				
			}
			if (!valid){
				JOptionPane.showMessageDialog(null, "Invalid age! Please try again.");
			}
		}
		while (!valid);
		
		return age;
	}
	
	public static char promptForGender(){
		char gender = '\u0000';
		Object[] options = {"Male", "Female"};   
		int genderMorF = JOptionPane.showOptionDialog(null, "Select gender:", 
                       		"Select Gender", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, 
                       		null, options, options[0]);
		if (genderMorF == 0){
			gender = 'M';
		}
		else { gender = 'F'; }
		
		return gender;
	}
	
	public static String promptForPhoneNumber(){
		boolean valid = false;
		String phoneNumber = "";
		do{
			phoneNumber = JOptionPane.showInputDialog("Enter Phone Number (e.g. (555) 555-555: ");
			if (phoneNumber.matches("\\d{3}\\-\\d{3}-\\d{4}")){
				valid = true;
			}
		}
		while (!valid);
		
		return phoneNumber;
	}
  }
