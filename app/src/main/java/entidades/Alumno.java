package entidades;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Alumno {
    @PrimaryKey
    @NonNull
    public String numControl;

    @NonNull
    @ColumnInfo(name="Nombre")
    public String nombre;

    @NonNull
    @ColumnInfo(name= "Edad")
    public byte edad;

    public Alumno(byte edad, @NonNull String nombre, @NonNull String numControl) {
        this.edad = edad;
        this.nombre = nombre;
        this.numControl = numControl;
    }

    @NonNull
    public String getNumControl() {
        return numControl;
    }

    public void setNumControl(@NonNull String numControl) {
        this.numControl = numControl;
    }

    @NonNull
    public String getNombre() {
        return nombre;
    }

    public void setNombre(@NonNull String nombre) {
        this.nombre = nombre;
    }

    public byte getEdad() {
        return edad;
    }

    public void setEdad(byte edad) {
        this.edad = edad;
    }

    @Override
    public String toString() {
        return
                numControl +
                "|" + nombre +
                "|" + edad;
    }
}
