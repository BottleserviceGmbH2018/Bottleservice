package ch.digitalmediafactory.bottleservice;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Chris Gacu on 06/03/2018.
 */

public class OutboxApp2 extends Fragment {
    private static final String TAG = "OutboxApp2";



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.outbox_appowner,container,false);

        return view;
    }
}
