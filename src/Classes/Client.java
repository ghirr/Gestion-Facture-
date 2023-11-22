/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class Client  {
    private StringProperty codeCli,nomCli,adresseCli,emailCli ;

    public Client(String codeCli, String nomCli, String adresseCli, String emailCli) {
        this.codeCli = new SimpleStringProperty(codeCli);
        this.nomCli = new SimpleStringProperty(nomCli);
        this.adresseCli =new SimpleStringProperty(adresseCli);
        this.emailCli =new SimpleStringProperty(emailCli);
    }

    public String getCodeCli() {
        return codeCli.get();
    }

        public void setCodeCli(String codeCli) {
        this.codeCli.set(codeCli);
    }

    public void setNomCli(String nomCli) {
        this.nomCli.set(nomCli);
    }

    public void setAdresseCli(String adresseCli) {
        this.adresseCli.set(adresseCli);
    }

    public void setEmailCli(String emailCli) {
        this.emailCli.set(emailCli);
    }

    public String getNomCli() {
        return nomCli.get();
    }

    public String getAdresseCli() {
        return adresseCli.get();
    }

    public String getEmailCli() {
        return emailCli.get();
    }
    
    

    @Override
    public String toString() {
        return "client{" + "codeCli=" + codeCli + ", nomCli=" + nomCli + ", adresseCli=" + adresseCli + ", emailCli=" + emailCli + '}';
    }
    

    /*@Override
    public int compareTo(Client o) {
    int res =0;
   if (this.codeCli.equals(o)){
       res=1;
   } else {
       res=-1;
   }
    
    
 return res;   
}*/
}

