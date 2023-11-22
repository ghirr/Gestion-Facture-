/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;



import Classes.Client;
import Classes.Facture;
import DAO.DAOFct;
import DAO.LaConnexion;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


/**
 *
 * @author islem
 */
public class FXMLFactureController implements Initializable {
    @FXML
    TableView<Facture> tv = new TableView<>();
    @FXML
    TextField txtNum;
    @FXML
    TextField txtTotal;
    @FXML
private ComboBox<String> comboBox;
    @FXML
    DatePicker txtDate;
    @FXML
    TableColumn<Client, String> ColNum;
    @FXML
    TableColumn<Client, Date> ColDate;
    @FXML
    TableColumn<Client, Float> ColTotal;
    @FXML
    TableColumn<Client, String> ColRef;
    @FXML
    Button btnAJ;
    @FXML
    Button btnMod;
    @FXML
    Button btnAr;
    String profile;
    
    static Connection cd = LaConnexion.seConnecter();

    ObservableList<Facture> observableList;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        
    }
    @FXML
    private void Ajouter(ActionEvent event){
        if (txtNum.getText().isEmpty() || txtTotal.getText().isEmpty() || comboBox.getValue()== null||txtDate.getValue()== null) {
            showAlert("Veuillez remplir tous les champs", Alert.AlertType.WARNING,"warning","warning.png","Attention");
        } else {
       DAOFct.ajouter(txtNum.getText(),Date.valueOf(txtDate.getValue()),Float.parseFloat(txtTotal.getText()),comboBox.getValue());
        showAlert("Facture Ajouter", Alert.AlertType.INFORMATION,"ok","ok.png","Succeed");
       remiseAzéro();
       lister();
    }}
    @FXML
    private void Modifier(ActionEvent event){
         Facture selectedFacture = tv.getSelectionModel().getSelectedItem();
         if (selectedFacture != null) {
             selectedFacture.setNumFacture(txtNum.getText());
             selectedFacture.setDateFacture(Date.valueOf(txtDate.getValue()));
             selectedFacture.setRefCli(comboBox.getValue());
             selectedFacture.setTotal(Float.parseFloat(txtTotal.getText()));
             if (DAOFct.modifier(selectedFacture)) {
                 showAlert("Facture Modifier", Alert.AlertType.INFORMATION,"ok","ok.png","Succeed");
                tv.refresh();
                remiseAzéro();
         }else {
                  showAlert("Something went wrong. La modification a échoué", Alert.AlertType.ERROR,"error","erreur.png","Erreur");
            }
        } else {
              showAlert("Aucune Facture sélectionnée", Alert.AlertType.WARNING,"warning","warning.png","Attention");
        }
        lister();
    }
    @FXML
    private void Archiver(ActionEvent event){
        Facture selectedFacture = tv.getSelectionModel().getSelectedItem();
         if (selectedFacture != null) {
             selectedFacture.setNumFacture(txtNum.getText());
             selectedFacture.setDateFacture(Date.valueOf(txtDate.getValue()));
             selectedFacture.setRefCli(comboBox.getValue());
             selectedFacture.setTotal(Float.parseFloat(txtTotal.getText()));
             if (DAOFct.supprimer(selectedFacture)) {
                showAlert("Facture Supprimee", Alert.AlertType.INFORMATION,"ok","ok.png","Welcome");
                tv.refresh();
                remiseAzéro();
         }else {
               showAlert("Something went wrong. La Suppresion a échoué", Alert.AlertType.ERROR,"error","erreur.png","Erreur");
            }
        } else {
            showAlert("Aucune Facture sélectionnée", Alert.AlertType.WARNING,"warning","warning.png","Attention");
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
public void emchiclient(ActionEvent event) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXMLClient.fxml"));
        Parent root = loader.load();
        FXMLClientController clientController = loader.getController();
        clientController.getProfile(this.profile);
        // Accédez à la scène actuelle
        Scene currentScene = ((Node) event.getSource()).getScene();

        // Créez une nouvelle scène pour la facture
        Scene factureScene = new Scene(root);

        // Remplacez le contenu de la scène actuelle par la scène de la facture
        Stage currentStage = (Stage) currentScene.getWindow();
        currentStage.setScene(factureScene);
        currentStage.setTitle("Gestion Client");
        currentStage.show();

    } catch (IOException e) {
        e.printStackTrace();
    }
}
    public void lister() {
       // Connection cd = cn.seConnecter();
        tv.getItems().clear();
        try {
            ResultSet rs = cd.createStatement().executeQuery("SELECT * FROM `facture`");
            while (rs.next()){
                observableList.add(new Facture(rs.getString(1),rs.getDate(2), rs.getFloat(3), rs.getString(4)));
                
            }
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
        
        tv.setItems(observableList);
    }
     public void remiseAzéro(){
        txtNum.clear();
        txtNum.requestFocus();
        txtTotal.clear();
         comboBox.setValue(null);
    txtDate.setValue(null);    }

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
          ArrayList<String> references = DAOFct.getRef();

    // Ajoutez les références au ComboBox
    comboBox.getItems().addAll(references);
    observableList = FXCollections.observableArrayList();
        ColNum.setCellValueFactory(new PropertyValueFactory<>("numFacture"));
        ColDate.setCellValueFactory(new PropertyValueFactory<>("dateFacture"));
        ColTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        ColRef.setCellValueFactory(new PropertyValueFactory<>("refCli"));
        tv.setItems(observableList);
        lister();
        tv.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
    if (newValue != null) {
        // Récupérer les données de la ligne sélectionnée
        String num = newValue.getNumFacture();
        Date date = newValue.getDateFacture();
        Float total = newValue.getTotal();
        String ref = newValue.getRefCli();

        // Insérer les données dans les TextField
         txtNum.setText(num);
        txtDate.setValue(date.toLocalDate());
        txtTotal.setText(total.toString());
        comboBox.setValue(ref);
    }
});
    } 
    
         public void getProfile(String Profile){
         this.profile=Profile;
System.out.println(Profile);
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
