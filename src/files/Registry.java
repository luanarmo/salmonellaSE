package files;

import java.util.Arrays;

/**
 * Clase que representa un registro del archivo maestro
 */
public class Registry
{
    private boolean deleted;
    private String[] records;

    /**
     * Constructor de la clase Registry
     *
     * @param deleted la premisa completa está eliminada
     * @param records    campos del registro
     */
    public Registry(boolean deleted, String[] records)
    {
        this.deleted = deleted;
        this.records = records;
    }

    /**
     * @return la premisa completa está eliminada
     */
    public boolean isDeleted()
    {
        return deleted;
    }

    /**
     * @param deleted la premisa completa está eliminada
     */
    public void setDeleted(boolean deleted)
    {
        this.deleted = deleted;
    }

    /**
     * @return campos del registro
     */
    public String[] getRecords()
    {
        return records;
    }

    /**
     * @param records campos del registro
     */
    public void setRecords(String[] records)
    {
        this.records = records;
    }

    /**
     * @return representación en String de la instancia
     */
    @Override
    public String toString()
    {
        return String.format("%s%s%s", deleted ? "¬(" : "", Arrays.toString(this.records), deleted ? ")" : "");
    }
}
