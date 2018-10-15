package org.hanan.gcm.gcmdep;

import com.google.firebase.messaging.FirebaseMessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
@RequestMapping({"/api/v1"})
public class TokenController {

    private String firebaseToken;
    private String gcmToken;

    @Autowired private  Fcm fcm;
    @Autowired private  Gcm gcm;

    @RequestMapping(value = "/token", method= RequestMethod.POST)
    public @ResponseBody ResponseEntity<String> tokenPost(String token, Boolean isGcm) {
        if(isGcm) this.gcmToken = token;
        else this.firebaseToken = token;
        return new ResponseEntity("ok", HttpStatus.OK);
    }

    @RequestMapping(value = "/token", method= RequestMethod.GET)
    public @ResponseBody ResponseEntity<String> tokenGet(Boolean isGcm) {
        return new ResponseEntity(isGcm ? gcmToken : firebaseToken, HttpStatus.OK);
    }

    @RequestMapping(value = "/message", method= RequestMethod.POST)
    public @ResponseBody ResponseEntity<String> messagePost(String message, Boolean isGcm) throws FirebaseMessagingException, IOException {

        if(isGcm)gcm.sendMessage(message, gcmToken);
        else fcm.sendMessage(message, firebaseToken);
        return new ResponseEntity("ok", HttpStatus.OK);
    }
}
