package cl.ufro.dci.proyectosmartcity.ui.Avisos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import cl.ufro.dci.proyectosmartcity.R;

/*
    Clase Adaptador que nos permite administrar los datos en el RecyclerView, y determina como
    sera la vsualización.
*/

public class AvisosViewModel extends  RecyclerView.Adapter<AvisosViewModel.ComercioViewHolder> {

    private ArrayList<Hortaliza> listaComercio;
    private View.OnClickListener listener;
    private View view;
    private RecyclerItemClick itemClick;

    public AvisosViewModel(ArrayList<Hortaliza> listaComercio,RecyclerItemClick itemClick) {
        this.listaComercio= listaComercio;
        this.itemClick = itemClick;
    }

    @Override
    public ComercioViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        //Inflate adjunta la vista en itemList en nuestra vista
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemlist,parent,false);

        return new ComercioViewHolder(view);
    }


    /*Muestra los datos en pantalla según el orden
     establecido, el método actualiza la vista de los items*/

    @Override
    public void onBindViewHolder(@NonNull ComercioViewHolder holder, int position){
        final Hortaliza item = listaComercio.get(position);
        holder.txtNombre.setText(item.getNombre());
        holder.txtInformacion.setText(item.getInfo());
        holder.txtServicio.setText(item.getServicio());
        holder.txtPrecio.setText(item.getPrecio());

      /*GLIDE DESCARGA LAS IMAGENES POR MEDIO DE URL*/
        Glide
                .with(view)
                .load(listaComercio.get(position).getImagenId())
                .centerCrop()
                .into(holder.foto);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                itemClick.itemClick(item,view);
            }
        }


        );

    }

    @Override
    public int getItemCount() {
        return listaComercio.size();
    }

    public void setOnclickListener(View.OnClickListener listener){
        this.listener=listener;
    }


    public class ComercioViewHolder extends RecyclerView.ViewHolder {

        private TextView txtNombre, txtInformacion, txtServicio, txtPrecio;
        private ImageView foto;

        //Se asigna valores en nuestros XML, a partir de datos obtenidos
        public ComercioViewHolder(View itemView) {
            super(itemView);
            txtNombre= (TextView) itemView.findViewById(R.id.idNombre);
            txtInformacion= (TextView) itemView.findViewById(R.id.idInfo);
            txtServicio= (TextView) itemView.findViewById(R.id.idServicio);
            txtPrecio= (TextView) itemView.findViewById(R.id.idPrecio);
            foto= (ImageView) itemView.findViewById(R.id.idImagen);
        }
    }

    public interface RecyclerItemClick {
        void itemClick(Hortaliza item,View view);
    }

}
