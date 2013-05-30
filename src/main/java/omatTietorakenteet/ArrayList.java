package omatTietorakenteet;

public class ArrayList<E> implements Iterable<E> {

    private Object[] varasto;
    private int alkukoko = 15;
    private int mahtuu;
    private int koko;
    public ArrayList tama = this;

    public ArrayList() {
        varasto = new Object[alkukoko];
        mahtuu = alkukoko;
        koko = 0;
    }

    public E get(int i) {
        if (i > koko || i < 0) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        return (E) varasto[i];
    }

    public boolean add(E e) {
        if(koko == mahtuu - 2) {
            kasvata();
        }
        varasto[koko] = (E) e;
        koko++;
        return true;
    }
    
    private void kasvata() {
        mahtuu *= 2;
        Object[] varasto = new Object[mahtuu];
        
        for (int i = 0; i < koko; i++) {
            varasto[i] = get(i);
        }
        this.varasto = varasto;
    }

    public int size() {
        return koko;
    }

    public void clear() {
        varasto = new Object[alkukoko];
        mahtuu = alkukoko;
        koko = 0;
    }

    public boolean isEmpty() {
        if (koko == 0) {
            return true;
        }
        return false;
    }

    public boolean contains(E e) {
        for (Object object : varasto) {
            if (e.equals(object)) {
                return true;
            }
        }
        return false;
    }

    public void remove(int i) {
        if (i > koko || i < 0) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }

    public java.util.Iterator<E> iterator() {
        return new java.util.Iterator() {
            private int paikka = 0;

            public boolean hasNext() {
                if (paikka < koko) {
                    return true;
                }
                return false;
            }

            public E next() {
                return get(paikka++);
            }

            public void remove() {
                tama.remove(paikka);
            }
        };
    }
}
