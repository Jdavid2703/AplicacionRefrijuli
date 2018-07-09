package views;


import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
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

import model.Categoria;
import model.Conexion;
import model.Insumo;
import model.Presentacion;
import model.Unidad;


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
     private TextField txtPrecioUnidad;
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
     private TableView<Insumo>tblViewInsumo;
     @FXML
     private TableColumn<Insumo, Number>clmnIdInsumo;
     @FXML
     private TableColumn<Insumo, String>clmnNombre;
     @FXML
     private TableColumn<Insumo, String>clmnDescripcion;
     @FXML
     private TableColumn<Insumo, Number>clmnCantidad;
     @FXML
     private TableColumn<Insumo, Categoria>clmnIdCategoria;
     @FXML
     private TableColumn<Insumo, Number>clmnStockMin;
     @FXML
     private TableColumn<Insumo, Number>clmnStockMax;
     @FXML
     private TableColumn<Insumo, Presentacion>clmnIdPresentacion;
     @FXML
     private TableColumn<Insumo, Unidad>clmnIdUnidad;
     @FXML
     private TableColumn<Insumo, Number>clmnPrecioUnidad;
     @FXML
     private TableColumn<Insumo, Number>clmnDisponibilidad;
     
     
     @FXML
    public void limpiarCamposNuevoRegistro() {
        
        txtIdInsumo.setText("null");
        txtNombre.setText("null");
        txtDescripcion.setText("null");
        txtCantidad.setText("null");
        cmbIdCategoria.setValue("null");
        txtStockMin.setText("");
        txtStockMax.setText("");
        cmbIdPresentacion.setValue("null");
        cmbIdUnidad.setValue("null");
        txtPrecioUnidad.setText("null");
        txtDisponibilidad.setText("null");
        btnGuardar.setDisable(false);
        btnActualizar.setDisable(true);
        btnEliminar.setDisable(true);
        txtIdInsumo.requestFocus();

    }
private Conexion conexion;
private ObservableList<Insumo> listaInsumo;
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
                 Integer.valueOf(txtPrecioUnidad.getText()),
                Integer.valueOf(txtPrecioUnidad.getText()));
               
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
                Integer.valueOf(txtPrecioUnidad.getText()),
                Integer.valueOf(txtPrecioUnidad.getText()));
        
                
       
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
    public void initialize(URL location, ResourceBundle resources) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
     
}            
