package com.cg.ibs.loanmgmt.service;

import java.math.BigInteger;
import java.util.List;

import com.cg.ibs.loanmgmt.bean.BankAdmins;
import com.cg.ibs.loanmgmt.bean.CustomerBean;
import com.cg.ibs.loanmgmt.bean.LoanMaster;
import com.cg.ibs.loanmgmt.bean.LoanTypeBean;
import com.cg.ibs.loanmgmt.bean.TopUp;

public interface BankService {
	boolean verifyBankLogin(String userId, String password);

	BankAdmins getBankAdmin(String userId);

	public LoanTypeBean getLoanTypeByTypeID(Integer typeId);

	public LoanMaster updateLoanApproval(LoanMaster loanMasterTemp);
	
	public TopUp updateTopUpApproval(TopUp topUp);
	
	public void updateTopUpDenial(TopUp topUp);

	public List<LoanMaster> getPendingLoans();
	
	public List<TopUp> getPendingTopUp();

	public CustomerBean getCustomerDetailsByUci(BigInteger uci);

	public void updateLoanDenial(LoanMaster loanMasterTemp);

	public void downloadDocument(LoanMaster loanMaster);

	public List<LoanMaster> getPendingPreClosures();

	public LoanMaster updatePreClosureApproval(LoanMaster loanMasterTemp);

	public LoanMaster getLoanByApplicantNum(BigInteger applicantNum);
}
