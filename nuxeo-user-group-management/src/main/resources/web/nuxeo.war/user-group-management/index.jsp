<!--
(C) Copyright 2016 Nuxeo SA (http://nuxeo.com/) and others.
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

Contributors:
    Gabriel Barata <gbarata@nuxeo.com>
-->
<%@ page import="org.nuxeo.common.Environment"%>
<%@ page import="org.nuxeo.runtime.api.Framework"%>

<!doctype html>
<html lang="">

<head>
  <meta charset="utf-8">
  <meta name="description" content="">
  <meta name="viewport" content="width=device-width, initial-scale=1">

  <title>Nuxeo User &amp; Group Management</title>

  <!-- Chrome for Android theme color -->
  <meta name="theme-color" content="#2E3AA1">

  <!-- Web Application Manifest -->
  <link rel="manifest" href="manifest.json">

  <!-- Tile color for Win8 -->
  <meta name="msapplication-TileColor" content="#3372DF">

  <!-- Add to homescreen for Chrome on Android -->
  <meta name="mobile-web-app-capable" content="yes">
  <meta name="application-name" content="PSK">
  <link rel="icon" sizes="192x192" href="images/touch/chrome-touch-icon-192x192.png">

  <!-- Add to homescreen for Safari on iOS -->
  <meta name="apple-mobile-web-app-capable" content="yes">
  <meta name="apple-mobile-web-app-status-bar-style" content="black">
  <meta name="apple-mobile-web-app-title" content="Nuxeo User Management">
  <link rel="apple-touch-icon" href="images/touch/apple-touch-icon.png">

  <!-- Tile icon for Win8 (144x144) -->
  <meta name="msapplication-TileImage" content="images/touch/ms-touch-icon-144x144-precomposed.png">

  <link rel="stylesheet" href="select2.css">
  <script src="webcomponents-lite.min.js"></script>
  <script src="jquery.js"></script>
  <script src="select2.js"></script>

  <link rel="import" href="nuxeo-user-group-management.html">

  <style is="custom-style">
    body {
      background-color: #fafafa;
      margin: 0;
      padding: 0;
      font-family: 'Roboto', 'Noto', sans-serif;
    }
    #header {
      background-color: #fff;
      color: #00adff ;
      box-shadow: 0 1px 1px rgba(0,0,0,0.2);
      width: 100%;
      z-index: 2;
    }
    #container {
      background-color: #fff;
      border: 1px solid #ddd;
      width: auto;
      margin: 2% 4%;
      padding: 24px;
    }
    h3 {
      margin-left: 24px;
    }
  </style>
</head>

<body unresolved class="fullbleed layout vertical">
  <nuxeo-connection url="<%= request.getContextPath() %>"></nuxeo-connection>
  <div id="header">
    <h3>Users & Groups Management</h3>
  </div>
  <div id="container">
    <nuxeo-user-group-management></nuxeo-user-group-management>
  </div>
</body>

</html>
