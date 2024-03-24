/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clsw;

/**
 *
 * @author emeka
 */
public class TestCirlceEqns {

    public static void main(String[] args) {
        int x2 = 200;
        int y2 = 100;
        double a = 0;
        for (int i = 0; i < 720; i++) {
//            y2 = y2 * -1;
//            x2 = x2 - 100;
//            y2 = y2 + 100;
            a = a + 1;
            Double sina = Math.sin(Math.toRadians(a));
            Double cosa = Math.cos(Math.toRadians(a));
            //System.out.println("sina = " + sina + " cosa =" + cosa);

            Double xd2 = cosa * 100;
            Double yd2 = sina * 100;

            x2 = xd2.intValue();
            y2 = yd2.intValue();

            x2 = x2 + 100;
            y2 = y2 - 100;
            y2 = y2 * -1;
            System.out.println(x2);
            System.out.println(y2);
        }
    }
;
}
