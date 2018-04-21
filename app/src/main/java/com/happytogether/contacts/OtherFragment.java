package com.happytogether.contacts;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Image;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.qrcode.QRCodeReader;
import com.google.zxing.qrcode.encoder.QRCode;
import com.happytogether.contacts.MainActivity;

import static android.app.Activity.RESULT_OK;


/**
 * Created by Monsterkill on 2018/4/16.
 */

public class OtherFragment extends Fragment {
    private int REQUEST_CODE_SCAN = 111;
    public TextView result ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_other, container, false);
        result = (TextView) rootView.findViewById(R.id.resultText);
        CardView QRScan = (CardView) rootView.findViewById(R.id.cardScan);
        QRScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.CAMERA},1);
                } else {
                    /*ZxingConfig是配置类  可以设置是否显示底部布局，闪光灯，相册，是否播放提示音  震动等动能
                     * 也可以不传这个参数
                     * 不传的话  默认都为默认不震动  其他都为true
                     * https://github.com/yuzhiqiang1993/zxing
                     */
                    // ZxingConfig config = new ZxingConfig();
                    // config.setShowbottomLayout(true);//底部布局（包括闪光灯和相册）
                    // config.setPlayBeep(true);//是否播放提示音
                    // config.setShake(true);//是否震动
                    // config.setShowAlbum(true);//是否显示相册
                    // config.setShowFlashLight(true);//是否显示闪光灯
                    //
                    // 如果不传 ZxingConfig的话，两行代码就能搞定了

                    Intent intent = new Intent(getActivity(), com.yzq.zxinglibrary.android.CaptureActivity.class);
                    //intent.putExtra(Constant.INTENT_ZXING_CONFIG, config);
                    startActivityForResult(intent, REQUEST_CODE_SCAN);
                }

            }
        });
        return rootView;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // 扫描二维码/条码回传
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            if (data != null) {

                String content = data.getStringExtra(com.yzq.zxinglibrary.common.Constant.CODED_CONTENT);
                //暂时把扫描结果显示在TextView
                result.setText("扫描结果为：" + content);
            }
        }
    }


}
