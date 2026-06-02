package com.example.equi;

import android.content.Intent;

public abstract class RiderMenuActivity extends BaseMenuActivity {

    @Override
    protected int getMenuResourceId() {
        return R.menu.menu_rider_global;
    }

    @Override
    protected boolean handleMenuItemSelection(int itemId) {
        if (itemId == R.id.menu_rider_space) {
            Intent intent = new Intent(this, activity_acceuil.class);
            intent.putExtra("USER_NAME", resolveUserName());
            startActivity(intent);
            return true;
        } else if (itemId == R.id.menu_rider_logout) {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
            return true;
        }

        return false;
    }
}
