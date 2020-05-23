/**
 ***@author: Foltran Federico Marcone Lorenzo
 ***@version:9.0
 */

package scritturafile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.awt.Canvas;
import java.awt.Graphics;
import javax.swing.JFrame;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.awt.Color;

import javax.imageio.ImageIO;

public class AllDati extends Canvas implements ActionListener {

    private static int LungArr; //indica quante coppie di dati vengono inserite nell'arrayList
    private static ArrayList d = new ArrayList();
    private static int stampa = 0; //viene utilizzata per stampare gli oggeti in cui vengono salvate le temperature e le date
    public static int prova = 0; //utilizzata per saltare la prima colonna del file csv
    public static boolean disegno = false; //utilizzata per disegnare i differenti grafici

    static void leggiCSV(String File) {

        String line = "";
        String cvsSplitBy = ",";


        // Creo il file reader
        try (BufferedReader br = new BufferedReader(new FileReader(File))) {

            // Leggo linea per linea
            while ((line = br.readLine()) != null) {

                // Splitto le linee grazie al ,
                String[] dati = line.split(cvsSplitBy);
                if (prova == 0) {
                    prova++;

                } else {
                    dati[1] = dati[1] + "." + dati[2]; //concateno la stringa 1 con la stringa 2
                    int lunghezza = dati[1].length();
                    String eliminazione = dati[1].substring(1, lunghezza - 1); //elimino gli " in eccesso

                    //Salvataggio dei dati letti su anno e su temperatura
                    int anno = Integer.parseInt(dati[0]);
                    double temperatura = Double.parseDouble(eliminazione);


                    //Creo l'oggetto e l'inserisco nell'arrayList
                    d.add(new Dato(anno, temperatura));
                    System.out.println(d.get(stampa));

                    LungArr++;
                    stampa++;


                }
            }
        } catch (IOException e) {
            System.out.println("Errore");
        }
    }


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        InputStreamReader In = new InputStreamReader(System.in);
        BufferedReader Tastiera = new BufferedReader(In);

        String File = "temp_media_annua.csv"; //nome del file da leggere


        leggiCSV(File); //richiama il metodo statico leggiCSV passandogli il nome del file



        ///////Schermata principale///////
        JFrame f = new JFrame("Home");
        JPanel totale = new JPanel();
        JPanel titolo = new JPanel();
        JPanel bottoni = new JPanel();
        JPanel area = new JPanel();
        JTextArea area1 = new JTextArea(12, 44);

        area1.setEditable(false);

        JButton bottone1 = new JButton("Istogramma");
        JButton bottone2 = new JButton("Diagramma Cartesiano");
        JButton bottone3 = new JButton("Visualizza Dati");

        bottoni.add(bottone1);
        bottoni.add(bottone2);
        bottoni.add(bottone3);

        area.add(area1);

        JLabel label = new JLabel("GRAFICO TEMPERATURE MEDIE 1990-2017");

        JScrollPane sp = new JScrollPane(area1, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        area.add(sp);

        totale.add(titolo, "North");
        totale.add(bottoni, "Center");
        totale.add(area, "South");

        titolo.add(label);
        Font fontTitolo = new Font("Monospaced", Font.BOLD, 19); //modifico il font del titolo
        label.setFont(fontTitolo);
        label.setForeground(Color.RED); //cambia colore al testo sulla label
        Font fontStampa = new Font("Helvetica", Font.BOLD, 13); //modifico il font alla stampa delle temperature
        bottone1.setFont(fontStampa);
        bottone2.setFont(fontStampa);
        bottone3.setFont(fontStampa);


        f.getContentPane().add(totale);
        f.setSize(500, 310);

        f.setLocation(100, 100);

        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Alla chiusura del frame si chiude tutto

        ///////Visualizzazione dati///////

        bottone3.setActionCommand("Visualizza Dati");


        bottone3.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if ("Visualizza Dati".equals(e.getActionCommand())) {
                    area1.append(" \t                  Anni                              Temperatura \n");
                    for (int contatore = 0; contatore < LungArr; contatore++) {
                        Dato d1 = new Dato((Dato) d.get(contatore)); //copia di ogni posizione dell'array list su un oggetto
                        int anni = d1.getAnno(); //salva sulla variabile anni l'anno presente nell'oggetto d1
                        String stampa = String.valueOf(anni);
                        area1.append("\t                  " + stampa + "\t");
                        Dato d2 = new Dato((Dato) d.get(contatore)); //copia di ogni posizione dell'array list su un oggetto
                        double temperatura = d2.getTemperatura(); //salva sulla variabile temperatura la temperatura presente nell'oggetto d2
                        stampa = String.valueOf(temperatura);
                        area1.append("                                " + stampa + " °C\n");
                    }


                }
            }
        });

        ///////Visualizzazione istogramma///////

        bottone1.setActionCommand("Visualizza Grafico1");


        bottone1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                disegno = true;
                if ("Visualizza Grafico1".equals(e.getActionCommand())) {

                    JFrame Grafico = new JFrame("Istogramma");
                    JPanel p = new JPanel();
                    Canvas c = new AllDati();
                    JButton salva = new JButton("Save as PNG");

                    Grafico.add(p);
                    p.add(salva);
                    p.add(c);
                    Grafico.pack();

                    p.setLayout(new BorderLayout());

                    Grafico.getContentPane().add(p);

                    Grafico.setLocation(600, 10);
                    Grafico.setSize(1200, 600);
                    c.setSize(1200, 550);

                    Grafico.setBackground(Color.WHITE);
                    p.setBackground(Color.WHITE);
                    c.setBackground(Color.WHITE);

                    Grafico.setVisible(true);

                    salva.setActionCommand("Salva");

                    salva.addActionListener(new ActionListener() {

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if ("Salva".equals(e.getActionCommand())) {
                                BufferedImage image = new BufferedImage(c.getWidth(), c.getHeight(), BufferedImage.TYPE_4BYTE_ABGR_PRE);
                                RenderedImage rendImage = image;

                                Graphics g = image.getGraphics();
                                c.paint(g);
                                File file = new File("Istogramma.png");
                                try {
                                    ImageIO.write(rendImage, "png", file);

                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }

                            }

                        }
                    });


                }

            }
        });
        ///////Visualizzazione diagramma cartesiano///////
        bottone2.setActionCommand("Visualizza Grafico2");


        bottone2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                disegno = false;
                if ("Visualizza Grafico2".equals(e.getActionCommand())) {
                    JFrame Grafico2 = new JFrame("Diagramma Cartesiano");
                    JPanel p2 = new JPanel();
                    Canvas c2 = new AllDati();
                    JButton salva = new JButton("Save as PNG");

                    Grafico2.add(p2);
                    p2.add(salva);
                    p2.add(c2);
                    Grafico2.pack();

                    p2.setLayout(new BorderLayout());

                    Grafico2.getContentPane().add(p2);

                    Grafico2.setLocation(600, 10);
                    Grafico2.setSize(1200, 600);
                    c2.setSize(1200, 550);


                    Grafico2.setBackground(Color.WHITE);
                    p2.setBackground(Color.WHITE);
                    c2.setBackground(Color.WHITE);

                    Grafico2.setVisible(true);

                    salva.setActionCommand("Salva");

                    salva.addActionListener(new ActionListener() {

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if ("Salva".equals(e.getActionCommand())) {
                                BufferedImage image = new BufferedImage(c2.getWidth(), c2.getHeight(), BufferedImage.TYPE_4BYTE_ABGR_PRE);
                                RenderedImage rendImage = image;

                                Graphics g = image.getGraphics();

                                c2.paint(g);
                                File file = new File("DiagrammaCartesiano.png");
                                try {
                                    ImageIO.write(rendImage, "png", file);

                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }

                            }

                        }
                    });

                }

            }
        });
    }

    public void paint(Graphics g) {

        ///////Disegno istogramma///////
        if (disegno == true) {

            double temp2 = 0; //variabile su cui viene salvata ogni temperatura presente sull'oggetto

            int anni = 0; //variabile su cui viene salvato ogni anno per poi stamparlo a schermo     ///

            int posAnni = 72; //variabile che indica la posizione delle x per stampare gli anni

            int contatore = (LungArr * 30) + 70; //variabile che indica l'ultima posizione dell'asse degli anni

            int contatore2 = 0; //contatore che serve per salvare ogni posizione dell'ArrayList su un oggetto

            int altezza = 5; //valore in pixel per ogni 0.1 gradi

            int posTemp = 75; //variabile che indica la posizione iniziale delle x per la stampa del grafico delle temperature

            int temperatura = 0; //variabile che viene utilizzata per stampare le temperature sull'asse delle y

            double posizione = 0; //variabile che indica la posizione sull'asse delle y per ogni temperatura

            int posizione1 = 0; //come posizione

            /*
             ***Ciclo per stampare le temperature e disegnare l'asse del temperature
             */

            for (int v = 470; v >= 0; v = v - 25) {
                g.setColor(Color.BLACK);
                g.drawString(String.valueOf(temperatura), 40, v); //scrive le temperature sull'asse
                g.setColor(Color.GRAY);
                temperatura++; //incremento del valore della temperatura che appare a schermo
                g.setColor(Color.RED);
                g.drawLine(60, v - 4, 65, v - 4);
                /*
                 ***ciclo che serve per stampare la linea tratteggiata
                 */
                for (int z = 60; z <= LungArr * 32.5; z += 10) {
                    g.setColor(Color.RED);
                    g.drawLine(z, v - 4, z + 5, v - 4);
                }
            }
            /*
             ***ciclo for che serve per stampare l'asse degli anni
             */
            for (int x = 100; x <= contatore; x = x + 30) {
                Dato d1 = new Dato((Dato) d.get(contatore2)); //copia di ogni posizione dell'array list su un oggetto
                anni = d1.getAnno(); //salva sulla variabile anni l'anno presente nell'oggetto d1
                int anni1 = anni / 1000;
                int anni2 = (anni / 100) % 10;
                int anni3 = (anni / 10) % 10;
                int anni4 = anni % 10;
                g.setColor(Color.BLACK);
                g.drawString(String.valueOf(anni1), posAnni, 490); //stampa l'anno sul grafico
                g.drawString(String.valueOf(anni2), posAnni, 502); //stampa l'anno sul grafico
                g.drawString(String.valueOf(anni3), posAnni, 514); //stampa l'anno sul grafico
                g.drawString(String.valueOf(anni4), posAnni, 526); //stampa l'anno sul grafico
                g.setColor(Color.RED);
                g.drawLine(x - 25, 470, x - 25, 475);
                g.setColor(Color.GRAY);
                posAnni = posAnni + 30; //incremento della posizione in cui viene scritto l'anno
                contatore2++; //incremento della variabile utilizzata per salvare ogni posizione dell'ArrayList su un oggetto
            }
            g.setColor(Color.RED);
            g.drawString("Anni", posAnni + 20, 490); //indica cosa si trova nell'asse delle ordinate
            g.setColor(Color.GRAY);

            /*
             ***Stampa la temperatura relativa ad ogni anno
             */

            for (int y = 0; y <= (LungArr - 1); y++) {
                Dato d1 = new Dato((Dato) d.get(y)); //copia di ogni posizione dell'array list su un oggetto
                temp2 = d1.getTemperatura(); //salva sulla variabile temp2 ll temperatura presente nell'oggetto d1
                posizione = (450 * temp2) / 18; //indica la posizione sull'asse delle y per ogni temperatura
                posizione1 = (int)(466 - posizione); //converte il double in un int (casting)
                /*
                 *** ciclo che serve per disegnare l'asse della temperatura corrispondente ad ogni anno
                 */
                g.setColor(Color.BLACK);
                g.drawString(String.valueOf(temp2), posTemp - 13, posizione1 - 5); //stampa la temperatura per ogni anno
                g.setColor(Color.RED);
                g.fillRect(posTemp - 4, posizione1, 10, altezza); //quadratino massimo della temperatura
                for (int p = posizione1 + 1; p <= 462; p++) {
                    g.setColor(Color.GRAY);
                    g.fillRect(posTemp - 4, p, 10, altezza);
                }
                posTemp += 30; //posizione sull'asse delle x del grafico della temperatura per ogni anno
            }
            g.setColor(Color.red);
            g.drawString("Temp", 2, 10); //indica cosa si trova nell'asse delle ascisse
            g.drawString("°C", 13, 22);

        } else {
            ///////Diagramma cartesiano///////
            double temp2 = 0; //variabile su cui viene salvata ogni temperatura presente sull'oggetto

            int anni = 0; //variabile su cui viene salvato ogni anno per poi stamparlo a schermo     ///

            int posAnni = 72; //variabile che indica la posizione delle x per stampare gli anni

            int contatore = (LungArr * 30) + 70; //variabile che indica l'ultima posizione dell'asse degli anni

            int contatore2 = 0; //contatore che serve per salvare ogni posizione dell'ArrayList su un oggetto

            int altezza = 5; //valore in pixel per ogni 0.1 gradi

            int posTemp = 75; //variabile che indica la posizione iniziale delle x per la stampa del grafico delle temperature

            int temperatura = 0; //variabile che viene utilizzata per stampare le temperature sull'asse delle y

            double posizione = 0; //variabile che indica la posizione sull'asse delle y per ogni temperatura

            int posizione1 = 0; //come posizione

            int salvataggioX = 0;

            int salvataggioY = 0;

            int controllo = 0;

            /*
             ***Ciclo per stampare le temperature e disegnare l'asse del tempo
             */
            for (int v = 470; v >= 0; v = v - 25) {
                g.setColor(Color.BLACK);
                g.drawString(String.valueOf(temperatura), 40, v); //scrive le temperature sull'asse
                g.setColor(Color.GRAY);
                temperatura++; //incremento del valore della temperatura che appare a schermo
                g.setColor(Color.RED);
                g.drawLine(60, v - 4, 65, v - 4);
            }
            /*
             ***ciclo for che serve per stampare l'asse degli anni
             */
            for (int x = 100; x <= contatore; x = x + 30) {
                Dato d1 = new Dato((Dato) d.get(contatore2)); //copia di ogni posizione dell'array list su un oggetto
                anni = d1.getAnno(); //salva sulla variabile anni l'anno presente nell'oggetto d1
                int anni1 = anni / 1000;
                int anni2 = (anni / 100) % 10;
                int anni3 = (anni / 10) % 10;
                int anni4 = anni % 10;
                g.setColor(Color.BLACK);
                g.drawString(String.valueOf(anni1), posAnni, 490); //stampa l'anno sul grafico
                g.drawString(String.valueOf(anni2), posAnni, 502); //stampa l'anno sul grafico
                g.drawString(String.valueOf(anni3), posAnni, 514); //stampa l'anno sul grafico
                g.drawString(String.valueOf(anni4), posAnni, 526); //stampa l'anno sul grafico
                g.setColor(Color.RED);
                g.drawLine(x - 25, 470, x - 25, 475);
                g.setColor(Color.GRAY);
                posAnni = posAnni + 30; //incremento della posizione in cui viene scritto l'anno
                contatore2++; //incremento della variabile utilizzata per salvare ogni posizione dell'ArrayList su un oggetto
            }
            g.setColor(Color.RED);
            g.drawString("Anni", posAnni + 20, 490); //indica cosa si trova nell'asse delle ordinate
            g.setColor(Color.GRAY);

            /*
             ***Stampa la temperatura relativa ad ogni anno
             */

            for (int y = 0; y <= (LungArr - 1); y++) {
                Dato d1 = new Dato((Dato) d.get(y)); //copia di ogni posizione dell'array list su un oggetto
                temp2 = d1.getTemperatura(); //salva sulla variabile temp2 ll temperatura presente nell'oggetto d1
                posizione = (450 * temp2) / 18; //indica la posizione sull'asse delle y per ogni temperatura
                posizione1 = (int)(466 - posizione); //converte il double in un int (casting)
                /*
                 *** ciclo che serve per disegnare l'asse della temperatura corrispondente ad ogni anno
                 */
                g.setColor(Color.BLUE);
                g.drawLine(posTemp, posizione1, posTemp, posizione1); //linea massima della temperatura
                posTemp += 30; //posizione sull'asse delle x del grafico della temperatura per ogni anno
                if (controllo == 0) {
                    controllo++;
                } else {
                    g.drawLine(posTemp - 30, posizione1, salvataggioX, salvataggioY);

                }

                int z = posizione1;
                do {
                    g.setColor(Color.RED);
                    g.drawLine(posTemp - 30, z, posTemp - 30, z + 3);
                    z = z + 9;
                } while (z <= 470);

                g.setColor(Color.BLUE);
                salvataggioX = posTemp - 30;
                salvataggioY = posizione1;
            }
            g.setColor(Color.red);
            g.drawString("Temp", 2, 10); //indica cosa si trova nell'asse delle ascisse
            g.drawString("°C", 13, 22);
        }

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}