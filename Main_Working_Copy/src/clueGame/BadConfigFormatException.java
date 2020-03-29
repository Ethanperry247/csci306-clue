// Authors: Caleb Pan, Ethan Perry

package clueGame;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class BadConfigFormatException extends Exception {
	
	private String message;
	
	// default constructor, sets default message
	public BadConfigFormatException() throws FileNotFoundException {	
		super("Error loading config file from formatting incompatibility");
		message = "Error loading config file from formatting incompatibility";
		try {	// exception handling writes to a log
	    	PrintWriter out = new PrintWriter("logfile.txt");
	    	out.println("Error logged: formatting incompatibility");
	        out.close();
	        
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
	}
	
	// 1-parameter constructor, will set specific message
	public BadConfigFormatException(String message) throws FileNotFoundException {
		super(message);
		this.message = message;
		try {	// exception handling writes to a log
	    	PrintWriter out = new PrintWriter("logfile.txt");
	    	out.println("Error logged: " + message);
	        out.close();
	        
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
	}

	@Override
	public String toString() {
		return "BadConfigFormatException [" + message + "]";
	}
	
}
