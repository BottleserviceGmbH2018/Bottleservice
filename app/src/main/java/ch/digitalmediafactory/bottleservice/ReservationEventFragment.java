package ch.digitalmediafactory.bottleservice;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by Chris Gacu on 21/02/2018.
 */

public class ReservationEventFragment extends Fragment {
    private static final String TAG = "ReservationBarFragment";



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.reservation_event_fragment,container,false);



        return view;
    }
}