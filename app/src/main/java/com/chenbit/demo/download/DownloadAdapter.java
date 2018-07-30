package com.chenbit.demo.download;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chenbit.demo.R;
import com.chenbit.demo.bean.AppInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by NSKY on 2018/7/3.
 */

public class DownloadAdapter extends BaseAdapter {
    private static final String TAG = DownloadAdapter.class.getSimpleName();
    private List<AppInfo> mDataList;
    private Context mContext;
    private long oldTime;
    private Map<String, Integer> mPositionIndex;

    public DownloadAdapter(Context context, List<AppInfo> datas) {
        mContext = context;
        mDataList = datas;
        mPositionIndex = new HashMap<>();
    }

    @Override
    public int getCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataList == null ? null : mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_download, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final AppInfo appInfo = (AppInfo) getItem(position);
        mPositionIndex.put(appInfo.url, position);
        viewHolder.progressBar.setProgress(appInfo.progress);
        viewHolder.tvName.setText(appInfo.name);
        viewHolder.btnStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "====click===");
                long curTime = System.currentTimeMillis();
                if (curTime - oldTime < 500) {
                    //do nothing
                } else {
                    startDownload(appInfo);
                    oldTime = System.currentTimeMillis();
                }
            }
        });
        int status = appInfo.status;
        setContentByStatus(status, viewHolder.btnStatus);
        return convertView;
    }

    static class ViewHolder {
        public ProgressBar progressBar;
        public Button btnStatus;
        public TextView tvName;

        public ViewHolder(View view) {
            progressBar = view.findViewById(R.id.pb);
            btnStatus = view.findViewById(R.id.btnStatus);
            tvName = view.findViewById(R.id.tvName);
        }
    }

    private void startDownload(final AppInfo appInfo) {
        Intent intent = new Intent(mContext, DownloadService.class);
        intent.setAction(DownloadService.ACTION_DOWNLOAD);
        intent.putExtra(DownloadService.DATA_DOWNLOAD_INFO, appInfo);
        mContext.startService(intent);
    }

    public AppInfo findDataByTag(String tag) {
        if (mDataList != null && mDataList.size() > 0) {
            for (AppInfo app : mDataList) {
                if (tag.equalsIgnoreCase(app.url)) {
                    return app;
                }
            }
        }
        return null;
    }

    public int findPositionByTag(String tag) {
        return mPositionIndex.get(tag);
    }

    public void setContentByStatus(int status, Button btn) {
        switch (status) {
            case DownloadService.STATUS_DOWNLOADING:
                btn.setText("暂停");
                break;
            case DownloadService.STATUS_PAUSE:
                btn.setText("下载");
                break;
            case DownloadService.STATUS_COMPLETED:
                btn.setText("安装");
                break;
            default:
                btn.setText("下载");
                break;
        }
    }
}
