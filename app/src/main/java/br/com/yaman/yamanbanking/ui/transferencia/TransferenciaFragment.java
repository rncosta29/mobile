package br.com.yaman.yamanbanking.ui.transferencia;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import br.com.yaman.yamanbanking.R;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class TransferenciaFragment extends Fragment {

    private TransferenciaViewModel transferenciaViewModel;
    private TextView meuSaldo;
    private Button botaoConfirmar;
    private String url;
    private String saldoContaCorrente;
    private SharedPreferences preferencias;
    private boolean error;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        transferenciaViewModel =
                ViewModelProviders.of(this).get(TransferenciaViewModel.class);
        View root = inflater.inflate(R.layout.fragment_transferencia, container, false);
        botaoConfirmar = root.findViewById(R.id.fragment_transferencia_botao_confirmar);
        meuSaldo = root.findViewById(R.id.id_meu_saldo);

        url = "";

        botaoConfirmar.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                transferir();
            }
        });
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        Bundle dados = getActivity().getIntent().getExtras();
        saldoContaCorrente = dados.getString("saldo");
        preferencias = getActivity().getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        meuSaldo.setText(preferencias.getString("saldo", "nulo"));
    }

    private void transferir() {

        @SuppressLint("StaticFieldLeak") AsyncTask<Integer, Void, Void>
                task = new AsyncTask<Integer, Void, Void>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Void doInBackground(Integer... integers) {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(url)
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    if (response.code() == 200) {
                        error = false;
                        JSONObject I = new JSONObject(response.body().string());
                        saldoContaCorrente = I.getString("valorContaCorrente");
                        //saldoContaPoupanca = I.getDouble("valorContaPoupanca");

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
                    preferencias = getActivity().getSharedPreferences("preferencias", Context.MODE_PRIVATE);
                    SharedPreferences.Editor ed = preferencias.edit();
                    ed.putString("saldo", String.format("R$ %.2f", saldoContaCorrente));
                    ed.apply();
                } else {
                    Log.i("teste", "Sem conexão");
                }
            }
        };
        task.execute();
    }

//    public Dialog transferir(){
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        LayoutInflater inflater = requireActivity().getLayoutInflater();
//
//
//        builder.setView(inflater.inflate(R.layout.activity_dialog_senha, null))
//                .setPositiveButton(R.string.ok, new Dialog.OnClickListener(){
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        Toast.makeText(getContext(), "Tranferencia conclude", Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//
//        builder.show();
//        return builder.create();
//    }
}