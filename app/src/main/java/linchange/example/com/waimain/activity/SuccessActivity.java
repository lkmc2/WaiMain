package linchange.example.com.waimain.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import linchange.example.com.waimain.R;
import linchange.example.com.waimain.entity.Product;

public class SuccessActivity extends Activity {

    private TextView tvResult; //订单信息显示文字控件
    private Button btnContinueBuy; //继续购物按钮
    private Button btnCancelOrder; //取消订单按钮

    private ArrayList<Product> selectedProducts = new ArrayList<Product>(); //被选择的的商品数据

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);

        initViews(); //初始化页面控件
        initDatas(); //初始化页面数据
        initEvents(); //初始化控件事件
    }

    //初始化控件事件
    private void initEvents() {
        //给继续购物按钮设置点击事件
        btnContinueBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到购物界面
                startActivity(new Intent(getApplicationContext(), ShoppingActivity.class));
                SuccessActivity.this.finish(); //结束当前界面
            }
        });

        //给取消订单按钮设置点击事件
        btnCancelOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //提示订单取消成功
                Toast.makeText(SuccessActivity.this, "订单取消成功", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //初始化页面数据
    private void initDatas() {
        Intent otherIntent = getIntent(); //获取信息输入界面传来的意图
        selectedProducts = otherIntent.getParcelableArrayListExtra("selectedProducts"); //从意图中获取被选择的的商品数据

        String phone = otherIntent.getStringExtra("phone"); //从意图获取手机号
        String address = otherIntent.getStringExtra("address"); //从意图获取送货地址

        StringBuilder orderResult = new StringBuilder(); //新建订单结果信息
        orderResult.append("订单成功提交！\n"); //添加文本内容
        orderResult.append("商品将于25分钟内送达。\n\n");

        orderResult.append("订单信息如下\n");
        orderResult.append("商品名\t\t\t\t数量\t\t\t\t价格\n");

        double totalPrice = 0; //购买商品总价
        for (int i = 0; i < selectedProducts.size(); i++) { //遍历商品列表并往文本写入商品信息
            Product product = selectedProducts.get(i);
            orderResult.append(product.getName() + "\t\t\t" + product.getSelectedCount() + "\t\t\t\t\t" + product.getPrice() * product.getSelectedCount() + "\n");

            totalPrice += product.getPrice() * product.getSelectedCount(); //设置商品总价
        }
        orderResult.append("\n共计：" + totalPrice + "元\n\n");

        orderResult.append("联系电话：" + phone + "\n");
        orderResult.append("送货地址：" + address);

        tvResult.setText(orderResult); //将订单结果信息设置到订单信息显示文字控件
    }

    //初始化页面控件
    private void initViews() {
        tvResult = (TextView) findViewById(R.id.tv_result);
        btnContinueBuy = (Button) findViewById(R.id.btn_continue_buy);
        btnCancelOrder = (Button) findViewById(R.id.btn_cancel_order);
    }
}
