package com.scaledinference.amped.store.lists;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.scaledinference.amped.store.R;
import com.scaledinference.amped.store.model.Order;
import com.scaledinference.amped.store.model.Product;

import java.util.List;


/**
 * Adapter for store screen.
 */
public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.CompositeViewHolder> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    private List<ListItem> listItems;
    private Order order;

    /** Base view holder class for different types of items in the sectioned list. */
    static abstract class CompositeViewHolder<T extends ListItem>  extends RecyclerView.ViewHolder {
        CompositeViewHolder(View itemView) {
            super(itemView);
        }

        abstract void bind(T listItem);
    }

    class ItemViewHolder extends CompositeViewHolder<ProductListItem> implements View.OnClickListener {
        private TextView titleTextView;
        private TextView detailsTextView;
        private ImageView imageView;
        private Button addButton;

        ItemViewHolder(View v) {
            super(v);
            titleTextView = v.findViewById(R.id.title);
            detailsTextView = v.findViewById(R.id.details);
            imageView = v.findViewById(R.id.picture);

            addButton = v.findViewById(R.id.addButton);
            addButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            ProductListItem item = (ProductListItem) listItems.get(getAdapterPosition());
            order.add(item.getProduct());

            StoreAdapter.this.notifyItemChanged(getAdapterPosition());
        }

        @Override
        void bind(ProductListItem listItem) {
            Product product = listItem.getProduct();

            titleTextView.setText(product.getName());
            detailsTextView.setText(product.getDetails());

            Context context = imageView.getContext();
            int resourceId = context.getResources().getIdentifier(
                    "drawable/ic_" + product.getId(), null, context.getPackageName());
            imageView.setImageResource(resourceId);

            int addedCount = order.addedCount(listItem.getProduct());
            String title = addedCount == 0
                    ? context.getString(R.string.add)
                    : context.getString(R.string.added) + " " + addedCount;
            addButton.setText(title);
        }
    }

    static class HeaderViewHolder extends CompositeViewHolder<HeaderListItem> {
        private TextView titleTextView;

        HeaderViewHolder(View v) {
            super(v);
            titleTextView = v.findViewById(R.id.headerTitle);
        }

        void bind(HeaderListItem item) {
            titleTextView.setText(item.getHeader());
        }
    }

    @Override
    public int getItemViewType(int position) {
        return listItems.get(position) instanceof HeaderListItem ? TYPE_HEADER : TYPE_ITEM;
    }

    public StoreAdapter(List<ListItem> listItems, Order order) {
        this.listItems = listItems;
        this.order = order;
    }

    @Override
    @NonNull
    public CompositeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_header_item, parent, false);
            return new HeaderViewHolder(itemView);
        }

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_store_item, parent, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onBindViewHolder(@NonNull CompositeViewHolder holder, int position) {
        holder.bind(listItems.get(position));
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }
}
