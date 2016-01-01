package com.jorge.boats.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class PresentationStripe {

  private String month;
  private long num;
  private String link;
  private String year;
  private String news;
  private String safe_title;
  private String transcript;
  private String alt;
  private String img;
  private String title;
  private String day;

  /**
   * @return The month
   */
  public String getMonth() {
    return month;
  }

  /**
   * @param month The month
   */
  public void setMonth(String month) {
    this.month = month;
  }

  /**
   * @return The num
   */
  public long getNum() {
    return num;
  }

  /**
   * @param num The num
   */
  public void setNum(long num) {
    this.num = num;
  }

  /**
   * @return The link
   */
  public String getLink() {
    return link;
  }

  /**
   * @param link The link
   */
  public void setLink(String link) {
    this.link = link;
  }

  /**
   * @return The year
   */
  public String getYear() {
    return year;
  }

  /**
   * @param year The year
   */
  public void setYear(String year) {
    this.year = year;
  }

  /**
   * @return The news
   */
  public String getNews() {
    return news;
  }

  /**
   * @param news The news
   */
  public void setNews(String news) {
    this.news = news;
  }

  /**
   * @return The safe_title
   */
  public String getSafe_title() {
    return safe_title;
  }

  /**
   * @param safe_title The safe_title
   */
  public void setSafe_title(String safe_title) {
    this.safe_title = safe_title;
  }

  /**
   * @return The transcript
   */
  public String getTranscript() {
    return transcript;
  }

  /**
   * @param transcript The transcript
   */
  public void setTranscript(String transcript) {
    this.transcript = transcript;
  }

  /**
   * @return The alt
   */
  public String getAlt() {
    return alt;
  }

  /**
   * @param alt The alt
   */
  public void setAlt(String alt) {
    this.alt = alt;
  }

  /**
   * @return The img
   */
  public String getImg() {
    return img;
  }

  /**
   * @param img The img
   */
  public void setImg(String img) {
    this.img = img;
  }

  /**
   * @return The title
   */
  public String getTitle() {
    return title;
  }

  /**
   * @param title The title
   */
  public void setTitle(String title) {
    this.title = title;
  }

  /**
   * @return The day
   */
  public String getDay() {
    return day;
  }

  /**
   * @param day The day
   */
  public void setDay(String day) {
    this.day = day;
  }

  @Override public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

  @Override public int hashCode() {
    return new HashCodeBuilder().append(month)
        .append(num)
        .append(link)
        .append(year)
        .append(news)
        .append(safe_title)
        .append(transcript)
        .append(alt)
        .append(img)
        .append(title)
        .append(day)
        .toHashCode();
  }

  @Override public boolean equals(Object other) {
    if (other == this) {
      return true;
    }
    if (!(other instanceof PresentationStripe)) {
      return false;
    }
    PresentationStripe rhs = ((PresentationStripe) other);
    return new EqualsBuilder().append(month, rhs.month)
        .append(num, rhs.num)
        .append(link, rhs.link)
        .append(year, rhs.year)
        .append(news, rhs.news)
        .append(safe_title, rhs.safe_title)
        .append(transcript, rhs.transcript)
        .append(alt, rhs.alt)
        .append(img, rhs.img)
        .append(title, rhs.title)
        .append(day, rhs.day)
        .isEquals();
  }
}
