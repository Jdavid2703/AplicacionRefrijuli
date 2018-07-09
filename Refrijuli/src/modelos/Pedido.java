package modelos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Date;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

public class Pedido {

    private IntegerProperty idPedido;
    private Date fechaEntrega;
    private Date fechaPedido;
    private StringProperty direccionEntrega;
    private Time horaEntrega;
    private Cliente idCliente;
    private Estado idEstado;

    public Pedido(
            Integer idPedido,
            Date fechaEntrega,
            Date fechaPedido,
            String direccionEntrega,
            Time horaEntrega,
            Cliente idCliente,
            Estado idEstado) {
        this.idPedido = new SimpleIntegerProperty(idPedido);
        this.fechaEntrega = fechaEntrega;
        this.fechaPedido = fechaPedido;
        this.direccionEntrega = new SimpleStringProperty(direccionEntrega);
        this.horaEntrega = horaEntrega;
        this.idCliente = idCliente;
        this.idEstado = idEstado;

    }

//GET Y SET ID PEDIDO
    public Integer getIdPedido() {
        return idPedido.get();
    }

    public void setIdPedido(Integer idPedido) {
        this.idPedido = new SimpleIntegerProperty(idPedido);
    }

    public IntegerProperty idPedidoProperty() {
        return idPedido;
    }

//GET Y SET FECHA ENTREGA
    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public Date fechaEntregaProperty() {
        return fechaEntrega;
    }

//GET Y SET FECHA PEDIDO
    public Date getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(Date fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public Date fechaPedidoProperty() {
        return fechaPedido;
    }

//GET Y SET DIRECCIÓN
    public String getDireccionEntrega() {
        return direccionEntrega.get();
    }

    public void setDireccionEntrega(String direccionEntrega) {
        this.direccionEntrega = new SimpleStringProperty(direccionEntrega);
    }

    public StringProperty direccionEntregaProperty() {
        return direccionEntrega;
    }

//GET Y SET HORA ENTREGA
    public Time getHoraEntrega() {
        return horaEntrega;
    }

    public void setHoraEntrega(Time horaEntrega) {
        this.horaEntrega = horaEntrega;
    }

    public Time horaEntregaProperty() {
        return horaEntrega;
    }

//GET Y SET CLIENTE
    public Cliente getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Cliente idCliente) {
        this.idCliente = idCliente;
    }

    public Cliente idClienteProperty() {
        return idCliente;
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

//METODO LLENAR INFORMACIÓN
    public static void llenarInformacionPedido(Connection connection,
            ObservableList<Pedido> listaPedido) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultado = statement.executeQuery(
                    "SELECT idPedido, "
                    + "fechaEntrega, "
                    + "fechaPedido, "
                    + "direccionEntrega, "
                    + "horaEntrega, "
                    + "idCliente, "
                    + "idEstado "
                    + "FROM Pedido"
            );

            while (resultado.next()) {
                listaPedido.add(
                        new Pedido(
                                resultado.getInt("idPedido"),
                                resultado.getDate("fechaEntrega"),
                                resultado.getDate("fechaPedido"),
                                resultado.getString("direccionEntrega"),
                                resultado.getTime("horaEntrega"),
                                new Cliente(
                                        resultado.getInt("idCliente"),
                                        resultado.getString("primerNombre"),
                                        resultado.getString("segundoNombre"),
                                        resultado.getString("primerApellido"),
                                        resultado.getString("segundoApellido"),
                                        new TipoDocumento(
                                                resultado.getInt("idTipoDocumento"),
                                                resultado.getString("nombreTipoDocumento"),
                                                resultado.getString("desecripcionTipoDocumento")),
                                        resultado.getInt("numeroDocumento"),
                                        new Estado(
                                                resultado.getInt("idEstado"),
                                                resultado.getString("nombreEstado"),
                                                resultado.getString("Descripcion")),
                                        resultado.getString("direccion"),
                                        resultado.getInt("telefono"),
                                        resultado.getInt("celular")),
                                new Estado(
                                        resultado.getInt("idEstado"),
                                        resultado.getString("nombreEstado"),
                                        resultado.getString("Descripcion")
                                )));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

// METODO GUARDAR
    public int guardarPedido(Conexion conexion) {
        try {
            PreparedStatement ps = conexion.getConnection().prepareStatement(
                    "INSERT INTO Pedido ( "
                    + "idPedido, "
                    + "fechaEntrega, "
                    + "fechaPedido "
                    + "direccionEntrega, "
                    + "horaEntrega, "
                    + "idCliente, "
                    + "idEstado "
                    + ") VALUES (?,  ?,  ?,  ?,  ?, ?, ?)"
            );
            ps.setInt(1, idPedido.get());
            ps.setDate(2, (java.sql.Date) fechaEntrega);
            ps.setDate(3, (java.sql.Date) fechaPedido);
            ps.setString(4, direccionEntrega.get());
            ps.setTime(5, (java.sql.Time) horaEntrega);
            ps.setInt(6, idCliente.getIdCliente());
            ps.setInt(7, idEstado.getIdEstado());

            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

// METODO ACTUALIZAR
    public int actualizarPedido(Conexion conexion) {
        try {
            PreparedStatement ps = conexion.getConnection().prepareStatement(
                    "UPDATE Pedido"
                    + "SET idPedido = ? "
                    + "fechaEntrega = ? "
                    + "fechaPedido = ? "
                    + "direccionEntrega = ? "
                    + "horaEntrega = ? "
                    + "idCliente = ? "
                    + "idEstado = ? "
                    + "WHERE idPedido = ?"
            );

            ps.setInt(1, idPedido.get());
            ps.setDate(2, (java.sql.Date) fechaEntrega);
            ps.setDate(3, (java.sql.Date) fechaPedido);
            ps.setString(4, direccionEntrega.get());
            ps.setTime(5, (java.sql.Time) horaEntrega);
            ps.setInt(6, idCliente.getIdCliente());
            ps.setInt(7, idEstado.getIdEstado());

            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

//METODO ELIMINAR
    public int eliminarPedido(Conexion conexion) {
        try {
            PreparedStatement ps = conexion.getConnection().prepareStatement(
                    "DELETE FROM Pedido"
                    + " WHERE idPedido = ?"
            );

            ps.setInt(1, idPedido.get());

            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }

    }
}
