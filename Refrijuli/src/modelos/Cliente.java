/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

/**
 *
 * @author JEISONANDRES
 */
public class Cliente {
    
    private IntegerProperty idCliente;
    private StringProperty primerNombre;
    private StringProperty segundoNombre;
    private StringProperty primerApellido;
    private StringProperty segundoApellido;
    private TipoDocumento idTipoDocumento;
    private IntegerProperty numeroDocumento;
    private Estado idEstado;
    private StringProperty direccion;
    private IntegerProperty telefono;
    private IntegerProperty celular;
    
     public Cliente(
     Integer idCliente,
     String primerNombre,
     String segundoNombre,
     String primerApellido,
     String segundoApellido,
     TipoDocumento idTipoDocumento,
     Integer numeroDocumento,
     Estado idEstado,
     String direccion,
     Integer telefono,
     Integer celular) {
        this.idCliente = new SimpleIntegerProperty(idCliente);
        this.primerNombre = new SimpleStringProperty(primerNombre);
        this.segundoNombre = new SimpleStringProperty(segundoNombre);
        this.primerApellido = new SimpleStringProperty(primerApellido);
        this.segundoApellido = new SimpleStringProperty(segundoApellido);
        this.idTipoDocumento = idTipoDocumento;
        this.numeroDocumento = new SimpleIntegerProperty(numeroDocumento);
        this.idEstado = idEstado;
        this.direccion = new SimpleStringProperty(direccion);
        this.telefono = new SimpleIntegerProperty(telefono);
        this.celular = new SimpleIntegerProperty(celular);

    }
    
     //GET Y SET PEDIDO
    public Integer getIdCliente() {
        return idCliente.get();
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = new SimpleIntegerProperty(idCliente);
    }

    public IntegerProperty idClienteProperty() {
        return idCliente;
    }

//GET Y SET FECHA ENTREGA
    public String getPrimerNombre() {
        return primerNombre.get();
    }

    public void setPrimerNombre (String primerNombre) {
        this.primerNombre = new SimpleStringProperty(primerNombre) ;
    }

    public String primerNombreProperty() {
        return primerNombre.get();
    }

//GET Y SET FECHA PEDIDO
    public String getSegundoNombre() {
        return segundoNombre.get();
    }

    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre =  new SimpleStringProperty(segundoNombre);
    }

    public String segundoNombreProperty() {
        return segundoNombre.get();
    }

//GET Y SET DIRECCIÓN
    public String getPrimerApellido() {
        return primerApellido.get();
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = new SimpleStringProperty(primerApellido);
    }

    public StringProperty primerApellidoProperty() {
        return primerApellido;
    }

//GET Y SET HORA ENTREGA
    public String getSegundoApellido() {
        return segundoApellido.get();
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = new SimpleStringProperty(segundoApellido);
    }

    public StringProperty segundoApellidoProperty() {
        return segundoApellido;
    }

//GET Y SET CLIENTE
    public TipoDocumento getIdTipoDocumento() {
        return idTipoDocumento.get();
    }

    public void setIdTipoDocumento(TipoDocumento idTipoDocumento) {
        this.idTipoDocumento = idTipoDocumento;
    }

    public TipoDocumento idTipoDocumentoProperty() {
        return idTipoDocumento;
    }

    //nombre documento
     public Integer getNumeroDocumento() {
        return numeroDocumento.get();
    }

    public void setNumeroDocumento(Integer numeroDocumento) {
        this.numeroDocumento = new SimpleIntegerProperty(numeroDocumento);
    }

    public IntegerProperty numeroDocumentoProperty() {
        return numeroDocumento;
    }
     
    
//GET Y SET ESTADO
    public Estado getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Estado idEstado) {
        this.idEstado = idEstado;
    }

    public Estado idEstadoProperty() {
        return idEstado;
    }
    
//GET Y SET ESTADO
     public String getDireccion() {
        return direccion.get();
    }

    public void setDireccion(String direccion) {
        this.direccion = new SimpleStringProperty(direccion);
    }

    public StringProperty direccionProperty() {
        return direccion;
    }
     
//GET Y SET ESTADO
    public Integer getTelefono() {
        return telefono.get();
    }

    public void setTelefono(Integer telefono) {
        this.telefono = new SimpleIntegerProperty(telefono);
    }

    public IntegerProperty telefonoProperty() {
        return telefono;
    }
    
    //GET Y SET ESTADO
    public Integer getCelular() {
        return celular.get();
    }

    public void setCelular(Integer celular) {
        this.celular = new SimpleIntegerProperty(celular);
    }

    public IntegerProperty celularProperty() {
        return celular;
    }

    
     public static void llenarInformacionCliente(Connection connection,
            ObservableList<Cliente> listaCliente) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultado = statement.executeQuery(
                    "SELECT idCliente, "
                    + "primerNombre, "
                    + "segundoNombre, "
                    + "primerApellido, "
                    + "segundoApellido, "
                    + "idTipoDocumento, "
                    + "numeroDocumento, "
                    + "idEstado "
                    + "direccion "
                    + "telefono "
                    + "celular "
                    + "FROM Cliente"
            );

            while (resultado.next()) {
                listaCliente.add(
                        new Cliente(
                                resultado.getInt("idCliente"),
                                resultado.getDate("primerNombre"),
                                resultado.getDate("segundoNombre"),
                                resultado.getString("primerApellido"),
                                resultado.getDate("segundoApellido"),
                                new TipoDocumento(
                                        resultado.getInt("id"),
                                        resultado.getString("nombre"),
                                        resultado.getString("descripcion")),
                                 resultado.getInt("numeroDocumento"),
                                        new Estado(
                                                resultado.getInt("idEstado"),
                                                resultado.getString("nombre"),
                                                resultado.getString("Descripcion")),
                                        resultado.getString("direccion"),
                                        resultado.getInt("telefono"),
                                        resultado.getInt("celular")
                                
                        ));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    
    
}
