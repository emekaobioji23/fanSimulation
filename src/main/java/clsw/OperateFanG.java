package clsw;

import java.util.HashMap;
import java.lang.Math;

public class OperateFanG extends Thread {

    //the fan operated by this Fan operator
    FanG fang;

    public OperateFanG(FanG f) {
        fang = f;
    }

    public double delayDuration() {
        return fang.speed;
    }

    @Override
    public void run() {
        System.out.println("OFG:run-" + fang.name + "[entry]");
        /*
         * if a fan is rotating, continue rotating it at the set speed i.e.
         * constant velocity. rotation is simply redrawing the blades at
         * different preset coordinates after a delay, giving impression of real
         * rotation. The delay is proportional to the level, the higher levels
         * have lower delays producing more speed. There is a method in FanG
         * that returns the next (x2,y2)given the previous (x2, y2) for the
         * three blades. After obtaining the new coordinates, the Fan is
         * repainted on the screen. Another operator is created and the current
         * fan given to it. Hence each operator moves one coordinate and hands
         * over. However, each time the run method of the operator is executed
         * it checks if fan is rotating before it continues rotating at constant
         * speeds, else it stops fan. The celerate method is yet to be written
         * so that the Fan does starts and stops gracefully.
         */
        try {
            if (fang.isRotating) {
                System.out.println("OFG:run-"+fang.name+"[speed="+fang.speed+"]");
                //sleep duration
                Double sd = delayDuration();
                System.out.println("OFG:run-"+fang.name+ "[sleep="+sd+"]");

                sleep(sd.longValue());
                fang.setNextBladePostions();
                fang.repaint();
                OperateFanG ofang = new OperateFanG(fang);
                ofang.start();
            } else {
                if (!fang.bladesAreAtInitialPositions()) {
                    System.out.println("OFG:run-"+fang.name+"[speed="+fang.speed+"]");
                    //sleep duration
                    Double sd = delayDuration();
                    System.out.println("OFG:run-"+fang.name+ "[sleep="+sd+"]");
                    fang.speed = fang.speed + 1;
                    sleep(sd.longValue());
                    if (fang.speed > 100) {
                        fang.resetBladePositionsToInitial();
                    } else {
                        fang.setNextBladePostions();
                    }
                    //redraw the fan
                    fang.repaint();
                    OperateFanG ofang = new OperateFanG(fang);
                    ofang.start();
                }
            }
        } catch (Exception e) {
            System.err.println(e.toString());
        }
        System.out.println("OFG:run-" + fang.name + "[exit]");
    }

}
