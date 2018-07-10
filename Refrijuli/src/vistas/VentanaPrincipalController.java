package vistas;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class VentanaPrincipalController {

    @FXML
    void newpage(ActionEvent event) {

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
            FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/vistas/FXMLinsumo.fxml"));
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
            FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/vistas/FXMLpedido.fxml"));
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
            FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/vistas/FXMLpresentacion.fxml"));
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
            FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/vistas/FXMLunidad.fxml"));
            Parent root1 = (Parent) fxmlloader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (Exception e) {

            System.err.println(e.getMessage());

        }

    }

}
