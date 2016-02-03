package com.jorge.boats.xkcd.data.mapper;

import android.support.annotation.Nullable;
import com.jorge.boats.xkcd.data.model.DataStripe;
import com.jorge.boats.xkcd.domain.entity.DomainStripe;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.jetbrains.annotations.Contract;

@Singleton public class DomainEntityMapper {

  /**
   * Make explicit that this constructor can be used to inject the class.
   */
  @Inject public DomainEntityMapper() {
  }

  @Contract("null -> null") public DomainStripe transform(final @Nullable DataStripe dataStripe) {
    DomainStripe ret = null;

    if (dataStripe != null) {
      ret = new DomainStripe();
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
