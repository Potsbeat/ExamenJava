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
    

    public boolean hasFile(String name){
        File file = new File(root_path + "/hilo" + id_hilo + "/" + name);
        if (file.exists()) {
            return true;
        } else {
            return false;
        }
    }
    
    public int getIdHilo(){
        return id_hilo;
    }
    
    public Hilo getNext() {

        return Main.hilos.get((id_hilo + 1) % Main.hilos.size());

    }
    
    public int getNextId(){
        return Main.hilos.get((id_hilo + 1) % Main.hilos.size()).getIdHilo();
    }
    
    public int getPrevId(){
        if(id_hilo==0){
            return Main.hilos.get(Main.hilos.size()-1).getIdHilo();
        }else{
            return Main.hilos.get((id_hilo - 1)).getIdHilo();
        }
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
    
    public void joinLog(String txt){
        log_field.append(txt);
    }

    public void setLog(String txt){
        log_field.setText(txt);
    }
    
    
    public void buscar(String name) {

        addLog("Buscando " + name + " en la carpeta del hilo " + id_hilo + "...");  
        
        if(hasFile(name)){
            addLog("¡Se ha encontrado el archivo "+name+" en este hilo!");       
        }else{
            int j=0;
            int k;
            String msg = "";
            boolean found = false;
            
            addLog("No se ha encontrado. Preguntando al hilo siguiente ("+getNextId()+")..."); 
            for(int i=getIdHilo()+1;i<Main.hilos.size()+getIdHilo();i++){
                j = i%(Main.hilos.size());
                
                Main.hilos.get(j).addLog("El hilo "+Main.hilos.get(j).getPrevId()+
                                            " pregunta por el archivo "+
                                            name+".\nBuscando en esta carpeta ("+
                                            j+")..."); 
                
                if(Main.hilos.get(j).hasFile(name)){
                    found = true;
                    Main.hilos.get(j).addLog("Se ha encontrado el archivo en esta carpeta ("+Main.hilos.get(j).getIdHilo()+")."); 
                    break;
                }else{
                    Main.hilos.get(j).joinLog("No se ha encontrado.");
                    found = false;
                    if(j==getPrevId()){
                        Main.hilos.get(j).addLog(" Ningún hilo tiene el archivo.");
                        
                    }else{
                        Main.hilos.get(j).addLog(" Preguntando al hilo siguiente ("+Main.hilos.get(j).getNextId()+")...");
                    }
                }
                
            }
            
            if(found){
                msg = "El hilo "+j+" tiene el archivo.";
               
            }else{
                msg = "Ningún hilo tiene el archivo :(";
            }
            
            k = j;
            
            while(k!=getIdHilo()){
                k = Main.hilos.get(k).getPrevId();
                Main.hilos.get(k).addLog("Respuesta del hilo "+Main.hilos.get(k).getNextId()+
                                         ": "+msg); 
            }
        }
        addLog("Ha finalizado la búsqueda.");

    }

    public Hilo(int id) {
        id_hilo = id;
        folder = new File(root_path + "/hilo" + id_hilo);
        
        
        top_panel = new JPanel();
        ventana = new JFrame();
        
        top_panel.setLayout(new BorderLayout(5,5));
        ventana.setSize(350, 350);
        ventana.setTitle("Hilo " + id_hilo );
        ventana.setLayout(new BorderLayout(10,10));
        
        //lab.setBounds(10, 2, 200, 20);
        //arch_field.setBounds(5, 23, 150, 20);
        //buscar_btn.setBounds(156, 23, 75, 20);
        //log_field.setBounds(7, 54, 270, 150);
        log_field.setEditable(false);
        log_field.setLineWrap(true);
       
        buscar_btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                buscar(arch_field.getText());
                
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
