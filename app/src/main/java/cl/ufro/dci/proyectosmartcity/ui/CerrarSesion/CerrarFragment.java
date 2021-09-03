package cl.ufro.dci.proyectosmartcity.ui.CerrarSesion;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;


import cl.ufro.dci.proyectosmartcity.MainActivity;
import cl.ufro.dci.proyectosmartcity.R;

public class CerrarFragment extends Fragment implements View.OnClickListener {


    private CerrarViewModel cerrarViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        cerrarViewModel =
                ViewModelProviders.of(this).get(CerrarViewModel.class);
        View root = inflater.inflate(R.layout.fragment_sesion, container, false);
        final TextView textView = root.findViewById(R.id.button_sesion);
        final TextView textView1 = root.findViewById(R.id.button_sesion2);

        cerrarViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                      textView.setText(s);
                      textView1.setText(s);
            }
        });
        return root;
    }

    @NonNull
    public static Intent createIntent(@NonNull Context context) {
        return new Intent(context, MainActivity.class);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }


    @Override
    public void onClick(View v) {
    switch (View.generateViewId()){
        case R.id.action_cerrarSesion:


    }
    }



}
