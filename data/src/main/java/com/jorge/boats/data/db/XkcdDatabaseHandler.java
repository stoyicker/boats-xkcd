package com.jorge.boats.data.db;

import android.content.ContentValues;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.raizlabs.android.dbflow.sql.language.Insert;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.ModelAdapter;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.jetbrains.annotations.Contract;

@Singleton public class XkcdDatabaseHandler {

  @Inject public XkcdDatabaseHandler() {
  }

  public void insertStripe(final @NonNull DatabaseStripe databaseStripe) {
    final Insert<DatabaseStripe> statement = SQLite.insert(DatabaseStripe.class).orReplace();

    final ContentValues values = new ContentValues();

    @SuppressWarnings("unchecked") final ModelAdapter<DatabaseStripe> adapter = (ModelAdapter<DatabaseStripe>) databaseStripe.getModelAdapter();

    adapter.bindToContentValues(values, databaseStripe);

    statement.columnValues(values).queryClose();
  }

  @Nullable @Contract(pure = true)
  public DatabaseStripe queryForStripeWithNum(final long stripeNum) {
    return SQLite.select()
        .from(DatabaseStripe.class)
        .where(DatabaseStripe_Table.NUM.is(stripeNum))
        .querySingle();
  }
}
