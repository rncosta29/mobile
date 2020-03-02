package br.com.yaman.yamanbanking.ui.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.getbase.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import br.com.yaman.yamanbanking.R;

import br.com.yaman.yamanbanking.MainActivity;
import br.com.yaman.yamanbanking.MenuActivity;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private TextView m_SaldoAtualizado;
    private TextView m_UltimaTransferencia;

    private FloatingActionButton fab1;
    private FloatingActionButton fab2;

    private String saldo;
    private boolean error;

    private TextView saldoPoupanca;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        saldoPoupanca = root.findViewById(R.id.id_saldo_conta_poupanca);

        fab1 = root.findViewById(R.id.botao_flutuante2);
        fab2 = root.findViewById(R.id.botao_flutuante3);

        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MenuActivity.class);
                startActivity(intent);
            }
        });

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        Resources res = getResources();
        String[] saldoAtualizado = res.getStringArray(R.array.saldoAtualizado);
        m_SaldoAtualizado = root.findViewById(R.id.textSaldoAtualizado);

        String[] ultimaTransferencia = res.getStringArray(R.array.ultimaTransferencia);
        m_UltimaTransferencia = root.findViewById(R.id.textUltimaTransferencia);


        m_SaldoAtualizado.setText(saldoAtualizado[0] + saldoAtualizado[1] + saldoAtualizado[2]);
        m_UltimaTransferencia.setText(ultimaTransferencia[0] + ultimaTransferencia[1] + ultimaTransferencia[2] + ultimaTransferencia[3]);

        carregarSaldo();

        return root;
    }

    private void carregarSaldo() {

        @SuppressLint("StaticFieldLeak") AsyncTask<Integer, Void, Void>
                task = new AsyncTask<Integer, Void, Void>() {

            @Override
            protected void onPreExecute() {
                Log.i("onPreExecute", "Assincrono");
                super.onPreExecute();
            }

            @Override
            protected Void doInBackground(Integer... integers) {
                Log.i("LOG ENVIO", "GET DADOS ROTAS");
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("http://172.16.34.225:8080/operacao/buscar-saldo-poupanca?agencia=1234&numeroConta=123456")
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    if (response.code() == 200) {
                        error = false;
                        JSONObject I = new JSONObject(response.body().string());
                        saldo = I.getString("valorContaPoupanca");

                        ResponseBody responseBody = response.body();
                        responseBody.close();
                    }
                    else {
                        error = true;
                        Log.i("Rotas", "nao deu 200");
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    System.out.println(e);
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                if (!error) {
                    saldoPoupanca.setText("R$ " + saldo);
                } else {
                    Log.i("teste", "Sem conexão");
                }
            }
        };
        task.execute();
    }
}