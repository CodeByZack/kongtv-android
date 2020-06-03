package com.zack.appupdate;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import androidx.core.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by zack on 2018/5/20.
 */

public class AppUpdate {
    private  AlertDialog alertDialog ;
    private  ProgressDialog progressDialog;
    private  Context context;
    private String downloadUrl,savePath;
    private static AppUpdate appUpdate;
    private boolean force;

    private AppUpdate(Context context){
        this.context = context;
    }

    public static AppUpdate init(Context context){
        if(appUpdate == null){
            appUpdate = new AppUpdate(context);
        }
        return appUpdate;
    }
    public AppUpdate setDownloadUrl(String url){
        appUpdate.downloadUrl = url;
        return appUpdate;
    }
    public AppUpdate setSavePath(String savePath){
        appUpdate.savePath = savePath;
        return appUpdate;
    }
    public AppUpdate setForceUpdate(boolean force){
        appUpdate.force = force;
        return appUpdate;
    }


    public void download(){
        if(TextUtils.isEmpty(downloadUrl) || TextUtils.isEmpty(savePath)){
            Log.e("AppUpdate:","请检查 downloadurl 和 savepath是否为空！");
            return;
        }
        initProcessDialog();
        new DownLoadApk().execute(downloadUrl,savePath);
    }
    public void showUpdateDialog(String title , String desc, DialogInterface.OnClickListener listener){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title)
                .setMessage(desc)
                .setPositiveButton("确定更新", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        download();
                        alertDialog.dismiss();
                    }
                });
        if(!this.force){
            if(listener != null){
                builder.setNegativeButton("取消",listener);
            }else{
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        alertDialog.dismiss();
                    }
                });
            }
        }
        alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }

    public void dismiss(){
        this.context = null;
        if(alertDialog!=null){
            alertDialog.dismiss();
            alertDialog = null;
        }
        appUpdate = null;
    }

    private class DownLoadApk extends AsyncTask<String,Integer,Boolean>{
        @Override
        protected Boolean doInBackground(String... strings) {
            download(strings[0],strings[1]);
            return true;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            if(progressDialog!=null) progressDialog.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(progressDialog != null) {
                progressDialog.cancel();
                progressDialog = null;
            }
            if(aBoolean){
                installApk(new File(savePath));
            }else{
                Log.e("AppUpdate:","下载失败！");
            }
        }

        private boolean download(String urlSrc,String savePath){
            int leng = -1;
            File file = new File(savePath);
            InputStream inputStream = null;
            FileOutputStream fileOutputStream = null;
            BufferedInputStream bufferedInputStream = null;
            try {
                URL url = new URL(urlSrc);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(5000);
                leng = conn.getContentLength();
                if (file.exists()) {
                    file.delete();
                }
                inputStream = conn.getInputStream();
                fileOutputStream = new FileOutputStream(file);
                bufferedInputStream = new BufferedInputStream(inputStream);
                byte[] buffer = new byte[1024];
                int len;
                int total = 0;
                while ((len = bufferedInputStream.read(buffer)) != -1) {
                    fileOutputStream.write(buffer, 0, len);
                    total += len;
                    if (null != progressDialog) {
                        publishProgress((int) ((total / (float) leng) * 100));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (file.exists()) {
                    file.delete();
                }
            } finally {
                try {
                    if (null != fileOutputStream) {
                        fileOutputStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    if (null != bufferedInputStream) {
                        bufferedInputStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    if (null != inputStream) {
                        inputStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return file.exists();
        }
    }
    private void initProcessDialog() {
        if (progressDialog == null) {

            progressDialog = new ProgressDialog(context);
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    progressDialog = null;
                }
            });
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setMessage("下载中...");
            progressDialog.setMax(100);
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.show();

        }
    }
    public void installApk(File file){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri data;
        // 判断版本大于等于7.0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // "net.csdn.blog.ruancoder.fileprovider"即是在清单文件中配置的authorities
            data = FileProvider.getUriForFile(context, "com.zack.kongtv.appupdate.fileprovider", file);
            // 给目标应用一个临时授权
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            data = Uri.fromFile(file);
        }
        intent.setDataAndType(data, "application/vnd.android.package-archive");
        context.startActivity(intent);
    }
}
