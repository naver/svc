package com.naver.android.svc.core.screen;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;

/**
 * @author bs.nam@navercorp.com
 */
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
