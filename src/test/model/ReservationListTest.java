
// project modelled after JsonSerializationDemo and TellerApp from EdX Edge
// JsonSerializationDemo: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
// TellerApp: https://github.students.cs.ubc.ca/CPSC210/TellerApp

package model;

import org.json.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

class ReservationListTest {

    private ReservationList testReservationList;
    private final int INITIAL_SIZE = 24;

    @BeforeEach
    void runBefore() {
        testReservationList = new ReservationList();
    }

    @Test
    void testConstructor() {
        assertEquals(INITIAL_SIZE, testReservationList.getAllReservations().size());
    }

    @Test
    void testNumReservations() {
        assertEquals(testReservationList.numReservations(), 0);
        assertEquals(testReservationList.getAllReservations().size(), 24);
        testReservationList.getAllReservations().add(new ArrayList<Reservation>());
        assertEquals(testReservationList.numReservations(), 0);
        assertEquals(testReservationList.getAllReservations().size(), 25);
        testReservationList.getAllReservations().add(new ArrayList<Reservation>());
        assertEquals(testReservationList.getAllReservations().size(), 26);
        assertEquals(testReservationList.numReservations(), 0);
        testReservationList.getAllReservations().get(2).add(new Reservation("me", 12, 24));
        assertEquals(testReservationList.getAllReservations().size(), 26);
        assertEquals(testReservationList.numReservations(), 1);
        testReservationList.getAllReservations().get(4).add(new Reservation("you", 15, 24));
        assertEquals(testReservationList.getAllReservations().size(), 26);
        assertEquals(testReservationList.numReservations(), 2);
    }

    @Test
    void testAdd() {
        assertEquals(INITIAL_SIZE, testReservationList.getAllReservations().size());
        testReservationList.getAllReservations().add(new ArrayList<Reservation>());
        assertEquals(INITIAL_SIZE + 1, testReservationList.getAllReservations().size());
    }

    @Test
    void testAddReservation() {
        testReservationList.getAllReservations().add(new ArrayList<Reservation>());
        for (int i = 0; i < INITIAL_SIZE; i++) {
            testReservationList.getAllReservations().add(new ArrayList<Reservation>());
        }

        Reservation reservation1 = new Reservation("Cleopatra", 13, 500);
        Reservation reservation2 = new Reservation("Romulus", 15, 5);
        Reservation reservation3 = new Reservation("Remus", 18, 1);
        Reservation reservation4 = new Reservation("Darius", 13, 500);
        testReservationList.addReservation(reservation1);
        testReservationList.addReservation(reservation2);
        testReservationList.addReservation(reservation3);
        testReservationList.addReservation(reservation4);

        assertEquals(4, testReservationList.numReservations());

    }

    @Test
    void testRemove() {
        testReservationList.getAllReservations().add(new ArrayList<Reservation>());
        assertEquals(INITIAL_SIZE + 1, testReservationList.getAllReservations().size());

        testReservationList.getAllReservations().remove(0);
        assertEquals(INITIAL_SIZE, testReservationList.getAllReservations().size());
    }

    @Test
    void testReservationsToJson() {
        testReservationList.getAllReservations().add(new ArrayList<Reservation>());
        testReservationList.getAllReservations().add(new ArrayList<Reservation>());
        testReservationList.getAllReservations().add(new ArrayList<Reservation>());
        testReservationList.getAllReservations().get(0).add(new Reservation("Aristotle", 17, 4));
        testReservationList.getAllReservations().get(1).add(new Reservation("Plato", 17, 4));
        testReservationList.getAllReservations().get(2).add(new Reservation("Socrates", 5, 4));

        JSONArray jArray = testReservationList.reservationsToJson();

        System.out.println(jArray.toString(2));
    }

    @Test
    void testToJson() {
        testReservationList.getAllReservations().add(new ArrayList<Reservation>());
        testReservationList.getAllReservations().add(new ArrayList<Reservation>());
        testReservationList.getAllReservations().add(new ArrayList<Reservation>());
        testReservationList.getAllReservations().get(0).add(new Reservation("Aristotle", 17, 4));
        testReservationList.getAllReservations().get(1).add(new Reservation("Plato", 17, 4));
        testReservationList.getAllReservations().get(2).add(new Reservation("Socrates", 5, 4));

        JSONObject jObj = testReservationList.toJson();

        System.out.println(jObj.toString(2));
    }

}