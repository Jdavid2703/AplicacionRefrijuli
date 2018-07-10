package modelos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

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

//GET Y SET ID CLIENTE
    public Integer getIdCliente() {
        return idCliente.get();
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = new SimpleIntegerProperty(idCliente);
    }

    public IntegerProperty idClienteProperty() {
        return idCliente;
    }
    
//GET Y SET PRIMER NOMBRE

    public String getPrimerNombre() {
        return primerNombre.get();
    }

    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = new SimpleStringProperty(primerNombre);
    }

    public StringProperty primerNombreProperty() {
        return primerNombre;
    }

//GET Y SET SEGUNDO NOMBRE
    public String getSegundoNombre() {
        return segundoNombre.get();
    }

    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = new SimpleStringProperty(segundoNombre);
    }

    public StringProperty segundoNombreProperty() {
        return segundoNombre;
    }

//GET Y SET PRIMER APELLIDO

    public String getPrimerApellido() {
        return primerApellido.get();
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = new SimpleStringProperty(primerApellido);
    }

    public StringProperty primerApellidoProperty() {
        return primerApellido;
    }

//GET Y SET SEGUNDO APELLIDO
    public String getSegundoApellido() {
        return segundoApellido.get();
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = new SimpleStringProperty(segundoApellido);
    }

    public StringProperty segundoApellidoProperty() {
        return segundoApellido;
    }

//GET Y SET ID TIPO DOCUMENTO
    public TipoDocumento getIdTipoDocumento() {
        return idTipoDocumento;
    }

    public void setIdTipoDocumento(TipoDocumento idTipoDocumento) {
        this.idTipoDocumento = idTipoDocumento;
    }

    public TipoDocumento idTipoDocumentoProperty() {
        return idTipoDocumento;
    }

//GET Y SET NUMERO DOCUMENTO
    public Integer getNumeroDocumento() {
        return numeroDocumento.get();
    }

    public void setNumeroDocumento(Integer numeroDocumento) {
        this.numeroDocumento = new SimpleIntegerProperty(numeroDocumento);
    }

    public IntegerProperty numeroDocumentoProperty() {
        return numeroDocumento;
    }

//GET Y SET ID ESTADO
    public Estado getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Estado idEstado) {
        this.idEstado = idEstado;
    }

    public Estado idEstadoProperty() {
        return idEstado;
    }
    
//GET Y SET DIRECCION
    public String getDireccion() {
        return direccion.get();
    }

    public void setDireccion(String direccion) {
        this.direccion = new SimpleStringProperty(direccion);
    }

    public StringProperty direccionProperty() {
        return direccion;
    }

//GET Y SET TELEFONO
    public Integer getTelefono() {
        return telefono.get();
    }

    public void setTelefono(Integer telefono) {
        this.telefono = new SimpleIntegerProperty(telefono);
    }

    public IntegerProperty telefonoProperty() {
        return telefono;
    }

//GET Y SET CELULAR
    public Integer getCelular() {
        return celular.get();
    }

    public void setCelular(Integer celular) {
        this.celular = new SimpleIntegerProperty(celular);
    }

    public IntegerProperty celularProperty() {
        return celular;
    }

// METODO LLENAR INFORMACION
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
                    + "idEstado, "
                    + "direccion, "
                    + "telefono, "
                    + "celular "
                    + "FROM Cliente"
            );

            while (resultado.next()) {
                listaCliente.add(
                        new Cliente(
                                resultado.getInt("idCliente"),
                                resultado.getString("primerNombre"),
                                resultado.getString("segundoNombre"),
                                resultado.getString("primerApellido"),
                                resultado.getString("segundoApellido"),
                                new TipoDocumento(
                                        resultado.getInt("idTipoDocumento"),
                                        resultado.getString("nombreTipoDocumento"),
                                        resultado.getString("descripcionTipoDocumento")),
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

// METODO GUARDAR
    public int guardarCliente(Conexion conexion) {
        try {
            PreparedStatement ps = conexion.getConnection().prepareStatement(
                    "INSERT INTO Cliente ( "
                    + "idCliente, "
                    + "primerNombre, "
                    + "segundoNombre "
                    + "primerApellido, "
                    + "segundoApellido "
                    + "idTipoDocumento "
                    + "numeroDocumento "
                    + "idEstado "
                    + "direccion "
                    + "telefono "
                    + "celular "
                    + ") VALUES (?,  ?,  ?,  ?,  ?,  ?, ?,  ?,  ?,  ?,  ?"
            );
            ps.setInt(1, idCliente.get());
            ps.setString(2, primerNombre.get());
            ps.setString(3, segundoNombre.get());
            ps.setString(4, primerApellido.get());
            ps.setString(5, segundoApellido.get());
            ps.setInt(6, idTipoDocumento.getIdTipoDocumento());
            ps.setInt(7, numeroDocumento.get());
            ps.setInt(8, idEstado.getIdEstado());
            ps.setString(9, direccion.get());
            ps.setInt(10, telefono.get());
            ps.setInt(11, celular.get());

            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

// METODO ACTUALIZAR
    public int actualizarCliente(Conexion conexion) {
        try {
            PreparedStatement ps = conexion.getConnection().prepareStatement(
                    "UPDATE Cliente"
                    + "SET idCliente = ? "
                    + "primerNombre = ? "
                    + "segundoNombre = ? "
                    + "primerApellido = ? "
                    + "segundoApellido = ? "
                    + "idTipoDocumento = ? "
                    + "numeroDocumento = ? "
                    + "idEstado = ? "
                    + "direccion = ? "
                    + "telefono = ? "
                    + "celular = ? "
                    + "WHERE idCliente = ?"
            );

            ps.setInt(1, idCliente.get());
            ps.setString(2, primerNombre.get());
            ps.setString(3, segundoNombre.get());
            ps.setString(4, primerApellido.get());
            ps.setString(5, segundoApellido.get());
            ps.setInt(1, idTipoDocumento.getIdTipoDocumento());
            ps.setInt(1, numeroDocumento.get());
            ps.setInt(7, idEstado.getIdEstado());
            ps.setString(7, direccion.get());
            ps.setInt(7, telefono.get());
            ps.setInt(7, celular.get());

            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

// METODO ELIMINAR
    public int eliminarCliente(Conexion conexion) {
        try {
            PreparedStatement ps = conexion.getConnection().prepareStatement(
                    "DELETE FROM Cliente"
                    + " WHERE idCliente = ?"
            );

            ps.setInt(1, idCliente.get());

            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }

    }

}
