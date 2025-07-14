package com.devansh.annotations;

import com.devansh.Calculator;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BeforeAfterTest {

    private static Calculator calc;

    @BeforeAll
    static void testSetup() {
        calc = new Calculator();
        System.out.println("Setup method calling....");
    }

    @AfterEach
    void testTearDown() {
        System.out.println("after each method calling....");
    }

    @Disabled
    @DisplayName("add 2 nums")
    @Test
    public void testAdd() {
        assertEquals(3, calc.add(1, 2));
        System.out.println("Add method calling....");
    }

    @DisplayName("Subtract 2 number")
    @Test
    public void testSubtract() {
        assertEquals(-1, calc.subtract(1, 2));
        System.out.println("Subtract method calling....");
    }

    @DisplayName("multiply 2 nums")
    @Test
    public void testMultiply() {
        assertEquals(10, calc.multiply(2, 5));
        System.out.println("Multiply method calling....");
    }
}




















