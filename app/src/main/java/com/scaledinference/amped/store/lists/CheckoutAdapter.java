package com.scaledinference.amped.store.lists;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.scaledinference.amped.store.R;
import com.scaledinference.amped.store.model.CheckoutItem;
import com.scaledinference.amped.store.model.Order;

import static com.scaledinference.amped.store.CartActivity.PRICE_STRING_FORMAT;

/**
 * Adapter for checkout screen.
 */
public class CheckoutAdapter extends RecyclerView.Adapter<CheckoutAdapter.ItemViewHolder> {
    private Order order;

    public CheckoutAdapter(Order order) {
        this.order = order;
    }

    @Override
    @NonNull
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_checkout_item, parent, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.bind(order.getCheckoutItems().get(position));
    }

    @Override
    public int getItemCount() {
        return order.getCheckoutItems().size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTextView;
        private ImageView imageView;
        private TextView priceTextView;
        private TextView countTextView;

        ItemViewHolder(View v) {
            super(v);
            titleTextView = v.findViewById(R.id.title);
            imageView = v.findViewById(R.id.picture);
            priceTextView = v.findViewById(R.id.price);
            countTextView = v.findViewById(R.id.count);
        }

        void bind(CheckoutItem item) {
            titleTextView.setText(item.getProduct().getName());

            Context context = imageView.getContext();
            final int resourceId = context.getResources().getIdentifier(
                    "drawable/ic_" + item.getProduct().getId(), null,
                    context.getPackageName());
            imageView.setImageResource(resourceId);
            priceTextView.setText(String.format(PRICE_STRING_FORMAT, item.getProduct().getPrice()));
            countTextView.setText(String.valueOf(item.getCount()));
        }
    }
}
