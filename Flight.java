package assignment2;
//Choyon Uddin

import java.util.ArrayList;

/* 
 *  Class to model an airline flight. In this simple system, all flights originate from Toronto
 *  
 *  This class models a simple flight that has only economy seats
 */

import java.util.Random;
import java.util.TreeMap;

public class Flight
{	
	//declaring and initializing proper variables and objects
	public enum Status {DELAYED, ONTIME, ARRIVED, INFLIGHT};
	public static enum Type {SHORTHAUL, MEDIUMHAUL, LONGHAUL};
	public static enum SeatType {ECONOMY, FIRSTCLASS, BUSINESS};
	public static enum FlightType {SHORTHAUL, MEDIUMHAUL, LONGHAUL};

	private String flightNum;
	private String airline;
	private String origin, dest;
	private String departureTime;
	private Status status;
	private int flightDuration;
	protected Aircraft aircraft;
	protected int numPassengers;
	protected Type type;
	protected ArrayList<Passenger> manifest;
	//changed seat map into tree map instead of arraylist
	protected TreeMap<String, Passenger> seatMap = new TreeMap<String,Passenger>();

	protected Random random = new Random();

	private String errorMessage = "";
	
	public String getErrorMessage()
	{
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage)
	{
		this.errorMessage = errorMessage;
	}
	//method to initialize the proper variables for Flight method when called
	public Flight()
	{
		this.flightNum = "";
		this.airline = "";
		this.dest = "";
		this.origin = "Toronto";
		this.departureTime = "";
		this.flightDuration = 0;
		this.aircraft = null;
		numPassengers = 0;
		status = Status.ONTIME;
		type = Type.MEDIUMHAUL;
		manifest = new ArrayList<Passenger>();
	}
	//setting this.flightnum to the flightnum provided in a different class
	public Flight(String flightNum)
	{
		this.flightNum = flightNum;
	}
	//
	public Flight(String flightNum, String airline, String dest, String departure, int flightDuration, Aircraft aircraft)
	{
		this.flightNum = flightNum;
		this.airline = airline;
		this.dest = dest;
		this.origin = "Toronto";
		this.departureTime = departure;
		this.flightDuration = flightDuration;
		this.aircraft = aircraft;
		numPassengers = 0;
		status = Status.ONTIME;
		type = Type.MEDIUMHAUL;
		manifest = new ArrayList<Passenger>();
	}

	public Type getFlightType(){
		return type;
	}

	public String getFlightNum()
	{
		return flightNum;
	}
	public void setFlightNum(String flightNum)
	{
		this.flightNum = flightNum;
	}
	public String getAirline()
	{
		return airline;
	}
	public void setAirline(String airline)
	{
		this.airline = airline;
	}
	public String getOrigin()
	{
		return origin;
	}
	public void setOrigin(String origin)
	{
		this.origin = origin;
	}
	public String getDest()
	{
		return dest;
	}
	public void setDest(String dest)
	{
		this.dest = dest;
	}
	public String getDepartureTime()
	{
		return departureTime;
	}
	public void setDepartureTime(String departureTime)
	{
		this.departureTime = departureTime;
	}

	public Status getStatus()
	{
		return status;
	}
	public void setStatus(Status status)
	{
		this.status = status;
	}
	public int getFlightDuration()
	{
		return flightDuration;
	}
	public void setFlightDuration(int dur)
	{
		this.flightDuration = dur;
	}

	public int getNumPassengers()
	{
		return numPassengers;
	}
	public void setNumPassengers(int numPassengers)
	{
		this.numPassengers = numPassengers;
	}

	public void assignSeat(Passenger p)
	{
		int seat = random.nextInt(aircraft.numEconomySeats);
		p.setSeat(seat + "");
	}

	public String getLastAssignedSeat()
	{
		if (!manifest.isEmpty())
			return manifest.get(manifest.size()-1).getSeat();
		return "";
	}
	public boolean seatsAvailable(String seatType)
	{
		if (!seatType.equalsIgnoreCase("")) return false;
		return numPassengers < aircraft.numEconomySeats;
	}
	
	public boolean equals(Object other)
	{
		Flight otherFlight = (Flight) other;
		return this.flightNum.equals(otherFlight.flightNum);
	}

	public String toString(){
		return airline + "\t Flight:  " + flightNum + "\t Dest: " + dest + "\t Departing: " + departureTime + "\t Duration: " + flightDuration + "\t Status: " + status;
	//Method to print the seat layout when called upon
	}public void printSeats() {
		//so long as the seatLayout method is not null the code will continue 
		if(this.aircraft.seatLayout!=null) {
			//looping for each row
			for(int row=0; row<4; row++) {
				String strRow = "";
				//goes through the seatMap and prints XX to occupied seats if it contains the same seat number as the seatLayout 
				//Otherwise it prints the seats and moves on
				for(int column=0;column<this.aircraft.seatLayout[0].length;column++) {
					if(this.seatMap.containsKey(aircraft.seatLayout[row][column]+ "")) {
						strRow+="XX ";
					}else {
						strRow+=this.aircraft.seatLayout[row][column]+ " ";
					}
		//Once the number of rows reaches the midpoint through the letters, a black line is entered to organize seperation
				}if(row==2) {
					System.out.println();
				}System.out.println(strRow);
				//legend for Occupied and First Class seats
			}System.out.println("\nXX = Occupied\t+ = First Class");
		}
		//method to print out how many passengers are on a flight when called
	}public void printPassengerManifest() {
		//for each loop to loop through each passenger in the manifest
		for(Passenger passenger: manifest) {
			System.out.println(passenger);
		}
		//Cancel seat method that cancels the seat of a passenger based of the info passed into passenger p and throws and exception if 
		//the seat is not found
	}public void cancelSeat(Passenger p) throws PassengerSeatNotFound {
		//for each loop to loop through each passenger in the manifest
		for(Passenger passenger:manifest) {
			//if the parameters in passenger match that of the parameters in p then p is removed from the manifest and the seat removed from the seat map
			if(passenger.equals(p)) {
				//p removed from manifest
				manifest.remove(p);
				//seat removed from seatMao
				seatMap.remove(p.getSeat());
				return;
			}
			//exception if seat is not found
		}throw new PassengerSeatNotFound("Passenger seat not found on flight");
		
		//method for reserving a seat for a passenger based off of the appropriate parameters, throws appropriate exception for if seat is occupied 
	}public void reserveSeat(Passenger p, String seat) throws SeatOccupiedException {
		//checking to see if flight is full, if so, prints the flight in context is full, otherwise continues forth
		if(numPassengers >= aircraft.getNumSeats()) {
			System.out.println("Flight "+flightNum+ "Full");
			return;
			
			//if the passenger parameter is not null then if the seat is not contained in the seatMap, the seat is booked
		}else if(p!=null) {
			if(!seatMap.containsKey(seat)) {
				manifest.add(p);
				seatMap.put(seat, p);
				//if the seat is already booked then the error message prints out
			}else {
				throw new SeatOccupiedException("Seat "+ seat + " already occupied");
			}
		}
	}

}
