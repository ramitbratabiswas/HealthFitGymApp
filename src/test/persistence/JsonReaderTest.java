package persistence;

import model.Reservation;
import model.ReservationList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.json.*;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            ReservationList rl = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyReservationList() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyReservationList.json");
        try {
            ReservationList rl = reader.read();
            assertEquals(24, rl.getAllReservations().size());
            assertEquals(0, rl.numReservations());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralReservationList() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralReservationList.json");
        try {
            ReservationList rl = reader.read();
            assertEquals(24, rl.getAllReservations().size());
            assertEquals(0, rl.numReservations());
            rl.getAllReservations().add(new ArrayList<Reservation>());
            rl.getAllReservations().add(new ArrayList<Reservation>());
            assertEquals(24 + 2, rl.getAllReservations().size());
            assertEquals(0, rl.numReservations());

            rl.addReservation((new Reservation("Zeus", 12, 476)));
            assertEquals(1, rl.numReservations());
            assertEquals("Zeus", rl.getAllReservations().get(12).get(0).getName());
            assertEquals(12, rl.getAllReservations().get(12).get(0).getTime());
            assertEquals(476, rl.getAllReservations().get(12).get(0).getFeesPaid());
            assertTrue(rl.getAllReservations().get(12).get(0).isLockerAccess());
            assertTrue(rl.getAllReservations().get(12).get(0).isLoungeAccess());

            rl.addReservation((new Reservation("Poseidon", 8, 6)));
            assertEquals(2, rl.numReservations());
            assertEquals("Poseidon", rl.getAllReservations().get(8).get(0).getName());
            assertEquals(8, rl.getAllReservations().get(8).get(0).getTime());
            assertEquals(6, rl.getAllReservations().get(8).get(0).getFeesPaid());
            assertTrue(rl.getAllReservations().get(8).get(0).isLockerAccess());
            assertFalse(rl.getAllReservations().get(8).get(0).isLoungeAccess());

            rl.addReservation((new Reservation("Hades", 8, 2)));
            assertEquals(3, rl.numReservations());
            assertEquals("Hades", rl.getAllReservations().get(8).get(1).getName());
            assertEquals(8, rl.getAllReservations().get(8).get(1).getTime());
            assertEquals(2, rl.getAllReservations().get(8).get(1).getFeesPaid());
            assertFalse(rl.getAllReservations().get(8).get(1).isLockerAccess());
            assertFalse(rl.getAllReservations().get(8).get(1).isLoungeAccess());

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
