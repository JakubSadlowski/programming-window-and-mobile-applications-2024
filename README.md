# Animal Shelter Management System

This project is a Java-based Animal Shelter Management System that helps shelters manage animals, track adoptions, and handle various administrative tasks. It features a Graphical User Interface (GUI) for both administrators and clients, providing an intuitive and interactive way to manage shelter data.

## Features

### For Administrators:
- **Login:** Secure login system to authenticate admin users.
- **Shelter Management:**
  - Add, remove, and modify shelters.
  - View and manage shelter details like name, phone number, and capacity.
- **Animal Management:**
  - Add, remove, and modify animal records (name, species, condition, age, and price).
  - Track adoptions and remove adopted animals.
- **Data Summary:** View detailed summaries of shelter occupancy and animals' information.

### For Clients:
- **View Shelters:** See a list of shelters available.
- **View Animals:** Browse the animals available for adoption at each shelter.
- **Adopt Animals:** Choose and adopt an animal from the shelter.
- **Request Contact:** Easily request contact with a shelter for adoption inquiries.

## Architecture

The application uses the **Facade Design Pattern** to separate the user interface from the business logic and data management layers. The core components are:

- **AnimalShelter:** Represents a shelter that holds animals and relevant details.
- **Animal:** Represents an individual animal with details like species, age, and condition.
- **ShelterManager:** Manages shelters and animals, including adding, removing, and modifying records.
- **ShelterFacade:** Provides a simplified interface for interacting with shelters and animals.
- **AccountsManager:** Handles user authentication and login functionality.
- **DAO:** Data Access Object layer for handling the persistence of shelter and animal data (can be extended for database integration).

## GUI

The user interface is built using **JavaFX**, providing a modern and responsive design. It includes:
- A login panel for administrators and clients.
- A shelter and animal list view for browsing available shelters and animals.
- Forms for adding and modifying shelters and animals.

## Technologies Used
- **JavaFX:** For building the GUI.
- **Java:** The main programming language used for the backend logic.
- **DAO Pattern:** To handle data storage and retrieval.
- **Facade Pattern:** To simplify interaction with the business logic.
- **Collections Framework:** For managing shelters, animals, and other data structures.

## Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/animal-shelter-management-system.git
