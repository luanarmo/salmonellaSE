package files;

import java.io.EOFException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase para manejar el archivo maestro
 */
public class Master {
    private RandomAccessFile file;
    private final int CHAR_LENGTH = 55;
    private final int NUMBER_OF_FIELDS = 23;
    private final int LENGTH = 2 + (CHAR_LENGTH * NUMBER_OF_FIELDS * 2);
    private final String FILE_NAME = System.getProperty("user.dir") + "/src/files/master.bin";

    /**
     * Almacenamiento de un nuevo registro al final del archivo maestro
     *
     * @param registry registro a almacenar
     * @throws IOException el archivo no existe o no se puede escribir o leer
     */
    public void save(Registry registry) throws IOException {
        String leftJustifyFormat = "%1$-" + CHAR_LENGTH + "s";

        file = new RandomAccessFile(FILE_NAME, "rw");
        file.seek(file.length());

        String[] records = registry.getRecords();

        file.writeChar(registry.isDeleted() ? 't' : 'f');
        for (int i = 0; i < records.length - 1; i++) {
            String line = String.format(leftJustifyFormat, records[i]);
            for (int j = 0; j < CHAR_LENGTH; j++)
                file.writeChar(line.charAt(j));
        }

        for (int i = 0; i < NUMBER_OF_FIELDS - records.length; i++)
            for (int j = 0; j < CHAR_LENGTH; j++)
                file.writeChar(' ');

        String line = String.format(leftJustifyFormat, records[records.length - 1]);
        for (int j = 0; j < CHAR_LENGTH; j++)
            file.writeChar(line.charAt(j));
        file.close();
    }

    /**
     * Lee de forma secuencial el archivo
     *
     * @return lista de registros contenidos
     * @throws IOException el archivo no existe o no se puede escribir o leer
     */
    public List<Registry> read() throws IOException {
        List<Registry> registries = new ArrayList<>();

        file = new RandomAccessFile(FILE_NAME, "r");
        file.seek(0);
        int i = 0;
        while (true) {
            try {
                file = new RandomAccessFile(FILE_NAME, "r");
                Registry registry = readRegistry(i);

                if (registry != null)
                    registries.add(registry);
                i++;
            } catch (EOFException e) {
                file.close();
                break;
            }
        }

        return registries;
    }

    /**
     * Lee un registro en particular
     *
     * @param n numero del registro a leer
     * @return registro leido
     * @throws IOException el archivo no existe o no se puede escribir o leer
     */
    public Registry readRegistry(int n) throws IOException {
        Registry registry;
        file = new RandomAccessFile(FILE_NAME, "r");
        file.seek(n * LENGTH);

        boolean all = file.readChar() == 't';

        String[] records = new String[NUMBER_OF_FIELDS];
        for (int i = 0; i < NUMBER_OF_FIELDS; i++) {
            StringBuilder builder = new StringBuilder();
            for (int j = 0; j < CHAR_LENGTH; j++)
                builder.append(file.readChar());
            records[i] = builder.toString().trim();
        }

        file.close();
        registry = new Registry(all, records);
        if (!registry.isDeleted())
            return registry;
        else
            return null;
    }

    /**
     * Actualizar un registro dado. Se realiza sobreescribiendo el registro anterior con el nuevo
     *
     * @param n        numero de registro a actualizar
     * @param registry contenido del registro nuevo
     * @throws IOException el archivo no existe o no se puede escribir o leer
     */
    public void update(int n, Registry registry) throws IOException {
        String leftJustifyFormat = "%1$-" + CHAR_LENGTH + "s";
        file = new RandomAccessFile(FILE_NAME, "rw");
        file.seek(n * LENGTH);

        String[] records = registry.getRecords();
        file.writeChar(registry.isDeleted() ? 't' : 'f');
        for (int i = 0; i < records.length - 1; i++) {
            String line = String.format(leftJustifyFormat, records[i]);
            for (int j = 0; j < CHAR_LENGTH; j++)
                file.writeChar(line.charAt(j));
        }

        for (int i = 0; i < NUMBER_OF_FIELDS - records.length; i++)
            for (int j = 0; j < CHAR_LENGTH; j++)
                file.writeChar(' ');

        String line = String.format(leftJustifyFormat, records[records.length - 1]);
        for (int j = 0; j < CHAR_LENGTH; j++)
            file.writeChar(line.charAt(j));

        file.close();
    }

    /**
     * Se elimina el registro deseado
     *
     * @param n numero de registro a eliminar
     * @throws IOException el archivo no existe o no se puede escribir o leer
     */
    public void delete(int n) throws IOException {
        file = new RandomAccessFile(FILE_NAME, "r");
        int num = (int) (file.length() / LENGTH);

        if (n < num) {
            Registry registry = readRegistry(n);
            registry.setDeleted(true);
            update(n, registry);
        }
    }

    public int getNumberOfRegistries() {
        try {
            int i = 0;
            while (true) {
                try {
                    file = new RandomAccessFile(FILE_NAME, "r");
                    Registry registry = readRegistry(i);
                    i++;
                } catch (EOFException e) {
                    file.close();
                    return i;
                }
            }
        } catch (IOException e) {
            return 0;
        }
    }
}
