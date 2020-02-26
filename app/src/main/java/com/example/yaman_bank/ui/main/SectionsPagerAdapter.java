package com.example.yaman_bank.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yaman_bank.ContaCorrenteFragment;
import com.example.yaman_bank.ContaPoupancaFragment;
import com.example.yaman_bank.R;
import com.example.yaman_bank.ui.extrato.ExtratoFragment;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.extrato_conta_corrente, R.string.extrato_conta_poupanca};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        ContaCorrenteFragment contaCorrente = new ContaCorrenteFragment();
        ContaPoupancaFragment contaPoupanca = new ContaPoupancaFragment();
        if(position == 0) {
            return contaCorrente;
        } else {
            return contaPoupanca;
        }
        //return PlaceholderFragment.newInstance(position +1);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 2;
    }
}