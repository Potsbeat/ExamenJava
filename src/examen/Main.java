package examen;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;

/**
 * @author Potsbeat
 */
public class Main {

    public static int global_total;
    public static ArrayList<Hilo> hilos = new ArrayList<>();
    public static void main(String[] args) {
        
        Integer[] nums = new Integer[8];
        for(int i=0;i<nums.length;i++){
            nums[i] = i+3;
        }
        
        JFrame f = new JFrame();//creating instance of JFrame  
        JComboBox cb = new JComboBox(nums);
        JButton b = new JButton("Crear");
        JButton b_clean = new JButton("Limpiar");
        JLabel lab = new JLabel("Hilos");
        
       
        //b.setBounds(130, 100, 100, 40);//x axis, y axis, width, height  
        b.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                global_total = (int) cb.getSelectedItem();
                for(int i=0;i<(int) cb.getSelectedItem();i++){
                    hilos.add(new Hilo(i));
                }
                
                for(int i=0;i<hilos.size();i++){
                    hilos.get(i).start();
                }
                
                b.setEnabled(false);
                cb.setEnabled(false);
                b_clean.setEnabled(true);
                
            }
        });
        
        b_clean.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                for(Hilo h : hilos){
                    h.setLog("");
                }
                
            }
        });
        
        f.add(lab);
        f.add(cb);
        f.add(b);
        f.add(b_clean);
        
        b_clean.setEnabled(false);

        f.setSize(250, 250);//400 width and 500 height  
        f.setLayout(new FlowLayout(FlowLayout.LEFT));
        f.setVisible(true);//making the frame visible  
        f.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

}
