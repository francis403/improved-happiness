package fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.gamethetown.R;


public class AddPhotoFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_photo, container, false);

        Button button = (Button) view.findViewById(R.id.setPhoto);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmPhoto(v);
            }
        });


        return view;
    }

    public void confirmPhoto(View view){
        MapFragment mFrag = new MapFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.map,mFrag);

        transaction.addToBackStack(null);
        transaction.commit();
    }

}
