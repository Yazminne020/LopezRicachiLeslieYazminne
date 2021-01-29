package com.example.lopezricachileslieyazminne;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Registros extends AppCompatActivity {

    private TextView tv_log;
    private ListView lv_datos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registros);

        tv_log=findViewById(R.id.tv_log);
        lv_datos=findViewById(R.id.lv_datos);

        //recuperar datos enviados del "Ingreso Activity"
        Bundle datos=getIntent().getExtras();
        String nombre=datos.getString("clave_nombre");
        tv_log.setText(nombre);

        //Crear datos para asociar al ListView
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,recuperarDatosListView());
        //Asociar el adaptador con el lsitview para controlar los datos
        lv_datos.setAdapter(adapter);

        lv_datos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //recuperar el item seleccionado
                String seleccionado=(String) lv_datos.getAdapter().getItem(position);
                String[] idS=seleccionado.split("       |");
                String estado="";

                try {
                    Statement stn=conexionBD().createStatement();
                    ResultSet rs=stn.executeQuery("select estado from RESOLUCION where ID_RESOLUCION='"+idS[1]+"'");

                    if (rs.next()){
                       estado=rs.getString("estado");
                    }
                    if(estado.equals("1")){
                        Toast.makeText(getApplicationContext(),"ESTADO PENDIENTE",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(),"NO SE PROCESA",Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), e.getMessage(),Toast.LENGTH_SHORT).show();
                }

                 //Toast.makeText(getApplicationContext(), idS[1],Toast.LENGTH_SHORT).show();

            }
        });

    }
    public Connection conexionBD(){
        Connection conexion=null;

        try {

            StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            conexion= DriverManager.getConnection("jdbc:jtds:sqlserver://SQL5101.site4now.net;databaseName=DB_A6D3C2_AppFISEI;user=DB_A6D3C2_AppFISEI_admin;password=fuhrer420;");
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();

        }
        return  conexion;
    }

    private List<String> recuperarDatosListView(){
        List<String>lista_datos=new ArrayList<String>();

        try {
            Statement stn=conexionBD().createStatement();
            ResultSet rs=stn.executeQuery("select ID_RESOLUCION, CODIGO, Fecha from RESOLUCION where ID_ESTUDIANTE_PERTENECE='"+tv_log.getText()+"'");
            lista_datos.add("CODIGO"+" | "+"RESOLUCIÓN");
            while (rs.next()){
                lista_datos.add(rs.getString("ID_RESOLUCION")+"       | "+rs.getString("CODIGO")+" | "+rs.getString("Fecha"));
            }
        }catch (Exception e){

        }
        return lista_datos;
    }
}