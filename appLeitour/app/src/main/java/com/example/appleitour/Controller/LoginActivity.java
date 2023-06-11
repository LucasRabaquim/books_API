package com.example.appleitour.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appleitour.Database.DatabaseHelper;
import com.example.appleitour.Model.User;
import com.example.appleitour.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText editEmail = findViewById(R.id.txtemail);
        EditText editSenha = findViewById(R.id.txtpassword);
        CheckBox keepLogged = findViewById(R.id.checkbox_lembrarLogin);
        Button btnLogin = findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(view -> {
            String email = editEmail.getText().toString().trim();
            String senha = editSenha.getText().toString().trim();
            DatabaseHelper db = new DatabaseHelper(getApplicationContext());
            db.getReadableDatabase();
            if(db.verificarUsuarioCadastrado(email,senha)){
                SharedPreferences settings = getSharedPreferences("com.example.appleitour", 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putInt("UserId",db.selectUser(email));
                editor.putBoolean("keepLogged",keepLogged.isChecked());
                editor.apply();
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                finish();
                startActivity(intent);
            }else{
                Toast.makeText(getApplicationContext(),"Usuario ou senha incorretos",Toast.LENGTH_SHORT);
            }


        });
    }
}