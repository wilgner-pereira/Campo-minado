package br.com.wilgner.modelo;


import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Tabuleiro implements CampoObservador {

    private final int linhas, colunas, minas;


    private final List<Campo> campos = new ArrayList<>();
    private final List<Consumer<ResultadoEvento>> observadores = new ArrayList<>();

    public Tabuleiro(int linhas, int colunas, int minas) {
        this.linhas = linhas;
        this.colunas = colunas;
        this.minas = minas;

        gerarCampos();
        associarOsVizinhos();
        sortearMinas();
    }

    public void registrarObservador(Consumer<ResultadoEvento> observador) {
        observadores.add(observador);
    }

    private void notificarObservadores(boolean resultado){
        observadores.stream().forEach(o -> o.accept(new ResultadoEvento(resultado)));
    }

    public void abrir(int linha, int coluna) {
            campos.stream()
                    .filter(c -> c.getLinha() == linha && c.getColuna() == coluna)
                    .findFirst()
                    .ifPresent(c -> c.abrir());
    }



    public void alternarMarcacao(int linha, int coluna) {
        campos.stream()
                .filter(c -> c.getLinha() == linha && c.getColuna() == coluna)
                .findFirst()
                .ifPresent(c -> c.alternarMarcado()); // <- correto
    }


    private void gerarCampos(){
        for(int i = 0; i < linhas; i++){
            for(int j = 0; j < colunas; j++){
                Campo c = new Campo(i, j);
                c.registrarObservador(this);
                campos.add(c);
            }
        }
    }

    private void associarOsVizinhos(){
        for(Campo c1 : campos){
            for(Campo c2 : campos){
                c1.adicionarVizinho(c2);
            }
        }
    }

    private void sortearMinas(){
        long minasArmadas = 0;
        Predicate<Campo> minado = c -> c.isMinado();
        do{

            int aleatorio = (int) (Math.random() * campos.size());
            campos.get(aleatorio).minar();
            minasArmadas = campos.stream().filter(minado).count();
        } while(minasArmadas < minas);
    }

    public boolean objetivoAlcancado(){
        return campos.stream().allMatch(c -> c.objetivoAlcancancado());
    }

    public void reiniciar(){
        campos.stream().forEach(c -> c.reiniciar());
        sortearMinas();
    }

    @Override
    public void eventoOcorreu(Campo campo, CampoEvento evento) {
        if(evento == CampoEvento.EXPLODIR){
            mostrarMinas();
            notificarObservadores(false);
        } else if(objetivoAlcancado()){
            System.out.println("Ganhou");
            notificarObservadores(true);
        }
    }

    private void mostrarMinas() {
        campos.stream()
                .filter(c -> c.isMinado())
                .filter(c -> !c.isMarcado())
                .forEach(c -> c.setAberto(true));
    }

    public void paraCada(Consumer<Campo> funcao){
        campos.forEach(funcao);
    }

    public int getColunas() {
        return colunas;
    }
    public int getLinhas() {
        return linhas;
    }
    public int getMinas() {
        return minas;
    }
}
