/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Classes.Client;
import DAO.DAOFct;

/**
 *
 * @author islem
 */
public class jassa {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       // Client c =new Client("3","salim","fa7es","salim@gmail.com");
       // DAOClt.Ajouter("11","taha","Bardo","taha@gmail.com");
      /*boolean hama=DAOClt.modifier(c);
       System.out.println("result est "+ hama);*/
        System.out.println(DAOFct.getRef());
    }
    
}
