package ch.digitalmediafactory.bottleservice;

/**
 * Created by Chris Gacu on 21/02/2018.
 */
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by User on 2/28/2017.
 */

public class OutboxFragment2 extends Fragment {
    private static final String TAG = "OutboxFragment2";



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.outbox_fragment,container,false);

        return view;
    }
}