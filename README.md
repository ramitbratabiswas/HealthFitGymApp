# Gym Reservation Project

## Proposal

In this gym reservation app, customers are able to make gym reservations
and check whether their reservation is in the database.

The gym has time slots at every hour from 8 am to 10 pm. In each time slot,
there is a maximum number of people the gym can accommodate. 
If the gym has more reservations than it has spots, the time will stop being
available and the user cannot make a reservation at that time.

The gym also runs on a "pay as much as you want" business model, i.e
you can choose your own fees. However, paying 5$ or more grants you locker access
and paying 10$ or more grants you access to a VIP Lounge, along with the lockers.

The user interface was modelled after the TellerApp program given on edX,
and so it uses a text-based command interface. The idea for the program was mostly
inspired by UBC's gym reservation system due to COVID regulations.

## User Stories

As a user, I would like to be able to
- **make** a gym reservation only at available times (depending on spots left and time of day)
- **add** gym reservations to a database of gym reservations
- **check** my older reservation statuses
- **ensure** that I am gaining access to my desired amenities (*locker/lounge*) when paying fees
- **save** my gym reservations to a file
- **load** my gym reservations from a file
- **use** a Graphical User Interface (GUI) to do the above conveniently

## Phase 4: Task 2

- if the user makes a reservation and closes the window, the event "User added new reservation to database" is logged
- if the user finds their reservation and closes, the event "User found existing reservation in database" is logged
- otherwise, no event is logged

## Phase 4: Task 3

UML_Design_Diagram.png

I would refactor it by creating more abstract classes for my methods in AppFrame
and creating a better inheritance structure 