package model;

import org.json.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReservationTest {
    private Reservation testReservation;

    @BeforeEach
    void runBefore() {
        testReservation = new Reservation("Shakespeare", 15, 20);
    }

    @Test
    void testConstructorBoth() {
        assertEquals("Shakespeare", testReservation.getName());
        assertEquals(15, testReservation.getTime());
        assertEquals(20, testReservation.getFeesPaid());
        assertTrue(testReservation.isLoungeAccess());
        assertTrue(testReservation.isLockerAccess());
    }

    @Test
    void testConstructorBothBoundary() {
        testReservation = new Reservation("Babur", 19, 10);
        assertTrue(testReservation.isLockerAccess());
        assertTrue(testReservation.isLoungeAccess());
    }

    @Test
    void testConstructorOnlyLocker() {
        testReservation = new Reservation("Julius Caesar", 16, 6);
        assertTrue(testReservation.isLockerAccess());
        assertFalse(testReservation.isLoungeAccess());
    }

    @Test
    void testConstructorOnlyLockerBoundary() {
        testReservation = new Reservation("Genghis Khan", 17, 5);
        assertTrue(testReservation.isLockerAccess());
        assertFalse(testReservation.isLoungeAccess());
    }

    @Test
    void testConstructorNone() {
        testReservation = new Reservation("Confucius", 17, 4);
        assertFalse(testReservation.isLockerAccess());
        assertFalse(testReservation.isLoungeAccess());
    }

    @Test
    void testToJson() {
        testReservation = new Reservation("Alexander", 19, 4);
        JSONObject jObj = testReservation.toJson();
        assertEquals("{\"loungeAccess\":false," +
                "\"name\":\"Alexander\"," +
                "\"feesPaid\":4," +
                "\"lockerAccess\":false," +
                "\"time\":19}", jObj.toString());
    }

}