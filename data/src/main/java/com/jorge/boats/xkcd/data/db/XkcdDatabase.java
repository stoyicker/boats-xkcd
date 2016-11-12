package com.jorge.boats.xkcd.data.db;

import com.raizlabs.android.dbflow.annotation.Database;

@Database(name = XkcdDatabase.NAME, version = XkcdDatabase.VERSION)
public class XkcdDatabase {

  static final String NAME = "Xkcd";

  static final int VERSION = 1;
}
