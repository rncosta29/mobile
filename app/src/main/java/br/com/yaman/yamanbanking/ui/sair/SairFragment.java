package br.com.yaman.yamanbanking.ui.sair;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import br.com.yaman.yamanbanking.R;

public class SairFragment extends Fragment {

    private SairViewModel sairViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        sairViewModel = ViewModelProviders.of(this).get(SairViewModel.class);
        View root = inflater.inflate(R.layout.fragment_sair, container, false);
//        final TextView textView = root.findViewById(R.id.text_sair);
//        sairViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

        return root;
    }
    public Dialog sair(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = requireActivity().getLayoutInflater();
        builder.setMessage("Tem certeza que deseja sair da aplicação")
            .setPositiveButton(R.string.ok, new Dialog.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    getActivity().finish();
                }
            })
            .setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            })
        ;


        builder.show();
        return builder.create();
    }
}