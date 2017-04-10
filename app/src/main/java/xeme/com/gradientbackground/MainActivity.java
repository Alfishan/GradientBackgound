package xeme.com.gradientbackground;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;

import static xeme.com.gradientbackground.R.id.fab;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.am_gradbg)
    AppCompatImageView mAmGradbg;
    @BindView(fab)
    FloatingActionButton mFab;
    private GradientBackgroundPainter mGradientBackgroundPainter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        startGradientBackgroundPainter();
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Hi from Alfishan... :)", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    void startGradientBackgroundPainter() {
        if (mGradientBackgroundPainter == null) {
            mGradientBackgroundPainter = new GradientBackgroundPainter((AppCompatImageView) mAmGradbg);
        }
        mGradientBackgroundPainter.Start();
    }

    void stopGradientBackgroundPainter() {
        if (mGradientBackgroundPainter != null) {
            mGradientBackgroundPainter.stop();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopGradientBackgroundPainter();
    }
}
