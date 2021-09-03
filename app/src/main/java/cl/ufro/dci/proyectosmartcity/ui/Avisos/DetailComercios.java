package cl.ufro.dci.proyectosmartcity.ui.Avisos;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import cl.ufro.dci.proyectosmartcity.R;

/*
    Activity que hace uso del fragment_comercios para visualizar detalles de cad item view
*/

public class DetailComercios extends AppCompatActivity {

    private ImageView mImage;
    private TextView mTitulo;
    private TextView mDescripcion;
    private TextView mDireccion;
    private TextView mPrecio;
    private Hortaliza itemDetail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_comercios);
        setTitle(getClass().getSimpleName());

        initViews();
        initValues();
    }

    //PROPIEDADES LAYOUT FRAGMENT_COMERCIOS
    private void initViews() {
        mImage = findViewById(R.id.imagenDetalle);
        mTitulo = findViewById(R.id.Titulo);
        mDescripcion = findViewById(R.id.dDescripcion);
        mDireccion = findViewById(R.id.dDireccion);
        mPrecio = findViewById(R.id.dPrecio);
    }

    //OBTENIENDO LOS VALORES A PARTIR DEL ARREGLO HORTALIZA
    private void initValues() {
        itemDetail = (Hortaliza) getIntent().getExtras().getSerializable("itemDetail");
        mTitulo.setText(itemDetail.getNombre());
        mDireccion.setText(itemDetail.getServicio());
        mPrecio.setText(itemDetail.getPrecio());
        Glide
                .with(this)
                .load(itemDetail.getImagenId())
                .centerCrop()
                .into(mImage);

    }

}
