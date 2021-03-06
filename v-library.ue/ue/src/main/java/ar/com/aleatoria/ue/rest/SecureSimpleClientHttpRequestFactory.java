package ar.com.aleatoria.ue.rest;

/*
 * Copyright 2002-2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.util.Assert;

/**
 * This class is a modification of the class SimpleClientHttpRequestFactory that make us able
 * to set a CredentialProvider.
 * 
 * @author Arjen Poutsma
 * @author Roy Clarkson
 * @autor Agus
 * @see java.net.HttpURLConnection
 * @see org.springframework.http.client.SimpleClientHttpRequestFactory
 * @since 1.0
 */
public class SecureSimpleClientHttpRequestFactory implements ClientHttpRequestFactory {

	// Specifying 0 results in the Android system default chunk length
	private static final int DEFAULT_CHUNK_SIZE = 0;

	private Proxy proxy;

	private boolean bufferRequestBody = true;

	private int chunkSize = DEFAULT_CHUNK_SIZE;

	private int connectTimeout = -1;

	private int readTimeout = -1;

	/**
	 * Sets the {@link Proxy} to use for this request factory.
	 */
	public void setProxy(Proxy proxy) {
		this.proxy = proxy;
	}
	
    protected CredentialsProvider credentialsProvider;
    
    public SecureSimpleClientHttpRequestFactory() {
    }

    public CredentialsProvider getCredentialsProvider() {
            return credentialsProvider;
    }

    public void setCredentialsProvider(CredentialsProvider credentialsProvider) {
            this.credentialsProvider = credentialsProvider;
    }
    
    /**
     * If you want specify the encrypting method that use your own SecureSimpleClient implementation
     * then you should override this class and specify the authentication method.
     * @param connection
     */
     public void prepareSecureConnection(HttpURLConnection connection) {}

	/**
	 * Indicates whether this request factory should buffer the {@linkplain ClientHttpRequest#getBody() request body}
	 * internally.
	 * <p>
	 * Default is {@code true}. When sending large amounts of data via POST or PUT, it is recommended to change this
	 * property to {@code false}, so as not to run out of memory. This will result in a {@link ClientHttpRequest} that
	 * either streams directly to the underlying {@link HttpURLConnection} (if the
	 * {@link org.springframework.http.HttpHeaders#getContentLength() Content-Length} is known in advance), or that will
	 * use "Chunked transfer encoding" (if the {@code Content-Length} is not known in advance).
	 * 
	 * @see #setChunkSize(int)
	 * @see HttpURLConnection#setFixedLengthStreamingMode(int)
	 */
	public void setBufferRequestBody(boolean bufferRequestBody) {
		this.bufferRequestBody = bufferRequestBody;
	}

	/**
	 * Sets the number of bytes to write in each chunk when not buffering request bodies locally.
	 * <p>
	 * Note that this parameter is only used when {@link #setBufferRequestBody(boolean) bufferRequestBody} is set to
	 * {@code false}, and the {@link org.springframework.http.HttpHeaders#getContentLength() Content-Length} is not
	 * known in advance.
	 * 
	 * @see #setBufferRequestBody(boolean)
	 */
	public void setChunkSize(int chunkSize) {
		this.chunkSize = chunkSize;
	}

	/**
	 * Set the underlying URLConnection's connect timeout (in milliseconds). A timeout value of 0 specifies an infinite
	 * timeout.
	 * <p>
	 * Default is the system's default timeout.
	 * 
	 * @see URLConnection#setConnectTimeout(int)
	 */
	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	/**
	 * Set the underlying URLConnection's read timeout (in milliseconds). A timeout value of 0 specifies an infinite
	 * timeout.
	 * <p>
	 * Default is the system's default timeout.
	 * 
	 * @see URLConnection#setReadTimeout(int)
	 */
	public void setReadTimeout(int readTimeout) {
		this.readTimeout = readTimeout;
	}


	public ClientHttpRequest createRequest(URI uri, HttpMethod httpMethod) throws IOException {
		HttpURLConnection connection = openConnection(uri.toURL(), this.proxy);
		prepareConnection(connection, httpMethod.name());
		prepareSecureConnection(connection);
		if (this.bufferRequestBody) {
			return new SimpleBufferingClientHttpRequest(connection);
		} else {
			return new SimpleStreamingClientHttpRequest(connection, this.chunkSize);
		}
	}

	/**
	 * Opens and returns a connection to the given URL.
	 * <p>
	 * The default implementation uses the given {@linkplain #setProxy(java.net.Proxy) proxy} - if any - to open a
	 * connection.
	 * 
	 * @param url the URL to open a connection to
	 * @param proxy the proxy to use, may be {@code null}
	 * @return the opened connection
	 * @throws IOException in case of I/O errors
	 */
	protected HttpURLConnection openConnection(URL url, Proxy proxy) throws IOException {
		URLConnection urlConnection = (proxy != null ? url.openConnection(proxy) : url.openConnection());
		Assert.isInstanceOf(HttpURLConnection.class, urlConnection);
		return (HttpURLConnection) urlConnection;
	}

	/**
	 * Template method for preparing the given {@link HttpURLConnection}.
	 * <p>
	 * The default implementation prepares the connection for input and output, and sets the HTTP method.
	 * 
	 * @param connection the connection to prepare
	 * @param httpMethod the HTTP request method ({@code GET}, {@code POST}, etc.)
	 * @throws IOException in case of I/O errors
	 */
	protected void prepareConnection(HttpURLConnection connection, String httpMethod) throws IOException {
		if (this.connectTimeout >= 0) {
			connection.setConnectTimeout(this.connectTimeout);
		}
		if (this.readTimeout >= 0) {
			connection.setReadTimeout(this.readTimeout);
		}
		connection.setDoInput(true);
		if ("GET".equals(httpMethod)) {
			connection.setInstanceFollowRedirects(true);
		} else {
			connection.setInstanceFollowRedirects(false);
		}
		if ("PUT".equals(httpMethod) || "POST".equals(httpMethod)) {
			connection.setDoOutput(true);
		} else {
			connection.setDoOutput(false);
		}
		connection.setRequestMethod(httpMethod);
	}

}
