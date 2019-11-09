package com.cg.ibs.loanmgmt.dao;

import java.math.BigInteger;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;

import com.cg.ibs.loanmgmt.bean.LoanStatus;
import com.cg.ibs.loanmgmt.bean.TopUp;
import com.cg.ibs.loanmgmt.bean.TopUpStatus;
import com.cg.ibs.loanmgmt.util.JpaUtil;

public class TopUpDaoImpl implements TopUpDao {

	TopUp topUp = new TopUp();
	private static Logger LOGGER = Logger.getLogger(LoanMasterDaoImpl.class);
	private EntityManager entityManager;

	public TopUpDaoImpl() {
		entityManager = JpaUtil.getEntityManger();
	}

	@Override
	public TopUp applyTopUp(TopUp topUp) {
		LOGGER.info("Applied TopUp is being saved");
		entityManager.persist(topUp);
		return topUp;
	}

	public List<TopUp> getPendingTopUp() {
		LOGGER.info("Pending TopUps are being fetched from database.");
		TypedQuery<TopUp> query = entityManager.createQuery("Select t from TopUp t where t.TopUpStatus ='PENDING'",
				TopUp.class);
		List<TopUp> pendingTopUp = query.getResultList();
		return sortTopUpListByAppNum(pendingTopUp);

	}

	private List<TopUp> sortTopUpListByAppNum(List<TopUp> topUpList) {
		LOGGER.info("Loan details comparator is being defined.");
		Comparator<TopUp> loanApplicantNumComparator = new Comparator<TopUp>() {
			public int compare(TopUp o1, TopUp o2) {
				return o1.getApplicationNumber().compareTo(o2.getApplicationNumber());
			}
		};
		Collections.sort(topUpList, loanApplicantNumComparator);
		return topUpList;
	}
//NOT COMPLETED
	@Override
	public TopUp updateTopUpApprovalDao(TopUp topUp, BigInteger topUpId) {
		LOGGER.info("TopUp details are being updated after approval of topUp");
		
		topUp.setTopUpStatus(TopUpStatus.APPROVED);
		topUp.setTopUpId(topUpId);

		return topUp;

	}

	@Override
	public void updateTopUpDenialDao(TopUp topUpTemp) {
		LOGGER.info("TopUp details are being updated after denial of topUp");
		topUpTemp.setTopUpStatus(TopUpStatus.DENIED);
		entityManager.merge(topUpTemp);

	}
	
	public 

}
