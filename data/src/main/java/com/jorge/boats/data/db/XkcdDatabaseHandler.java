package com.jorge.boats.data.db;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.jetbrains.annotations.Contract;

@Singleton public class XkcdDatabaseHandler {

  @Inject public XkcdDatabaseHandler() {
  }

  public void insertStripe(final @NonNull DatabaseStripe databaseStripe) {
    databaseStripe.insert();
  }

  @Nullable @Contract(pure = true)
  public DatabaseStripe queryForStripeWithNum(final long stripeNum) {
    return SQLite.select()
        .from(DatabaseStripe.class)
        .where(DatabaseStripe_Table.NUM.is(stripeNum))
        .querySingle();
  }
}
