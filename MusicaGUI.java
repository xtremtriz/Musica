/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package musica;

/**
 *
 * @author Ariana
 */
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import java.util.Date;
import java.util.StringTokenizer;

import java.awt.GridLayout;
import java.awt.FlowLayout;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MusicaGUI extends JFrame implements ActionListener{
private JTextField tfSearchBar;
    private JButton    bPlaySong, bStopSong, bSearchSong, bSongCatalog, bSalir;
    private JPanel     panel1, panel2;
    private JTextArea  taDatos;
    private StringTokenizer st;
    
    private Conexion conexion = new Conexion();
    
    public MusicaGUI(){
        super("Music");
        // 1. Crear los objetos de los atributos
        panel1 = new JPanel();
        panel2 = new JPanel();
        taDatos = new JTextArea(10,30);

        tfSearchBar = new JTextField();
        bSearchSong = new JButton("Buscar cancion");
        bSongCatalog = new JButton("Ver catalogo");

        bSalir = new JButton("Exit");
        
        // Adicionar addActionListener a lo JButtons
        
        bSearchSong.addActionListener(this);
        bSongCatalog.addActionListener(this);
        bSalir.addActionListener(this);

        // 2. Definir los Layouts de los JPanels
        panel1.setLayout(new GridLayout(4,2));
        panel2.setLayout(new FlowLayout());
        
        // 3. Colocar los objetos de los atributos en los JPanels correspondientes
        panel1.add(new JLabel("Cancion a reproducir: "));
        panel1.add(tfSearchBar);
        panel1.add(bSearchSong);
        panel1.add(bSongCatalog);

        panel1.add(bSalir);
        
        panel2.add(panel1);
        panel2.add(new JScrollPane(taDatos));
        
        // 4. Adicionar el panel2 al JFrame y hacerlo visible
        add(panel2);
        setSize(600,600);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void tokenizar(String datos){
        String token = "";
        st = new StringTokenizer(datos,"*");
        while(st.hasMoreTokens()){
            token = token + st.nextToken() + '\n';
            System.out.println(token);
            taDatos.setText(token);
        }
    }
    
    public void actionPerformed(ActionEvent e){
        String datos="", respuesta="";
        
        if(e.getSource() == bSearchSong){
            // 1. Obtener datos
            datos = tfSearchBar.getText();
            System.out.println(datos);
            // 2. Checar datos: datos no vacios y saldo numerico, y realizar la captura de datos
            if(datos.equals("VACIO"))
                respuesta = "Algun campo esta vacio...";
            else{
                // 2.1 Establecer conexion con el server
                conexion.establecerConexion();
                    
                // 2.2 Enviar la transaccion a realizar
                conexion.enviarDatos("consultarCancion");
                    
                // 2.3 Enviar los datos a capturar en la DB
                conexion.enviarDatos(datos);
    
                // 2.4 Recibir resultado de la transaccion
                respuesta = conexion.recibirDatos();

                // 2.5 Cerrar la conexion
                conexion.cerrarConexion();
            }
            
            // 3. Desplegar resultado de la transaccion
            taDatos.setText(respuesta);
        }
        
        if(e.getSource() == bSongCatalog){
            // 1. Establecer conexion con el Server
            conexion.establecerConexion();

            // 2. Enviar transaccion a realizar
            conexion.enviarDatos("consultarCatalogo");

            // 3. Recibir resultado de la transaccion
            datos = conexion.recibirDatos();

            // 4. Cerrrar conexion
            conexion.cerrarConexion();

            // 5. Desplegar datos
            // taDatos.setText(datos);
            tokenizar(datos);
        }
        
        if(e.getSource() == bSalir){
            System.exit(0);
        }
    }
    
    public static void main(String args[]){
        new MusicaGUI();
    }
}