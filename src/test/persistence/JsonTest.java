
// project modelled after JsonSerializationDemo and TellerApp from EdX Edge
// JsonSerializationDemo: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
// TellerApp: https://github.students.cs.ubc.ca/CPSC210/TellerApp

package persistence;

import model.Reservation;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkReservation(String name,
                                    int time,
                                    int feesPaid,
                                    boolean lounge,
                                    boolean locker,
                                    Reservation reservation) {
        assertEquals(name, reservation.getName());
        assertEquals(time, reservation.getTime());
        assertEquals(feesPaid, reservation.getFeesPaid());
        assertEquals(lounge, reservation.isLoungeAccess());
        assertEquals(locker, reservation.isLockerAccess());
    }
}
