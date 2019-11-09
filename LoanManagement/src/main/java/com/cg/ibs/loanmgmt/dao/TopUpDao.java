package com.cg.ibs.loanmgmt.dao;

import java.math.BigInteger;
import java.util.List;

import com.cg.ibs.loanmgmt.bean.LoanMaster;
import com.cg.ibs.loanmgmt.bean.TopUp;

public interface TopUpDao {
	TopUp applyTopUp(TopUp topUp);
	
	TopUp updateTopUpApprovalDao(TopUp topUp, BigInteger topUpId);
	
	void updateTopUpDenialDao(TopUp topUpTemp);
	
	List<TopUp> getPendingTopUp();

}
