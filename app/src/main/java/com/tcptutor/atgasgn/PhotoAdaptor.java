package com.tcptutor.atgasgn;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;


public class PhotoAdaptor extends RecyclerView.Adapter<PhotoAdaptor.PhotoViewHolder> {
    String[] data;
    String [] image;
    String [] title;
    public PhotoAdaptor(String[] data, String[] image, String[] title) {
        this.data=data;
        this.image=image;
        this.title=title;
    }
    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.card_layout, viewGroup , false);
        return new PhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PhotoViewHolder photoViewHolder, int i) {
        String id =data[i];
        String imagee=image[i];
        String titlee=title[i];
        photoViewHolder.photoId.setText("Image id: "+id);
        photoViewHolder.photoTitle.setText(titlee);
        Picasso.get().load(imagee).into(photoViewHolder.photo);



    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public class PhotoViewHolder extends RecyclerView.ViewHolder
    {
        ImageView photo;
        TextView photoId;
        TextView photoTitle;

        public PhotoViewHolder(View itemView){
            super(itemView);
            photo =(ImageView) itemView.findViewById(R.id.photoView);
            photoId = (TextView) itemView.findViewById(R.id.photoId);
            photoTitle = (TextView) itemView.findViewById(R.id.photoTitle);
        }
    }
}
