package vistas;

import java.net.URL;
import java.sql.Date;
import java.sql.Time;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import modelos.Cliente;
import modelos.Conexion;
import modelos.Estado;
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
    private ComboBox<Cliente> cmbIdCliente;
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
    private TableColumn<Pedido, Cliente> clmnIdCliente;
    @FXML
    private TableColumn<Pedido, Estado> clmnIdEstado;

// METODO LIMPIAR CAMPOS
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
    
// METODO ACTUALIZAR
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
        conexion = new Conexion();
        conexion.establecerConexion();

// INICIALIZAR
        listaPedido = FXCollections.observableArrayList();
        listaCliente = FXCollections.observableArrayList();
        listaEstado = FXCollections.observableArrayList();

// LLENAR LISTAS
        Pedido.llenarInformacionPedido(conexion.getConnection(), listaPedido);
        Cliente.llenarInformacionCliente(conexion.getConnection(), listaCliente);
        Estado.llenarInformacionEstado(conexion.getConnection(), listaEstado);

// ENLAZAR LISTAS CON COMBOBOX
        cmbIdCliente.setItems(listaCliente);
        cmbIdEstado.setItems(listaEstado);

// ENLAZAR COLUMNAS CON ATRIBUTOS
        clmnIdPedido.setCellValueFactory(new PropertyValueFactory<Pedido, Number>("idPedido"));
        clmnFechaEntrega.setCellValueFactory(new PropertyValueFactory<Pedido, Date>("fechaEntrega"));
        clmnFechaPedido.setCellValueFactory(new PropertyValueFactory<Pedido, Date>("fechaPedido"));
        clmnDireccionEntrega.setCellValueFactory(new PropertyValueFactory<Pedido, String>("direccionEntrega"));
        clmnHoraEntrega.setCellValueFactory(new PropertyValueFactory<Pedido, Number>("horaEntrega"));

// ENLAZAR COLUMNAS CON ATRIBUTOS COMBOBOX
        clmnIdCliente.setCellValueFactory(new PropertyValueFactory<Pedido, Cliente>("idCliente"));
        clmnIdEstado.setCellValueFactory(new PropertyValueFactory<Pedido, Estado>("idEstado"));

// TABLE VIEWS
        tblViewPedido.setItems(listaPedido);
        gestionarEventos();
        conexion.cerrarConexion();

    }

    public void gestionarEventos() {
        tblViewPedido.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Pedido>() {
            @Override
            public void changed(ObservableValue<? extends Pedido> observable, Pedido valorAnterior,
                    Pedido valorSeleccionado) {
                if (valorSeleccionado != null) {
                    txtIdPedido.setText(String.valueOf(valorSeleccionado.getIdPedido()));
                    dapickeFechaEntrega.setValue(valorSeleccionado.getFechaEntrega().toLocalDate());
                    dapickeFechaPedido.setValue(valorSeleccionado.getFechaPedido().toLocalDate());
                    txtDireccionEntrega.setText(valorSeleccionado.getDireccionEntrega());
                    txtHoraEntrega.setText(String.valueOf(valorSeleccionado.getHoraEntrega()));
                    //LOS COMBOBOX
                    cmbIdCliente.setValue(valorSeleccionado.getIdCliente());
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

