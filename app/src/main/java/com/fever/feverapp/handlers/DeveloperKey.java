// Copyright 2014 Google Inc. All Rights Reserved.

package com.fever.feverapp.handlers;

/**
 * Static container class for holding a reference to your YouTube Developer Key.
 */
public class DeveloperKey {
  /**
   * Please replace this with a valid API key which is enabled for the
   * YouTube Data API v3 service. Go to the
   * <a href="https://console.developers.google.com/">Google Developers Console</a>
   * to register a new developer key.
   */
  private static final String DEVELOPER_KEY = "AIzaSyACP-8mTF37GLE5lI3KEkE2f-Bl5y5oXjU";

  public static String getDeveloperKey() {
    return DEVELOPER_KEY;
  }
}
