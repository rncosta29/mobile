package br.com.yaman.yamanbanking;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ContaPoupancaFragment extends Fragment {

    private RecyclerView recyclerView;
    private String s1[], s2[], s3[];

    public ContaPoupancaFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_conta_poupanca, container, false);

        recyclerView = view.findViewById(R.id.reciclador);

        s1 = getResources().getStringArray(R.array.servico);
        s2 = getResources().getStringArray(R.array.estabelecimento);
        s3 = getResources().getStringArray(R.array.preco);

        MyAdapter2 myAdapter2 = new MyAdapter2(getContext(), s1, s2, s3);

        recyclerView.setAdapter(myAdapter2);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }
}