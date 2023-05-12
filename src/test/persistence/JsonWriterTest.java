
// project modelled after JsonSerializationDemo and TellerApp from EdX Edge
// JsonSerializationDemo: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
// TellerApp: https://github.students.cs.ubc.ca/CPSC210/TellerApp

package persistence;

import model.Reservation;
import model.ReservationList;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            ReservationList rl = new ReservationList();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyReservationList() {
        try {
            ReservationList rl = new ReservationList();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyReservationList.json");
            writer.open();
            writer.write(rl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyReservationList.json");
            rl = reader.read();
            assertEquals(0, rl.numReservations());
            assertEquals(24, rl.getAllReservations().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralReservationList() {
        try {
            ReservationList rl = new ReservationList();
            rl.addReservation(new Reservation("Newton", 16, 20));
            rl.addReservation(new Reservation("Einstein", 14, 12));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralReservationList.json");
            writer.open();
            writer.write(rl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralReservationList.json");
            rl = reader.read();
            assertEquals(2, rl.numReservations());
            checkReservation("Newton", 16, 20, true, true,
                    rl.getAllReservations().get(16).get(0));
            checkReservation("Einstein", 14, 12, true, true,
                    rl.getAllReservations().get(14).get(0));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

}
