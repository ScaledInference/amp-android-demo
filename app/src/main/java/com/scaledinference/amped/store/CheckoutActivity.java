package com.scaledinference.amped.store;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.scaledinference.amped.store.lists.CheckoutAdapter;
import com.scaledinference.amped.store.model.CheckoutSession;
import com.scaledinference.amped.store.model.UserSession;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.scaledinference.amped.store.CartActivity.PRICE_STRING_FORMAT;

public class CheckoutActivity extends AppCompatActivity {
    private CheckoutSession checkoutSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        checkoutSession = UserSession.getInstance().getCheckoutSession();
        setTheme(checkoutSession.getTheme());

        setContentView(R.layout.checkout_activity);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager mLayoutManager =
                new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.setAdapter(new CheckoutAdapter(checkoutSession.getOrder()));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        updateOrderInformation();
    }

    void updateOrderInformation() {
        // Subtotal
        TextView subtotalTextView = findViewById(R.id.subtotalTextView);
        subtotalTextView.setText(
                String.format(PRICE_STRING_FORMAT, checkoutSession.getOrder().getSubtotalCost()));

        // Discount
        TextView discountTextView = findViewById(R.id.discountTextView);
        discountTextView.setText(
                String.format(PRICE_STRING_FORMAT, checkoutSession.getOrder().getDiscountAmount()));

        // Total
        TextView totalTextView = findViewById(R.id.totalTextView);
        totalTextView.setText(
                String.format(PRICE_STRING_FORMAT, checkoutSession.getOrder().getSubtotalCost()));

        // Delivery
        TextView deliveryDateTextView = findViewById(R.id.deliveryDateTextView);
        deliveryDateTextView.setText(String.valueOf(checkoutSession.getOrder().getTotalCount()));

        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT);
        String deliveryDateString = dateFormat.format(new Date());
        deliveryDateTextView.setText(deliveryDateString);
    }

    public void placeOrder(View view) {
        Map<String, Object> properties = new HashMap<>();
        properties.put("totalCost", checkoutSession.getOrder().getTotalCost());
        properties.put("count", checkoutSession.getOrder().getTotalCount());

        checkoutSession.reportEvent("Checkout event", properties);

        startActivity(new Intent(this, ThanksActivity.class));
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
}
