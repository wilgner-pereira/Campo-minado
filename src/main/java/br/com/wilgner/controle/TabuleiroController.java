package br.com.wilgner.controle;

import br.com.wilgner.modelo.ResultadoEvento;
import br.com.wilgner.modelo.Tabuleiro;

import java.util.function.Consumer;

public class TabuleiroController {
    private final Tabuleiro tabuleiro;

    public TabuleiroController(int linhas, int colunas, int minas) {
        this.tabuleiro = new Tabuleiro(linhas, colunas, minas);
    }

    public Tabuleiro getTabuleiro() {
        return tabuleiro;
    }
    public int getLinhas(){
        return tabuleiro.getLinhas();
    }

    public int getColunas(){
        return tabuleiro.getColunas();
    }

    public void abrir(int linha, int coluna){
        tabuleiro.abrir(linha, coluna);
    }

    public void alternarMarcacao(int linha, int coluna){
        tabuleiro.alternarMarcacao(linha, coluna);
    }
    public void reiniciar(){
        tabuleiro.reiniciar();
    }

    public void registrarObservador(Consumer<ResultadoEvento> observador){
        tabuleiro.registrarObservador(observador);
    }
}
