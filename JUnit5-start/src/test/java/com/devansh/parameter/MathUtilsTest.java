package com.devansh.parameter;

import com.devansh.MathUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.ValueSources;

import static org.junit.jupiter.api.Assertions.*;

class MathUtilsTest {

    @ParameterizedTest
    @ValueSource(ints = {2, 4, 8, 10})
    void isEvenTest(int number) {
        MathUtils mathUtils = new MathUtils();
        assertTrue(mathUtils.isEven(number));
    }

    @ParameterizedTest
    @ValueSource(strings = {"John", "JUnit"})
    void valueSourceTest(String num) {
        assertNotNull(num);
    }
}






















