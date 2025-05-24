package br.com.wilgner.visao;

import br.com.wilgner.modelo.Campo;
import br.com.wilgner.modelo.CampoEvento;
import br.com.wilgner.modelo.CampoObservador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class BotaoCampo extends JButton implements CampoObservador, MouseListener {
    private final Color BG_PADRAO = new Color(184,184,184);
    private final Color BG_MARCADO = new Color(8,179,247);
    private final Color BG_EXPLOSAO = new Color(189,66,68);
    private final Color TEXTO_VERDE = new Color(0,100,0);
    private Campo campo;


    public BotaoCampo(Campo campo) {
        this.campo = campo;
        setBackground(BG_PADRAO);
        setBorder(BorderFactory.createBevelBorder(0));
        setOpaque(true);

        addMouseListener(this);
        campo.registrarObservador(this);
    }

    @Override
    public void eventoOcorreu(Campo campo, CampoEvento evento){

        switch (evento){
            case ABRIR:
                aplicarEstiloAbrir();
                break;
            case MARCAR:
                aplicarEstiloMarcar();
                break;
            case EXPLODIR:
                aplicarEstiloExplodir();
                break;
            default:
                aplicarEstiloPadrao();
        }
        SwingUtilities.invokeLater(() ->{
            repaint();
            validate();
        });

    }

    private void aplicarEstiloPadrao() {
        setBackground(BG_PADRAO);
        setBorder(BorderFactory.createBevelBorder(0));
        setText("");
    }

    private void aplicarEstiloExplodir() {
        setBackground(BG_EXPLOSAO);

        setForeground(Color.WHITE);
        setText("X");
    }

    private void aplicarEstiloMarcar() {
        setBackground(BG_MARCADO);
        setForeground(Color.BLACK);
        setText("M");
    }

    private void aplicarEstiloAbrir() {
        setBorder(BorderFactory.createLineBorder(Color.gray));
        if(campo.isMinado()){
            setBackground(BG_EXPLOSAO);
            return;
        }

        setBackground(BG_PADRAO);


        switch(campo.minasNaVizinhanca()){
            case 1:
                setForeground(TEXTO_VERDE);
                    break;
            case 2:
                setForeground(Color.blue);
                    break;
            case 3:
                setForeground(Color.YELLOW);
                    break;
            case 4:
            case 5:
            case 6:
                setForeground(Color.RED);
                    break;
            default:
                setForeground(Color.PINK);
        }

        String valor = !campo.vizinhancaSegura() ?
                campo.minasNaVizinhanca() +"" : "";
        setText(valor);
    }

    // Interface events mouse
    @Override
    public void mousePressed(MouseEvent e){
        if(e.getButton() == MouseEvent.BUTTON1){
            campo.abrir();
        }else{
            campo.alternarMarcado();
        }
    }


    // interface exige, não utilizados
    public void mouseClicked(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}

}
