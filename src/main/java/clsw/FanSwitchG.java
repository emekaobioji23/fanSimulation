package clsw;

import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Objects;

public class FanSwitchG extends JPanel {

    //the fan associated with this switch
    FanG fang;

    //the object that will operate the fan to which the switch is attached.
    OperateFanG ofang;

    //the buttons for different fan speed levels
    JButton zero;
    JButton one;
    JButton two;
    JButton three;
    JButton four;
    JButton five;
    JButton six;
    /*
     * the button dimensions
     */
    int width = 20;
    int height = 30;

    //keep all buttons together 
    HashMap<Integer, JButton> buttons;

    //default background color
    Color defaultBgColor;

    /*
     * the constructor takes a Fan object
     */
    public FanSwitchG(FanG f) {
        fang = f;
        /*
         * instantiate each button, set the dimensions, clear the margins to
         * reduce to fit and fit all in a row, and make it sensitve to mouse and
         * keyboard activity
         */

        six = new JButton("6");
        six.setPreferredSize(new Dimension(width, height));
        six.setMargin(new Insets(0, 0, 0, 0));
        six.addActionListener((ActionEvent e) -> {
            fanSwitchButtonActivity(fang, ofang, 0, true, buttons, 6);
        });
        five = new JButton("5");
        five.setPreferredSize(new Dimension(width, height));
        five.setMargin(new Insets(0, 0, 0, 0));
        five.addActionListener((ActionEvent e) -> {
            fanSwitchButtonActivity(fang, ofang, 1, true, buttons, 5);
        });
        four = new JButton("4");
        four.setPreferredSize(new Dimension(width, height));
        four.setMargin(new Insets(0, 0, 0, 0));
        //four.setPreferredSize(new Dimension(width,height));
        four.addActionListener((ActionEvent e) -> {
            fanSwitchButtonActivity(fang, ofang, 2, true, buttons, 4);
        });
        three = new JButton("3");
        three.setPreferredSize(new Dimension(width, height));
        three.setMargin(new Insets(0, 0, 0, 0));
        three.addActionListener((ActionEvent e) -> {
            fanSwitchButtonActivity(fang, ofang, 3, true, buttons, 3);
        });
        two = new JButton("2");
        two.setPreferredSize(new Dimension(width, height));
        two.setMargin(new Insets(0, 0, 0, 0));
        two.addActionListener((ActionEvent e) -> {
            fanSwitchButtonActivity(fang, ofang, 4, true, buttons, 2);
        });
        one = new JButton("1");
        one.setPreferredSize(new Dimension(width, height));
        one.setMargin(new Insets(0, 0, 0, 0));
        one.addActionListener((ActionEvent e) -> {
            fanSwitchButtonActivity(fang, ofang, 5, true, buttons, 1);
        });
        zero = new JButton("0");
        zero.setPreferredSize(new Dimension(width, height));
        zero.setMargin(new Insets(0, 0, 0, 0));
        zero.addActionListener((ActionEvent e) -> {
            fanSwitchButtonActivity(fang, ofang, 6, false, buttons, 0);
        });
        /*
         * add to JPanel which FanSwitchG extends
         */
        add(zero);
        add(one);
        add(two);
        add(three);
        add(four);
        add(five);
        add(six);
        setBackground(Color.white);
        //place buttons in hashmap
        buttons = new HashMap<>();
        buttons.put(0, zero);
        buttons.put(1, one);
        buttons.put(2, two);
        buttons.put(3, three);
        buttons.put(4, four);
        buttons.put(5, five);
        buttons.put(6, six);
        //store default background color
        defaultBgColor = zero.getBackground();

    }

    public void maintainFanSwitchButtonStatus(HashMap<Integer, JButton> buttons, Integer toHighlight) {
        for (Integer i = 0; i < 7; i++) {
            if (Objects.equals(i, toHighlight)) {
                buttons.get(i).setBackground(Color.magenta);
            } else {
                buttons.get(i).setBackground(defaultBgColor);
            }
        }
    }

    /*
     * captures what happens when a button is pressed
     * when pressed, highlights, sets speed, and checks if fan is still 
     * running, if not checks if OperateFanG thread should be created, restarted,
     * if fan is to be put off then set isRotating false.
     */
    public void fanSwitchButtonActivity(FanG fg, OperateFanG ofg, Integer fs, boolean rs, HashMap<Integer, JButton> buttons, Integer toHighlight) {
        //fang.isRotating = rs;
        fang.speed = fs;
        maintainFanSwitchButtonStatus(buttons, toHighlight);
        System.out.println(fang.name + "[speed=" + fang.speed + "]");
        if (fang.isRotating == false) {
            if (ofang == null) {
                ofang = new OperateFanG(fang);
                ofang.start();
                fang.isRotating = rs;
            } else {
                if (ofang.isAlive() == false) {
                    ofang = new OperateFanG(fang);
                    ofang.start();
                    fang.isRotating = rs;
                }
            }
        } else {
            if (toHighlight == 0) {
                fang.isRotating = rs;
            }
        }
    }
}
