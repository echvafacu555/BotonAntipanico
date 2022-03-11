package echevasoft.antipanico.ui.share;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import echevasoft.antipanico.R;

public class ShareFragment extends Fragment {

    private ShareViewModel shareViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        shareViewModel = ViewModelProviders.of(this).get(ShareViewModel.class);
        View root = inflater.inflate(R.layout.fragment_share, container, false);
        shareViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {


                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, "¡Descarga la App Botón Antipánico! https://play.google.com/store/apps/details?id=echevasoft.antipanico");
                startActivity(Intent.createChooser(intent, "Compartir con"));
            }
        });
        return root;
    }
}