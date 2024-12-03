package bd;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import controladores.AlumnoDAO;
import entidades.Alumno;

@Database(entities = {Alumno.class}, version = 1)
public abstract class EscuelaBD extends RoomDatabase {

    private static EscuelaBD INSTANCE;

    public abstract AlumnoDAO alumnoDAO();

    public static EscuelaBD getAppDataBase(Context context){

        if(INSTANCE == null)

            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),EscuelaBD.class, "escuelaBD").build();

        return INSTANCE;
    }

    public static void destroyInstance(){INSTANCE=null;}

}
