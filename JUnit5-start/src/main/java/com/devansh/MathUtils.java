package com.devansh;

public class MathUtils {

    public boolean isEven(int number) {
        return number % 2 == 0;
    }

    public boolean isWeekday(Days day) {
        return day != Days.SATURDAY && day != Days.SUNDAY;
    }


}
