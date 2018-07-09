package vistas;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import modelos.Conexion;
import modelos.Unidad;

public class TipoDocumentoController implements Initializable {

    @FXML
    private TextField txtIdtipoDocumento;
    @FXML
    private TextField txtNombre;
    @FXML
    private Button btnNuevo;
    @FXML
    private Button btnActualizar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnGuardar;
    @FXML
    private TableView<Unidad> tblViewUnidad;
    @FXML
    private TableColumn<Unidad, Number> clmnIdUnidad;
    @FXML
    private TableColumn<Unidad, String> clmnNombreUnidad;

    
// METODO LIMPIAR CAMPOS
    @FXML
    public void limpiarCamposUnidad() {

        txtIdUnidad.requestFocus();
        txtIdUnidad.setText("");
        txtNombreUnidad.setText("");
        btnGuardar.setDisable(false);
        btnEliminar.setDisable(true);
        btnActualizar.setDisable(true);

    }

    private Conexion conexion;//Instanciando la conexion
    private ObservableList<Unidad> listaUnidad;

//AGREGAR UNIDAD
    public void agregarUnidad() {
        Unidad unidad = new Unidad(
                Integer.valueOf(txtIdUnidad.getText()),
                txtNombreUnidad.getText());
                

        conexion.establecerConexion();
        int resultado = unidad.guardarUnidad(conexion);
        conexion.cerrarConexion();

        if (resultado == 1) {
            listaUnidad.add(unidad);
        }
    }

// ACTUALIZAR UNIDAD
    public void actualizarUnidad() {
        Unidad unidad = new Unidad(
                Integer.valueOf(txtIdUnidad.getText()),
                txtNombreUnidad.getText());

        conexion.establecerConexion();
        int resultado = unidad.actualizarUnidad(conexion);
        conexion.cerrarConexion();

        if (resultado == 1) {
            listaUnidad.set(
                    tblViewUnidad.getSelectionModel().getSelectedIndex(),
                    unidad
            );
        }
    }

// ELIMINAR UNIDAD
    public void eliminarUnidad() {
        conexion.establecerConexion();
        int resultado = tblViewUnidad.getSelectionModel().getSelectedItem()
                .eliminarUnidad((Conexion) conexion.getConnection());
        conexion.cerrarConexion();

        if (resultado == 1) {

            listaUnidad.remove(tblViewUnidad.getSelectionModel().getSelectedIndex());

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
        listaUnidad = FXCollections.observableArrayList();

// LLENAR LISTAS
        Unidad.llenarInformacionUnidad(conexion.getConnection(), listaUnidad);

// ENLAZAR COLUMNAS CON ATRIBUTOS
        clmnIdUnidad.setCellValueFactory(new PropertyValueFactory<Unidad, Number>("idUnidad"));
        clmnNombreUnidad.setCellValueFactory(new PropertyValueFactory<Unidad, String>("nombreUnidad"));

// TABLE VIEWS
        tblViewUnidad.setItems(listaUnidad);
        gestionarEventos();
        conexion.cerrarConexion();

    }

    public void gestionarEventos() {
        tblViewUnidad.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Unidad>() {
            @Override
            public void changed(ObservableValue<? extends Unidad> observable, Unidad valorAnterior,
                    Unidad valorSeleccionado) {
                if (valorSeleccionado != null) {
                    txtIdUnidad.setText(String.valueOf(valorSeleccionado.getIdUnidad()));
                    txtNombreUnidad.setText(String.valueOf(valorSeleccionado.getNombreUnidad()));
  

                    btnGuardar.setDisable(true);
                    btnEliminar.setDisable(false);
                    btnActualizar.setDisable(false);
                }
            }
        }
        );
    }
}