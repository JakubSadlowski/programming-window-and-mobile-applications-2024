# **Animal Shelter Management System**

A **Java-based Animal Shelter Management System** designed to assist animal shelters in managing animals, tracking adoptions, and handling various administrative tasks. This project features a **Graphical User Interface (GUI)** for both administrators and clients, providing an intuitive and interactive way to manage shelter data.

---

## **Features**

### **For Administrators**
- **Login**: Secure login system to authenticate admin users.
- **Shelter Management**:
  - Add, remove, and modify shelters.
  - View and manage shelter details such as name, phone number, and capacity.
- **Animal Management**:
  - Add, remove, and modify animal records (name, species, condition, age, and price).
  - Track adoptions and remove adopted animals.
- **Data Summary**: View detailed summaries of shelter occupancy and animals' information.

### **For Clients**
- **View Shelters**: Browse a list of available shelters.
- **View Animals**: Browse the animals available for adoption at each shelter.
- **Adopt Animals**: Choose and adopt an animal from the shelter.
- **Request Contact**: Easily request contact with a shelter for adoption inquiries.

### **Data Import/Export**
- **CSV Export**: Export shelter animal data to CSV files for backup or analysis
- **CSV Import**: Import animal data from CSV files to populate shelters quickly
- **Reflection-based Processing**: Automatic handling of Animal class fields for flexible data structure
- **Error Handling**: Robust error management during file operations
- **Data Validation**: Ensures data integrity during import/export operations

---

## **Architecture**

The system utilizes the **Facade Design Pattern** to maintain a clear separation between the user interface and the business logic and data management layers.

### **Core Components**
- **`AnimalShelter`**: Represents a shelter with animals and relevant details.
- **`Animal`**: Represents an individual animal with details such as species, age, and condition.
- **`ShelterManager`**: Manages shelters and animals, including adding, removing, and modifying records.
- **`ShelterFacade`**: Provides a simplified interface for interacting with shelters and animals.
- **`AccountsManager`**: Handles user authentication and login functionality.
- **`ShelterIO`**: Manages data import/export operations for shelter data.

---

## **Database Integration**

The system uses a **MySQL database** for persistent data storage, ensuring reliable management of shelters and animals. **Hibernate** serves as the ORM (Object-Relational Mapping) tool to simplify database operations.

### **Database Features**
- **Shelters**:
  - Stored with details like name, capacity, and phone number.
  - Linked to animals housed within the shelter.
- **Animals**:
  - Stored with details such as name, species, age, condition, and price.
  - Linked to their respective shelters.
- **User Authentication**:
  - Securely stores accounts for administrators and clients.

The DAO (Data Access Object) pattern ensures clean and efficient interaction with the database.

---

## **IO Operations**

The system includes robust input/output functionality for data management:

### **CSV Export Features**
- Dynamic field extraction using Java Reflection
- Automatic header generation from Animal class fields
- Structured data writing with proper field separation
- Support for all Animal attributes including custom types

### **CSV Import Features**
- Header-based field mapping
- Automatic type conversion for different field types
- Support for:
  - String fields
  - Integer values
  - Double values
  - Enum types (AnimalCondition)
- Comprehensive error handling for file operations

---

## **GUI**

The user interface is developed with **JavaFX**, offering a modern and user-friendly design. Key components include:
- **Login Panel**: For administrators and clients.
- **Shelter and Animal List View**: Allows users to browse available shelters and animals.
- **Forms**: For adding and modifying shelters and animals.
- **Import/Export Controls**: Interface for managing data files.

---

## **Technologies Used**
- **Java**: Backend logic.
- **JavaFX**: GUI development.
- **Hibernate**: Database management.
- **MySQL**: Data persistence.
- **Facade Pattern**: Simplifies interaction with business logic.
- **Java Reflection**: Dynamic handling of class fields for IO operations.

---

## **Getting Started**

### **Prerequisites**
- Java 11 or higher
- MySQL server
- Maven (for project dependencies)

### **Setup Instructions**
1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/animal-shelter-management.git
   ```
2. Set up the MySQL database:
   - Create a database named `animal_shelter`.
   - Import the provided schema and data file (if available).
3. Configure database access in `hibernate.cfg.xml`.
4. Build the project using Maven:
   ```bash
   mvn clean install
   ```
5. Run the application:
   ```bash
   mvn javafx:run
   ```

### **Data Management**
1. Exporting data:
   - Select a shelter from the list
   - Click "Export/Import file"
   - Choose export option and specify filename
   - Data will be saved in CSV format

2. Importing data:
   - Select target shelter
   - Click "Export/Import file"
   - Choose import option and select CSV file
   - Ensure CSV format matches system requirements
