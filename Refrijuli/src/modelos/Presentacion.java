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

public class Presentacion {

    private IntegerProperty idPresentacion;
    private StringProperty descripcion;

    public Presentacion(
            Integer idPresentacion,
            String descripcion) {
        this.idPresentacion = new SimpleIntegerProperty(idPresentacion);
        this.descripcion = new SimpleStringProperty(descripcion);
    }

    /**
     * Constructor vacío para parametrizar un único valor
     */
    public Presentacion() {
    }

//GET Y SET ID PRESENTACION
    public Integer getIdPresentacion() {
        return idPresentacion.get();
    }

    public void setIdPresentacion(Integer idPresentacion) {
        this.idPresentacion = new SimpleIntegerProperty(idPresentacion);
    }

    public IntegerProperty idPresentacionProperty() {
        return idPresentacion;
    }

//GET Y SET DESCRIPCION
    public String getDescripcion() {
        return descripcion.get();
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = new SimpleStringProperty(descripcion);
    }

    public StringProperty descripcionProperty() {
        return descripcion;
    }

//METODO LLENAR INFORMACIÓN
    public static void llenarInformacionPresentacion(Connection connection,
            ObservableList<Presentacion> listaPresentacion) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultado = statement.executeQuery(
                    "SELECT idPresentacion, "
                    + "descripcion "
                    + "FROM Presentacion"
            );

            while (resultado.next()) {
                listaPresentacion.add(
                        new Presentacion(
                                resultado.getInt("idPresentacion"),
                                resultado.getString("descripcion")
                        )
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return descripcion.get();
    }

//METODO GUARDAR 
    public int guardarPresentación(Conexion conexion) {
        try {
            PreparedStatement ps = conexion.getConnection().prepareStatement(
                    "INSERT INTO  Presentacion ( "
                    + "idPresentacion, "
                    + "descripcion "
                    + ") VALUES (?, ?)"
            );
            ps.setInt(1, idPresentacion.get());
            ps.setString(2, descripcion.get());

            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

// METODO ACTUALIZAR
    public int actualizarPresentacion(Conexion conexion) {
        try {
            PreparedStatement ps = conexion.getConnection().prepareStatement(
                    "UPDATE Presentacion "
                    + "SET idPresentacion = ?, "
                    + "descripcion = ? "
                    + "WHERE idPresentacion = ?"
            );
            ps.setInt(1, idPresentacion.get());
            ps.setString(2, descripcion.get());
            ps.setInt(3, idPresentacion.get());

            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

//METODO ELIMINAR
    public int eliminarPresentacion(Conexion conexion) {
        try {
            PreparedStatement ps = conexion.getConnection().prepareStatement(
                    "DELETE FROM Presentacion "
                    + "WHERE idPresentacion = ?"
            );
            ps.setInt(1, idPresentacion.get());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

}
