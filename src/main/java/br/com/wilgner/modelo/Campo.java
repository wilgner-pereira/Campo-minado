package br.com.wilgner.modelo;
import java.util.ArrayList;
import java.util.List;

public class Campo {

    private final int linha, coluna;
    private boolean minado, aberto, marcado;

    private List<Campo> vizinhos = new ArrayList<>();
    private List<CampoObservador> observadores = new ArrayList<>();

     public Campo(int linha, int coluna){
        this.linha = linha;
        this.coluna = coluna;
    }

    public void registrarObservador(CampoObservador observador){
         observadores.add(observador);
    }

    private void notificarObservadores(CampoEvento evento){
         observadores.stream().forEach(o -> o.eventoOcorreu(this, evento));
    }

    public boolean adicionarVizinho(Campo vizinho) {
        int deltaLinha = Math.abs(linha - vizinho.linha);
        int deltaColuna = Math.abs(coluna - vizinho.coluna);

        if(deltaLinha == 0 && deltaColuna == 0) {
            return false; // é o próprio campo
        }

        if(deltaLinha <= 1 && deltaColuna <= 1) {
            vizinhos.add(vizinho);
            return true;
        }

        return false;
    }

    public void alternarMarcado() {
        if(!aberto) {
            marcado = !marcado;

            if(marcado) {
                notificarObservadores(CampoEvento.MARCAR);
            } else {
                notificarObservadores(CampoEvento.DESMARCAR);
            }
        }
    }

    public boolean abrir() {
        if (!aberto && !marcado) {
            if (minado) {
                notificarObservadores(CampoEvento.EXPLODIR);
                return true;
            }
            setAberto(true);
            if (vizinhancaSegura()) {
                vizinhos.stream()
                        .filter(v -> !v.aberto)
                        .forEach(v -> v.abrir());
            }

            return true;
        } else {
            return false;
        }
    }


    public boolean vizinhancaSegura(){
         return vizinhos.stream().noneMatch( v -> v.minado);
    }

    void minar(){
         if(!minado) {
             minado = true;
         }
    }

    public boolean isMarcado() {
         return marcado;
    }

    public boolean isAberto() {
         return aberto;
    }

    public boolean isFechado() {
        return !isAberto();
    }

    public boolean isMinado() {
         return minado;
    }

    public int getLinha() {
         return linha;
    }
    public int getColuna() {
         return coluna;
    }

    public void setAberto(boolean aberto){
         this.aberto = aberto;

         if(aberto) {
             notificarObservadores(CampoEvento.ABRIR);
         }
    }

    public boolean objetivoAlcancancado(){
        boolean desvendado = !minado && aberto;
        boolean protegido = minado && marcado;
        return desvendado || protegido;
    }


    public int minasNaVizinhanca(){
         return (int)vizinhos.stream().filter( v -> v.minado).count();
    }

    void reiniciar(){
         aberto = false;
         minado = false;
         marcado = false;
         notificarObservadores(CampoEvento.REINICIAR);
    }




}
