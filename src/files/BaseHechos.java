package files;

import java.util.*;
/**
 * @dominios Son los dominios de cada variable.
 * @actuales Lista de listas de String.
 * @hechos Lista de hechos.
 */
public class BaseHechos {
    Map<String,List<String>> dominios;
    List<List<String>> actuales;
    List<Predicado> hechos;
    /**
     * @param dominios se reciben los dominios.
     * @param actuales se reciben los actuales.
     * @param hechos se reciben los hechos.
     * */
    public BaseHechos(Map<String,List<String>> dominios, List<List<String>> actuales, List<Predicado> hechos) {
        this.dominios = dominios;
        this.actuales = actuales;
        this.hechos = hechos;
    }
    /**
     * @method Este metodo instancia al objeto Base de Hechos y no recibe ningun parametro pero iniciliza todas las listas.
     */
    public BaseHechos() {
        this.dominios = new HashMap<>();
        this.actuales = new ArrayList<>();
        this.hechos = new ArrayList<>();
    }
    /**
     * @method Devuelve un mapa que tiene por llave un String y por valor una lista de String.

     */
    public Map<String,List<String>> getDominios() {
        return dominios;
    }
    /**
     *
     *
     * **/
    public void agregarDominios(String variable,List<String> dominios) {
        this.dominios.put(variable,dominios);
    }

    public List<List<String>> getActuales() {
        return actuales;
    }

    public void agregarActuales(List<String> actuales) {
        this.actuales.add(actuales);
    }

    public List<Predicado> getHechos() {
        return hechos;
    }

    public void agregarHechos(String hechos) {
        String[] aux = hechos.split("-");
        String[] aux1 = aux[1].split(",");
        this.hechos.add(new Predicado(aux[0], Arrays.asList(aux1)));
    }
    public void agregarHechos(List<Predicado> hechos) {
        this.hechos.addAll(hechos);
    }

    public void setDominios(Map<String,List<String>> dominios)
    {
        this.dominios = dominios;
    }
}