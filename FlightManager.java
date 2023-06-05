package assignment2;
//Choyon Uddin
//501031767
//April 16th 2021
import java.io.File;
import java.io.FileNotFoundException;
//Choyon Uddin
//501031767
//March 26th 2021
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import java.util.Scanner;
import java.util.TreeMap;
import java.nio.file.Path;
import java.nio.file.Paths;


public class FlightManager
{	//changed flights into a treemap
	public TreeMap<String, Flight> flights = new TreeMap<String,Flight>();
	
	//ArrayList<Flight> flights = new ArrayList<Flight>();

	String[] cities 	= 	{"Dallas", "New York", "London", "Paris", "Tokyo"};
	final int DALLAS = 0;  final int NEWYORK = 1;  final int LONDON = 2;  final int PARIS = 3; final int TOKYO = 4;

	int[] flightTimes = { 3, // Dallas
			1, // New York
			7, // London
			8, // Paris
			16,// Tokyo
	};

	ArrayList<Aircraft> airplanes = new ArrayList<Aircraft>();  
	ArrayList<String> flightNumbers = new ArrayList<String>();

	String errMsg = null;
	Random random = new Random();

	

	public FlightManager() throws FileNotFoundException
	{	//scanning the file for the flights
		Scanner scanner = new Scanner(new File("flights.txt"));
		
		// Create some aircraft types with max seat capacities
		airplanes.add(new Aircraft(8, "Boeing 737"));
		airplanes.add(new Aircraft(8,"Airbus 320"));
		airplanes.add(new Aircraft(88, "Dash-8 100"));
		airplanes.add(new Aircraft(4, "Bombardier 5000"));
		airplanes.add(new Aircraft(100, 16, "Boeing 747"));
		//going through each line in the txt file and adding those flights to the tree map
		while (scanner.hasNextLine()) {
				//spliting the sentence into segements to get the appropriate parameters
			   String line = scanner.nextLine();
			   //add the words in the sentence to the array
			   String[]words = line.split(" ");
			   //Gets the name of the airline and replaces the underscore
			   String airline = words[0].replace("_", " ");
			   //Gets the flight number from the generateFlightnumber method which uses the airline name and assigns that to the variable flightnum
			   String flightNum = generateFlightNumber(airline);
			   //gets the location the flight is going by going to the 2nd index of the array and replacing the underscore
			   String location = words[1].replace("_", " ");
			 //gets the departure time of the flight  by going to the 3rd index of the array and replacing the underscore
			   String departureTime = words[2];
			   //gets the seat capacity by "changing" the string to an integer value in the 4th index of the array words
			   int seatCapacity = Integer.parseInt(words[3]);
			   //declaring variable location 
			   int Location=0;
			   //if the location is Dallas then the Location equals the final int of dallas, as well as adds a new flight to flight treemap
			   if(location.equalsIgnoreCase("Dallas")) {
				   Location=DALLAS;
				   Flight flight = new Flight(flightNum,airline,location,departureTime,flightTimes[Location],airplanes.get(0));
				   flights.put(flightNum, flight);
			   }else if(location.equalsIgnoreCase("New York")) {
				   Location=NEWYORK;
				   Flight flight = new Flight(flightNum,airline,location,departureTime,flightTimes[Location],airplanes.get(1));
				   flights.put(flightNum, flight);
			   }else if(location.equalsIgnoreCase("London")) {
				   Location=LONDON;
				   Flight flight = new Flight(flightNum,airline,location,departureTime,flightTimes[Location],airplanes.get(2));
				   flights.put(flightNum, flight);
			   }else if(location.equalsIgnoreCase("Paris")) {
				   Location=PARIS;
				   Flight flight = new Flight(flightNum,airline,location,departureTime,flightTimes[Location],airplanes.get(3));
				   flights.put(flightNum, flight);
			   }else if(location.equalsIgnoreCase("Tokyo")) {
				   Flight flight = new LongHaulFlight(flightNum,airline,location,departureTime,flightTimes[Location],airplanes.get(4));
				   flights.put(flightNum, flight);
			   }
			}
		
	}

		//generate a flight number
	private String generateFlightNumber(String airline)
	{
		String word1, word2;
		Scanner scanner = new Scanner(airline);
		word1 = scanner.next();
		word2 = scanner.next();
		String letter1 = word1.substring(0, 1);
		String letter2 = word2.substring(0, 1);
		letter1.toUpperCase(); letter2.toUpperCase();

		// Generate random number between 101 and 300
		boolean duplicate = false;
		int flight = random.nextInt(200) + 101;
		String flightNum = letter1 + letter2 + flight;
		return flightNum;
	}

	public void printAllFlights()
	{
		for (Flight f : flights.values())
			System.out.println(f);
	}
	//change
	public void seatsAvailable(String flightNum) throws FlightNotFoundException
	{
		
		if (!flights.containsKey(flightNum))
		{
			throw new FlightNotFoundException("Flight " + flightNum + " Not Found");
		}
		Flight flight = flights.get(flightNum);
		flight.printSeats();
	}

	public Reservation reserveSeatOnFlight(String flightNum, String name, String passport, String seatType) throws FlightNotFoundException, SeatOccupiedException
	{
		
		if (!flights.containsKey(flightNum))
		{
			throw new FlightNotFoundException("Flight " + flightNum + " Not Found");
		}
		Flight flight = flights.get(flightNum);
		Passenger p = new Passenger(name, passport, "", seatType);
		flight.reserveSeat(p,seatType);
		
		String seat = flight.getLastAssignedSeat();
		return new Reservation(flightNum, flight.toString(), name, passport, seat, seatType);
	}
	//change
	public void cancelReservation(Reservation res)throws FlightNotFoundException, PassengerSeatNotFound
	{
		String flightNum = (res.getFlightNum());
		if (!flights.containsKey(flightNum))
		{
			throw new FlightNotFoundException("Flight " + res.getFlightNum() + " Not Found");
			
		}
		Flight flight = flights.get(flightNum);
		Passenger p = new Passenger(res.getname(),res.getPassport(),"",res.getseatType());
		flight.cancelSeat(p);
		
		//method to return the passenger manifest for the given flight
	}public void pasman(String flightNum) throws FlightNotFoundException {
		//if the flight number given isn't part of flights the error message is printed
		if (!flights.containsKey(flightNum))
		{
			throw new FlightNotFoundException("Flight " + flightNum + " Not Found");
		}//else calls the method from flight given the flightnumber
		Flight flight = flights.get(flightNum);
		flight.printPassengerManifest();
	}

}