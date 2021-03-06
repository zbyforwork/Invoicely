package com.dzfp.dzfp.ui.activity;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.dzfp.dzfp.R;
import com.dzfp.dzfp.control.CreateCompanyControl;
import com.dzfp.dzfp.databinding.UpdateCompanyBinding;
import com.dzfp.dzfp.model.VagueCompanyBean;
import com.dzfp.dzfp.ui.BaseActivity;
import com.dzfp.dzfp.model.Card.Data;
import com.dzfp.dzfp.presenter.CreateCompanyPresenter;
import com.dzfp.dzfp.ui.fragment.HomeFragment;

import static android.content.ContentValues.TAG;


public class UpdateCompanyAty extends BaseActivity implements CreateCompanyControl.View, View.OnClickListener {
    private UpdateCompanyBinding mBinding;
    private Data companyBean;
    private CreateCompanyPresenter mCompanyPresenter;

    @Override
    public void querByCompanySHERROR(VagueCompanyBean mVagueCompanyBean) {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.update_company);
        init();
    }

    private void init() {
        companyBean = (Data) getIntent().getSerializableExtra("companyBean");
        mBinding.updateQymc.setDetails(companyBean.getNAME());
        mBinding.updateQysh.setDetails(companyBean.getNSRSBH());
        mBinding.updateQydz.setDetails(companyBean.getADDRESS());
        mBinding.updateQydh.setDetails(companyBean.getPHONE());
        mBinding.updateKhh.setDetails(companyBean.getKHH());
        mBinding.updateYhzh.setDetails(companyBean.getYHZH());
        mBinding.setOnCliclistener(this);
        mCompanyPresenter = new CreateCompanyPresenter(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_back_update:
                setResult(HomeFragment.NOT_FUNCTION);
                finish();
                break;
            case R.id.bt_bing_update:
                setUpdateCompanyState(false);
                mCompanyPresenter.updateCompany();
                break;
        }
    }

    private void setUpdateCompanyState(boolean state) {
        if (state) {
            mBinding.btBingUpdate.setBackground(getResources().getDrawable(R.drawable.buttonblue));
        } else {
            mBinding.btBingUpdate.setBackground(getResources().getDrawable(R.drawable.buttonblue_0));
        }
        mBinding.btBingUpdate.setEnabled(state);
    }

    @Override
    public void updateCompanySuccess() {
        setUpdateCompanyState(true);
        setResult(HomeFragment.UPDATE_COMPANY);
        finish();
    }

    @Override
    public void hintMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public Context getViewContext() {
        return this;
    }

    @Override
    public Data getCreateCompanyDescribe() {
        String companyName = mBinding.updateQymc.getDetails();
        String companySH = mBinding.updateQysh.getDetails();
        String companyAddress = mBinding.updateQydz.getDetails();
        String companyPhone = mBinding.updateQydh.getDetails();
        String companyKhh = mBinding.updateKhh.getDetails();
        String companyYhzh = mBinding.updateYhzh.getDetails();
        Log.i(TAG, "companyName: " + companyName);
        Log.i(TAG, "companySH: " + companySH);
        Log.i(TAG, "companyAddress: " + companyAddress);
        Log.i(TAG, "companyPhone: " + companyPhone);
        Log.i(TAG, "companyKhh: " + companyKhh);
        Log.i(TAG, "companyYhzh: " + companyYhzh);
        companyBean.setNAME(companyName);
        companyBean.setNSRSBH(companySH);
        companyBean.setADDRESS(companyAddress);
        companyBean.setPHONE(companyPhone);
        companyBean.setKHH(companyKhh);
        companyBean.setYHZH(companyYhzh);
        return companyBean;
    }


    @Override
    public void createCompanySuccess() {
        /*创建公司和修改公司用的同一个借口 修改公司不做创建工作*/
    }

    @Override
    public void queryByCompanyNameSuccess(VagueCompanyBean mVagueCompanyBean) {
        /*创建公司功能暂时不启用*/
    }

    @Override
    public void queryByCompanySHSuccess(VagueCompanyBean mVagueCompanyBean) {
        /*创建公司功能暂时不启用*/
    }

    @Override
    public String getCompanyName() {
        /*更新和创建用的同一个Presenter 这里不做操作*/
        return null;
    }

    @Override
    public String getCompanySH() {
        /*更新和创建用的同一个Presenter 这里不做操作*/
        return null;
    }

    @Override
    public void OnHttpListenerFailed(String error) {
        setUpdateCompanyState(true);
    }

}
