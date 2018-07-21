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

public class Unidad {

    private IntegerProperty idUnidad;
    private StringProperty nombreUnidad;

    public Unidad(
            Integer idUnidad,
            String nombreUnidad) {
        this.idUnidad = new SimpleIntegerProperty(idUnidad);
        this.nombreUnidad = new SimpleStringProperty(nombreUnidad);
    }

            /**
     * Constructor vacío para parametrizar un único valor
     */
    public Unidad() {
    }
    
//GET Y SET ID UNIDAD
    public Integer getIdUnidad() {
        return idUnidad.get();
    }

    public void setIdUnidad(Integer idUnidad) {
        this.idUnidad = new SimpleIntegerProperty(idUnidad);
    }

    public IntegerProperty idUnidadProperty() {
        return idUnidad;
    }

//GET Y SET NOMBRE UNIDAD
    public String getNombreUnidad() {
        return nombreUnidad.get();
    }

    public void setNombreUnidad(String nombreUnidad) {
        this.nombreUnidad = new SimpleStringProperty(nombreUnidad);
    }

    public StringProperty nombreUnidadProperty() {
        return nombreUnidad;
    }

//METODO LLENAR INFORMACIÓN
    public static void llenarInformacionUnidad(Connection connection,
            ObservableList<Unidad> listaUnidad) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultado = statement.executeQuery(
                    "SELECT idUnidad, "
                    + "nombreUnidad "
                    + "FROM Unidad"
            );

            while (resultado.next()) {
                listaUnidad.add(
                        new Unidad(
                                resultado.getInt("idUnidad"),
                                resultado.getString("nombreUnidad")
                        )
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
        @Override
    public String toString(){
        return nombreUnidad.get();
    }

//METODO GUARDAR UNIDAD
    public int guardarUnidad(Conexion conexion) {
        try {
            PreparedStatement ps = conexion.getConnection().prepareStatement(
                    "INSERT INTO  Unidad( "
                    + "idUnidad, "
                    + "nombreUnidad "
                    + ") VALUES (?, ?)"
            );
            ps.setInt(1, idUnidad.get());
            ps.setString(2, nombreUnidad.get());

            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

//METODO ACTUALIZAR UNIDAD
    public int actualizarUnidad(Conexion conexion) {
        try {
            PreparedStatement ps = conexion.getConnection().prepareStatement(
                    "UPDATE Unidad "
                    + "SET idUnidad = ?, "
                    + "nombreUnidad = ? "
                    + "WHERE idUnidad = ?"
            );

            ps.setInt(1, idUnidad.get());
            ps.setString(2, nombreUnidad.get());
            ps.setInt(3, idUnidad.get());

            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

//METODO ELIMINAR UNIDAD
    public int eliminarUnidad(Conexion conexion) {
        try {
            PreparedStatement ps = conexion.getConnection().prepareStatement(
                    "DELETE FROM Unidad"
                    + " WHERE idUnidad = ?"
            );

            ps.setInt(1, idUnidad.get());

            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }

    }

}
