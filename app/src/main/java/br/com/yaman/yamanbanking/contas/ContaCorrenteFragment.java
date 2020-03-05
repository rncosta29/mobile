package br.com.yaman.yamanbanking.contas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import br.com.yaman.yamanbanking.R;
import br.com.yaman.yamanbanking.ui.extrato.AdapterContaCorrente;

public class ContaCorrenteFragment extends Fragment {

    private RecyclerView recyclerView;
    private String s1[], s2[], s3[];

    public ContaCorrenteFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_conta_corrente, container, false);

        recyclerView = view.findViewById(R.id.reciclador);

        s1 = getResources().getStringArray(R.array.servico);
        s2 = getResources().getStringArray(R.array.estabelecimento);
        s3 = getResources().getStringArray(R.array.preco);

        AdapterContaCorrente adapterContaCorrente = new AdapterContaCorrente(getContext(), s1, s2, s3);

        recyclerView.setAdapter(adapterContaCorrente);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }
}