package echevasoft.antipanico.ui.Politicas;

import android.content.Intent;
import android.net.Uri;
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

public class PoliticasFragment extends Fragment {

    private PoliticasViewModel politicasViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        politicasViewModel = ViewModelProviders.of(this).get(PoliticasViewModel.class);
        View root = inflater.inflate(R.layout.fragment_politicas, container, false);
        politicasViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {


                Intent i = new Intent(Intent.ACTION_VIEW);

                String url = "https://drive.google.com/file/d/1LfQNdyXXknpKLxMslxDmHNhFW0L8u9Tz/view?usp=sharing";
                i.setData(Uri.parse(url));
                startActivity(i);



            }
        });
        return root;
    }
}