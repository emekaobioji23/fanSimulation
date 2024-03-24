package clsw;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.geom.Ellipse2D;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.BorderLayout;
import java.util.HashMap;

public class FanG extends JLabel {

    //coordinates of box containing fan rotor
    //box containig circle boundary coordinate x1
    public Integer bx1 = 50;
    //box containig circle boundary coordinate y1
    public Integer by1 = 50;
    //box containig circle boundary coordinate x2, added to bx1
    public Integer bx2 = 100;
    //box containig circle boundary coordinate y2, added to by1
    public Integer by2 = 100;
    
//x coordinate center of circle
    public int ccx = 100; 
    //y coordinate center of circle; made public because was assessed by ...
    public int ccy = 100;

    //each fan knows it's own creation position in the pack
    public static int fanCount = 0;
    public String name;//name of fan
    //the spacing on the screen to avoid cluttering
    public static int fanSpacing = 10;
    //max width of JPanel, on the screen starting from x=0, and y=0
    public static final int MX = 200;
    //max height of JPanel
    public static final int MY = 200;
    //Screen coordinate (0,0) signifies left-top corner
    public static int screenCordX = 0;
    public static int screenCordY = 0;

    /*
     * blade rest state coordinates; all have the l*x1, l*y1 as the centre of
     * the rotor which is (ccx,ccy).
     *
     * the coordinates x2 and y2 of each blade at rest is determined by a unit
     * of an angle determined by 360 degrees divided by number of blades.
     *
     * blade 1
     */
    public Integer l1x2 = MX;
    public Integer l1y2 = ccy;

    //blade 1.5
    public Integer l15x2 = ccx;
    public Integer l15y2 = 0;

    //blade 2
    public Integer l2x2 = 0;
    public Integer l2y2 = ccy;

    //blade 3
    public Integer l3x2 = ccx;
    public Integer l3y2 = MY;

    /*
     * blades and blade positions based on angle radius of circle on which blade
     * points move displacement to make JLabel on which the fan is drawn that is
     * like a 1st quadrant turned over the second quadrant to have the origin at
     * 0,0
     */
    public HashMap<Integer, FanBlade> blades;
    public HashMap<Integer, Point> points;
    int radius = 100;
    int displacement = 100;
    /*
     * properties capturing the state of the fan holds the speed levels, by
     * default is off which is speed level 0
     */
    public double speed = 0.0;
    public boolean isRotating = false;//the fan is off

    /*
     * objects used to simulate the fan visually on the screen, the g/G at the
     * end of the names indicate graphics version
     */
    JFrame jframe;
    FanSwitchG fsg;
    Graphics2D g2d;
    Ellipse2D ellipse;

    //object used to animate or operate the fan
    OperateFanG ofang;

    public FanG(int numberOfBlades) {
        //create a list for keeping all points on the circle circumference
        //separated by angle of 1 degrees.
        generateAllValidBladePoints();
        /*
         * build blades correcting for numberOfBlades that is not a factor of
         * 360
         */
        buildBladesAndSetToInitialPosition(numberOfBlades);

        //count number of fan created
        fanCount = fanCount + 1;
        //set fan name
        name = "fan" + fanCount;
        //set fan dimensions
        setPreferredSize(new Dimension(MX, MY));

        /*
         * create a switch for this fan, and set the dimensions
         */
        fsg = new FanSwitchG(this);
        //define fan switch height fsh
        int fsh = 40;
        fsg.setPreferredSize(new Dimension(MX, fsh));

        /*
         * displaying fan on the screen using a JFrame object jframe which
         * serves as canvas on which we paint and simulate.
         */
        jframe = new JFrame();
        //make fan display above switch
        jframe.setLayout(new BorderLayout());
        //get the width of the current screen device
        int currentScreenWidth = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        //position this fan on screen
        jframe.setLocation(screenCordX, screenCordY);
        /*
         * check whether the next fan can fit on current row of the screen, and
         * if not move it to next row (making sure it does not overlap with
         * first row, else set the next screen position for next fan
         */
        if (screenCordX + (2 * MX) < currentScreenWidth) {
            screenCordX = screenCordX + MX + fanSpacing;
        } else {
            screenCordX = 0;
            System.out.println(name + "[screenCordX+(2*maxX) > currentScreenWidth, and screenCordX set to zero "+ screenCordX+"]");
            screenCordY = screenCordY + MY + fsh + fanSpacing * 6;
        }
        System.out.println(name + "[screenCordX=" + screenCordX + ", "+ "screenCordY=" + screenCordY+"]");

        /*
         * set properties of jframe
         */
        jframe.setBackground(Color.WHITE);
        jframe.setAlwaysOnTop(true);
        jframe.setResizable(false);

        /*
         * make jframe end program when closed
         */
        jframe.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        /*
         * add this fan and switch to jframe
         */
        jframe.add(this, BorderLayout.PAGE_START);
        jframe.add(fsg, BorderLayout.PAGE_END);

        //finish up with properties of jframe
        jframe.setVisible(true);
        jframe.pack();
    }

    @Override
    public void paint(Graphics g) {
        //get class Graphics2D and create object g2d for 
        //drawing on other objects
        g2d = (Graphics2D) g;
        g2d.setPaint(Color.LIGHT_GRAY);
        /*
         * determine the appropriate thicknness of line mimicking blade
         */
        int bladeWidth = 60;
        if (blades.size() > 3) {
            bladeWidth = 250 / blades.size();
        }
        /*
         * draw blades
         */
        g2d.setStroke(new BasicStroke(bladeWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        for (int i = 0; i < blades.size(); i++) {
            g2d.drawLine(ccx, ccy, blades.get(i).x2, blades.get(i).y2);
        }
        /*
         * draw the rotor
         */
        g2d.setStroke(new BasicStroke(1));
        g2d.setPaint(Color.DARK_GRAY);
        ellipse = new Ellipse2D.Double(bx1, by1, bx2, by2);
        g2d.fill(ellipse);
        g2d.draw(ellipse);
    }


    public void buildBladesAndSetToInitialPosition(int numberOfBlades) {
        blades = new HashMap();
        if (!(numberOfBlades > 1 && numberOfBlades < 360)) {
            numberOfBlades = 3;
        }
        numberOfBlades = (numberOfBlades / 3) * 3;
        if (numberOfBlades == 0) {
            numberOfBlades = 1;
        }
        int anglebwblades = 360 / numberOfBlades;
        int angle;
        FanBlade fb;
        Point p;
        for (int i = 0; i < numberOfBlades; i++) {
            angle = i * anglebwblades;
            p = points.get(angle);
            fb = new FanBlade(p.x2, p.y2, angle);
            blades.put(i, fb);
        }
    }
    /*
     * checks if blades are set to the position they had at creation
     */
    public boolean bladesAreAtInitialPositions() {
        boolean toreturn = true;
        int anglebwblades = 360 / blades.size();
        int angle;
        FanBlade fb;
        Point p;
        for (int i = 0; i < blades.size(); i++) {
            angle = i * anglebwblades;
            p = points.get(angle);
            fb = blades.get(i);
            if (!(fb.a == angle && fb.x2 == p.x2 && fb.y2 == p.y2)) {
                toreturn = false;
                break;
            }
        }
        return toreturn;
    }
    /*
     * set blades to the position they had at creation
     */
    public void resetBladePositionsToInitial() {
        int anglebwblades = 360 / blades.size();
        int angle;
        FanBlade fb;
        Point p;
        for (int i = 0; i < blades.size(); i++) {
            angle = i * anglebwblades;
            p = points.get(angle);
            fb = blades.get(i);
            fb.x2 = p.x2;
            fb.y2 = p.y2;
            fb.a = angle;
        }
    }

    /*
     * change location of blades for each blade, get its angle. Increment the 
     * angle by 1, and get the points of the new angle. Remember the points 
     * for all angles from 0 to 360 inclusive have been generated stepping by 1 
     * for a given radius, and adjusted for plotting on a JLabel which is like 
     * the first quadrant of coordinate geometry turned onto the second quadrant
     *  
  0,0 -------------------200,0
      |			|
      |			|
      |			|
      |			|
      |			|
      |			|
 0,200|			|200,200

     */
    public void setNextBladePostions() {
        FanBlade fb;
        Integer a;
        Point p;
        for (int i = 0; i < blades.size(); i++) {
            fb = blades.get(i);
            a = fb.a;
            a = a + 1;
            if (a > 360) {
                a = 0;
            }
            p = points.get(a);
            fb.x2 = p.x2;
            fb.y2 = p.y2;
            fb.a = a;
        }

    }

    public void generateAllValidBladePoints() {
        points = new HashMap();
        //declare objects that will be used to build the list of points
        Double sina, cosa, xd2, yd2;
        int x2, y2;
        Point p;
        /*
         * build list of points using formula for angle a, 
         * x = cos(a)* radius,a in radians and y = sin(a)*radius, a in radians.
        * to adjust for the JLabel on which the fan is drawn which is like the
        * first quadrant in coordinate geometry folded onto the second, 
        * we add or subtract the displacement.
        * */
        for (int a = 0; a <= 360; a++) {
            sina = Math.sin(Math.toRadians(a));
            cosa = Math.cos(Math.toRadians(a));
            xd2 = cosa * radius;
            yd2 = sina * radius;

            x2 = xd2.intValue();
            y2 = yd2.intValue();

            x2 = x2 + displacement;
            y2 = y2 - displacement;
            y2 = y2 * -1;
            p = new Point(x2, y2);
            points.put(a, p);
        }
    }
}
