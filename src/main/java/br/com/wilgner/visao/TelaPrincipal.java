package br.com.wilgner.visao;

import br.com.wilgner.modelo.Tabuleiro;

import javax.swing.*;

public class TelaPrincipal extends JFrame {

    public TelaPrincipal() {
        Tabuleiro tabuleiro = new Tabuleiro(16,30,50);
        PainelTabuleiro painelTabuleiro = new PainelTabuleiro(tabuleiro);

        add(painelTabuleiro);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(690, 438);
        setTitle("Campo Minado");
        setVisible(true);
    }

    public static void main(String[] args) {
        new TelaPrincipal();
    }

}
