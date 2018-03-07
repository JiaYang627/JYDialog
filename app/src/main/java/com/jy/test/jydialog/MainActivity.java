package com.jy.test.jydialog;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.timmy.tdialog.TDialog;
import com.timmy.tdialog.base.BindViewHolder;
import com.timmy.tdialog.base.TBaseAdapter;
import com.timmy.tdialog.list.TListDialog;
import com.timmy.tdialog.listener.OnBindViewListener;
import com.timmy.tdialog.listener.OnViewClickListener;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.useTDialogButton)
    Button mUseTDialogButton;
    @BindView(R.id.upgradeDialogButton)
    Button mUpgradeDialogButton;
    @BindView(R.id.tipsDialogButton)
    Button mTipsDialogButton;
    @BindView(R.id.loadingDialogButton)
    Button mLoadingDialogButton;
    @BindView(R.id.dialogViewButton)
    Button mDialogViewButton;
    @BindView(R.id.homeAdviceDialogButton)
    Button mHomeAdviceDialogButton;
    @BindView(R.id.evaluateDialogButton)
    Button mEvaluateDialogButton;
    @BindView(R.id.updateHeadDialogButton)
    Button mUpdateHeadDialogButton;
    @BindView(R.id.listDialogButton)
    Button mListDialogButton;
    @BindView(R.id.bottomListDialogButton)
    Button mBottomListDialogButton;
    @BindView(R.id.shareDialogButton)
    Button mShareDialogButton;
    private Unbinder unbinder;
    private TDialog mTDialog;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mTDialog.dismiss();
        }
    };
    private String[] data = {"java", "android", "NDK", "c++", "python", "ios", "Go", "Unity3D", "Kotlin", "Swift", "js"};
    private String[] sharePlatform = {"微信", "朋友圈", "短信", "微博", "QQ空间", "Google", "FaceBook", "微信", "朋友圈", "短信", "微博", "QQ空间"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        mHandler.removeCallbacksAndMessages(null);
    }

    @OnClick({R.id.useTDialogButton, R.id.upgradeDialogButton, R.id.tipsDialogButton, R.id.loadingDialogButton, R.id.dialogViewButton, R.id.homeAdviceDialogButton, R.id.evaluateDialogButton, R.id.updateHeadDialogButton, R.id.listDialogButton, R.id.bottomListDialogButton, R.id.shareDialogButton})
    public void onViewClicked(final View view) {
        switch (view.getId()) {
            /**
             * Dialog基本使用
             */
            case R.id.useTDialogButton:
                new TDialog.Builder(getSupportFragmentManager())
                        .setLayoutRes(R.layout.dialog_click)                    //设置弹窗展示的xml布局
//                        .setWidth(600)                  //设置弹窗宽度(px)
//                        .setHeight(300)                 //设置弹窗高度(px)
                        .setScreenHeightAspect(MainActivity.this ,0.3f)         //设置弹窗高度(参数aspect为屏幕宽度比例 0 - 1f)
                        .setScreenWidthAspect(MainActivity.this , 0.8f)         //设置弹窗宽度(参数aspect为屏幕宽度比例 0 - 1f)
                        .setGravity(Gravity.CENTER)     //设置弹窗展示位置
                        .setTag("DialogTest")           //设置Tag
                        .setDimAmount(0.6f)             //设置弹窗背景透明度(0-1f)
                        .setCancelableOutside(true)     //弹窗在界面外是否可以点击取消
                        .setCancelable(true)            //弹窗是否可以取消
                        .setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                Toast.makeText(MainActivity.this,"弹窗消失回调",Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setOnBindViewListener(new OnBindViewListener() {       //通过BindViewHolder拿到控件对象,进行修改
                            @Override
                            public void bindView(BindViewHolder viewHolder) {
                                viewHolder.setText(R.id.tv_content, "abcdef");
                                viewHolder.setText(R.id.tv_title, "我是Title");
                            }
                        })
                        .addOnClickListener(R.id.btn_left, R.id.btn_right, R.id.tv_title)   //添加进行点击控件的id
                        .setOnViewClickListener(new OnViewClickListener() {     //View控件点击事件回调
                            @Override
                            public void onViewClick(BindViewHolder viewHolder, View view, TDialog tDialog) {
                                switch (view.getId()) {
                                    case R.id.btn_left:
                                        Toast.makeText(MainActivity.this, "left clicked", Toast.LENGTH_SHORT).show();
                                        break;
                                    case R.id.btn_right:
                                        Toast.makeText(MainActivity.this, "right clicked", Toast.LENGTH_SHORT).show();
                                        tDialog.dismiss();
                                        break;
                                    case R.id.tv_title:
                                        Toast.makeText(MainActivity.this, "title clicked", Toast.LENGTH_SHORT).show();
                                        break;
                                }
                            }
                        })
                        .create()   //创建TDialog
                        .show();    //展示
                break;
            /**
             * 版本升级Dialog
             */
            case R.id.upgradeDialogButton:
                new TDialog.Builder(getSupportFragmentManager())
                        .setLayoutRes(R.layout.dialog_version_upgrde)
                        .setScreenWidthAspect(MainActivity.this , 0.4f)
                        .setCancelableOutside(false)
                        .setCancelable(true)        // 默认是可取消，如果设置false 则控件点击事件获取不到
                        .addOnClickListener(R.id.tv_cancel , R.id.tv_confirm)
                        .setOnViewClickListener(new OnViewClickListener() {
                            @Override
                            public void onViewClick(BindViewHolder viewHolder, View view, TDialog tDialog) {
                                switch (view.getId()) {
                                    case R.id.tv_cancel:
                                        tDialog.dismiss();
                                        break;
                                    case R.id.tv_confirm:
                                        Toast.makeText(MainActivity.this, "开始下载新版本apk文件", Toast.LENGTH_SHORT).show();
                                        tDialog.dismiss();
                                        break;

                                }
                            }
                        })
                        .create()
                        .show();
                break;
            /**
             * 兑换弹窗
             */
            case R.id.tipsDialogButton:
                new TDialog.Builder(getSupportFragmentManager())
                        .setLayoutRes(R.layout.dialog_vb_convert)
                        .setScreenWidthAspect(MainActivity.this , 0.55f)
                        .setCancelableOutside(false)
                        .addOnClickListener(R.id.tv_jiuyuan_desc, R.id.tv_cancel, R.id.tv_confirm)
                        .setOnViewClickListener(new OnViewClickListener() {
                            @Override
                            public void onViewClick(BindViewHolder viewHolder, View view, TDialog tDialog) {
                                switch (view.getId()) {
                                    case R.id.tv_jiuyuan_desc:
                                        Toast.makeText(MainActivity.this, "进入说明界面", Toast.LENGTH_SHORT).show();
                                        break;
                                    case R.id.tv_cancel:
                                        tDialog.dismiss();
                                        break;
                                    case R.id.tv_confirm:
                                        Toast.makeText(MainActivity.this, "操作兑换", Toast.LENGTH_SHORT).show();
                                        tDialog.dismiss();
                                        break;
                                }
                            }
                        })
                        .create()
                        .show();
                break;
            /**
             * 加载中Dialog
             */
            case R.id.loadingDialogButton:
                mTDialog = new TDialog.Builder(getSupportFragmentManager())
                        .setLayoutRes(R.layout.dialog_loading)
                        .setWidth(300)
                        .setHeight(300)
                        .setCancelable(false)
                        .create()
                        .show();
                mHandler.sendEmptyMessageDelayed(0, 1000 * 5);
                break;
            /**
             * 使用setDialogView传入View 的Dialog
             */
            case R.id.dialogViewButton:
                TextView textView = new TextView(MainActivity.this);
                textView.setText("DialogView");
                textView.setTextSize(25);
                textView.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                textView.setBackgroundColor(getResources().getColor(android.R.color.holo_green_dark));


                new TDialog.Builder(getSupportFragmentManager())
                        .setLayoutRes(R.layout.dialog_loading)
                        .setWidth(400)
                        .setHeight(300)
                        .setDialogView(textView)
                        .create()
                        .show();


                break;
            /**
             * 首页广告Dialog
             */
            case R.id.homeAdviceDialogButton:

                new TDialog.Builder(getSupportFragmentManager())
                        .setLayoutRes(R.layout.dialog_home_ad)
                        .setScreenWidthAspect(MainActivity.this , 0.4f)
                        .setScreenHeightAspect(MainActivity.this , 0.8f)
                        .setOnBindViewListener(new OnBindViewListener() {
                            @Override
                            public void bindView(BindViewHolder viewHolder) {
                                // 对图片进行处理
                            }
                        })
                        .addOnClickListener(R.id.iv_close)
                        .setOnViewClickListener(new OnViewClickListener() {
                            @Override
                            public void onViewClick(BindViewHolder viewHolder, View view, TDialog tDialog) {
                                tDialog.dismiss();
                            }
                        })
                        .create()
                        .show();
                break;
            /**
             * 评价Dialog
             */
            case R.id.evaluateDialogButton:
                new TDialog.Builder(getSupportFragmentManager())
                        .setLayoutRes(R.layout.dialog_evaluate)
                        .setScreenWidthAspect(MainActivity.this,1.0f)
                        .setGravity(Gravity.BOTTOM)
                        .setOnBindViewListener(new OnBindViewListener() {
                            @Override
                            public void bindView(BindViewHolder viewHolder) {
                                final EditText editText = viewHolder.getView(R.id.editText);
                                editText.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        InputMethodManager imm = (InputMethodManager) MainActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                                        imm.showSoftInput(editText, 0);
                                    }
                                });
                            }
                        })
                        .addOnClickListener(R.id.btn_evluate)
                        .setOnViewClickListener(new OnViewClickListener() {
                            @Override
                            public void onViewClick(BindViewHolder viewHolder, View view, TDialog tDialog) {
                                EditText editText = viewHolder.getView(R.id.editText);
                                String content = editText.getText().toString().trim();
                                Toast.makeText(MainActivity.this, "评价内容:" + content, Toast.LENGTH_SHORT).show();
                                tDialog.dismiss();
                            }
                        })
                        .create()
                        .show();



                break;
            /**
             * 底部修改头像Dialog
             */
            case R.id.updateHeadDialogButton:
                new TDialog.Builder(getSupportFragmentManager())
                        .setLayoutRes(R.layout.dialog_change_avatar)
                        .setScreenWidthAspect(MainActivity.this, 1.0f)
                        .setGravity(Gravity.BOTTOM)
                        .addOnClickListener(R.id.tv_open_camera, R.id.tv_open_album, R.id.tv_cancel)
                        .setOnViewClickListener(new OnViewClickListener() {
                            @Override
                            public void onViewClick(BindViewHolder viewHolder, View view, TDialog tDialog) {
                                switch (view.getId()) {
                                    case R.id.tv_open_camera:
                                        Toast.makeText(MainActivity.this, "打开相机", Toast.LENGTH_SHORT).show();
                                        tDialog.dismiss();
                                        break;
                                    case R.id.tv_open_album:
                                        Toast.makeText(MainActivity.this, "打开相册", Toast.LENGTH_SHORT).show();
                                        tDialog.dismiss();
                                        break;
                                    case R.id.tv_cancel:
                                        tDialog.dismiss();
                                        break;
                                }
                            }
                        })
                        .create()
                        .show();

                break;
            /**
             * 列表内容使用Dialog
             */
            case R.id.listDialogButton:
                new TListDialog.Builder(getSupportFragmentManager())
                        .setHeight(400)
                        .setScreenWidthAspect(MainActivity.this , 0.4f)
                        .setGravity(Gravity.CENTER)
                        .setAdapter(new TBaseAdapter<String>(R.layout.item_simple_text , Arrays.asList(data)) {

                            @Override
                            protected void onBind(BindViewHolder holder, int position, String s) {
                                holder.setText(R.id.tv, s);
                            }
                        })
                        .setOnAdapterItemClickListener(new TBaseAdapter.OnAdapterItemClickListener<String>() {

                            @Override
                            public void onItemClick(BindViewHolder holder, int position, String s, TDialog tDialog) {
                                Toast.makeText(MainActivity.this, "click:" + s, Toast.LENGTH_SHORT).show();
                                tDialog.dismiss();
                            }
                        })
                        .create()
                        .show();


                break;
            /**
             * 底部列表内容使用Dialog
             */
            case R.id.bottomListDialogButton:
                new TListDialog.Builder(getSupportFragmentManager())
                        .setScreenWidthAspect(MainActivity.this , 1.0f)
                        .setScreenHeightAspect(MainActivity.this , 0.4f)
                        .setGravity(Gravity.BOTTOM)
                        .setAdapter(new TBaseAdapter<String>(R.layout.item_simple_text ,Arrays.asList(data)){

                            @Override
                            protected void onBind(BindViewHolder holder, int position, String s) {
                                holder.setText(R.id.tv, s);
                            }
                        })
                        .setOnAdapterItemClickListener(new TBaseAdapter.OnAdapterItemClickListener<String>() {
                            @Override
                            public void onItemClick(BindViewHolder holder, int position, String s, TDialog tDialog) {
                                Toast.makeText(MainActivity.this, "click:" + s, Toast.LENGTH_SHORT).show();
                                tDialog.dismiss();
                            }
                        })
                        .setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                Toast.makeText(MainActivity.this, "setOnDismissListener 回调", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .create()
                        .show();

                break;
            /**
             * 分享使用Dialog
             */
            case R.id.shareDialogButton:
                new TListDialog.Builder(getSupportFragmentManager())
                        .setListLayoutRes(R.layout.dialog_share_recycler , LinearLayoutManager.HORIZONTAL)
                        .setScreenWidthAspect(MainActivity.this , 1.0f)
                        .setGravity(Gravity.BOTTOM)
                        .setAdapter(new TBaseAdapter<String>(R.layout.item_share ,Arrays.asList(sharePlatform)) {
                            @Override
                            protected void onBind(BindViewHolder holder, int position, String s) {
                                holder.setText(R.id.tv, s);
                            }
                        })
                        .setOnAdapterItemClickListener(new TBaseAdapter.OnAdapterItemClickListener<String>() {

                            @Override
                            public void onItemClick(BindViewHolder holder, int position, String s, TDialog tDialog) {
                                Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
                                tDialog.dismiss();
                            }
                        })
                        .create()
                        .show();

                break;
        }
    }
}
