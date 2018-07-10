package vistas;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class VentanaPrincipalController {

     
    @FXML
  public void newpage(ActionEvent event) {

        try {
            FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("FXMLcategoria.fxml"));
            Parent root1 = (Parent) fxmlloader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (Exception e) {

            System.err.println(e.getMessage());

        }

    }

    @FXML
    void newpage2(ActionEvent event) {

        try {
            FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("FXMLtipoDocumento.fxml"));
            Parent root1 = (Parent) fxmlloader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (Exception e) {

            System.err.println(e.getMessage());

        }

    }

    @FXML
    void newpage3(ActionEvent event) {

        try {
            FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("FXMLinsumo.fxml"));
            Parent root1 = (Parent) fxmlloader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (Exception e) {

            System.err.println(e.getMessage());

        }

    }

    @FXML
    void newpage4(ActionEvent event) {

        try {
            FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("FXMLpedido.fxml"));
            Parent root1 = (Parent) fxmlloader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (Exception e) {

            System.err.println(e.getMessage());

        }

    }

    @FXML
    void newpage5(ActionEvent event) {

        try {
            FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("FXMLpresentacion.fxml"));
            Parent root1 = (Parent) fxmlloader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (Exception e) {

            System.err.println(e.getMessage());

        }

    }

    @FXML
    void newpage6(ActionEvent event) {

        try {
            FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("FXMLunidad.fxml"));
            Parent root1 = (Parent) fxmlloader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (Exception e) {

            System.err.println(e.getMessage());

        }
        

    }
    
    @FXML
    void newpage7(ActionEvent event) {

        try {
            FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("FXMLcliente.fxml"));
            Parent root1 = (Parent) fxmlloader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (Exception e) {

            System.err.println(e.getMessage());

        }

    }
    
    @FXML
    void newpage8(ActionEvent event) {

        try {
            FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("FXMLproducto.fxml"));
            Parent root1 = (Parent) fxmlloader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (Exception e) {

            System.err.println(e.getMessage());

        }

    }

}
