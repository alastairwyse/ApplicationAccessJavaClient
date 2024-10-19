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

package net.alastairwyse.applicationaccessclient.models;

import java.util.ArrayList;
import java.util.Map.Entry;

/**
 * Container class holding the data returned from a REST API when an error occurs.
 */
public class HttpErrorResponse
{
    /** An internal code representing the error. */
    protected String code;
    /** A description of the error. */
    protected String message;
    /** The target of the error. */
    protected String target;
    /** A collection of key/value pairs which give additional details of the error. */
    protected ArrayList<Entry<String, String>> attributes;
    /** The error which caused this error. */
    protected HttpErrorResponse innerError;

    /** 
     * @return An internal code representing the error.
     */
    public String getCode() {
        return code; 
    }

    /** 
     * @return A description of the error.
     */
    public String getMessage() {
        return message; 
    }

    /** 
     * @return The target of the error.
     */
    public String getTarget() {
        return target; 
    }

    /** 
     * @return A collection of key/value pairs which give additional details of the error.
     */
    public Iterable<Entry<String, String>> getAttributes() {
        return attributes; 
    }

    /** 
     * @return The error which caused this error.
     */
    public HttpErrorResponse getInnerError() {
        return innerError; 
    }

    /**
     * Constructs a HttpErrorResponse.
     * 
     * @param code An internal code representing the error.
     * @param message A description of the error.
     */
    public HttpErrorResponse(String code, String message) {
        // Ordinarily would have exception handlers here for null or whitespace 'code' and 'message' parameters...
        //   However since this instances of this class will likely be created as part of exception handling code, we don't want to throw further exceptions and risk hiding/losing the original exception details.

        this.code = code;
        this.message = message;
        target = null;
        attributes = new ArrayList<Entry<String, String>>();
        innerError = null;
    }

    /**
     * Constructs a HttpErrorResponse.
     * 
     * @param code An internal code representing the error.
     * @param message A description of the error.
     * @param target The target of the error.
     */
    public HttpErrorResponse(String code, String message, String target) {
        this (code, message);

        this.target = target;
    }

    /**
     * Constructs a HttpErrorResponse.
     * 
     * @param code An internal code representing the error.
     * @param message A description of the error.
     * @param attributes A collection of key/value pairs which give additional details of the error.
     */
    public HttpErrorResponse(String code, String message, ArrayList<Entry<String, String>> attributes) {
        this(code, message);

        this.attributes = attributes;
    }

    /**
     * Constructs a HttpErrorResponse.
     * 
     * @param code An internal code representing the error.
     * @param message A description of the error.
     * @param innerError The error which caused this error.
     */
    public HttpErrorResponse(String code, String message, HttpErrorResponse innerError) {
        this(code, message);

        this.innerError = innerError;
    }

    /**
     * Constructs a HttpErrorResponse.
     * 
     * @param code An internal code representing the error.
     * @param message A description of the error.
     * @param target The target of the error.
     * @param attributes A collection of key/value pairs which give additional details of the error.
     */
    public HttpErrorResponse(String code, String message, String target, ArrayList<Entry<String, String>> attributes) {
        this(code, message);

        this.target = target;
        this.attributes = attributes;
    }

    /**
     * Constructs a HttpErrorResponse.
     * 
     * @param code An internal code representing the error.
     * @param message A description of the error.
     * @param attributes A collection of key/value pairs which give additional details of the error.
     * @param innerError The error which caused this error.
     */
    public HttpErrorResponse(String code, String message, ArrayList<Entry<String, String>> attributes, HttpErrorResponse innerError) {
        this(code, message);

        this.attributes = attributes;
        this.innerError = innerError;
    }

    /**
     * Constructs a HttpErrorResponse.
     * 
     * @param code An internal code representing the error.
     * @param message A description of the error.
     * @param target The target of the error.
     * @param innerError The error which caused this error.
     */
    public HttpErrorResponse(String code, String message, String target, HttpErrorResponse innerError) {
        this(code, message);

        this.target = target;
        this.innerError = innerError;
    }

    /**
     * Constructs a HttpErrorResponse.
     * 
     * @param code An internal code representing the error.
     * @param message A description of the error.
     * @param target The target of the error.
     * @param attributes A collection of key/value pairs which give additional details of the error.
     * @param innerError The error which caused this error.
     */
    public HttpErrorResponse(String code, String message, String target, ArrayList<Entry<String, String>> attributes, HttpErrorResponse innerError) {
        this(code, message);

        this.target = target;
        this.attributes = attributes;
        this.innerError = innerError;
    }
}