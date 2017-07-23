package linchange.example.com.waimain.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import linchange.example.com.waimain.R;
import linchange.example.com.waimain.adapter.ProductListAdapter;
import linchange.example.com.waimain.entity.Product;

public class ShoppingActivity extends Activity {
    public static ShoppingActivity mInstance = null; //当前对象的实例本身

    private ListView lvProduct; //商品展示列表控件

    private TextView tvSelected; //已选择商品数量提示文字
    private TextView tvTotal; //已选择商品总价提示文字

    private Button btnTakeOrder; //下单按钮

    private Button btnSortByNew; //根据新品排序
    private Button btnSortBySale; //根据销量排序
    private Button btnSortByPrice; //根据价格排序
    private Button btnSortByAll; //根据综合情况排序

    private List<Product> productsData = new ArrayList<Product>(); //所有的商品数据
    private ArrayList<Product> selectedProducts = new ArrayList<Product>(); //被选择的的商品数据

    private ProductListAdapter productListAdapter; //商品列表适配器

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);

        initViews(); //初始化页面控件
        initDatas(); //初始化数据
        initEvents(); //初始化控件事件
    }

    //初始化控件事件
    private void initEvents() {
        mInstance = this; //设置实例等于当前对象本身

        //将数据源放入适配器中
        productListAdapter = new ProductListAdapter(this, productsData);
        //给商品展示列表控件设置顶部分隔线
        lvProduct.addHeaderView(new View(this));
        //给商品展示列表控件设置适配器
        lvProduct.setAdapter(productListAdapter);

        //给下单按钮添加点击事件
        btnTakeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedProducts.size() != 0) { //已选择商品数据不为空
                    Intent intent = new Intent(getApplicationContext(), InputActivity.class); //新建意图
                    intent.putParcelableArrayListExtra("selectedProducts", selectedProducts); //设置已选择的商品数据
                    startActivity(intent); //启动信息输入界面
                } else { //已选择商品数据为空
                    Toast.makeText(ShoppingActivity.this, "购物车为空，请选择商品", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //给按新品排序按钮设置点击事件
        btnSortByNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //点击事件
                Collections.sort(productsData, new Comparator<Product>() { //对商品数据进行排序
                    @Override
                    public int compare(Product product1, Product product2) { //比较商品
                        //如果本商品被选择次数大于第二个商品
                        if (product1.getSelectedCount() > product2.getSelectedCount()) {
                            return 1; //返回正数代表本商品大于第二个商品
                        }
                        return -1; //返回负数代表本商品小于第二个商品
                    }
                });
                productListAdapter.notifyDataSetChanged(); //通知系统数据改变
            }
        });

        //给按售价排序按钮设置点击事件
        btnSortBySale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //点击事件
                Collections.sort(productsData, new Comparator<Product>() { //对商品数据进行排序
                    @Override
                    public int compare(Product product1, Product product2) { //比较商品
                        //如果本商品的销量小于第二个商品
                        if (product1.getSale() < product2.getSale()) {
                            return 1; //返回正数代表本商品大于第二个商品
                        }
                        return -1; //返回负数代表本商品小于第二个商品
                    }
                });
                productListAdapter.notifyDataSetChanged(); //通知系统数据改变
            }
        });

        //给按价格排序按钮设置点击事件
        btnSortByPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //点击事件
                Collections.sort(productsData, new Comparator<Product>() { //对商品数据进行排序
                    @Override
                    public int compare(Product product1, Product product2) { //比较商品
                        //如果本商品的价格大于第二个商品
                        if (product1.getPrice() > product2.getSale()) {
                            return 1; //返回正数代表本商品大于第二个商品
                        }
                        return -1; //返回负数代表本商品小于第二个商品
                    }
                });
                productListAdapter.notifyDataSetChanged(); //通知系统数据改变
            }
        });

        //给按综合排序按钮设置点击事件
        btnSortByAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //点击事件
                Collections.sort(productsData, new Comparator<Product>() { //对商品数据进行排序
                    @Override
                    public int compare(Product product1, Product product2) { //比较商品
                        //如果本商品的价格大于第二个商品
                        if (product1.getId()> product2.getId()) {
                            return 1; //返回正数代表本商品大于第二个商品
                        }
                        return -1; //返回负数代表本商品小于第二个商品
                    }
                });
                productListAdapter.notifyDataSetChanged(); //通知系统数据改变
            }
        });
    }

    //设置被选择商品数量与价格
    public void setSelectedCountAndPrice(int selectedCount, int totalPrice) {
        tvSelected.setText(String.valueOf(selectedCount)); //在界面上设置已选择商品数量的信息
        tvTotal.setText(String.valueOf(totalPrice)); //在界面上设置已选择商品总价的信息

        if (selectedCount == 0 && totalPrice == 0) { //当被选择商品数量和总价都为0时
            for (Product product : productsData) { //遍历商品列表
                product.clearZero(); //将每一项商品的选择次数归零与不显示减去按钮
            }

            selectedProducts.clear(); //清空已选择商品列表
            productListAdapter.notifyDataSetChanged(); //通知系统数据已改变
        }
    }

    //设置被选择的商品数据
    public void setSelectedProducts(ArrayList<Product> data) {
        this.selectedProducts = data; //获取被选择商品数据
        productListAdapter.setSelectedProducts(data); //设置被选择商品数据到商品适配器
    }

    //初始化数据
    private void initDatas() {
        Product product = new Product(0, "西式全餐",28, R.drawable.pic_product_01, 220, "食遇", "套餐包括：帕尼尼面包 + 猪柳饼 + 培根片 + 太阳蛋", false);
        productsData.add(product);
        product = new Product(1, "中式全餐",18, R.drawable.pic_product_02, 340,"食遇", "套餐包括：炒粉、饮料、青菜各一份", false);
        productsData.add(product);
        product = new Product(2,"三菇春卷",10, R.drawable.pic_product_03, 572, "倾心工坊", "套餐包括：三姑春卷2根", false);
        productsData.add(product);
        product = new Product(3,"葡式蛋挞",10, R.drawable.pic_product_04, 678, "倾心工坊", "套餐包括：葡式蛋挞2个", false);
        productsData.add(product);
        product = new Product(4, "炸鸡套餐",32, R.drawable.pic_product_05, 441, "好味基", "套餐包括：原味鸡2块 + 薯条1包 + 可乐1杯", false);
        productsData.add(product);
        product = new Product(5,"咖喱鸡饭",22, R.drawable.pic_product_06, 233, "K记", "套餐包括：米饭1份 + 丝瓜 + 日式咖喱鸡", false);
        productsData.add(product);
        product = new Product(6, "烤鸡腿饭",20, R.drawable.pic_product_07, 128, "K记", "套餐包括：米饭1份 + 卤蛋 + 奥尔良烤鸡腿饭", false);
        productsData.add(product);
        product = new Product(7, "十二翅盒",20, R.drawable.pic_product_08, 214, "龙门食府", "套餐包括：神鸡翅4块 + 异域翅4块 + 醇香翅4块", false);
        productsData.add(product);
        product = new Product(8, "小吃拼盘",26, R.drawable.pic_product_09, 82, "龙门食府", "套餐包括：枫香烤翅4块 + 风味鸡翅4块", false);
        productsData.add(product);
        product = new Product(9, "枫香烤腿",18, R.drawable.pic_product_10, 185, "龙门食府", "套餐包括：北美特色枫香烤腿2个", false);
        productsData.add(product);
        product = new Product(10, "海鲜意面",23, R.drawable.pic_product_11, 86, "慕刻", "套餐包括：海鲜意面1份", false);
        productsData.add(product);
        product = new Product(11, "意式比萨",56, R.drawable.pic_product_12, 133, "慕刻", "套餐包括：意式培根卷大虾比萨1份", false);
        productsData.add(product);
        product = new Product(12, "辣海鲜饭",24, R.drawable.pic_product_13, 184, "简餐", "套餐包括：瑶柱辣海鲜饭1份", false);
        productsData.add(product);
        product = new Product(13, "香牛肉饭",20, R.drawable.pic_product_14, 266, "简餐", "套餐包括：川香牛肉饭1份", false);
        productsData.add(product);
        product = new Product(14, "鱼丸米线",23, R.drawable.pic_product_15, 452, "晴朗小庄", "套餐包括：港式鱼丸米线1份、煎蛋1个", false);
        productsData.add(product);
        product = new Product(15, "烧鸭米线",24, R.drawable.pic_product_16, 134, "晴朗小庄", "套餐包括：雪里红烧鸭米线1份、青菜1份", false);
        productsData.add(product);
    }

    //初始化页面控件
    private void initViews() {
        lvProduct = (ListView) findViewById(R.id.lv_product);
        tvSelected = (TextView) findViewById(R.id.tv_selected);
        tvTotal = (TextView) findViewById(R.id.tv_total);
        btnTakeOrder = (Button) findViewById(R.id.btn_take_order);
        btnSortByNew = (Button) findViewById(R.id.btn_sort_by_new);
        btnSortByPrice = (Button) findViewById(R.id.btn_sort_by_price);
        btnSortBySale = (Button) findViewById(R.id.btn_sort_by_sale);
        btnSortByAll = (Button) findViewById(R.id.btn_sort_by_all);
    }
}
