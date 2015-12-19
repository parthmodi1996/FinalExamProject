package base;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.apache.poi.ss.formula.functions.FinanceLib;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import domain.RateDomainModel;
import util.HibernateUtil;

public class RateDAL {


//	public static double getRate(int GivenCreditScore) {
//		//FinalExam - please implement		
//		// Figure out which row makes sense- return back the 
//		// right interest rate from the table based on the given credit score
//		
//		//FinalExam - obviously change the return value
//		return 0;
//	}
	
	public static double getRate(int GivenCreditScore) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		RateDomainModel rateGet = null;
		double interestRate = 0;

		try {
			tx = session.beginTransaction();
			
//			if (GivenCreditScore != rateGet.getMinCreditScore()){
//				throw new CreditScoreException();
//			}
			
			Query query = session.createQuery("from RateDomainModel where MinCreditScore = :MCS ");
			query.setParameter("MCS", GivenCreditScore);

			rateGet = (RateDomainModel) query.list().get(0);

			tx.commit();
			
			interestRate = rateGet.getInterestRate();

		} catch (IndexOutOfBoundsException ex) {
			rateGet = null;
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
//		} catch (CreditScoreException e){
//			rateGet = null;
		} finally {
			session.close();
		}
		return interestRate;
	}
	
	public static double CalculateMortgage (double houseCost, int CreditScore, int term) {
		double rate = getRate(CreditScore);
		double mortgage = FinanceLib.pmt(rate/(100*12), term*12, houseCost, 0, false);
		return (Math.round(mortgage*-100.0)/100.0);
	}
	
	public static boolean isEligible(double income, double expense, double mortgage) {
		boolean eligibility = false;
		double monthlyIncome = income/12;
		if (mortgage <= (monthlyIncome*0.36) && mortgage <= (monthlyIncome + expense)*0.18) {
			eligibility = true;
		} else {
			eligibility = false;
		}
		
		return eligibility;
	}

}