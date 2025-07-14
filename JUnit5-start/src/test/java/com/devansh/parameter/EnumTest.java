package com.devansh.parameter;

import com.devansh.Days;
import com.devansh.MathUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.*;

public class EnumTest {

    @ParameterizedTest
    @EnumSource(value = Days.class, names = {"MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY"})
    public void enumTest(Days day) {
        MathUtils utils = new MathUtils();
        assertTrue(utils.isWeekday(day));
    }

}
