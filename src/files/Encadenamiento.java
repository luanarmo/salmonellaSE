package files;

import java.util.*;

public class Encadenamiento {
    public static Scanner scan = new Scanner(System.in);

    private List<Regla> reglas;
    private BaseHechos bh;

    public Encadenamiento(List<String> strings, List<String> hechos, Map<String, List<String>> dominios) {
        this.reglas = new ArrayList<>(convertir(strings));
        this.bh = new BaseHechos();
        for (String hecho : hechos)
            this.bh.agregarHechos(hecho);
        this.bh.setDominios(dominios);
    }

    public void inferencia() {
        int R;
        List<Predicado> nuevosHechos;
        List<Integer> conjuntoConflicto = new ArrayList<>();
        conjuntoConflicto.add(extraeRegla(reglas));

        while (!noVacio(bh.getHechos()) && !noVacio(conjuntoConflicto)) {
            conjuntoConflicto = equiparar(antecedente(reglas), bh, reglas);
//            System.out.println(conjuntoConflicto.toString());

            if (!noVacio(conjuntoConflicto)) {
                R = resolver(conjuntoConflicto, reglas);
//                System.out.println(R);
                if (R < reglas.size()) {
                    nuevosHechos = aplicar(R, reglas, bh, new ArrayList<>());
                    if (!nuevosHechos.isEmpty()) {
//                    System.out.printf("Submeta: '%s'\n", nuevosHechos);
                        actualizar(bh, nuevosHechos);
                        conjuntoConflicto = equiparar(antecedente(reglas), bh, reglas);
                    }
//                }
                }
            }
        }
    }

    public BaseHechos getBh() {
        return bh;
    }

    /*public static void main(String[] args) {
//        bc.add("caballo-x&rapido-x>candidatocompeticion-x");
//        bc.add("caballo-x&padre-x,y&rapido-y>rapido-x");

        bc.add("activa-PhopPhoq>reprime_isl-spi1,PhopPhoq");
        bc.add("activa-PhopPhoq>reprime_isl-spi2,PhopPhoq");
        bc.add("tiene-a,b,c,d>activa-PhopPhoq");
        bc.add("tiene-a,b>activa-x");
        bc.add("tiene-e&tiene-y>activa-y,e");
        bc.add("activa-y,e&activa-y,e>activa_isl-spi2,y");


        List<String> dominio1 = new ArrayList<>();
        List<String> dominio2 = new ArrayList<>();

//        dominio1.add("cometa");
//        dominio1.add("veloz");
//        dominio1.add("bronco");
//        dominio1.add("rayo");

        dominio1.add("bajoOxigeno");
        dominio1.add("altaOsmoralidad");
        dominio1.add("inflamacion");
        dominio1.add("invasion");
        dominio1.add("estresOxi");
        dominio1.add("bajaOsmolaridad");
        dominio1.add("bajoCalcio");
        dominio1.add("phAcido");

        dominio2.add("ssef");
        dominio2.add("sseg");
        dominio2.add("ssraSsrb");

        bh.agregarDominios("a", dominio1);
        bh.agregarDominios("b", dominio1);
        bh.agregarDominios("c", dominio1);
        bh.agregarDominios("d", dominio1);
        bh.agregarDominios("e", dominio1);
        bh.agregarDominios("f", dominio1);
        bh.agregarDominios("y", dominio2);
        bh.agregarDominios("x", dominio2);

//        bh.agregarHechos("caballo-cometa");
//        bh.agregarHechos("caballo-bronco");
//        bh.agregarHechos("caballo-veloz");
//        bh.agregarHechos("caballo-rayo");
//        bh.agregarHechos("padre-cometa,veloz");
//        bh.agregarHechos("padre-cometa,bronco");
//        bh.agregarHechos("padre-veloz,rayo");
//        bh.agregarHechos("rapido-bronco");
//        bh.agregarHechos("rapido-rayo");

        bh.agregarHechos("tiene-estresOxi,bajaOsmolaridad,bajoCalcio,phAcido");
        bh.agregarHechos("tiene-bajoOxigeno,altaOsmolaridad");
        bh.agregarHechos("invasion-phAcido");
        bh.agregarHechos("activa-ssef,estresOxi");
        bh.agregarHechos("tiene-ssef");
        bh.agregarHechos("tiene-phAcido");


//        List<String> preguntas = preguntar(bc);
        int R;
        List<Predicado> nuevosHechos;
        List<Integer> conjuntoConflicto = new ArrayList<>();
        conjuntoConflicto.add(extraeRegla(reglas));

        while (!noVacio(bh.getHechos()) && !noVacio(conjuntoConflicto)) {
            conjuntoConflicto = equiparar(antecedente(reglas), bh, reglas);
            System.out.println(conjuntoConflicto.toString());

            if (!noVacio(conjuntoConflicto)) {
                R = resolver(conjuntoConflicto, reglas);
                System.out.println(R);
//                if (R < reglas.size()) {
                nuevosHechos = aplicar(R, reglas, bh, new ArrayList<>());
                if (!nuevosHechos.isEmpty()) {
//                    System.out.printf("Submeta: '%s'\n", nuevosHechos);
                    actualizar(bh, nuevosHechos);
//                    conjuntoConflicto = equiparar(antecedente(bc), bh.getHechos());
                }
//                }
            }
        }
        for (Predicado pre : bh.getHechos()) {
            System.out.println(pre.toString());
        }
        /*int max = 0;
        for (String string : bh.getHechos()) {
            if (valores.get(string) > max) {
                max = valores.get(string);
            }
        }
        System.out.printf("Resultado: ");
        for (String str : bh.getHechos()) {
            if (valores.get(str) == max) {
                System.out.println(str);
            }
        }*/
//    }

    List<Regla> convertir(List<String> bc) {
        List<Regla> reglas = new ArrayList<>();
        for (String s : bc) {
            String[] revision = s.split(">");
            String[] antecedentes = revision[0].split("&");
            List<Predicado> p = new ArrayList<>();
            for (String a : antecedentes) {
                String[] predicados = a.split("-");
                String[] variables = predicados[1].split(",");
                p.add(new Predicado(predicados[0], Arrays.asList(variables)));
            }
            String[] aux = revision[1].split("-");
            String[] aux1 = aux[1].split(",");
            reglas.add(new Regla(p, new Predicado(aux[0], Arrays.asList(aux1))));
        }

        return reglas;
    }

    List<String> preguntar(List<String> bc) {
        Set<String> antecedentes = new HashSet<>();
        Set<String> consecuentes = new HashSet<>();

        for (String regla : bc) {
            String[] aux = regla.split(">");
            consecuentes.add(aux[1]);
            for (String s : aux[0].split("&"))
                antecedentes.add(s);
        }

        antecedentes.removeAll(consecuentes);
        return new ArrayList<>(antecedentes);
    }

    boolean revisa(List<String> antecedente, List<String> bh) {
        for (String l : antecedente) {
            if (!antecedente.contains(l)) {
                return false;
            }
        }
        return true;
    }

    List<Integer> equiparar(List<List<Predicado>> antecedente, BaseHechos bh, List<Regla> reglas) {
        boolean add;
        int cont = -1;
        List<Integer> list = new ArrayList<>();
        for (List<Predicado> predicados : antecedente) {
            add = false;
            for (int i = 0; i < predicados.size(); i++)
                for (int j = 0; j < bh.getHechos().size(); j++) {
                    List<Boolean> auxilio = new ArrayList<>();
                    if (bh.getHechos().get(j).getNombre().equals(predicados.get(i).getNombre()) &&
                            bh.getHechos().get(j).getVariables().size() == predicados.get(i).getVariables().size()) {
                        for (int k = 0; k < predicados.get(i).getVariables().size(); k++) {
                            List<String> dominio = bh.getDominios().get(predicados.get(i).getVariables().get(k));
                            if (dominio.contains(bh.getHechos().get(j).getVariables().get(k))) {
                                auxilio.add(true);
                            } else {
                                auxilio.add(false);
                            }
                        }
                    }
                    if (!auxilio.contains(false)) {
                        add = true;
                        break;
                    }
                }
            cont++;
            Regla regla = reglas.get(cont);
            if (add && regla.getContador() < 3)
                list.add(cont);
        }
        return list;
    }

    List<List<Predicado>> antecedente(List<Regla> bc) {
        List<List<Predicado>> antecedentes = new ArrayList<>();
        bc.forEach((b) -> {

            antecedentes.add(b.getAntecedentes());
        });
        return antecedentes;
    }

    Integer extraeRegla(List<Regla> bc) {
        if (bc.isEmpty())
            return null;
        else
            return 0;
    }

    boolean noContenida(String meta, List<String> bh) {
        return !bh.contains(meta);
    }

    boolean noVacio(List ConjuntoConflicto) {
        return ConjuntoConflicto.size() == 0;
    }

    int resolver(List<Integer> ConjuntoConflicto, List<Regla> reglas) {
        int i = 0;
        int conjunto;
        Regla regla;
        do {
            try {
                conjunto = ConjuntoConflicto.get(i);
            } catch (IndexOutOfBoundsException e) {
                return reglas.size() + 1;
            }
            regla = reglas.get(conjunto);
            i++;
        } while (regla.getContador() >= 3 && i <= reglas.size());
        //ConjuntoConflicto.remove(0);
        //String[] aux2 = conjunto.split("-");
        return conjunto;
    }

    List<Predicado> aplicar(int R, List<Regla> bc, BaseHechos bh, List<String> preguntas) {
        Map<String, List<String>> variables = new HashMap<>();
        Regla regla = bc.get(R);
        regla.aumentarContador();
//        System.out.println(regla.toString());
        List<String> valores = null;
        List<Predicado> predicados = new ArrayList<>();
        for (Predicado predicado : regla.getAntecedentes()) {
            List<Predicado> coincidencias = igualar(predicado, bh);
//            System.out.println(coincidencias.toString());
            if (coincidencias.size() > 0) {
                for (int i = 0; i < predicado.getVariables().size(); i++) {
                    if (variables.get(predicado.getVariables().get(i)) == null) {
                        Set<String> setCoincidencias = new HashSet<>();
                        for (Predicado coin : coincidencias) {
                            setCoincidencias.add(coin.getVariables().get(i));
                        }
                        variables.put(predicado.getVariables().get(i), new ArrayList<>(setCoincidencias));
                    } else {
                        Set<String> setValores = new HashSet<>(variables.get(predicado.getVariables().get(i)));
                        Set<String> setCoincidencias = new HashSet<>();
                        for (Predicado coin : coincidencias) {
                            setCoincidencias.add(coin.getVariables().get(i));
                        }
                        setValores.retainAll(setCoincidencias);
                        variables.put(predicado.getVariables().get(i), new ArrayList<>(setValores));
                    }
                }
            }
        }
//        System.out.println(variables);
        int foo = 1;
        for (int i = 0; i < regla.getConsecuente().getVariables().size(); i++) {
            try {
                foo *= variables.get(regla.getConsecuente().getVariables().get(i)).size();
            } catch (NullPointerException e) {
                if (bh.getDominios().keySet().contains(regla.getConsecuente().getVariables().get(i)))
//                    continue;
                    return new ArrayList<>();
            }
        }
//        foo=1;

//        System.out.println(foo);
        List<List<String>> combinaciones = new ArrayList<>();
        int exp = 0;
        for (String variable : regla.getConsecuente().getVariables()) {
            int n = (int) Math.pow(2, exp);
            int apuntador = 0;
            for (int i = 0; i < foo; i++) {
                try {
                    combinaciones.get(i).add(variables.get(variable).get(apuntador));
                } catch (IndexOutOfBoundsException e) {
                    combinaciones.add(new ArrayList<>());
                    try {
                        combinaciones.get(i).add(variables.get(variable).get(apuntador));
                    } catch (NullPointerException f) {
                        if (!bh.getDominios().keySet().contains(regla.getConsecuente().getVariables().get(i))) {
                            combinaciones.get(i).add(regla.getConsecuente().getVariables().get(i));
                        } else {
                            return new ArrayList<>();
                        }
                    }
                } catch (NullPointerException e) {
                    if (!bh.getDominios().keySet().contains(regla.getConsecuente().getVariables().get(i))) {
                        combinaciones.get(i).add(regla.getConsecuente().getVariables().get(i));
                    } else {
                        return new ArrayList<>();
                    }
                }
                if ((i + 1) % n == 0) {
                    if (!bh.getDominios().keySet().contains(variable))
                        apuntador = 0;
                    else {
                        if (apuntador >= variables.get(variable).size() - 1)
                            apuntador = 0;
                        else
                            apuntador++;
                    }

                }
            }
            exp++;
        }
        for (List<String> list : combinaciones)
            predicados.add(new Predicado(regla.getConsecuente().getNombre(), list));
        return predicados;
    }

    List<Predicado> igualar(Predicado predicado, BaseHechos bh) {
        List<Predicado> foo = new ArrayList<>();
        for (int j = 0; j < bh.getHechos().size(); j++) {
            List<Boolean> auxilio = new ArrayList<>();
            if (bh.getHechos().get(j).getNombre().equals(predicado.getNombre()) &&
                    bh.getHechos().get(j).getVariables().size() == predicado.getVariables().size()) {
                for (int k = 0; k < predicado.getVariables().size(); k++) {
                    List<String> dominio = bh.getDominios().get(predicado.getVariables().get(k));
                    if (dominio.contains(bh.getHechos().get(j).getVariables().get(k))) {
                        auxilio.add(true);
                    } else {
                        auxilio.add(false);
                    }
                }
            }
            if (!auxilio.contains(false) && !auxilio.isEmpty()) {
                foo.add(bh.getHechos().get(j));
            }
        }
        return foo;
    }

    void actualizar(BaseHechos bh, List<Predicado> nuevosHechos) {
        bh.agregarHechos(nuevosHechos);
    }
}