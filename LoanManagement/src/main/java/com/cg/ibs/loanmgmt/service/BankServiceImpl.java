package com.cg.ibs.loanmgmt.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityTransaction;

import org.apache.log4j.Logger;

import com.cg.ibs.loanmgmt.bean.BankAdmins;
import com.cg.ibs.loanmgmt.bean.CustomerBean;
import com.cg.ibs.loanmgmt.bean.LoanMaster;
import com.cg.ibs.loanmgmt.bean.LoanTypeBean;
import com.cg.ibs.loanmgmt.bean.TopUp;
import com.cg.ibs.loanmgmt.dao.BankAdminsDao;
import com.cg.ibs.loanmgmt.dao.BankAdminsDaoImpl;
import com.cg.ibs.loanmgmt.dao.CustomerDao;
import com.cg.ibs.loanmgmt.dao.CustomerDaoImpl;
import com.cg.ibs.loanmgmt.dao.LoanMasterDao;
import com.cg.ibs.loanmgmt.dao.LoanMasterDaoImpl;
import com.cg.ibs.loanmgmt.dao.LoanTypeDao;
import com.cg.ibs.loanmgmt.dao.LoanTypeDaoImpl;
import com.cg.ibs.loanmgmt.dao.TopUpDao;
import com.cg.ibs.loanmgmt.dao.TopUpDaoImpl;
import com.cg.ibs.loanmgmt.util.JpaUtil;

public class BankServiceImpl implements BankService {
	private CustomerDao customerDao = new CustomerDaoImpl();
	private LoanMasterDao loanMasterDao = new LoanMasterDaoImpl();
	private TopUpDao topUpDao = new TopUpDaoImpl();
	BankAdminsDao bankAdminsDao = new BankAdminsDaoImpl();
	private LoanTypeDao loanTypeDao = new LoanTypeDaoImpl();
	private static Logger LOGGER = Logger.getLogger(BankServiceImpl.class);
	private LoanMaster loanMaster = new LoanMaster();
	private TopUp topUp = new TopUp();

	@Override
	public boolean verifyBankLogin(String userId, String password) {
		LOGGER.info("Verifying customer login details");
		boolean login = false;
		BankAdmins admin = bankAdminsDao.getAdminByUserId(userId);
		if (null != admin && password.equals(admin.getPassword())) {
			login = true;
		}
		return login;
	}

	public LoanTypeBean getLoanTypeByTypeID(Integer typeId) {
		return loanTypeDao.getLoanTypeByTypeID(typeId);
	}

	public CustomerBean getCustomerDetailsByUci(BigInteger uci) {
		return customerDao.getCustomerDetailsByUci(uci);
	}

	public BigInteger generateLoanNumber(LoanMaster loanMaster) {
		StringBuilder sb = new StringBuilder();
		sb.append(loanMaster.getAppliedDate().getYear()).append(loanMaster.getAppliedDate().getMonthValue())
				.append(loanMaster.getApplicationNumber());
		BigInteger bigInteger = new BigInteger(sb.toString());
		return bigInteger;

	}

	public void updateLoanDenial(LoanMaster loanMasterTemp) {
		EntityTransaction transaction = JpaUtil.getTransaction();
		transaction.begin();
		loanMasterDao.updateLoanDenialDao(loanMasterTemp);
		transaction.commit();

	}

	public LoanMaster updateLoanApproval(LoanMaster loanMasterTemp) {
		EntityTransaction transaction = JpaUtil.getTransaction();

		transaction.begin();
		loanMaster = loanMasterDao.updateLoanApprovalDao(loanMasterTemp, generateLoanNumber(loanMasterTemp));
		transaction.commit();
		return loanMaster;
	}

	@Override
	public BankAdmins getBankAdmin(String userId) {
		LOGGER.info("Fetching customer details");
		return bankAdminsDao.getAdminByUserId(userId);
	}

	public List<LoanMaster> getPendingLoans() {
		List<LoanMaster> listTemp = null;
		listTemp = loanMasterDao.getPendingLoans();
		return listTemp;
	}

	public List<LoanMaster> getPendingPreClosures() {
		List<LoanMaster> listTemp = null;
		listTemp = loanMasterDao.getPendingPreClosures();
		return listTemp;
	}

	public LoanMaster updatePreClosureApproval(LoanMaster loanMasterTemp) {
		EntityTransaction transaction = JpaUtil.getTransaction();
		transaction.begin();
		loanMaster = loanMasterDao.updatePreClosureApprovalDao(loanMasterTemp);
		transaction.commit();
		return loanMaster;
	}

	@Override
	public void downloadDocument(LoanMaster loanMaster) {
		byte[] content = loanMaster.getDocument();
		File dir = new File("./downloads");
		if (!dir.exists()) {
			dir.mkdir();
		}
		try (FileOutputStream outputStream = new FileOutputStream(
				dir.getPath() + "/" + loanMaster.getApplicationNumber() + ".pdf")) {

			outputStream.write(content);
			outputStream.flush();
			outputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<TopUp> getPendingTopUp() {
		List<TopUp> listPendingTopUp = null;
		listPendingTopUp = topUpDao.getPendingTopUp();
		return listPendingTopUp;
	}
	//NOT DONE
	public BigInteger generateTopUpId(TopUp topUpTemp) {
		List<TopUp> topUpList= new ArrayList<TopUp>();
		topUpList=
	
		return null;

	}

	@Override
	public TopUp updateTopUpApproval(TopUp topUpTemp) {
		EntityTransaction transaction = JpaUtil.getTransaction();
		transaction.begin();
		topUp = topUpDao.updateTopUpApprovalDao(topUpTemp, generateTopUpId(topUpTemp));
		transaction.commit();
		return topUp;
	}

	@Override
	public void updateTopUpDenial(TopUp topUp) {
		EntityTransaction transaction = JpaUtil.getTransaction();
		transaction.begin();
		topUpDao.updateTopUpDenialDao(topUp);
		transaction.commit();
	

	}

	@Override
	public LoanMaster getLoanByApplicantNum(BigInteger applicantNum) {
		EntityTransaction transaction = JpaUtil.getTransaction();
		transaction.begin();
		loanMaster = loanMasterDao.getLoanByApplicantNumber(applicantNum);
		transaction.commit();
		return null;
	}

}
