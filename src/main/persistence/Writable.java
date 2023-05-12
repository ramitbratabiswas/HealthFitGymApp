
// project modelled after JsonSerializationDemo and TellerApp from EdX Edge
// JsonSerializationDemo: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
// TellerApp: https://github.students.cs.ubc.ca/CPSC210/TellerApp

package persistence;

import org.json.*;

public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
