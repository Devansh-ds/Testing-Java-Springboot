package com.devansh.annotations;

import com.devansh.Calculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NestedCalculatorTest {

    private Calculator calc;

    @BeforeEach
    void setup() {
        calc = new Calculator();
    }

    @Nested
    class AdditionTest {

        @Test
        public void addTestPositiveNumbers() {
            assertEquals(10, calc.add(5, 5));
        }

        @Test
        public void addTestNegativeNumbers() {
            assertEquals(-8, calc.add(-3, -5));
        }

        @Test
        public void addTestZeroNumbers() {
            assertEquals(0, calc.add(0, 0));
        }
    }

    @Nested
    class SubtractionTest {

        @Test
        public void testSubtractionPositiveNumbers() {
            assertEquals(0, calc.subtract(5, 5));
        }

        @Test
        public void testSubtractionNegativeNumbers() {
            assertEquals(2, calc.subtract(-3, -5));
        }
    }

}
