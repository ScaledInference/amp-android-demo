package com.scaledinference.amped.store;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.scaledinference.amped.store.lists.StoreAdapter;
import com.scaledinference.amped.store.model.CheckoutSession;
import com.scaledinference.amped.store.model.UserSession;

public class StoreActivity extends AppCompatActivity implements OrderUpdateListener {
    private RecyclerView recyclerView;
    private CheckoutSession checkoutSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        checkoutSession = UserSession.getInstance().getCheckoutSession();
        setTheme(checkoutSession.getTheme());

        setContentView(R.layout.store_activity);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager =
                new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.setAdapter(
                new StoreAdapter(DataProvider.getCompositeItems(this), checkoutSession.getOrder()));
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkoutSession.getOrder().addListener(this);
        recyclerView.getAdapter().notifyDataSetChanged();
        invalidateOptionsMenu();
    }

    @Override
    protected void onStop() {
        super.onStop();
        checkoutSession.getOrder().removeListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater findMenuItems = getMenuInflater();
        findMenuItems.inflate(R.menu.store_menu, menu);
        MenuItem cartMenuItem = menu.findItem(R.id.action_cart);
        String title = getString(R.string.cart)+ " - " + checkoutSession.getOrder().getTotalCount();
        cartMenuItem.setTitle(title);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_cart:
                Intent intent = new Intent(this, CartActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void orderUpdated() {
        invalidateOptionsMenu();
    }
}
