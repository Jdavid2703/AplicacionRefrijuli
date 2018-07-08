
package vistas;

import java.net.URL;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import modelos.Pedido;


public class PedidoController implements Initializable {

  
     @FXML
    private TextField txtIdPedido;
    @FXML
    private DatePicker dapickeFechaEntrega;
    @FXML
    private DatePicker dapickeFechaPedido;
    @FXML
    private TextField txtDireccionEntrega;
    @FXML
    private TextField txtHoraEntrega;
    @FXML
    private TextField txtIdCliente;
    @FXML
    private TextField txtIdEstado;
    @FXML
    private Button btnNuevo;
    @FXML
    private Button btnActualizar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnGuardar;

    //TABLAS Y COLUMNAS
    @FXML
    private TableView<Pedido> tblViewPedido;
    @FXML
    private TableColumn<Pedido, Number> clmnIdPedido;
    @FXML
    private TableColumn<Pedido, Date> clmnFechaEntrega;
    @FXML
    private TableColumn<Pedido, Date> clmnFechaPedido;
    @FXML
    private TableColumn<Pedido, String> clmnDireccionEntrega;
    @FXML
    private TableColumn<Pedido, Number> clmnHoraEntrega;
    @FXML
    private TableColumn<Pedido, Number> clmnTotalMonto;
    @FXML
    private TableColumn<Pedido, Cliente> clmnIdCliente;
    @FXML
    private TableColumn<Pedido, Number> clmnIdEstado;


    @FXML
    public void limpiarCamposPedido() {

        txtIdPedido.requestFocus();
        txtIdPedido.setText("");
        txtDireccionEntrega.setText("");
        txtHoraEntrega.setText("");
        txtIdCliente.setText("");
        txtIdEstado.setText("");
        btnGuardar.setDisable(false);   
        btnActualizar.setDisable(true);
        btnEliminar.setDisable(true);

    }

    private Conexion conexion; //INSTANCIANDO LA CLASE CONEXION
    private ObservableList<Pedido> listaPedido;

    @FXML
    public void agregarPedido() {
        Pedido pedido = new Pedido(
                Integer.valueOf(txtIdPedido.getText()),
                Date.valueOf(dapickeFechaEntrega.getValue()),
                Date.valueOf(dapickeFechaPedido.getValue()),
                txtDireccionEntrega.getText(),
                Integer.valueOf(txtHoraEntrega.getText()),
                Integer.valueOf(txtIdCliente.getText()),
                Integer.valueOf(txtIdEstado.getText())
          
        );

        conexion.establecerConexion();
        int resultado = pedido.guardarPedido(conexion);
        conexion.cerrarConexion();

        if (resultado == 1) {
            listaPedido.add(pedido);

        }

    }

    @FXML
    public void actualizarPedido() {
        Pedido pedido = new Pedido(
                Integer.valueOf(txtIdPedido.getText()),
                Date.valueOf(dapickeFechaEntrega.getValue()),
                Date.valueOf(dapickeFechaPedido.getValue()),
                txtDireccionEntrega.getText(),
                Integer.valueOf(txtHoraEntrega.getText()),
                Integer.valueOf(txtIdCliente.getText()),
                Integer.valueOf(txtIdEstado.getText())
    
        );

        conexion.establecerConexion();
        int resultado = pedido.actualizarPedido(conexion);
        conexion.cerrarConexion();

        if (resultado == 1) {
            listaPedido.set(
                    tblViewPedido.getSelectionModel().getSelectedIndex(),
                    pedido
            );

            limpiarCamposPedido();

        }
    }

    @FXML
    public void eliminarPedido() {
        Alert cuadroDialogoConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        cuadroDialogoConfirmacion.setTitle("Confirmacion");
        cuadroDialogoConfirmacion.setHeaderText("Eliminar Registro");
        cuadroDialogoConfirmacion.setContentText("¿Está Seguro de Eliminar el Registro?");
        Optional<ButtonType> resultado = cuadroDialogoConfirmacion.showAndWait();
        if (resultado.get() == ButtonType.OK) {
            Pedido pedido = new Pedido();
            pedido.setIdPedido(Integer.valueOf(txtIdPedido.getText()));
            conexion.establecerConexion();
            int r = pedido.eliminarPedido(conexion);
            conexion.cerrarConexion();

            if (r == 1) {
                listaPedido.remove(tblViewPedido.getSelectionModel().getSelectedIndex());
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
    
    }    
    
}
