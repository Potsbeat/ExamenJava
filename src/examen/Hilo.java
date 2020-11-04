package examen;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author Potsbeat
 */
public class Hilo extends Thread {
    JPanel top_panel;
    JFrame ventana;
    private int id_hilo;
    private File folder;
    private JLabel lab = new JLabel("Nombre del archivo con extensión:");
    private JTextField arch_field = new JTextField(18);
    private JButton buscar_btn = new JButton("buscar");
    private JTextArea log_field = new JTextArea(5,10);
    private String root_path = "hilos_dir";
    

    public Hilo getNext() {

        return Main.hilos.get((id_hilo + 1) % Main.hilos.size());

    }
    
    public Hilo getPrev() {
        if(id_hilo==0){
            return Main.hilos.get(Main.hilos.size()-1);
        }else{
            return Main.hilos.get((id_hilo - 1));
        }
    }
    
    public void addLog(String txt){
        log_field.append(txt+"\n");
    }

    public void buscar(String name) {
        File file = new File(root_path + "/hilo" + id_hilo + "/" + name);

        log_field.append("Buscando " + name + " en la carpeta del hilo " + id_hilo + "...\n");

        if (file.exists()) {

        } else {

        }

    }

    public void buscar(String name, int id) {
      
            log_field.append("No se ha encontrado el archivo en niguna carpeta\n");
            
        
            log_field.append("El hilo " + id + " pregunta por el archivo " + name + ".\n");
            
        
    }

    public Hilo(int id) {
        id_hilo = id;
        folder = new File(root_path + "/hilo" + id_hilo);
        
        
        top_panel = new JPanel();
        ventana = new JFrame();
        
        top_panel.setLayout(new BorderLayout(5,5));
        ventana.setSize(320, 350);
        ventana.setTitle("Hilo " + id_hilo);
        ventana.setLayout(new BorderLayout(10,10));
        
        //lab.setBounds(10, 2, 200, 20);
        //arch_field.setBounds(5, 23, 150, 20);
        //buscar_btn.setBounds(156, 23, 75, 20);
        //log_field.setBounds(7, 54, 270, 150);
        log_field.setEditable(false);
        log_field.setLineWrap(true);
       
        buscar_btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //buscar(arch_field.getText());
                
            }
        });
        
        
        top_panel.add(lab, BorderLayout.NORTH);
        top_panel.add(arch_field, BorderLayout.WEST);
        top_panel.add(buscar_btn, BorderLayout.CENTER);
        ventana.add(top_panel, BorderLayout.NORTH);
        ventana.add(log_field, BorderLayout.CENTER);
       
    }

    @Override
    public void run() {
        folder.mkdirs();
        ventana.setVisible(true);
    }

}
