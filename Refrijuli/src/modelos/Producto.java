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

public class Producto {

    private IntegerProperty idProducto;
    private StringProperty nombre;
    private StringProperty descripcion;
    private StringProperty precio;
    private StringProperty idEstado;

    public Producto(Integer idProducto, String nombre, String descripcion, String precio, String foto, String estado) {
        this.idProducto = new SimpleIntegerProperty(idProducto);
        this.nombre = new SimpleStringProperty(nombre);
        this.descripcion = new SimpleStringProperty(descripcion);
        this.precio = new SimpleStringProperty(precio);
        this.idEstado = new SimpleStringProperty(estado);
    }

    public Producto() {
    }
    public IntegerProperty getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto = new SimpleIntegerProperty(idProducto);
    }

    public StringProperty getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = new SimpleStringProperty(nombre);
    }

    public StringProperty getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = new SimpleStringProperty(descripcion);
    }

    public StringProperty getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = new SimpleStringProperty(precio);
    }

    public StringProperty getEstado() {
        return idEstado;
    }

    public void setEstado(String estado) {
        this.idEstado = new SimpleStringProperty(estado);
    }

    public static void llenarInformacionProducto(Connection connection,
            ObservableList<Producto> listaProducto) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultado = statement.executeQuery(
                    "SELECT idProducto"
                    + "nombre"
                    + "descripcion "
                    + "precio"
                    + "foto"
                    + "idEstado"
                    + "FROM Producto"
            );

            while (resultado.next()) {
                listaProducto.add(new Producto(
                        resultado.getInt("idProducto"),
                        resultado.getString("nombre"),
                        resultado.getString("descripcion"),
                        resultado.getString("precio"),
                        resultado.getString("foto"),
                        resultado.getString("idEstado")
                )
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int guardarProducto(Conexion conexion) {
        try {
            PreparedStatement ps = conexion.getConnection().prepareStatement(
                    "INSERT INTO  producto ( "
                    + "idProducto, "
                    + "nombre "
                    + "descripcion "
                    + "precio "
                   
                    + "idEstado "
                    + ") VALUES (?, ?, ?, ?, ?)"
            );
            ps.setInt(1, idProducto.get());
            ps.setString(2, nombre.get());
            ps.setString(3, descripcion.get());
            ps.setString(4, precio.get());
            ps.setString(5, idEstado.get());

            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int actualizarProducto(Conexion conexion) {
        try {
            PreparedStatement ps = conexion.getConnection().prepareStatement(
                    "UPDATE Producto "
                    + "SET idProducto = ? "
                    + "nombre = ? "
                    + "descripcion = ? "
                    + "precio = ? "
                    + "idEstado = ? "
                    + "WHERE idProducto = ?"
            );

            ps.setInt(1, idProducto.get());
            ps.setString(2, nombre.get());
            ps.setString(3, descripcion.get());
            ps.setString(4, precio.get());

            ps.setString(5, idEstado.get());

            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int eliminarProducto(Conexion conexion) {
        try {
            PreparedStatement ps = conexion.getConnection().prepareStatement(
                    "DELETE FROM Producto WHERE idProducto = ?"
            );
            ps.setInt(1, idProducto.get());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

}
