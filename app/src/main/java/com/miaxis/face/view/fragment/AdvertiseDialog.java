package com.miaxis.face.view.fragment;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageSwitcher;
import android.widget.VideoView;

import com.miaxis.face.R;
import com.miaxis.face.event.PlayAdvertisementEvent;
import com.miaxis.face.util.FileUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by xu.nan on 2016/10/14.
 */

public class AdvertiseDialog extends BaseDialogFragment {

    Unbinder unbinder;
    private View.OnTouchListener listener;

    @BindView(R.id.vv_advertisement)
    VideoView vvAdvertisement;

    private Bitmap bitmap;
    private File[] adFiles;
    private int fileNo = 0;

    public View.OnTouchListener getListener() {
        return listener;
    }

    public void setListener(View.OnTouchListener listener) {
        this.listener = listener;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            DisplayMetrics dm = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
        hideNavigationBar();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.dialog_advertise, container);
        view.setOnTouchListener(listener);
        unbinder = ButterKnife.bind(this, view);
        showAdvertisement();
        EventBus.getDefault().register(this);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
        unbinder.unbind();
    }

    private void showAdvertisement() {
        File videoDir = new File(FileUtil.getAdvertisementFilePath());
        if (videoDir.exists() && videoDir.isDirectory()) {
            adFiles = videoDir.listFiles();
            if (adFiles != null && adFiles.length > 0) {
                play(adFiles[0].getAbsolutePath());
            }
        }
    }

    public static boolean isAdExist() {
        File videoDir = new File(FileUtil.getAdvertisementFilePath());
        File[] adFiles;
        if (videoDir.exists() && videoDir.isDirectory()) {
            adFiles = videoDir.listFiles();
            if (adFiles != null && adFiles.length > 0) {
                return true;
            }
        }
        return false;
    }

    private void play(String path) {
        fileNo ++;
        if (fileNo >= adFiles.length) {
            fileNo = 0;
        }
        vvAdvertisement.setBackgroundResource(0);
        if (bitmap != null) {
            bitmap.recycle();
        }

        if (View.VISIBLE != vvAdvertisement.getVisibility()) {
            vvAdvertisement.setVisibility(View.VISIBLE);
        }
        if (isVideo(path)) {
            try {
                vvAdvertisement.setVideoPath(path);
                vvAdvertisement.requestFocus();
                vvAdvertisement.start();
                vvAdvertisement.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        EventBus.getDefault().post(new PlayAdvertisementEvent());
                    }
                });
            } catch (Exception e) {
                EventBus.getDefault().post(new PlayAdvertisementEvent());
            }
        } else if (isImg(path)) {
            vvAdvertisement.setVisibility(View.VISIBLE);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            options.inSampleSize = 4;
            options.inJustDecodeBounds = false;
            bitmap = BitmapFactory.decodeFile(path, options);
            Drawable _drawable = new BitmapDrawable(bitmap);
            vvAdvertisement.setBackground(_drawable);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(8000);
                        EventBus.getDefault().post(new PlayAdvertisementEvent());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    private boolean isVideo(String path) {
        if (path.toLowerCase().endsWith(".mov")
                || path.toLowerCase().endsWith(".mkv")
                || path.toLowerCase().endsWith(".mp4")
                || path.toLowerCase().endsWith(".avi")) {
            return true;
        }
        return false;
    }

    // 判断是否为图片文件
    private boolean isImg(String path) {
        if (path.toLowerCase().endsWith(".jpg")
                || path.toLowerCase().endsWith(".gif")
                || path.toLowerCase().endsWith(".png")
                || path.toLowerCase().endsWith(".jpeg")) {

            return true;
        }
        return false;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPlayAdvertisementEvent(PlayAdvertisementEvent e) {
        play(adFiles[fileNo].getAbsolutePath());
    }

}
