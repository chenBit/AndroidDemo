package com.chenbit.demo.ui;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.chenbit.demo.R;
import com.chenbit.demo.download.DownloadActivity;
import com.chenbit.demo.receiver.DpmReceiver;
import com.chenbit.demo.test.binder.ITestBinder;
import com.chenbit.demo.test.binder.TestBinderService;
import com.chenbit.demo.test.dynamic_proxy.DynamicProxy;
import com.chenbit.demo.test.dynamic_proxy.ISell;
import com.chenbit.demo.test.event.EventTestActivity;
import com.chenbit.demo.test.hook.AnnotationTest;
import com.chenbit.demo.test.hook.Hooker;
import com.chenbit.demo.test.hook.TestAnnotation;
import com.chenbit.demo.test.sso.TestSsoActivity;
import com.chenbit.demo.utils.SimUtil;
import com.cw.androidbase.sdk.receiver.NetworkChangeReceiver;
import com.cw.androidbase.sdk.ui.activity.BaseActivity;
import com.cw.androidbase.sdk.utils.AssetUtil;
import com.cw.androidbase.sdk.utils.Util;
import com.tdtech.devicemanager.TelephonyPolicy;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static android.app.admin.DevicePolicyManager.ACTION_PROVISION_MANAGED_PROFILE;
import static android.app.admin.DevicePolicyManager.EXTRA_PROVISIONING_DEVICE_ADMIN_COMPONENT_NAME;
import static android.app.admin.DevicePolicyManager.EXTRA_PROVISIONING_DEVICE_ADMIN_PACKAGE_NAME;

public class MainActivity extends BaseActivity implements NetworkChangeReceiver.NetEventListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private boolean isRestrict;
    private DevicePolicyManager mDPM;
    private ComponentName mDPMComponentName;
    private IBinder mRemoteBinder;

    @Override
    protected int initContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
//        initReceiver();
        Button btnDownload = findViewById(R.id.btnDownload);
        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DownloadActivity.class);
                startActivity(intent);
            }
        });
        Button btnTest = findViewById(R.id.btnTest);
        initDPM();
        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                testString();
//                testProxy();
//                testReflect();
//                testAnnotation();
//                testDialog();
//                startKeepAliveActivity();
//                testBinder();
                isProcessExist(MainActivity.this, "com.nq.safelauncher");
            }
        });
        int id = SimUtil.getDefaultDataSubId(this);
        Log.e(TAG, "===MainActivity===" + Process.myPid() + "===id===" + id);

        findViewById(R.id.btnTestSso).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TestSsoActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initDPM() {
        mDPM = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        mDPMComponentName = DpmReceiver.getComponentName();
    }

    private void testEvent() {
        Intent intent = new Intent(this, EventTestActivity.class);
        startActivity(intent);
    }

    private void testDialog() {
        TestDialog dialog = new TestDialog();
        dialog.show(getFragmentManager(), "ff");
    }

    private void go2DeviceAdmin() {
        Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, DpmReceiver.getComponentName());
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, getString(R.string.app_name));
        startActivity(intent);
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void testPassword() {
        mDPM.setPasswordQuality(mDPMComponentName, DevicePolicyManager.PASSWORD_QUALITY_NUMERIC);
        mDPM.setPasswordMinimumLength(mDPMComponentName, 8);// 密码长度
        mDPM.setMaximumFailedPasswordsForWipe(mDPMComponentName, 3);// 最大尝试次数
        mDPM.setMaximumTimeToLock(mDPMComponentName, 15 * 1000);// 自动锁定最长时间
        mDPM.setPasswordExpirationTimeout(mDPMComponentName, 10 * 1000);// 密码有效期
        mDPM.setPasswordHistoryLength(mDPMComponentName, 3);// 密码历史记录

        if (!mDPM.isActivePasswordSufficient()) {
            Intent intent = new Intent(DevicePolicyManager.ACTION_SET_NEW_PASSWORD);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    //集合中有包含关系的字符串去重
    public List<String> dinstinct(List<String> words) {
        List<String> result = new ArrayList<>();
        // 将集合安装字符串长度升序排序
        StepComparator stepComparator = new StepComparator();
        Collections.sort(words, stepComparator);

        for (int i = 0; i < words.size(); i++) {
            boolean flag = true;
            String shortString = words.get(i);
            for (int j = i + 1; j < words.size(); j++) {
                String longString = words.get(j);
                if (longString.contains(shortString)) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                result.add(words.get(i));
            }
        }
        return result;
    }

    //比较器
    class StepComparator implements Comparator<String> {
        @Override
        public int compare(String str1, String str2) {
            if (str1.length() > str2.length()) {
                return 1;
            } else {
                return -1;
            }
        }
    }

    private void testString() {
        String a = "111222/";
        String b = a.substring(0, a.length() - 1);
        Handler handler = new Handler();
        Log.e(TAG, b);
    }

    private void testReflect() {
        Class<Hooker> hookerClass = Hooker.class;
        try {
            Field fuck = hookerClass.getDeclaredField("fuck");
            fuck.setAccessible(true);
            Hooker hooker = hookerClass.newInstance();
            fuck.set(hooker, 555);
            Log.d("---", "===" + hooker.fuck);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void testProxy() {
        DynamicProxy proxy = new DynamicProxy();
        ISell seller = proxy.getProxy();
        seller.sell("香蕉");
    }

    private void testBinder() {
        Intent intent = new Intent(this, TestBinderService.class);
        startService(intent);
        bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                ITestBinder binder = ITestBinder.Stub.asInterface(service);
                try {
                    binder.fuckBinder();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        }, Context.BIND_AUTO_CREATE);
    }

    private void testAnnotation() {
        if (TestAnnotation.class.isAnnotationPresent(AnnotationTest.class)) {
            AnnotationTest annotation = TestAnnotation.class.getAnnotation(AnnotationTest.class);
            int age = annotation.age();
            String value = annotation.value();
            Log.d("---", "===age:" + age + "===value: " + value);
        }
    }

    private void maybeLaunchProvisioning() {
        Intent intent = new Intent(ACTION_PROVISION_MANAGED_PROFILE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            intent.putExtra(EXTRA_PROVISIONING_DEVICE_ADMIN_COMPONENT_NAME, DpmReceiver.getComponentName());
        } else {
            intent.putExtra(EXTRA_PROVISIONING_DEVICE_ADMIN_PACKAGE_NAME, getPackageName());
        }
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, 0);
        } else {
            Toast.makeText(this, "设置work profile失败", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 0) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        testPassword();
                    }
                }, 5000);
            } else {
                finish();
            }
        } else {
            Toast.makeText(this, "Provisioning failed.", Toast.LENGTH_SHORT).show();
        }
    }

    private void startKioskMode() {
        final ComponentName customLauncher = new ComponentName(this, DownloadActivity.class);

        // enable custom launcher (it's disabled by default in manifest)
        getPackageManager().setComponentEnabledSetting(
                customLauncher,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);

        // set custom launcher as default home activity
        DevicePolicyManager mDevicePolicyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        ComponentName mAdminComponentName = DpmReceiver.getComponentName();
        mDevicePolicyManager.addPersistentPreferredActivity(mAdminComponentName, Util.getHomeIntentFilter(), customLauncher);
        Intent launchIntent = Util.getHomeIntent();
        startActivity(launchIntent);
    }

    private void testDeviceOwner() {
        ComponentName mAdminComponentName = DpmReceiver.getComponentName();
        DevicePolicyManager mDevicePolicyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
//        mDevicePolicyManager.setLockTaskPackages(mAdminComponentName,new String[]{});
        mDevicePolicyManager.clearDeviceOwnerApp(getPackageName());
    }

    @Override
    protected void initData() {

    }

    private void set2Array() {
        Set set = new HashSet();
        set.add(1);
        set.add(2);
        set.add(3);
        Integer[] iArray = new Integer[set.size()];
        set.toArray(iArray);
        for (Integer integer : iArray) {
            Log.e(TAG, "==integer==" + integer);
        }
    }

    private void testAssetConfig() {
        String ip = AssetUtil.getConfigIp(0);
        Log.e(TAG, "==IP==" + ip);
        String ip1 = AssetUtil.getConfigIp(1);
        Log.e(TAG, "==IP1==" + ip1);
    }

    private void testTelephoneWhiteList() {
        try {
            com.tdtech.devicemanager.DevicePolicyManager dpm = com.tdtech.devicemanager.DevicePolicyManager.getInstance(MainActivity.this);
            Log.e(TAG, "=====isRestrict======" + isRestrict);
            TelephonyPolicy telephonyPolicy = dpm.getTelephonyPolicy();
            telephonyPolicy.isAllowForPhoneNumberIntercepted(isRestrict);
            isRestrict = !isRestrict;
            String allInterceptInfo = telephonyPolicy.getAllInterceptInfo();
            Log.e(TAG, "=====testTelephoneWhiteList====allInterceptInfo==" + allInterceptInfo);
            boolean isInWhiteList = telephonyPolicy.queryPhoneNumber("18201664470");
            Log.e(TAG, "=====testTelephoneWhiteList==18201664470==isInWhiteList==" + isInWhiteList);
            telephonyPolicy.deletePhoneNumber("18511866081");
            telephonyPolicy.deletePhoneNumber("18201664470");
        } catch (Exception e) {
            e.printStackTrace();
            showToast("出错啦");
        }
    }

    public static void showAlert(Context context, String title_res, String msg_res) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle(title_res)
                .setMessage(msg_res)
                .setIcon(android.R.drawable.ic_dialog_info);
        builder.create();
        builder.setCancelable(true);
        builder.show();
    }

    @Override
    public void onNetChange(int netWorkState) {
        Log.e(TAG, "===onNetChange===" + netWorkState);
        showToast("网络发生变化了：" + netWorkState);
    }

    private void initReceiver() {
        //创建广播
        InnerRecevier innerReceiver = new InnerRecevier();
        //动态注册广播
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        //启动广播
        registerReceiver(innerReceiver, intentFilter);
    }

    class InnerRecevier extends BroadcastReceiver {

        final String SYSTEM_DIALOG_REASON_KEY = "reason";

        final String SYSTEM_DIALOG_REASON_RECENT_APPS = "recentapps";

        final String SYSTEM_DIALOG_REASON_HOME_KEY = "homekey";

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (Intent.ACTION_CLOSE_SYSTEM_DIALOGS.equals(action)) {
                String reason = intent.getStringExtra(SYSTEM_DIALOG_REASON_KEY);
                if (reason != null) {
                    if (reason.equals(SYSTEM_DIALOG_REASON_HOME_KEY)) {
                        Toast.makeText(MainActivity.this, "Home键被监听", Toast.LENGTH_SHORT).show();
                    } else if (reason.equals(SYSTEM_DIALOG_REASON_RECENT_APPS)) {
                        Toast.makeText(MainActivity.this, "多任务键被监听", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }

    private void initAmsBinder() {
        try {
            Class<?> amnClass = Class.forName("android.app.ActivityManagerNative");
            Object amn = amnClass.getMethod("getDefault").invoke(amnClass);
            Field mRemote = amn.getClass().getDeclaredField("mRemote");
            mRemote.setAccessible(true);
            mRemoteBinder = (IBinder) mRemote.get(amn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startKeepAliveActivity() {
        Intent intent = new Intent(getApplicationContext(), KeepAliveActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public boolean isProcessExist(Context contex, String pkg) {
        ActivityManager activityManager = (ActivityManager) contex.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> processInfos = activityManager.getRunningAppProcesses();
        if (processInfos != null) {
            for (ActivityManager.RunningAppProcessInfo info : processInfos) {
                Log.d(TAG,"===process name: " + info.processName);
                if (pkg.equals(info.processName)) {
                    return true;
                }
            }
        }
        return false;
    }
}
