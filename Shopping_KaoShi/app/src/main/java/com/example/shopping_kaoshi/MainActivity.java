package com.example.shopping_kaoshi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopping_kaoshi.adapter.Item_Recy_Adapter;
import com.example.shopping_kaoshi.bean.Item_Bean;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, Item_Recy_Adapter.CheckInterface, Item_Recy_Adapter.ModifyCountInterface {

    private static final String TAG = "MainActivity";
    /**
     * 清空
     */
    private Button mBtnClear;
    /**
     * 删除
     */
    private Button mBtnDelete;
    private RecyclerView mRecyId;
    /**
     * 全选
     */
    private CheckBox mCheckAll;
    /**
     * 总金额：
     */
    private TextView mContentParce;
    /**
     * 0.0
     */
    private TextView mPrace;
    /**
     * 结算
     */
    private Button mBtnAccount;
    private ArrayList<Item_Bean> beans = new ArrayList<>();
    private Item_Recy_Adapter mAdapter;
    private double price;
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initData() {
        for (int i = 0; i < 10; i++) {
            Item_Bean bean = new Item_Bean(i,"雪碧"+i,3+i,0);
            beans.add(bean);
        }
        mRecyId.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        mAdapter = new Item_Recy_Adapter(MainActivity.this,beans);
        mAdapter.notifyDataSetChanged();
        mRecyId.setAdapter(mAdapter);

        mAdapter.setCheckInterface(this);
        mAdapter.setModifyCountInterface(this);
    }

    private void initView() {
        mBtnClear = (Button) findViewById(R.id.btn_clear);
        mBtnClear.setOnClickListener(this);
        mBtnDelete = (Button) findViewById(R.id.btn_delete);
        mBtnDelete.setOnClickListener(this);
        mRecyId = (RecyclerView) findViewById(R.id.recy_id);
        mCheckAll = (CheckBox) findViewById(R.id.check_All);
        mCheckAll.setOnClickListener(this);
        mContentParce = (TextView) findViewById(R.id.contentParce);
        mPrace = (TextView) findViewById(R.id.prace);
        mBtnAccount = (Button) findViewById(R.id.btn_account);
        mBtnAccount.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.btn_clear:
                for (int i = 0; i < beans.size(); i++) {
                    Item_Bean item_bean = beans.get(i);
                    if (item_bean.isIstrue()){
                        item_bean.setIstrue(false);
                    }
                }
                mCheckAll.setChecked(false);
                mPrace.setText(0.0+"");
                mAdapter.notifyDataSetChanged();
                break;
            case R.id.btn_delete:
                for (int i = 0; i < beans.size(); i++) {
                    Item_Bean item_bean = beans.get(i);
                    if (item_bean.isIstrue()){
                        mAdapter.mBeans.remove(i);
                    }
                }
                mPrace.setText(0.0+"");
                mAdapter.notifyDataSetChanged();
                break;
            case R.id.check_All:
                isCheckAll();
                break;
            case R.id.btn_account:
                Toast.makeText(this, "总共有"+count+"种商品"+"\n"+"共"+price+"元", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void isCheckAll() {
        if (beans.size()!=0){
            if (mCheckAll.isChecked()){
                for (int i = 0; i < beans.size(); i++) {
                    Item_Bean item_bean = beans.get(i);
                    item_bean.setIstrue(true);
                }
                mAdapter.notifyDataSetChanged();
            }else {
                for (int i = 0; i < beans.size(); i++) {
                    Item_Bean item_bean = beans.get(i);
                    item_bean.setIstrue(false);
                }
                mAdapter.notifyDataSetChanged();
            }
            strCount();
        }
    }

    private void strCount() {
        price = 0.0;
        count = 0;
        //遍历集合中所有对象
        for (int i = 0; i < beans.size(); i++) {
            //拿到集合中的单个对象
            Item_Bean item_bean = beans.get(i);
            //判断这个对象是否选中状态
            if (item_bean.isIstrue()){
                count ++;
                //计算价钱
                price += item_bean.getPrice() * item_bean.getCount();
            }
        }
        //设置到控件上
        mPrace.setText(price+"");
    }

    @Override
    public void doIncrease(int position, View showCountView, boolean isChecked) {
        //点击加号时，先获取点击的自条目，然后获得他的个数
        Item_Bean item_bean = beans.get(position);
        int count = item_bean.getCount();
        //点一次  加一次个数
        count ++;
        //把新的个数设置到集合中
        item_bean.setCount(count);
        //显示到页面的个数
        ((TextView)showCountView).setText(count+"");
        //刷新
        mAdapter.notifyDataSetChanged();
        //及时计算价钱
        strCount();
    }



    @Override
    public void doDecrease(int position, View showCountView, boolean isChecked) {
        //点击减号时，获取相对应的自条目
        Item_Bean item_bean = beans.get(position);
        //获取该自条目的个数
        int count = item_bean.getCount();
        //如果减到的时候  为最小数，不能减了  所有return
        if (count ==0){
            return;
        }
        //如果没有到0  就能减减
        count --;
        //设置到集合中去
        item_bean.setCount(count);
        //显示到页面
        ((TextView)showCountView).setText(count+"");
        //刷新适配器  及时更新页面
        mAdapter.notifyDataSetChanged();
        //计算现在的价钱
        strCount();
    }

    @Override
    public void checkGroup(int position, boolean isChecked) {
        beans.get(position).setIstrue(isChecked);
        if (isAll()){
            mCheckAll.setChecked(true);
        }else {
            mCheckAll.setChecked(false);
        }
        mAdapter.notifyDataSetChanged();
        strCount();
    }

    private boolean isAll() {
        for (Item_Bean be : beans) {
            if (!be.isIstrue()){
                return false;
            }
        }
        return true;
    }
}
