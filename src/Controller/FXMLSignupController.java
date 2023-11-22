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
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
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
public class FXMLSignupController implements Initializable {
    
     @FXML
    private TextField txtName;
      @FXML
    private TextField txtLastName;

    @FXML
    private TextField txtEmail;

    @FXML
    private PasswordField txtPwd;


    @FXML
    private StackPane s;
    @FXML
    private ComboBox<String> txtProfile;
    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        
    }
    public void goToLogin(ActionEvent event) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXMLLogin.fxml"));
        Parent root = loader.load();

        // Accédez à la scène actuelle
        Scene currentScene = ((Node) event.getSource()).getScene();

        // Créez une nouvelle scène pour la facture
        Scene factureScene = new Scene(root);

        // Remplacez le contenu de la scène actuelle par la scène de la facture
        Stage currentStage = (Stage) currentScene.getWindow();
        currentStage.setScene(factureScene);
        currentStage.setTitle("Login");
        currentStage.show();

    } catch (IOException e) {
        e.printStackTrace();
    }
    }
    public void signUp(ActionEvent event){
        if (txtProfile.getValue()==null||txtName.getText().isEmpty() ||txtLastName.getText().isEmpty() || txtEmail.getText().isEmpty() || txtPwd.getText().isEmpty()) {
            showAlert("Veuillez remplir tous les champs", Alert.AlertType.WARNING,"warning","warning.png","Attention");
        } else {
            String profile = txtProfile.getValue();
            String name = txtName.getText();
            String lastname = txtLastName.getText();
            String email = txtEmail.getText();
            String pwd = txtPwd.getText();
            User user = new User(profile,name,lastname,email,pwd);

            if (DAOUser.signUp(user)) {
                showAlert("USER CREATED SUCCESSFULLY, Welcome to our application", Alert.AlertType.INFORMATION,"ok","ok.png","Welcome");
                this.goToLogin(event);
             
    } else {
        showAlert("Something went wrong. Failed to create user.", Alert.AlertType.ERROR,"error","erreur.png","Erreur");
    }
        }
    
    
    }
    
     private void showAlert(String message, Alert.AlertType alertType,String classe,String img,String hala) {
        Alert alert = new Alert(alertType, message, ButtonType.OK);
        DialogPane dialog = alert.getDialogPane();
         dialog.getStylesheets().add(getClass().getResource("/View/Style.css").toString());
         dialog.getStyleClass().add(classe);
         
         Image image = new Image(getClass().getResourceAsStream("/images/"+img));
               ImageView imageView = new ImageView(image);
               imageView.setFitWidth(48); // Définir la largeur souhaitée de l'image
               imageView.setFitHeight(48); // Définir la hauteur souhaitée de l'image

        alert.setGraphic(imageView);
        alert.setHeaderText(hala);
        alert.setTitle("");
        alert.show();
    }
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
    // Ajoutez les références au ComboBox
 
   
    txtProfile.getItems().addAll("Admin","User");
    //txtProfile.getItems().addAll("Admin","User");
    }    
    
}
