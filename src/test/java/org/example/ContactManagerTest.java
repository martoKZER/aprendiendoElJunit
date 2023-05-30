package org.example;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS) // Sirve para eliminar la palabra static de los metodos "BeforeAll" y "AfterAll", una tontada.
class ContactManagerTest {
    private ContactManager contactManager;

    // Anotacion importante 1. Lo primero que se tiene que ejecutar al darle a run.
    @BeforeAll
    public void setupAll() {
        System.out.println("Should Print Before All Tests");
    }

    // Anotacion importante 2, antes de cualquier prueba unitaria se ejecuta esto.
    @BeforeEach
    public void setup() {
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
    @DisplayName("Should create contact.")
    @EnabledOnOs(value = OS.MAC, disabledReason = "Enabled Only on MAC OS.")
    // Este solo se ejecutaría si estuvieramos en un MAC.
    public void shouldCreateContactOnlyOnMac() {
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

    // SUPUESTOS o Assumptions.
    @Test
    @DisplayName("Test contact creation on Developer Machine")
    @Disabled
    public void shoudCreateContactCreationOnDev() {
        // Asumimos que estamos en la máquina del desarrollador, o que este entorno es el del desarrollador.
        Assumptions.assumeTrue("DEV".equals(System.getProperty("ENV")));
        contactManager.addContact("Perro", "Sanxe", "0123456789");
        assertFalse(contactManager.getAllContacts().isEmpty());
        assertEquals(1, contactManager.getAllContacts().size());
    }

    @DisplayName("Should test contact creation 5 times.")
    // TEST REPETIDOS Se elimina la anotacion Test y se pone esta para repetir muchas veces un test.
    // Se utiliza cuando hay valores atleatorios, por ejemplo, si se crearan contactos atleatorios.
    @RepeatedTest(value = 5, name = "Repeating Contact Creation Test: {currentRepetition} of {totalRepetitions}")
    public void shouldTestCreateContactRepeated() {
        contactManager.addContact("Perro", "Sanche", "0123456789");
        Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
        Assertions.assertEquals(1, contactManager.getAllContacts().size());
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

    // TEST PARAMETRIZADOS. Importar libreria junit-jupiter-params
    @DisplayName("Should test contact creation with value source.")
    @ParameterizedTest
    // Le pasas los numeros de telefono que quieres probar por aquí, y evitas el hardcodeo.
    @ValueSource(strings = {"0123456789", "0143456556", "0645387692", "0867987453"})
    public void shouldTestContactCreationUsingValueSource(String phoneNumber) {
        contactManager.addContact("Perro", "Sanche", phoneNumber);
        Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
        Assertions.assertEquals(1, contactManager.getAllContacts().size());
    }

    @DisplayName("Should test contact creation with the method source.")
    @ParameterizedTest
    @MethodSource("phoneNumberList")
    @Disabled
    public void shouldTestContactCreationUsingMethodSource(String phoneNumber) {
        contactManager.addContact("Perro", "Sanche", phoneNumber);
        Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
        Assertions.assertEquals(1, contactManager.getAllContacts().size());
    }

    public static List<String> phoneNumberList() {
        return Arrays.asList("0123456789, 0465748374", "0654374836");
    }

    // Cuidao con los false friends, after = despues, before = antes.
    @AfterEach
    public void tearDown() {
        System.out.println("Should execute after each test");
    }

    @AfterAll
    public void tearDownAll() {
        System.out.println("Should be executed at the end of the Test.");
    }
}