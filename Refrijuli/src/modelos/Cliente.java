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
    
    private IntegerProperty idcliente;
    private StringProperty primerNombre;
    private StringProperty segundoNombre;
    private StringProperty primerApellido;
    private StringProperty segundoApellido;
    private TipoDocumento idTipoDocumento;
    private Estado idEstado;
    private StringProperty direccion;
    private IntegerProperty telefono;
    private IntegerProperty celular;
    
     public Cliente(
     Integer idcliente,
     String primerNombre,
     String segundoNombre,
     String primerApellido,
     String segundoApellido,
     TipoDocumento idTipoDocumento,
     Estado idEstado,
     String direccion,
     Integer telefono,
     Integer celular) {
        this.idcliente = new SimpleIntegerProperty(idcliente);
        this.primerNombre = new SimpleStringProperty(primerNombre);
        this.segundoNombre = new SimpleStringProperty(segundoNombre);
        this.primerApellido = new SimpleStringProperty(primerApellido);
        this.segundoApellido = new SimpleStringProperty(segundoApellido);
        this.idTipoDocumento = idTipoDocumento;
        this.idEstado = idEstado;
        this.direccion = new SimpleStringProperty(direccion);
        this.telefono = new SimpleIntegerProperty(telefono);
        this.celular = new SimpleIntegerProperty(celular);

    }
    
     //GET Y SET PEDIDO
    public Integer getIdCliente() {
        return idcliente.get();
    }

    public void setIdCliente(Integer idcliente) {
        this.idcliente = new SimpleIntegerProperty(idcliente);
    }

    public IntegerProperty idClienteProperty() {
        return idcliente;
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
                    "SELECT idcliente, "
                    + "primerNombre, "
                    + "segundoNombre, "
                    + "primerApellido, "
                    + "segundoApellido, "
                    + "idTipoDocumento, "
                    + "idEstado "
                    + "direccion "
                    + "telefono "
                    + "celular "
                    + "FROM Cliente"
            );

            while (resultado.next()) {
                listaCliente.add(
                        new Cliente(
                                resultado.getInt("idcliente"),
                                resultado.getDate("primerNombre"),
                                resultado.getDate("segundoNombre"),
                                resultado.getString("primerApellido"),
                                resultado.getDate("segundoApellido"),
                                new TipoDocumento(
                                        resultado.getInt("id"),
                                        resultado.getString("nombre"),
                                        resultado.getString("descripcion"),
                                        new TipoDocumento(
                                                resultado.getInt("idTipoDocumento"),
                                                resultado.getString("nombreTipoDocumento")),
                                        resultado.getInt("numeroDocumento"),
                                        new Estado(
                                                resultado.getInt("idEstado"),
                                                resultado.getString("nombreEstado"),
                                                resultado.getString("Descripcion")),
                                        resultado.getString("direccion"),
                                        new Municipio(
                                                resultado.getInt("idMunicipio"),
                                                resultado.getString("nombreMunicipio")),
                                        resultado.getInt("telefono"),
                                        resultado.getInt("celular")
                                )
                        ));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    
    
}
