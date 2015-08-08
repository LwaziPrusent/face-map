package za.co.be.rettakid.facemap.fragments;

import android.app.Activity;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import za.co.be.rettakid.facemap.R;

@EActivity(R.layout.activity_camera)
public class CameraActivity extends Activity {

    private static final String TAG = CameraActivity.class.getName();

    public enum CURRENT_MARGIN {TOP,LEFT};

    @ViewById
    ImageView imgTopControl;
    @ViewById
    ImageView imgLeftControl;
    @ViewById
    RelativeLayout faceContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null == savedInstanceState) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, Camera2BasicFragment.newInstance())
                    .commit();
        }
    }

    @AfterViews
    public void init()  {
        imgTopControl.setOnTouchListener(new MovableImage(CURRENT_MARGIN.TOP,faceContainer));
        imgLeftControl.setOnTouchListener(new MovableImage(CURRENT_MARGIN.LEFT,faceContainer));

        Log.i(TAG, imgLeftControl.getRight() + " - " + imgLeftControl.getRight());

    }

}

class MovableImage implements OnTouchListener   {

    private static final String TAG = MovableImage.class.getName();

    CameraActivity.CURRENT_MARGIN currentMargin;
    RelativeLayout faceContainer;

    PointF DownPT = new PointF(); // Record Mouse Position When Pressed Down
    PointF StartPT = new PointF(); // Record Start Position of 'img'

    public MovableImage(CameraActivity.CURRENT_MARGIN currentMargin, RelativeLayout faceContainer) {
        this.currentMargin = currentMargin;
        this.faceContainer = faceContainer;
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        int eid = event.getAction();
        switch (eid) {
            case MotionEvent.ACTION_MOVE:
                PointF mv = new PointF(event.getX() - DownPT.x, event.getY() - DownPT.y);
                view.setX((int) (StartPT.x + mv.x));
                view.setY((int) (StartPT.y + mv.y));
                StartPT = new PointF(view.getX(), view.getY());
                break;
            case MotionEvent.ACTION_DOWN:
                DownPT.x = event.getX();
                DownPT.y = event.getY();
                StartPT = new PointF(view.getX(), view.getY());
                break;
            case MotionEvent.ACTION_UP:
                // Nothing have to do
                break;
            default:
                break;
        }

        RelativeLayout.LayoutParams faceContainerLayout = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        switch (currentMargin)  {
            case TOP:
                faceContainerLayout.setMargins(50,(int)view.getY(),50,(int)view.getY());
                break;
            case LEFT:
                faceContainerLayout.setMargins((int)view.getX(),50,(int)view.getY(),50);
                break;
        }

        faceContainer.setLayoutParams(faceContainerLayout);
        return true;
    }

}