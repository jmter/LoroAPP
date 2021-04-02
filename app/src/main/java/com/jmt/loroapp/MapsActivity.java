package com.jmt.loroapp;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private TextView ultimomensaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        iniciarGraficos();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        readInfo(new FireabaseReadInfo() {
            @Override
            public void onCallback(ArrayList<EStatus> eStatusArrayList) {
                for (int i = 0; i < 1; i++) {
                    EStatus eStatus = eStatusArrayList.get(eStatusArrayList.size() - 1-i);
                    Log.i("ES", String.valueOf(eStatusArrayList.get(eStatusArrayList.size() - 1-i).getId()));
                    LatLng sydney = new LatLng(Float.parseFloat(eStatus.getLatitude()), Float.parseFloat(eStatus.getLongitude()));
                    ultimomensaje.setText("Ultimo mensaje: " + eStatus.getHora() + ":" + eStatus.getMin() + " " + eStatus.getDia() + "/" + eStatus.getMes() + "/" + eStatus.getAno());
                    mMap.addMarker(new MarkerOptions().position(sydney).title("Horacio"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15));
                }
            }
        },"+59898703609");

        // Add a marker in Sydney and move the camera

    }
    private void iniciarGraficos(){
        ultimomensaje = findViewById(R.id.UltimoMensaje);
    }
    private void readInfo(final FireabaseReadInfo fireabaseReadInfo, String adress){
        FirebaseDatabase.getInstance().getReference().child("MonitorData").child("Fabidal").child(adress).orderByKey().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<EStatus> eStatusArrayList = new ArrayList<>();
                for(DataSnapshot ds:snapshot.getChildren()){
                    EStatus eStatus = ds.getValue(EStatus.class);
                    eStatusArrayList.add(eStatus );
                }
                fireabaseReadInfo.onCallback(eStatusArrayList);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private interface FireabaseReadInfo{
        void onCallback(ArrayList<EStatus> eStatusArrayList);
    }

}
