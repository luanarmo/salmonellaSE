package files;

public class Par {
    int llave;
    int direccion;
    
    public Par(int key, int dir){
        llave = key;
        direccion = dir;
    }
    
    public int getLlave() {
        return llave;
    }

    public void setLlave(int llave) {
        this.llave = llave;
    }

    public int getDireccion() {
        return direccion;
    }

    public void setDireccion(int direccion) {
        this.direccion = direccion;
    }   
}