
package com.jorge.boats.data.model;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Generated("org.jsonschema2pojo")
public class DataStripe {

    @SerializedName("month")
    @Expose
    private String month;
    @SerializedName("num")
    @Expose
    private long num;
    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("year")
    @Expose
    private String year;
    @SerializedName("news")
    @Expose
    private String news;
    @SerializedName("safe_title")
    @Expose
    private String safe_title;
    @SerializedName("transcript")
    @Expose
    private String transcript;
    @SerializedName("alt")
    @Expose
    private String alt;
    @SerializedName("img")
    @Expose
    private String img;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("day")
    @Expose
    private String day;
    protected final static Object NOT_FOUND_VALUE = new Object();

    /**
     * 
     * @return
     *     The month
     */
    public String getMonth() {
        return month;
    }

    /**
     * 
     * @param month
     *     The month
     */
    public void setMonth(String month) {
        this.month = month;
    }

    /**
     * 
     * @return
     *     The num
     */
    public long getNum() {
        return num;
    }

    /**
     * 
     * @param num
     *     The num
     */
    public void setNum(long num) {
        this.num = num;
    }

    /**
     * 
     * @return
     *     The link
     */
    public String getLink() {
        return link;
    }

    /**
     * 
     * @param link
     *     The link
     */
    public void setLink(String link) {
        this.link = link;
    }

    /**
     * 
     * @return
     *     The year
     */
    public String getYear() {
        return year;
    }

    /**
     * 
     * @param year
     *     The year
     */
    public void setYear(String year) {
        this.year = year;
    }

    /**
     * 
     * @return
     *     The news
     */
    public String getNews() {
        return news;
    }

    /**
     * 
     * @param news
     *     The news
     */
    public void setNews(String news) {
        this.news = news;
    }

    /**
     * 
     * @return
     *     The safe_title
     */
    public String getSafe_title() {
        return safe_title;
    }

    /**
     * 
     * @param safe_title
     *     The safe_title
     */
    public void setSafe_title(String safe_title) {
        this.safe_title = safe_title;
    }

    /**
     * 
     * @return
     *     The transcript
     */
    public String getTranscript() {
        return transcript;
    }

    /**
     * 
     * @param transcript
     *     The transcript
     */
    public void setTranscript(String transcript) {
        this.transcript = transcript;
    }

    /**
     * 
     * @return
     *     The alt
     */
    public String getAlt() {
        return alt;
    }

    /**
     * 
     * @param alt
     *     The alt
     */
    public void setAlt(String alt) {
        this.alt = alt;
    }

    /**
     * 
     * @return
     *     The img
     */
    public String getImg() {
        return img;
    }

    /**
     * 
     * @param img
     *     The img
     */
    public void setImg(String img) {
        this.img = img;
    }

    /**
     * 
     * @return
     *     The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * 
     * @param title
     *     The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 
     * @return
     *     The day
     */
    public String getDay() {
        return day;
    }

    /**
     * 
     * @param day
     *     The day
     */
    public void setDay(String day) {
        this.day = day;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @SuppressWarnings({
        "unchecked"
    })
    protected boolean declaredProperty(String name, Object value) {
        if ("month".equals(name)) {
            if (value instanceof String) {
                setMonth(((String) value));
            } else {
                throw new IllegalArgumentException(("property \"month\" is of type \"java.lang.String\", but got "+ value.getClass().toString()));
            }
            return true;
        } else {
            if ("num".equals(name)) {
                if (value instanceof Long) {
                    setNum(((Long) value));
                } else {
                    throw new IllegalArgumentException(("property \"num\" is of type \"long\", but got "+ value.getClass().toString()));
                }
                return true;
            } else {
                if ("link".equals(name)) {
                    if (value instanceof String) {
                        setLink(((String) value));
                    } else {
                        throw new IllegalArgumentException(("property \"link\" is of type \"java.lang.String\", but got "+ value.getClass().toString()));
                    }
                    return true;
                } else {
                    if ("year".equals(name)) {
                        if (value instanceof String) {
                            setYear(((String) value));
                        } else {
                            throw new IllegalArgumentException(("property \"year\" is of type \"java.lang.String\", but got "+ value.getClass().toString()));
                        }
                        return true;
                    } else {
                        if ("news".equals(name)) {
                            if (value instanceof String) {
                                setNews(((String) value));
                            } else {
                                throw new IllegalArgumentException(("property \"news\" is of type \"java.lang.String\", but got "+ value.getClass().toString()));
                            }
                            return true;
                        } else {
                            if ("safe_title".equals(name)) {
                                if (value instanceof String) {
                                    setSafe_title(((String) value));
                                } else {
                                    throw new IllegalArgumentException(("property \"safe_title\" is of type \"java.lang.String\", but got "+ value.getClass().toString()));
                                }
                                return true;
                            } else {
                                if ("transcript".equals(name)) {
                                    if (value instanceof String) {
                                        setTranscript(((String) value));
                                    } else {
                                        throw new IllegalArgumentException(("property \"transcript\" is of type \"java.lang.String\", but got "+ value.getClass().toString()));
                                    }
                                    return true;
                                } else {
                                    if ("alt".equals(name)) {
                                        if (value instanceof String) {
                                            setAlt(((String) value));
                                        } else {
                                            throw new IllegalArgumentException(("property \"alt\" is of type \"java.lang.String\", but got "+ value.getClass().toString()));
                                        }
                                        return true;
                                    } else {
                                        if ("img".equals(name)) {
                                            if (value instanceof String) {
                                                setImg(((String) value));
                                            } else {
                                                throw new IllegalArgumentException(("property \"img\" is of type \"java.lang.String\", but got "+ value.getClass().toString()));
                                            }
                                            return true;
                                        } else {
                                            if ("title".equals(name)) {
                                                if (value instanceof String) {
                                                    setTitle(((String) value));
                                                } else {
                                                    throw new IllegalArgumentException(("property \"title\" is of type \"java.lang.String\", but got "+ value.getClass().toString()));
                                                }
                                                return true;
                                            } else {
                                                if ("day".equals(name)) {
                                                    if (value instanceof String) {
                                                        setDay(((String) value));
                                                    } else {
                                                        throw new IllegalArgumentException(("property \"day\" is of type \"java.lang.String\", but got "+ value.getClass().toString()));
                                                    }
                                                    return true;
                                                } else {
                                                    return false;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @SuppressWarnings({
        "unchecked"
    })
    protected Object declaredPropertyOrNotFound(String name, Object notFoundValue) {
        if ("month".equals(name)) {
            return getMonth();
        } else {
            if ("num".equals(name)) {
                return getNum();
            } else {
                if ("link".equals(name)) {
                    return getLink();
                } else {
                    if ("year".equals(name)) {
                        return getYear();
                    } else {
                        if ("news".equals(name)) {
                            return getNews();
                        } else {
                            if ("safe_title".equals(name)) {
                                return getSafe_title();
                            } else {
                                if ("transcript".equals(name)) {
                                    return getTranscript();
                                } else {
                                    if ("alt".equals(name)) {
                                        return getAlt();
                                    } else {
                                        if ("img".equals(name)) {
                                            return getImg();
                                        } else {
                                            if ("title".equals(name)) {
                                                return getTitle();
                                            } else {
                                                if ("day".equals(name)) {
                                                    return getDay();
                                                } else {
                                                    return notFoundValue;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @SuppressWarnings({
        "unchecked"
    })
    public<T >T get(String name) {
        Object value = declaredPropertyOrNotFound(name, DataStripe.NOT_FOUND_VALUE);
        if (DataStripe.NOT_FOUND_VALUE!= value) {
            return ((T) value);
        } else {
            throw new IllegalArgumentException((("property \""+ name)+"\" is not defined"));
        }
    }

    @SuppressWarnings({
        "unchecked"
    })
    public void set(String name, Object value) {
        if (!declaredProperty(name, value)) {
            throw new IllegalArgumentException((("property \""+ name)+"\" is not defined"));
        }
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(month).append(num).append(link).append(year).append(news).append(safe_title).append(transcript).append(alt).append(img).append(title).append(day).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof DataStripe) == false) {
            return false;
        }
        DataStripe rhs = ((DataStripe) other);
        return new EqualsBuilder().append(month, rhs.month).append(num, rhs.num).append(link, rhs.link).append(year, rhs.year).append(news, rhs.news).append(safe_title, rhs.safe_title).append(transcript, rhs.transcript).append(alt, rhs.alt).append(img, rhs.img).append(title, rhs.title).append(day, rhs.day).isEquals();
    }

}
