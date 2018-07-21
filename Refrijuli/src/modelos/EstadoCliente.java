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

public class EstadoCliente {

    private IntegerProperty idEstadoCliente;
    private StringProperty nombreEstadoCliente;
    private StringProperty descripcionEstadoCliente;

    public EstadoCliente(
            Integer idEstadoCliente,
            String nombreEstadoCliente,
            String descripcionEstadoCliente) {
        this.idEstadoCliente = new SimpleIntegerProperty(idEstadoCliente);
        this.nombreEstadoCliente = new SimpleStringProperty(nombreEstadoCliente);
        this.descripcionEstadoCliente = new SimpleStringProperty(descripcionEstadoCliente);
    }
    
        /**
     * Constructor vacío para parametrizar un único valor
     */
    public EstadoCliente() {
    }

//GET Y SET ID ESTADO
    public Integer getIdEstadoCliente() {
        return idEstadoCliente.get();
    }

    public void setIdEstadoCliente(Integer idEstadoCliente) {
        this.idEstadoCliente = new SimpleIntegerProperty(idEstadoCliente);
    }

    public IntegerProperty idEstadoClienteProperty() {
        return idEstadoCliente;
    }

//GET Y SET NOMBRE ESTADO
    public String getnombreEstadoCliente() {
        return nombreEstadoCliente.get();
    }

    public void setnombreEstadoCliente(String nombreEstadoCliente) {
        this.nombreEstadoCliente = new SimpleStringProperty(nombreEstadoCliente);
    }

    public StringProperty nombreEstadoClienteProperty() {
        return nombreEstadoCliente;
    }

//GET Y SET DESCRIPCION
    public String getDescripcionEstadoCliente() {
        return descripcionEstadoCliente.get();
    }

    public void setDescripcion(String descripcionEstadoCliente) {
        this.descripcionEstadoCliente = new SimpleStringProperty(descripcionEstadoCliente);
    }

    public StringProperty descripcionEstadoClienteProperty() {
        return descripcionEstadoCliente;
    }

    
    public static void llenarInformacionEstadoCliente(Connection connection,
            ObservableList<EstadoCliente> listaEstadoCliente) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultado = statement.executeQuery(
                    "SELECT idEstadoCliente, "
                    + "nombreEstadoCliente, "
                    + "descripcionEstadoCliente "
                    + "FROM EstadoCliente"
            );

            while (resultado.next()) {
                listaEstadoCliente.add(
                        new EstadoCliente(
                        resultado.getInt("idEstadoCliente"),
                        resultado.getString("nombreEstadoCliente"),
                        resultado.getString("descripcionEstadoCliente")
                )
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
       @Override
    public String toString(){
        return nombreEstadoCliente.get();
    }


//METODO GUARDAR
    public int guardarEstadoCliente(Conexion conexion) {
        try {
            PreparedStatement ps = conexion.getConnection().prepareStatement(
                    "INSERT INTO  EstadoCliente ( "
                    + "idEstadoCliente, "
                    + "nombreEstadoCliente, "
                    + "descripcionEstadoCliente, "
                    + ") VALUES (?,  ?,  ?)"
            );
            ps.setInt(1, idEstadoCliente.get());
            ps.setString(2, nombreEstadoCliente.get());
            ps.setString(3, descripcionEstadoCliente.get());

            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int actualizarEstadoCliente(Conexion conexion) {
        try {
            PreparedStatement ps = conexion.getConnection().prepareStatement(
                    "UPDATE EstadoCliente "
                    + "SET idEstadoCliente = ?, "
                    + "nombreEstadoCliente = ?, "
                    + "descripcionEstadoCliente = ? "
                    + "WHERE idEstadoCliente = ?"
            );

            ps.setInt(1, idEstadoCliente.get());
            ps.setString(2, nombreEstadoCliente.get());
            ps.setString(3, descripcionEstadoCliente.get());
            ps.setInt(4, idEstadoCliente.get());

            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int eliminarEstadoCliente(Conexion conexion) {
        try {
            PreparedStatement ps = conexion.getConnection().prepareStatement(
                    "DELETE FROM EstadoCliente"
                    + " WHERE idEstadoCliente = ?"
            );

            ps.setInt(1, idEstadoCliente.get());

            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }

    }
}
