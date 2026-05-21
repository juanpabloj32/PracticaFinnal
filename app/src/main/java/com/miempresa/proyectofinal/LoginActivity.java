package com.miempresa.proyectofinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;

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

        // Código unificado: Al presionar el botón se pasa directamente a ProductosActivity
        btnEntrar.setOnClickListener(v -> {

            Intent intent = new Intent(LoginActivity.this, ProductosActivity.class);

            startActivity(intent);

            finish();

        });
    }
}