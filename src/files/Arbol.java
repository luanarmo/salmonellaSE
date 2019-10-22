package files;

public class Arbol {

    Nodo raiz;
    String fin = "";
    String oracion = "";

    public Arbol() {
        raiz = null;
    }

    public boolean vacio() {
        return raiz == null;
    }

    public void nuevo(int llave, int dirMemoria) {
        Par datos = new Par(llave, dirMemoria);
        raiz = nuevo(datos, raiz);
    }

    public Nodo nuevo(Par d, Nodo r) {
        if (r == null) {
            r = new Nodo(d);
        } else if (d.llave < r.p.llave) {
            r.izq = nuevo(d, r.izq);
            while (altura(r.izq) - altura(r.der) >= 2) {
                if (d.llave < r.izq.p.llave) {
                    r = rotarIzquierda(r);
                } else {
                    r = dobleRotacionIzquierda(r);
                }
            }
        } else if (d.llave > r.p.llave) {
            r.der = nuevo(d, r.der);
            if (altura(r.der) - altura(r.izq) >= 2) {
                if (d.llave > r.der.p.llave) {
                    r = rotarDerecha(r);
                } else {
                    r = dobleRotacionDerecha(r);
                }
            }
        }

        r.altura = maximo(altura(r.izq), altura(r.der)) + 1;
        return r;
    }

    public void balancear() {

    }

    public int buscar(int llave) {
        return buscar(raiz, llave);
    }

    public String ordenar() {
        preorden(raiz);
        return oracion;
    }

    private void preorden(Nodo r) {
        if (r != null) {
            oracion = oracion + r.p.llave + '_' + r.p.direccion + ',';
            preorden(r.izq);
            preorden(r.der);
        }
    }

    private int buscar(Nodo raiz, int llave) {
        boolean found = false;
        int dir = 0;

        while ((raiz != null) && !found) {
            int rval = raiz.p.llave;
            if (llave < rval) {
                raiz = raiz.izq;
            } else if (llave > rval) {
                raiz = raiz.der;
            } else {
                found = true;
                break;
            }
            dir = buscar(raiz, llave);
        }
        return raiz.p.direccion;
    }

    public void eliminar(int llave) {
        raiz = eliminar(raiz, llave);
    }

    public Nodo eliminar(Nodo R, int D) {
        if (R == null) {
            return R;
        }
        if (D < R.p.llave) {
            R.izq = eliminar(R.izq, D);
        } else if (D > R.p.llave) {
            R.der = eliminar(R.der, D);
        } else {
            if (R.izq == null) {
                return R.der;
            } else if (R.der == null) {
                return R.izq;
            }

            R.p.llave = minimo(R.der);
            R.der = eliminar(R.der, R.p.llave);

        }
        return R;
    }

    public int minimo(Nodo R) {
        int vm;
        vm = R.p.llave;
        while (R.izq != null) {
            vm = R.izq.p.llave;
            R = R.izq;
        }
        return vm;
    }

    private Nodo rotarIzquierda(Nodo n2) {
        Nodo n1 = n2.izq;
        n2.izq = n1.der;
        n1.der = n2;
        n2.altura = maximo(altura(n2.izq), altura(n2.der)) + 1;
        n1.altura = maximo(altura(n1.izq), n2.altura) + 1;
        return n1;
    }

    private Nodo rotarDerecha(Nodo n1) {
        Nodo n2 = n1.der;
        n1.der = n2.izq;
        n2.izq = n1;
        n1.altura = maximo(altura(n1.izq), altura(n1.der)) + 1;
        n2.altura = maximo(altura(n2.der), n1.altura) + 1;
        return n2;
    }

    private Nodo dobleRotacionIzquierda(Nodo n3) {
        n3.izq = rotarIzquierda(n3.izq);
        return rotarIzquierda(n3);
    }

    private Nodo dobleRotacionDerecha(Nodo n1) {
        n1.der = rotarIzquierda(n1.der);
        return rotarIzquierda(n1);
    }

    private int maximo(int lhs, int rhs) {
        if (lhs > rhs) {
            return lhs;
        } else {
            return rhs;
        }
    }

    private int altura(Nodo r) {
        if (r == null) {
            return -1;
        } else {
            return r.altura;
        }
    }
}
