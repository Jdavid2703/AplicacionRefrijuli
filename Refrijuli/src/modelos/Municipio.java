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

public class Municipio {

    private IntegerProperty idMunicipio;
    private StringProperty nombre;
    private StringProperty descripcion;

    public Municipio(
            Integer idMunicipio,
            String nombre,
            String descripcion) {
        this.idMunicipio = new SimpleIntegerProperty(idMunicipio);
        this.nombre = new SimpleStringProperty(nombre);
        this.descripcion = new SimpleStringProperty(descripcion);
    }
    
        /**
     * Constructor vacío para parametrizar un único valor
     */
    public Municipio() {
    }

//GET Y SET ID ESTADO
    public Integer getIdMunicipio() {
        return idMunicipio.get();
    }

    public void setIdMunicipio(Integer idMunicipio) {
        this.idMunicipio = new SimpleIntegerProperty(idMunicipio);
    }

    public IntegerProperty idMunicipioProperty() {
        return idMunicipio;
    }

//GET Y SET NOMBRE ESTADO
    public String getnombre() {
        return nombre.get();
    }

    public void setnombre(String nombre) {
        this.nombre = new SimpleStringProperty(nombre);
    }

    public StringProperty nombreProperty() {
        return nombre;
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

    
    public static void llenarInformacionMunicipio(Connection connection,
            ObservableList<Municipio> listaMunicipio) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultado = statement.executeQuery(
                    "SELECT idMunicipio, "
                    + "nombre, "
                    + "descripcion "
                    + "FROM Municipio"
            );

            while (resultado.next()) {
                listaMunicipio.add(
                        new Municipio(
                        resultado.getInt("idMunicipio"),
                        resultado.getString("nombre"),
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
        return nombre.get();
    }


//METODO GUARDAR
    public int guardarMunicipio(Conexion conexion) {
        try {
            PreparedStatement ps = conexion.getConnection().prepareStatement(
                    "INSERT INTO  Municipio ( "
                    + "idMunicipio, "
                    + "nombre, "
                    + "descripcion "
                    + ") VALUES (?,  ?,  ?)"
            );
            ps.setInt(1, idMunicipio.get());
            ps.setString(2, nombre.get());
            ps.setString(3, descripcion.get());

            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int actualizarMunicipio(Conexion conexion) {
        try {
            PreparedStatement ps = conexion.getConnection().prepareStatement(
                    "UPDATE Municipio "
                    + "SET idMunicipio = ?, "
                    + "nombre = ?, "
                    + "descripcion = ? "
                    + "WHERE idMunicipio = ?"
            );

            ps.setInt(1, idMunicipio.get());
            ps.setString(2, nombre.get());
            ps.setString(3, descripcion.get());
            ps.setInt(4, idMunicipio.get());

            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int eliminarMunicipio(Conexion conexion) {
        try {
            PreparedStatement ps = conexion.getConnection().prepareStatement(
                    "DELETE FROM Municipio"
                    + " WHERE idMunicipio = ?"
            );

            ps.setInt(1, idMunicipio.get());

            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }

    }
}
