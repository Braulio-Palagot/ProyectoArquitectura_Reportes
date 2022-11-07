package view;

import javax.swing.*;

public class Login extends JFrame {
    private JPanel pnlLogin;
    private JTextField txtCorreo;
    private JTextField txtContrasenia;
    private JButton bttnLogin;
    private JButton bttnRegistrarse;
    private JLabel imgLogin;

    public JPanel getPnlLogin() {
        return pnlLogin;
    }

    public JTextField getTxtCorreo() {
        return txtCorreo;
    }

    public JTextField getTxtContrasenia() {
        return txtContrasenia;
    }

    public JButton getBttnLogin() {
        return bttnLogin;
    }

    public JButton getBttnRegistrarse() {
        return bttnRegistrarse;
    }

    public JLabel getImgLogin() {
        return imgLogin;
    }
}