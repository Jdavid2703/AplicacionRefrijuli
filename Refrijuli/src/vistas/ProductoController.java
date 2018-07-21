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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import modelos.Conexion;
import modelos.EstadoProducto;
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
    private ComboBox<EstadoProducto> cmbIdEstadoProducto;
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
    private TableColumn<Producto, EstadoProducto> clmnIdEstadoProducto;

    @FXML
    public void limpiarCamposProducto() {

        txtidProducto.setText("");
        txtNombre.setText("");
        txtDescripcion.setText("");
        btnGuardar.setDisable(false);
        txtPrecio.setText("");
        cmbIdEstadoProducto.setValue(null);
        btnActualizar.setDisable(true);
        btnEliminar.setDisable(true);
        txtidProducto.requestFocus();
        txtidProducto.setDisable(false);

    }
    private Conexion conexion;//Instanciando la conexion
    private ObservableList<Producto> listaProducto;
    private ObservableList<EstadoProducto> listaEstadoProducto;

    public void agregarProducto() {
        Producto producto = new Producto(
                Integer.valueOf(txtidProducto.getText()),
                txtNombre.getText(),
                txtDescripcion.getText(),
                Integer.valueOf(txtPrecio.getText()),
                cmbIdEstadoProducto.getSelectionModel().getSelectedItem()
        );

        conexion.establecerConexion();
        int resultado = producto.guardarProducto(conexion);
        conexion.cerrarConexion();

        if (resultado == 1) {
            listaProducto.add(producto);
            
            Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
            mensaje.setTitle("Registro agregado");
            mensaje.setContentText("Registro ha sido agregado con exíto");
            mensaje.setHeaderText("Resultado:");
            mensaje.show();

            limpiarCamposProducto();
        }
    }

    public void actualizarProducto() {
        Producto producto = new Producto(
                Integer.valueOf(txtidProducto.getText()),
                String.valueOf(txtNombre.getText()),
                String.valueOf(txtDescripcion.getText()),
                Integer.valueOf(txtPrecio.getText()),
                cmbIdEstadoProducto.getSelectionModel().getSelectedItem()
        );

        conexion.establecerConexion();
        int resultado = producto.actualizarProducto(conexion);
        conexion.cerrarConexion();

        if (resultado == 1) {
            listaProducto.set(tblViewProducto.getSelectionModel().getSelectedIndex(), producto);

            Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
            mensaje.setTitle("Registro Actualizado");
            mensaje.setContentText("Registro ha sido actualizado con exito");
            mensaje.setHeaderText("Resultado:");
            mensaje.show();
        
            limpiarCamposProducto();

        }
    }

    @FXML
    public void eliminarProducto() {
        Alert cuadroDialogoConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        cuadroDialogoConfirmacion.setTitle("Confirmación");
        cuadroDialogoConfirmacion.setHeaderText("Eliminar Registro");
        cuadroDialogoConfirmacion.setContentText("¿Está Seguro de Eliminar el Registro?");
        Optional<ButtonType> resultado = cuadroDialogoConfirmacion.showAndWait();
        if (resultado.get() == ButtonType.OK) {
            Producto producto = new Producto();
            producto.setIdProducto(Integer.valueOf(txtidProducto.getText()));
            conexion.establecerConexion();
            int r = producto.eliminarProducto(conexion);
            conexion.cerrarConexion();

            if (r == 1) {
                listaProducto.remove(tblViewProducto.getSelectionModel().getSelectedIndex());

                Alert cuadroDialogo = new Alert(Alert.AlertType.INFORMATION);
                cuadroDialogo.setContentText("Registro Eliminado con Éxito");
                cuadroDialogo.setTitle("Registro Eliminado");
                cuadroDialogo.setHeaderText("Resultado: ");
                cuadroDialogo.showAndWait();

                limpiarCamposProducto();
            }

        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        conexion = new Conexion();
        conexion.establecerConexion();
        
// INICIALIZAR
        listaProducto = FXCollections.observableArrayList();
        listaEstadoProducto = FXCollections.observableArrayList();
        
// LLENAR LISTAS
        Producto.llenarInformacionProducto(conexion.getConnection(), listaProducto);
        EstadoProducto.llenarInformacionEstadoProducto(conexion.getConnection(), listaEstadoProducto);
        
// ENLAZAR LISTAS CON COMBOBOX
        cmbIdEstadoProducto.setItems(listaEstadoProducto);
        
// ENLAZAR COLUMNAS CON ATRIBUTOS
        clmnIdProducto.setCellValueFactory(new PropertyValueFactory<Producto, Number>("idProducto"));
        clmnNombre.setCellValueFactory(new PropertyValueFactory<Producto, String>("nombre"));
        clmnDescripcion.setCellValueFactory(new PropertyValueFactory<Producto, String>("descripcion"));
        clmnPrecio.setCellValueFactory(new PropertyValueFactory<Producto, Number>("precio"));
// ENLAZAR COLUMNAS CON ATRIBUTOS COMBOBOX

       clmnIdEstadoProducto.setCellValueFactory(new PropertyValueFactory<Producto, EstadoProducto>("idEstadoProducto"));
       
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
                    cmbIdEstadoProducto.setValue(valorSeleccionado.getIdEstadoProducto());

                    btnGuardar.setDisable(true);
                    btnEliminar.setDisable(false);
                    btnActualizar.setDisable(false);
                    txtidProducto.setDisable(true);
                }
            }
        }
        );
    }
   

}
