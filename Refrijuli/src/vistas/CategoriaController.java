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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import modelos.Categoria;
import modelos.Conexion;

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

// ELIMINAR CATEGORIA
    public void eliminarCategoria() {
        conexion.establecerConexion();
        int resultado = tblViewCategoria.getSelectionModel().getSelectedItem()
                .eliminarCategoria((Conexion) conexion.getConnection());
        conexion.cerrarConexion();

        if (resultado == 1) {

            listaCategoria.remove(tblViewCategoria.getSelectionModel().getSelectedIndex());

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
        listaCategoria = FXCollections.observableArrayList();

// LLENAR LISTAS
        Categoria.llenarInformacionCategoria(conexion.getConnection(), listaCategoria);

// ENLAZAR COLUMNAS CON ATRIBUTOS
        clmnIdCategoria.setCellValueFactory(new PropertyValueFactory<Categoria, Number>("idCategoria"));
        clmnNombre.setCellValueFactory(new PropertyValueFactory<Categoria, String>("nombre"));
        clmnDescripcion.setCellValueFactory(new PropertyValueFactory<Categoria, String>("descripcion"));

// TABLE VIEWS
        tblViewCategoria.setItems(listaCategoria);
        gestionarEventos();
        conexion.cerrarConexion();

    }

    public void gestionarEventos() {
        tblViewCategoria.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Categoria>() {
            @Override
            public void changed(ObservableValue<? extends Categoria> observable, Categoria valorAnterior,
                    Categoria valorSeleccionado) {
                if (valorSeleccionado != null) {
                    txtIdCategoria.setText(String.valueOf(valorSeleccionado.getIdCategoria()));
                    txtNombre.setText(String.valueOf(valorSeleccionado.getNombre()));
                    txtDescripcion.setText(String.valueOf(valorSeleccionado.getDescripcion()));

                    btnGuardar.setDisable(true);
                    btnEliminar.setDisable(false);
                    btnActualizar.setDisable(false);
                }
            }
        }
        );
    }
}
