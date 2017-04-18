package com.example.administrator.screensolutionconvert;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private EditText et_width;
    private EditText et_height;
    private EditText et_dpi;
    private EditText et_width_reference;
    private EditText et_height_reference;
    private EditText et_width_specify;
    private EditText et_height_specify;
    private TextView tv_sw;
    private TextView tv_result_width;
    private TextView tv_result_height;
    private Button btn_ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        setListener();
    }

    private void setListener() {
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String targetPhoneWidth = et_width.getText().toString().trim();
                if (TextUtils.isEmpty(targetPhoneWidth)) {
                    et_width.requestFocus();
                    return;
                }
                int targetPhoneWidthValue = Integer.parseInt(targetPhoneWidth);

                String targetPhoneHeight = et_height.getText().toString().trim();
                if (TextUtils.isEmpty(targetPhoneHeight)) {
                    et_height.requestFocus();
                    return;
                }
                int targetPhoneHeightValue = Integer.parseInt(targetPhoneHeight);

                String targetPhoneDPI = et_dpi.getText().toString().trim();
                if (TextUtils.isEmpty(targetPhoneDPI)) {
                    et_dpi.requestFocus();
                    return;
                }
                int targetPhoneDPIValue = Integer.parseInt(targetPhoneDPI);

                String referencePhoneWidth = et_width_reference.getText().toString().trim();
                if (TextUtils.isEmpty(referencePhoneWidth)) {
                    et_width_reference.requestFocus();
                    return;
                }
                int referencePhoneWidthValue = Integer.parseInt(referencePhoneWidth);

                String referencePhoneHeight = et_height_reference.getText().toString().trim();
                if (TextUtils.isEmpty(referencePhoneHeight)) {
                    et_height_reference.requestFocus();
                    return;
                }
                int referencePhoneHeightValue = Integer.parseInt(referencePhoneHeight);

                String specifyPhoneWidth = et_width_specify.getText().toString().trim();
                if (TextUtils.isEmpty(specifyPhoneWidth)) {
                    et_width_specify.requestFocus();
                    return;
                }
                int specifyPhoneWidthValue = Integer.parseInt(specifyPhoneWidth);

                String specifyPhoneHeight = et_height_specify.getText().toString().trim();
                if (TextUtils.isEmpty(specifyPhoneHeight)) {
                    et_height_specify.requestFocus();
                    return;
                }
                int specifyPhoneHeightValue = Integer.parseInt(specifyPhoneHeight);

                int resultWidthValue = (int) Math.ceil(1.0 * targetPhoneWidthValue / referencePhoneWidthValue *
                        specifyPhoneWidthValue * 160 / targetPhoneDPIValue);
                int resultHeightValue = (int) Math.ceil(1.0 * targetPhoneHeightValue / referencePhoneHeightValue *
                        specifyPhoneHeightValue * 160 / targetPhoneDPIValue);
                int smallWitdh = targetPhoneWidthValue > targetPhoneHeightValue ? targetPhoneHeightValue :
                        targetPhoneWidthValue;
                int sw = (int) Math.ceil(smallWitdh * 160.0 / targetPhoneDPIValue);

                tv_result_width.setText("结果宽: "+resultWidthValue + "dp");
                tv_result_height.setText("结果长: "+resultHeightValue + "dp");
                tv_sw.setText("结果放置位置: values-sw" + sw + "dp/dimens.xml");
            }
        });
    }

    private void initView() {
        et_width = (EditText) findViewById(R.id.et_width);
        et_height = (EditText) findViewById(R.id.et_height);
        et_dpi = (EditText) findViewById(R.id.et_dpi);
        et_width_reference = (EditText) findViewById(R.id.et_width_reference);
        et_height_reference = (EditText) findViewById(R.id.et_height_reference);
        et_width_specify = (EditText) findViewById(R.id.et_width_specify);
        et_height_specify = (EditText) findViewById(R.id.et_height_specify);
        tv_sw = (TextView) findViewById(R.id.tv_sw);
        tv_result_width = (TextView) findViewById(R.id.tv_result_width);
        tv_result_height = (TextView) findViewById(R.id.tv_result_height);
        btn_ok = (Button) findViewById(R.id.btn_ok);
    }
}
