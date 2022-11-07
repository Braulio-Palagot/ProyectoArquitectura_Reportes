package view;

import javax.swing.*;

public class Perfil {
    private JPanel pnlPerfil;
    private JLabel lblNombre;
    private JTable tblEventos;
    private JLabel imgPerfil;
    private JLabel lblApellidoPaterno;
    private JLabel lblApellidoMaterno;
    private JLabel lblCorreo;
    private JButton bttnExportarEventos;

    public JPanel getPnlPerfil() {
        return pnlPerfil;
    }

    public JLabel getLblNombre() {
        return lblNombre;
    }

    public JTable getTblEventos() {
        return tblEventos;
    }

    public JLabel getImgPerfil() {
        return imgPerfil;
    }

    public JLabel getLblApellidoPaterno() {
        return lblApellidoPaterno;
    }

    public JLabel getLblApellidoMaterno() {
        return lblApellidoMaterno;
    }

    public JLabel getLblCorreo() {
        return lblCorreo;
    }

    public JButton getBttnExportarEventos() {
        return bttnExportarEventos;
    }
}