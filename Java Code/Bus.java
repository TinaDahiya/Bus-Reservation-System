import java.util.*;
public class Bus {
    String busID;
    String departureLocation;
    String arrivalLocation;
    String date;
    String time;
    int totalSeats;
    int availableSeats;
    List<String> seatingLayout;
    int totalBookings; // To track bookings per bus

    Bus(String busID, String departureLocation, String arrivalLocation, String date, String time, int totalSeats) {
        this.busID = busID;
        this.departureLocation = departureLocation;
        this.arrivalLocation = arrivalLocation;
        this.date = date;
        this.time = time;
        this.totalSeats = totalSeats;
        this.availableSeats = totalSeats;
        seatingLayout = new ArrayList<>(Collections.nCopies(totalSeats, "Available"));
        this.totalBookings = 0; // Initially no bookings
    }

    boolean bookSeat(int seatNumber, User user) {
        if (seatNumber >= 0 && seatNumber < totalSeats && seatingLayout.get(seatNumber).equals("Available")) {
            seatingLayout.set(seatNumber, "Booked");
            availableSeats--;
            totalBookings++; // Increment bookings
            String bookingID = busID + "-" + (seatNumber + 1) + "-" + totalBookings; // Generate shorter booking ID
            user.addBookingHistory(bookingID);
            System.out.println("Booking successful! Your Booking ID: " + bookingID);
            return true;
        }
        return false;
    }

    boolean cancelSeat(String bookingID, User user) {
        // Parse bookingID to extract seat number
        String[] parts = bookingID.split("-");// B001-5-1
        if (parts.length != 3 || !parts[0].equals(busID)) {
            return false;
        }

        int seatNumber = Integer.parseInt(parts[1]) - 1; // Convert to 0-based index
        if (seatNumber >= 0 && seatNumber < totalSeats && seatingLayout.get(seatNumber).equals("Booked")) {
            seatingLayout.set(seatNumber, "Available");
            availableSeats++;
            totalBookings--; // Decrement bookings
            user.removeBookingHistory(bookingID);
            System.out.println("Ticket canceled successfully!");
            return true;
        }
        return false;
    }

    void displaySeatingLayout() {
        System.out.println("Seating Layout for Bus " + busID + ":");
        for (int i = 0; i < totalSeats; i++) {
            // Print left-side seats
            System.out.print("Seat " + (i + 1) + ": " + seatingLayout.get(i));
            if (i + 1 < totalSeats) {
                i++;
                System.out.print(" | Seat " + (i + 1) + ": " + seatingLayout.get(i));
            } else {
                System.out.print(" |       "); // Blank if no pair
            }
    
            // Simulate aisle space
            System.out.print("   ||   ");
    
            // Print right-side seats
            if (i + 1 < totalSeats) {
                i++;
                System.out.print("Seat " + (i + 1) + ": " + seatingLayout.get(i));
                if (i + 1 < totalSeats) {
                    i++;
                    System.out.println(" | Seat " + (i + 1) + ": " + seatingLayout.get(i));
                } else {
                    System.out.println(" |       "); // Blank if no pair
                }
            } else {
                System.out.println(" |       |       "); // Blank for both seats
            }
        }
    }
    

    int getBookings() {
        return totalBookings;
    }

    int getRevenue(int pricePerSeat) {
        return totalBookings * pricePerSeat;
    }
}
