/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Classes.Client;
import DAO.DAOClt;
import DAO.LaConnexion;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author DELL
 */
public class FXMLClientController implements Initializable {
    
    @FXML
    TableView<Client> tv = new TableView<>();
    
    @FXML
    TextField txtCode;
    @FXML
    TextField txtNom;
    @FXML
    TextField txtAdresse;
    @FXML
    TextField txtEmail;
    @FXML
    Button btnAJ;
    @FXML
    Button btnMod;
    @FXML
    Button btnAr;
    
    
    @FXML
    TableColumn<Client, String> ColCode;
    @FXML
    TableColumn<Client, String> ColNom;
    @FXML
    TableColumn<Client, String> ColAdresse;
    @FXML
    TableColumn<Client, String> ColEmail;
    @FXML
    AnchorPane s;
    
    static Connection cd = LaConnexion.seConnecter();

    ObservableList<Client> observableList;
    String profile="";
    
    
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        
    }
    
    @FXML
    private void Ajouter(ActionEvent event){
        if (txtCode.getText().isEmpty() || txtNom.getText().isEmpty() || txtAdresse.getText().isEmpty()|| txtEmail.getText().isEmpty()) {
           showAlert("Veuillez remplir tous les champs", Alert.AlertType.WARNING,"warning","warning.png","Attention");
        } else {
        DAOClt.Ajouter(txtCode.getText(), txtNom.getText(), txtAdresse.getText(),txtEmail.getText());
        showAlert("Client Ajouter", Alert.AlertType.INFORMATION,"ok","ok.png","Succeed");
        lister();
        remiseAzéro();

    }}
    @FXML
    private void Modifier(ActionEvent event){

    Client selectedClient = tv.getSelectionModel().getSelectedItem();
         if (selectedClient != null) {
             selectedClient.setCodeCli(txtCode.getText());
             selectedClient.setNomCli(txtNom.getText());
             selectedClient.setAdresseCli(txtAdresse.getText());
             selectedClient.setEmailCli(txtEmail.getText());
             if (DAOClt.modifier(selectedClient)) {
                 showAlert("Client Modifier", Alert.AlertType.INFORMATION,"ok","ok.png","Succeed");
                tv.refresh();
                remiseAzéro();
         }else {
                 showAlert("Something went wrong. La modification a échoué", Alert.AlertType.ERROR,"error","erreur.png","Erreur");
            }
        } else {
                showAlert("Aucun Client sélectionnée", Alert.AlertType.WARNING,"warning","warning.png","Attention");
        }
        lister();



    }
    @FXML
    private void Archiver(ActionEvent event){
       
        
        Client selectedClient = tv.getSelectionModel().getSelectedItem();
         if (selectedClient != null) {
             if (DAOClt.archiver(txtCode.getText())) {
                showAlert("Client Supprimee", Alert.AlertType.INFORMATION,"ok","ok.png","Welcome");
                tv.refresh();
                remiseAzéro();
         }else {
                 showAlert("Something went wrong. La Suppresion a échoué", Alert.AlertType.ERROR,"error","erreur.png","Erreur");
            }
        } else {
             showAlert("Aucun Client sélectionnée", Alert.AlertType.WARNING,"warning","warning.png","Attention");
        }
        lister();

    }
    @FXML
    public void logOut(ActionEvent event){
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
 @FXML
public void emchiFacture(ActionEvent event) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXMLFacture.fxml"));
        Parent root = loader.load();
        FXMLFactureController factureController = loader.getController();
        factureController.getProfile(this.profile);
        // Accédez à la scène actuelle
        Scene currentScene = ((Node) event.getSource()).getScene();

        // Créez une nouvelle scène pour la facture
        Scene factureScene = new Scene(root);
        

        // Remplacez le contenu de la scène actuelle par la scène de la facture
        Stage currentStage = (Stage) currentScene.getWindow();
        currentStage.setScene(factureScene);
        currentStage.setTitle("Gestion Facture");
        currentStage.show();

    } catch (IOException e) {
        e.printStackTrace();
    }
}



    public void remiseAzéro(){
        txtCode.clear();
        txtCode.requestFocus();
        txtNom.clear();
        txtAdresse.clear();
        txtEmail.clear();
    }
    


    public void lister() {
       // Connection cd = cn.seConnecter();
        tv.getItems().clear();
        try {
            ResultSet rs = cd.createStatement().executeQuery("SELECT * FROM `client`");
            while (rs.next()){
                observableList.add(new Client(rs.getString(1), rs.getString(2),  rs.getString(3),  rs.getString(4)));
                
            }
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
        
        tv.setItems(observableList);
    }
        @Override
    public void initialize(URL location, ResourceBundle resources) {
        observableList = FXCollections.observableArrayList();
        ColCode.setCellValueFactory(new PropertyValueFactory<>("codeCli"));
        ColNom.setCellValueFactory(new PropertyValueFactory<>("nomCli"));
        ColAdresse.setCellValueFactory(new PropertyValueFactory<>("adresseCli"));
        ColEmail.setCellValueFactory(new PropertyValueFactory<>("emailCli"));
        lister();
tv.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
    if (newValue != null) {
        // Récupérer les données de la ligne sélectionnée
        String code = newValue.getCodeCli();
        String nom = newValue.getNomCli();
        String adresse = newValue.getAdresseCli();
        String email = newValue.getEmailCli();

        // Insérer les données dans les TextField
        txtCode.setText(code);
        txtNom.setText(nom);
        txtAdresse.setText(adresse);
        txtEmail.setText(email);
    }
});


    }
     public void getProfile(String Profile){
         this.profile=Profile;
  if (!"Admin".equals(this.profile)) {
    btnAJ.setDisable(true);
    btnMod.setDisable(true);
    btnAr.setDisable(true);
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
    }
    

