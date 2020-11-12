package com.lti.repository;

import java.util.List;
import java.time.LocalDate;
import java.time.Period;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.lti.entity.Accomodation;
import com.lti.entity.Course;
import com.lti.entity.Enroll;
import com.lti.entity.HomeList;
import com.lti.entity.NGO;
import com.lti.entity.SukanyaYojna;
import com.lti.entity.User;

@Repository
public class EmpowermentRepositoryImpl implements EmpowermentRepository {
	
	@PersistenceContext
	EntityManager em,em1 ;
	
	int maxAccomodation;
	int reservedAccomodation;
	String administratorEmail="admin@gmail.com";
	String administratorPassword="admin@123";
	
	public int getMaxAccomodation() {
		return maxAccomodation;
	}
	public void setMaxAccomodation(int maxAccomodation) {
		this.maxAccomodation = maxAccomodation;
	}
	public int getReservedAccomodation() {
		return reservedAccomodation;
	}
	public void setReservedAccomodation(int reservedAccomodation) {
		this.reservedAccomodation = reservedAccomodation;
	}

	@Transactional
	public int registerAUser(User user) {
		User u=em.merge(user) ;
		return u.getUserId();
	}

	public List<Course> viewAllCourses() {
		Query query = em.createQuery("select cour from Course cour LEFT JOIN FETCH cour.ngo" , Course.class) ;
		return query.getResultList() ;
	}

	@Transactional
	public Enroll applyEnrollmentForCourse(Enroll enroll) {
			Enroll enrollment=em.merge(enroll) ;  //use enrollemntDate as localdate.now() while testing  
			return enrollment; //while testing input Enrollment status as Approval Pending 	
	}

	@Transactional
	public int approveEnrollment(Enroll enroll) {
		int y=0;
		if(enroll.getUserEnrollmentStatus().equalsIgnoreCase("Not Approved")){
			User user = enroll.getUser();
			Period period = Period.between(user.getUserDateOfBirth() , enroll.getEnrollmentDate());
			int years=period.getYears();
			System.out.println(years);
			if((period.getYears() >= 16 )&&(user.getUserGender().equals("Female"))){
				String jpql = "UPDATE Enroll en SET en.userEnrollmentStatus = 'Approved' "	+ "WHERE en.enrollmentId=:eid";
				Query query = em.createQuery(jpql) ;
				query.setParameter("eid", enroll.getEnrollmentId());
				y=query.executeUpdate() ;
			}
			else {
				String jpql = "UPDATE Enroll en SET en.userEnrollmentStatus = 'Rejected' "	+ "WHERE en.enrollmentId=:eid";
				Query query = em.createQuery(jpql) ;
				query.setParameter("eid", enroll.getEnrollmentId());
				y=query.executeUpdate() ;
				y=0;
			}
		}
		return y;
	}

	@Transactional
	public Accomodation applyAccomodation(Accomodation accomodation) {
		Accomodation a=em.merge(accomodation) ;
		return a;
	}

	@Transactional
	public SukanyaYojna applySukanyaScheme(SukanyaYojna sukanya) {
		SukanyaYojna s=em.merge(sukanya) ;
		return s;
	}

	@Transactional
	public int approveSukanyaScheme(SukanyaYojna sukanya) {
		int y=0;
		if((sukanya.getGirlChildNationality().equals("Indian"))&&(sukanya.getGirlChildAge()<=10)) {
			String jpql = "UPDATE SukanyaYojna s SET s.applicationStatus = 'Approved' "	+ "WHERE s.schemeId=:sid";
			Query query = em.createQuery(jpql) ;
			query.setParameter("sid",sukanya.getSchemeId());
			y=query.executeUpdate() ;
		}
		else {
			String jpql = "UPDATE SukanyaYojna s SET s.applicationStatus = 'Rejected' "	+ "WHERE s.schemeId=:sid";
			Query query = em.createQuery(jpql) ;
			query.setParameter("sid", sukanya.getSchemeId());
			y=query.executeUpdate() ;
			y=0;
		}
		return y;
	}

	@Transactional
	public int registerAnNGO(NGO ngo) {
		ngo.setNgoApplicationStatus("Pending");
		NGO n=em.merge(ngo);
		return n.getNgoId();
	}

	public User logInUser(String userEmail, String userPassword) {
		String jpql="select u from User u where u.userEmail=:uel and u.userPassword=:pwd";
		TypedQuery<User> query=em.createQuery(jpql, User.class);
		query.setParameter("uel", userEmail);
		query.setParameter("pwd", userPassword);
		User loggeduser=(User)query.getSingleResult();
		return loggeduser;
	}

	@Transactional
	public int addACourse(Course course) {
		Course c=em.merge(course) ;
		return c.getCourseId();
	}

	@Transactional
	public int editACourse(Course course) {
		Course c=em.merge(course) ;
		return c.getCourseId();
	}

	@Transactional
	public int deleteACourse(int courseId) {
		String jpql="delete from Course c where c.courseId=:cid";
		Query query=em.createQuery(jpql);
		query.setParameter("cid",courseId);
		int x=query.executeUpdate();
		return x;
	}


	public Course findACourse(int courseId) {
		return em.find(Course.class, courseId);
	}

	
	public List<Enroll> viewAllEnrollment() {
		Query query = em.createQuery("select e from Enroll e" , Enroll.class);
		return query.getResultList() ;
	}

	public List<User> viewAllUsers() {
		Query query = em.createQuery("select u from User u LEFT JOIN FETCH u.ngo" , User.class) ;
		return query.getResultList() ;
	}

	public User findAUser(int userId) {
		return em.find(User.class,userId);
	}

	public NGO findAnNGO(int ngoId) {
		return em.find(NGO.class,ngoId);
	}

	public Enroll findAnEnrollment(int enrollmentId) {
		return em.find(Enroll.class, enrollmentId);
	}

	public List<Enroll> findNotApprovedEnroll() {
		String jpql="select e from Enroll e where e.userEnrollmentStatus=:stat";
		Query query=em.createQuery(jpql, Enroll.class);
		query.setParameter("stat","Not Approved");
		return query.getResultList();
	}

	public List<SukanyaYojna> viewNotApprovedSukanya() {
		String jpql="select s from SukanyaYojna s where s.applicationStatus=:stat";
		Query query=em.createQuery(jpql, SukanyaYojna.class);
		query.setParameter("stat","Not Approved");
		return query.getResultList();
	}
	
	public Accomodation findAccomodation(int accomodationId) {
		return em.find(Accomodation.class,accomodationId);
	}
	
	public SukanyaYojna findASukanya(int schemeId) {
		return em.find(SukanyaYojna.class, schemeId);
	}

	public NGO logInNGO(String ngoEmail, String ngoPassword) {
		String jpql="select n from NGO n where n.ngoEmail=:nel and n.ngoPassword=:pwd and n.ngoApplicationStatus=:stat ";
		TypedQuery<NGO> query=em.createQuery(jpql, NGO.class);
		query.setParameter("nel", ngoEmail);
		query.setParameter("pwd", ngoPassword);
		query.setParameter("stat","Approved");
		return query.getSingleResult();
	}
	
	public int logInAdmin(String adminEmail, String adminPassword) {
		if((adminEmail.equals(administratorEmail))&&(adminPassword.equals(administratorPassword)))
			return 1;
		else
			return 0;
	}
	
	public List<Course> viewCourseBySector(String trainingSector) {
		String jpql="select c from Course c where c.trainingSector=:tsc";
		TypedQuery<Course> query=em.createQuery(jpql, Course.class);
		query.setParameter("tsc", trainingSector);
		return query.getResultList();
	}

	public List<Course> viewCourseByNGO(int ngoId) {
		String jpql="select c from Course c where ngo_Id=:nid";
		TypedQuery<Course> query=em.createQuery(jpql, Course.class);
		query.setParameter("nid", ngoId);
		return query.getResultList();
	}

	public List<NGO> viewAllNGO() {
		Query query = em.createQuery("select n from NGO n" , NGO.class) ;
		return query.getResultList();
	}
	
	@Transactional
	public int approveNGO(NGO ngo) {
		int y=0;
		String jpql = "UPDATE NGO n SET n.ngoApplicationStatus = 'Approved' "	+ "WHERE n.ngoId=:nid";
		Query query = em.createQuery(jpql) ;
		query.setParameter("nid",ngo.getNgoId());
		y=query.executeUpdate() ;
		return y;
	}

	@Transactional
	public int updateUserPassword(String userEmail, String newPassword) {
		int y=0;
		String jpql = "UPDATE User u SET u.userPassword=:npsw"+" WHERE u.userEmail=:uel";
		Query query = em.createQuery(jpql) ;
		query.setParameter("uel",userEmail);
		query.setParameter("npsw",newPassword);
		y=query.executeUpdate();
		return y;
	}

	@Transactional
	public int updateNGOPassword(String ngoEmail, String newPassword) {
		int y=0;
		String jpql = "UPDATE NGO n SET n.ngoPassword=:npsw"+" WHERE n.ngoEmail=:nel";
		Query query = em.createQuery(jpql) ;
		query.setParameter("nel",ngoEmail);
		query.setParameter("npsw",newPassword);
		y=query.executeUpdate();
		return y;
	}

	public int updateAdminPassword(String email,String newPassword) {
		administratorPassword=newPassword;
		return 1;
	}
	
	public boolean isUserPresent(String email) {
		return (Long)
                em
                .createQuery("select count(u.userId) from User u where u.userEmail=:uel")
                .setParameter("uel", email)
                .getSingleResult() == 1 ? true : false;
	}
	
	public boolean isNGOPresent(String email) {
		return (Long)
                em
                .createQuery("select count(n.ngoId) from NGO n where n.ngoEmail=:nel")
                .setParameter("nel", email)
                .getSingleResult() == 1 ? true : false;
	}
	
	public boolean isCoursePresent(String courseName) {
		return (Long)
                em
                .createQuery("select count(c.courseId) from Course c where c.courseName=:cname")
                .setParameter("cname", courseName)
                .getSingleResult() == 1 ? true : false;
	}
	
	public NGO findNGOByEmail(String email) {
		String jpql="select n from NGO n where n.ngoEmail=:nel";
		TypedQuery<NGO> query=em.createQuery(jpql, NGO.class);
		query.setParameter("nel", email);
		return query.getSingleResult();
	}
	
	public boolean isAlreadyRegisteredWithNgo(int userId) {
		return (Long)
                em
                .createQuery("select count(u.userId) from User u where u.userId=:uid and ngo_id IS NULL")
                .setParameter("uid", userId)
                .getSingleResult() == 1 ? true : false;
	}
	@Transactional
	public int deleteANonRegisteredUser(int userId) {
		String jpql="delete from User u where u.userId=:uid";
		Query query=em.createQuery(jpql);
		query.setParameter("uid",userId);
		int x=query.executeUpdate();
		return x;
	}
	public boolean isAlreadyEnrolled(int userId,int courseId) {
		String jpql = "select count(e.enrollmentId) from Enroll e where user_id=:uid and course_id=:cid";
		Query query = em.createQuery(jpql) ;
		query.setParameter("uid",userId);
		query.setParameter("cid",courseId);
		return (Long)
                query.getSingleResult() == 1 ? true : false;
	}

	public List<NGO> viewAllApprovedNGO() {
		String jpql="select n from NGO n where n.ngoApplicationStatus=:stat";
		Query query=em.createQuery(jpql, NGO.class);
		query.setParameter("stat","Approved");
		return query.getResultList();
	}
	public boolean isAlreadyAccomodated(int userId) {
		String jpql = "select count(a.accomodationId) from Accomodation a where user_id=:uid";
		Query query = em.createQuery(jpql);
		query.setParameter("uid",userId);
		return (Long)
                query.getSingleResult() == 1 ? true : false;
	}
	@Transactional
	public int addHome(HomeList home) {
		HomeList h=em.merge(home);
		return h.getCityId();
	}
	@Transactional
	public int updateHome(HomeList home, int rooms, int delete) {
		int oldrooms=home.getNumberOfRooms();
		int newrooms;
		if(delete==1) {
			newrooms=oldrooms-rooms;
		}
		else {
			newrooms=oldrooms+rooms;
		}
		int id=home.getCityId();
		int y=0;
		String jpql = "UPDATE HomeList h SET h.numberOfRooms=:nr"+" WHERE h.cityId=:cid";
		Query query = em.createQuery(jpql) ;
		query.setParameter("nr",newrooms);
		query.setParameter("cid",id);
		y=query.executeUpdate();
		return y;
	}
	public HomeList findHomeByCityId(int cityId) {
		return em.find(HomeList.class, cityId);
	}
	public boolean isHomeAlreadyPresent(String city) {
		String jpql = "select count(h.cityId) from HomeList h where h.city=:c";
		Query query = em.createQuery(jpql);
		query.setParameter("c",city);
		return (Long)
                query.getSingleResult() == 1 ? true : false;
	}
	public List<HomeList> viewAllHomes() {
		Query query = em.createQuery("select h from HomeList h" , HomeList.class) ;
		return query.getResultList();
	}
	public boolean isAlreadyASukanya(int userId) {
		String jpql = "select count(s.schemeId) from SukanyaYojna s where user_id=:uid";
		Query query = em.createQuery(jpql);
		query.setParameter("uid",userId);
		return (Long)
                query.getSingleResult() == 1 ? true : false;
	}
	public List<SukanyaYojna> viewSukanya() {
		String jpql="select s from SukanyaYojna s";
		Query query=em.createQuery(jpql, SukanyaYojna.class);
		return query.getResultList();
	}

	public List<Accomodation> viewAccomodation() {
		String jpql="select a from Accomodation a";
		Query query=em.createQuery(jpql, Accomodation.class);
		return query.getResultList();
	}
	
	public List<Accomodation> viewNotApprovedWorkingPhysical() {
		String jpql="select a from Accomodation a where a.applicationStatus=:stat and a.anyPhysicalChallenges=:phy and a.employmentStatus=:emp";
		Query query=em.createQuery(jpql, Accomodation.class);
		query.setParameter("stat","Not Approved");
		query.setParameter("phy","Yes");
		query.setParameter("emp","Employed");
		return query.getResultList();
	}
	
	public List<Accomodation> viewNotApprovedWorkingST() {
		String jpql="select a from Accomodation a where a.applicationStatus=:stat and a.caste=:cs and a.employmentStatus=:emp";
		Query query=em.createQuery(jpql, Accomodation.class);
		query.setParameter("stat","Not Approved");
		query.setParameter("cs","ST");
		query.setParameter("emp","Employed");
		return query.getResultList();
	}
	
	public List<Accomodation> viewNotApprovedWorkingSC() {
		String jpql="select a from Accomodation a where a.applicationStatus=:stat and a.caste=:cs and a.employmentStatus=:emp";
		Query query=em.createQuery(jpql, Accomodation.class);
		query.setParameter("stat","Not Approved");
		query.setParameter("cs","SC");
		query.setParameter("emp","Employed");
		return query.getResultList();
	}
	
	public List<Accomodation> viewNotApprovedWorkingOBC() {
		String jpql="select a from Accomodation a where a.applicationStatus=:stat and a.caste=:cs and a.employmentStatus=:emp";
		Query query=em.createQuery(jpql, Accomodation.class);
		query.setParameter("stat","Not Approved");
		query.setParameter("cs","OBC");
		query.setParameter("emp","Employed");
		return query.getResultList();
	}
	
	public List<Accomodation> viewNotApprovedWorkingEWS() {
		String jpql="select a from Accomodation a where a.applicationStatus=:stat and a.caste=:cs and a.employmentStatus=:emp";
		Query query=em.createQuery(jpql, Accomodation.class);
		query.setParameter("stat","Not Approved");
		query.setParameter("cs","EWS");
		query.setParameter("emp","Employed");
		return query.getResultList();
	}
	
	public List<Accomodation> viewNotApprovedWorkingHusband() {
		String jpql="select a from Accomodation a where a.applicationStatus=:stat and a.husbandResidenceName<>a.userResidenceName and a.employmentStatus=:emp";
		Query query=em.createQuery(jpql, Accomodation.class);
		query.setParameter("stat","Not Approved");
		query.setParameter("emp","Employed");
		return query.getResultList();
	}
	
	public List<Accomodation> viewNotApprovedWorkingMetropolitan() {
		String jpql="select a from Accomodation a where a.applicationStatus=:stat and a.grossIncomePerMonth<'50000' and a.areaOfResidence=:res and a.employmentStatus=:emp";
		Query query=em.createQuery(jpql, Accomodation.class);
		query.setParameter("stat","Not Approved");
		query.setParameter("emp","Employed");
		query.setParameter("res","Metropolitan");
		return query.getResultList();
	}
	
	public List<Accomodation> viewNotApprovedWorkingNonMetropolitan() {
		String jpql="select a from Accomodation a where a.applicationStatus=:stat and a.grossIncomePerMonth<'35000' and a.employmentStatus=:emp";
		Query query=em.createQuery(jpql);
		query.setParameter("stat","Not Approved");
		query.setParameter("emp","Employed");
		return query.getResultList();
	}
	
	public List<Accomodation> viewNotApprovedNotWorking() {
		String jpql="select a from Accomodation a where a.applicationStatus=:stat and a.employmentStatus=:emp";
		Query query=em.createQuery(jpql, Accomodation.class);
		query.setParameter("stat","Not Approved");
		query.setParameter("emp","Unemployed");
		return query.getResultList();
	}
	
	@Transactional
	public int approveAccomodation(Accomodation acc) {
		if(acc.getUser().getUserGender().equalsIgnoreCase("Female")) {
			int y1=0,y2=0;
			String city=acc.getAccomodationCity();
			System.out.println(city);
			int rooms=acc.getNumberOfBoyChildBelow5()+acc.getNumberOfGirlChildBelow18()+1;
			String jpql1="select h from HomeList h where h.city=:c";
			Query query1=em.createQuery(jpql1);
			query1.setParameter("c",city);
			HomeList home=(HomeList)query1.getSingleResult();
			if(home==null) {
				return 0;
			}
			int actualroom=home.getNumberOfRooms()-rooms;
			if(actualroom<0) {
				return 0;
			}
			home.setNumberOfRooms(actualroom);
			
			String jpql = "UPDATE Accomodation a SET a.applicationStatus = 'Approved' "	+ "WHERE a.accomodationId=:aid";
			Query query = em.createQuery(jpql) ;
			query.setParameter("aid",acc.getAccomodationId());
			y1=query.executeUpdate();
			
			String jpql2 = "UPDATE HomeList h SET h.numberOfRooms=:nr "	+ "WHERE h.city=:cit";
			Query query2 = em.createQuery(jpql2) ;
			query2.setParameter("nr",home.getNumberOfRooms());
			query2.setParameter("cit",city);
			y2=query2.executeUpdate();
			
			if((y1==1)&&(y2==1))
				return 1;
			else
				return 0;
		}
		return 0;
	}
	
	@Transactional
	public int rejectAccomodation(Accomodation acc) {
		int y=0;
		String jpql = "UPDATE Accomodation a SET a.applicationStatus = 'Rejected' "	+ "WHERE a.accomodationId=:aid";
		Query query = em.createQuery(jpql) ;
		query.setParameter("aid",acc.getAccomodationId());
		y=query.executeUpdate() ;
		return y;
	}
}
