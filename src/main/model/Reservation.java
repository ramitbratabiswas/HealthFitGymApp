
// project modelled after JsonSerializationDemo and TellerApp from EdX Edge
// JsonSerializationDemo: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
// TellerApp: https://github.students.cs.ubc.ca/CPSC210/TellerApp

package model;

import org.json.*;
import persistence.Writable;

// Represents one gym reservation with the customer's name, reservation time, and fees paid
public class Reservation implements Writable {

    // fields:
    private String name; // customer name
    private int time; // reservation time
    private double feesPaid; // amount of fees paid during reservation
    private boolean loungeAccess = false; // shows whether the customer has access to a VIP Lounge
    private boolean lockerAccess = false; // shows whether the customer has access to lockers

    /*
     * REQUIRES: name has a non-zero length
     *           time is between 8 and 22
     * EFFECTS: name on reservation is set to name;
     *          reservation time is set to time (as an integer representing hours);
     *          if the customer pays at least 5$, they get locker access;
     *          if the customer pays at least 10$, they get both VIP Lounge and locker access;
     */
    public Reservation(String name, int time, double feesPaid) {
        this.name = name;
        this.time = time;
        this.feesPaid = feesPaid;
        if (feesPaid >= 10) {
            this.loungeAccess = true;
        }
        if (feesPaid >= 5) {
            this.lockerAccess = true;
        }
    }

    // Getter methods:
    public String getName() {
        EventLog.getInstance().logEvent(new Event("User found existing reservation in database"));
        return this.name;
    }

    public int getTime() {
        return this.time;
    }

    public double getFeesPaid() {
        return feesPaid;
    }

    public boolean isLoungeAccess() {
        return loungeAccess;
    }

    public boolean isLockerAccess() {
        return lockerAccess;
    }

    // converts this Reservation object to a JSON Object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("time", time);
        json.put("feesPaid", feesPaid);
        json.put("loungeAccess", loungeAccess);
        json.put("lockerAccess", lockerAccess);
        return json;
    }
}