
import java.util.*;

public class User {
    String username;
    String password;
    List<String> bookingHistory;

    User(String username, String password) {
        this.username = username;
        this.password = password;
        bookingHistory = new ArrayList<>();
    }

    void addBookingHistory(String bookingID) {
        bookingHistory.add(bookingID);
    }

    void removeBookingHistory(String bookingID) {
        bookingHistory.remove(bookingID);
    }

    void viewBookingHistory() {
        System.out.println("Booking History for " + username + ": ");
        for (String booking : bookingHistory) {
            System.out.println(booking);
        }
    }
}