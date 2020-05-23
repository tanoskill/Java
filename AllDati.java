/**
 ***@author:Foltran Federico Marcone Lorenzo
 ***@version:6.0
 */

package scritturafile;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.awt.Canvas;
import java.awt.Graphics;
import javax.swing.JFrame;

public class AllDati extends Canvas {

    private static int LungArr = 28; //indica la lunghezza dell'ArrayList, il numero è da modificare in base al numero di temperature in input
    private static ArrayList d = new ArrayList(LungArr);
    private static int stampa = 0; //viene utilizzata per stampare gli oggeti in cui vengono salvate le temperature e le date
    static void leggiCSV(String File) {

        String line = "";
        String cvsSplitBy = ";";

        // Creo il file reader
        try (BufferedReader br = new BufferedReader(new FileReader(File))) {

            // Leggo linea per linea
            while ((line = br.readLine()) != null) {

                // Spezzo le linee del file nelle virgole
                String[] dati = line.split(cvsSplitBy);

                //Salvataggio dei dati letti su anno e su temperatura
                int anno = Integer.parseInt(dati[0].trim());
                double temperatura = Double.parseDouble(dati[1].trim());


                //Creo l'oggetto
                d.add(new Dato(anno, temperatura));
                System.out.println(d.get(stampa));

                stampa++;
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

        JFrame Grafico = new JFrame("Grafico");
        JPanel p = new JPanel();
        Canvas c = new AllDati();

        Grafico.add(p);
        p.add(c);
        Grafico.pack();

        p.setLayout(new BorderLayout());

        Grafico.getContentPane().add(p);

        Grafico.setLocation(10, 10);
        Grafico.setSize(1800, 1100);
        c.setSize(1700, 1000);



        Grafico.setVisible(true);
        Grafico.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Alla chiusura del frame si chiude tutto




    }

    public void paint(Graphics g) {
        double temp2 = 0; //variabile su cui viene salvata ogni temperatura presente sull'oggetto

        int anni = 1990; //variabile su cui viene salvato ogni anno per poi stamparlo a schermo

        int posAnni = 72; //variabile che indica la posizione delle x per stampare gli anni

        int contatore = (LungArr * 40) + 40; //variabile che indica l'ultima posizione dell'asse degli anni

        int contatore2 = 0; //contatore che serve per salvare ogni posizione dell'ArrayList su un oggetto

        int altezza = 5; //valore in pixel per ogni 0.1 gradi

        int posTemp = 80; //variabile che indica la posizione iniziale delle x per la stampa del grafico delle temperature

        int temperatura = 0; //variabile che viene tilizzata per stampare le temperature sull'asse delle y

        double posizione = 0; //variabile che indica la posizione sull'asse delle y per ogni temperatura

        int posizione1 = 0; //come posizione


        g.setColor(Color.GRAY);

        /*
         ***ciclo for che serve per stampare l'asse degli anni
         */
        for (int x = 80; x <= contatore; x = x + 40) {
            Dato d1 = new Dato((Dato) d.get(contatore2)); //copia di ogni posizione dell'array list su un oggetto
            anni = d1.getAnno(); //salva sulla variabile anni l'anno presente nell'oggetto d1
            g.setColor(Color.BLACK);
            g.drawString(String.valueOf(anni), posAnni, 930); //stampa l'anno sul grafico
            g.setColor(Color.GRAY);
            posAnni = posAnni + 40; //incremento della posizione in cui viene scritto l'anno
            contatore2++; //incremento della variabile utilizzata per salvare ogni posizione dell'ArrayList su un oggetto
        }
        g.setColor(Color.RED);
        g.drawString("Anni", posAnni + 40, 930); //indica cosa si trova nell'asse delle ordinate
        g.setColor(Color.GRAY);

        /*
         ***stampa per ogni anno il grafico della corrispettiva temperatura
         */
        for (int y = 0; y <= 27; y++) {
            Dato d1 = new Dato((Dato) d.get(y)); //copia di ogni posizione dell'array list su un oggetto
            temp2 = d1.getTemperatura(); //salva sulla variabile temp2 ll temperatura presente nell'oggetto d1
            posizione = (900 * temp2) / 18; //indica la posizione sull'asse delle y per ogni temperatura
            posizione1 = (int)(900 - posizione); //converte il double in un int (casting)
            /*
             *** ciclo che serve per disegnare l'asse della temperatura corrispondente ad ogni anno
             */
            g.setColor(Color.BLACK);
            g.drawString(String.valueOf(temp2), posTemp - 9, posizione1 - 5); //stampa la temperatura per ogni anno
            g.setColor(Color.RED);
            g.fillRect(posTemp, posizione1, 10, altezza); //quadratino massimo della temperatura
            for (int p = posizione1 + 1; p <= 900; p++) {
                g.setColor(Color.GRAY);
                g.fillRect(posTemp, p, 10, altezza);
            }
            posTemp += 40; //posizione sull'asse delle x del grafico della temperatura per ogni anno
        }


        /*
         ***Stampa l'asse delle temperature
         */
        for (int v = 900; v >= 0; v = v - 50) {
            g.setColor(Color.BLACK);
            g.drawString(String.valueOf(temperatura), 40, v); //scrive le temperature sull'asse
            g.setColor(Color.GRAY);
            temperatura++; //incremento del valore della temperatura che appare a schermo
            /*
             ***ciclo che serve per stampare la linea tratteggiata
             */
            for (int z = 60; z <= LungArr * 42.5; z += 10) {
                g.setColor(Color.RED);
                g.drawLine(z, v, z + 5, v);
            }
        }
        g.setColor(Color.BLACK);
        g.drawString(String.valueOf(18), 40, 10);
        g.setColor(Color.red);
        g.drawString("Temp", 4, 10); //indica cosa si trova nell'asse delle ascisse
    }


}