package vistas;


import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import modelos.Conexion;
import modelos.Estado;
import modelos.Producto;

public class ProductoController implements Initializable {

    @FXML
    private TextField txtidProducto;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextArea txtDescripcion;
    @FXML
    private TextField txtPrecio;
    @FXML
    private ComboBox<Estado> cmbIdEstado;
    @FXML
    private Button btnNuevo;
    @FXML
    private Button btnActualizar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnGuardar;
    @FXML
    private TableView<Producto> tblViewProducto;
    @FXML
    private TableColumn<Producto, Number> clmnIdProducto;
    @FXML
    private TableColumn<Producto, String> clmnNombre;
    @FXML
    private TableColumn<Producto, String> clmnDescripcion;
    @FXML
    private TableColumn<Producto, Number> clmnPrecio;
    @FXML
    private TableColumn<Producto, Estado> clmnIdEstado;

    @FXML
    public void limpiarCamposProducto() {

        txtidProducto.setText("");
        txtNombre.setText("");
        txtDescripcion.setText("");
        btnGuardar.setDisable(false);
        txtPrecio.setText("");
        cmbIdEstado.setValue(null);
        btnActualizar.setDisable(true);
        btnEliminar.setDisable(true);
        txtidProducto.requestFocus();

    }
    private Conexion conexion;//Instanciando la conexion
    private ObservableList<Producto> listaProducto;
    private ObservableList<Estado> listaEstado;

    public void agregarProducto() {
        Producto producto = new Producto(
                Integer.valueOf(txtidProducto.getText()),
                txtNombre.getText(),
                txtDescripcion.getText(),
                Integer.valueOf(txtPrecio.getText()),
                cmbIdEstado.getSelectionModel().getSelectedItem()
        );

        conexion.establecerConexion();
        int resultado = producto.guardarProducto(conexion);
        conexion.cerrarConexion();

        if (resultado == 1) {
            listaProducto.add(producto);
        }
    }

    public void actualizarProducto() {
        Producto producto = new Producto(
                Integer.valueOf(txtidProducto.getText()),
                String.valueOf(txtNombre.getText()),
                String.valueOf(txtDescripcion.getText()),
                Integer.valueOf(txtPrecio.getText()),
                cmbIdEstado.getSelectionModel().getSelectedItem()
        );

        conexion.establecerConexion();
        int resultado = producto.actualizarProducto(conexion);
        conexion.cerrarConexion();

        if (resultado == 1) {
            listaProducto.set(
                    tblViewProducto.getSelectionModel().getSelectedIndex(), producto);

            limpiarCamposProducto();

        }
    }

    @FXML
    public void eliminarPedido() {
        conexion.establecerConexion();
        int resultado = tblViewProducto.getSelectionModel().getSelectedItem()
                .eliminarProducto((Conexion) conexion.getConnection());
        conexion.cerrarConexion();

        if (resultado == 1) {

            listaProducto.remove(tblViewProducto.getSelectionModel().getSelectedIndex());

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
        listaProducto = FXCollections.observableArrayList();
        listaEstado = FXCollections.observableArrayList();
// LLENAR LISTAS
        Producto.llenarInformacionProducto(conexion.getConnection(), listaProducto);
        Estado.llenarInformacionEstado(conexion.getConnection(), listaEstado);
// ENLAZAR LISTAS CON COMBOBOX
        cmbIdEstado.setItems(listaEstado);
// ENLAZAR COLUMNAS CON ATRIBUTOS
        clmnIdProducto.setCellValueFactory(new PropertyValueFactory<Producto, Number>("idProducto"));
        clmnNombre.setCellValueFactory(new PropertyValueFactory<Producto, String>("nombre"));
        clmnDescripcion.setCellValueFactory(new PropertyValueFactory<Producto, String>("descripcion"));
        clmnPrecio.setCellValueFactory(new PropertyValueFactory<Producto, Number>("precio"));
// ENLAZAR COLUMNAS CON ATRIBUTOS COMBOBOX
       clmnIdEstado.setCellValueFactory(new PropertyValueFactory<Producto, Estado>("idEstado"));
// TABLE VIEWS
        tblViewProducto.setItems(listaProducto);
        gestionarEventos();
        conexion.cerrarConexion();
    }

    private void gestionarEventos() {
     tblViewProducto.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Producto>() {
            @Override
            public void changed(ObservableValue<? extends Producto> observable, Producto valorAnterior,
                    Producto valorSeleccionado) {
                if (valorSeleccionado != null) {
                    txtidProducto.setText(String.valueOf(valorSeleccionado.getIdProducto()));
                    txtNombre.setText(String.valueOf(valorSeleccionado.getNombre()));
                    txtDescripcion.setText(String.valueOf(valorSeleccionado.getDescripcion()));
                    txtPrecio.setText(String.valueOf(valorSeleccionado.getPrecio()));
                    //LOS COMBOBOX
                    cmbIdEstado.setValue(valorSeleccionado.getIdEstado());

                    btnGuardar.setDisable(true);
                    btnEliminar.setDisable(false);
                    btnActualizar.setDisable(false);
                }
            }
        }
        );
    }
   

}
