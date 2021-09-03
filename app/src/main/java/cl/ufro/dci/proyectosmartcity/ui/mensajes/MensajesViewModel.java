package cl.ufro.dci.proyectosmartcity.ui.mensajes;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MensajesViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MensajesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This ventana Mensajes");
    }

    public LiveData<String> getText() {
        return mText;
    }

}
