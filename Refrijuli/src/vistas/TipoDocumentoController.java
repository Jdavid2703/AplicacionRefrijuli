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
import modelos.TipoDocumento;

public class TipoDocumentoController implements Initializable {

    @FXML
    private TextField txtIdTipoDocumento;
    @FXML
    private TextField txtNombreTipoDocumento;
    @FXML
    private TextField txtDescripcionTipoDocumento;
    @FXML
    private Button btnNuevo;
    @FXML
    private Button btnActualizar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnGuardar;
    @FXML
    private TableView<TipoDocumento> tblViewTipoDocumento;
    @FXML
    private TableColumn<TipoDocumento, Number> clmnIdTipoDocumento;
    @FXML
    private TableColumn<TipoDocumento, String> clmnNombreTipoDocumento;
    @FXML
    private TableColumn<TipoDocumento, String> clmnDescripcionTipoDocumento;

// METODO LIMPIAR CAMPOS
    @FXML
    public void limpiarCamposUnidad() {

        txtIdTipoDocumento.requestFocus();
        txtNombreTipoDocumento.setText("");
        txtDescripcionTipoDocumento.setText("");
        btnGuardar.setDisable(false);
        btnEliminar.setDisable(true);
        btnActualizar.setDisable(true);

    }

    private Conexion conexion;//Instanciando la conexion
    private ObservableList<TipoDocumento> listaTipoDocumento;

//METODO AGREGAR 
    public void agregarTipoDocumento() {
        TipoDocumento tipoDocumento = new TipoDocumento(
                Integer.valueOf(txtIdTipoDocumento.getText()),
                txtNombreTipoDocumento.getText(),
                txtDescripcionTipoDocumento.getText());

        conexion.establecerConexion();
        int resultado = tipoDocumento.guardarTipoDocumento(conexion);
        conexion.cerrarConexion();

        if (resultado == 1) {
            listaTipoDocumento.add(tipoDocumento);
        }
    }

// METODO ACTUALIZAR 
    public void actualizarTipoDocumento() {
        TipoDocumento tipoDocumento = new TipoDocumento(
                Integer.valueOf(txtIdTipoDocumento.getText()),
                txtNombreTipoDocumento.getText(),
                txtDescripcionTipoDocumento.getText());

        conexion.establecerConexion();
        int resultado = tipoDocumento.actualizarTipoDocumento(conexion);
        conexion.cerrarConexion();

        if (resultado == 1) {
            listaTipoDocumento.set(
                    tblViewTipoDocumento.getSelectionModel().getSelectedIndex(),
                    tipoDocumento
            );
        }
    }

// METODO ELIMINAR 
    public void eliminarTipoDocumento() {
        conexion.establecerConexion();
        int resultado = tblViewTipoDocumento.getSelectionModel().getSelectedItem()
                .eliminarTipoDocumento((Conexion) conexion.getConnection());
        conexion.cerrarConexion();

        if (resultado == 1) {

            listaTipoDocumento.remove(tblViewTipoDocumento.getSelectionModel().getSelectedIndex());

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
        listaTipoDocumento = FXCollections.observableArrayList();

// LLENAR LISTAS
        TipoDocumento.llenarInformacionTipoDocumento(conexion.getConnection(), listaTipoDocumento);

// ENLAZAR COLUMNAS CON ATRIBUTOS
        clmnIdTipoDocumento.setCellValueFactory(new PropertyValueFactory<TipoDocumento, Number>("idTipoDocumento"));
        clmnNombreTipoDocumento.setCellValueFactory(new PropertyValueFactory<TipoDocumento, String>("nombreTipoDocumento"));
        clmnDescripcionTipoDocumento.setCellValueFactory(new PropertyValueFactory<TipoDocumento, String>("descripcionTipoDocumento"));
        
// TABLE VIEWS
        tblViewTipoDocumento.setItems(listaTipoDocumento);
        gestionarEventos();
        conexion.cerrarConexion();

    }

    public void gestionarEventos() {
        tblViewTipoDocumento.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TipoDocumento>() {
            @Override
            public void changed(ObservableValue<? extends TipoDocumento> observable, TipoDocumento valorAnterior,
                    TipoDocumento valorSeleccionado) {
                if (valorSeleccionado != null) {
                    txtIdTipoDocumento.setText(String.valueOf(valorSeleccionado.getIdTipoDocumento()));
                    txtNombreTipoDocumento.setText(String.valueOf(valorSeleccionado.getNombreTipoDocumento()));
                    txtDescripcionTipoDocumento.setText(String.valueOf(valorSeleccionado.getDescripcionTipoDocumento()));

                    btnGuardar.setDisable(true);
                    btnEliminar.setDisable(false);
                    btnActualizar.setDisable(false);
                }
            }
        }
        );
    }
}
