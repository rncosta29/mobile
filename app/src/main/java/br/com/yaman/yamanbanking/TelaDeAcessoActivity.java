package br.com.yaman.yamanbanking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class TelaDeAcessoActivity extends AppCompatActivity {

    private Button botaoEntrarMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_de_acesso);

        botaoEntrarMain = findViewById(R.id.btnEntrar);
    }

    public void Main(View v) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

}
