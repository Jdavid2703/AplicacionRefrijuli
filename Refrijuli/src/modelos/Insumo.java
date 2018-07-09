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

public class Insumo {

    private IntegerProperty idInsumo;
    private StringProperty nombre;
    private StringProperty descripcion;
    private IntegerProperty cantidad;
    private Categoria idCategoria;
    private IntegerProperty stockMin;
    private IntegerProperty stockMax;
    private Presentacion idPresentacion;
    private Unidad idUnidad;
    private IntegerProperty precioUnitario;
    private IntegerProperty disponibilidad;

    public Insumo(
            Integer idInsumo,
            String nombre,
            String descripcion,
            Integer cantidad,
            Categoria idCategoria,
            Integer stockMin,
            Integer stockMax,
            Presentacion idPresentacion,
            Unidad idUnidad,
            Integer precioUnitario,
            Integer disponibilidad) {
        this.idInsumo = new SimpleIntegerProperty(idInsumo);
        this.nombre = new SimpleStringProperty(nombre);
        this.descripcion = new SimpleStringProperty(descripcion);
        this.cantidad = new SimpleIntegerProperty(cantidad);
        this.idCategoria = idCategoria;
        this.stockMin = new SimpleIntegerProperty(stockMin);
        this.stockMax = new SimpleIntegerProperty(stockMax);
        this.idPresentacion = idPresentacion;
        this.idUnidad = idUnidad;
        this.precioUnitario = new SimpleIntegerProperty(precioUnitario);
        this.disponibilidad = new SimpleIntegerProperty(disponibilidad);
    }

    public Insumo() {
    }

    public IntegerProperty getIdInsumo() {
        return idInsumo;
    }

    public void setIdInsumo(Integer idInsumo) {
        this.idInsumo = new SimpleIntegerProperty(idInsumo);
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

    public IntegerProperty getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = new SimpleIntegerProperty(cantidad);
    }

    public Categoria getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Categoria idCategoria) {
        this.idCategoria = idCategoria;
    }

    public IntegerProperty getStockMin() {
        return stockMin;
    }

    public void setStockMin(Integer stockMin) {
        this.stockMin = new SimpleIntegerProperty(stockMin);
    }

    public IntegerProperty getStockMax() {
        return stockMax;
    }

    public void setStockMax(Integer stockMax) {
        this.stockMax = new SimpleIntegerProperty(stockMax);
    }

    public Presentacion getIdPresentacion() {
        return idPresentacion;
    }

    public void setIdPresentacion(Presentacion idPresentacion) {
        this.idPresentacion = idPresentacion;
    }

    public Unidad getIdUnidad() {
        return idUnidad;
    }

    public void setIdUnidad(Unidad idUnidad) {
        this.idUnidad = idUnidad;
    }

    public IntegerProperty getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(Integer precioUnitario) {
        this.precioUnitario = new SimpleIntegerProperty(precioUnitario);
    }

    public IntegerProperty getDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(Integer disponibilidad) {
        this.disponibilidad = new SimpleIntegerProperty(disponibilidad);
    }

    //METODO LLENAR INFORMACION
    public static void llenarInformacionInsumo(Connection connection,
            ObservableList<Insumo> listaInsumo) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultado = statement.executeQuery(
                    "SELECT idInsumo, "
                    + "nombre,"
                    + "descripcion,"
                    + "cantidad,"
                    + "idCategoria,"
                    + "stockMin,"
                    + "stockMax,"
                    + "idPresentacion,"
                    + "idUnidad,"
                    + "precioUnitario,"
                    + "disponibilidad"
            );


            while (resultado.next()) {
                listaInsumo.add(
                        new Insumo(
                                resultado.getInt("idInsumo"),
                                resultado.getString("nombre"),
                                resultado.getString("descripcion"),
                                resultado.getInt("cantidad"),
                                new Categoria(resultado.getInt("idCategoria"),
                                        resultado.getString("nombre"),
                                        resultado.getString("descripcion")),
                                resultado.getInt("stockMin"),
                                resultado.getInt("stockMax"),
                                new Presentacion(resultado.getInt("idPresentacion"),
                                        resultado.getString("descripcion")),
                                new Unidad(resultado.getInt("idUnidad"),
                                        resultado.getString("tipoUnidad"),
                                        resultado.getInt("cantidad")),
                                resultado.getInt("precioUnitario"),
                                resultado.getInt("disponibilidad")
                        )
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //METODO GUARDAR 
    public int guardarInsumo(Conexion conexion) {
        try {
            PreparedStatement ps = conexion.getConnection().prepareStatement(
                    "INSERT INTO  Insumo ( "
                    + "idInsumo, "
                    + "nombre,"
                    + "descripcion,"
                    + "cantidad,"
                    + "idCategoria,"
                    + "stockMin,"
                    + "stockMax,"
                    + "idPresentacion,"
                    + "idUnidad,"
                    + "precioUnitario,"
                    + "disponibilidad"
                    + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )"
            );
            ps.setInt(1, idInsumo.get());
            ps.setString(2, nombre.get());
            ps.setString(3, descripcion.get());
            ps.setInt(4, cantidad.get());
            ps.setInt(5, idCategoria.getIdCategoria());
            ps.setInt(6, stockMin.get());
            ps.setInt(7, stockMax.get());
            ps.setInt(8, idPresentacion.getIdPresentacion());
            ps.setInt(9, idUnidad.getIdUnidad());
            ps.setInt(10, precioUnitario.get());
            ps.setInt(11, disponibilidad.get());

            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
    //METODO ACTUALIZAR 

    public int actualizarInsumo(Conexion conexion) {
        try {
            PreparedStatement ps = conexion.getConnection().prepareStatement(
                    "UPDATE Insumo "
                    + "SET idInsumo = ? ,"
                    + "nombre = ?,"
                    + "descripcion = ?,"
                    + "cantidad = ?,"
                    + "idCategoria = ?,"
                    + "stockMin = ?,"
                    + "stockMax = ?,"
                    + "idPresentacion = ?,"
                    + "idUnidad = ?,"
                    + "precioUnitario = ?,"
                    + "disponibilidad = ?"
                    + "WHERE idInsumo = ?"
            );

            ps.setInt(1, idInsumo.get());
            ps.setString(2, nombre.get());
            ps.setString(3, descripcion.get());
            ps.setInt(4, cantidad.get());
            ps.setInt(5, idCategoria.getIdCategoria());
            ps.setInt(6, stockMin.get());
            ps.setInt(7, stockMax.get());
            ps.setInt(8, idPresentacion.getIdPresentacion());
            ps.setInt(9, idUnidad.getIdUnidad());
            ps.setInt(10, precioUnitario.get());
            ps.setInt(11, disponibilidad.get());

            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
    //METODO ELIMINAR

    public int eliminarInsumo(Conexion conexion) {
        try {
            PreparedStatement ps = conexion.getConnection().prepareStatement(
                    "DELETE FROM insumo WHERE idInsumo = ?"
            );
            ps.setInt(1, idInsumo.get());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

}
