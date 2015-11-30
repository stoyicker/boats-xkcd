package com.jorge.boats.data.db;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

@Table(databaseName = StubDatabase.NAME) public class StubTable extends BaseModel {

  @Column @PrimaryKey(autoincrement = true) long id;
}
