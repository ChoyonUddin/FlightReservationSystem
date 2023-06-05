package assignment2;
//Choyon Uddin

/*
 * A Long Haul Flight is a flight that travels a long distance and has two types of seats (First Class and Economy)
 */

public class LongHaulFlight extends Flight
{
	int firstClassPassengers;
		
	public LongHaulFlight(String flightNum, String airline, String dest, String departure, int flightDuration, Aircraft aircraft)
	{
		super(flightNum, airline, dest, departure, flightDuration, aircraft);
		type = Flight.Type.LONGHAUL;
	}

	public LongHaulFlight()
	{
		firstClassPassengers = 0;
		type = Flight.Type.LONGHAUL;
	}
	
	public void assignSeat(Passenger p)
	{
		int seat = random.nextInt(aircraft.getNumFirstClassSeats());
		p.setSeat(seat + "+");
	}
	//change
	public void reserveSeat(String name, String passport, String seatType) throws DuplicatePassengerException, NoFirstClassSeatsException, SeatOccupiedException{
		Passenger p = new Passenger(name, passport, "", seatType);
		if (manifest.indexOf(p) >=  0){
			throw new DuplicatePassengerException("Duplicate Passenger " + p.getName() + " " + p.getPassport());
		}
		if (seatType.contains("+")){
			
			if (firstClassPassengers >= aircraft.getNumFirstClassSeats()){
				throw new NoFirstClassSeatsException("No First Class Seats Available");				
			}
			
			assignSeat(p);
			manifest.add(p);
			firstClassPassengers++;
			
		}else // economy passenger
			super.reserveSeat(p, seatType);
	}
	//change
	public void cancelSeat(String name, String passport, String seatType) throws PassengerNotFoundException, PassengerSeatNotFound{
		Passenger p = new Passenger(name, passport);
		if (seatType.equalsIgnoreCase("+"))
		{
			
			if (manifest.indexOf(p) == -1) 
			{
				throw new PassengerNotFoundException("Passenger " + p.getName() + " " + p.getPassport() + " Not Found");
				
			}
			manifest.remove(p);
			if (firstClassPassengers > 0)	
			firstClassPassengers--;
		}
		else
			super.cancelSeat(p);
	}
	
	public int getPassengerCount()
	{
		return getNumPassengers() +  firstClassPassengers;
	}
	
	//change
	public boolean seatsAvailable(String seatType)
	{
		if (seatType.equals("FCL"))
			return firstClassPassengers < aircraft.getNumFirstClassSeats();
		else
			return super.seatsAvailable(seatType);
	}
	
	public String toString()
	{
		 return super.toString() + "\t LongHaul";
	}
}
