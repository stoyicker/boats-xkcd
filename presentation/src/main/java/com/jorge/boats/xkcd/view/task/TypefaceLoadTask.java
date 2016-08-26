package com.jorge.boats.xkcd.view.task;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;

import com.jorge.boats.xkcd.domain.executor.PostExecutionThread;
import com.jorge.boats.xkcd.domain.executor.ThreadExecutor;
import com.jorge.boats.xkcd.domain.interactor.UseCase;
import com.jorge.boats.xkcd.view.FontManager;

import javax.inject.Inject;

import rx.Observable;

public class TypefaceLoadTask extends UseCase<Typeface> {

  private final Context mContext;

  @Inject
  public TypefaceLoadTask(final @NonNull Context context, final ThreadExecutor threadExecutor,
      final PostExecutionThread postExecutionThread) {
    super(threadExecutor, postExecutionThread);
    mContext = context;
  }

  //TODO Change this to use rx.single, remove Frodo
  @Override
  protected Observable<Typeface> buildUseCaseObservable() {
    return Observable.just(FontManager.get(mContext, FontManager.FONT_APP));
  }
}
