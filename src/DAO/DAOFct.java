/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Classes.Client;
import Classes.Facture;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 *
 * @author islem
 */
public class DAOFct {
    static Connection cn = LaConnexion.seConnecter();
     public static ArrayList<Facture> lister(){
        ArrayList<Facture> cl= new ArrayList<>();
        
        String requete = "SELECT * FROM `facture`;";
        Facture f;
        try{
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(requete);
            while(rs.next()){
                f = new Facture(rs.getString(1),rs.getDate(2), rs.getFloat(3), rs.getString(4));
                cl.add(f);
            }
            System.out.println("consultation ok");
        } catch (SQLException e) {
            System.out.println("probleme de consultation "+e.getMessage());
        }
        return cl;
    }
    public static ArrayList<String> getRef(){
        ArrayList<String> cl= new ArrayList<>();
        
        String requete = "SELECT codeCli FROM `client` ;";
        try{
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(requete);
            while(rs.next()){
               
                cl.add(rs.getString("codeCli"));
            }
            System.out.println("consultation ok");
            
        } catch (SQLException e) {
            System.out.println("probleme de consultation "+e.getMessage());
        }
        return cl;
    }
     public static void ajouter(String p1, Date p2, Float p3, String p4){
        String requete = "insert into `facture` values(?,?,?,?)";
        try{
            PreparedStatement pst = cn.prepareStatement(requete);
            pst.setString(1, p1);
            pst.setDate(2, p2);
            pst.setFloat(3, p3);
            pst.setString(4, p4);
            int n= pst.executeUpdate();
            if(n>=1){
                System.out.println("ajout réussi");
            }
            
        }catch(SQLException e) {
            System.out.println("probleme de requete "+e.getMessage());
    }
    }
     public static boolean modifier(Facture c) {
    String requete = "UPDATE facture SET dateFacture = ?, refCli = ?, total = ? WHERE numFacture = ?";
    try {
        PreparedStatement ps = cn.prepareStatement(requete);
        ps.setString(4, c.getNumFacture());
        ps.setString(2, c.getRefCli());
        ps.setDate(1, c.getDateFacture());
        ps.setFloat(3, c.getTotal());
        int n = ps.executeUpdate();
        if (n >= 1) {
            System.out.println("Modification réussie");
            return true;
        }
    } catch (SQLException e) {
        System.out.println("Problème de requête: " + e.getMessage());
    }
    return false;
}
      public static boolean supprimer(Facture c) {

    String requete = "DELETE FROM facture WHERE numFacture=?";
    try {
        PreparedStatement ps = cn.prepareStatement(requete);
        ps.setString(1, c.getNumFacture());
        int n = ps.executeUpdate();
        if (n >= 1) {
            System.out.println("Suppression réussie");
            return true;
        }
    } catch (SQLException e) {
        System.out.println("Problème de requête: " + e.getMessage());
    }
    return false;
}
    
}
