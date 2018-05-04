/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package musica;

/**
 *
 * @author Uriel
 */
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.InputStreamReader;

import java.io.IOException;

import java.net.Socket;

public class Conexion extends JFrame {
    private JTextField tfDatos;
    private JButton bEnviar;
    private JTextArea taDatos;
    private JPanel panel1;
    private JPanel panel2;

    private Socket socket;

    private BufferedReader bufferEntrada;
    private PrintWriter bufferSalida;

    // Constructor
    public Conexion() {
        super("Conexion");

        // 1. Crear los objetos de los atributos
        tfDatos = new JTextField();
        taDatos = new JTextArea();
        bEnviar = new JButton("Enviar datos");
        panel1 = new JPanel();
        panel2 = new JPanel();

        // 2. Definir el Layout de los panels
        panel1.setLayout(new GridLayout(1, 2));
        panel2.setLayout(new BorderLayout());

        // 4. Adicionar los objetos a los panels
        panel1.add(tfDatos);
        panel1.add(bEnviar);

        panel2.add(panel1, BorderLayout.NORTH);
        panel2.add(new JScrollPane(taDatos), BorderLayout.CENTER);

        // 5. Adicionar panel2 al JFrame y hacerlo visible
        add(panel2);
        setSize(400, 400);
        // setVisible(true);
    }

    public void enviarDatos(String datos) {
        bufferSalida.println(datos);
        bufferSalida.flush();
    }

    public String recibirDatos() {
        String datos = "";

        try {
            datos = bufferEntrada.readLine();
            bufferSalida.flush();
        } catch (IOException ioe) {
            System.out.println("Error: " + ioe);
        }

        return datos;
    }

    public void establecerConexion(){
        String mensaje = "";

        try {
                // 1. Establecer conexion con el server en el puerto 5005
                socket = new Socket("localhost",5005);
                //taDatos.append("\nSe hizo la Conexion con el Server...\n");

                // 2. Preparar canales o buffers de comunicacion
                bufferEntrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                bufferSalida = new PrintWriter(socket.getOutputStream());
                bufferSalida.flush();
                //taDatos.append("Mensaje del Server: " + mensaje);
        } catch (IOException ioe) {
            System.out.println("Error: " + ioe);
        }
    }

    public void cerrarConexion() {
        try {
            bufferEntrada.close();
            bufferSalida.close();
            socket.close();
        } catch (IOException ioe) {
            System.out.println("Error: " + ioe);
        }
    }

}
