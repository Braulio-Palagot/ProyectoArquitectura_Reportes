package view;

import model.DAO;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainFrame extends JFrame {
    private final Image fondo = new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/FondoGeneral.jpg"))).getImage();
    private final Image fondoMenu = new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/FondoMenu.jpg"))).getImage();

    String db = "reportesbd";
    String url = "jdbc:mysql://localhost/" + db;
    String user = "root";
    String pass = "";
    String clase = "com.mysql.cj.jdbc.Driver";

    ResultSet usuario = null;
    String correoUsuario = "";
    int id_usuario = -1;
    int totalEventos = 0;
    ResultSet eventos = null;

    private view.Menu menu = new Menu();
    private JPanel pnlMenu = menu.getPnlMenu();
    private JPanel pnlMain;
    BackgroundPanel pnlFondo = new BackgroundPanel(fondo);
    BackgroundPanel pnlSideBar = new BackgroundPanel(fondoMenu);
    private Login login;
    private JPanel pnlLogin;
    private Registro registro;
    private JPanel pnlRegistro;
    private Perfil perfil;
    private JPanel pnlPerfil;
    private Foro foro;
    private JPanel pnlForo;
    private Evento evento;
    private JPanel pnlEvento;

    public MainFrame() {
        pnlFondo.setLayout(new BorderLayout());
        pnlFondo.setLayout(new BorderLayout());
        pnlFondo.setVisible(true);
        add(pnlFondo, BorderLayout.CENTER);
        validate();

        loadLoginRegistro();
        configurarLogin();
        configurarRegistro();
        changeToLogin();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    private void loadLoginRegistro() {
        if (pnlLogin == null) {
            login = new Login();
            pnlLogin = login.getPnlLogin();
            pnlLogin.setOpaque(false);
        }
        if (pnlRegistro == null) {
            registro = new Registro();
            pnlRegistro = registro.getPnlRegistro();
            pnlRegistro.setOpaque(false);
        }
    }

    private void unloadLoginRegistro() {
        pnlLogin.setVisible(false);
        pnlRegistro.setVisible(false);
        pnlFondo.remove(pnlLogin);
        pnlFondo.remove(pnlRegistro);
        pnlFondo.validate();
        pnlLogin = null;
        pnlRegistro = null;
    }

    private void loadMain() {
        pnlSideBar.setLayout(new BorderLayout());
        pnlSideBar.add(pnlMenu, BorderLayout.CENTER);
        if (pnlPerfil == null) {
            perfil = new Perfil();
            pnlPerfil = perfil.getPnlPerfil();
            pnlPerfil.setOpaque(false);
        }
        if (pnlForo == null) {
            foro = new Foro();
            pnlForo = foro.getPnlForo();
            pnlForo.setOpaque(false);
        }
        if (pnlEvento == null) {
            evento = new Evento();
            pnlEvento = evento.getPnlEvento();
            pnlEvento.setOpaque(false);
        }
        pnlSideBar.setVisible(true);
        pnlFondo.add(pnlSideBar, BorderLayout.EAST);
        pnlFondo.validate();
    }

    private void unloadMain() {
        pnlPerfil.setVisible(false);
        pnlForo.setVisible(false);
        pnlEvento.setVisible(false);
        pnlMenu.setVisible(false);
        pnlSideBar.setVisible(false);
        pnlFondo.remove(pnlPerfil);
        pnlFondo.remove(pnlForo);
        pnlFondo.remove(pnlEvento);
        pnlFondo.remove(pnlMenu);
        pnlFondo.remove(pnlSideBar);
        pnlFondo.validate();
        pnlPerfil = null;
        pnlForo = null;
        pnlEvento = null;
    }

    private void configurarMenu() {
        menu.getBttnPerfil().addActionListener(e -> changeToPerfil());
        menu.getBttnForo().addActionListener(e -> changeToForo());
        menu.getBttnLogout().addActionListener(e -> {
            if (JOptionPane.showConfirmDialog(null, "¿Está seguro que desea cerrar sesión?", "Cerrar Sesión", JOptionPane.YES_NO_OPTION) == 0) {
                MainFrame.this.dispose();
            }
        });
    }

    private void changeToLogin() {
        pnlLogin.setVisible(true);
        pnlRegistro.setVisible(false);
        pnlFondo.add(pnlLogin, BorderLayout.CENTER);
        setTitle("Iniciar Sesión");
        pnlFondo.validate();
    }

    private void configurarLogin() {
        DAO dao = new DAO();
        login.getBttnLogin().addActionListener(e -> {
            String correo = login.getTxtCorreo().getText();
            String contrasenia = login.getTxtContrasenia().getText();

            if (correo.equals("") || contrasenia.equals("")) {
                JOptionPane.showMessageDialog(null, "Por favor, llene todos los campos.");
            } else {
                dao.conectarBD(clase, url, user, pass);
                try {
                    usuario = dao.loggearUsuario(correo, contrasenia);
                    usuario.next();
                    correoUsuario = usuario.getString("Correo");
                    id_usuario = usuario.getInt("ID_Usuario");
                    unloadLoginRegistro();
                    loadMain();
                    configurarMenu();
                    changeToPerfil();
                    configurarPerfil();
                    configurarForo();
                } catch (SQLException ex) {
                    Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(this, "No se encontró el usuario.", "Error al Iniciar Sesión", JOptionPane.ERROR_MESSAGE);
                }
                dao.desconectarBD();
            }
        });
        login.getBttnRegistrarse().addActionListener(e -> changeToRegistro());
    }

    private void changeToRegistro() {
        pnlLogin.setVisible(false);
        pnlRegistro.setVisible(true);
        pnlFondo.add(pnlRegistro, BorderLayout.CENTER);
        setTitle("Registrarse");
        pnlFondo.validate();
    }

    private void configurarRegistro() {
        DAO dao = new DAO();
        registro.getBttnRegistrarUsuario().addActionListener(e -> {
            String nombre = registro.getTxtNombre().getText();
            String apellidoPaterno = registro.getTxtApellidoPaterno().getText();
            String apellidoMaterno = registro.getTxtApellidoMaterno().getText();
            String correo = registro.getTxtCorreo().getText();
            String contrasenia = registro.getTxtContrasenia().getText();
            String contraseniaRep = registro.getTxtContraseniaRep().getText();

            if (
                    nombre.isEmpty() ||
                            apellidoPaterno.isEmpty() ||
                            apellidoMaterno.isEmpty() ||
                            correo.isEmpty() ||
                            contrasenia.isEmpty() ||
                            contraseniaRep.isEmpty()
            ) {
                JOptionPane.showMessageDialog(this, "Debe capturar todos los datos.", "Faltan Datos", JOptionPane.ERROR_MESSAGE);
            } else {
                dao.conectarBD(clase, url, user, pass);
                String mensaje = dao.registrarUsuario(nombre, correo, apellidoPaterno, apellidoMaterno, contrasenia);
                JOptionPane.showMessageDialog(this, mensaje, "Estado de Registro", JOptionPane.INFORMATION_MESSAGE);
                dao.desconectarBD();
                changeToLogin();
            }
        });
        registro.getBttnCancelarRegistro().addActionListener(e -> changeToLogin());
    }

    private void changeToPerfil() {
        pnlPerfil.setVisible(true);
        pnlForo.setVisible(false);
        pnlEvento.setVisible(false);

        menu.getBttnPerfil().setVisible(false);
        menu.getBttnForo().setVisible(true);

        pnlFondo.add(pnlPerfil, BorderLayout.CENTER);
        setTitle("Perfil");
        pnlFondo.validate();
    }

    private void configurarPerfil() {
        DAO dao = new DAO();
        dao.desconectarBD();
        dao.conectarBD(clase, url, user, pass);
        ResultSet infoUsuario = dao.informacionPerfil(correoUsuario);
        String datos;

        DefaultTableModel eventoModel;
        try {
            infoUsuario.next();
            datos = infoUsuario.getString("nombreUsuario");
            perfil.getLblNombre().setText(datos);
            datos = infoUsuario.getString("apellidoPaternoUsuario");
            perfil.getLblApellidoPaterno().setText(datos);
            datos = infoUsuario.getString("apellidoMaternoUsuario");
            perfil.getLblApellidoMaterno().setText(datos);
            datos = infoUsuario.getString("Correo");
            perfil.getLblCorreo().setText(datos);

            perfil.getTblEventos().setModel(new DefaultTableModel(
                    new Object[][] {
                            {null, null, null}
                    },
                    new String[] {
                            "Evento", "Descripción", "Fecha"
                    }
            ));
            eventoModel = (DefaultTableModel) perfil.getTblEventos().getModel();
            eventoModel.setRowCount(0);
            do {
                eventoModel.addRow(new String[]{
                        infoUsuario.getString("nombre"),
                        infoUsuario.getString("direccion"),
                        infoUsuario.getString("fecha")
                });
            } while (infoUsuario.next());
            dao.desconectarBD(); //Desconectamos la BD
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        perfil.getBttnExportarEventos().addActionListener(e -> {
            Connection conexion = dao.conectarBD(clase, url, user, pass);

            try {
                File archivo = new File("C:\\Users\\Braulio\\OneDrive - Tecnológico Nacional de México Campus Orizaba\\07_Semestre\\07_Arquitectura de Software para la Web\\Unidad 02\\Proyecto_Reportes\\src\\reportes\\Eventos_Usuario.jrxml");
                JasperDesign jasperDesign = JRXmlLoader.load(archivo);
                JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, conexion);
                JasperViewer.viewReport(jasperPrint, false);
            } catch (JRException ex) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }

            dao.desconectarBD();
        });
    }

    private void changeToForo() {
        pnlPerfil.setVisible(false);
        pnlForo.setVisible(true);
        pnlEvento.setVisible(false);

        menu.getBttnPerfil().setVisible(true);
        menu.getBttnForo().setVisible(false);

        pnlFondo.add(pnlForo, BorderLayout.CENTER);
        setTitle("Foro");
        pnlFondo.validate();
    }

    private void configurarForo() {
        DAO dao = new DAO();
        totalEventos = 0;
        dao.desconectarBD();
        dao.conectarBD(clase, url, user, pass);
        eventos = dao.selectEventos();

        try {
            foro.getBttnSiguiente().setEnabled(true);
            eventos.beforeFirst();
            while (eventos.next()) {
                totalEventos++;
            }
            eventos.beforeFirst();
            eventos.next();
            foro.getLblNombreEvento().setText(eventos.getString("nombre"));
            foro.getLblDireccionEvento().setText(eventos.getString("direccion"));
            foro.getLblFechaEvento().setText(eventos.getString("fecha"));
            foro.getLblEventoActual().setText(eventos.getString("ID_Evento"));
            foro.getLblTotalEventos().setText(String.valueOf(totalEventos) + "    ");
            foro.getBttnAnterior().setEnabled(false);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        foro.getBttnAnterior().addActionListener(e -> {
            try {
                // Cambiar el evento al siguiente y asegurar que el botón de evento anterior está habilitado.
                if (eventos.previous()) {
                    foro.getLblNombreEvento().setText(eventos.getString("nombre"));
                    foro.getLblDireccionEvento().setText(eventos.getString("direccion"));
                    foro.getLblFechaEvento().setText(eventos.getString("fecha"));
                    foro.getLblEventoActual().setText(eventos.getString("ID_Evento"));
                    foro.getBttnSiguiente().setEnabled(true);
                    if (eventos.isFirst())
                        foro.getBttnAnterior().setEnabled(false);
                }
            } catch (SQLException ex) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        foro.getBttnSiguiente().addActionListener(e -> {
            try {
                // Cambiar el evento al siguiente y asegurar que el botón de evento anterior está habilitado.
                if (eventos.next()) {
                    foro.getLblNombreEvento().setText(eventos.getString("nombre"));
                    foro.getLblDireccionEvento().setText(eventos.getString("direccion"));
                    foro.getLblFechaEvento().setText(eventos.getString("fecha"));
                    foro.getLblEventoActual().setText(eventos.getString("ID_Evento"));
                    foro.getBttnAnterior().setEnabled(true);
                    if (eventos.isLast())
                        foro.getBttnSiguiente().setEnabled(false);
                }
            } catch (SQLException ex) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        foro.getBttnVerMas().addActionListener(e -> {
            changeToEvento();
            configurarEvento();
        });
    }

    private void changeToEvento() {
        pnlPerfil.setVisible(false);
        pnlForo.setVisible(false);
        pnlEvento.setVisible(true);

        menu.getBttnPerfil().setVisible(true);
        menu.getBttnForo().setVisible(true);

        pnlFondo.add(pnlEvento, BorderLayout.CENTER);
        setTitle("Evento");
        pnlFondo.validate();
    }

    private void configurarEvento() {
        DAO dao = new DAO();
        dao.desconectarBD();
        dao.conectarBD(clase, url, user, pass);
        ResultSet infoEvento;
        String datos;
        evento.getTblPonencias().setModel(new DefaultTableModel(
                new Object[][] {
                        {null, null}
                },
                new String[] {
                        "Ponencia", "Descripción"
                }
        ));
        DefaultTableModel ponenciasModel;
        try {
            infoEvento = dao.informacionEvento(eventos.getInt("ID_Evento"));
            infoEvento.next();
            datos = infoEvento.getString("nombre");
            evento.getLblNombreEvento().setText(datos);

            ponenciasModel = (DefaultTableModel) evento.getTblPonencias().getModel();
            ponenciasModel.setRowCount(0);
            do {
                ponenciasModel.addRow(new String[]{
                        infoEvento.getString("tema"),
                        infoEvento.getString("documentacion")
                });
            } while (infoEvento.next());
            dao.desconectarBD(); //Desconectamos la BD
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        evento.getBttnComentar().addActionListener(e -> {
            DAO daoComentario = new DAO();
            daoComentario.conectarBD(clase, url, user, pass);
            try {
                if (evento.getTxtComentario().getText().trim().length() > 0) {
                    String mensaje = daoComentario.insertarComentario(evento.getTxtComentario().getText().trim(), eventos.getInt("ID_Evento"), id_usuario);
                    JOptionPane.showMessageDialog(this, mensaje, "Comentario", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "No se puede insertar un comentario vacío", "Comentario Vacío", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            daoComentario.desconectarBD();
        });

        evento.getBttnExportarComentarios().addActionListener(e -> {
            Connection conexion = dao.conectarBD(clase, url, user, pass);

            String ruta = "src/img/Comentarios_Evento.jasper";
            JasperReport reporte = null;

            try {
                reporte = (JasperReport) JRLoader.loadObjectFromFile(ruta);
                JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, null, conexion);
                JasperViewer viewer = new JasperViewer(jasperPrint);
                viewer.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                viewer.setVisible(true);
                viewer.setTitle("Reporte de Comentarios");
            } catch (JRException ex) {
                throw new RuntimeException(ex);
            }

            dao.desconectarBD();
        });

        evento.getBttnExportarPonencias().addActionListener(e -> {
            Connection conexion = dao.conectarBD(clase, url, user, pass);

            String ruta = "src/img/Ponencias_Evento.jasper";
            JasperReport reporte = null;

            try {
                reporte = (JasperReport) JRLoader.loadObjectFromFile(ruta);
                JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, null, conexion);
                JasperViewer viewer = new JasperViewer(jasperPrint);
                viewer.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                viewer.setVisible(true);
                viewer.setTitle("Reporte de Ponencias");
            } catch (JRException ex) {
                throw new RuntimeException(ex);
            }

            dao.desconectarBD();
        });
    }
}