package assignment2;
//Choyon Uddin

/*
 * 
 * This class models an aircraft type with a model name, a maximum number of economy seats, and a max number of forst class seats 
 * 
 * Add code such that class Aircraft implements the Comparable interface
 * Compare two Aircraft objects by first comparing the number of economy seats. If the number is equal, then compare the
 * number of first class seats 
 */

import java.util.TreeMap;

public class Aircraft implements Comparable<Aircraft>
{
	//Declaring Variables for class Aircraft to be used in methods 
	int numEconomySeats;
	int numFirstClassSeats;
	String[][] seatLayout;
	String model;

	public Aircraft(int seats, String model)
	{	
		//Initializing variables for Aircraft Method
		this.numEconomySeats = seats;
		this.numFirstClassSeats = 0;
		this.model = model;
		//Calling SeatLayout Method
		this.SeatLayout();
	}

	public Aircraft(int economy, int firstClass, String model)
	{	//Initializing variables for Aircraft Method
		this.numEconomySeats = economy;
		this.numFirstClassSeats = firstClass;
		this.model = model;
		//Calling SeatLayout Method
		this.SeatLayout();
	}

	public int getNumSeats()
	{	//Returning the number of economy seats
		return numEconomySeats;
	}
	
	public int getTotalSeats()
	{	//Returning the number of total seats
		return numEconomySeats + numFirstClassSeats;
	}

	//Method for getting and returning the number of first class seats
	public int getNumFirstClassSeats()
	{
		return numFirstClassSeats;
	}
	
	public String getModel()
	{
		return model;
	}

	public void setModel(String model)
	{
		this.model = model;
	}
	//Prints the model of plane plus the number of economy and first class seats
	public void print()
	{	
		System.out.println("Model: " + model + "\t Economy Seats: " + numEconomySeats + "\t First Class Seats: " + numFirstClassSeats);
	}
	
	//Method for getting how many first class or economy seats are available after changes have been made
	public int compareTo(Aircraft other)
	{
		if (this.numEconomySeats == other.numEconomySeats)
			return this.numFirstClassSeats - other.numFirstClassSeats;
		return this.numEconomySeats - other.numEconomySeats; 
		
	//This method is used to generate the seat layout on the flight
	}public void SeatLayout() {
		//labeling the Rows from A-D
		String SeatLetters = "ABCD";
		//Getting the number of seats per row by dividing the number of seats by 4
		int NumberOfRows = this.getTotalSeats()/4;
		//Initializing seatLayout to giving 4 rows followed by the number of seats per row
		this.seatLayout = new String[4][NumberOfRows];
		//Nested for loop assign the row letters to every seats i goes through the letters and j goes through the seats
		for(int i=0; i<4;i++) {
			for(int j=0;j<NumberOfRows;j++) {
				this.seatLayout[i][j] = j+1 + Character.toString(SeatLetters.charAt(i));
			}
		}
	}
	
}
