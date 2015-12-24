package com.jorge.boats.task;

import android.content.Context;
import android.support.annotation.NonNull;
import com.fernandocejas.frodo.annotation.RxLogObservable;
import com.jorge.boats.domain.executor.PostExecutionThread;
import com.jorge.boats.domain.executor.ThreadExecutor;
import com.jorge.boats.domain.interactor.UseCase;
import com.jorge.boats.io.FontManager;
import javax.inject.Inject;
import rx.Observable;

public class TypefaceLoadTask extends UseCase {

  private final Context mContext;

  @Inject
  public TypefaceLoadTask(final @NonNull Context context, final ThreadExecutor threadExecutor,
      final PostExecutionThread postExecutionThread) {
    super(threadExecutor, postExecutionThread);
    mContext = context;
  }

  //This would be better done using rx.Single but support from Frodo is still in the works and it also could clunk the architecture a bit
  @RxLogObservable @Override protected Observable<Object> buildUseCaseObservable() {
    return Observable.<Object>just(FontManager.get(mContext, FontManager.FONT_APP));
  }
}
