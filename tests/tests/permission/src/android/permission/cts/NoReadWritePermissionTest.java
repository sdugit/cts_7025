/*
 * Copyright (C) 2009 The Android Open Source Project
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

package android.permission.cts;

import dalvik.annotation.ToBeFixed;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.provider.Contacts;
import android.provider.Settings;
import android.test.AndroidTestCase;
/**
 * Verify the location access without specific permissions.
 */
public class NoReadWritePermissionTest extends AndroidTestCase {
    private ContentResolver mContentResolver;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mContentResolver = getContext().getContentResolver();
    }

    private void queryProvider(Uri uri) {
        try {
            mContentResolver.query(uri, null, null, null, null);
            fail("read from provider did not throw SecurityException as expected.");
        } catch (SecurityException e) {
            // expected
        }
    }

    private void insertProvider(Uri uri, ContentValues values) {
        try {
            mContentResolver.insert(uri, values);
            fail("Write into provider did not throw SecurityException as expected.");
        } catch (SecurityException e) {
            // expected
        }
    }

    /**
     * Verify that read and write to calendar requires permissions.
     * <p>Requires Permission:
     *   {@link android.Manifest.permission#READ_CALENDAR}
     *   {@link android.Manifest.permission#WRITE_CALENDAR}
     */
    public void testReadWriteCalendar() {
        Uri uri = Uri.parse("content://calendar/events/");

        // read permission
        queryProvider(uri);

            // write permission
        ContentValues values = new ContentValues();
        values.put("eventTimezone", "EST");
        values.put("calendar_id", 1);
        values.put("title", "Party over thurr");
        values.put("allDay", 0);
        values.put("transparency", 0);
        values.put("visibility", 0);
        values.put("hasAlarm", 0);

        insertProvider(uri, values);
    }

    /**
     * Verify that read and write to contact requires permissions.
     * <p>Requires Permission:
     *   {@link android.Manifest.permission#READ_CONTACTS}
     *   {@link android.Manifest.permission#WRITE_CONTACTS}
     */
    public void testReadWriteContacts() {
        Uri uri = Contacts.People.CONTENT_URI;

        // read permission
        queryProvider(uri);


        // write permission
        ContentValues values = new ContentValues();
        values.put(Contacts.People.NAME, "New Contact");

        insertProvider(uri, values);
    }

    /**
     * Verify that read and write to sms requires permissions.
     * <p>Requires Permission:
     *   {@link android.Manifest.permission#READ_SMS}
     *   {@link android.Manifest.permission#WRITE_SMS}
     */
    public void testReadWriteSms() {
        Uri uri = Uri.parse("content://sms/inbox");

        // read permission
        queryProvider(uri);

        // write permission
        ContentValues values = new ContentValues();
        values.put("person", "google");

        insertProvider(uri, values);
    }

    /**
     * Verify that read and write to sync settings requires permissions.
     * <p>Requires Permission:
     *   {@link android.Manifest.permission#READ_SYNC_SETTINGS}
     *   {@link android.Manifest.permission#WRITE_SYNC_SETTINGS}
     */
    public void testReadWriteSyncSettings() {
        Uri uri = Uri.parse("content://sync/settings");

        // read permission
        queryProvider(uri);

        // write permission
        ContentValues values = new ContentValues();
        values.put("name", "vendor");
        values.put("value", "google");

        insertProvider(uri, values);
    }

    /**
     * Verify that read to sync stats requires permissions.
     * <p>Requires Permission:
     *   {@link android.Manifest.permission#READ_SYNC_STATS}
     */
    public void testReadSyncStats() {
        Uri uri = Uri.parse("content://sync/stats");

        // read permission
        queryProvider(uri);
    }

    /**
     * Verify that write to apn settings requires permissions.
     * <p>Requires Permission:
     *   {@link android.Manifest.permission#WRITE_APN_SETTINGS}
     */
    public void testWriteApnSettings() {
        Uri uri = Uri.parse("content://telephony/carriers");

        // write permission
        ContentValues values = new ContentValues();
        values.put("apn", "google");

        insertProvider(uri, values);
    }

    /**
     * Verify that write to settings requires permissions.
     * <p>Requires Permission:
     *   {@link android.Manifest.permission#WRITE_SETTINGS}
     */
    public void testWriteSettings() {
        Uri uri = Uri.parse("content://" + Settings.AUTHORITY + "/bookmarks");

        // write permission
        ContentValues values = new ContentValues();
        values.put("title", "google");

        insertProvider(uri, values);
    }

    /**
     * Verify that read and write of subsribedfeeds requires permissions.
     * <p>Requires Permission:
     *   {@link android.Manifest.permission#SUBSCRIBED_FEEDS_READ}
     *   {@link android.Manifest.permission#SUBSCRIBED_FEEDS_WRITE}
     */
    @ToBeFixed(bug = "", explanation = "access subscribedfeeds data through ContentResolver"
            + " should check permission of android.Manifest.permission#SUBSCRIBED_FEEDS.")
    public void testReadSubscribedFeeds() {

    }
}