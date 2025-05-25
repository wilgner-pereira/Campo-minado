package br.com.wilgner.visao;

import br.com.wilgner.controle.TabuleiroController;

import javax.swing.*;
import java.awt.*;

public class PainelTabuleiro extends JPanel {


    public PainelTabuleiro(TabuleiroController controller) {


        setLayout(new GridLayout(controller.getTabuleiro().getLinhas(), controller.getTabuleiro().getColunas()));


        controller.getTabuleiro().paraCada(c -> add(new BotaoCampo(c)));


        controller.registrarObservador(e -> {
            SwingUtilities.invokeLater(() -> {
                if (e.isGanhou()) {
                    JOptionPane.showMessageDialog(this, "Ganhou");
                } else {
                    JOptionPane.showMessageDialog(this, "Perdeu");
                }

                controller.reiniciar();
            });
        });
    }
}
