
// project modelled after JsonSerializationDemo and TellerApp from EdX Edge
// JsonSerializationDemo: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
// TellerApp: https://github.students.cs.ubc.ca/CPSC210/TellerApp

package model;

import org.json.*;
import persistence.Writable;

import java.util.ArrayList;

// represents a list of all the lists of reservations
public class ReservationList implements Writable {

    // constants
    private static final int INITIAL_SIZE = 24; // initial size of each list of reservations
    // (represents 24 hours in a day)

    // fields
    private static ArrayList<ArrayList<Reservation>> allReservations; // list of all reservation

    // EFFECTS: creates an empty ArrayList of ArrayLists to store reservations
    //          initializes by creating empty ArrayLists that represent time slots
    public ReservationList() {
        allReservations = new ArrayList<ArrayList<Reservation>>();
        for (int i = 0; i < INITIAL_SIZE; i++) {
            allReservations.add(new ArrayList<Reservation>());
        }
    }

    // Getter method:
    public static ArrayList<ArrayList<Reservation>> getAllReservations() {
        return allReservations;
    }

    // REQUIRES: ReservationList initialized with empty array lists
    public void addReservation(Reservation reservation) {
        allReservations.get(reservation.getTime()).add(reservation);
        EventLog.getInstance().logEvent(new Event("User added new reservation to database"));
    }

    // EFFECTS: produces the number of individual reservations made across time slots
    public int numReservations() {
        int sum = 0;
        for (ArrayList<Reservation> timeSlot : allReservations) {
            sum += timeSlot.size();
        }
        return sum;
    }

    // EFFECTS: returns this list of reservations as a JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("allReservations", reservationsToJson());
        return json;
    }

    // EFFECTS: returns things in this ReservationList as a JSON array
    public JSONArray reservationsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (ArrayList<Reservation> reservations : allReservations) {
            for (Reservation r : reservations) {
                jsonArray.put(r.toJson());
            }
        }

        return jsonArray;
    }
}
