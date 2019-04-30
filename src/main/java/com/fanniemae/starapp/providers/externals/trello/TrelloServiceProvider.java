package com.fanniemae.starapp.providers.externals.trello;

import com.julienvey.trello.Trello;
import com.julienvey.trello.impl.TrelloImpl;
import com.julienvey.trello.impl.http.ApacheHttpClient;
import org.apache.http.HttpHost;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.SSLContext;
import java.security.cert.X509Certificate;

import static org.apache.http.conn.params.ConnRoutePNames.DEFAULT_PROXY;

@Configuration
public class TrelloServiceProvider {

    private static final Logger LOGGER = LogManager.getLogger(TrelloServiceProvider.class);

    @Value("${starapp.trello.key}")
    private String key;

    @Value("${starapp.trello.token}")
    private String accountAuthToken;

    @Bean
    public Trello createTrelloProvider() throws Exception {
        HttpHost proxy = new HttpHost("104.129.194.41",10479);

        DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxy);

        TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;

        SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom()
                .loadTrustMaterial(null, acceptingTrustStrategy)
                .build();

        SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);

        HttpClient httpclient = HttpClients.custom()
                .setSSLSocketFactory(csf)
                .setRoutePlanner(routePlanner).build();
        //HttpClient httpclient = HttpClients.custom().setSSLHostnameVerifier(new NoopHostnameVerifier()).setRoutePlanner(routePlanner).build();
        return new TrelloImpl(key, accountAuthToken, new ApacheHttpClient(httpclient));
    }
}
