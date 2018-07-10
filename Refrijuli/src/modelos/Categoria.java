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

public class Categoria {

    private IntegerProperty idCategoria;
    private StringProperty nombre;
    private StringProperty descripcion;

    public Categoria(Integer idCategoria, String nombre, String descripcion) {
        this.idCategoria = new SimpleIntegerProperty(idCategoria);
        this.nombre = new SimpleStringProperty(nombre);
        this.descripcion = new SimpleStringProperty(descripcion);
    }

//GET Y SET ID CATEGORIA
  
   public Integer getIdCategoria() {
        return idCategoria.get();
    }

    public void setIdCategoria(Integer idCategoria) {
        this.idCategoria = new SimpleIntegerProperty(idCategoria);
    }

    public IntegerProperty idCategoriaProperty() {
        return idCategoria;
    }

//GET Y SET NOMBRE 
    public String getNombre() {
        return nombre.get();
    }

    public void setNombre(String nombre) {
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

//METODO LLENAR INFORMACIÓN
    public static void llenarInformacionCategoria(Connection connection,
            ObservableList<Categoria> listaCategoria) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultado = statement.executeQuery(
                    "SELECT idCategoria, "
                    + "nombre, "
                    + "descripcion, "
            );

            while (resultado.next()) {
                listaCategoria.add(
                        new Categoria(
                                resultado.getInt("idCategoria"),
                                resultado.getString("nombre"),
                                resultado.getString("descripcion")
                        )
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//METODO GUARDAR 
    public int guardarCategoria(Conexion conexion) {
        try {
            PreparedStatement ps = conexion.getConnection().prepareStatement(
                    "INSERT INTO categoria ( "
                    + "idCategoria, "
                    + "nombre, "
                    + "descripcion "
                    + ") VALUES (?,  ?,  ?)"
            );
            ps.setInt(1, idCategoria.get());
            ps.setString(2, nombre.get());
            ps.setString(3, descripcion.get());

            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

// METODO ACTUALIZAR
    public int actualizarCategoria(Conexion conexion) {
        try {
            PreparedStatement ps = conexion.getConnection().prepareStatement(
                    "UPDATE Categoria"
                    + "SET idCategoria = ? "
                    + "nombre = ? "
                    + "descripcion = ? "
                    + "WHERE idCategoria = ?"
            );

            ps.setInt(1, idCategoria.get());
            ps.setString(2, nombre.get());
            ps.setString(3, descripcion.get());

            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

//METODO ELIMINAR
    public int eliminarCategoria(Conexion conexion) {
        try {
            PreparedStatement ps = conexion.getConnection().prepareStatement(
                    "DELETE FROM Categoria"
                    + " WHERE idCategoria = ?"
            );

            ps.setInt(1, idCategoria.get());

            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }

    }
}
