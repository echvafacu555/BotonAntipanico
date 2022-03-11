package echevasoft.antipanico.ui.Politicas;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PoliticasViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public PoliticasViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is politicas fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}