/**
***@author:Foltran Federico Marcone Lorenzo
***@version:6.0
*/

package scritturafile;

import java.io.*;
import java.util.ArrayList;

class Dato {

  private int anno;
  private double temp;

/**
*** @param anno indica l'anno
 *  @param temp indica la temperatura
 */
  public Dato(int anno,double temp)
  {
    this.anno=anno;
    this.temp=temp;
  }

    //Costruttore di copia
  /**
   *** @param d oggetto da cui copiare di tipo Dato
   */
  public Dato(Dato d){
    anno=d.anno;
    temp=d.temp;

  }

  /**
   *** @return temp ritorna il valore della temperatura
   */

  public double getTemperatura(){

    return temp;
  }

  /**
   *** @return anno ritorna il valore dell' anno
   */
  public int getAnno(){

    return anno;
  }
  /**
   *** @return pippo ritorna l'oggetto in formato testuale
   */
  public String toString()
  {
    String pippo = "";

    pippo+="Anno: "+anno+"\n";
    pippo+="Temperatura: "+temp+"\n";

    return pippo;
  }

  /**
   * stampa l'oggetto con il toString();
   */
  public void Stampa(){
      
      System.out.println(toString());
  }
}
