package com.miempresa.proyectofinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    EditText usuario, password;
    Button btnEntrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usuario = findViewById(R.id.usuario);
        password = findViewById(R.id.password);
        btnEntrar = findViewById(R.id.btnEntrar);

        // ANIMACIÓN
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        btnEntrar.startAnimation(animation);

        btnEntrar.setOnClickListener(v -> {

            String user = usuario.getText().toString();
            String pass = password.getText().toString();

            if(user.equals("admin") && pass.equals("1234")) {

                Toast.makeText(this, "Bienvenido", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);

                finish();

            } else {

                Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
            }

        });
    }
}