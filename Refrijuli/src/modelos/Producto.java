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
    private IntegerProperty precio;
    private EstadoProducto idEstadoProducto;

    public Producto(
            Integer idProducto,
            String nombre,
            String descripcion,
            Integer precio,
            EstadoProducto idEstadoProducto) {
        this.idProducto = new SimpleIntegerProperty(idProducto);
        this.nombre = new SimpleStringProperty(nombre);
        this.descripcion = new SimpleStringProperty(descripcion);
        this.precio = new SimpleIntegerProperty(precio);
        this.idEstadoProducto = idEstadoProducto;
    }

        /**
     * Constructor vacío para parametrizar un único valor
     */
    public Producto() {
    }

    public Integer getIdProducto() {
        return idProducto.get();
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto = new SimpleIntegerProperty(idProducto);
    }

    public IntegerProperty idProductoProperty() {
        return idProducto;
    }

    public String getNombre() {
        return nombre.get();
    }

    public void setNombre(String nombre) {
        this.nombre = new SimpleStringProperty(nombre);
    }

    public StringProperty nombreProperty() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion.get();
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = new SimpleStringProperty(descripcion);
    }

    public StringProperty descripcionProperty() {
        return descripcion;
    }

    public Integer getPrecio() {
        return precio.get();
    }

    public void setPrecio(Integer precio) {
        this.precio = new SimpleIntegerProperty(precio);
    }

    public IntegerProperty precioProperty() {
        return precio;
    }

    public EstadoProducto getIdEstadoProducto() {
        return idEstadoProducto;
    }

    public void setIdEstadoProducto(EstadoProducto idEstadoProducto) {
        this.idEstadoProducto = idEstadoProducto;
    }

    public EstadoProducto idEstadoProductoProperty() {
        return idEstadoProducto;
    }

    public static void llenarInformacionProducto(Connection connection,
            ObservableList<Producto> listaProducto) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultado = statement.executeQuery(
                    "SELECT idProducto, "
                    + "nombre, "
                    + "descripcion, "
                    + "precio, "
                    + "idEstadoProducto "
                    + "FROM Producto"
            );

            while (resultado.next()) {
                listaProducto.add(
                        new Producto(
                                resultado.getInt("idProducto"),
                                resultado.getString("nombre"),
                                resultado.getString("descripcion"),
                                resultado.getInt("precio"),
                                new EstadoProducto(
                                        resultado.getInt("idEstadoProducto"),
                                        resultado.getString("nombreEstadoProducto"),
                                        resultado.getString("descripcionEstadoProducto"))
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
                    + "nombre, "
                    + "descripcion, "
                    + "precio, "
                    + "idEstadoProducto "
                    + ") VALUES (?,  ?,  ?,  ?,  ?)"
            );
            ps.setInt(1, idProducto.get());
            ps.setString(2, nombre.get());
            ps.setString(3, descripcion.get());
            ps.setInt(4, precio.get());
            ps.setInt(5, idEstadoProducto.getIdEstadoProducto());

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
                    + "SET idProducto = ?, "
                    + "nombre = ?, "
                    + "descripcion = ?, "
                    + "precio = ?, "
                    + "idEstadoProducto = ? "
                    + "WHERE idProducto = ?"
            );

            ps.setInt(1, idProducto.get());
            ps.setString(2, nombre.get());
            ps.setString(3, descripcion.get());
            ps.setInt(4, precio.get());
            ps.setInt(5, idEstadoProducto.getIdEstadoProducto());
            ps.setInt(6, idProducto.get());

            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int eliminarProducto(Conexion conexion) {
        try {
            PreparedStatement ps = conexion.getConnection().prepareStatement(
                    "DELETE FROM Producto "
                   + "WHERE idProducto = ?"
            );
            ps.setInt(1, idProducto.get());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

}
