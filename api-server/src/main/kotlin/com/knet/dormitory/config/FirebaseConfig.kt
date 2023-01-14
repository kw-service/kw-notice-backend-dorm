package com.knet.dormitory.config

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.messaging.FirebaseMessaging
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.io.FileInputStream


@Configuration
class FirebaseConfig {
    @Value(value = "\${firebase.secret.path}")
    lateinit var firebaseSecretKeyPath: String

    @Bean
    fun firebaseApp(): FirebaseMessaging {
        val serviceAccount = FileInputStream(firebaseSecretKeyPath)
        val options: FirebaseOptions = FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            .build()
        val app = FirebaseApp.initializeApp(options, "dormitory-notice")
        return FirebaseMessaging.getInstance(app);
    }
}