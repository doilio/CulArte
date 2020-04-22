package com.doiliomatsinhe.cularte.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.doiliomatsinhe.cularte.R;
import com.doiliomatsinhe.cularte.databinding.CategoryItemBinding;
import com.doiliomatsinhe.cularte.model.Category;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private List<Category> categoryList = new ArrayList<>();
    private CategoryItemClickListener onClickListener;

    public CategoryAdapter(CategoryItemClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private CategoryItemBinding binding;

        private ViewHolder(@NonNull CategoryItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(this);

        }

        private void bind(Category currentCategory) {
            binding.categoryName.setText(currentCategory.getNome());
            binding.categoryDescription.setText(currentCategory.getDescricao());

            if (!currentCategory.getImagemUrl().isEmpty()) {
                Picasso.get().load(currentCategory.getImagemUrl()).error(R.drawable.undraw_server_down).into(binding.categoryImage);
            } else {

                //Picasso.get().load(R.drawable.undraw_photographer).fit().centerInside().placeholder(R.color.colorPlaceholder).into(binding.categoryImage);
                Picasso.get().load(R.color.colorPlaceholder).fit().centerInside().into(binding.categoryImage);
            }


            binding.executePendingBindings();
        }

        @Override
        public void onClick(View v) {
            int clickedItem = getAdapterPosition();
            onClickListener.onCategoryItemClick(clickedItem);
        }
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        CategoryItemBinding binding = CategoryItemBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Category currentCategory = categoryList.get(position);

        if (currentCategory != null) {
            holder.bind(currentCategory);
        }

    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public interface CategoryItemClickListener {
        void onCategoryItemClick(int position);
    }

}
