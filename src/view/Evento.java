package view;

import javax.swing.*;

public class Evento {
    private JPanel pnlEvento;
    private JTable tblPonencias;
    private JButton bttnComentar;
    private JTextArea txtComentario;
    private JLabel lblNombreEvento;
    private JButton bttnExportarComentarios;
    private JButton bttnExportarPonencias;

    public JPanel getPnlEvento() {
        return pnlEvento;
    }

    public JTable getTblPonencias() {
        return tblPonencias;
    }

    public JButton getBttnComentar() {
        return bttnComentar;
    }

    public JTextArea getTxtComentario() {
        return txtComentario;
    }

    public JLabel getLblNombreEvento() {
        return lblNombreEvento;
    }

    public JButton getBttnExportarComentarios() {
        return bttnExportarComentarios;
    }

    public JButton getBttnExportarPonencias() {
        return bttnExportarPonencias;
    }
}