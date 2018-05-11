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
import com.scaledinference.amped.store.model.CheckoutItem;
import com.scaledinference.amped.store.model.Order;

import static com.scaledinference.amped.store.CartActivity.PRICE_STRING_FORMAT;

/**
 * Adapter for cart screen.
 */
public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ItemViewHolder> {
    private Order order;

    public CartAdapter(Order order) {
        this.order = order;
    }

    @Override
    @NonNull
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_preview_item, parent, false);
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

    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTitleTextView;
        private ImageView mImageView;
        private TextView mPriceTextView;
        private TextView mCountTextView;
        private Button mAddButton;
        private Button mRemoveButton;

        private CheckoutItem item;

        ItemViewHolder(View v) {
            super(v);
            mTitleTextView = v.findViewById(R.id.title);
            mImageView = v.findViewById(R.id.picture);
            mPriceTextView = v.findViewById(R.id.price);
            mCountTextView = v.findViewById(R.id.count);
            mAddButton = v.findViewById(R.id.addButton);
            mRemoveButton = v.findViewById(R.id.removeButton);

            mAddButton.setOnClickListener(this);
            mRemoveButton.setOnClickListener(this);
        }

        void bind(CheckoutItem item) {
            this.item = item;
            mTitleTextView.setText(item.getProduct().getName());

            Context context = mImageView.getContext();
            int resourceId = context.getResources().getIdentifier(
                    "drawable/ic_" + item.getProduct().getId(), null,
                    context.getPackageName());
            mImageView.setImageResource(resourceId);
            mPriceTextView.setText(String.format(PRICE_STRING_FORMAT, item.getProduct().getPrice()));
            mCountTextView.setText(String.valueOf(item.getCount()));
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();

            switch (view.getId()) {
                case R.id.addButton:
                    item.setCount(item.getCount() + 1);
                    notifyItemChanged(position);
                    break;

                case R.id.removeButton:
                    int oldCount = item.getCount();
                    if (oldCount == 1) {
                        order.getCheckoutItems().remove(item);
                        notifyItemRemoved(position);
                        break;
                    }
                    item.setCount(oldCount - 1);
                    notifyItemChanged(position);
                    break;
            }

            order.notifyOrderUpdated();
        }
    }
}
