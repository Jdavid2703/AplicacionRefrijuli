package vistas;

import java.net.URL;
import java.sql.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import modelos.Categoria;
import modelos.Conexion;
import modelos.Pedido;

public class CategoriaController implements Initializable {

    @FXML
    private TextField txtIdCategoria;
    @FXML
    private TextField txtNombre;
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
    private TableView<Categoria> tblViewCategoria;
    @FXML
    private TableColumn<Categoria, Number> clmnIdCategoria;
    @FXML
    private TableColumn<Categoria, String> clmnNombre;
    @FXML
    private TableColumn<Categoria, String> clmnDescripcion;

    @FXML
    //metodo limpiar campos
    public void limpiarCamposNuevoRegistro() {

        txtIdCategoria.requestFocus();
        txtIdCategoria.setText("");
        txtNombre.setText("");
        txtDescripcion.setText("");
        btnGuardar.setDisable(false);
        btnEliminar.setDisable(true);
        btnActualizar.setDisable(true);

    }

    private Conexion conexion;//Instanciando la conexion
    private ObservableList<Categoria> listaCategoria;
//AGREGAR CATEGORIA

    public void agregarCategoria() {
        Categoria categoria = new Categoria(
                Integer.valueOf(txtIdCategoria.getText()),
                txtNombre.getText(),
                txtDescripcion.getText());

        conexion.establecerConexion();
        int resultado = categoria.guardarCategoria(conexion);
        conexion.cerrarConexion();

        if (resultado == 1) {
            listaCategoria.add(categoria);
        }
    }

    // ACTUALIZAR CATEGORIA
    public void actualizarCategoria() {
        Categoria categoria = new Categoria(
                Integer.valueOf(txtIdCategoria.getText()),
                txtNombre.getText(),
                txtDescripcion.getText());

        conexion.establecerConexion();
        int resultado = categoria.actualizarCategoria(conexion);
        conexion.cerrarConexion();

        if (resultado == 1) {
            listaCategoria.set(
                    tblViewCategoria.getSelectionModel().getSelectedIndex(),
                    categoria
            );
        }
    }

    //Eliminar categoria
    public void eliminarCategoria() {
        Alert cuadroDialogoConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        cuadroDialogoConfirmacion.setTitle("Confirmacion");
        cuadroDialogoConfirmacion.setHeaderText("Eliminar Registro");
        cuadroDialogoConfirmacion.setContentText("¿Está Seguro de Eliminar el Registro?");
        Optional<ButtonType> resultado = cuadroDialogoConfirmacion.showAndWait();
        if (resultado.get() == ButtonType.OK) {
            Categoria categoria = new Categoria();
            categoria.setIdCategoria(Integer.valueOf(txtIdCategoria.getText()));
            conexion.establecerConexion();
            int r = categoria.eliminarCategoria(conexion);
            conexion.cerrarConexion();

            if (r == 1) {
                listaCategoria.remove(tblViewCategoria.getSelectionModel().getSelectedIndex());
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
    }
}
