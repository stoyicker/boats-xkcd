package com.jorge.boats.data.entity;

import android.support.annotation.Nullable;
import com.jorge.boats.data.db.DatabaseStripe;
import com.jorge.boats.data.model.DataStripe;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.jetbrains.annotations.Contract;

@Singleton public class DatabaseEntityMapper {

  /**
   * Make explicit that this constructor can be used to inject the class.
   */
  @Inject public DatabaseEntityMapper() {
  }

  @Contract("null -> null")
  public DataStripe transform(final @Nullable DatabaseStripe databaseStripe) {
    DataStripe ret = null;

    if (databaseStripe != null) {
      ret = new DataStripe();
      ret.setAlt(databaseStripe.getAlt());
      ret.setDay(databaseStripe.getDay());
      ret.setImg(databaseStripe.getImg());
      ret.setLink(databaseStripe.getLink());
      ret.setMonth(databaseStripe.getMonth());
      ret.setNews(databaseStripe.getNews());
      ret.setNum(databaseStripe.getNum());
      ret.setSafe_title(databaseStripe.getSafe_title());
      ret.setTitle(databaseStripe.getTitle());
      ret.setTranscript(databaseStripe.getTranscript());
      ret.setYear(databaseStripe.getYear());
    }

    return ret;
  }

  @Contract("null -> null") public DatabaseStripe transform(final @Nullable DataStripe dataStripe) {
    DatabaseStripe ret = null;

    if (dataStripe != null) {
      ret = new DatabaseStripe();
      ret.setAlt(dataStripe.getAlt());
      ret.setDay(dataStripe.getDay());
      ret.setImg(dataStripe.getImg());
      ret.setLink(dataStripe.getLink());
      ret.setMonth(dataStripe.getMonth());
      ret.setNews(dataStripe.getNews());
      ret.setNum(dataStripe.getNum());
      ret.setSafe_title(dataStripe.getSafe_title());
      ret.setTitle(dataStripe.getTitle());
      ret.setTranscript(dataStripe.getTranscript());
      ret.setYear(dataStripe.getYear());
    }

    return ret;
  }
}
