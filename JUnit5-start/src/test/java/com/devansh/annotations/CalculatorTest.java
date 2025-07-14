package com.devansh.annotations;

import com.devansh.Calculator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Calculator operations Test")
class CalculatorTest {

    @DisplayName("add 2 nums \uD83D\uDD21\uD83C\uDF0D\uD83D\uDCCA")
    @Test
    public void testAdd() {
        Calculator calc = new Calculator();
        int actualResult = calc.add(1, 2);
        assertEquals(3,actualResult);
    }

    @DisplayName("Subtract 2 number")
    @Test
    public void testSubtract() {
        Calculator calc = new Calculator();
        assertEquals(-1, calc.subtract(1, 2));
    }

}