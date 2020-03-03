package br.com.yaman.yamanbanking;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class LoginActivity extends AppCompatActivity {

    private Button botao;
    private EditText agencia, conta, senha;
    private String url;
    private String agenciaLogin, contaLogin, senhaLogin;
    private String valorAgencia, valorConta, valorSenha;
    private String iSenha;
    private Integer iAgencia, iConta;
    private boolean error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        agencia = findViewById(R.id.id_agencia);
        conta = findViewById(R.id.id_conta);
        senha = findViewById(R.id.id_senha);

        url = "https://api-yaman-banking.herokuapp.com/operacao/login";

        botao = findViewById(R.id.activity_login_botao_entrar);

        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarLogin();
            }
        });
    }

    private void validarLogin() {

        iAgencia = Integer.parseInt(agencia.getText().toString());
        iConta = Integer.parseInt(conta.getText().toString());
        iSenha = senha.getText().toString();

        @SuppressLint("StaticFieldLeak") AsyncTask<Integer, Void, Void>
                task = new AsyncTask<Integer, Void, Void>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Void doInBackground(Integer... integers) {
               final MediaType JSON
                        = MediaType.parse("application/json; charset=utf-8");

                OkHttpClient client = new OkHttpClient();
                Log.i("VALORES", iSenha +" "+iConta+" "+iAgencia);



                Request request = new Request.Builder()
                        .url(url)
                        .post(RequestBody.create(MediaType.parse("application/json"),
                            "{" +
                                    " \"agencia\" : "+iAgencia+" " +
                                    " \"numeroConta\" : "+iConta+" " +
                                    " \"senha\" : "+iSenha+" " +
                                    "}"))
                        .build();

                try {
                    Response response = client.newCall(request).execute();
                    if (response.code() == 200) {
                        error = false;
                        //JSONObject I = new JSONObject(response.body().string());
                        String verificacao = response.body().string();
                        Log.i("verificar", verificacao);
                        ResponseBody responseBody = response.body();
                        responseBody.close();
                    }
                    else {
                        Log.e("REQUEST", "" + response.code() );
                        Log.e("REQUEST", "" + response.message() );
                        error = true;
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }
//                catch (JSONException e) {
//                    System.out.println(e);
//                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                if (!error) {
                    Log.i("ok", "Ok");
                    Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                    startActivity(intent);
                } else {
                    Log.i("teste", "Sem conexão");
                }
            }
        };
        task.execute();
    }
}