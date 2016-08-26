package com.jorge.boats.xkcd.data.db;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

@Table(database = XkcdDatabase.class)
public class DatabaseStripe extends BaseModel {

  public String getMonth() {
    return mMonth;
  }

  public void setMonth(String mMonth) {
    this.mMonth = mMonth;
  }

  public long getNum() {
    return mNum;
  }

  public void setNum(long mNum) {
    this.mNum = mNum;
  }

  public String getLink() {
    return mLink;
  }

  public void setLink(String mLink) {
    this.mLink = mLink;
  }

  public String getYear() {
    return mYear;
  }

  public void setYear(String mYear) {
    this.mYear = mYear;
  }

  public String getNews() {
    return mNews;
  }

  public void setNews(String mNews) {
    this.mNews = mNews;
  }

  public String getSafe_title() {
    return mSafe_title;
  }

  public void setSafe_title(String mSafe_title) {
    this.mSafe_title = mSafe_title;
  }

  public String getTranscript() {
    return mTranscript;
  }

  public void setTranscript(String mTranscript) {
    this.mTranscript = mTranscript;
  }

  public String getAlt() {
    return mAlt;
  }

  public void setAlt(String mAlt) {
    this.mAlt = mAlt;
  }

  public String getImg() {
    return mImg;
  }

  public void setImg(String mImg) {
    this.mImg = mImg;
  }

  public String getTitle() {
    return mTitle;
  }

  public void setTitle(String mTitle) {
    this.mTitle = mTitle;
  }

  public String getDay() {
    return mDay;
  }

  public void setDay(String mDay) {
    this.mDay = mDay;
  }

  @Column(name = "MONTH")
  String mMonth;

  @Column(name = "NUM")
  @PrimaryKey
  long mNum;

  @Column(name = "LINK")
  String mLink;

  @Column(name = "YEAR")
  String mYear;

  @Column(name = "NEWS")
  String mNews;

  @Column(name = "SAFE_TITLE")
  String mSafe_title;

  @Column(name = "TRANSCRIPT")
  String mTranscript;

  @Column(name = "ALT")
  String mAlt;

  @Column(name = "IMG")
  String mImg;

  @Column(name = "TITLE")
  String mTitle;

  @Column(name = "DAY")
  String mDay;
}
