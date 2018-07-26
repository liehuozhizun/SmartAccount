package com.demo.account.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.demo.account.R;
import com.demo.account.db.dao.UserEntity;
import com.demo.account.db.dao.helper.UserDaoHelper;
import com.demo.account.event.UserLoginChangeEvent;
import com.demo.account.storage.BaseParamNames;
import com.demo.account.storage.SharedPreferencesHelper;
import com.demo.account.util.Toasty;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportFragment;

public class MyFragment extends SupportFragment {


    @BindView(R.id.tv_username)
    TextView tvUsername;
    @BindView(R.id.btn_logout)
    Button btnLogout;
    @BindView(R.id.content_my)
    LinearLayout contentMy;
    @BindView(R.id.et_login_user_name)
    EditText etLoginUserName;
    @BindView(R.id.et_login_password)
    EditText etLoginPassword;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.btn_login_sign_up)
    Button btnLoginSignUp;
    @BindView(R.id.content_login)
    LinearLayout contentLogin;
    @BindView(R.id.et_register_user_name)
    EditText etRegisterUserName;
    @BindView(R.id.et_register_password)
    EditText etRegisterPassword;
    @BindView(R.id.btn_register_sign_up)
    Button btnRegisterSignUp;
    @BindView(R.id.content_register)
    LinearLayout contentRegister;
    Unbinder unbinder;

    private UserDaoHelper userDaoHelper = new UserDaoHelper();

    public static MyFragment getInstance() {
        return new MyFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btn_logout)
    public void onLogoutClicked() {
        SharedPreferencesHelper.getInstance().putString(BaseParamNames.USER_NAME, "");
        showLoginContent();
        postLoginChangeEvent(false);
    }

    @OnClick(R.id.btn_login)
    public void onLoginClicked(){
        String userName = this.etLoginUserName.getText().toString();
        String password = this.etLoginPassword.getText().toString();

        if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(password)) {
            Toasty.showShort(getContext(), "username or password must not null!");
            return;
        }

        UserEntity user = this.userDaoHelper.getUserOfName(userName);
        if (user == null){
            Toasty.showShort(getContext(), "username is unregistered!");
            return;
        }

        String psw = user.getPsw();
        if (!TextUtils.equals(psw, password)) {
            Toasty.showShort(getContext(), "Wrong password, please re-enter.");
            return;
        }

        this.etLoginUserName.setText("");
        this.etLoginPassword.setText("");

        SharedPreferencesHelper.getInstance().putString(BaseParamNames.USER_NAME, userName);
        showMyContent(userName);

        postLoginChangeEvent(true);
    }

    @OnClick(R.id.btn_login_sign_up)
    public void onLoginSignUpClicked(){
        showRegisterContent();
    }

    @OnClick(R.id.btn_register_sign_up)
    public void onSignUpClicked(){
        String userName = this.etRegisterUserName.getText().toString();
        String password = this.etRegisterPassword.getText().toString();

        if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(password)) {
            Toasty.showShort(getContext(), "username or password must not null!");
            return;
        }

        UserEntity user = this.userDaoHelper.getUserOfName(userName);
        if (user != null){
            Toasty.showShort(getContext(), "username is registered!");
            return;
        }

        UserEntity newUser = new UserEntity();
        newUser.setName(userName);
        newUser.setPsw(password);
        this.userDaoHelper.addUser(newUser);

        this.etRegisterUserName.setText("");
        this.etRegisterPassword.setText("");

        SharedPreferencesHelper.getInstance().putString(BaseParamNames.USER_NAME, userName);

        showMyContent(userName);

        postLoginChangeEvent(true);
    }

    @OnClick(R.id.btn_register_login)
    public void onRegisterLoginClicked(){
        this.etRegisterUserName.setText("");
        this.etRegisterPassword.setText("");
        showLoginContent();
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        String username = SharedPreferencesHelper.getInstance()
                .getString(BaseParamNames.USER_NAME, "");
        if (TextUtils.isEmpty(username)) {
            showLoginContent();
            return;
        }

        showMyContent(username);

    }


    private void showMyContent(String username){
        this.contentMy.setVisibility(View.VISIBLE);
        this.contentLogin.setVisibility(View.GONE);
        this.contentRegister.setVisibility(View.GONE);

        this.tvUsername.setText(username);
    }

    private void showRegisterContent(){
        this.contentMy.setVisibility(View.GONE);
        this.contentLogin.setVisibility(View.GONE);
        this.contentRegister.setVisibility(View.VISIBLE);
    }

    private void showLoginContent(){
        this.contentMy.setVisibility(View.GONE);
        this.contentLogin.setVisibility(View.VISIBLE);
        this.contentRegister.setVisibility(View.GONE);
    }


    private void postLoginChangeEvent(boolean login){
        EventBus.getDefault().post(new UserLoginChangeEvent(login));
    }

}
