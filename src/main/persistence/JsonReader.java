
// project modelled after JsonSerializationDemo and TellerApp from EdX Edge
// JsonSerializationDemo: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
// TellerApp: https://github.students.cs.ubc.ca/CPSC210/TellerApp

package persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import model.Reservation;
import model.ReservationList;
import org.json.*;

// Represents a reader that reads reservation list from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads reservation list from file and returns it;
    // throws IOException if an error occurs reading data from file
    public ReservationList read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseReservationList(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses reservation list from JSON object and returns it
    private ReservationList parseReservationList(JSONObject jsonObject) {
        ReservationList rl = new ReservationList();
        addReservations(rl, jsonObject);
        return rl;
    }

    // MODIFIES: rl
    // EFFECTS: parses reservations from JSON object and adds them to the list of reservations
    private void addReservations(ReservationList rl, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("allReservations");
        for (Object json : jsonArray) {
            JSONObject nextReservation = (JSONObject) json;
            addReservation(rl, nextReservation);
        }
    }

    // MODIFIES: rl
    // EFFECTS: parses reservation from JSON object and adds it to the list of reservations
    private void addReservation(ReservationList rl, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int time = jsonObject.getInt("time");
        double feesPaid = jsonObject.getDouble("feesPaid");
        Reservation reservation = new Reservation(name, time, feesPaid);
        rl.addReservation(reservation);
    }
}
