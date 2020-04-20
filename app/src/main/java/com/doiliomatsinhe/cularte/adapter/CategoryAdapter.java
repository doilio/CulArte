package com.doiliomatsinhe.cularte.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
//            categoryImage = itemView.findViewById(R.id.category_image);
//            categoryName = itemView.findViewById(R.id.category_name);
//            categoryDescription = itemView.findViewById(R.id.category_description);

        }

        private void bind(Category currentCategory) {
            binding.categoryName.setText(currentCategory.getNome());
            binding.categoryDescription.setText(currentCategory.getDescricao());

            if (!currentCategory.getImagemUrl().isEmpty()) {
                Picasso.get().load(currentCategory.getImagemUrl()).error(R.color.colorPrimary).into(binding.categoryImage);
            } else {
                Picasso.get().load(R.color.colorPrimary).into(binding.categoryImage);
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
        //View view = inflater.inflate(R.layout.category_item, parent, false);
        CategoryItemBinding binding = CategoryItemBinding.inflate(inflater, parent, false);
        //return new ViewHolder(view);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Category currentCategory = categoryList.get(position);

        if (currentCategory != null) {
            holder.bind(currentCategory);
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
        return categoryList.size();
    }

    public interface CategoryItemClickListener {
        void onCategoryItemClick(int position);
    }

}
