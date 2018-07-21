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

public class Estado {

    private IntegerProperty idEstado;
    private StringProperty nombreEstado;
    private StringProperty descripcion;

    public Estado(
            Integer idEstado,
            String Nombre,
            String descripcion) {
        this.idEstado = new SimpleIntegerProperty(idEstado);
        this.nombreEstado = new SimpleStringProperty(Nombre);
        this.descripcion = new SimpleStringProperty(descripcion);
    }
    
        /**
     * Constructor vacío para parametrizar un único valor
     */
    public Estado() {
    }

//GET Y SET ID ESTADO
    public Integer getIdEstado() {
        return idEstado.get();
    }

    public void setIdEstado(Integer idEstadoPedido) {
        this.idEstado = new SimpleIntegerProperty(idEstadoPedido);
    }

    public IntegerProperty idEstadoProperty() {
        return idEstado;
    }

//GET Y SET NOMBRE ESTADO
    public String getnombreEstado() {
        return nombreEstado.get();
    }

    public void setnombreEstado(String nombreEstado) {
        this.nombreEstado = new SimpleStringProperty(nombreEstado);
    }

    public StringProperty nombreEstadoProperty() {
        return nombreEstado;
    }

//GET Y SET DESCRIPCION
    public String getDescripcion() {
        return descripcion.get();
    }

    public void setDescripcion(String Descripcion) {
        this.descripcion = new SimpleStringProperty(Descripcion);
    }

    public StringProperty descripcionProperty() {
        return descripcion;
    }

    
    public static void llenarInformacionEstado(Connection connection,
            ObservableList<Estado> listaEstado) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultado = statement.executeQuery(
                    "SELECT idEstado, "
                    + "nombreEstado, "
                    + "descripcion "
                    + "FROM Estado"
            );

            while (resultado.next()) {
                listaEstado.add(new Estado(
                        resultado.getInt("idEstado"),
                        resultado.getString("nombreEstado"),
                        resultado.getString("descripcion")
                )
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
       @Override
    public String toString(){
        return nombreEstado.get();
    }


//METODO GUARDAR
    public int guardarEstado(Conexion conexion) {
        try {
            PreparedStatement ps = conexion.getConnection().prepareStatement(
                    "INSERT INTO  Estado ( "
                    + "idEstado, "
                    + "nombreEstado, "
                    + "Descripcion, "
                    + ") VALUES (?,  ?,  ?)"
            );
            ps.setInt(1, idEstado.get());
            ps.setString(2, nombreEstado.get());
            ps.setString(3, descripcion.get());

            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int actualizarEstado(Conexion conexion) {
        try {
            PreparedStatement ps = conexion.getConnection().prepareStatement(
                    "UPDATE Estado "
                    + "SET idEstado = ?, "
                    + "nombreEstado = ?, "
                    + "descripcion = ? "
                    + "WHERE idEstado = ?"
            );

            ps.setInt(1, idEstado.get());
            ps.setString(2, nombreEstado.get());
            ps.setString(3, descripcion.get());
            ps.setInt(4, idEstado.get());

            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int eliminarEstado(Conexion conexion) {
        try {
            PreparedStatement ps = conexion.getConnection().prepareStatement(
                    "DELETE FROM Estado"
                    + " WHERE idEstado = ?"
            );

            ps.setInt(1, idEstado.get());

            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }

    }
}
