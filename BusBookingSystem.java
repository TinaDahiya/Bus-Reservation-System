import java.util.*;

public class BusBookingSystem {
    private static Map<String, User> users = new HashMap<>();
    private static admin admin = new admin();
    private static Map<String, Bus> availableBuses = new HashMap<>();
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        // Adding some initial data for testing
        admin.addBus(new Bus("B001", "Delhi", "Agra", "2024-12-01", "08:00", 20));
        admin.addBus(new Bus("B002", "Jaipur", "Varanasi", "2024-12-01", "09:00", 20));

        // User registration and login menu
        while (true) {
            System.out.println("\n---- Bus Booking System ----");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Admin Login");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    registerUser();
                    break;
                case 2:
                    loginUser();
                    break;
                case 3:
                    adminLogin();
                    break;
                case 4:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void registerUser() {
        System.out.print("Enter username: ");
        String username = sc.nextLine();
        System.out.print("Enter password: ");
        String password = sc.nextLine();

        if (users.containsKey(username)) {
            System.out.println("User already exists.");
        } else {
            users.put(username, new User(username, password));
            System.out.println("User registered successfully.");
        }
    }

    private static void loginUser() {
        System.out.print("Enter username: ");
        String username = sc.nextLine();
        System.out.print("Enter password: ");
        String password = sc.nextLine();

        User user = users.get(username);
        if (user != null && user.password.equals(password)) {
            System.out.println("Login successful.");
            userMenu(user);
        } else {
            System.out.println("Invalid credentials.");
        }
    }

    private static void userMenu(User user) {
        while (true) {
            System.out.println("\n---- User Menu ----");
            System.out.println("1. Search Buses");
            System.out.println("2. View Booking History");
            System.out.println("3. Cancel Ticket");
            System.out.println("4. Logout");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    searchAndBookBus(user);
                    break;
                case 2:
                    user.viewBookingHistory();
                    break;
                case 3:
                    cancelTicket(user);
                    break;
                case 4:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void cancelTicket(User user) {
        System.out.print("Enter booking ID to cancel: ");
        String bookingID = sc.nextLine();

        String busID = bookingID.split("-")[0]; // Extract bus ID
        Bus bus = admin.buses.get(busID);

        if (bus != null) {
            if (bus.cancelSeat(bookingID, user)) {
                // Cancellation handled inside the cancelSeat method
            } else {
                System.out.println("Unable to cancel ticket. Invalid booking ID or seat number.");
            }
        } else {
            System.out.println("Bus not found.");
        }
    }

    private static void searchAndBookBus(User user) {
        System.out.print("Enter departure location: ");
        String departureLocation = sc.nextLine();
        System.out.print("Enter arrival location: ");
        String arrivalLocation = sc.nextLine();
        System.out.print("Enter date (YYYY-MM-DD): ");
        String date = sc.nextLine();
    
        availableBuses.clear(); 
        for (Bus bus : admin.buses.values()) {
            if (bus.departureLocation.equals(departureLocation) && bus.arrivalLocation.equals(arrivalLocation) && bus.date.equals(date)) {
                availableBuses.put(bus.busID, bus);
            }
        }
    
        if (availableBuses.isEmpty()) {
            System.out.println("No buses available for the selected route and date.");
            return;
        }
    
        System.out.println("Available buses:");
        for (Bus bus : availableBuses.values()) {
            System.out.println("Bus ID: " + bus.busID + ", Departure: " + bus.departureLocation + ", Arrival: " + bus.arrivalLocation + ", Time: " + bus.time + ", Available Seats: " + bus.availableSeats);
        }
    
        System.out.print("Enter bus ID to select: ");
        String busID = sc.nextLine();
        Bus selectedBus = availableBuses.get(busID);
    
        if (selectedBus != null) {
            selectedBus.displaySeatingLayout();
            System.out.print("Enter seat number to book: ");
            int seatNumber = sc.nextInt() - 1; // adjusting for 0-based index
            sc.nextLine(); // consume newline
    
            if (selectedBus.bookSeat(seatNumber, user)) {
                // Booking handled directly inside the bookSeat method
            } else {
                System.out.println("Seat already booked or invalid seat number.");
            }
        } else {
            System.out.println("Invalid bus ID.");
        }
    }
    

    private static void adminLogin() {
        System.out.print("Enter admin password: ");
        String password = sc.nextLine();

        if (password.equals("admin123")) {
            adminMenu();
        } else {
            System.out.println("Invalid admin password.");
        }
    }

    private static void adminMenu() {
        while (true) {
            System.out.println("\n---- Admin Menu ----");
            System.out.println("1. View Bus Details");
            System.out.println("2. Add Bus");
            System.out.println("3. Update Bus Schedule");
            System.out.println("4. Delete Bus");
            System.out.println("5. Generate Report");
            System.out.println("6. Logout");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine(); // consume newline
    
            switch (choice) {
                case 1:
                    admin.viewBusDetails();
                    break;
                case 2:
                    addBus();
                    break;
                case 3:
                    updateBusSchedule();
                    break;
                case 4:
                    deleteBus();  // New method call 
                    break;
                case 5:
                    admin.generateReport();
                    break;
                case 6:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
    
    private static void deleteBus() {
        System.out.print("Enter bus ID to delete: ");
        String busID = sc.nextLine();
    
        // Use admin's deleteBus method to handle deletion
        admin.deleteBus(busID);
    }
    
    
    
    private static void addBus() {
        System.out.print("Enter bus ID: ");
        String busID = sc.nextLine();
        System.out.print("Enter departure location: ");
        String departureLocation = sc.nextLine();
        System.out.print("Enter arrival location: ");
        String arrivalLocation = sc.nextLine();
        System.out.print("Enter date (YYYY-MM-DD): ");
        String date = sc.nextLine();
        System.out.print("Enter time: ");
        String time = sc.nextLine();
        System.out.print("Enter total seats: ");
        int totalSeats = sc.nextInt();
        sc.nextLine(); // consume newline

        Bus newBus = new Bus(busID, departureLocation, arrivalLocation, date, time, totalSeats);
        admin.addBus(newBus);
    }
      
    private static void updateBusSchedule() {
        System.out.print("Enter bus ID to update: ");
        String busID = sc.nextLine();
        System.out.print("Enter new date (YYYY-MM-DD): ");
        String date = sc.nextLine();
        System.out.print("Enter new time: ");
        String time = sc.nextLine();

        admin.updateBusSchedule(busID, date, time);
    }
}
