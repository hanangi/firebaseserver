package org.hanan.gcm.gcmdep;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;

@Service
public class Fcm {

    @PostConstruct
    public void init() throws IOException {
        //FileInputStream serviceAccount = new FileInputStream("path/to/serviceAccountKey.json");
        InputStream serviceAccount = this.getClass().getResourceAsStream("hanan-push-project-firebase-adminsdk-qux1z-d7df2e9a19.json");

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://hanan-push-project.firebaseio.com")
                .build();

        FirebaseApp.initializeApp(options);
    }

    public void sendMessage(String payload, String registrationToken) throws FirebaseMessagingException {

        // See documentation on defining a message payload.
        Message message = Message.builder()
                //.putData("score", "850")
                //.putData("time", "2:45")
                .putData("payload", payload)
                .setToken(registrationToken)
                /*.setNotification(new Notification(
                        "Hanan Test",
                        payload))*/
                .build();

        // Send a message to the device corresponding to the provided
        // registration token.
        String response = FirebaseMessaging.getInstance().send(message);
        // Response is a message ID string.
        System.out.println("Successfully sent message: " + response);
    }
}
