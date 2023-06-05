package assignment2;
//Choyon Uddin

import java.io.FileNotFoundException;
//Choyon Uddin
import java.util.ArrayList;
import java.util.Scanner;

// Flight System for one single day at YYZ (Print this in title) Departing flights!!

public class FlightReservationSystem
{
	public static void main(String[] args) throws FileNotFoundException, ManagerException, FlightNotFoundException, SeatOccupiedException
	{
		FlightManager manager = new FlightManager();
		Flight Flight = new Flight();
		ArrayList<Reservation> myReservations = new ArrayList<Reservation>();	// my flight reservations


		Scanner scanner = new Scanner(System.in);
		System.out.print(">");

		while (scanner.hasNextLine())
		{
			String inputLine = scanner.nextLine();
			if (inputLine == null || inputLine.equals("")) 
			{
				System.out.print("\n>");
				continue;
			}

			Scanner commandLine = new Scanner(inputLine);
			String action = commandLine.next();

			if (action == null || action.equals("")) 
			{
				System.out.print("\n>");
				continue;
			}

			else if (action.equals("Q") || action.equals("QUIT"))
				return;

			else if (action.equalsIgnoreCase("LIST"))
			{
				manager.printAllFlights(); 
			}
			else if (action.equalsIgnoreCase("RES"))
			{
				String flightNum = null;
				String passengerName = "";
				String passport = "";
				String seatType = "";

				if (commandLine.hasNext())
				{
					flightNum = commandLine.next();
				}
				if (commandLine.hasNext())
				{
					passengerName = commandLine.next();
				}
				if (commandLine.hasNext())
				{
					passport = commandLine.next();
				}
				if (commandLine.hasNext())
				{
					seatType = commandLine.next();
					try {
						Reservation res = manager.reserveSeatOnFlight(flightNum, passengerName, passport, seatType);
						if (res != null)
						{
							myReservations.add(res);
							res.print();
						}
					}
					catch(Exception e){
						System.out.println(e);
					}

				}

			}else if (action.equalsIgnoreCase("CANCEL")){
				Reservation res = null;
				String flightNum = null;
				String passengerName = "";
				String passport = "";
				String seatType = "";

				if (commandLine.hasNext())
				{
					flightNum = commandLine.next();
				}
				if (commandLine.hasNext())
				{
					passengerName = commandLine.next();
				}
				if (commandLine.hasNext())
				{
					passport = commandLine.next();
					try {
						int index = myReservations.indexOf(new Reservation(flightNum, passengerName, passport));
						if (index >= 0)
						{
							manager.cancelReservation(myReservations.get(index));
							myReservations.remove(index);
						}
					}
					catch(Exception e){
						System.out.println(e);
					}

				}
			}else if (action.equalsIgnoreCase("SEATS")){
				try {
					String flightNum = "";

					if (commandLine.hasNext()){
						flightNum = commandLine.next();

						manager.seatsAvailable(flightNum); // "FCL" or "ECO"
						System.out.println("Seats are available");
					}
				}
				catch(Exception e) {
					System.out.println(e);
				}
				//calls and prints out the passenger manifest if called upon
			}else if (action.equalsIgnoreCase("PASMAN")){
				try {
					String flightNum = "";
					if (commandLine.hasNext()){
						flightNum = commandLine.next();
						manager.pasman(flightNum);
					}
				}
				catch(Exception e) {
					System.out.println(e);
				}
			}else if (action.equalsIgnoreCase("MYRES")){
				try {
					for (Reservation myres : myReservations)
						myres.print();
				}
				catch(Exception e) {
					System.out.println(e);
				}
			}System.out.print("\n>");
		}

	}
}




