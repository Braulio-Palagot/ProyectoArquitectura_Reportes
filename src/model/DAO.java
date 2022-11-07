package model;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class DAO {

    Connection link = null;

    public Connection conectarBD(String clase, String url, String user, String pass) {

        try {
            Class.forName(clase);
            link = DriverManager.getConnection(url, user, pass);
            System.out.println("CONECTADO A LA BD");
        } catch (HeadlessException | ClassNotFoundException | SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        return link;
    }

    public void desconectarBD() {
        try {
            link.close();
        } catch (Exception e) {
            System.out.println("Error al desconectar con la base de datos");
        }
    }

    public String registrarUsuario(String nombreUsuario, String Correo, String apellidoPaternoUsuario,String apellidoMaternoUsuario,String contrasenia ) {
        String mensaje;
        try {
            Statement st;
            //Sentencia SQl para registro de categoría
            String sentenciaSQL = "INSERT INTO usuario(nombreUsuario, Correo, apellidoPaternoUsuario, apellidoMaternoUsuario, contrasenia) VALUES ('" + nombreUsuario + "', '" + Correo + "', '" + apellidoPaternoUsuario + "', '" + apellidoMaternoUsuario + "', '" + contrasenia + "');";

            st = link.createStatement();
            st.executeUpdate(sentenciaSQL);
            mensaje = "Usuario registrado exitosamente.";
        } catch (SQLException e) {
            mensaje = "No se pudo registrar el usuario.";
            System.out.println(e);
        }
        return mensaje;
    }

    public ResultSet loggearUsuario(String Correo, String contrasenia) {
        ResultSet usuario = null;
        try {
            String sentenciaSQL = "SELECT * FROM usuario WHERE Correo = '"+Correo+"' AND contrasenia = '"+contrasenia+"';";
            PreparedStatement OBPST = link.prepareStatement(sentenciaSQL, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            usuario = OBPST.executeQuery();
        } catch (SQLException e) {
            System.out.println(e);
        }
        return usuario;
    }

    public ResultSet informacionPerfil(String Correo) {
        ResultSet usuario = null;
        try {
            String sentenciaSQL = "SELECT t1.ID_Usuario, t1.nombreUsuario, t1.Correo, t1.apellidoPaternoUsuario, t1.apellidoMaternoUsuario, t3.nombre, t3.direccion, t3.fecha FROM usuario t1"
                    + "           INNER JOIN evento_usuario t2 on t1.ID_Usuario = t2.ID_Usuario" +
                    "             INNER JOIN evento t3 on t2.ID_Evento = t3.ID_Evento"
                    + "           WHERE t1.Correo = '"+Correo+ "';";
            PreparedStatement OBPST = link.prepareStatement(sentenciaSQL, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            usuario = OBPST.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuario;
    }

    public ResultSet informacionEvento(int id_evento) {
        ResultSet eventos = null;
        try {
            String sentenciaSQL = "SELECT t1.ID_Evento, t1.nombre, t1.direccion, t1.fecha, t3.tema, t3.documentacion FROM evento t1" +
                    " INNER JOIN ponencia_evento t2 on t1.ID_Evento = t2.ID_Evento" +
                    " INNER JOIN ponencia t3 on t2.ID_Ponencia = t3.ID_Ponencia" +
                    " WHERE t1.ID_Evento = "+id_evento+";";

            PreparedStatement OBPST = link.prepareStatement(sentenciaSQL, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            eventos = OBPST.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return eventos;
    }

    public String insertarComentario(String Comentario, int id_evento, int id_usuario) {
        String mensaje="";
        try {
            Statement st = null;
            //Sentencia SQl para registro de comentario
            String sentenciaSQL = "INSERT INTO comentario (Comentario) VALUES ('"+Comentario+"');";
            st = link.createStatement();
            st.executeUpdate(sentenciaSQL);

            //Sentencia SQl para registro de comentario_evento
            sentenciaSQL = "INSERT INTO comentario_evento (ID_Evento, ID_Comentario) VALUES ("+id_evento+", (SELECT ID_Comentario FROM comentario WHERE Comentario = '"+Comentario+"'));";
            st = link.createStatement();
            st.executeUpdate(sentenciaSQL);

            //Sentencia SQl para registro de comentario_usuario
            sentenciaSQL = "INSERT INTO comentario_usuario (ID_Usuario, ID_Comentario) VALUES ("+id_usuario+", (SELECT ID_Comentario FROM comentario WHERE Comentario = '"+Comentario+"'));";
            st = link.createStatement();
            st.executeUpdate(sentenciaSQL);

            mensaje = "Comentario registrado exitosamente.";
            System.out.println("Entró al try");
        } catch (SQLException e) {
            mensaje = "No se pudo registrar el comentario.";
            System.out.println("Entró al catch");
            System.out.println(e);
        }
        return mensaje;
    }

    public ResultSet selectEventos() {
        ResultSet eventos = null;
        String sentenciaSQL = "SELECT * FROM evento;";

        try {
            PreparedStatement OBPST = link.prepareCall(sentenciaSQL, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            eventos = OBPST.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return eventos;
    }
}
