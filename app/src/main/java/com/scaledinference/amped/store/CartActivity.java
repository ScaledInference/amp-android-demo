package com.scaledinference.amped.store;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.scaledinference.amped.store.lists.CartAdapter;
import com.scaledinference.amped.store.model.CheckoutSession;
import com.scaledinference.amped.store.model.UserSession;

public class CartActivity extends AppCompatActivity implements OrderUpdateListener {
    public static final String PRICE_STRING_FORMAT = "%.2f";

    private CheckoutSession checkoutSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        checkoutSession = UserSession.getInstance().getCheckoutSession();
        setTheme(checkoutSession.getTheme());

        setContentView(R.layout.cart_activity);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);


        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(new CartAdapter(checkoutSession.getOrder()));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        updateOrderInformation();
    }

    @Override
    protected void onStart() {
        super.onStart();

        checkoutSession.getOrder().addListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();

        checkoutSession.getOrder().removeListener(this);
    }

    void updateOrderInformation() {
        // Counter
        TextView countTextView = findViewById(R.id.countTextView);
        countTextView.setText(String.valueOf(checkoutSession.getOrder().getTotalCount()));

        // Subtotal
        TextView subtotalTextView = findViewById(R.id.subtotalTextView);
        subtotalTextView.setText(
                String.format(PRICE_STRING_FORMAT, checkoutSession.getOrder().getSubtotalCost()));

        // Free shipping time limit
        TextView freeShippingTextView = findViewById(R.id.freeShippingTextView);
        long minutesLeftForFreeShipping = checkoutSession.getMinutesLeftForFreeShipping();
        if (minutesLeftForFreeShipping == 0) {
            freeShippingTextView.setVisibility(View.GONE);
        } else {
            String text = "Order in " + minutesLeftForFreeShipping + " minutes to get free shipping.";
            freeShippingTextView.setText(text);
        }
    }

    public void openCheckoutPage(View view) {
        startActivity(new Intent(this, CheckoutActivity.class));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void orderUpdated() {
        updateOrderInformation();
    }
}
