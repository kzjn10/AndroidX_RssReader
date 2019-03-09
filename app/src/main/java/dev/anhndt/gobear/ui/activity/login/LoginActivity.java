package dev.anhndt.gobear.ui.activity.login;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import androidx.appcompat.widget.SwitchCompat;
import dev.anhndt.gobear.R;
import dev.anhndt.gobear.ui.activity.base.BaseActivity;
import dev.anhndt.gobear.ui.activity.main.MainActivity;
import dev.anhndt.gobear.utils.IntentUtils;
import dev.anhndt.gobear.utils.SharedPrefUtils;

public class LoginActivity extends BaseActivity {


    private TextInputLayout mTilUserName;
    private TextInputLayout mTilPassword;
    private TextInputEditText mTetUserName;
    private TextInputEditText mTetPassword;
    private SwitchCompat mScRememberMe;
    private Button mBtnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gbp_activity_login);
        initViews();
        initListeners();
    }

    private void initViews() {
        mTilUserName = findViewById(R.id.gbp_la_til_user_name);
        mTilPassword = findViewById(R.id.gbp_la_til_password);
        mTetUserName = findViewById(R.id.gbp_la_tet_user_name);
        mTetPassword = findViewById(R.id.gbp_la_tet_password);
        mScRememberMe = findViewById(R.id.gbp_la_sc_remember_me);
        mBtnLogin = findViewById(R.id.gbp_la_btn_login);
    }

    private void initListeners() {
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processLogin();
            }
        });
    }

    private void processLogin() {
        do {
            //Validation data
            String currentUserName = mTetUserName.getText().toString().trim();
            String currentPassword = mTetPassword.getText().toString().trim();

            if (currentUserName.length() == 0) {
                mTilUserName.setError(getText(R.string.gbp_msg_user_name_empty));
                break;
            }

            if (!currentUserName.equals("GoBear")) {
                mTilUserName.setError(getText(R.string.gbp_msg_user_name_invalid));
                break;
            }

            mTilUserName.setError("");

            if (currentPassword.length() == 0) {
                mTilPassword.setError(getText(R.string.gbp_msg_pwd_empty));
                break;
            }

            if (!currentPassword.equals("GoBearDemo")) {
                mTilPassword.setError(getText(R.string.gbp_msg_pwd_invalid));
                break;
            }

            mTilPassword.setError("");

            //Check if need remember username & password
            boolean isRememberMe = mScRememberMe.isChecked();
            if (isRememberMe) {
                SharedPrefUtils.setRememberLogin(true);
            }

            IntentUtils.startActivity(LoginActivity.this, MainActivity.class);
            LoginActivity.this.finish();
        } while (false);

    }
}
