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
    
        /**
     * Constructor vacío para parametrizar un único valor
     */
    public Insumo() {
    }

//GET Y SET ID INSUMO
    public Integer getIdInsumo() {
        return idInsumo.get();
    }

    public void setIdInsumo(Integer idInsumo) {
        this.idInsumo = new SimpleIntegerProperty(idInsumo);
    }

    public IntegerProperty idInsumoProperty() {
        return idInsumo;
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

//GET Y SET CANTIDAD
    public Integer getCantidad() {
        return cantidad.get();
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = new SimpleIntegerProperty(cantidad);
    }

    public IntegerProperty cantidadProperty() {
        return cantidad;
    }

//GET Y SET ID CATEGORIA
    public Categoria getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Categoria idCategoria) {
        this.idCategoria = idCategoria;
    }

    public Categoria idCategoriaProperty() {
        return idCategoria;
    }

//GET Y SET STOCK MIN
    public Integer getStockMin() {
        return stockMin.get();
    }

    public void setStockMin(Integer stockMin) {
        this.stockMin = new SimpleIntegerProperty(stockMin);
    }

    public IntegerProperty stockMinProperty() {
        return stockMin;
    }

//GET Y SET STOCK MAX
    public Integer getStockMax() {
        return stockMax.get();
    }

    public void setStockMax(Integer stockMax) {
        this.stockMax = new SimpleIntegerProperty(stockMax);
    }

    public IntegerProperty stockMaxProperty() {
        return stockMax;
    }

//GET Y SET ID PRESENTACION
    public Presentacion getIdPresentacion() {
        return idPresentacion;
    }

    public void setIdPresentacion(Presentacion idPresentacion) {
        this.idPresentacion = idPresentacion;
    }

    public Presentacion idPresentacionProperty() {
        return idPresentacion;
    }

//GET Y SET ID UNIDAD
    public Unidad getIdUnidad() {
        return idUnidad;
    }

    public void setIdUnidad(Unidad idUnidad) {
        this.idUnidad = idUnidad;
    }

    public Unidad idUnidadProperty() {
        return idUnidad;
    }

//GET Y SET PRECIO UNITARIO
    public Integer getPrecioUnitario() {
        return precioUnitario.get();
    }

    public void setPrecioUnitario(Integer precioUnitario) {
        this.precioUnitario = new SimpleIntegerProperty(precioUnitario);
    }

    public IntegerProperty precioUnitarioProperty() {
        return precioUnitario;
    }

//GET Y SET DISPONIBILIDAD
    public Integer getDisponibilidad() {
        return disponibilidad.get();
    }

    public void setDisponibilidad(Integer disponibilidad) {
        this.disponibilidad = new SimpleIntegerProperty(disponibilidad);
    }

    public IntegerProperty disponibilidadProperty() {
        return disponibilidad;
    }

    
//METODO LLENAR INFORMACION
    public static void llenarInformacionInsumo(Connection connection,
            ObservableList<Insumo> listaInsumo) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultado = statement.executeQuery(
                    "SELECT idInsumo, "
                    + "nombre, "
                    + "descripcion, "
                    + "cantidad, "
                    + "idCategoria, "
                    + "stockMin, "
                    + "stockMax, "
                    + "idPresentacion, "
                    + "idUnidad, "
                    + "precioUnitario, "
                    + "disponibilidad "
                    + "FROM Insumo"
            );

            while (resultado.next()) {
                listaInsumo.add(
                        new Insumo(
                                resultado.getInt("idInsumo"),
                                resultado.getString("nombre"),
                                resultado.getString("descripcion"),
                                resultado.getInt("cantidad"),
                                new Categoria(
                                        resultado.getInt("idCategoria"),
                                        resultado.getString("nombre"),
                                        resultado.getString("descripcion")),
                                resultado.getInt("stockMin"),
                                resultado.getInt("stockMax"),
                                new Presentacion(
                                        resultado.getInt("idPresentacion"),
                                        resultado.getString("descripcion")),
                                new Unidad(
                                        resultado.getInt("idUnidad"),
                                        resultado.getString("nombreUnidad")),
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
                    "INSERT INTO Insumo ( "
                    + "idInsumo, "
                    + "nombre, "
                    + "descripcion, "
                    + "cantidad, "
                    + "idCategoria, "
                    + "stockMin, "
                    + "stockMax, "
                    + "idPresentacion, "
                    + "idUnidad, "
                    + "precioUnitario, "
                    + "disponibilidad "
                    + ") VALUES (?,  ?,  ?,  ?,  ?,  ?,  ?,  ?,  ?,  ?,  ?)"
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
                    + "SET idInsumo = ?, "
                    + "nombre = ?, "
                    + "descripcion = ?, "
                    + "cantidad = ?, "
                    + "idCategoria = ?, "
                    + "stockMin = ?, "
                    + "stockMax = ?, "
                    + "idPresentacion = ?, "
                    + "idUnidad = ?, "
                    + "precioUnitario = ?, "
                    + "disponibilidad = ? "
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
            ps.setInt(12, idInsumo.get());

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
                    "DELETE FROM insumo "
                     + "WHERE idInsumo = ?"
            );
            ps.setInt(1, idInsumo.get());
            
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

}
