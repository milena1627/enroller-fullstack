package com.company.enroller.controllers;

import com.company.enroller.model.Meeting;
import com.company.enroller.model.Participant;
import com.company.enroller.persistence.MeetingService;
import com.company.enroller.persistence.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/api/meetings")
public class MeetingRestController {

    @Autowired
    MeetingService meetingService;

    @Autowired
    ParticipantService participantService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<?> getMeetings() {

        Collection<Meeting> meetings = meetingService.getAll();
        return new ResponseEntity<Collection<Meeting>>(meetings, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getMeeting(@PathVariable("id") long id) {
        Meeting meeting = meetingService.findById(id);
        if (meeting == null) {
            return new ResponseEntity<String>("Unable to find meeting with id" + id, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Meeting>(meeting, HttpStatus.OK);
    }
    
 // POST http://localhost:9000/api/meetings
    @RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<?> addMeeting(@RequestBody Meeting meeting) {
		if (meetingService.findById(meeting.getId()) != null) {
			return new ResponseEntity<String>(
					"Unable to create. Participant with login '" + meeting.getId() + "' already exists",
					HttpStatus.CONFLICT);

		}
		meetingService.add(meeting);
		return new ResponseEntity<Meeting>(meeting, HttpStatus.CREATED);

	}
    
    
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
 	public ResponseEntity<?> deleteMeeting(@PathVariable("id") long id) {
 		Meeting meeting = meetingService.findById(id);
 		if (meeting == null) {
 			return new ResponseEntity(HttpStatus.NOT_FOUND);
 		}
 		meetingService.delete(meeting);
 		return new ResponseEntity<Meeting>(meeting, HttpStatus.NO_CONTENT);
 	}
	
	
	
	
	 @RequestMapping(value = "/{id}", method = RequestMethod.POST)
	    public ResponseEntity<?> addParticipantToMeeting(@PathVariable("id") long id,
	            @RequestBody Participant participant) {
	        Meeting meeting = meetingService.findById(id);
	        if (meeting == null) {
	            return new ResponseEntity<String>("Unable to add participant as meeting not exists",
	                    HttpStatus.NOT_FOUND);
	        }
	        meeting.addParticipant(participant);
	        return new ResponseEntity<Meeting>(meeting, HttpStatus.OK);
	    }
	 
	 
	 @RequestMapping(value = "/{id}/participants", method = RequestMethod.GET)
	    public ResponseEntity<?> getMeetingsParticipants(@PathVariable("id") long id) {
	        Meeting meeting = meetingService.findById(id);
	        if (meeting == null) {
	            return new ResponseEntity<String>("Unable to find meeting with id '" + id, HttpStatus.NOT_FOUND);
	        }
	        Collection<Participant> participants = meeting.getParticipants();
	        return new ResponseEntity<Collection<Participant>>(participants, HttpStatus.OK);
	    }
	 
	 @RequestMapping(value = "/{id}/participants", method = RequestMethod.DELETE)
	    public ResponseEntity<?> removeParticicpantFromMeeting(@PathVariable("id") long id,
	            @RequestBody Participant participant) {
	        Meeting meeting = meetingService.findById(id);
	        if (meeting == null) {
	            return new ResponseEntity<String>("Unable to remove participant to meeting that does not exist",
	                    HttpStatus.NOT_FOUND);
	        }
	        meeting.removeParticipant(participant);
	        return new ResponseEntity<Meeting>(meeting, HttpStatus.OK);
	        
	      
	 }
	 
}
