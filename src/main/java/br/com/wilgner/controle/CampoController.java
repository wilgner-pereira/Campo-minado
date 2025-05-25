package br.com.wilgner.controle;

import br.com.wilgner.modelo.Campo;

public class CampoController {
    private final Campo campo;

    public CampoController(Campo campo) {
        this.campo = campo;
    }

    public void abrirCampo() {
        campo.abrir();
    }

    public void alternarMarcado(){
        campo.alternarMarcado();
    }

    public Campo getCampo() {
        return campo;
    }

}
