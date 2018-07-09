package vistas;

import java.net.URL;
import java.sql.Date;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import modelos.Categoria;
import modelos.Conexion;
import modelos.Insumo;
import modelos.Presentacion;
import modelos.Unidad;

public class InsumoController implements Initializable {

    @FXML
    private TextField txtIdInsumo;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextArea txtDescripcion;
    @FXML
    private TextField txtCantidad;
    @FXML
    private ComboBox<Categoria> cmbIdCategoria;
    @FXML
    private TextField txtStockMin;
    @FXML
    private TextField txtStockMax;
    @FXML
    private ComboBox<Presentacion> cmbIdPresentacion;
    @FXML
    private ComboBox<Unidad> cmbIdUnidad;
    @FXML
    private TextField txtPrecioUnitario;
    @FXML
    private TextField txtDisponibilidad;
    @FXML
    private Button btnNuevo;
    @FXML
    private Button btnActualizar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnGuardar;

    @FXML
    private TableView<Insumo> tblViewInsumo;
    @FXML
    private TableColumn<Insumo, Number> clmnIdInsumo;
    @FXML
    private TableColumn<Insumo, String> clmnNombre;
    @FXML
    private TableColumn<Insumo, String> clmnDescripcion;
    @FXML
    private TableColumn<Insumo, Number> clmnCantidad;
    @FXML
    private TableColumn<Insumo, Categoria> clmnIdCategoria;
    @FXML
    private TableColumn<Insumo, Number> clmnStockMin;
    @FXML
    private TableColumn<Insumo, Number> clmnStockMax;
    @FXML
    private TableColumn<Insumo, Presentacion> clmnIdPresentacion;
    @FXML
    private TableColumn<Insumo, Unidad> clmnIdUnidad;
    @FXML
    private TableColumn<Insumo, Number> clmnPrecioUnitario;
    @FXML
    private TableColumn<Insumo, Number> clmnDisponibilidad;

    @FXML
    public void limpiarCamposInsumo() {

        txtIdInsumo.setText("");
        txtNombre.setText("");
        txtDescripcion.setText("");
        txtCantidad.setText("");
        cmbIdCategoria.setValue(null);
        txtStockMin.setText("");
        txtStockMax.setText("");
        cmbIdPresentacion.setValue(null);
        cmbIdUnidad.setValue(null);
        txtPrecioUnitario.setText("");
        txtDisponibilidad.setText("");
        btnGuardar.setDisable(false);
        btnActualizar.setDisable(true);
        btnEliminar.setDisable(true);
        txtIdInsumo.requestFocus();

    }
    private Conexion conexion;
    private ObservableList<Insumo> listaInsumo;
    private ObservableList<Categoria> listaCategoria;
    private ObservableList<Presentacion> listaPresentacion;
    private ObservableList<Unidad> listaUnidad;

    @FXML
    public void agregarInsumo() {
        Insumo insumo = new Insumo(
                Integer.valueOf(txtIdInsumo.getText()),
                txtNombre.getText(),
                txtDescripcion.getText(),
                Integer.valueOf(txtCantidad.getText()),
                cmbIdCategoria.getSelectionModel().getSelectedItem(),
                Integer.valueOf(txtStockMin.getText()),
                Integer.valueOf(txtStockMax.getText()),
                cmbIdPresentacion.getSelectionModel().getSelectedItem(),
                cmbIdUnidad.getSelectionModel().getSelectedItem(),
                Integer.valueOf(txtPrecioUnitario.getText()),
                Integer.valueOf(txtPrecioUnitario.getText()));

        conexion.establecerConexion();
        int resultado = insumo.guardarInsumo(conexion);
        conexion.cerrarConexion();

        if (resultado == 1) {
            listaInsumo.add(insumo);
        }
    }

    @FXML
    public void actualizarInsumo() {
        Insumo insumo = new Insumo(
                Integer.valueOf(txtIdInsumo.getText()),
                txtNombre.getText(),
                txtDescripcion.getText(),
                Integer.valueOf(txtCantidad.getText()),
                cmbIdCategoria.getSelectionModel().getSelectedItem(),
                Integer.valueOf(txtStockMin.getText()),
                Integer.valueOf(txtStockMax.getText()),
                cmbIdPresentacion.getSelectionModel().getSelectedItem(),
                cmbIdUnidad.getSelectionModel().getSelectedItem(),
                Integer.valueOf(txtPrecioUnitario.getText()),
                Integer.valueOf(txtPrecioUnitario.getText()));

        conexion.establecerConexion();
        int resultado = insumo.actualizarInsumo(conexion);
        conexion.cerrarConexion();

        if (resultado == 1) {
            listaInsumo.set(
                    tblViewInsumo.getSelectionModel().getSelectedIndex(),
                    insumo
            );
        }
    }

    public void eliminarInsumo() {
        Alert cuadroDialogoConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        cuadroDialogoConfirmacion.setTitle("Confirmacion");
        cuadroDialogoConfirmacion.setHeaderText("Eliminar Registro");
        cuadroDialogoConfirmacion.setContentText("¿Está Seguro de Eliminar el Registro?");
        Optional<ButtonType> resultado = cuadroDialogoConfirmacion.showAndWait();
        if (resultado.get() == ButtonType.OK) {
            Insumo insumo = new Insumo();
            insumo.setIdInsumo(Integer.valueOf(txtIdInsumo.getText()));
            conexion.establecerConexion();
            int r = insumo.eliminarInsumo(conexion);
            conexion.cerrarConexion();

            if (r == 1) {
                listaInsumo.remove(tblViewInsumo.getSelectionModel().getSelectedIndex());
                Alert cuadroDialogo = new Alert(Alert.AlertType.INFORMATION);
                cuadroDialogo.setContentText("Registro Eliminado con Éxito");
                cuadroDialogo.setTitle("Registro Eliminado");
                cuadroDialogo.setHeaderText("Resultado: ");
                cuadroDialogo.showAndWait();
            }

        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        conexion = new Conexion();
        conexion.establecerConexion();

// INICIALIZAR
        listaInsumo = FXCollections.observableArrayList();
        listaCategoria = FXCollections.observableArrayList();
        listaPresentacion = FXCollections.observableArrayList();
        listaUnidad = FXCollections.observableArrayList();

// LLENAR LISTAS
        Insumo.llenarInformacionInsumo(conexion.getConnection(), listaInsumo);
        Categoria.llenarInformacionCategoria(conexion.getConnection(), listaCategoria);
        Presentacion.llenarInformacionPresentacion(conexion.getConnection(), listaPresentacion);
        Unidad.llenarInformacionUnidad(conexion.getConnection(), listaUnidad);

// ENLAZAR LISTAS CON COMBOBOX
        cmbIdCategoria.setItems(listaCategoria);
        cmbIdPresentacion.setItems(listaPresentacion);
        cmbIdUnidad.setItems(listaUnidad);

// ENLAZAR COLUMNAS CON ATRIBUTOS
        clmnIdInsumo.setCellValueFactory(new PropertyValueFactory<Insumo, Number>("idInsumo"));
        clmnNombre.setCellValueFactory(new PropertyValueFactory<Insumo, String>("nombre"));
        clmnDescripcion.setCellValueFactory(new PropertyValueFactory<Insumo, String>("descripcion"));
        clmnCantidad.setCellValueFactory(new PropertyValueFactory<Insumo, Number>("cantidad"));
        clmnStockMin.setCellValueFactory(new PropertyValueFactory<Insumo, Number>("stockMin"));
        clmnStockMax.setCellValueFactory(new PropertyValueFactory<Insumo, Number>("stockMax"));
        clmnPrecioUnitario.setCellValueFactory(new PropertyValueFactory<Insumo, Number>("precioUnitario"));
        clmnDisponibilidad.setCellValueFactory(new PropertyValueFactory<Insumo, Number>("disponibilidad"));
        
// ENLAZAR COLUMNAS CON ATRIBUTOS COMBOBOX
        clmnIdCategoria.setCellValueFactory(new PropertyValueFactory<Insumo, Categoria>("idCategoria"));
        clmnIdPresentacion.setCellValueFactory(new PropertyValueFactory<Insumo, Presentacion>("idPresentacion"));
        clmnIdUnidad.setCellValueFactory(new PropertyValueFactory<Insumo, Unidad>("idUnidad"));
        
// TABLE VIEWS
        tblViewInsumo.setItems(listaInsumo);
        gestionarEventos();
        conexion.cerrarConexion();

    }

    public void gestionarEventos() {
        tblViewInsumo.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Insumo>() {
            @Override
            public void changed(ObservableValue<? extends Insumo> observable, Insumo valorAnterior,
                    Insumo valorSeleccionado) {
                if (valorSeleccionado != null) {
                    txtIdInsumo.setText(String.valueOf(valorSeleccionado.getIdInsumo()));
                    txtNombre.setText(String.valueOf(valorSeleccionado.getNombre()));
                    txtDescripcion.setText(valorSeleccionado.getDescripcion());                
                    //LOS COMBOBOX
                    cmbIdCategoria.setValue(valorSeleccionado.getIdCategoria());
                    cmbIdPresentacion.setValue(valorSeleccionado.getIdPresentacion());
                    cmbIdUnidad.setValue(valorSeleccionado.getIdUnidad());

                    btnGuardar.setDisable(true);
                    btnEliminar.setDisable(false);
                    btnActualizar.setDisable(false);
                }
            }
        }
        );
    }

}
