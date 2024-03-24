package clsw;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.JDialog;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.FlowLayout;
public class ComputerLab {
    static int numberOfFans;
    public static void main(String[] args) {
        //pop-up window for collecting number of blades from user
        JDialog numberOfFansCollector = new JDialog();
        numberOfFansCollector.setLayout(new FlowLayout());
        numberOfFansCollector.setLocationRelativeTo(null);

        //360 possible fan blades
        JComboBox numberOfFansOptions = new JComboBox();
        for(int i=1;i<=360;i++){
            numberOfFansOptions.addItem(i);
        }
        numberOfFansCollector.add(numberOfFansOptions);
        
        //define ok button with action listener
        JButton numberOfFansOk = new JButton("ok");
        numberOfFansOk.addActionListener((ActionEvent e) -> {
            numberOfFans = numberOfFansOptions.getSelectedIndex();
            numberOfFansCollector.dispose();
        });

        numberOfFansCollector.add(numberOfFansOk);
        numberOfFansCollector.pack();
        numberOfFansCollector.setModal(true);
        numberOfFansCollector.setVisible(true);
        
        //setup fans
        ArrayList<FanG> fans = new ArrayList<>();
        FanG fan;

            for ( int i=0; i < 12; i++){
                fan = new FanG(numberOfFans);
                fans.add(fan);
            }
  
    }
}
