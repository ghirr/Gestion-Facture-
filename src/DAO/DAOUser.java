/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Classes.User;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author islem
 */
public class DAOUser {
     static Connection cn = LaConnexion.seConnecter();
private static String encryptPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xFF & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            // Handle the exception
            e.printStackTrace();
        }
        return null;
    }

public static boolean signUp(User user) {
        String requte = "INSERT INTO user VALUES (?, ?, ?, ?, ?)";
        System.out.println(requte);
        try {
            PreparedStatement pst = cn.prepareStatement(requte);
            pst.setString(1, user.getProfile());
            pst.setString(2, user.getName());
            pst.setString(3, user.getLastname());
            pst.setString(4, user.getEmail());

            // Encrypt the password before storing it
            String encryptedPassword = encryptPassword(user.getPassword());
            pst.setString(5, encryptedPassword);
            
            int rowsAffected = pst.executeUpdate();
            if (rowsAffected >= 1) {
                System.out.println("Ajout réussi");
                 System.out.println(encryptedPassword+"   ahawa");
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Problème de requête: " + e.getMessage());
        }

        return false;
    }


public static String login(User c) {
    String res=null;
        String requte = "SELECT * FROM `user` WHERE email=? AND pwd=?";
        try {
            PreparedStatement pst = cn.prepareStatement(requte);
            pst.setString(1, c.getEmail());

            // Encrypt the entered password
            String encryptedPassword = encryptPassword(c.getPassword());
            pst.setString(2, encryptedPassword);
            System.out.println(encryptedPassword);

            ResultSet r = pst.executeQuery();
           
            if (r.next()) {
                res=r.getString("profile");
            }
        } catch (SQLException e) {
            // Error occurred during login
        }
        return res;
    }




    
}
