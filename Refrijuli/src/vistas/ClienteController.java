/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import modelos.Cliente;
import modelos.Conexion;
import modelos.Estado;
import modelos.TipoDocumento;


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
    private ComboBox<TipoDocumento> cmbIdTipoDocumento;
    @FXML
    private TextField txtNumeroDocumento;
    @FXML
    private ComboBox<Estado> cmbIdEstado;
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
    private TableColumn<Cliente, String> clmnPrimerApellido;
    @FXML
    private TableColumn<Cliente, String> clmnSegundoApellido;
    @FXML
    private TableColumn<Cliente, TipoDocumento> clmnIdTipoDocumento;
    @FXML
    private TableColumn<Cliente, Number> clmnNumeroDocumento;
    @FXML
    private TableColumn<Cliente, Estado> clmnIdEstado;
    @FXML
    private TableColumn<Cliente, String> clmnDireccion;
    @FXML
    private TableColumn<Cliente, Number> clmnTelefono;
    @FXML
    private TableColumn<Cliente, Number> clmnCelular;

    @FXML
    public void limpiarCamposCliente() {

        txtIdCliente.requestFocus();
        txtIdCliente.setText("");
        txtPrimerNombre.setText("");
        txtSegundoNombre.setText("");
        txtPrimerApellido.setText("");
        txtSegundoApellido.setText("");
        cmbIdTipoDocumento.setValue(null);
        txtNumeroDocumento.setText("");
        cmbIdEstado.setValue(null);
        txtDireccion.setText("");
        txtTelefono.setText("");
        txtCelular.setText("");
        btnGuardar.setDisable(false);
        btnActualizar.setDisable(true);
        btnEliminar.setDisable(true);

    }

    private Conexion conexion; //INSTANCIANDO LA CLASE CONEXION
    private ObservableList<Cliente> listaCliente;
    private ObservableList<TipoDocumento> listaTipoDocumento;
    private ObservableList<Estado> listaEstado;

// METODO AGREGAR
    @FXML
    public void agregarCliente() {
        Cliente Cliente = new Cliente(
                Integer.valueOf(txtIdCliente.getText()),
                txtPrimerNombre.getText(),
                txtSegundoNombre.getText(),
                txtPrimerApellido.getText(),
                txtSegundoApellido.getText(),
                cmbIdTipoDocumento.getSelectionModel().getSelectedItem(),
                Integer.valueOf(txtNumeroDocumento.getText()),
                cmbIdEstado.getSelectionModel().getSelectedItem(),
                txtDireccion.getText(),
                Integer.valueOf(txtTelefono.getText()),
                Integer.valueOf(txtCelular.getText())
        );

        conexion.establecerConexion();
        int resultado = Cliente.guardarCliente(conexion);
        conexion.cerrarConexion();

        if (resultado == 1) {
            listaCliente.add(Cliente);

        }

    }

    @FXML
    public void actualizarCliente() {
        Cliente Cliente = new Cliente(
                Integer.valueOf(txtIdCliente.getText()),
                txtPrimerNombre.getText(),
                txtSegundoNombre.getText(),
                txtPrimerApellido.getText(),
                txtSegundoApellido.getText(),
                cmbIdTipoDocumento.getSelectionModel().getSelectedItem(),
                Integer.valueOf(txtNumeroDocumento.getText()),
                cmbIdEstado.getSelectionModel().getSelectedItem(),
                txtDireccion.getText(),
                Integer.valueOf(txtTelefono.getText()),
                Integer.valueOf(txtCelular.getText())
        );

        conexion.establecerConexion();
        int resultado = Cliente.actualizarCliente(conexion);
        conexion.cerrarConexion();

        if (resultado == 1) {
            listaCliente.set(
                    tblViewCliente.getSelectionModel().getSelectedIndex(),
                    Cliente
            );

            limpiarCamposCliente();

        }
    }

    @FXML
    public void eliminarPedido() {
        conexion.establecerConexion();
        int resultado = tblViewCliente.getSelectionModel().getSelectedItem()
                .eliminarCliente((Conexion) conexion.getConnection());
        conexion.cerrarConexion();

        if (resultado == 1) {

            listaCliente.remove(tblViewCliente.getSelectionModel().getSelectedIndex());

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
        listaCliente = FXCollections.observableArrayList();
        listaTipoDocumento = FXCollections.observableArrayList();
        listaEstado = FXCollections.observableArrayList();

// LLENAR LISTAS
        Cliente.llenarInformacionCliente(conexion.getConnection(), listaCliente);
        TipoDocumento.llenarInformacionTipoDocumento(conexion.getConnection(), listaTipoDocumento);
        Estado.llenarInformacionEstado(conexion.getConnection(), listaEstado);

// ENLAZAR LISTAS CON COMBOBOX
        cmbIdTipoDocumento.setItems(listaTipoDocumento);
        cmbIdEstado.setItems(listaEstado);

// ENLAZAR COLUMNAS CON ATRIBUTOS
        clmnIdCliente.setCellValueFactory(new PropertyValueFactory<Cliente, Number>("idCliente"));
        clmnPrimerNombre.setCellValueFactory(new PropertyValueFactory<Cliente, String>("primerNombre"));
        clmnSegundoNombre.setCellValueFactory(new PropertyValueFactory<Cliente, String>("segundoNombre"));
        clmnPrimerApellido.setCellValueFactory(new PropertyValueFactory<Cliente, String>("primerApellido"));
        clmnSegundoApellido.setCellValueFactory(new PropertyValueFactory<Cliente, String>("segundoApellido"));
        clmnNumeroDocumento.setCellValueFactory(new PropertyValueFactory<Cliente, Number>("numeroDocumento"));
        clmnDireccion.setCellValueFactory(new PropertyValueFactory<Cliente, String>("direccion"));
        clmnTelefono.setCellValueFactory(new PropertyValueFactory<Cliente, Number>("telefono"));
        clmnCelular.setCellValueFactory(new PropertyValueFactory<Cliente, Number>("celular"));

// ENLAZAR COLUMNAS CON ATRIBUTOS COMBOBOX
        clmnIdTipoDocumento.setCellValueFactory(new PropertyValueFactory<Cliente, TipoDocumento>("idTipoDocumento"));
        clmnIdEstado.setCellValueFactory(new PropertyValueFactory<Cliente, Estado>("idEstado"));

// TABLE VIEWS
        tblViewCliente.setItems(listaCliente);
        gestionarEventos();
        conexion.cerrarConexion();

    }

    public void gestionarEventos() {
        tblViewCliente.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Cliente>() {
            @Override
            public void changed(ObservableValue<? extends Cliente> observable, Cliente valorAnterior,
                    Cliente valorSeleccionado) {
                if (valorSeleccionado != null) {
                    txtIdCliente.setText(String.valueOf(valorSeleccionado.getIdCliente()));
                    txtPrimerNombre.setText(String.valueOf(valorSeleccionado.getPrimerNombre()));
                    txtSegundoNombre.setText(String.valueOf(valorSeleccionado.getSegundoNombre()));
                    txtPrimerApellido.setText(String.valueOf(valorSeleccionado.getPrimerApellido()));
                    txtSegundoApellido.setText(String.valueOf(valorSeleccionado.getSegundoApellido()));
                    txtNumeroDocumento.setText(String.valueOf(valorSeleccionado.getNumeroDocumento()));
                    txtDireccion.setText(String.valueOf(valorSeleccionado.getDireccion()));
                    txtTelefono.setText(String.valueOf(valorSeleccionado.getTelefono()));
                    txtCelular.setText(String.valueOf(valorSeleccionado.getCelular()));
                    //LOS COMBOBOX
                    cmbIdTipoDocumento.setValue(valorSeleccionado.getIdTipoDocumento());
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
