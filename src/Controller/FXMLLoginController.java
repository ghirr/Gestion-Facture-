/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Classes.User;
import DAO.DAOUser;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author islem
 */
public class FXMLLoginController implements Initializable {
    
     @FXML
    private StackPane s;

    @FXML
    private TextField email;

    @FXML
    private PasswordField password;

    @FXML
    private Button loginButton;

    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    public void goToSignUp(ActionEvent event) {
     try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXMLSignup.fxml"));
        Parent root = loader.load();

        // Accédez à la scène actuelle
        Scene currentScene = ((Node) event.getSource()).getScene();

        // Créez une nouvelle scène pour la facture
        Scene factureScene = new Scene(root);

        // Remplacez le contenu de la scène actuelle par la scène de la facture
        Stage currentStage = (Stage) currentScene.getWindow();
        currentStage.setScene(factureScene);
        currentStage.setTitle("SignUp");
        currentStage.show();

    } catch (IOException e) {
        e.printStackTrace();
    }
    }
    public void login(ActionEvent event){ 
        String userEmail = email.getText();
        String userPassword = password.getText();

        // Create a new Users object with the entered email and password
        User user = new User(userEmail, userPassword);

        // Call the login method from DaoLogin class to authenticate the user
        String isAuthenticated = DAOUser.login(user);
        if (isAuthenticated !=null) {
            System.out.println(isAuthenticated);
            try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/FXMLClient.fxml"));
                    Parent root = loader.load();
                    FXMLClientController clientController = loader.getController();
                    clientController.getProfile(isAuthenticated);
                    // Access the current stage
                    Stage currentStage = (Stage) s.getScene().getWindow();

                    // Replace the content of the current scene with the content of FXMLClient.fxml
                    Scene scene = new Scene(root);
                    currentStage.setScene(scene);
                    currentStage.setTitle("Gestion Client");
                    currentStage.show();
                } catch (IOException e) {
                    showAlert("Failed to load FXMLClient.fxml", Alert.AlertType.ERROR);
                    e.printStackTrace();
                }
            } else {
                showAlert("Please Verify Your credentials", Alert.AlertType.ERROR);
            }
        }
        
    

    private void showAlert(String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setHeaderText(null);
         // Apply the custom style class to the alert dialog
         DialogPane dialog = alert.getDialogPane();
         dialog.getStylesheets().add(getClass().getResource("/View/Style.css").toString());
         dialog.getStyleClass().add("error");
         Image image = new Image(getClass().getResourceAsStream("/images/erreur.png"));
               ImageView imageView = new ImageView(image);
               imageView.setFitWidth(48); // Définir la largeur souhaitée de l'image
               imageView.setFitHeight(48); // Définir la hauteur souhaitée de l'image

        alert.setGraphic(imageView);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
