/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistas;

import java.net.URL;
import java.sql.Date;
import java.sql.Time;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import modelos.Cliente;
import modelos.Conexion;
import modelos.Estado;
import modelos.Pedido;

/**
 * FXML Controller class
 *
 * @author JEISONANDRES
 */
public class ClienteController implements Initializable {

    @FXML
    private TextField txtIdCliente;
    @FXML
    private TextField txtPrimerNombre;
    @FXML
    private TextField txtSegundoNombre;
    @FXML
    private TextField txtPrimerApellido;
    @FXML
    private TextField txtSegundoApellido;
    @FXML
    private ComboBox cmbIdTipoDocumento;
    @FXML
    private TextField txtNumeroDocumento;
    @FXML
    private ComboBox cmbIdEstado;
    @FXML
    private TextField txtDireccion;
    @FXML
    private TextField txtTelefono;
    @FXML
    private TextField txtCelular;
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
    private TableView<Cliente> tblViewCliente;
    @FXML
    private TableColumn<Cliente, Number> clmnIdCliente;
    @FXML
    private TableColumn<Cliente, String> clmnPrimerNombre;
    @FXML
    private TableColumn<Cliente, String> clmnSegundoNombre;
    @FXML
    private TableColumn<Cliente, String> clmnDireccionEntrega;
    @FXML
    private TableColumn<Cliente, Number> clmnHoraEntrega;
    @FXML
    private TableColumn<Pedido, Cliente> clmnIdCliente;
    @FXML
    private TableColumn<Pedido, Estado> clmnIdEstado;

    @FXML
    public void limpiarCamposPedido() {

        txtIdPedido.requestFocus();
        txtIdPedido.setText("");
        txtDireccionEntrega.setText("");
        txtHoraEntrega.setText("");
        cmbIdCliente.setValue(null);
        cmbIdEstado.setValue(null);
        btnGuardar.setDisable(false);
        btnActualizar.setDisable(true);
        btnEliminar.setDisable(true);

    }

    private Conexion conexion; //INSTANCIANDO LA CLASE CONEXION
    private ObservableList<Pedido> listaPedido;
    private ObservableList<Cliente> listaCliente;
    private ObservableList<Estado> listaEstado;

// METODO AGREGAR
    @FXML
    public void agregarPedido() {
        Pedido pedido = new Pedido(
                Integer.valueOf(txtIdPedido.getText()),
                Date.valueOf(dapickeFechaEntrega.getValue()),
                Date.valueOf(dapickeFechaPedido.getValue()),
                txtDireccionEntrega.getText(),
                Time.valueOf(txtHoraEntrega.getText()),
                cmbIdCliente.getSelectionModel().getSelectedItem(),
                cmbIdEstado.getSelectionModel().getSelectedItem()
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
                Time.valueOf(txtHoraEntrega.getText()),
                cmbIdCliente.getSelectionModel().getSelectedItem(),
                cmbIdEstado.getSelectionModel().getSelectedItem()
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
        conexion.establecerConexion();
        int resultado = tblViewPedido.getSelectionModel().getSelectedItem()
                .eliminarPedido((Conexion) conexion.getConnection());
        conexion.cerrarConexion();

        if (resultado == 1) {

            listaPedido.remove(tblViewPedido.getSelectionModel().getSelectedIndex());

            Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
            mensaje.setTitle("Registro Eliminado");
            mensaje.setContentText("Registro ha sido eliminado con exito");
            mensaje.setHeaderText("Resultado:");
            mensaje.show();

        }

    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
