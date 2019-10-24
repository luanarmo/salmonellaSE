/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package encademientohaciaadelante;

import java.util.ArrayList;
import java.util.List;


public class EncademientoHaciaAdelante {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    List<String> bc= new ArrayList<>();
    bc.add("b&d&e>f");
    bc.add("d&g>a");
    bc.add("c&f>a");
    bc.add("b>x");
    bc.add("d>e");
    bc.add("x&a>h");
    bc.add("c>d");
    bc.add("x&c>a");
    bc.add("x&b>d");
    
    List<String> bh= new ArrayList<>();
    bh.add("b");
    bh.add("c");
    String meta="h";
    String R;
    String nuevosHechos;
    List<String> conjuntoConflicto=new ArrayList<>();
    conjuntoConflicto.add(extraeRegla(bc));
        
    while(noContenida(meta,bh)&&!noVacio(conjuntoConflicto))
    {
        conjuntoConflicto=equiparar(antecedente(bc),bh);
        System.out.println("adsfafa"+conjuntoConflicto.toString());
        if(noVacio(conjuntoConflicto))
        {
            R=resolver(conjuntoConflicto);
            //nuevosHechos = aplicar(R,bh);
            System.out.println("razie;"+conjuntoConflicto.toString());
        }
    }
}
    static List equiparar(List<String> antecedente, List<String> bh)
    {
    //     boolean add = true;
    //     List equiparados=new ArrayList<String>();
    //     for (String string : antecedente) {
    //         add = true;
    //         for (String string2 : bh) {
    //             if(!string.contains(string2))
    //             {
    //                 add = false;
    //             }
    //         }
    //         if(add)
    //             equiparados.add(string);
    //     }
    //     return equiparados;
        boolean add;
        List<String> list = new ArrayList<>();
        for (String string : antecedente) 
        {
            add = true;
            String[] vals = string.split("&");
            for (int i = 0; i < vals.length; i++) {
                if (!bh.contains(vals[i]))
                    add = false;
            }
            if (add)
                list.add(string);
        }
        return list;
    }
    static List<String> antecedente(List<String> bc)
    {

        List<String> antecedentes=new ArrayList<>();
        bc.forEach((b)->{
            String [] array= b.split(">");
            antecedentes.add(array[0]);
        });
        return antecedentes; 
    }
    static String extraeRegla(List <String> bc)
    {
        return bc.get(0);
    }
    
    static boolean noContenida(String meta, List<String> bh)
    {
        return !bh.contains(meta);
    }
    
    static boolean noVacio(List<String> ConjuntoConflicto){
        return ConjuntoConflicto.size()==0;
    }
    
    static String resolver(List <String> ConjuntoConflicto)
    {
        String conjunto=ConjuntoConflicto.get(0);
        ConjuntoConflicto.remove(0);

        return conjunto;
    }
    static void aplicar(String R, List <String> bh){
        
    }
    
    void actualizar(List<String> bh, List<String> nuevosHechos){
        
    }
}
