package echevasoft.antipanico.ui.slideshow;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import echevasoft.antipanico.R;

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel = ViewModelProviders.of(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
        slideshowViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

                String[] TO = {"echevasoft@gmail.com"}; //aquí pon tu correo
                String[] CC = {""};
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.setType("text/plain");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
                emailIntent.putExtra(Intent.EXTRA_CC, CC);

// Esto podrás modificarlo si quieres, el asunto y el cuerpo del mensaje
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Asunto:");

                try {
                    startActivity(Intent.createChooser(emailIntent, "Enviar correo..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getContext(), "No tienes clientes de email instalados.", Toast.LENGTH_SHORT).show();
                }

            }
        });
        return root;
    }
}