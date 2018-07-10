package vistas;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import modelos.Cliente;
import modelos.Conexion;
import modelos.Estado;
import modelos.Pedido;
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

// INICIALIZAR
        listaPresentacion= FXCollections.observableArrayList();

// LLENAR LISTAS
        Presentacion.llenarInformacionPresentacion(conexion.getConnection(), listaPresentacion);


// ENLAZAR COLUMNAS CON ATRIBUTOS
        clmnIdPresentacion.setCellValueFactory(new PropertyValueFactory<Presentacion, Number>("idPresentacion"));
        clmnDescripcion.setCellValueFactory(new PropertyValueFactory<Presentacion, String>("descripcion"));
      

// TABLE VIEWS
        tblViewPresentacion.setItems(listaPresentacion);
        gestionarEventos();
        conexion.cerrarConexion();

    }

    public void gestionarEventos() {
        tblViewPresentacion.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Presentacion>() {
            @Override
            public void changed(ObservableValue<? extends Presentacion> observable, Presentacion valorAnterior,
                    Presentacion valorSeleccionado) {
                if (valorSeleccionado != null) {
                    txtIdPresentacion.setText(String.valueOf(valorSeleccionado.getIdPresentacion()));
                    txtDescripcion.setText(valorSeleccionado.getDescripcion());
                    

                    btnGuardar.setDisable(true);
                    btnEliminar.setDisable(false);
                    btnActualizar.setDisable(false);
                }
            }
        }
        );
    }
}


