/*
 * Copyright 2024 Alastair Wyse (https://github.com/alastairwyse/ApplicationAccessJavaClient/)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
     * @param baseUrl The base URL for the hosted Web API.
     */
    public AccessManagerStringElementClient(URI baseUrl) {
        super(baseUrl, new StringUniqueStringifier(), new StringUniqueStringifier(), new StringUniqueStringifier(), new StringUniqueStringifier());
    }

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
