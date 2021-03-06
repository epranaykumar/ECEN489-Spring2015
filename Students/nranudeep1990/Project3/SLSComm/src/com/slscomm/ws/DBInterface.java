package com.slscomm.ws;

import java.util.ArrayList;

import org.apache.log4j.Logger;

/**
 * Created by Ian on 4/25/2015.
 */
public class DBInterface {
	
	final static Logger logger = Logger.getLogger(DBInterface.class);

    public static ArrayList<String> selectFriends(String username) {
        DBHelper dbHelper = new DBHelper();
        ArrayList<String> friends = new ArrayList<String>();

        dbHelper.openDB();
        friends = dbHelper.getFriends(dbHelper.getUserIDByUserName(username));
        dbHelper.closeDB();

        return friends;
    }


    /*public static ArrayList<UserWithLocations> getRecentLocs(String username, ArrayList<String> friends) {
        DBHelper dbHelper = new DBHelper();
        ArrayList<UserWithLocations> friendsWithLocs = new ArrayList<UserWithLocations>();
        ArrayList<CheckIn> checkIns;


        dbHelper.openDB();
        for (String f : friends) {
            checkIns = dbHelper.getCheckInByUser(dbHelper.getUserIDByUserName(f));

            ArrayList<String> locs = new ArrayList<String>();

            for (int i = 0; i < 3; i++)
                locs.add(checkIns.get(i).getLocationName());

            friendsWithLocs.add(new UserWithLocations(f, locs));
        }

        return friendsWithLocs;
    }*/

    //Returns 3 most recent checkins of given friends
    public static ArrayList<UserWithCheckIns> getRecentLocs(String username, ArrayList<String> friends) {
        DBHelper dbHelper = new DBHelper();
        ArrayList<UserWithCheckIns> friendsWithCheckIns = new ArrayList<UserWithCheckIns>();

        dbHelper.openDB();
        for (String f : friends) {
            ArrayList<CheckIn> checkIns =  new ArrayList<CheckIn>(dbHelper.getCheckInByUser(dbHelper.getUserIDByUserName(f)));

            friendsWithCheckIns.add(new UserWithCheckIns(f, checkIns));
        }
        dbHelper.closeDB();

        return friendsWithCheckIns;
    }

    //Returns 3 most recent checkins of given locations
    public static ArrayList<LocWithCheckIns> getRecentFriends(String username, ArrayList<String> locs) {
        DBHelper dbHelper = new DBHelper();
        ArrayList<LocWithCheckIns> locsWithCheckIns = new ArrayList<LocWithCheckIns>();

        dbHelper.openDB();
        for (String l : locs) {
            ArrayList<CheckIn> checkIns =  new ArrayList<CheckIn>(dbHelper.getFriendsCheckInByLocation(dbHelper.getLocationIDByName(l), dbHelper.getUserIDByUserName(username)));

            locsWithCheckIns.add(new LocWithCheckIns(l, checkIns));
        }
        dbHelper.closeDB();

        return locsWithCheckIns;
    }

    //Added by Ian 4/28/15
    public static void addCheckIn(CheckIn ci) {
        DBHelper dbHelper = new DBHelper();

        dbHelper.openDB();
        dbHelper.checkIn(dbHelper.getUserIDByUserName(ci.getUserName()), dbHelper.getLocationIDByName(ci.getLocationName()), ci.getTimestamp(), ci.getMethod());
        dbHelper.closeDB();
    }

    //Given a username and a sessionID from that user, this function checks if the given sessionID is valid
    public static boolean checkForValidSessionID(String username, String sessionID) {
        String storedSessID;

        //call DBInterface.getSessionID to retrieve stored token for client
        storedSessID = DBInterface.getSessionID(username);

        if (storedSessID.equals(sessionID))
            return true;
        else
            return false;
    }
    //End added by Ian

    //Added by Trevor 4/27/15
    public static String getSessionID(String username) {
        DBHelper dbHelper = new DBHelper();
        String sessionID = null;

        dbHelper.openDB();
        sessionID = dbHelper.getSessionIDByUserName(username);
        dbHelper.closeDB();

        return sessionID;
    }

    public static String getPassword(String username) {
        DBHelper dbHelper = new DBHelper();
        String pass = null;

        dbHelper.openDB();
        pass = dbHelper.getPassByUserName(username);
        dbHelper.closeDB();

        return pass;
    }

    public static void addSessionID(String username, String ID) {
        DBHelper dbHelper = new DBHelper();
        dbHelper.openDB();
        dbHelper.addSessionID(username, ID);
        dbHelper.closeDB();
    }

    public static void resetSessionID(String username) {
        DBHelper dbHelper = new DBHelper();
        dbHelper.openDB();
        dbHelper.resetSessionID(username);
        dbHelper.closeDB();
    }

    //End added by Trevor

    // Updated by Nagaraj on 4/28/2015
    public static Boolean AddFriends(UserWithFriends userWithFriends){
        Boolean outcome = false;  // default
        DBHelper dbHelper = new DBHelper();
        dbHelper.openDB();
        Integer userID = dbHelper.getUserIDByUserName(userWithFriends.username);
        for (String friendName : userWithFriends.friends){
            Integer friendUserID = dbHelper.getUserIDByName(friendName);
            dbHelper.addFriend(userID,friendUserID);
        }
        outcome = true;
        dbHelper.closeDB();

        return outcome;
    }

    //End add by Nagaraj

}
