package com.doiliomatsinhe.cularte.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.doiliomatsinhe.cularte.R;
import com.doiliomatsinhe.cularte.databinding.ArtistItemBinding;
import com.doiliomatsinhe.cularte.model.Artist;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.ViewHolder> {

    private List<Artist> artistList = new ArrayList<>();
    private ArtistItemClickListener onClickListener;

    public ArtistAdapter(ArtistItemClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ArtistItemBinding binding;

        private ViewHolder(@NonNull ArtistItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(this);
//            categoryImage = itemView.findViewById(R.id.category_image);
//            categoryName = itemView.findViewById(R.id.category_name);
//            categoryDescription = itemView.findViewById(R.id.category_description);

        }

        private void bind(Artist currentArtist) {
            if (currentArtist.getNomeArtistico().isEmpty()) {
                binding.artistName.setText(currentArtist.getNomeCompleto());
            } else {
                binding.artistName.setText(currentArtist.getNomeArtistico());
            }

            binding.artistDescription.setText(currentArtist.getCurtaDescricao());

            if (currentArtist.getImagensUrl().size() > 0) {
                Picasso.get().load(currentArtist.getImagensUrl().get(0)).into(binding.artistImage);
            } else {
                Picasso.get().load(R.color.colorPrimary).into(binding.artistImage);
            }


            binding.executePendingBindings();
        }

        @Override
        public void onClick(View v) {
            int clickedItem = getAdapterPosition();
            onClickListener.onArtistItemClick(clickedItem);
        }
    }

    public void setArtistList(List<Artist> artistList) {
        this.artistList = artistList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        //View view = inflater.inflate(R.layout.category_item, parent, false);
        ArtistItemBinding binding = ArtistItemBinding.inflate(inflater, parent, false);
        //return new ViewHolder(view);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Artist currentArtist = artistList.get(position);

        if (currentArtist != null) {
            holder.bind(currentArtist);
        }

//        if (currentCategory != null) {
//            categoryName.setText(currentCategory.getNome());
//            categoryDescription.setText(currentCategory.getDescricao());
//
//            Picasso.get().load(currentCategory.getImagemUrl()).into(categoryImage);
//        }

    }

    @Override
    public int getItemCount() {
        return artistList.size();
    }

    public interface ArtistItemClickListener {
        void onArtistItemClick(int position);
    }

}
