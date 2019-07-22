package com.santamaria.flappykek.tools;

import java.util.Calendar;

/**
 * Created by Nicolò Santamaria on 13/12/17.
 */

public class EasterEgg {
    private static Calendar cal = Calendar.getInstance();
    private static int dayOfMonth;
    private static int month;

    public EasterEgg () {
        dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
        month = cal.get(Calendar.MONTH);
    }

    public boolean isXmas () {
        return (dayOfMonth > 14 && month == 11) || (dayOfMonth < 16 && month == 0);
    }
}
