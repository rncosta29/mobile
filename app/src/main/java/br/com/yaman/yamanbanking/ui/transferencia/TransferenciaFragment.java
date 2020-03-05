package br.com.yaman.yamanbanking.ui.transferencia;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import java.io.IOException;

import br.com.yaman.yamanbanking.ui.MenuActivity;
import br.com.yaman.yamanbanking.R;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class TransferenciaFragment extends Fragment {

    private TransferenciaViewModel transferenciaViewModel;
    private TextView meuSaldo;
    private EditText agencia, conta, valor, conferenciaSenha;
    private Button botaoConfirmar;
    private String url;
    private SharedPreferences preferencias, preferences;
    private boolean error;
    private String idAgencia, idConta, conferirSenha;
    private String oAgencia, oConta, oValor;
    private ProgressBar progressBar;
    private ConnectivityManager connectivityManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        transferenciaViewModel =
                ViewModelProviders.of(this).get(TransferenciaViewModel.class);
        View root = inflater.inflate(R.layout.fragment_transferencia, container, false);
        connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        botaoConfirmar = root.findViewById(R.id.fragment_transferencia_botao_confirmar);
        meuSaldo = root.findViewById(R.id.id_meu_saldo);
        agencia = root.findViewById(R.id.agenciaTransferencia);
        conta = root.findViewById(R.id.contaTransferencia);
        valor = root.findViewById(R.id.valorTransferencia);

        url = "https://api-yaman-banking.herokuapp.com/operacao/transferir";

        preferences = getActivity().getSharedPreferences("dados", Context.MODE_PRIVATE);
        idAgencia = preferences.getString("agencia", "0");
        idConta = preferences.getString("conta", "0");
        conferirSenha = preferences.getString("senha","Erro");

        botaoConfirmar.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                confirmarTransferencia();
            }
        });
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        preferencias = getActivity().getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        meuSaldo.setText(preferencias.getString("saldo", "nulo"));
    }

    private void transferir() {

        if(agencia.getText().toString() != null && conta.getText().toString() != null && valor.getText().toString() != null) {
            oAgencia = agencia.getText().toString();
            oConta = conta.getText().toString();
            oValor = valor.getText().toString();
        } else {
            showToast();
        }

        @SuppressLint("StaticFieldLeak") AsyncTask<Integer, Void, Void>
                task = new AsyncTask<Integer, Void, Void>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                exibirProgress(true);
            }

            @Override
            protected Void doInBackground(Integer... integers) {
                Log.i("transferir", "Transferir");
                OkHttpClient client = new OkHttpClient();

                MultipartBody.Builder builder = new MultipartBody.Builder();
                builder.setType(MultipartBody.FORM);
                builder.addFormDataPart("remetenteNumeroConta", idConta);
                builder.addFormDataPart("remetenteAgencia", idAgencia);
                builder.addFormDataPart("remetenteTipoProdutoFinanceiro", "2");
                builder.addFormDataPart("destinatarioNumeroConta", oConta);
                builder.addFormDataPart("destinatarioAgencia", oAgencia);
                builder.addFormDataPart("destinatarioTipoProdutoFinanceiro", "2");
                builder.addFormDataPart("valorDaTransferencia", oValor);


                RequestBody body = builder.build();
                Request request = new Request.Builder()
                        .url(url)
                        .post(body)
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    if (response.code() == 200) {
                        error = false;

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
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                exibirProgress(false);
                if (!error) {
                    Log.i("ok", "Ok");
                } else {
                    Log.i("falha", "Sem conexão");
                }
            }
        };
        task.execute();
    }

    public void showToast() {
        Context contexto = getActivity().getApplicationContext();
        String texto = "Verifique suas informações";
        int duracao = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(contexto, texto, duracao);
        toast.show();
    }

    public Dialog confirmarTransferencia(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();


        final View view =inflater.inflate(R.layout.activity_dialog_senha, null);
        builder.setView(view)
                .setPositiveButton(R.string.ok, new Dialog.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        conferenciaSenha = view.findViewById(R.id.id_conferencia_senha);
                        Log.i("transferencia", conferenciaSenha.getText().toString() );
                        if (conferirSenha.equals(conferenciaSenha.getText().toString())) {
                            transferir();
                            Toast.makeText(getContext(), "Tranferencia concluida", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getActivity().getApplicationContext(), MenuActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getContext(), "Senha incorreta", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        builder.show();
        return builder.create();
    }

    public void exibirProgress(boolean exibir) {
        if (connectivityManager.getActiveNetworkInfo() != null
                && connectivityManager.getActiveNetworkInfo().isAvailable()
                && connectivityManager.getActiveNetworkInfo().isConnected()) {
            progressBar.setVisibility(exibir ? View.VISIBLE : View.GONE);
        } else {
            Toast.makeText(getActivity(),
                    "Verifique sua conexão com a rede", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
        }
    }
}