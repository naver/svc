package com.naver.android.svc.core.screen;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

public class SafeDialogFragment extends DialogFragment {

    @Override
    public void show(FragmentManager manager, String tag) {
        try {
            super.show(manager, tag);
        } catch (IllegalStateException e) {
            manager.beginTransaction().add(this, tag).commitAllowingStateLoss();
        }
    }
}
