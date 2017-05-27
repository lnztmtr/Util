package com.example.administrator.screensolutionconvert;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String KEY_HIGH_PRECISION = "high_precision";
    private static final String KEY_SAVED_TARGET_WIDTH = "saved_target_width";
    private static final String KEY_SAVED_TARGET_HEIGHT = "saved_target_height";
    private static final String KEY_SAVED_TARGET_SIZE = "saved_target_size";
    private static final String KEY_SAVED_REFERENCE_WIDTH = "saved_reference_width";
    private static final String KEY_SAVED_REFERENCE_HEIGHT = "saved_reference_height";

    private EditText et_width;
    private EditText et_height;
    private EditText et_precision;
    private EditText et_width_reference;
    private EditText et_height_reference;
    private EditText et_width_specify;
    private EditText et_height_specify;
    private TextView tv_sw;
    private TextView tv_result_width;
    private TextView tv_unit;
    private TextView tv_result_height;
    private TextView tv_min_screen_width;
    private Button btn_ok;
    private Button btn_min_screen_width;
    private CheckBox cbHighPrecision;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private boolean isHighPrecision;
    private String savedTargetWidth;
    private String savedTargetHeight;
    private String savedTargetSize;
    private String savedReferenceWidth;
    private String savedReferenceHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();
        setListener();
    }

    private void initView() {
        et_width = (EditText) findViewById(R.id.et_width);
        et_height = (EditText) findViewById(R.id.et_height);
        et_precision = (EditText) findViewById(R.id.et_precision);
        et_width_reference = (EditText) findViewById(R.id.et_width_reference);
        et_height_reference = (EditText) findViewById(R.id.et_height_reference);
        et_width_specify = (EditText) findViewById(R.id.et_width_specify);
        et_height_specify = (EditText) findViewById(R.id.et_height_specify);
        tv_sw = (TextView) findViewById(R.id.tv_sw);
        tv_min_screen_width = (TextView) findViewById(R.id.tv_min_screen_width);
        tv_result_width = (TextView) findViewById(R.id.tv_result_width);
        tv_result_height = (TextView) findViewById(R.id.tv_result_height);
        tv_unit = (TextView) findViewById(R.id.tv_unit);
        btn_ok = (Button) findViewById(R.id.btn_ok);
        btn_min_screen_width = (Button) findViewById(R.id.btn_min_screen_width);
        cbHighPrecision = (CheckBox) findViewById(R.id.cb_high_precision);
    }

    private void initData() {
        sharedPreferences = getSharedPreferences("screen", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        isHighPrecision = sharedPreferences.getBoolean(KEY_HIGH_PRECISION, false);
        cbHighPrecision.setChecked(isHighPrecision);
        if (isHighPrecision) {
            et_precision.setHint(getString(R.string.phone_inch));
            tv_unit.setText(getString(R.string.phone_unit_inch));
        } else {
            et_precision.setHint(getString(R.string.phone_dpi));
            tv_unit.setText(getString(R.string.phone_unit_dpi));
        }
        savedTargetWidth = sharedPreferences.getString(KEY_SAVED_TARGET_WIDTH, "");
        savedTargetHeight = sharedPreferences.getString(KEY_SAVED_TARGET_HEIGHT, "");
        savedTargetSize = sharedPreferences.getString(KEY_SAVED_TARGET_SIZE, "");
        savedReferenceWidth = sharedPreferences.getString(KEY_SAVED_REFERENCE_WIDTH, "");
        savedReferenceHeight = sharedPreferences.getString(KEY_SAVED_REFERENCE_HEIGHT, "");
        if (!TextUtils.isEmpty(savedTargetWidth)) {
            et_width.setText(savedTargetWidth);
        }
        if (!TextUtils.isEmpty(savedTargetHeight)) {
            et_height.setText(savedTargetHeight);
        }
        if (!TextUtils.isEmpty(savedTargetSize)) {
            et_precision.setText(savedTargetSize);
        }
        if (!TextUtils.isEmpty(savedReferenceWidth)) {
            et_width_reference.setText(savedReferenceWidth);
        }
        if (!TextUtils.isEmpty(savedReferenceHeight)) {
            et_height_reference.setText(savedReferenceHeight);
        }
    }

    private void setListener() {
        setTargetPhoneInputChangeListener(et_width);
        setTargetPhoneInputChangeListener(et_height);
        setTargetPhoneInputChangeListener(et_precision);
        setUIInputChangeListener(et_width_reference);
        setUIInputChangeListener(et_height_reference);
        setUIInputChangeListener(et_width_specify);
        setUIInputChangeListener(et_height_specify);
        cbHighPrecision.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                et_precision.setText("");
                et_precision.requestFocus();
                tv_min_screen_width.setText("");
                tv_result_width.setVisibility(View.GONE);
                tv_result_height.setVisibility(View.GONE);
                tv_sw.setVisibility(View.GONE);
                if (isChecked) {
                    tv_unit.setText(getString(R.string.phone_unit_inch));
                    et_precision.setHint(getString(R.string.phone_inch));
                } else {
                    tv_unit.setText(getString(R.string.phone_unit_dpi));
                    et_precision.setHint(getString(R.string.phone_dpi));
                }
            }
        });
        btn_min_screen_width.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String targetPhoneWidth = et_width.getText().toString().trim();
                if (TextUtils.isEmpty(targetPhoneWidth)) {
                    et_width.requestFocus();
                    Toast.makeText(MainActivity.this, "请输入需适配手机宽度", Toast.LENGTH_LONG).show();
                    return;
                }
                int targetPhoneWidthValue = Integer.parseInt(targetPhoneWidth);

                String targetPhoneHeight = et_height.getText().toString().trim();
                if (TextUtils.isEmpty(targetPhoneHeight)) {
                    et_height.requestFocus();
                    Toast.makeText(MainActivity.this, "请输入需适配手机高度", Toast.LENGTH_LONG).show();
                    return;
                }
                int targetPhoneHeightValue = Integer.parseInt(targetPhoneHeight);

                String targetPhoneDPI = et_precision.getText().toString().trim();
                if (TextUtils.isEmpty(targetPhoneDPI)) {
                    et_precision.requestFocus();
                    Toast.makeText(MainActivity.this, "请输入需适配手机尺寸", Toast.LENGTH_LONG).show();
                    return;
                }
                float targetPhoneSizeValue = Float.parseFloat(targetPhoneDPI);

                if (cbHighPrecision.isChecked() && targetPhoneSizeValue > 160) {
                    et_precision.requestFocus();
                    Toast.makeText(MainActivity.this, "屏幕尺寸不能超过160inch", Toast.LENGTH_LONG).show();
                    return;
                }
                int smallWitdh = targetPhoneWidthValue > targetPhoneHeightValue ? targetPhoneHeightValue :
                        targetPhoneWidthValue;
                ;
                int sw;
                if (cbHighPrecision.isChecked()) {
                    float exactDpi = (float) ((Math.sqrt(targetPhoneWidthValue * targetPhoneWidthValue +
                            targetPhoneHeightValue * targetPhoneHeightValue)) / targetPhoneSizeValue);
                    sw = (int) Math.ceil(smallWitdh * 160.0 / exactDpi);
                } else {
                    sw = (int) Math.ceil(smallWitdh * 160.0 / targetPhoneSizeValue);
                }
                tv_min_screen_width.setText(sw + "dp");
            }
        });
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String targetPhoneWidth = et_width.getText().toString().trim();
                if (TextUtils.isEmpty(targetPhoneWidth)) {
                    et_width.requestFocus();
                    Toast.makeText(MainActivity.this, "请输入需适配手机宽度", Toast.LENGTH_LONG).show();
                    return;
                }
                int targetPhoneWidthValue = Integer.parseInt(targetPhoneWidth);

                String targetPhoneHeight = et_height.getText().toString().trim();
                if (TextUtils.isEmpty(targetPhoneHeight)) {
                    et_height.requestFocus();
                    Toast.makeText(MainActivity.this, "请输入需适配手机高度", Toast.LENGTH_LONG).show();
                    return;
                }
                int targetPhoneHeightValue = Integer.parseInt(targetPhoneHeight);

                String targetPhoneDPI = et_precision.getText().toString().trim();
                if (TextUtils.isEmpty(targetPhoneDPI)) {
                    et_precision.requestFocus();
                    Toast.makeText(MainActivity.this, "请输入需适配手机尺寸", Toast.LENGTH_LONG).show();
                    return;
                }
                float targetPhoneSizeValue = Float.parseFloat(targetPhoneDPI);

                if (cbHighPrecision.isChecked() && targetPhoneSizeValue > 160) {
                    et_precision.requestFocus();
                    Toast.makeText(MainActivity.this, "屏幕尺寸不能超过160inch", Toast.LENGTH_LONG).show();
                    return;
                }


                String referencePhoneWidth = et_width_reference.getText().toString().trim();
                if (TextUtils.isEmpty(referencePhoneWidth)) {
                    et_width_reference.requestFocus();
                    Toast.makeText(MainActivity.this, "请输入美工提供设计图宽度", Toast.LENGTH_LONG).show();
                    return;
                }
                int referencePhoneWidthValue = Integer.parseInt(referencePhoneWidth);

                String referencePhoneHeight = et_height_reference.getText().toString().trim();
                if (TextUtils.isEmpty(referencePhoneHeight)) {
                    et_height_reference.requestFocus();
                    Toast.makeText(MainActivity.this, "请输入美工提供设计图高度", Toast.LENGTH_LONG).show();
                    return;
                }
                int referencePhoneHeightValue = Integer.parseInt(referencePhoneHeight);

                int specifyPhoneWidthValue = 0;
                String specifyPhoneWidth = et_width_specify.getText().toString().trim();
                if (!TextUtils.isEmpty(specifyPhoneWidth)) {
                    specifyPhoneWidthValue = Integer.parseInt(specifyPhoneWidth);
                }
                int specifyPhoneHeightValue = 0;
                String specifyPhoneHeight = et_height_specify.getText().toString().trim();
                if (!TextUtils.isEmpty(specifyPhoneHeight)) {
                    specifyPhoneHeightValue = Integer.parseInt(specifyPhoneHeight);
                }

                int resultWidthValue;
                int resultHeightValue;
                int smallWitdh;
                int sw;
                if (cbHighPrecision.isChecked()) {
                    float exactDpi = (float) ((Math.sqrt(targetPhoneWidthValue * targetPhoneWidthValue +
                            targetPhoneHeightValue * targetPhoneHeightValue)) / targetPhoneSizeValue);
                    resultWidthValue = (int) Math.ceil(1.0 * targetPhoneWidthValue /
                            referencePhoneWidthValue *
                            specifyPhoneWidthValue * 160 / exactDpi);
                    resultHeightValue = (int) Math.ceil(1.0 * targetPhoneHeightValue /
                            referencePhoneHeightValue *
                            specifyPhoneHeightValue * 160 / exactDpi);
                    smallWitdh = targetPhoneWidthValue > targetPhoneHeightValue ? targetPhoneHeightValue :
                            targetPhoneWidthValue;
                    sw = (int) Math.ceil(smallWitdh * 160.0 / exactDpi);

                } else {
                    resultWidthValue = (int) Math.ceil(1.0 * targetPhoneWidthValue /
                            referencePhoneWidthValue *
                            specifyPhoneWidthValue * 160 / targetPhoneSizeValue);
                    resultHeightValue = (int) Math.ceil(1.0 * targetPhoneHeightValue /
                            referencePhoneHeightValue *
                            specifyPhoneHeightValue * 160 / targetPhoneSizeValue);
                    smallWitdh = targetPhoneWidthValue > targetPhoneHeightValue ? targetPhoneHeightValue :
                            targetPhoneWidthValue;
                    sw = (int) Math.ceil(smallWitdh * 160.0 / targetPhoneSizeValue);
                }

                tv_result_width.setVisibility(View.VISIBLE);
                tv_result_height.setVisibility(View.VISIBLE);
                tv_sw.setVisibility(View.VISIBLE);

                tv_result_width.setText("结果宽: " + resultWidthValue + "dp");
                tv_result_height.setText("结果长: " + resultHeightValue + "dp");
                tv_sw.setText("结果放置位置: values-sw" + sw + "dp/dimens.xml");
                editor.putString(KEY_SAVED_TARGET_WIDTH, String.valueOf(targetPhoneWidthValue));
                editor.putString(KEY_SAVED_TARGET_HEIGHT, String.valueOf(targetPhoneHeightValue));
                editor.putString(KEY_SAVED_TARGET_SIZE, String.valueOf(targetPhoneSizeValue));
                editor.putString(KEY_SAVED_REFERENCE_WIDTH, String.valueOf(referencePhoneWidthValue));
                editor.putString(KEY_SAVED_REFERENCE_HEIGHT, String.valueOf(referencePhoneHeightValue));
                editor.putBoolean(KEY_HIGH_PRECISION, cbHighPrecision.isChecked());
                editor.commit();
            }
        });
    }

    private void setUIInputChangeListener(EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tv_result_width.setVisibility(View.GONE);
                tv_result_height.setVisibility(View.GONE);
                tv_sw.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void setTargetPhoneInputChangeListener(EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tv_result_width.setVisibility(View.GONE);
                tv_result_height.setVisibility(View.GONE);
                tv_sw.setVisibility(View.GONE);
                tv_min_screen_width.setText("");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

}
