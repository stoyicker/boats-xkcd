package com.jorge.boats.xkcd.data.preference.list;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.preference.ListPreference;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.jorge.boats.xkcd.data.P;
import com.jorge.boats.xkcd.data.R;

import java.util.Arrays;
import java.util.Locale;

public class StyledListPreference extends ListPreference {

  public interface ThemeChangeListener {

    void onThemeChanged();
  }

  private ListAdapter mAdapter;
  private ThemeChangeListener mThemeChangeListener;
  private Dialog mDialog;

  public StyledListPreference(final @NonNull Context context) {
    super(context);
    init();
  }

  public StyledListPreference(final @NonNull Context context, final @Nullable AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public StyledListPreference(final @NonNull Context context, final @Nullable AttributeSet attrs,
      final int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  public StyledListPreference(final @NonNull Context context, final @Nullable AttributeSet attrs,
      final int defStyleAttr, final int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    init();
  }

  private void init() {
    mAdapter = new StyledListPreferenceAdapter(getEntries());
  }

  public Dialog buildDialog(final @NonNull AlertDialog.Builder builder, final boolean isDark,
      final @Nullable ThemeChangeListener themeChangeListener) {
    mThemeChangeListener = themeChangeListener;

    ((StyledListPreferenceAdapter) mAdapter).setDark(isDark);

    builder.setTitle(getTitle())
        .setNegativeButton(android.R.string.cancel, null)
        .setSingleChoiceItems(mAdapter, Arrays.asList(getEntries()).indexOf(getEntries()), null);

    return mDialog = builder.create();
  }

  private boolean isSelected(final @NonNull CharSequence item) {
    return getEntry().toString().contentEquals(item);
  }

  private class StyledListPreferenceAdapter extends BaseAdapter {

    private final CharSequence[] mItems;
    private boolean isDark;

    private StyledListPreferenceAdapter(final @NonNull CharSequence[] items) {
      mItems = items;
    }

    @Override
    public boolean areAllItemsEnabled() {
      return true;
    }

    @Override
    public boolean hasStableIds() {
      return true;
    }

    @Override
    public boolean isEmpty() {
      return false;
    }

    @Override
    public int getCount() {
      return mItems.length;
    }

    @Override
    public CharSequence getItem(final int position) {
      return mItems[position];
    }

    @Override
    public long getItemId(final int position) {
      return position;
    }

    @Override
    public View getView(final int position, final @Nullable View convertView,
        final @NonNull ViewGroup parent) {
      final CharSequence item = getItem(position);
      final ViewHolder holder;

      View v = convertView;

      if (v == null) {
        final Context context = StyledListPreference.this.getContext();

        v = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(
            R.layout.preference_list_item, parent, false);
        holder =
            new ViewHolder(context.getResources(), (TextView) v.findViewById(android.R.id.text1));
        holder.setDark(isDark);
        v.setTag(holder);
      } else {
        holder = (ViewHolder) v.getTag();
      }

      holder.setText(item);
      holder.setSelected(isSelected(item));

      return v;
    }

    private void setDark(final boolean dark) {
      isDark = dark;
    }

    private class ViewHolder {

      private final TextView mThemeNameView;
      private final Resources mResources;
      private boolean isDark;

      private ViewHolder(final @NonNull Resources resources,
          final @NonNull TextView themeNameView) {
        mResources = resources;
        mThemeNameView = themeNameView;
        init();
      }

      private void init() {
        mThemeNameView.setOnClickListener(new View.OnClickListener() {

          @Override
          public void onClick(final @NonNull View v) {
            final String newThemeName;
            final boolean isNew;

            if (isNew = (!isSelected(newThemeName = (((TextView) v).getText().toString())))) {
              P.themeName.put(newThemeName).apply();
              StyledListPreference.this.setValue(newThemeName);
            }

            StyledListPreference.this.mDialog.dismiss();

            if (isNew && mThemeChangeListener != null) {
              mThemeChangeListener.onThemeChanged();
            }
          }
        });
      }

      private void setText(final @NonNull CharSequence text) {
        mThemeNameView.setText(text);
      }

      private void setDark(final boolean dark) {
        isDark = dark;
        mThemeNameView.setTextColor(isDark ? Color.WHITE : Color.BLACK);
      }

      private void setSelected(final boolean isSelected) {
        mThemeNameView.setCompoundDrawablesWithIntrinsicBounds(
            isSelected ? getSelectedTintedDrawable() : getUnselectedTintedDrawable(), null, null,
            null);
      }

      @NonNull
      private Drawable getSelectedTintedDrawable() {
        final @DrawableRes int resId = R.drawable.ic_list_preference_selected;

        return tintDrawable(resId);
      }

      @NonNull
      private Drawable getUnselectedTintedDrawable() {
        final @DrawableRes int resId = R.drawable.ic_list_preference_unselected;

        return tintDrawable(resId);
      }

      @NonNull
      private Drawable tintDrawable(final @DrawableRes int drawableResId) {
        final Drawable ret;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
          ret = mResources.getDrawable(drawableResId, getContext().getTheme());
        } else {
          //noinspection deprecation
          ret = mResources.getDrawable(drawableResId);
        }

        if (ret != null) {
          ret.mutate();
          ret.setColorFilter(isDark ? Color.WHITE : Color.BLACK, PorterDuff.Mode.SRC_ATOP);

          return ret;
        }

        throw new IllegalStateException(String.format(Locale.ENGLISH, "Drawable %s not found.",
            mResources.getResourceName(drawableResId)));
      }
    }
  }
}
