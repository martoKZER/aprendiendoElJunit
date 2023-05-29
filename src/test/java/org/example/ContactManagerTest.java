package org.example;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class ContactManagerTest {
    private ContactManager contactManager;
    // Anotacion importante 1. Lo primero que se tiene que ejecutar al darle a run.
    @BeforeAll
    public static void setupAll() {
        System.out.println("Should Print Before All Tests");
    }
    // Anotacion importante 2, antes de cualquier prueba unitaria se ejecuta esto.
    @BeforeEach
    public void setup() {
        System.out.println("Instantiating Contact Manager");
        contactManager = new ContactManager();
    }
    // Anotacion importante 3, cualquier prueba unitaria tiene que tenerla.
    @Test
    @DisplayName("Should create contact.")
    public void shouldCreateContact() {
        contactManager.addContact("Perro", "Sanche", "0123456789");
        Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
        Assertions.assertEquals(1, contactManager.getAllContacts().size());
        Assertions.assertTrue(contactManager.getAllContacts().stream()
                .filter(contact -> contact.getFirstName().equals("Perro") &&
                        contact.getLastName().equals("Sanche") &&
                        contact.getPhoneNumber().equals("0123456789"))
                .findAny()
                .isPresent());
    }

    @Test
    @DisplayName("Should Not Create Contact When First Name is Null")
    public void shouldThrowRuntimeExceptionWhenFirstNameIsNull() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            contactManager.addContact(null, "Doe", "0123456789");
        });
    }

    @Test
    @DisplayName("Should Not Create Contact When Last Name is Null")
    public void shouldThrowRuntimeExceptionWhenLastNameIsNull() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            contactManager.addContact("John", null, "0123456789");
        });
    }

    @Test
    @DisplayName("Should Not Create Contact When Phone Number is Null")
    public void shouldThrowRuntimeExceptionWhenPhoneNumberIsNull() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            contactManager.addContact("John", "Doe", null);
        });
    }
}