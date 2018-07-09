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

public class TipoDocumento {

    private IntegerProperty idTipoDocumento;
    private StringProperty nombreTipoDocumento;

    public TipoDocumento(
            Integer idTipoDocumento,
            String nombreTipoDocumento) {
        this.idTipoDocumento = new SimpleIntegerProperty(idTipoDocumento);
        this.nombreTipoDocumento = new SimpleStringProperty(nombreTipoDocumento);
    }

//GET Y SET ID TIPO DOCUMENTO
    public Integer getIdTipoDocumento() {
        return idTipoDocumento.get();
    }

    public void setIdTipoDocumento(Integer idTipoDocumento) {
        this.idTipoDocumento = new SimpleIntegerProperty(idTipoDocumento);
    }
    
     public IntegerProperty idTipoDocumentoProperty() {
        return idTipoDocumento;
    }

//GET Y SET NOMBRE TIPO DOCUMENTO
    public String getNombreTipoDocumento() {
        return nombreTipoDocumento.get();
    }

    public void setNombreTipoDocumento(String nombreTipoDocumento) {
        this.nombreTipoDocumento = new SimpleStringProperty(nombreTipoDocumento);
    }
    
    public StringProperty nombreTipoDocumentoProperty() {
        return nombreTipoDocumento;
    }

    
//METODO LLENAR INFORMACIÓN
    public static void llenarInformacionTipoDocumento(Connection connection, 
                            ObservableList<TipoDocumento> listaTipoDocumento) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultado = statement.executeQuery(
                    "SELECT idTipoDocumento, "
                            + "nombreTipoDocumento, "
                           
            );
            
            while (resultado.next()) {
                listaTipoDocumento.add(
                        new TipoDocumento(
                            resultado.getInt("idTipoDocumento"),      
                            resultado.getString("nombreTipoDocumento")
                        
                        )
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
//METODO GUARDAR TIPO DOCUMENTO
    public int guardarTipoDocumento(Conexion conexion) {
        try {
            PreparedStatement ps = conexion.getConnection().prepareStatement(
                    "INSERT INTO  TipoDocumento ( "
                    + "idTipoDocumento, "
                    + "nombreTipoDocumento, "
                    + ") VALUES (?, ?)"
            );
            ps.setInt(1, idTipoDocumento.get());
            ps.setString(2, nombreTipoDocumento.get());

            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
        
//METODO ACTUALIZAR TIPO DOCUMENTO

    public int actualizarTipoDocumento(Conexion conexion){
        try {
            PreparedStatement ps = conexion.getConnection().prepareStatement(
                    "UPDATE TipoDocumento"
                    + "SET idTipoDocumento = ?, "
                    + "nombreTipoDocumento = ?, "
                    + "WHERE idTipoDocumento = ?"
            );

            ps.setInt(1, idTipoDocumento.get());
            ps.setString(2, nombreTipoDocumento.get());

            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }


//METODO ELIMINAR TIPO DOCUMENTO
    public int eliminarTipoDocumento(Conexion conexion) {
        try {
            PreparedStatement ps = conexion.getConnection().prepareStatement(
                    "DELETE FROM TipoDocumento"
                    + " WHERE idTipoDocumento = ?"
            );

            ps.setInt(1, idTipoDocumento.get());

            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }

    }


  
}
 
