package com.example.equi;

import android.content.Intent;

public abstract class AdminMenuActivity extends BaseMenuActivity {

    @Override
    protected int getMenuResourceId() {
        return R.menu.menu_admin_global;
    }

    @Override
    protected boolean handleMenuItemSelection(int itemId) {
        if (itemId == R.id.menu_admin_dashboard) {
            Intent intent = new Intent(this, ActivityAdmin.class);
            intent.putExtra("USER_NAME", resolveUserName());
            startActivity(intent);
            return true;
        } else if (itemId == R.id.menu_admin_horses) {
            startActivity(new Intent(this, activity_club_horses.class));
            return true;
        } else if (itemId == R.id.menu_admin_riders) {
            startActivity(new Intent(this, RiderListActivity.class));
            return true;
        } else if (itemId == R.id.menu_admin_sessions) {
            startActivity(new Intent(this, ManageSessionsActivity.class));
            return true;
        } else if (itemId == R.id.menu_admin_logout) {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
            return true;
        }

        return false;
    }
}
