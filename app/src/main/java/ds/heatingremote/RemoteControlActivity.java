package ds.heatingremote;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.particle.android.sdk.cloud.ParticleCloud;
import io.particle.android.sdk.cloud.ParticleCloudException;
import io.particle.android.sdk.cloud.ParticleDevice;
import io.particle.android.sdk.utils.Async;

/**
 * Created by Damian Smith on 03/01/2016.
 * Twitter @Javawocky
 */
public class RemoteControlActivity extends Activity {

    private Button auxOn;
    private Button heatOn;
    private Button heatOff;
    private Button auxOff;

    int aux;
    int heat;

    public int getAux() {
        return aux;
    }

    public void setAux(int aux) {
        this.aux = aux;
    }

    public int getHeat() {
        return heat;
    }

    public void setHeat(int heat) {
        this.heat = heat;
    }

    private static final String ARG_DEVICEID = "ARG_DEVICEID";

    public static Intent buildIntent(Context ctx, Integer value, String deviceid) {
        Intent intent = new Intent(ctx, StartActivity.class);
        intent.putExtra(ARG_DEVICEID, deviceid);

        return intent;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_remote);

        this.auxOn = (Button)this.findViewById(R.id.aux_button_on);
        this.auxOff = (Button)this.findViewById(R.id.aux_btn_off);
        this.heatOn = (Button)this.findViewById(R.id.heating_btn_on);
        this.heatOff = (Button)this.findViewById(R.id.heating_button_off);

        if(getAux()==0){
            this.auxOn.setBackgroundColor(Color.MAGENTA);
            this.auxOff.setBackgroundColor(Color.LTGRAY);
        }else{
            this.auxOff.setBackgroundColor(Color.MAGENTA);
            this.auxOn.setBackgroundColor(Color.LTGRAY);
        }

        if(getHeat()==0){
            this.heatOn.setBackgroundColor(Color.MAGENTA);
            this.heatOff.setBackgroundColor(Color.LTGRAY);
        }else {
            this.heatOff.setBackgroundColor(Color.MAGENTA);
            this.heatOn.setBackgroundColor(Color.LTGRAY);

        }
        /************************************
            Relay Switch Buttons
        ************************************/
        findViewById(R.id.heating_btn_on).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Async.executeAsync(ParticleCloud.get(RemoteControlActivity.this), new Async.ApiWork<ParticleCloud, Integer>() {
                    @Override
                    public Integer callApi(ParticleCloud particleCloud) throws ParticleCloudException, IOException {
                        ParticleDevice device = particleCloud.getDevice(getIntent().getStringExtra(ARG_DEVICEID));
                        List<String> list = new ArrayList<>();
                        list.add("on");
                        list.add("1");
                        try{
                           return device.callFunction("heatSwitchOn", list);
                        }catch(ParticleDevice.FunctionDoesNotExistException e){
                            Log.e("ERR", e.toString());
                        }
                        return -2;
                    }

                    public void onSuccess(Integer result) {
                        heatOn.setBackgroundColor(Color.MAGENTA);
                        heatOff.setBackgroundColor(Color.LTGRAY);
                    }
                    public void onFailure(ParticleCloudException value) {
                    }
                });
            }
        });

        findViewById(R.id.aux_button_on).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Async.executeAsync(ParticleCloud.get(RemoteControlActivity.this), new Async.ApiWork<ParticleCloud, Integer>() {
                    @Override
                    public Integer callApi(ParticleCloud particleCloud) throws ParticleCloudException, IOException {
                        ParticleDevice device = particleCloud.getDevice(getIntent().getStringExtra(ARG_DEVICEID));
                        List<String> list = new ArrayList<>();
                        list.add("on");
                        list.add("1");
                        try{
                            return device.callFunction("auxSwitchOn", list);
                        }catch(ParticleDevice.FunctionDoesNotExistException e){
                            Log.e("ERR", e.toString());
                        }
                        return -1;
                    }

                    public void onSuccess(Integer result) {
                        auxOn.setBackgroundColor(Color.MAGENTA);
                        auxOff.setBackgroundColor(Color.LTGRAY);

                    }
                    public void onFailure(ParticleCloudException value) {
                        Log.e("ERR", "Aux Button Fail : " + value.toString());
                    }
                });
            }
        });

        findViewById(R.id.heating_button_off).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Async.executeAsync(ParticleCloud.get(RemoteControlActivity.this), new Async.ApiWork<ParticleCloud, Integer>() {
                    @Override
                    public Integer callApi(ParticleCloud particleCloud) throws ParticleCloudException, IOException {
                        ParticleDevice device = particleCloud.getDevice(getIntent().getStringExtra(ARG_DEVICEID));
                        List<String> list = new ArrayList<>();
                        list.add("off");
                        list.add("1");
                        try{
                            return device.callFunction("turnoffheat", list);
                        }catch(ParticleDevice.FunctionDoesNotExistException e){
                            Log.e("ERR", e.toString());
                        }
                        return -1;
                    }

                    public void onSuccess(Integer result) {
                        heatOff.setBackgroundColor(Color.MAGENTA);
                        heatOn.setBackgroundColor(Color.LTGRAY);
                    }
                    public void onFailure(ParticleCloudException value) {
                        Log.e("ERR", "Heat Button Fail : " + value.toString());
                    }
                });

            }
        });

        findViewById(R.id.aux_btn_off).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Async.executeAsync(ParticleCloud.get(RemoteControlActivity.this), new Async.ApiWork<ParticleCloud, Integer>() {
                    @Override
                    public Integer callApi(ParticleCloud particleCloud) throws ParticleCloudException, IOException {
                        ParticleDevice device = particleCloud.getDevice(getIntent().getStringExtra(ARG_DEVICEID));
                        List<String> list = new ArrayList<>();
                        list.add("off");
                        list.add("1");
                        try{
                            return device.callFunction("auxSwitchOff", list);
                        }catch(ParticleDevice.FunctionDoesNotExistException e){
                            Log.e("ERR", e.toString());
                        }
                        return -1;
                    }

                    public void onSuccess(Integer result) {
                        auxOff.setBackgroundColor(Color.MAGENTA);
                        auxOn.setBackgroundColor(Color.LTGRAY);
                    }
                    public void onFailure(ParticleCloudException value) {
                        Log.e("ERR", "Aux Button Fail : " + value.toString());
                    }
                });

            }
        });
    }
}




