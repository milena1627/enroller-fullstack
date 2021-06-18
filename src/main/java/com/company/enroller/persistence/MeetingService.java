package com.company.enroller.persistence;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.company.enroller.model.Meeting;
import com.company.enroller.model.Participant;

@Component("meetingService")
public class MeetingService {
	
	
	@Autowired
	ParticipantService partcipantService;

	DatabaseConnector connector;
	// Session session;

	public MeetingService() {
		connector = DatabaseConnector.getInstance();
	}

	public Collection<Meeting> getAll() {
		String hql = "FROM Meeting";
		Query query = connector.getSession().createQuery(hql);
		return query.list();
	}

	public Meeting add(Meeting meeting) {
		Transaction transaction = this.connector.getSession().beginTransaction();
		connector.getSession().save(meeting);
		transaction.commit();
		return meeting;
	}

	public void delete(Meeting meeting) {
		Transaction transaction = this.connector.getSession().beginTransaction();
		connector.getSession().delete(meeting);
		transaction.commit();
	}

	public void addParticipant(Meeting foundMeeting, Participant participant) {
		Transaction transaction = this.connector.getSession().beginTransaction();
		connector.getSession().save(participant);
		transaction.commit();
	}
	
	public Meeting findById(long meetingId) {
		return (Meeting) connector.getSession().get(Meeting.class, meetingId);
	}
	

	/*
	 * public Meeting findByID(long id) { return (Meeting)
	 * session.get(Meeting.class, id);
	 * 
	 * }
	 */

	public Collection<Participant> getMeetingParticipants(long meetingID) {
		Meeting meeting = this.findById(meetingID);
		Collection<Participant> participants = meeting.getParticipants();
		return participants;
	}


	/* public Participant addParticipant(Meeting meeting, String username) {
	        Participant participant = participantService.findByLogin(username);
	        meeting.addParticipant(participant);
	        Transaction transaction = connector.getSession().beginTransaction();
	        connector.getSession().save(meeting);
	        transaction.commit();
	        return participant;
	    }*/

	
}
