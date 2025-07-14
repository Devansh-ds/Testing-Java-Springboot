package com.devansh.annotations;

import com.devansh.Calculator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrderCalculatorTest {

    @Order(3)
    @Test
    void testAdd() {
        System.out.println("Add method");
        Calculator calc = new Calculator();
        assertEquals(5, calc.add(1, 4));
    }

    @Order(2)
    @Test
    void testSubtract() {
        System.out.println("Subtract method");
        Calculator calc = new Calculator();
        assertEquals(-3, calc.subtract(1, 4));
    }

    @Order(1)
    @Test
    void testMultiply() {
        System.out.println("Multiply method");
        Calculator calc = new Calculator();
        assertEquals(12, calc.multiply(3, 4));
    }

    @Order(4)
    @Test
    void testDivide() {
        System.out.println("Divide method");
        Calculator calc = new Calculator();
        assertEquals(0.5, calc.divide(2, 4));
    }

}
