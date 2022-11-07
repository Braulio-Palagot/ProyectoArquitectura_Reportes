package view;

import javax.swing.*;

public class Foro extends JFrame {
    private JPanel pnlForo;
    private JLabel lblNombreEvento;
    private JLabel lblDireccionEvento;
    private JLabel lblFechaEvento;
    private JButton bttnVerMas;
    private JButton bttnAnterior;
    private JButton bttnSiguiente;
    private JLabel lblEventoActual;
    private JLabel lblTotalEventos;
    private JPanel imgEvento;

    public JPanel getPnlForo() {
        return pnlForo;
    }

    public JLabel getLblNombreEvento() {
        return lblNombreEvento;
    }

    public JLabel getLblDireccionEvento() {
        return lblDireccionEvento;
    }

    public JLabel getLblFechaEvento() {
        return lblFechaEvento;
    }

    public JButton getBttnVerMas() {
        return bttnVerMas;
    }

    public JButton getBttnAnterior() {
        return bttnAnterior;
    }

    public JButton getBttnSiguiente() {
        return bttnSiguiente;
    }

    public JLabel getLblEventoActual() {
        return lblEventoActual;
    }

    public JLabel getLblTotalEventos() {
        return lblTotalEventos;
    }

    public JPanel getImgEvento() {
        return imgEvento;
    }
}