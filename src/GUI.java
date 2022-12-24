import org.jfree.ui.RefineryUtilities;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class GUI extends JFrame{
    private JButton Load = new JButton("Load From File"),exit=new JButton("Quit");
    class LoadFromFile implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JFileChooser c = new JFileChooser();
            int rVal = c.showOpenDialog(GUI.this);
            if (rVal == JFileChooser.APPROVE_OPTION) {
                if(c.getCurrentDirectory().toString()!=null) general.FileName=c.getCurrentDirectory().toString();
                general.FileName+="\\"+c.getSelectedFile().getName();
            }
            Main.run();
            for (Variable var:general.fuzzySystem.variables){
                XYLineChart_AWT chart = new XYLineChart_AWT(var.getName(),var.FuzzySets);
                chart.pack();
                RefineryUtilities.centerFrameOnScreen( chart );
                chart.setVisible( true );
            }

        }
    }
    class Exit implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    public GUI(){
        Load.setForeground(Color.BLUE);
        Load.setBackground(Color.CYAN);

        exit.setForeground(Color.BLACK);
        exit.setBackground(Color.pink);

        Load.setBounds(150, 120, 260, 70);
        exit.setBounds(150, 200, 260, 70);
        Load.addActionListener(new LoadFromFile());
        exit.addActionListener(new Exit());

        JLabel l1=new JLabel("Welcome to my Fuzzy System :)");
        l1.setBounds(130,10, 400,50);
        l1.setForeground(Color.BLUE);
        l1.setFont(new Font("Verdana", Font.PLAIN, 18));
        add(l1,BorderLayout.NORTH);
        add(Load);
        add(exit);
        //SwingConstants.CENTER
        setLocationRelativeTo(null);
    }



    public static void main(String[] args) {
        run(new GUI(),600,600);


    }

    public static void run(JFrame frame, int width, int height) {
        frame.setLayout(null);
        frame.setLocationByPlatform(true);
        frame.setName("SoftComputing Assignment 2");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width, height);
        frame.setVisible(true);
    }

}