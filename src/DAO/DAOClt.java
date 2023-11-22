
package DAO;

import Classes.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.TreeSet;


public class DAOClt {
    static Connection cn = LaConnexion.seConnecter();
    public static ArrayList<Client> lister(){
        ArrayList<Client> cl= new ArrayList<>();
        
        String requete = "SELECT * FROM `client`;";
        String codeCli ;
        String nomCli, adresseCli, emailCli;
        Client c;
        try{
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(requete);
            while(rs.next()){
                c = new Client(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4));
                cl.add(c);
            }
            System.out.println("consultation ok");
            
        } catch (SQLException e) {
            System.out.println("probleme de consultation "+e.getMessage());
        }
        return cl;
    }
    public static TreeMap<String, Client> trouver(String val) {
        TreeMap<String, Client> res = new TreeMap<>();
        // Connection cn =laConnexion.seConnecter();
        String requete = "SELECT * FROM client WHERE nomCli LIKE ?";

        try (PreparedStatement pst = cn.prepareStatement(requete)) {
            pst.setString(1, val + "%");
            ResultSet rs = pst.executeQuery();
            String code;
            String nomCli, adresseCli, emailCli;
            Client c;
            if (rs != null) {
                while (rs.next()) {
                    code = rs.getString("codeCli");
                    nomCli = rs.getString("nomCli");
                    adresseCli = rs.getString("adresseCli");
                    emailCli = rs.getString("emailCli");
                    c = new Client(code, nomCli, adresseCli, emailCli);
                    res.put(code, c);
                }
            }
        } catch (SQLException ex) {
            System.out.println("probleme req select " + ex.getMessage());
        }
        return res;

    }
    
    
    public static TreeSet<Client> listerParVille(String ville) throws SQLException {
        TreeSet<Client> res = new TreeSet<>();
        
        String requete = "SELECT * FROM `client` WHERE `adresseCli` = ?";
        
        try (PreparedStatement pst = cn.prepareStatement(requete)) {
            pst.setString(1, ville );
            ResultSet rs = pst.executeQuery();
            String code;
            String nomCli, adresseCli, emailCli;
            Client c;
            if(rs!=null) {
                while (rs.next()) {
                            code = rs.getString("codeCli");
                            rs.getString("nomCli");
                            rs.getString("adresseCli");
                            rs.getString("emailCli");
                            c=new Client("codeCli","nomCli","adresseCli","emailCli");
                            res.add(c);
                }
            }
        }catch (SQLException ex) {
            System.out.println("probleme req select "+ex.getMessage());
        }
        return res;
}
    
    public static Client chercher(String codeCli) throws SQLException {
        String sql = "SELECT * FROM `client` WHERE `codeCli` = ?";
        Connection cn = LaConnexion.seConnecter();
        Client resultat = null;

        try (PreparedStatement pst = cn.prepareStatement(sql)) {
            pst.setString(1, codeCli);
            ResultSet rs = pst.executeQuery();
            String code;
            String nomCli, adresseCli, emailCli;
            while (rs.next()) {
                resultat = new Client(rs.getString("codeCli"), rs.getString("nomCli"), rs.getString("adresseCli"), rs.getString("emailCli"));
            }
        } catch (SQLException e) {
            System.out.println("probleme de consultation ");

        }
        return resultat;
    }
    
    public static boolean ajouter(Client c) throws SQLException{
        String requete = "insert into `client` values(?,?,?,?)";
        try{
            PreparedStatement pst = cn.prepareStatement(requete);
            pst.setString(1, c.getCodeCli());
            pst.setString(2, c.getNomCli());
            pst.setString(3, c.getAdresseCli());
            pst.setString(4, c.getEmailCli());
            int n= pst.executeUpdate();
            if(n>=1){
                System.out.println("ajout réussi");
            }
            return true;
        }catch(SQLException e) {
            System.out.println("probleme de requete "+e.getMessage());
    }
        return false;
}
    
  /*  public static boolean ajout(Client c) throws SQLException{
        String requete = "insert into `client` values('"+c.getCodeCli()+"','"+c.getNomCli()+"','"+c.getAdresseCli()+"','"+c.getEmailCli()+"');";
        try{
            Statement st = cn.createStatement();
            
            int n= st.executeUpdate(requete);
            if(n>=1){
                System.out.println("ajout réussi!!");
            }
            return true;
        }catch(SQLException e) {
            System.out.println("probleme de requete "+e.getMessage());
    }
        return false;
}*/
    
    public static boolean changerAdresse(Client c,String adr){
        String requete = "update `client` set `adresseCli`=? where `client`.`codeCli`=? ";
        try{
            PreparedStatement pst = cn.prepareStatement(requete);
            pst.setString(1, adr);
            pst.setString(2, c.getCodeCli());
            int n= pst.executeUpdate();
            if(n>=1){
                System.out.println("modif réussi");
            }
            return true;
    }catch(SQLException e) {
            System.out.println("probleme de requete modif "+e.getMessage());
    }
        return false;
}
    
    public static boolean modifAdresse(Client c,String adr){
        String requete = "update `client` set `adresseCli`='"+c.getAdresseCli()+"' where `client`.`codeCli`='"+c.getCodeCli()+"' ";
        try{
            Statement st = cn.createStatement();
            int n= st.executeUpdate(requete);
            if(n>=1){
                System.out.println("modif réussi");
            }
            return true;
    }catch(SQLException e) {
            System.out.println("probleme de requete modif "+e.getMessage());
    }
        return false;
}
    
    public static boolean Supprimer(Client c) {
        //"update `client` set `adresseCli`=? where `client`.`codeCli`=? ";
        String requete = "delete from `client` where `client`.`codeCli`=? ;";

        try {
            PreparedStatement pst = cn.prepareStatement(requete);
            pst.setString(1, c.getCodeCli());

            int n = pst.executeUpdate();
            if (n >= 1) {
                System.out.println("suppression réussi");
            }
            return true;
        } catch (SQLException e) {
            System.out.println("probleme de requete modif " + e.getMessage());
        }
        return false;
    }
    
    
    public static void Ajouter(String p1, String p2, String p3, String p4){
        String requete = "insert into `client` values(?,?,?,?);";
        try{
            PreparedStatement pst = cn.prepareStatement(requete);
            pst.setString(1, p1);
            pst.setString(2, p2);
            pst.setString(3, p3);
            pst.setString(4, p4);
            int n= pst.executeUpdate();
            if(n>=1){
                System.out.println("ajout réussi");
            }
            
        }catch(SQLException e) {
            System.out.println("probleme de requete "+e.getMessage());
    }
    }
    public static boolean modifier(Client c){
    String requete = "UPDATE `client` SET `nomCli`=?,`adresseCli`=?,`emailCli`=? WHERE `codeCli`=?;";
        try{
            PreparedStatement pst = cn.prepareStatement(requete);
            pst.setString(1,c.getNomCli() );
            pst.setString(2, c.getAdresseCli());
            pst.setString(3, c.getEmailCli());
            pst.setString(4, c.getCodeCli());
            int n= pst.executeUpdate();
            if(n>=1){
                System.out.println("modif réussi");
            }
            return true;
    }catch(SQLException e) {
            System.out.println("probleme de requete modif "+e.getMessage());
    }
        return false;}
    public static boolean archiver(String code) {
    String requeteSelect = "SELECT * FROM `client` WHERE `codeCli`=?";
    String requeteInsert = "INSERT INTO `client_archive` VALUES (?,?,?,?)";
    String requeteDelete = "DELETE FROM `client` WHERE `codeCli`=?";
    try {
        PreparedStatement pstSelect = cn.prepareStatement(requeteSelect);
        pstSelect.setString(1, code);
        ResultSet rs = pstSelect.executeQuery();
        if (rs.next()) {
            PreparedStatement pstInsert = cn.prepareStatement(requeteInsert);
            pstInsert.setString(1, rs.getString("codeCli"));
            pstInsert.setString(2, rs.getString("nomCli"));
            pstInsert.setString(3, rs.getString("adresseCli"));
            pstInsert.setString(4, rs.getString("emailCli"));
            int n = pstInsert.executeUpdate();
            if (n >= 1) {
                System.out.println("Archivage réussi");
            }
            PreparedStatement pstDelete = cn.prepareStatement(requeteDelete);
            pstDelete.setString(1, code);
            pstDelete.executeUpdate();
            return true;
        } else {
            System.out.println("Client introuvable");
            return false;
        }
    } catch (SQLException e) {
        System.out.println("problème de requête archiver " + e.getMessage());
        return false;
    }
}
}
