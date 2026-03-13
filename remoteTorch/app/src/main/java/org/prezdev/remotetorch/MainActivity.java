package org.prezdev.remotetorch;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import org.prezdev.remotetorch.model.FlashlightProvider;
import org.prezdev.remotetorch.model.IP;
import org.prezdev.remotetorch.model.Rules;
import org.prezdev.remotetorch.model.request.GetPhoneInfo;
import org.prezdev.remotetorch.model.request.ToggleLight;
import org.prezdev.remotetorch.model.response.PhoneInfoResponse;
import org.prezdev.remotetorch.model.response.ToggleLightResponse;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {
    private FlashlightProvider flashlightProvider;
    private ImageView torchImage;
    private TextView serverIpTextView;
    private InetAddress discoveredAddress;
    private Client client;
    private Server server;
    private PhoneInfoResponse phoneInfoResponse;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        torchImage = findViewById(R.id.torchImage);
        serverIpTextView = findViewById(R.id.serverIpTextView);

        flashlightProvider = new FlashlightProvider(getApplicationContext());

        torchImage.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if(client == null){
                    client = new Client();
                    client.start();

                    Kryo kryo = client.getKryo();

                    kryo.register(ToggleLight.class);
                    kryo.register(ToggleLightResponse.class);
                    kryo.register(GetPhoneInfo.class);
                    kryo.register(PhoneInfoResponse.class);

                    try {
                        client.addListener(new Listener() {
                            public void received (Connection connection, Object object) {
                                if (object instanceof ToggleLightResponse) {
                                    ToggleLightResponse response = (ToggleLightResponse)object;
                                    if(response.isLightOn()){
                                        torchImage.setColorFilter(
                                            getColor(R.color.lightOn),
                                            PorterDuff.Mode.SRC_IN
                                        );
                                    }else{
                                        torchImage.setColorFilter(
                                            getColor(R.color.lightOff),
                                            PorterDuff.Mode.SRC_IN
                                        );
                                    }
                                }else if(object instanceof  PhoneInfoResponse){
                                    phoneInfoResponse = (PhoneInfoResponse)object;
                                    System.out.println(phoneInfoResponse);
                                }
                            }
                        });

                        client.connect(
                            Rules.TIMEOUT,
                            discoveredAddress.getHostAddress(),
                            Rules.TCP_PORT,
                            Rules.UDP_PORT
                        );

                        client.sendTCP(new GetPhoneInfo());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                client.sendTCP(new ToggleLight());
            }
        });

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    public void onClickListener(View v) throws UnknownHostException {
        switch (v.getId()){
            case R.id.startServerButton:{
                if(server == null){
                    server = new Server();
                    server.start();
                    try {
                        server.bind(Rules.TCP_PORT, Rules.UDP_PORT);
                        Kryo kryo = server.getKryo();

                        kryo.register(ToggleLight.class);
                        kryo.register(ToggleLightResponse.class);
                        kryo.register(GetPhoneInfo.class);
                        kryo.register(PhoneInfoResponse.class);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    server.addListener(new Listener(){
                        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                        public void received (Connection connection, Object object) {
                            if (object instanceof ToggleLight) {
                                boolean isLightOn = flashlightProvider.toggleLight();

                                if(isLightOn){
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        torchImage.setColorFilter(
                                                getColor(R.color.lightOn),
                                                PorterDuff.Mode.SRC_IN
                                        );
                                    }
                                }else{
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        torchImage.setColorFilter(getColor(
                                                R.color.lightOff),
                                                PorterDuff.Mode.SRC_IN
                                        );
                                    }
                                }

                                connection.sendTCP(new ToggleLightResponse(isLightOn));
                            }else if(object instanceof GetPhoneInfo){
                                PhoneInfoResponse response = new PhoneInfoResponse();

                                response.setAndroidVersion(Build.VERSION.RELEASE);
                                response.setModel(Build.MODEL);
                                response.setProduct(Build.PRODUCT);
                                response.setBrand(Build.BRAND);

                                connection.sendTCP(response);
                            }
                        }
                    });

                    Client client = new Client();
                    discoveredAddress = client.discoverHost(Rules.UDP_PORT, Rules.TIMEOUT);
                }

                serverIpTextView.setText(IP.getLanIP());
                break;
            }

            case R.id.discoverButton: {
                Client client = new Client();
                discoveredAddress = client.discoverHost(Rules.UDP_PORT, Rules.TIMEOUT);
                System.out.println("ADDRESS!!!: "+ discoveredAddress);
                break;
            }
        }
    }
}
