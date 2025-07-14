package com.devansh.annotations;

import com.devansh.Factorial;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FactorialTest {

    @Test
    void factorial() {
        Factorial f = new Factorial();
        assertEquals(120, f.factorial(5));
    }
}