package com.example.equi;

public final class DrawableResolver {

    private DrawableResolver() {
    }

    public static int resolveHorseImage(String photoUrl) {
        if (photoUrl == null) {
            return R.drawable.logo_equ;
        }

        switch (photoUrl) {
            case "denverdeberni":
                return R.drawable.denverdeberni;
            case "calypsodream":
                return R.drawable.calypsodream;
            case "flashrider":
                return R.drawable.flashrider;
            case "zahra":
                return R.drawable.zahra;
            case "apacheking":
                return R.drawable.apacheking;
            case "logo_equ":
            default:
                return R.drawable.logo_equ;
        }
    }
}
