package com.cw.androidbase.sdk.utils;

import android.util.Log;

import com.cw.androidbase.sdk.log.Logger;

import java.security.KeyManagementException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Created by zhangjin on 16/3/25.
 */
public class SSLUtil {

    private static final String TAG = "SslUtil";

    public static void verify() {
        try {
            SSLContext sslcontext = SSLContext.getInstance("TLS");
            sslcontext = initInSecureTrustManagers(sslcontext);
            SSLSocketFactory sslSocketFactory = sslcontext.getSocketFactory();
            HttpsURLConnection.setDefaultSSLSocketFactory(sslSocketFactory);
            HttpsURLConnection.setDefaultHostnameVerifier(hv);
        } catch (Exception e) {
            Logger.d(TAG, "error msg: " + e.getMessage());
        }
    }

    private static SSLContext initInSecureTrustManagers(SSLContext sslcontext) throws KeyManagementException {
        sslcontext.init(null, INSECURE_TRUST_MANAGER, new java.security.SecureRandom());
        return sslcontext;
    }

    private static final TrustManager[] INSECURE_TRUST_MANAGER = new TrustManager[]{new X509TrustManager() {

        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[]{};
        }

        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }
    }};

    private static HostnameVerifier hv = new HostnameVerifier() {
        public boolean verify(String urlHostName, SSLSession session) {
            return true;
        }
    };
}
