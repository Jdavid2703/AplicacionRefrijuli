package vistas;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
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
import javafx.scene.control.cell.PropertyValueFactory;
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

// METODO LIMPIAR CAMPOS
    @FXML
    public void limpiarCamposPresentacion() {

        txtIdPresentacion.requestFocus();
        txtIdPresentacion.setText("");
        txtDescripcion.setText("");
        btnGuardar.setDisable(false);
        btnEliminar.setDisable(true);
        btnActualizar.setDisable(true);
        txtIdPresentacion.setDisable(false);

    }
    private Conexion conexion; //Instanciando la conexion
    private ObservableList<Presentacion> listaPresentacion;

// METODO AGREGAR
    public void agregarRegistroPresentacion() {
        Presentacion presentacion = new Presentacion(
                Integer.valueOf(txtIdPresentacion.getText()),
                txtDescripcion.getText());

        conexion.establecerConexion();
        int resultado = presentacion.guardarPresentación(conexion);
        conexion.cerrarConexion();

        if (resultado == 1) {
            listaPresentacion.add(presentacion);

            Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
            mensaje.setTitle("Registro agregado");
            mensaje.setContentText("Registro ha sido agregado con exíto");
            mensaje.setHeaderText("Resultado:");
            mensaje.show();

            limpiarCamposPresentacion();
        }
    }

// METODO ACTUALIZAR
    public void actualizarPresentacion() {
        Presentacion presentacion = new Presentacion(
                Integer.valueOf(txtIdPresentacion.getText()),
                txtDescripcion.getText());

        conexion.establecerConexion();
        int resultado = presentacion.actualizarPresentacion(conexion);
        conexion.cerrarConexion();

        if (resultado == 1) {
            listaPresentacion.set(
                    tblViewPresentacion.getSelectionModel().getSelectedIndex(), presentacion);

            Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
            mensaje.setTitle("Registro Actualizado");
            mensaje.setContentText("Registro ha sido actualizado con exito");
            mensaje.setHeaderText("Resultado:");
            mensaje.show();

            limpiarCamposPresentacion();
        }
    }

// METODO ELIMINAR 
    public void eliminarPresentacion() {

        Alert cuadroDialogoConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        cuadroDialogoConfirmacion.setTitle("Confirmación");
        cuadroDialogoConfirmacion.setHeaderText("Eliminar Registro");
        cuadroDialogoConfirmacion.setContentText("¿Está Seguro de Eliminar el Registro?");
        Optional<ButtonType> resultado = cuadroDialogoConfirmacion.showAndWait();
        if (resultado.get() == ButtonType.OK) {
            Presentacion presentacion = new Presentacion();
            presentacion.setIdPresentacion(Integer.valueOf(txtIdPresentacion.getText()));
            conexion.establecerConexion();
            int r = presentacion.eliminarPresentacion(conexion);
            conexion.cerrarConexion();

            if (r == 1) {
                listaPresentacion.remove(tblViewPresentacion.getSelectionModel().getSelectedIndex());

                Alert cuadroDialogo = new Alert(Alert.AlertType.INFORMATION);
                cuadroDialogo.setContentText("Registro Eliminado con Éxito");
                cuadroDialogo.setTitle("Registro Eliminado");
                cuadroDialogo.setHeaderText("Resultado: ");
                cuadroDialogo.showAndWait();

                limpiarCamposPresentacion();

            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        conexion = new Conexion();
        conexion.establecerConexion();

// INICIALIZAR
        listaPresentacion = FXCollections.observableArrayList();

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
                    txtIdPresentacion.setDisable(true);
                }
            }
        }
        );
    }
}
