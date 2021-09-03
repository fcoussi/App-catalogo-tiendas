package cl.ufro.dci.proyectosmartcity.ui.CerrarSesion;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CerrarViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CerrarViewModel() {
        mText = new MutableLiveData<>();

    }

    public LiveData<String> getText() {
        return mText;
    }
}
