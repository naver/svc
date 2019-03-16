package com.naver.android.svc.core.screen;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

/**
 * @author bs.nam@navercorp.com
 */
public class SafeDialogFragment extends DialogFragment {

    @Override
    public void show(@NonNull FragmentManager manager, String tag) {
        try {
            super.show(manager, tag);
        } catch (IllegalStateException e) {
            manager.beginTransaction().add(this, tag).commitAllowingStateLoss();
        }
    }
}
