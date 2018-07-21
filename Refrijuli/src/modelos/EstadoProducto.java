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

public class EstadoProducto{

    private IntegerProperty idEstadoProducto;
    private StringProperty nombreEstadoProducto;
    private StringProperty descripcionEstadoProducto;

    public EstadoProducto(
            Integer idEstadoProducto,
            String nombreEstadoProducto,
            String descripcionEstadoProducto) {
        this.idEstadoProducto = new SimpleIntegerProperty(idEstadoProducto);
        this.nombreEstadoProducto = new SimpleStringProperty(nombreEstadoProducto);
        this.descripcionEstadoProducto = new SimpleStringProperty(descripcionEstadoProducto);
    }
    
        /**
     * Constructor vacío para parametrizar un único valor
     */
    public EstadoProducto() {
    }

//GET Y SET ID ESTADO
    public Integer getIdEstadoProducto() {
        return idEstadoProducto.get();
    }

    public void setIdEstadoProducto(Integer idEstadoProducto) {
        this.idEstadoProducto = new SimpleIntegerProperty(idEstadoProducto);
    }

    public IntegerProperty idEstadoProductoProperty() {
        return idEstadoProducto;
    }

//GET Y SET NOMBRE ESTADO
    public String getnombreEstadoProducto() {
        return nombreEstadoProducto.get();
    }

    public void setnombreEstadoProducto(String nombreEstadoProducto) {
        this.nombreEstadoProducto = new SimpleStringProperty(nombreEstadoProducto);
    }

    public StringProperty nombreEstadoProductoProperty() {
        return nombreEstadoProducto;
    }

//GET Y SET DESCRIPCION
    public String getDescripcionEstadoProducto() {
        return descripcionEstadoProducto.get();
    }

    public void setEstadoProducto(String descripcionEstadoProducto) {
        this.descripcionEstadoProducto = new SimpleStringProperty(descripcionEstadoProducto);
    }

    public StringProperty descripcionEstadoProductoProperty() {
        return descripcionEstadoProducto;
    }

    
    public static void llenarInformacionEstadoProducto(Connection connection,
            ObservableList<EstadoProducto> listaEstadoProducto) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultado = statement.executeQuery(
                    "SELECT idEstadoProducto, "
                    + "nombreEstadoProducto, "
                    + "descripcionEstadoProducto "
                    + "FROM EstadoProducto"
            );

            while (resultado.next()) {
                listaEstadoProducto.add(
                        new EstadoProducto(
                        resultado.getInt("idEstadoProducto"),
                        resultado.getString("nombreEstadoProducto"),
                        resultado.getString("descripcionEstadoProducto")
                )
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
       @Override
    public String toString(){
        return nombreEstadoProducto.get();
    }


//METODO GUARDAR
    public int guardarEstadoProducto(Conexion conexion) {
        try {
            PreparedStatement ps = conexion.getConnection().prepareStatement(
                    "INSERT INTO  EstadoProducto ( "
                    + "idEstadoProducto, "
                    + "nombreEstadoProducto, "
                    + "descripcionEstadoProducto, "
                    + ") VALUES (?,  ?,  ?)"
            );
            ps.setInt(1, idEstadoProducto.get());
            ps.setString(2, nombreEstadoProducto.get());
            ps.setString(3, descripcionEstadoProducto.get());

            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int actualizarEstadoProducto(Conexion conexion) {
        try {
            PreparedStatement ps = conexion.getConnection().prepareStatement(
                    "UPDATE EstadoProducto "
                    + "SET idEstadoProducto = ?, "
                    + "nombreEstadoProducto = ?, "
                    + "descripcionEstadoProducto = ? "
                    + "WHERE idEstadoProducto = ?"
            );

            ps.setInt(1, idEstadoProducto.get());
            ps.setString(2, nombreEstadoProducto.get());
            ps.setString(3, descripcionEstadoProducto.get());
            ps.setInt(4, idEstadoProducto.get());

            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int eliminarEstadoProducto(Conexion conexion) {
        try {
            PreparedStatement ps = conexion.getConnection().prepareStatement(
                    "DELETE FROM EstadoProducto"
                    + " WHERE idEstadoProducto = ?"
            );

            ps.setInt(1, idEstadoProducto.get());

            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }

    }
}
