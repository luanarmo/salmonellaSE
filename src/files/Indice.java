package files;

import java.io.*;

public class Indice {

    FileWriter archivoIndice;
    FileReader archInd;
    //String nombre = "/src/files/indiceN.bin";
    private final String FILE_NAME = System.getProperty("user.dir") + "/src/files/indiceN.bin";
    Arbol ar = new Arbol();
    int direccion;

    public void Insercion(int llave, int tamanioArch) throws IOException {
        archivoIndice = new FileWriter(FILE_NAME, true); //ABRIR O CREAR ARCHIVO INDICE
        PrintWriter archIndice = new PrintWriter(archivoIndice); //ESCRIBIR EN ARCHIVO INDICE
        archIndice.write("" + llave + '_' + tamanioArch + '\n');//ESCRIBIR LLAVE_DIRECCION
        String sentencia = "" + llave + '_' + tamanioArch;//SENTENCIA A SER USADA PARA SEPARAR
        //LLAMAR A OTRO METODO PARA QUE LO SEPARE E INSERTE EN EL ARBOL
        Separar(sentencia);
        archivoIndice.close();
        archIndice.close();
        float tam = new File("indiceN.bin").length();
        //System.out.println(tam);
        ar.nuevo(llave, tamanioArch);
    }

    public int Recuperacion(int llave) throws IOException {
        archInd = new FileReader(FILE_NAME); //LEER DEL ARCHIVO INDICE
        //MANDAR A LLAMAR A BUSQUEDA DE ARBOL PARA TENER LA DIRECCION
        direccion = ar.buscar(llave);
        archInd.close();
        return direccion;
    }

    public void Modificar(int llave) throws IOException {
        Recuperacion(llave);
        //MANDAR A LLAMAR A MAESTRO PARA MODIFICAR


    }

    public void Eliminar(int llave) throws IOException {
        Recuperacion(llave);
        //MANDAR A LLAMAR ELIMINAR DE ARBOL
        ar.eliminar(llave);
        //MANDAR A LLAMAR ELIMINAR DE MAESTRO
    }

    public void Mantenimiento() throws IOException {
        String oracion = ar.ordenar();
        BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME));
        bw.write("");
        bw.close();
        archivoIndice = new FileWriter(FILE_NAME, true); //ABRIR O CREAR ARCHIVO INDICE
        PrintWriter archIndice = new PrintWriter(archivoIndice);
        String[] llenar = oracion.split(",");
        for (int i = 0; i < llenar.length; i++) {
            archIndice.write(llenar[i] + '\n');
        }
        archIndice.close();

    }

    public void Separar(String sentencia) {
        String[] array = sentencia.split("_");//SEPARA LA LLAVE_DIRECCION
        ar.nuevo(Integer.parseInt(array[0]), Integer.parseInt(array[1]));//INSERTA LLAVE Y DIRECCION EN EL ARBOL
    }

    public int llaveMasGrande() throws IOException {
        archInd = new FileReader(FILE_NAME);

        char[] chars = new char[300];
        archInd.read(chars);
        String reading = String.valueOf(chars);
        int key = 0;
        for (String s : reading.split("\n"))
            if (s.trim().length() > 0) {
                String[] vals = s.split("_");
                key = Math.max(Integer.parseInt(vals[0]), key);
            }
        archInd.close();

        return key;
    }

    public void construirArbol() throws IOException {
        archInd = new FileReader(FILE_NAME);

        char[] chars = new char[300];
        archInd.read(chars);
        String reading = String.valueOf(chars);
        int key = 0;
        for (String s : reading.split("\n"))
            if (s.trim().length() > 0) {
                String[] vals = s.split("_");
                ar.nuevo(Integer.parseInt(vals[0]), Integer.parseInt(vals[0]));
            }
        archInd.close();
    }
}
