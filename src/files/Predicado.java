package files;

import java.util.ArrayList;
import java.util.List;

public class Predicado {
    String nombre;
    List<String> variables;

    public Predicado(String valor, List<String> variables) {
        this.nombre = valor;
        this.variables = variables;
    }

    public Predicado() {
        List<String> variables = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<String> getVariables() {
        return variables;
    }

    public void setVariables(List<String> variables) {
        this.variables = variables;
    }

    @Override
    public String toString() {
        return "Predicado{" +
                "nombre='" + nombre + '\'' +
                ", variables=" + variables +
                '}';
    }
}
