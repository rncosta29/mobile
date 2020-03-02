package br.com.yaman.yamanbanking.ui.home;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.getbase.floatingactionbutton.FloatingActionButton;
import br.com.yaman.yamanbanking.R;

import br.com.yaman.yamanbanking.MainActivity;
import br.com.yaman.yamanbanking.MenuActivity;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private TextView m_SaldoAtualizado;
    private TextView m_UltimaTransferencia;

    private FloatingActionButton fab1;
    private FloatingActionButton fab2;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
//        final TextView textView = root.findViewById(R.id.text_home);
//        homeViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

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
        return root;
    }

    public void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}