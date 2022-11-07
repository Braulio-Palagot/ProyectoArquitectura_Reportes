package view;

import javax.swing.*;

public class Registro extends JFrame {
    private JLabel imgRegistro;
    private JTextField txtNombre;
    private JButton bttnRegistrarUsuario;
    private JTextField txtApellidoPaterno;
    private JTextField txtApellidoMaterno;
    private JTextField txtCorreo;
    private JTextField txtContrasenia;
    private JTextField txtContraseniaRep;
    private JPanel pnlRegistro;
    private JButton bttnCancelarRegistro;

    public JLabel getImgRegistro() {
        return imgRegistro;
    }

    public JTextField getTxtNombre() {
        return txtNombre;
    }

    public JButton getBttnRegistrarUsuario() {
        return bttnRegistrarUsuario;
    }

    public JTextField getTxtApellidoPaterno() {
        return txtApellidoPaterno;
    }

    public JTextField getTxtApellidoMaterno() {
        return txtApellidoMaterno;
    }

    public JTextField getTxtCorreo() {
        return txtCorreo;
    }

    public JTextField getTxtContrasenia() {
        return txtContrasenia;
    }

    public JTextField getTxtContraseniaRep() {
        return txtContraseniaRep;
    }

    public JPanel getPnlRegistro() {
        return pnlRegistro;
    }

    public JButton getBttnCancelarRegistro() {
        return bttnCancelarRegistro;
    }
}