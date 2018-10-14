package org.hanan.gcm.gcmdep;

import com.google.android.gcm.server.Endpoint;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Sender;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Service
public class Gcm {
    private static final String ANDROID_UPDATER_SERVER_KEY = "AIzaSyAG4pFB7K1NBMtUgSSBAwg8hazpxmavW1A";

    private Sender sender;

    @PostConstruct
    public void init() {
        sender = new Sender(ANDROID_UPDATER_SERVER_KEY, Endpoint.FCM);
    }

    public void sendMessage(String payload, String registrationToken) throws IOException {
        final Message.Builder builder = new Message.Builder().timeToLive(1000);
        builder.addData("payload", payload);
        final com.google.android.gcm.server.Result gcmResult = sender.send(builder.build(), registrationToken, 2);
    }
}
