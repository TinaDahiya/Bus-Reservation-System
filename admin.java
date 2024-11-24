import java.util.*;

public class admin {
    Map<String, Bus> buses;
    final int pricePerSeat = 1000; // Assuming a fixed price per seat for simplicity

    admin() {
        buses = new HashMap<>();
    }

    void addBus(Bus bus) {
        buses.put(bus.busID, bus);
        System.out.println("Bus added successfully.");
    }

    void deleteBus(String busID) {
        if (buses.containsKey(busID)) {
            buses.remove(busID);
            System.out.println("Bus " + busID + " deleted successfully.");
        } else {
            System.out.println("Bus ID not found.");
        }
    }

    void viewBusDetails() {
        for (Bus bus : buses.values()) {
            System.out.println("Bus ID: " + bus.busID + ", Departure: " + bus.departureLocation + ", Arrival: "
                    + bus.arrivalLocation + ", Date: " + bus.date + ", Time: " + bus.time
                    + ", Available Seats: " + bus.availableSeats);
        }
    }

    void updateBusSchedule(String busID, String date, String time) {
        Bus bus = buses.get(busID);
        if (bus != null) {
            bus.date = date;
            bus.time = time;
            System.out.println("Bus schedule updated for Bus " + busID);
        } else {
            System.out.println("Bus not found.");
        }
    }

    void generateReport() {
        System.out.println("\n---- Bus Booking Report ----");
        if (buses.isEmpty()) {
            System.out.println("No buses available.");
            return;
        }

        for (Bus bus : buses.values()) {
            int revenue = bus.getRevenue(pricePerSeat);
            System.out.println("Bus ID: " + bus.busID);
            System.out.println("Route: " + bus.departureLocation + " to " + bus.arrivalLocation);
            System.out.println("Date: " + bus.date + ", Time: " + bus.time);
            System.out.println("Total Seats: " + bus.totalSeats + ", Available Seats: " + bus.availableSeats);
            System.out.println("Total Bookings: " + bus.getBookings());
            System.out.println("Revenue: Rs " + revenue);
            System.out.println("---------------------------");
        }
    }
}

