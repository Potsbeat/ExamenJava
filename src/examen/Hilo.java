package examen;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author Potsbeat
 */
public class Hilo extends Thread {

    JFrame ventana;
    private int id_hilo;
    private File folder;
    private JLabel lab = new JLabel("Nombre del archivo con extensión:");
    private JTextField arch_field = new JTextField();
    private JButton buscar_btn = new JButton("buscar");
    private JTextArea log_field = new JTextArea();
    private String root_path = "hilos_dir";
    private int sig_tiene = 2; //2 = no se sabe  1 = lo tiene  0 = no lo tiene

    public int askNext(String name) {

        sig_tiene = Main.hilos.get((id_hilo + 1) % Main.hilos.size()).buscar(name, id_hilo);

        if (sig_tiene == 0) {
            return 0;
        } else{
            log_field.append("El siguiente hilo tiene el archivo " + name + ".\n");
            return 1;
        }

    }

    public int buscar(String name) {
        File file = new File(root_path + "/hilo" + id_hilo + "/" + name);

        if (sig_tiene == 0) {
            log_field.append("No se ha encontrado el archivo en niguna carpeta\n");
            return 2;
        } else {

            log_field.append("Buscando " + name + " en la carpeta del hilo " + id_hilo + "...\n");

            if (file.exists()) {
                log_field.append("Se ha encontrado el archivo en esta carpeta.\n");
                return 1;
            } else {
                log_field.append("No se ha encontrado el archivo en esta carpeta.\n");
                log_field.append("Preguntando al siguiente hilo...\n");
                sig_tiene = askNext(name);
                return 0;
            }
        }
    }

    public int buscar(String name, int id) {
        if (sig_tiene == 0) {
            log_field.append("No se ha encontrado el archivo en niguna carpeta\n");
            return 2;
        } else {
            log_field.append("El hilo " + id + " pregunta por el archivo " + name + ".\n");
            return buscar(name);
        }
    }

    public Hilo(int id) {
        id_hilo = id;
        folder = new File(root_path + "/hilo" + id_hilo);

        ventana = new JFrame();
        ventana.setSize(300, 250);
        ventana.setTitle("Hilo " + id_hilo);
        ventana.setLayout(null);

        lab.setBounds(10, 2, 200, 20);
        arch_field.setBounds(5, 23, 150, 20);
        buscar_btn.setBounds(156, 23, 75, 20);
        log_field.setBounds(7, 54, 270, 150);
        log_field.setEditable(false);
        log_field.setLineWrap(true);

        buscar_btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                buscar(arch_field.getText());
            }
        });

        ventana.add(lab);
        ventana.add(arch_field);
        ventana.add(buscar_btn);
        ventana.add(log_field);
        ventana.setLayout(null);
    }

    @Override
    public void run() {
        folder.mkdirs();
        ventana.setVisible(true);

    }

}
