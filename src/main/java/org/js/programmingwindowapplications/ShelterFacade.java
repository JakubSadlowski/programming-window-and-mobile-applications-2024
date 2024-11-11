package org.js.programmingwindowapplications;

public class ShelterFacade {

    /**
     * Validates the user's credentials.
     *
     * @param username The username input by the user.
     * @param password The password input by the user.
     * @return "admin" if the credentials match an admin account,
     *         "user" if they match a regular user account,
     *         or null if the credentials are invalid.
     */
    public String validateUser(String username, String password) {
        if ("admin".equals(username) && "adminpass".equals(password)) {
            return "admin";
        } else if ("user".equals(username) && "userpass".equals(password)) {
            return "user";
        }
        return null;
    }

    // For example:
    public void addAnimal(String name, String type) {
        // Logic to add a new animal to the shelter
    }

    public void deleteAnimal(int id) {
        // Logic to delete an animal by id
    }

    public void requestAdoption(int animalId, String username) {
        // Logic to handle an adoption request from a user
    }

    public void contactShelter(String message) {
        // Logic to send a message to the shelter
    }
}


