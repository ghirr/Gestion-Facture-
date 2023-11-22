/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.sql.Date;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author DELL
 */
public class Facture {

    public void setNumFacture(String numFacture) {
        this.numFacture.set(numFacture); 
    }

    public void setRefCli(String refCli) {
        this.refCli.set(refCli);
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public void setDateFacture(Date dateFacture) {
        this.dateFacture = dateFacture;
    }
     private StringProperty numFacture,refCli ;
     private float total;
     private Date dateFacture;

    public Facture(String numFacture, Date dateFacture, float total,String refCli) {
        this.numFacture = new SimpleStringProperty(numFacture);
        this.refCli = new SimpleStringProperty(refCli);
        this.total = total;
        this.dateFacture = dateFacture;
    }

    public String getNumFacture() {
        return numFacture.get();
    }

    public String getRefCli() {
        return refCli.get();
    }

    public float getTotal() {
        return total;
    }

    public Date getDateFacture() {
        return dateFacture;
    }
    
     
     
    
}
