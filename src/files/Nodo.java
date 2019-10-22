package files;

public class Nodo {
    Par p;
    Nodo izq;
    Nodo der;
    int altura;
    
    public Nodo(Par info) {
        p = info;
        izq=null;
        der=null;
        altura = 0;
    }
    
    public Par getP() {
        return p;
    }

    public void setP(Par p) {
        this.p = p;
    }

    public Nodo getIzq() {
        return izq;
    }

    public void setIzq(Nodo izq) {
        this.izq = izq;
    }

    public Nodo getDer() {
        return der;
    }

    public void setDer(Nodo der) {
        this.der = der;
    }
    
    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }
}
