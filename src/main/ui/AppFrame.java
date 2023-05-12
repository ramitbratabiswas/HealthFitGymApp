package ui;

import model.Event;
import model.EventLog;
import model.Reservation;
import model.ReservationList;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class AppFrame extends JFrame implements ActionListener {

    // Constants:

    // List
    private static final int MAX_CAPACITY = 30; // max number of reservations allowed at a particular time
    private static final int OPENING_TIME = 8; // time at which the gym opens
    private static final int CLOSING_TIME = 22; // time at which the gym closes

    // Screen attributes:
    private static final int SCREEN_WIDTH = 600;
    private static final int SCREEN_HEIGHT = 400;

    // Button attributes:
    private static final int BUTTON_WIDTH = 160;
    private static final int BUTTON_HEIGHT = 80;
    private static final int BUTTON_X = SCREEN_WIDTH - BUTTON_WIDTH - 10;

    // General content attributes:
    private static final int CONTENT_WIDTH = 420;
    private static final int CONTENT_X = 20;

    // Header attributes:
    private static final Font HEADER_FONT = new Font("Helvetica", Font.BOLD, 40);
    private static final int HEADER_Y = 20;
    private static final int HEADER_HEIGHT = 60;

    // Subheader attributes:
    private static final Font SUBHEADER_FONT = new Font("Helvetica", Font.BOLD, 20);
    private static final int SUBHEADER_Y = 80;
    private static final int SUBHEADER_HEIGHT = 40;

    // Footer attributes:
    private static final Font FOOTER_FONT = new Font("Helvetica", Font.PLAIN, 12);
    private static final int FOOTER_HEIGHT = 20;
    private static final int FOOTER_Y = SCREEN_HEIGHT - 60;

    // Text attributes:
    private static final Font TEXT_FONT = new Font("Helvetica", Font.PLAIN, 16);
    private static final int TEXT_HEIGHT = 25;

    // fields:

    // JSON and Reservations System:
    private static final String JSON_STORE = "./data/reservations.json"; // directory to store json data
    private ReservationList reservations; // list of time slots that are themselves lists of reservations
    private Scanner input; // allows users to make keyboard input
    private JsonWriter jsonWriter; // uses JSON to save data
    private JsonReader jsonReader; // uses JSON to load data
    private ArrayList<Integer> availableTimes;
    private boolean nameFound = false;
    private boolean makePanelOpen = false;
    private boolean checkPanelOpen = false;
    private String reserveName;
    private String checkReserveName;
    private int reserveTime;
    private double reserveFees;

    // JButton:
    private JButton buttonReserve;
    private JButton buttonCheck;
    private JButton buttonSave;
    private JButton buttonLoad;
    private JButton submit;
    private JButton submitCheck;

    // Template JLabels:
    private JLabel header = new JLabel("");
    private JLabel subHeader = new JLabel("");
    private JLabel text1 = new JLabel("");
    private JLabel text2 = new JLabel("");
    private JLabel text3 = new JLabel("");
    private JLabel text4 = new JLabel("");
    private JLabel text5 = new JLabel("");
    private JLabel text6 = new JLabel("");
    private JLabel text7 = new JLabel("");
    private JLabel text8 = new JLabel("");
    private JLabel text9 = new JLabel("");
    private JLabel text10 = new JLabel("");
    private JLabel footer = new JLabel("");

    // GUI elements
    private JComboBox availableTimesDrop;
    private JTextField nameField;
    private JTextField feesField;
    private JTextField checkNameField;
    private ImageIcon logo;
    private JLabel logoContainer;

    // constructor for GUI
    public AppFrame() {

        super("HealthFit Gym");

        initializeList();
        initializeButtons();
        initializeTemplate();

        setVisible(true);

        // EFFECTS: shows event log when quitting app
        WindowListener listener = new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                logPrinter(EventLog.getInstance());
            }
        };

        addWindowListener(listener);
    }

    // from SpaceInvaders on edX edge
    // Centres frame on desktop
    // modifies: this
    // effects:  location of frame is set so frame is centred on desktop
    private void centreOnScreen() {
        Dimension scrn = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((scrn.width - getWidth()) / 2, (scrn.height - getHeight()) / 2);
    }

    // effects: controls all the button actions
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == buttonReserve) {
            doNewReservation();
            makePanelOpen = true;
            logoContainer.setVisible(false);
        }
        if (e.getSource() == buttonCheck) {
            doCheckReservation();
            checkPanelOpen = true;
            logoContainer.setVisible(false);
        }
        if (e.getSource() == buttonSave) {
            doSaveReservations();
        }
        if (e.getSource() == buttonLoad) {
            doLoadReservations();
        }
        if (e.getSource() == submit) {
            processDetails();
        }
        if (e.getSource() == submitCheck) {
            processCheck();
        }
    }

    // the following methods make the template that the GUI uses

    // makes buttons of arbitrary size and position
    public JButton makeButton(String label, int x, int y, int w, int h) {
        JButton button = new JButton();
        button.setBounds(x, y, w, h);
        button.addActionListener(this);
        button.setBackground(Color.PINK);
        button.setText(label);
        button.setFont(new Font("Helvetica", Font.BOLD, 12));
        return button;
    }

    // makes specific buttons on the right-hand side
    public JButton makeSideButton(String label, int y) {
        JButton button = makeButton(label, BUTTON_X, y, BUTTON_WIDTH, BUTTON_HEIGHT);
        return button;
    }

    // sets header text
    public void makeHeader(String text) {
        header.setText(text);
    }

    // sets subheader text
    public void makeSubHeader(String text) {
        subHeader.setText(text);
    }

    // sets line 1 text
    public void makeText1(String text) {
        text1.setText(text);
    }

    // sets line 2 text
    public void makeText2(String text) {
        text2.setText(text);
    }

    // sets line 3 text
    public void makeText3(String text) {
        text3.setText(text);
    }

    // sets line 4 text
    public void makeText4(String text) {
        text4.setText(text);
    }

    // sets line 5 text
    public void makeText5(String text) {
        text5.setText(text);
    }

    // sets line 6 text
    public void makeText6(String text) {
        text6.setText(text);
    }

    // sets line 7 text
    public void makeText7(String text) {
        text7.setText(text);
    }

    // sets line 8 text
    public void makeText8(String text) {
        text8.setText(text);
    }

    // sets line 9 text
    public void makeText9(String text) {
        text9.setText(text);
    }

    // sets line 10 text
    public void makeText10(String text) {
        text10.setText(text);
    }

    // sets footer text
    public void makeFooter(String text) {
        footer.setText(text);
    }

    // the following methods initialize each screen or element when required

    // MODIFIES: this
    // EFFECTS: initializes reservation times and spaces and the JSON Reader and Writers
    private void initializeList() {
        reservations = new ReservationList();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // MODIFIES: this
    // EFFECTS: initializes Java Swing buttons
    private void initializeButtons() {
        buttonReserve = makeSideButton("Make Reservation", 10);
        buttonCheck = makeSideButton("Check Reservation", 100);
        buttonSave = makeSideButton("Save All Reservations", 190);
        buttonLoad = makeSideButton("Load All Reservations", 280);

        add(buttonReserve);
        add(buttonCheck);
        add(buttonSave);
        add(buttonLoad);
    }

    // MODIFIES: this
    // EFFECTS: initializes JFrame Template
    private void initializeTemplate() {

        initializeHeader();
        initializeSubHeader();
        initializeLogo();
        initializeText1();
        initializeText2();
        initializeText3();
        initializeText4();
        initializeText5();
        initializeText6();
        initializeText7();
        initializeText8();
        initializeText9();
        initializeText10();
        initializeFooter();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        setResizable(false);
        centreOnScreen();
        getContentPane().setBackground(new java.awt.Color(248, 142, 142, 255));

    }

    // MODIFIES: this
    // EFFECTS: initializes header
    private void initializeHeader() {
        header.setFont(HEADER_FONT);
        header.setForeground(Color.WHITE);
        header.setBounds(CONTENT_X, HEADER_Y, CONTENT_WIDTH, HEADER_HEIGHT);

        add(header);
        makeHeader("Welcome");
    }

    // MODIFIES: this
    // EFFECTS: initializes subheader
    private void initializeSubHeader() {
        subHeader.setFont(SUBHEADER_FONT);
        subHeader.setForeground(Color.WHITE);
        subHeader.setBounds(CONTENT_X, SUBHEADER_Y, CONTENT_WIDTH, SUBHEADER_HEIGHT);

        add(subHeader);
        makeSubHeader("to HealthFit's new Reservation app");
    }

    // MODIFIES: this
    // EFFECTS: initializes logo
    private void initializeLogo() {
        try {
            logo = new ImageIcon(getClass().getResource("logo.jpg"));
            logoContainer = new JLabel(logo);
            logoContainer.setBounds(20, 150, 350, 100);
            add(logoContainer);
        } catch (Exception e) {
            System.out.println("image not found");
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes line 1 text
    private void initializeText1() {
        text1.setFont(TEXT_FONT);
        text1.setForeground(Color.WHITE);
        text1.setBounds(CONTENT_X, 120, CONTENT_WIDTH, TEXT_HEIGHT);

        add(text1);
        makeText1("");
    }

    // MODIFIES: this
    // EFFECTS: initializes line 2 text
    private void initializeText2() {
        text2.setFont(TEXT_FONT);
        text2.setForeground(Color.WHITE);
        text2.setBounds(CONTENT_X, 140, CONTENT_WIDTH, TEXT_HEIGHT);

        add(text2);
        makeText2("");
    }

    // MODIFIES: this
    // EFFECTS: initializes line 3 text
    private void initializeText3() {
        text3.setFont(TEXT_FONT);
        text3.setForeground(Color.WHITE);
        text3.setBounds(CONTENT_X, 160, CONTENT_WIDTH, TEXT_HEIGHT);

        add(text3);
        makeText3("");
    }

    // MODIFIES: this
    // EFFECTS: initializes line 4 text
    private void initializeText4() {
        text4.setFont(TEXT_FONT);
        text4.setForeground(Color.WHITE);
        text4.setBounds(CONTENT_X, 180, CONTENT_WIDTH, TEXT_HEIGHT);

        add(text4);
        makeText4("");
    }

    // MODIFIES: this
    // EFFECTS: initializes line 5 text
    private void initializeText5() {
        text5.setFont(TEXT_FONT);
        text5.setForeground(Color.WHITE);
        text5.setBounds(CONTENT_X, 200, CONTENT_WIDTH, TEXT_HEIGHT);

        add(text5);
        makeText5("");
    }

    // MODIFIES: this
    // EFFECTS: initializes line 6 text
    private void initializeText6() {
        text6.setFont(TEXT_FONT);
        text6.setForeground(Color.WHITE);
        text6.setBounds(CONTENT_X, 220, CONTENT_WIDTH, TEXT_HEIGHT);

        add(text6);
        makeText6("");
    }

    // MODIFIES: this
    // EFFECTS: initializes line 7 text
    private void initializeText7() {
        text7.setFont(TEXT_FONT);
        text7.setForeground(Color.WHITE);
        text7.setBounds(CONTENT_X, 240, CONTENT_WIDTH, TEXT_HEIGHT);

        add(text7);
        makeText7("");
    }

    // MODIFIES: this
    // EFFECTS: initializes line 8 text
    private void initializeText8() {
        text8.setFont(TEXT_FONT);
        text8.setForeground(Color.WHITE);
        text8.setBounds(CONTENT_X, 260, CONTENT_WIDTH, TEXT_HEIGHT);

        add(text8);
        makeText8("");
    }

    // MODIFIES: this
    // EFFECTS: initializes line 9 text
    private void initializeText9() {
        text9.setFont(TEXT_FONT);
        text9.setForeground(Color.WHITE);
        text9.setBounds(CONTENT_X, 280, CONTENT_WIDTH, TEXT_HEIGHT);

        add(text9);
        makeText9("");
    }

    // MODIFIES: this
    // EFFECTS: initializes line 10 text
    private void initializeText10() {
        text10.setFont(TEXT_FONT);
        text10.setForeground(Color.WHITE);
        text10.setBounds(CONTENT_X, 300, CONTENT_WIDTH, TEXT_HEIGHT);

        add(text10);
        makeText10("We hope you have a great time");
    }

    // MODIFIES: this
    // EFFECTS: initializes footer text
    private void initializeFooter() {
        footer.setFont(FOOTER_FONT);
        footer.setForeground(Color.WHITE);
        footer.setBounds(CONTENT_X, FOOTER_Y, CONTENT_WIDTH, FOOTER_HEIGHT);

        add(footer);
        makeFooter("");
    }

    // MODIFIES: this
    // EFFECTS: initializes the form on the "Make Reservations" page
    private void initializeQuery() {

        queryRefresh();

        // combo box for available times
        availableTimesDrop = new JComboBox(availableTimes.toArray());
        initializeComboBox(availableTimesDrop);

        // text field for name
        nameField = new JTextField();
        initializeNameBox(nameField);

        // text field for fees
        feesField = new JTextField();
        initializeFeesBox(feesField);

        makeHeader("Make Reservations");
        makeSubHeader("Please type in the following details:");
        makeText2("Name:");
        makeText4("Time(24h):");
        makeText6("Fees($):");
        makeText7("(at least 5$: locker access, at least 10$: lounge access)");

        // submit button to start processing
        submit = makeButton("Submit", 310, 180, 80, 30);
        submit.setEnabled(false);
        add(submit);
    }

    // the following methods are for the "Make Reservations" screen

    // MODIFIES: this
    // EFFECTS: makes a new reservation for the user
    private void doNewReservation() {
        availableTimes = new ArrayList<Integer>();

        for (int i = OPENING_TIME; i < CLOSING_TIME; i++) {
            if (reservations.getAllReservations().get(i).size() < MAX_CAPACITY) {
                availableTimes.add(i);
            }
        }

        initializeQuery();

        if (nameField.getText().length() != 0 && feesField.getText().length() != 0) {
            submit.setEnabled(true);
        }

    }

    // refreshes form for new use
    private void queryRefresh() {

        if (checkPanelOpen) {
            submitCheck.setVisible(false);
            checkNameField.setVisible(false);
            checkPanelOpen = false;
        }

        makeText1("");
        makeText3("");
        makeText5("");
        makeText8("");
        makeText9("");
        makeText10("");
    }

    // initializes name text field
    private void initializeNameBox(JTextField box) {
        box.setBounds(100, 140, 200, 25);
        box.setBackground(Color.PINK);
        add(box);

        box.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                changed();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                changed();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                changed();
            }
        });
    }

    // initializes time drop down menu
    private void initializeComboBox(JComboBox box) {
        box.setBounds(100, 170, 200, 50);
        box.setBackground(Color.PINK);
        add(box);
    }

    // initializes fees text field
    private void initializeFeesBox(JTextField box) {
        box.setBounds(100, 220, 200, 25);
        box.setBackground(Color.PINK);
        add(box);

        box.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                changed();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                changed();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                changed();
            }
        });
    }

    // checks if required fields are empty and activates button if not
    public void changed() {
        if (nameField.getText().length() == 0 || feesField.getText().length() == 0) {
            submit.setEnabled(false);
        } else {
            submit.setEnabled(true);
        }
    }

    // adds reservation using input details
    private void processDetails() {
        reserveName = nameField.getText();
        reserveTime = (int) availableTimesDrop.getSelectedItem();
        reserveFees = Double.parseDouble(feesField.getText());

        reservations.addReservation(new Reservation(reserveName, reserveTime, reserveFees));

        initializeConclusion();
    }

    // message after reserving
    private void initializeConclusion() {
        makeText9("Reservation complete for " + reserveName);
        if (reserveFees >= 10) {
            makeText10(reserveName + " will have VIP Lounge and locker access.");
        } else if (reserveFees >= 5) {
            makeText10(reserveName + " will have locker access.");
        }
    }

    // The following methods are for the "Check Reservations" screen

    // MODIFIES: this
    // EFFECTS: checks whether user has a reservation under a given name
    private void doCheckReservation() {
        initializeCheck();
    }

    // initializes name text field
    private void initializeCheckNameBox(JTextField box) {
        box.setBounds(100, 140, 200, 25);
        box.setBackground(Color.PINK);
        add(box);

        box.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                checkChanged();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                checkChanged();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                checkChanged();
            }
        });
    }

    // checks if name text field is empty
    public void checkChanged() {
        if (checkNameField.getText().length() == 0) {
            submitCheck.setEnabled(false);
        } else {
            submitCheck.setEnabled(true);
        }
    }

    // processes input and checks if given name is in database
    private void processCheck() {

        checkReserveName = checkNameField.getText();
        nameFound = false;

        for (ArrayList<Reservation> timeSlot : reservations.getAllReservations()) {
            for (Reservation reservation : timeSlot) {
                if (checkReserveName.equals(reservation.getName())) {
                    reservationTextRefresh();
                    makeText4("Reservation found.");
                    makeText5("Reservation Time (in 24 hours): " + reservation.getTime() + ":00 hours");
                    makeText6("Lounge Access: " + reservation.isLoungeAccess());
                    makeText7("Locker Access: " + reservation.isLockerAccess());
                    nameFound = true;
                }
            }
        }

        if (!nameFound) {
            makeText4("Your name was not found in our reservation database.");
            makeText5("Please make a new reservation. Thank you.");
            reservationTextRefresh();
        }
    }

    // initializes check screen for new use
    private void initializeCheck() {

        if (makePanelOpen) {
            submit.setVisible(false);
            nameField.setVisible(false);
            availableTimesDrop.setVisible(false);
            feesField.setVisible(false);
            makeText3("");
            makeText4("");
            makeText6("");
            makeText7("");
            makeText9("");
            makeText10("");
            makePanelOpen = false;
        }

        makeText2("Name:");
        submitCheck = makeButton("Submit", 300, 140, 80, 30);
        submitCheck.setEnabled(false);
        add(submitCheck);

        makeHeader("Check Reservations");
        makeSubHeader("Please type your registered name");

        checkNameField = new JTextField();
        initializeCheckNameBox(checkNameField);
    }

    // refreshes screen for new use
    private void reservationTextRefresh() {
        makeText6("");
        makeText7("");
        makeText8("");
        makeText9("");
        makeText10("");
    }

    // EFFECTS: saves the list of reservations to a local file using JSON
    private void doSaveReservations() {
        try {
            jsonWriter.open();
            jsonWriter.write(reservations);
            jsonWriter.close();
            makeFooter("Saved current state to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            makeFooter("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads the list of reservations from a local file using JSON
    private void doLoadReservations() {
        try {
            reservations = jsonReader.read();
            makeFooter("Loaded last save file from " + JSON_STORE);
        } catch (IOException e) {
            makeFooter("Unable to read from file: " + JSON_STORE);
        }
    }

    // EFFECTS: prints out a log of all actions taken
    private void logPrinter(EventLog el) {
        for (Event e: el) {
            System.out.println(e.getDescription());
        }
    }

}
