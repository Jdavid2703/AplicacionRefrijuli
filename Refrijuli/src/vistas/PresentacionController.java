package vistas;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import modelos.Conexion;
import modelos.Presentacion;

public class PresentacionController implements Initializable {

    @FXML
    private TextField txtIdPresentacion;
    @FXML
    private TextArea txtDescripcion;
    @FXML
    private Button btnNuevo;
    @FXML
    private Button btnActualizar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnGuardar;

    @FXML
    private TableView<Presentacion> tblViewPresentacion;
    @FXML
    private TableColumn<Presentacion, Number> clmnIdPresentacion;
    @FXML
    private TableColumn<Presentacion, String> clmnDescripcion;

    @FXML
    public void limpiarCamposNuevoRegistro() {

        txtIdPresentacion.requestFocus();
        txtIdPresentacion.setText("");
        txtDescripcion.setText("");
        btnGuardar.setDisable(false);
        btnEliminar.setDisable(true);
        btnActualizar.setDisable(true);

    }
    private Conexion conexion;
    private ObservableList<Presentacion> listaPresentacion;

    public void agregarRegistroPresentacion() {
        Presentacion presentacion = new Presentacion(
                Integer.valueOf(txtIdPresentacion.getText()),
                txtDescripcion.getText());

        conexion.establecerConexion();
        int resultado = presentacion.guardarPresentación(conexion);
        conexion.cerrarConexion();

        if (resultado == 1) {
            listaPresentacion.add(presentacion);
        }
    }

    public void actualizarPresentacion() {
        Presentacion presentacion = new Presentacion(
                Integer.valueOf(txtIdPresentacion.getText()),
                txtDescripcion.getText());

        conexion.establecerConexion();
        int resultado = presentacion.actualizarPresentacion(conexion);
        conexion.cerrarConexion();

        if (resultado == 1) {
            listaPresentacion.set(
                    tblViewPresentacion.getSelectionModel().getSelectedIndex(),
                    presentacion
            );
        }
    }

     public void eliminarPresentacion() {
        conexion.establecerConexion();
        int resultado = tblViewPresentacion.getSelectionModel().getSelectedItem()
                .eliminarPresentacion((Conexion) conexion.getConnection());
        conexion.cerrarConexion();

        if (resultado == 1) {

            listaPresentacion.remove(tblViewPresentacion.getSelectionModel().getSelectedIndex());

            Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
            mensaje.setTitle("Registro Eliminado");
            mensaje.setContentText("Registro ha sido eliminado con exito");
            mensaje.setHeaderText("Resultado:");
            mensaje.show();

        }

    }
 @Override
    public void initialize(URL url, ResourceBundle rb) {
        conexion = new Conexion();
        conexion.establecerConexion();
    }
}

