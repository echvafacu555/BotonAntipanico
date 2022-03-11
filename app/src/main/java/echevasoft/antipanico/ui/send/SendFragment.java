package echevasoft.antipanico.ui.send;

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

public class SendFragment extends Fragment {

    private SendViewModel sendViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        sendViewModel = ViewModelProviders.of(this).get(SendViewModel.class);
        View root = inflater.inflate(R.layout.fragment_send, container, false);
        sendViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {


                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(
                        "https://play.google.com/store/apps/details?id=echevasoft.antipanico"));
                intent.setPackage("com.android.vending");
                startActivity(intent);
            }
        });
        return root;
    }
}