package com.jorge.boats.io.task;

import android.content.Context;
import android.support.annotation.NonNull;
import com.jorge.boats.domain.executor.PostExecutionThread;
import com.jorge.boats.domain.executor.ThreadExecutor;
import com.jorge.boats.domain.interactor.UseCase;
import com.jorge.boats.io.FontManager;
import javax.inject.Inject;
import rx.Observable;

public class TypefaceLoad extends UseCase {

  private final Context mContext;

  @Inject public TypefaceLoad(final @NonNull Context context, final ThreadExecutor threadExecutor,
      final PostExecutionThread postExecutionThread) {
    super(threadExecutor, postExecutionThread);
    mContext = context;
  }

  @Override protected Observable<Object> buildUseCaseObservable() {
    return Observable.<Object>just(FontManager.get(mContext, FontManager.FONT_APP));
  }
}
