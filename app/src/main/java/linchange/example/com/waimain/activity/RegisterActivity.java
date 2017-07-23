package linchange.example.com.waimain.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import linchange.example.com.waimain.MainActivity;
import linchange.example.com.waimain.R;
import linchange.example.com.waimain.utils.ShareUtils;

public class RegisterActivity extends Activity {

    private EditText etUsername; //用户名输入框
    private EditText etPassword; //密码输入框
    private EditText etPassAgain; //第二次密码输入框
    private TextView tvNameWrong; //用户名错误提示文字
    private TextView tvPssWrong; //密码错误提示文字
    private TextView tvPssNotMatch; //密码错误提示文字
    private Button btnRegister; //注册按钮
    private Button btnLogin; //登录按钮

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initViews(); //初始化页面控件
        initEvents(); //初始化控件事件
    }

    //初始化控件事件
    private void initEvents() {
        //给登陆按钮设置点击事件
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到登陆界面
                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                RegisterActivity.this.finish(); //结束当前界面
            }
        });

        //给登陆按钮设置点击事件
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取用户名输入框中的内容
                String username = etUsername.getText().toString();
                //获取密码输入框中的内容
                String password = etPassword.getText().toString();
                //获取再次输入密码输入框中的内容
                String passAgain = etPassAgain.getText().toString();

                //判断用户名和密码是否正确
                if (!TextUtils.isEmpty(username)
                        && !TextUtils.isEmpty(password)
                        && !TextUtils.isEmpty(passAgain)
                        && password.equals(passAgain)) { //用户名和密码都不为空且两次密码相同

                    hideNameAndPassError(); //隐藏用户名和密码错误提示
                    tryToRegister(username, password); //尝试登陆

                } else { //用户名或密码为空或两次密码不同
                    showNameOrPassError(username, password, passAgain); //显示用户名或密码错误文字提示
                }
            }
        });
    }

    //隐藏用户名和密码错误提示
    private void hideNameAndPassError() {
        tvNameWrong.setVisibility(View.INVISIBLE); //隐藏用户名不能为空提示文字
        tvPssWrong.setVisibility(View.INVISIBLE); //隐藏不能为空提示文字
        tvPssNotMatch.setVisibility(View.INVISIBLE); //隐藏两次密码不一致提示文字
    }

    //尝试登陆
    private void tryToRegister(String username, String password) {
        //获取数据库中该用户名对应的密码
        String realPassword = ShareUtils.getString(this, username, "");

        if ("".equals(realPassword)) { //数据库中没有该用户名的数据
            //保存用户名和密码到数据库中
            ShareUtils.putString(this, username, password);
            Toast.makeText(this, "成功注册用户：" + username, Toast.LENGTH_SHORT).show();
            //跳转到商品购买页面
            startActivity(new Intent(RegisterActivity.this, ShoppingActivity.class));
        } else { //该用户名已存在数据库中
            //提示用户名已存在
            Toast.makeText(this, "用户名已存在", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 显示用户名或密码错误文字提示
     * @param username 输入的用户名
     * @param password 输入的密码
     * @param passAgain 再次输入的密码
     */
    private void showNameOrPassError(String username, String password, String passAgain) {
        if (TextUtils.isEmpty(username)) { //用户名为空
            tvNameWrong.setVisibility(View.VISIBLE); //显示用户名错误提示文字
        } else { //用户名不为空
            tvNameWrong.setVisibility(View.INVISIBLE); //用户名错误提示文字消失
        }

        if (TextUtils.isEmpty(password)) { //密码为空
            tvPssWrong.setVisibility(View.VISIBLE); //显示密码错误提示文字
        } else { //密码不为空
            tvPssWrong.setVisibility(View.INVISIBLE); //密码错误提示文字消失
        }

        if (!password.equals(passAgain)) { //两次输入的密码不同
            tvPssNotMatch.setVisibility(View.VISIBLE); //显示两次密码输入不一致提示
        } else { //两次输入的密码相同
            tvPssNotMatch.setVisibility(View.INVISIBLE); //两次密码输入不一致提示消失
        }
    }

    //初始化页面控件
    private void initViews() {
        etUsername = (EditText) findViewById(R.id.et_username);
        etPassword = (EditText) findViewById(R.id.et_password);
        etPassAgain = (EditText) findViewById(R.id.et_pass_again);
        tvNameWrong = (TextView) findViewById(R.id.tv_name_wrong);
        tvPssWrong = (TextView) findViewById(R.id.tv_pass_wrong);
        tvPssNotMatch = (TextView) findViewById(R.id.tv_pass_not_match);
        btnRegister = (Button) findViewById(R.id.btn_register);
        btnLogin = (Button) findViewById(R.id.btn_login);
    }
}
