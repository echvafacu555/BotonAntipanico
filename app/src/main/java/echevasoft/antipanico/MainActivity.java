package echevasoft.antipanico;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.Settings;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import echevasoft.antipanico.ui.BaseDeDatos;
import echevasoft.antipanico.R;


public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_READ_PHONE_STATE =1 ;
    private static final int SEND_SMS = 1;
    private AppBarConfiguration mAppBarConfiguration;

    ImageView imageView, widget;


    public String latitud, longitud;


    private Button boton, misdatos;
    private EditText ubicacion, direccion;
    private Vibrator vibrator;
    String telefono1 = "", telefono2 = "";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SOLICITARPERMISOS();

        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        misdatos = (Button) findViewById(R.id.MISDATOS);
        cargardatos();
        ObtenerLocalizacion();
        imageView = (ImageView) findViewById(R.id.alerta);
        ubicacion = (EditText) findViewById(R.id.ubicacion);
        direccion = (EditText) findViewById(R.id.direccion);

        ImageView widget = (ImageView) findViewById(R.id.widget);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_home, R.id.nav_slideshow,R.id.nav_politicas, R.id.nav_share, R.id.nav_send).setDrawerLayout(drawer).build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);


       // mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
        //        .setDrawerLayout(drawer)
          //      .build();
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        //NavController navController  =R.id.nav_host_fragment,navController navHostFragment.navController
      //  NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
       // NavigationUI.setupWithNavController(navigationView, navController);


        //NavigationView navigationView = findViewById(R.id.nav_view);

        /*
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        */
         


        if (getIntent().hasExtra("EmitirAlerta")) {
            int arg = getIntent().getIntExtra("EmitirAlerta", 0);
            EmitirAlerta();
        }


        imageView.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                EmitirAlerta();
            }

        });




    }


    private void SOLICITARPERMISOS() {
        int permsRequestCode = 100;
        String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
                //Manifest.permission.READ_PHONE_STATE;
        Manifest.permission.SEND_SMS
                ,Manifest.permission.VIBRATE,
        };
        int accessFinePermission = checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION);
        int accessCoarsePermission = checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION);
    //    int READPermission = checkSelfPermission(Manifest.permission.READ_PHONE_STATE);
        int SMS = checkSelfPermission(Manifest.permission.SEND_SMS);
        int vibrate = checkSelfPermission(Manifest.permission.VIBRATE);


        if (//READPermission == PackageManager.PERMISSION_GRANTED &&
                accessFinePermission == PackageManager.PERMISSION_GRANTED
                && accessCoarsePermission == PackageManager.PERMISSION_GRANTED
                && SMS == PackageManager.PERMISSION_GRANTED
                && vibrate == PackageManager.PERMISSION_GRANTED) {
        } else {
            requestPermissions(perms, permsRequestCode);
        }
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 100:
                //acción o método a realizar.
                break;
        }
    }




    public class Ubicacion implements LocationListener {

        public void onLocationChanged(Location location) {


                location.getLatitude();
            location.getLongitude();
            latitud = String.valueOf(location.getLatitude());
            longitud = String.valueOf(location.getLongitude());

            ubicacion.setText("Lat:" + String.valueOf(location.getLatitude()) + " Long:" + String.valueOf(location.getLongitude()));
            Log.d("Latitud", String.valueOf(location.getLatitude()));
            Log.d("Longitud", String.valueOf(location.getLongitude()));


            //direcc
            if (location.getLatitude() != 0.0 && location.getLongitude() != 0.0) {
                try {
                    Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                    List<Address> list = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                    if (!list.isEmpty()) {
                        Address direcc = list.get(0);


                        direccion.setText(direcc.getAddressLine(0));

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration) || super.onSupportNavigateUp();
    }


    public void segundapantalla(View v) {
        Intent i = new Intent(this, AbmDatos.class);
        startActivity(i);

    }


    public void cargardatos() {
        BaseDeDatos objeto = new BaseDeDatos(this, "base", null, 1);
        SQLiteDatabase nuevo = objeto.getWritableDatabase();
        Cursor fila = nuevo.query("usuario", new String[]{"numcontacto1", "numcontacto2"}, null, null, null, null, null);
        if (fila.moveToFirst()) {

            telefono1 = (fila.getString(1));
            telefono2 = (fila.getString(1));


            objeto.close();

        } else {
        }
        objeto.close();

    }


    private void ObtenerLocalizacion() {
        LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Ubicacion Local = new Ubicacion();


        final boolean gpsEnabled = mlocManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (!gpsEnabled) {
           // Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            //startActivity(settingsIntent);


            alertDialog();
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ||

                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED

        ) {


            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
            return;


        }


        mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, (LocationListener) Local);
        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5, 0, (LocationListener) Local);
        mlocManager.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, 10, 0, (LocationListener) Local);


    }

    public void EmitirAlerta() {
cargardatos();

        if (telefono1.isEmpty() || telefono2.isEmpty()) {

            Toast.makeText(this, "Debe completar los datos de contacto para emitir un Alerta", Toast.LENGTH_SHORT).show();
            long [] patron = {10, 100,50,100};
            vibrator.vibrate(patron,-1);
        } else {
try {

    Toast.makeText(this, "¡AVISO DE ALERTA ENVIADO!", Toast.LENGTH_SHORT).show();
    vibrator.vibrate(800);


    if ((latitud == null) || (longitud == null)) {

        //  String strMessage = "¡Has recibido este Mensaje de Alerta, tu contacto necesita ayuda] Ubicación desconocida ";
        String strMessage = "¡Has recibido este Mensaje de Alerta, tu contacto necesita ayuda! Ubicación Desconocida";

        SmsManager sms = SmsManager.getDefault();

        ArrayList messageParts = sms.divideMessage(strMessage);

                sms.sendMultipartTextMessage(telefono1, null, messageParts, null, null);
                  sms.sendMultipartTextMessage(telefono2, null, messageParts, null, null);

    } else {
        String strMessage = "¡Has recibido este Mensaje de Alerta, tu contacto necesita ayuda! https://www.google.com/maps/place/" + latitud + "+" + longitud + " ";

        SmsManager sms = SmsManager.getDefault();

        ArrayList messageParts = sms.divideMessage(strMessage);

           sms.sendMultipartTextMessage(telefono1, null, messageParts, null, null);
            sms.sendMultipartTextMessage(telefono2, null, messageParts, null, null);

    }
} catch (Exception e) {
    e.printStackTrace();
    Toast.makeText(this, "Botón Antipánico necesita Permiso de SMS para emitir el Alerta",Toast.LENGTH_LONG).show();
}

        }

    }

    private void alertDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Solicitud de permiso");
        dialog.setIcon(R.drawable.alerta);
        dialog.setMessage("Botón Antipánico requiere la activación del servicio de ubicación.");

        dialog.setPositiveButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        dialog.setNegativeButton("Ajustes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(settingsIntent);
                dialog.cancel();
                dialog.dismiss();
            }
        });
        final AlertDialog alertDialog = dialog.create();
        alertDialog.getWindow().setGravity(Gravity.BOTTOM);
        alertDialog.show();


    }


}
