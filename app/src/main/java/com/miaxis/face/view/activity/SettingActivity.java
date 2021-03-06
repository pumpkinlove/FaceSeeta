package com.miaxis.face.view.activity;

import android.app.TimePickerDialog;
import android.app.smdt.SmdtManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.Gson;
import com.miaxis.face.R;
import com.miaxis.face.app.Face_App;
import com.miaxis.face.bean.AjaxResponse;
import com.miaxis.face.bean.Config;
import com.miaxis.face.bean.Version;
import com.miaxis.face.event.CountRecordEvent;
import com.miaxis.face.event.TimerResetEvent;
import com.miaxis.face.greendao.gen.ConfigDao;
import com.miaxis.face.greendao.gen.RecordDao;
import com.miaxis.face.net.UpdateVersion;
import com.miaxis.face.util.MyUtil;
import com.miaxis.face.view.fragment.UpdateDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.zz.mxhidfingerdriver.MXFingerDriver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SettingActivity extends BaseActivity {

    @BindView(R.id.tv_version)
    TextView tvVersion;
    @BindView(R.id.et_ip)
    EditText etIp;
    @BindView(R.id.et_port)
    EditText etPort;
    @BindView(R.id.et_org)
    EditText etOrg;
    @BindView(R.id.et_pass_score)
    EditText etPassScore;
    @BindView(R.id.rb_net_on)
    RadioButton rbNetOn;
    @BindView(R.id.rb_net_off)
    RadioButton rbNetOff;
    @BindView(R.id.rg_net)
    RadioGroup rgNet;
    @BindView(R.id.rb_query_on)
    RadioButton rbQueryOn;
    @BindView(R.id.rb_query_off)
    RadioButton rbQueryOff;
    @BindView(R.id.rg_query)
    RadioGroup rgQuery;
    @BindView(R.id.tv_select_time)
    TextView tvSelectTime;
    @BindView(R.id.tv_result_count)
    TextView tvResultCount;
    @BindView(R.id.et_monitor_interval)
    EditText etMonitorInterval;
    @BindView(R.id.et_banner)
    EditText etBanner;
    @BindView(R.id.btn_save_config)
    Button btnSaveConfig;
    @BindView(R.id.btn_cancel_config)
    Button btnCancelConfig;
    @BindView(R.id.btn_clear_now)
    Button btnClearNow;
    @BindView(R.id.btn_update)
    Button btnUpdate;
    @BindView(R.id.btn_exit)
    Button btnExit;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.s_verify_mode)
    Spinner sVerifyMode;

    private Config config;
    private UpdateDialog updateDialog;
    private SmdtManager smdtManager;
    private MXFingerDriver fingerDriver;
    private boolean hasFingerDevice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        initWindow();
        initData();
        initModeSpinner();
        initView();
        try {
            Face_App.getInstance().igpioControlDemo.setGpio(3, false);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    void initModeSpinner() {
        List<String> verifyModeList = Arrays.asList(getResources().getStringArray(R.array.verifyMode));
        if (!hasFingerDevice) {
            String faceOnly = verifyModeList.get(0);
            String local = verifyModeList.get(6);
            verifyModeList = new ArrayList<>();
            verifyModeList.add(faceOnly);
            verifyModeList.add(local);
        }
        ArrayAdapter<String> myAdapter = new ArrayAdapter<>(this, R.layout.spinner_style_display, R.id.tvDisplay, verifyModeList);
        sVerifyMode.setAdapter(myAdapter);
        sVerifyMode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (hasFingerDevice) {
                    config.setVerifyMode(position);
                } else {
                    if (position == 1) {
                        config.setVerifyMode(Config.MODE_LOCAL_FEATURE);
                    } else {
                        config.setVerifyMode(Config.MODE_FACE_ONLY);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                config.setVerifyMode(0);
            }
        });
    }

    private void initData() {
        config = Face_App.getInstance().getDaoSession().getConfigDao().loadByRowId(1L);
        smdtManager = SmdtManager.create(this);
        int pid = 0x0202;
        int vid = 0x821B;
        fingerDriver = new MXFingerDriver(getApplicationContext(), pid, vid);
        hasFingerDevice = checkHasFingerDevice();
    }

    private void initView() {
        etIp.setText(config.getIp());
        etPort.setText(config.getPort() + "");
        etBanner.setText(config.getBanner());
        etPassScore.setText(config.getPassScore() + "");
        etOrg.setText(config.getOrgName());
        tvSelectTime.setText(config.getUpTime());
        etMonitorInterval.setText(config.getIntervalTime() + "");
        tvVersion.setText(MyUtil.getCurVersion(this).getVersion());
        rbQueryOn.setChecked(config.getQueryFlag());
        rbQueryOff.setChecked(!config.getQueryFlag());
        rbNetOn.setChecked(config.getNetFlag());
        rbNetOff.setChecked(!config.getNetFlag());
        if (hasFingerDevice) {
            sVerifyMode.setSelection(config.getVerifyMode());
        } else {
            sVerifyMode.setSelection(config.getVerifyMode() / 6);           //无指纹模块时， 验证模式 只有0 或 6
        }

        etPwd.setText(config.getPassword());
        new Thread(new Runnable() {
            @Override
            public void run() {
                RecordDao recordDao = Face_App.getInstance().getDaoSession().getRecordDao();
                long t1 = System.currentTimeMillis();
                long notUpCount = recordDao.queryBuilder().where(RecordDao.Properties.HasUp.eq(false)).count();
                long t2 = System.currentTimeMillis();
                long count = recordDao.count();
                long t3 = System.currentTimeMillis();
                Log.e("==count", "耗时" + (t2 - t1) + " _ " + (t3 - t2));
                EventBus.getDefault().post(new CountRecordEvent(notUpCount, count));
            }
        }).start();

        updateDialog = new UpdateDialog();
        updateDialog.setContext(this);
    }

    private boolean checkHasFingerDevice() {
        int re;
        for (int i = 0; i < 20; i ++) {
            re = fingerDriver.mxGetDevVersion(new byte[120]);
            if (re == 0) {
                return true;
            }
        }
        return false;
    }

    @OnClick(R.id.tv_select_time)
    void onSelectTime(View view) {
        String[] strs = tvSelectTime.getText().toString().split(" : ");
        int h = Integer.valueOf(strs[0]);
        int m = Integer.valueOf(strs[1]);
        TimePickerDialog d = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String h = hourOfDay + "";
                String m = minute + "";
                if (hourOfDay < 10) {
                    h = "0" + h;
                }
                if (minute < 10) {
                    m = "0" + m;
                }
                tvSelectTime.setText(h + " : " + m);
            }
        }, h, m, true);
        d.show();
    }

    @OnClick(R.id.btn_save_config)
    void save() {
        config.setIp(etIp.getText().toString());
        config.setPort(Integer.valueOf(etPort.getText().toString()));
        config.setOrgName(etOrg.getText().toString());
        config.setPassScore(Float.valueOf(etPassScore.getText().toString()));
        config.setNetFlag(rbNetOn.isChecked());
        config.setQueryFlag(rbQueryOn.isChecked());
        config.setUpTime(tvSelectTime.getText().toString());
        config.setIntervalTime(Integer.valueOf(etMonitorInterval.getText().toString()));
        config.setBanner(etBanner.getText().toString());
        if (etPwd.getText().length() != 6) {
            Toast.makeText(this, "请填写6位数字密码", Toast.LENGTH_SHORT).show();
            return;
        }
        config.setPassword(etPwd.getText().toString());
        ConfigDao configDao = Face_App.getInstance().getDaoSession().getConfigDao();
        configDao.update(config);
        EventBus.getDefault().post(new TimerResetEvent());
        finish();
    }

    @OnClick(R.id.btn_cancel_config)
    void cancel() {
        finish();
    }

    @OnClick(R.id.btn_clear_now)
    void upLoad() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Face_App.timerTask.run();
            }
        }).start();
    }

    @OnClick(R.id.btn_update)
    void update() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://" + config.getIp() + ":" + config.getPort() + "/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        UpdateVersion uv = retrofit.create(UpdateVersion.class);
        Call<AjaxResponse> call = uv.checkVersion();
        call.enqueue(new Callback<AjaxResponse>() {
            @Override
            public void onResponse(Call<AjaxResponse> call, Response<AjaxResponse> rsp) {
                try {
                    Version lastVersion = null;
                    Gson g = new Gson();
                    AjaxResponse response = rsp.body();
                    if (response.getCode() == AjaxResponse.FAILURE) {
                        MyUtil.alert(getFragmentManager(), response.getMessage());
                        return;
                    } else if (response.getCode() == AjaxResponse.SUCCESS) {
                        lastVersion = g.fromJson(g.toJson(response.getData()), Version.class);
                    }
                    Version curVersion = MyUtil.getCurVersion(getApplicationContext());
                    if (lastVersion.getVersionCode() > curVersion.getVersionCode()) {
                        updateDialog.setLastVersion(lastVersion);
                        updateDialog.show(getFragmentManager(), "update_dialog");
                    } else {
                        MyUtil.alert(getFragmentManager(), "您已经是最新版了！");
                    }
                } catch (Exception e) {
                    MyUtil.alert(getFragmentManager(), "解析数据失败");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<AjaxResponse> call, Throwable t) {
                MyUtil.alert(getFragmentManager(), "更新失败！");
            }
        });
    }

    @OnClick(R.id.btn_exit)
    void singOut() {
        Face_App.getInstance().unableDog();
        smdtManager.smdtSetStatusBar(this, true);
        smdtManager.smdtSetGpioValue(2, false);
        try {
            Face_App.getInstance().igpioControlDemo.setGpio(3, false);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        finish();
        throw new RuntimeException();
    }

    @OnClick(R.id.btn_white_manage)
    void onWhiteManage() {
        startActivity(new Intent(this, WhiteActivity.class));
    }

    @OnClick(R.id.btn_local_feature_manage)
    void onLocalFeatureManage() {
        startActivity(new Intent(this, LocalFeatureActivity.class));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCountRecordEvent(CountRecordEvent e) {
        tvResultCount.setText(e.getNotUpCount() + " / " + e.getCount());
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
