package com.example.mert.mobiluygulamagelitirme;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

public class Util {

    public static class TIME {
        public static String PAZARTESI = "Pazartesi";
        public static String SALI = "Salı";
        public static String CARSAMBA = "Çarşamba";
        public static String PERSEMBE = "Perşembe";
        public static String CUMA = "Cuma";
        public static String CUMARTESI = "Cumartesi";
        public static String PAZAR = "Pazar";

        public static String getDay(int day) {
            String dayText = "";
            switch (day) {
                case 1:
                    dayText = Util.TIME.PAZARTESI;
                    break;
                case 2:
                    dayText = Util.TIME.SALI;
                    break;
                case 3:
                    dayText = Util.TIME.CARSAMBA;
                    break;
                case 4:
                    dayText = Util.TIME.PERSEMBE;
                    break;
                case 5:
                    dayText = Util.TIME.CUMA;
                    break;
                case 6:
                    dayText = Util.TIME.CUMARTESI;
                    break;
                case 7:
                    dayText = Util.TIME.PAZAR;
                    break;
                default:
                    dayText = "Incorrect";
                    break;
            }

            return dayText;
        }

        public static String getTimebyString(int day, int hour, int during) {
            String time = "";
            String hourText = "00";
            if (hour < 10) {
                hourText = "0" + hour;
            } else {
                hourText = String.valueOf(hour);
            }
            String duringText = "00";
            if ((hour + during) < 10) {
                duringText = "0" + (hour + during);
            } else {
                duringText = String.valueOf((hour + during));
            }
            time = getDay(day) + " / " + hourText + ":00 - " + duringText + ":00";

            return time;
        }
    }

    public static float dpToPx(Context context, float valueInDp) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, valueInDp, metrics);
    }
}
