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
import com.lti.entity.NGO;
import com.lti.entity.SukanyaYojna;
import com.lti.entity.User;

@Repository
public class EmpowermentRepositoryImpl implements EmpowermentRepository {
	
	@PersistenceContext
	EntityManager em ;
	
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
	public void applyEnrollmentForCourse(Enroll enroll, Course course) {
		if(course == null){
			System.out.println("Course not Found. Enter courseId from the List/Table");
		}
		else{
			enroll.setUserEnrollmentStatus("Not Approved");
			enroll.setCourse(course);
			enroll.setEnrollmentDate(LocalDate.now());
			em.merge(enroll) ;  //use enrollemntDate as localdate.now() while testing  
			System.out.println("User enrolled."); //while testing input Enrollment status as Approval Pending 	
		}
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
		}
		return y;
	}

	@Transactional
	public void applyAccomodation(Accomodation accomodation) {
		em.merge(accomodation) ;
		System.out.println("Accomodation request sent.");
	}

	@Transactional
	public int approveAccomodation(Accomodation acc) {
		System.out.println(maxAccomodation);
		System.out.println(reservedAccomodation);
		int flag=0;
		int requiredBeds=acc.getNumberOfBoyChildBelow5()+acc.getNumberOfGirlChildBelow18()+1;
		String caste=acc.getCaste();
		int allocatedBeds=0;
		if(acc.getEmploymentStatus().equals("Employed")) {
			if(caste.equals("OBC")||caste.equals("EFW")||caste.equals("ST")) {
				flag=1;
				if(reservedAccomodation>requiredBeds)
					allocatedBeds=requiredBeds;
				else
					allocatedBeds=reservedAccomodation;
				reservedAccomodation=reservedAccomodation-allocatedBeds;
			}
			if(acc.getAnyPhysicalChallenges().equals("Yes")) {
				flag=1;
				if(reservedAccomodation>requiredBeds)
					allocatedBeds=requiredBeds;
				else
					allocatedBeds=reservedAccomodation;
				reservedAccomodation=reservedAccomodation-allocatedBeds;
			}
			if(acc.getUser().getUserMaritalStatus().equals("Married")) {
				String city=acc.getUserResidenceName();
				String hcity=acc.getHusbandResidenceName();
				if(city.equals(hcity)==false) {
					flag=1;
					if(maxAccomodation>requiredBeds)
						allocatedBeds=requiredBeds;
					else
						allocatedBeds=maxAccomodation;
					maxAccomodation=maxAccomodation-allocatedBeds;
				}
				else
					flag=2;
			}
			if(acc.getAreaOfResidence().equals("Metropolitan")) {
				if(acc.getGrossIncomePerMonth()<50000) {
					flag=1;
					if(maxAccomodation>requiredBeds)
						allocatedBeds=requiredBeds;
					else
						allocatedBeds=maxAccomodation;
					maxAccomodation=maxAccomodation-allocatedBeds;
				}
			}
			if(acc.getAreaOfResidence().equals("Metropolitan")==false) {
				if(acc.getGrossIncomePerMonth()<35000) {
					flag=1;
					if(maxAccomodation>requiredBeds)
						allocatedBeds=requiredBeds;
					else
						allocatedBeds=maxAccomodation;
					maxAccomodation=maxAccomodation-allocatedBeds;
				}
			}
			if(flag==2)
				flag=0;
			else
				flag=1;
		}
		if(flag==1) {
			String jpql="UPDATE Accomodation a SET a.applicationStatus=:stat WHERE a.accomodationId=:aid";
			Query query=em.createQuery(jpql);
			query.setParameter("aid",acc.getAccomodationId());
			query.setParameter("stat","Approved");
			query.executeUpdate();
			return allocatedBeds;
		}
		return 0;
	}

	@Transactional
	public void applySukanyaScheme(SukanyaYojna sukanya) {
		em.merge(sukanya) ;
		System.out.println("Application request for Sukanya Samriddhi Yojna sent.");
	}

	@Transactional
	public int approveSukanyaScheme(SukanyaYojna sukanya) {
		int y=0;
		if((sukanya.getGirlChildNationality().equals("India"))&&(sukanya.getGirlChildAge()<=10)) {
			String jpql = "UPDATE SukanyaYojna s SET s.applicationStatus = 'Approved' "	+ "WHERE s.schemeId=:sid";
			Query query = em.createQuery(jpql) ;
			query.setParameter("sid",sukanya.getSchemeId());
			y=query.executeUpdate() ;
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
		Query query = em.createQuery("select e from Enroll e where e.userEnrollmentStatus=:stat" , Enroll.class) ;
		query.setParameter("stat","Approved");
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
		query.setParameter("stat","Pending");
		return query.getResultList();
	}

	public List<Accomodation> viewNotApprovedAccomodation() {
		String jpql="select a from Accomodation a where a.applicationStatus=:stat";
		Query query=em.createQuery(jpql, Accomodation.class);
		query.setParameter("stat","Pending");
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
		Query query = em.createQuery("select n from NGO n LEFT JOIN FETCH n.courses" , NGO.class) ;
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
}
