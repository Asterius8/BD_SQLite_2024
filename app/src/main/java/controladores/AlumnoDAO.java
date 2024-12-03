package controladores;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import entidades.Alumno;

@Dao
public interface AlumnoDAO {

    //--------------------------------- ALTAS ------------------------------------
    @Insert
    public void agregarAlumno(Alumno alumno);

    @Insert
    public void agregarAlumnoS(Alumno...alumnos);

    //--------------------------------- BAJAS ------------------------------------
    @Delete
    public void eliminarAlumno(Alumno alumno);

    @Query("DELETE FROM alumno WHERE numControl= :nc")
    public void eliminarAlumnoPorNumControl(String nc);


    //--------------------------------- CAMBIOS ------------------------------------
    @Update
    public void actualizarAlumnoPorNumControl(Alumno alumno);

    @Query("UPDATE alumno SET nombre =:n, edad= :e WHERE numControl = :nc")
    public void actualizarAlumnoPorNumControl(String nc, String n, byte e);

    //--------------------------------- CONSULTAS ------------------------------------
    @Query("SELECT * FROM Alumno")
    public List<Alumno> mostarTodos();

    @Query("SELECT * FROM alumno WHERE numControl = :nc")
    public List<Alumno> buscarPorNumeroDeControl(String nc);

    @Query("SELECT * FROM alumno WHERE nombre = :n")
    public List<Alumno> buscarPorNombre(String n);

    @Query("SELECT * FROM alumno WHERE edad = :e")
    public List<Alumno> buscarPorEdad(byte e);
}
