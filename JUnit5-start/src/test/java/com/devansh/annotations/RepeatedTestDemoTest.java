package com.devansh.annotations;

import com.devansh.Calculator;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class RepeatedTestDemoTest {

    //    setup methods will be called repeatedly by the repeated test.

    @BeforeEach
    void setup() {
        System.out.println("Setup method calling");
    }

    @AfterEach
    void teardown() {
        System.out.println("Teardown method calling");
    }

    @BeforeAll
    public static void setupAll() {
        System.out.println("before class ---- Setup method calling");
    }

    @AfterAll
    public static void teardownAll() {
        System.out.println("after class ---- Teardown method calling");
    }

    @DisplayName("addition repeatedly")
    @RepeatedTest(value = 5, name = RepeatedTest.LONG_DISPLAY_NAME)
    public void addTest() {
        Calculator calc = new Calculator();
        assertEquals(5, calc.add(2, 3));
    }

}
