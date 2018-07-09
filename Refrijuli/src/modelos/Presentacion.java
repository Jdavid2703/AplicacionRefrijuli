package modelos;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;

public class Presentacion implements Initializable {

    private IntegerProperty idPresentacion;
    private StringProperty descripcion;

    public Presentacion(Integer idPresentacion, String descripcion) {
        this.idPresentacion = new SimpleIntegerProperty(idPresentacion);
        this.descripcion = new SimpleStringProperty(descripcion);
    }

    public Presentacion() {
    }

    public Integer getIdPresentacion() {
        return idPresentacion.get();
    }

    public void setIdPresentacion(Integer idPresentacion) {
        this.idPresentacion = new SimpleIntegerProperty(idPresentacion);
    }

    public IntegerProperty idPresentacionProperty() {
        return idPresentacion;
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
    private Conexion conexion;//Instanciando la conexion
    //metodo llenar informacion
    public static void llenarInformacionPresentacion(Connection connection,
            ObservableList<Presentacion> listaPresentacion) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultado = statement.executeQuery(
                    "SELECT idPresentacion, "
                    + "descripcion,"
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

    public int actualizarPresentacion(Conexion conexion) {
        try {
            PreparedStatement ps = conexion.getConnection().prepareStatement(
                    "UPDATE Presentacion "
                    + "SET idPresentacion = ? "
                    + "descripcion = ? "
                    + "WHERE idPresentacion = ?"
            );
            ps.setInt(1, idPresentacion.get());
            ps.setString(2, descripcion.get());

            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int eliminarPresentacion(Conexion conexion) {
        try {
            PreparedStatement ps = conexion.getConnection().prepareStatement(
                    "DELETE FROM Presentacion WHERE idPresentacion = ?"
            );
            ps.setInt(1, idPresentacion.get());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
     @Override
    public void initialize(URL url, ResourceBundle rb) {
        conexion = new Conexion();
        conexion.establecerConexion();
    }
}
