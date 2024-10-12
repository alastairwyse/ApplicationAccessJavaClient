package net.alastairwyse.applicationaccessclient;

import java.net.URI;
import java.net.http.HttpClient;
import java.util.Map;

/**
 * Client class which interfaces to an AccessManager instance hosted as a REST web API, where users, groups, application components, and access levels are strings.
 */
public class AccessManagerStringElementClient extends AccessManagerClient<String, String, String, String> {

    /**
     * Constructs an AccessManagerStringElementClient.
     * 
     * @param httpClient The client to use to connect.
     * @param baseUrl The base URL for the hosted Web API.
     */
    public AccessManagerStringElementClient(
        HttpClient httpClient, 
        URI baseUrl
    ) {
        super(httpClient, baseUrl, new StringUniqueStringifier(), new StringUniqueStringifier(), new StringUniqueStringifier(), new StringUniqueStringifier());
    }

    /**
     * Constructs an AccessManagerClient.
     * 
     * @param httpClient The client to use to connect.
     * @param baseUrl The base URL for the hosted Web API.
     * @param requestHeaders HTTP headers to send with each request.
     */
    public AccessManagerStringElementClient(
        HttpClient httpClient, 
        URI baseUrl, 
        Map<String, String> requestHeaders
    ) {
        super(httpClient, baseUrl, new StringUniqueStringifier(), new StringUniqueStringifier(), new StringUniqueStringifier(), new StringUniqueStringifier(), requestHeaders);
    }
}
