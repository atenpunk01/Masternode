package co.th.aten.network.util;


public class BathText {

    public static double round( double a, int i ) {
        for( int ii = 0; ii < i; ii++ ) {
            a *= 10; // Shift left 1 position
        }

        if (a % 5 == 0) {
            a = StrictMath.ceil(a);
        } else {
            a = StrictMath.round(a);
        }

        for( int ii = 0; ii < i; ii++ ) {
            a /= 10; // Shift right 1 position
        }
        return a;
    }

    public static String bahtText( int a ) {
        return bahtText((double) a);
    }

    public static String bahtText( long a ) {
        return bahtText((double) a);
    }

    public static String bahtText( float a ) {
        return bahtText((double) a);
    }

    public static String bahtText( double a ) {

        a = round(a, 2); // For sure

        String bt = "";
        double a1 = Math.floor(a);

        if (a == 0) {
            return "\u0E28\u0E39\u0E19\u0E22\u0E4C\u0E1A\u0E32\u0E17";
        }

        bt = thaiText((long) a1) + (a1 >= 1 ? "\u0E1A\u0E32\u0E17" : "");
        if (a1 == a) {
            bt += "\u0E16\u0E49\u0E27\u0E19";
        } else {
            if (a < 1) {
                bt += "\u0E28\u0E39\u0E19\u0E22\u0E4C\u0E1A\u0E32\u0E17";
            }
            bt += thaiText(Math.round((a - a1) * 100)) + "\u0E2A\u0E15\u0E32\u0E07\u0E04\u0E4C";
        }
        return bt;
    }

    private static String thaiText( long a ) {
        String tt = "";
        long tmpA = a;
        try {
            long unit = Math.round(a / 1000000);

            if (unit > 0) { // More thai million
                tt = thaiText(unit) + "\u0E25\u0E49\u0E32\u0E19" + thaiText(a % 1000000);
                return tt;
            } else { // Less than million
                unit = Math.round(a / 100000);
                if (unit > 0) {
                    tt = (unit == 1 ? "\u0E2B\u0E19\u0E36\u0E48\u0E07" : thaiWord(unit)) + "\u0E41\u0E2A\u0E19";
                    a = a % 100000;
                }

                unit = Math.round(a / 10000);
                if (unit > 0) {
                    tt += (unit == 1 ? "\u0E2B\u0E19\u0E36\u0E48\u0E07" : thaiWord(unit)) + "\u0E2B\u0E21\u0E37\u0E48\u0E19";
                    a = a - unit * 10000;
                }

                unit = Math.round(a / 1000);
                if (unit > 0) {
                    tt += (unit == 1 ? "\u0E2B\u0E19\u0E36\u0E48\u0E07" : thaiWord(unit)) + "\u0E1E\u0E31\u0E19";
                    a = a - unit * 1000;
                }

                unit = Math.round(a / 100);
                if (unit > 0) {
                    tt += (unit == 1 ? "\u0E2B\u0E19\u0E36\u0E48\u0E07" : thaiWord(unit)) + "\u0E23\u0E49\u0E2D\u0E22";
                    a = a - unit * 100;
                }

                unit = Math.round(a / 10);
                if (unit > 0) {
                    if (unit == 2) {
                        tt += "\u0E22\u0E35\u0E48\u0E2A\u0E34\u0E1A";
                    } else if (unit == 1) {
                        tt += "\u0E2A\u0E34\u0E1A";
                    } else {
                        tt += thaiWord(unit) + "\u0E2A\u0E34\u0E1A";
                    }
                    a = a - unit * 10;
                }

                if (tmpA == 1) {
                    tt += "\u0E2B\u0E19\u0E36\u0E48\u0E07";
                }
                if (tmpA > 1 && a > 0) {
                    tt += thaiWord(a);
                }
                return tt;
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return tt;
    }

    private static String thaiWord( long a ) {
        if (a == 0) {
            return "\u0E28\u0E39\u0E19\u0E22\u0E4C";
        }
        if (a == 1) {
            return "\u0E40\u0E2D\u0E47\u0E14";
        }
        if (a == 2) {
            return "\u0E2A\u0E2D\u0E07";
        }
        if (a == 3) {
            return "\u0E2A\u0E32\u0E21";
        }
        if (a == 4) {
            return "\u0E2A\u0E35\u0E48";
        }
        if (a == 5) {
            return "\u0E2B\u0E49\u0E32";
        }
        if (a == 6) {
            return "\u0E2B\u0E01";
        }
        if (a == 7) {
            return "\u0E40\u0E08\u0E47\u0E14";
        }
        if (a == 8) {
            return "\u0E41\u0E1B\u0E14";
        }
        if (a == 9) {
            return "\u0E40\u0E01\u0E49\u0E32";
        }

        return "";
    }

}
