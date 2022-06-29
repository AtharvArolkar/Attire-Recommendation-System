package com.example.clothme.Recommendation;
public class ColourMatch {

    static double[] rgb_to_hsv(String hex)
    {

        double r = Integer.valueOf( hex.substring( 1, 3 ), 16 );
        double g = Integer.valueOf( hex.substring( 3, 5 ), 16 );
        double b = Integer.valueOf( hex.substring( 5, 7 ), 16 );
        // R, G, B values are divided by 255
        // to change the range from 0..255 to 0..1
        r = r / 255.0;
        g = g / 255.0;
        b = b / 255.0;

        // h, s, v = hue, saturation, value
        double cmax = Math.max(r, Math.max(g, b)); // maximum of r, g, b
        double cmin = Math.min(r, Math.min(g, b)); // minimum of r, g, b
        double diff = cmax - cmin; // diff of cmax and cmin.
        double h = -1, s = -1;

        // if cmax and cmax are equal then h = 0
        if (cmax == cmin)
            h = 0;

            // if cmax equal r then compute h
        else if (cmax == r)
            h = (60 * ((g - b) / diff) + 360) % 360;

            // if cmax equal g then compute h
        else if (cmax == g)
            h = (60 * ((b - r) / diff) + 120) % 360;

            // if cmax equal b then compute h
        else if (cmax == b)
            h = (60 * ((r - g) / diff) + 240) % 360;

        // if cmax equal zero
        if (cmax == 0)
            s = 0;
        else
            s = (diff / cmax) * 100;
        // compute v
        double v = cmax * 100;
        return new double[]{h,s,v};
        //System.out.println("(" + h + " " + s + " " + v + ")");
    }

    static double match (double[] colours){
        double test = colours[0]-colours[1];
        if (test < 40&&test>-40)
        {
            return test;
        }
        return 0;
    }

    public int getRank(String[] colours) {
        //String[] colours = {"f11111", "222222", "333333"};
        double[] colourtomatch = new double[2];
        int len = colours.length;
        double[][] HSVcolours = new double[len][3];
        double[] temp = new double[3];
        if (len == 1) {
            return (100);
        }
        for (int i = 0; i < len; i++) {
            temp = rgb_to_hsv(colours[i]);
            HSVcolours[i][0] = temp[0];
            HSVcolours[i][1] = temp[1];
            HSVcolours[i][2] = temp[2];
        }

        if (len == 2) {
            colourtomatch = new double[]{HSVcolours[0][0], HSVcolours[1][0]};
            if (match(colourtomatch) > 1)
            {
                return (int)match(colourtomatch);
            }
            else if (HSVcolours[0][2] < 40 || HSVcolours[1][2] < 40) {
                return 50;
            }
        }

        if (len == 3) {
            if (match(new double[]{HSVcolours[0][0], HSVcolours[1][0]})>=1||match(new double[]{HSVcolours[0][0], HSVcolours[2][0]})>=1||match(new double[]{HSVcolours[1][0], HSVcolours[2][0]})>=1)
            {
                return (int) match(new double[]{HSVcolours[0][0], HSVcolours[1][0]});
            }
            if (HSVcolours[0][2] < 40 || HSVcolours[1][2] < 40 || HSVcolours[2][2] < 40) {
                if (HSVcolours[0][2] < 40) {
                    colourtomatch[0] = HSVcolours[1][0];
                    colourtomatch[0] = HSVcolours[2][0];
                } else if (HSVcolours[1][2] < 40) {
                    colourtomatch[0] = HSVcolours[0][0];
                    colourtomatch[0] = HSVcolours[2][0];
                } else {
                    colourtomatch[0] = HSVcolours[0][0];
                    colourtomatch[0] = HSVcolours[1][0];
                }

                if (match(colourtomatch) >= 1) {
                    return (int)match(colourtomatch);
                }
            }

        }
        return 0;
    }

//    public static void main(String[] args) {
////        getRank(new String[]{"f11111", "222222", "333333"});
//        System.out.println( getRank(new String[]{"#f11111", "#222222", "#333333"}));
//    }

}
