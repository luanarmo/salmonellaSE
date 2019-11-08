package files;

import java.util.List;

public class Regla {
    List<Predicado> antecedentes;
    Predicado consecuente;
    Integer contador;

    public Regla(List<Predicado> antecedentes, Predicado consecuente) {
        this.antecedentes = antecedentes;
        this.consecuente = consecuente;
        this.contador = 0;
    }

    public List<Predicado> getAntecedentes() {
        return antecedentes;
    }

    public void setAntecedentes(List<Predicado> antecedentes) {
        this.antecedentes = antecedentes;
    }

    public Predicado getConsecuente() {
        return consecuente;
    }

    public void setConsecuente(Predicado consecuente) {
        this.consecuente = consecuente;
    }

    public Integer getContador() {
        return contador;
    }

    public void aumentarContador() {
        this.contador++;
    }

    @Override
    public String toString() {
        return "Regla{" +
                "antecedentes=" + antecedentes +
                ", consecuente=" + consecuente +
                ", contador=" + contador +
                '}';
    }
}
