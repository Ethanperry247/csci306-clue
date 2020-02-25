package clueGame;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class BadConfigFormatException extends Exception {
	
	public BadConfigFormatException() throws FileNotFoundException {
		super("Error loading config file from formatting incompatibility");
		try {
	    	PrintWriter out = new PrintWriter("logfile.txt");
	    	out.println("Error logged: formatting incompatibility");
	        out.close();
	        
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
	}
	
	public BadConfigFormatException(String s) throws FileNotFoundException {
		super(s);
		try {
	    	PrintWriter out = new PrintWriter("logfile.txt");
	    	out.println("Error logged: " + s);
	        out.close();
	        
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
	}

	@Override
	public String toString() {
		return "BadConfigFormatException []";
	}
	
}
