package com.example.lopezricachileslieyazminne;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {

    EditText usuario, clave;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usuario=findViewById(R.id.te_usuario);
        clave=findViewById(R.id.te_clave);
        btnLogin=findViewById(R.id.btn_ingresar);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    login();
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
    public void login(){
        try{
            String usu=usuario.getText().toString();
            String cla=clave.getText().toString();
            String usuariobd="", clavebd="";
            Statement stn=conexionBD().createStatement();

            ResultSet rs=stn.executeQuery("Select * from ESTUDIANTES where CORREO_UTA='"+usuario.getText()+"'");
            if (rs.next()){
                usuariobd=rs.getString("CORREO_UTA");
                clavebd=rs.getString("clave");
            }

            //Toast.makeText(getApplicationContext(),usuario.getText()+"  "+usuariobd+"    "+clavebd,Toast.LENGTH_SHORT).show();
            if(usu.equals(usuariobd)&& cla.equals(clavebd)){
                Toast.makeText(getApplicationContext(),"Bienvenido:"+usuariobd,Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(this, Registros.class);
                startActivity(intent);
            }else{
                Toast.makeText(getApplicationContext(),"Credenciales Incorrectas",Toast.LENGTH_SHORT).show();
            }


        }catch (Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

}