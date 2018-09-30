package com.dawoo.gamebox.view.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dawoo.gamebox.R;
import com.dyhdyh.widget.loading.factory.LoadingFactory;

/**
 * Created by benson on 18-1-2.
 */

public class CustomLoadingFactory implements LoadingFactory {
    @Override
    public View onCreateView(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.loading_progress_dialog_color_anim, parent, false);

        return view;
    }
}
