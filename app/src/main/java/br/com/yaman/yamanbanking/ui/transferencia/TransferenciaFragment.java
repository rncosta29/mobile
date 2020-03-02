package br.com.yaman.yamanbanking.ui.transferencia;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import br.com.yaman.yamanbanking.R;

public class TransferenciaFragment extends Fragment {

    private TransferenciaViewModel transferenciaViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        transferenciaViewModel =
                ViewModelProviders.of(this).get(TransferenciaViewModel.class);
        View root = inflater.inflate(R.layout.fragment_transferencia, container, false);
        Button botaoConfirmar = root.findViewById(R.id.fragment_transferencia_botao_confirmar);
        botaoConfirmar.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                transferir();
            }
        });
        return root;
    }

    public Dialog transferir(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();


        builder.setView(inflater.inflate(R.layout.activity_dialog_senha, null))
                .setPositiveButton(R.string.ok, new Dialog.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getContext(), "Tranferencia conclude", Toast.LENGTH_SHORT).show();
                    }
                });


        builder.show();
        return builder.create();
    }
}