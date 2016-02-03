package com.jorge.boats.xkcd.mapper;

import android.support.annotation.Nullable;
import com.jorge.boats.xkcd.di.PerActivity;
import com.jorge.boats.xkcd.domain.entity.DomainStripe;
import com.jorge.boats.xkcd.entity.PresentationStripe;
import javax.inject.Inject;
import org.jetbrains.annotations.Contract;

@PerActivity public class PresentationEntityMapper {

  /**
   * Make explicit that this constructor can be used to inject the class.
   */
  @Inject public PresentationEntityMapper() {
  }

  @Contract("null -> null")
  public PresentationStripe transform(final @Nullable DomainStripe domainStripe) {
    PresentationStripe ret = null;

    if (domainStripe != null) {
      ret = new PresentationStripe();
      ret.setAlt(domainStripe.getAlt());
      ret.setDay(domainStripe.getDay());
      ret.setImg(domainStripe.getImg());
      ret.setLink(domainStripe.getLink());
      ret.setMonth(domainStripe.getMonth());
      ret.setNews(domainStripe.getNews());
      ret.setNum(domainStripe.getNum());
      ret.setSafe_title(domainStripe.getSafe_title());
      ret.setTitle(domainStripe.getTitle());
      ret.setTranscript(domainStripe.getTranscript());
      ret.setYear(domainStripe.getYear());
    }

    return ret;
  }

  @Contract("null -> null")
  public DomainStripe transform(final @Nullable PresentationStripe presentationStripe) {
    DomainStripe ret = null;

    if (presentationStripe != null) {
      ret = new DomainStripe();
      ret.setAlt(presentationStripe.getAlt());
      ret.setDay(presentationStripe.getDay());
      ret.setImg(presentationStripe.getImg());
      ret.setLink(presentationStripe.getLink());
      ret.setMonth(presentationStripe.getMonth());
      ret.setNews(presentationStripe.getNews());
      ret.setNum(presentationStripe.getNum());
      ret.setSafe_title(presentationStripe.getSafe_title());
      ret.setTitle(presentationStripe.getTitle());
      ret.setTranscript(presentationStripe.getTranscript());
      ret.setYear(presentationStripe.getYear());
    }

    return ret;
  }
}
