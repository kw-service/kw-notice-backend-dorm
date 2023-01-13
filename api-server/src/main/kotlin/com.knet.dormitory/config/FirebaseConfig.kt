package com.knet.dormitory.config

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.io.FileInputStream


@Configuration
class FirebaseConfig {
    @Value(value = "\${firebase.secret.path}")
    lateinit var firebaseSecretKeyPath: String

    @Bean
    fun firebaseApp(): FirebaseApp {
        val serviceAccount = FileInputStream(firebaseSecretKeyPath)
        val options: FirebaseOptions = FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            .build()
        return FirebaseApp.initializeApp(options, "dormitory-notice")
    }
}