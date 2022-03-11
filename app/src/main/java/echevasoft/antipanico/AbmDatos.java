package echevasoft.antipanico;

import android.content.ContentValues;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import echevasoft.antipanico.ui.BaseDeDatos;
import echevasoft.antipanico.R;

public class AbmDatos extends AppCompatActivity {

    EditText cont1,cont2,cont3, numcont1,numcont2,numcont3;
    Button ingresar,modificar;
    private Vibrator vibrator;

public int n1=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abm_datos);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        ingresar=(Button)findViewById(R.id.ingresar);
        modificar=(Button)findViewById(R.id.modificar);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);


        cont1=(EditText)findViewById(R.id.nomcon);
        cont2=(EditText)findViewById(R.id.nomcon2);
        numcont1=(EditText)findViewById(R.id.numcont1);
        numcont2=(EditText)findViewById(R.id.numcont2);
        cargardatos();




    }




    public void deshabilitarcampos(){
        cont1.setEnabled(false);
        cont2.setEnabled(false);
        numcont1.setEnabled(false);
        numcont2.setEnabled(false);
    }

    public void habilitarcampos(){
        cont1.setEnabled(true);
        cont2.setEnabled(true);
        numcont1.setEnabled(true);
        numcont2.setEnabled(true);
    }



    public void cargardatos(){
        BaseDeDatos objeto = new BaseDeDatos(this,"base",null,1);
        SQLiteDatabase nuevo=objeto.getWritableDatabase();
        Cursor fila = nuevo.query("usuario", new String[]{"nomcontacto1","numcontacto1"
                ,"nomcontacto2","numcontacto2"}, null, null, null, null, null);
        if (fila.moveToFirst()){
            numcont1.setText(fila.getString(1));
            numcont2.setText(fila.getString(3));
            cont1.setText(fila.getString(0));
            cont2.setText(fila.getString(2));
           // ingresar.setEnabled(false);
            deshabilitarcampos();
        }else{



        }
        objeto.close();
    }





    public void alta(){



        BaseDeDatos objeto=new BaseDeDatos(this,"base",null,1);
        SQLiteDatabase nuevo=objeto.getWritableDatabase();

        ContentValues registro=new ContentValues();
        if (
                (numcont1.getText().toString().isEmpty()) ||
                (numcont2.getText().toString().isEmpty()) ||
                (cont1.getText().toString().isEmpty()) ||
                (cont2.getText().toString().isEmpty())||
                        (numcont1.getText().toString().equals("+549") )||
                        (numcont2.getText().toString().equals("+549")  ))


        {
            long [] patron = {10, 100,50,100};
            vibrator.vibrate(patron,-1);
            Toast.makeText(this, "Debe completar todos los datos", Toast.LENGTH_SHORT).show();
        }else{
            Log.d("numerouno", numcont1.getText().toString());
            String num1 = numcont1.getText().toString();
            String num2 = numcont2.getText().toString();
            String nomcont1 = cont1.getText().toString();
            String nomcon2 = cont2.getText().toString();
            registro.put("id", n1);
            registro.put("nomcontacto1", nomcont1);
            registro.put("numcontacto1", num1);
            registro.put("numcontacto2", num2);
            registro.put("nomcontacto2", nomcon2);

            nuevo.insert("usuario", null, registro);
            Toast.makeText(this, "Sus datos han sido ingresados correctamente", Toast.LENGTH_SHORT).show();
            deshabilitarcampos();
         //      ingresar.setEnabled(false);
        }
        objeto.close();



    }


    public void modificar(View v){
        habilitarcampos();
    }

public void guardardatos(View v){
    BaseDeDatos objeto = new BaseDeDatos(this,"base",null,1);
    SQLiteDatabase nuevo=objeto.getWritableDatabase();
    Cursor fila = nuevo.query("usuario", new String[]{"nomcontacto1","numcontacto1"
            ,"nomcontacto2","numcontacto2"}, null, null, null, null, null);
    if (fila.moveToFirst()){

        actualizardatos();



        deshabilitarcampos();
    }else {

        alta();

    }

    objeto.close();

    }


    public void actualizardatos ( ) {


        BaseDeDatos objeto=new BaseDeDatos(this,"base",null,1);
        SQLiteDatabase nuevo=objeto.getWritableDatabase();
        ContentValues registro = new ContentValues();


        if ((numcont1.getText().toString().isEmpty()) || (numcont2.getText().toString().isEmpty()) ||
                (cont1.getText().toString().isEmpty()) ||
                (cont2.getText().toString().isEmpty())||
                (numcont1.getText().toString().equals("+549") )||
                (numcont2.getText().toString().equals("+549")  ))


        {
            long [] patron = {10, 100,50,100};
            vibrator.vibrate(patron,-1);
            Toast.makeText(this, "Debe ingresar todos los datos", Toast.LENGTH_SHORT).show();
        } else {


            String num1 = numcont1.getText().toString();
            String num2 = numcont2.getText().toString();
            String nomcont1 = cont1.getText().toString();
            String nomcon2 = cont2.getText().toString();

            registro.put("nomcontacto1", nomcont1);
            registro.put("numcontacto1", num1);
            registro.put("numcontacto2", num2);
            registro.put("nomcontacto2", nomcon2);
            int cant = nuevo.update("usuario", registro, "id= " + n1, null);

            if (cant == 1) {
                Toast.makeText(this, "Los datos se han actualizado correctamemnte", Toast.LENGTH_SHORT).show();
                deshabilitarcampos();
            } else {
                long [] patron = {10, 100,50,100};
                vibrator.vibrate(patron,-1);
                Toast.makeText(this, "Error al actualizar los datos", Toast.LENGTH_SHORT).show();

            }
            objeto.close();

        }

    }



}
