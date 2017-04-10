package xeme.com.gradientbackground;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by Alfishan@gmail.com on 8/2/17.
 */
public class GradientBackgroundPainter {


    private final AppCompatImageView target;
    private final Context context;
    private CompositeDisposable mCompositeDisposable;
    private static  Drawable LastDrawable =null;
    /**
     * set TransitionTime in MILLISECONDS
     */
    private final long TransitionTime=2000;



    public GradientBackgroundPainter(@NonNull final AppCompatImageView target) {
        this.target = target;
        context = target.getContext().getApplicationContext();
        mCompositeDisposable=new CompositeDisposable();


    }

    public void Start(){
        Observable<Long> observable = Observable.interval(TransitionTime, TimeUnit.MILLISECONDS, Schedulers.io());
        mCompositeDisposable.add(observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Long>() {
                    @Override
                    public void onNext(Long aLong) {

                        if (LastDrawable == null) {
                            LastDrawable = ContextCompat.getDrawable(context, R.drawable.colors1);
                        }
                        GradientDrawable gd1= new GradientDrawable(getRandom(), new int[] {getMatColor("600"),getMatColor("300")});
                        gd1.setCornerRadius(0f);
                        gd1.setGradientType(GradientDrawable.LINEAR_GRADIENT);
                        TransitionDrawable transitionDrawable = new TransitionDrawable(new Drawable[] {LastDrawable, gd1 });
                        transitionDrawable.setCrossFadeEnabled(false);
                        //transitionDrawable.setAlpha(1);
                        transitionDrawable.startTransition((int) TransitionTime);
                        target.setImageDrawable(transitionDrawable);
                        LastDrawable =gd1;
                    }

                    @Override
                    public void onError(Throwable e) {
                        target.setImageDrawable(LastDrawable);
                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }

    public void stop() {
       // handler.removeCallbacksAndMessages(null);
        if (!mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.dispose();
        }
    }

    /**
     * to get random material color from array
     * @param typeColor // please take a look at array of colors for valid values
     *
     * @return random material random color
     */
    private int getMatColor(String typeColor) {
        int returnColor = Color.BLACK;
        int arrayId = context.getResources().getIdentifier("mdcolor_" + typeColor, "array", context.getPackageName());
        if (arrayId != 0) {
              TypedArray  colors =context. getResources().obtainTypedArray(arrayId);
            int index = (int) (Math.random() * colors.length());
            returnColor = colors.getColor(index, Color.BLACK);
            colors.recycle();
        }
        return returnColor;
    }

    /**
     *
     * @return random GradientDrawable.Orientation
     */
    private static GradientDrawable.Orientation getRandom() {
        return GradientDrawable.Orientation.values()[(int) (Math.random() * GradientDrawable.Orientation.values().length)];
    }
}