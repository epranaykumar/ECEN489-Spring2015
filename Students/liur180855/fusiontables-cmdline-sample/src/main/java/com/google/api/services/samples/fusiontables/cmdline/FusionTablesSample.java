/*
 * Copyright (c) 2012 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.google.api.services.samples.fusiontables.cmdline;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.store.DataStoreFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.fusiontables.Fusiontables;
import com.google.api.services.fusiontables.Fusiontables.Query.Sql;
import com.google.api.services.fusiontables.Fusiontables.Table.Delete;
import com.google.api.services.fusiontables.FusiontablesScopes;
import com.google.api.services.fusiontables.model.Column;
import com.google.api.services.fusiontables.model.Table;
import com.google.api.services.fusiontables.model.TableList;
import javax.servlet.*;

import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.UUID;

/**
 * @author Christian Junk
 *
 */
public class FusionTablesSample {

  /**
   * Be sure to specify the name of your application. If the application name is {@code null} or
   * blank, the application will log a warning. Suggested format is "MyCompany-ProductName/1.0".
   */
  private static final String APPLICATION_NAME = "My Project";

  /** Directory to store user credentials. */
  private static final java.io.File DATA_STORE_DIR =
      new java.io.File(System.getProperty("user.home"), "fusion_tables_sample");
  
  /**
   * Global instance of the {@link DataStoreFactory}. The best practice is to make it a single
   * globally shared instance across your application.
   */
  private static FileDataStoreFactory dataStoreFactory;

  /** Global instance of the HTTP transport. */
  private static HttpTransport httpTransport;

  /** Global instance of the JSON factory. */
  private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

  private static Fusiontables fusiontables;
  private static String TableName = "Project1Data";
  private static String column1 = "datetime";
  private static String column2 = "MAC";
  private static String column3 = "PBID";
  /** Authorizes the installed application to access user's protected data. */
  private static Credential authorize() throws Exception {
    // load client secrets
    GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(
        JSON_FACTORY, new InputStreamReader(
            FusionTablesSample.class.getResourceAsStream("/client_secrets.json")));
    if (clientSecrets.getDetails().getClientId().startsWith("Enter")
        || clientSecrets.getDetails().getClientSecret().startsWith("Enter ")) {
      System.out.println(
          "Enter Client ID and Secret from https://code.google.com/apis/console/?api=fusiontables "
          + "into fusiontables-cmdline-sample/src/main/resources/client_secrets.json");
      System.exit(1);
    }
    // set up authorization code flow
    GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
        httpTransport, JSON_FACTORY, clientSecrets,
        Collections.singleton(FusiontablesScopes.FUSIONTABLES)).setDataStoreFactory(
        dataStoreFactory).build();
    // authorize
    return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
  }

  public static void main(String[] args) {

    try {
      /*
      Class.forName("org.sqlite.JDBC");
      Connection conn = DriverManager.getConnection("jdbc:sqlite:test.db");
      Statement stat = conn.createStatement();
      stat.executeUpdate("drop table if exists "+TableName+";");
      stat.executeUpdate("create table people ("+column1+","+column2+","+column3+");");
      PreparedStatement prep = conn.prepareStatement(
              "insert into people values (?, ?, ?);");

      DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
      Date date = new Date();
      System.out.println(dateFormat.format(date)); //2014/08/06 15:59:48

      prep.setString(1, dateFormat.format(date));
      prep.setString(2, "politics");
      prep.setString(3, "1234");
      prep.addBatch();
      date = new Date();
      System.out.println(dateFormat.format(date));
      prep.setString(1, dateFormat.format(date));
      prep.setString(2, "computers");
      prep.setString(3, "2234");
      prep.addBatch();
      date = new Date();
      System.out.println(dateFormat.format(date));
      prep.setString(1, dateFormat.format(date));
      prep.setString(2, "smartypants");
      prep.setString(3, "3234");
      prep.addBatch();

      conn.setAutoCommit(false);
      prep.executeBatch();
      conn.setAutoCommit(true);

      ResultSet rs = stat.executeQuery("select * from people;");
      while (rs.next()) {
        System.out.println("column1 = " + rs.getString(column1));
        System.out.println("column2 = " + rs.getString(column2));
        System.out.println("column3 = " + rs.getString(column3));
      }
      rs.close();
      conn.close();


      Class.forName("org.sqlite.JDBC");
      Connection conn = DriverManager.getConnection("jdbc:sqlite:test.db");
      Statement stat = conn.createStatement();
      stat.executeUpdate("drop table if exists people;");
      stat.executeUpdate("create table people (name, occupation);");
      PreparedStatement prep = conn.prepareStatement(
              "insert into people values (?, ?);");

      prep.setString(1, "Gandhi");
      prep.setString(2, "politics");
      prep.addBatch();
      prep.setString(1, "Turing");
      prep.setString(2, "computers");
      prep.addBatch();
      prep.setString(1, "Wittgenstein");
      prep.setString(2, "smartypants");
      prep.addBatch();

      conn.setAutoCommit(false);
      prep.executeBatch();
      conn.setAutoCommit(true);

      ResultSet rs = stat.executeQuery("select * from people;");
      while (rs.next()) {
        System.out.println("name = " + rs.getString("name"));
        System.out.println("job = " + rs.getString("occupation"));
      }
      rs.close();
      conn.close();
*/
      httpTransport = GoogleNetHttpTransport.newTrustedTransport();
      dataStoreFactory = new FileDataStoreFactory(DATA_STORE_DIR);
      // authorization
      Credential credential = authorize();
      // set up global FusionTables instance
      fusiontables = new Fusiontables.Builder(
          httpTransport, JSON_FACTORY, credential).setApplicationName(APPLICATION_NAME).build();
      // run commands
      listTables();

      String tableId = getTableId(TableName);
      //deleteTable(tableId);
      if ( getTableId(TableName) == null) {
        tableId = createTable();
      }
      //deleteTable(tableId);
      Class.forName("org.sqlite.JDBC");
      Connection conn = DriverManager.getConnection("jdbc:sqlite:test.db");
      Statement stat = conn.createStatement();
/*
      stat.executeUpdate("drop table if exists "+TableName+";");
      System.out.println("123");
      stat.executeUpdate("create table "+TableName+" ("+column1+","+column2+","+column3+");");
      PreparedStatement prep = conn.prepareStatement(
              "insert into "+TableName+" values (?, ?, ?);");
      System.out.println("123");
      DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
      Date date = new Date();
      System.out.println(dateFormat.format(date)); //2014/08/06 15:59:48

      prep.setString(1, dateFormat.format(date).toString());
      prep.setString(2, "politics");
      prep.setString(3, "1234");
      prep.addBatch();
      date = new Date();
      System.out.println(dateFormat.format(date));
      prep.setString(1, dateFormat.format(date).toString());
      prep.setString(2, "computers");
      prep.setString(3, "2234");
      prep.addBatch();
      date = new Date();
      System.out.println(dateFormat.format(date));
      prep.setString(1, dateFormat.format(date).toString());
      prep.setString(2, "smartypants");
      prep.setString(3, "3234");
      prep.addBatch();

      conn.setAutoCommit(false);
      prep.executeBatch();
      conn.setAutoCommit(true);
*/
      ResultSet rs = stat.executeQuery("select * from "+TableName+";");
      while (rs.next()) {
        insertData(tableId,rs.getString(column1),rs.getString(column2),rs.getString(column3));
      }
      rs.close();
      conn.close();

      //deleteTable(tableId);
      // success!
      return;
    } catch (IOException e) {
      System.err.println(e.getMessage());
    } catch (Throwable t) {
      t.printStackTrace();
    }
    System.exit(1);
  }

  /**
   * @param tableId
   * @throws IOException
   */
  private static void showRows(String tableId) throws IOException {
    View.header("Showing Rows From Table");

    Sql sql = fusiontables.query().sql("SELECT "+column1+","+column2+","+column3+" FROM " + tableId);

    try {
      sql.execute();
    } catch (IllegalArgumentException e) {
      // For google-api-services-fusiontables-v1-rev1-1.7.2-beta this exception will always
      // been thrown.
      // Please see issue 545: JSON response could not be deserialized to Sqlresponse.class
      // http://code.google.com/p/google-api-java-client/issues/detail?id=545
    }
  }

  /** List tables for the authenticated user. */
  private static void listTables() throws IOException {
    View.header("Listing My Tables");
    // Fetch the table list
    Fusiontables.Table.List listTables = fusiontables.table().list();
    TableList tablelist = listTables.execute();
    if (tablelist.getItems() == null || tablelist.getItems().isEmpty()) {
      System.out.println("No tables found!");
      return;
    }
    for (Table table : tablelist.getItems()) {
      View.show(table);
      View.separator();
    }
  }

  /** Create a table for the authenticated user. */
  private static String createTable() throws IOException {
    View.header("Create Sample Table");

    // Create a new table
    Table table = new Table();
    table.setName(TableName);
    table.setIsExportable(false);
    table.setDescription(TableName);

    // Set columns for new table
    table.setColumns(Arrays.asList(
        new Column().setName(column1).setType("STRING"),
        new Column().setName(column2).setType("STRING"),
        new Column().setName(column3).setType("STRING")));

    // Adds a new column to the table.
    Fusiontables.Table.Insert t = fusiontables.table().insert(table);
    Table r = t.execute();

    View.show(r);

    return r.getTableId();
  }

  /** Inserts a row in the newly created table for the authenticated user. */
  private static void insertData(String tableId,String item1,String item2,String item3) throws IOException {
    Sql sql = fusiontables.query().sql("INSERT INTO " + tableId + " ("+column1+","+column2+","+column3+") "
        + "VALUES ('" + item1+ "', '" +item2 +"', '" +item3+"')");

    try {
      sql.execute();
    } catch (IllegalArgumentException e) {
      // For google-api-services-fusiontables-v1-rev1-1.7.2-beta this exception will always
      // been thrown.
      // Please see issue 545: JSON response could not be deserialized to Sqlresponse.class
      // http://code.google.com/p/google-api-java-client/issues/detail?id=545
    }
  }
  private static void updateData(String tableId) throws IOException {
    Sql sql = fusiontables.query().sql("UPDATE " + tableId + " SET  Text = 1 WHERE ROWID = '1'");
    try {
      sql.execute();
    } catch (IllegalArgumentException e) {
      // For google-api-services-fusiontables-v1-rev1-1.7.2-beta this exception will always
      // been thrown.
      // Please see issue 545: JSON response could not be deserialized to Sqlresponse.class
      // http://code.google.com/p/google-api-java-client/issues/detail?id=545
    }
  }
  /** Deletes a table for the authenticated user. */
  private static void deleteTable(String tableId) throws IOException {
    View.header("Delete Sample Table");
    // Deletes a table
    Delete delete = fusiontables.table().delete(tableId);
    delete.execute();
  }

  private static String getTableId(String tableName) throws IOException {
    View.header("Getting TableID for " + tableName);

    // Fetch the table list
    Fusiontables.Table.List listTables = fusiontables.table().list();
    TableList tablelist = listTables.execute();

    if (tablelist.getItems() == null || tablelist.getItems().isEmpty()) {
      System.out.println("No tables found!");
      return null;
    }

    String tid = null;
    for (Table table : tablelist.getItems()) {
      if (table.getName().equals(tableName))
        tid = table.getTableId();
    }
    System.out.println(tableName + " - tableId: " + tid);
    return tid;
  }
}
