import React, { useState } from "react";
import './Invitations.css';
import { type PrivateEventDetails, invitedEvents } from "../../types/types";

const Invitations: React.FC = () => {
    // State to track selected event
    const [selectedEventIdx, setSelectedEventIdx] = useState<number | null>(null);
    const [events, setEvents] = useState<PrivateEventDetails[]>(invitedEvents);

    // Handles RSVP action by updating the event's RSVP status when user clicks Accept or Decline
    const handleRSVP = (status: "accepted" | "declined") => {
        if (selectedEventIdx === null) return;
        const updatedEvents = [...events];
        updatedEvents[selectedEventIdx] = {
            ...updatedEvents[selectedEventIdx],
            rsvpStatus: status
        };
        setEvents(updatedEvents);
    };

    const selectedEvent = selectedEventIdx !== null ? events[selectedEventIdx] : null;

    return (
        <div className="invitations-section">
            <div className="invitations-container">
                <h1 className="invitations-header">Event Invitations</h1>
                {/* List of invited events */}
                <ul className="invitations-list">
                    {events.map((event, idx) => (
                        <li key={idx}>
                            <button onClick={() => setSelectedEventIdx(idx)}>
                                {event.title}
                            </button>
                        </li>
                    ))}
                </ul>
                {/* Show details when an event is selected */}
                {selectedEvent && (
                    <div className="invitations-details">
                        <div className="invitations-details-header">
                            <h1>You have been invited!</h1>
                        </div>
                        <h2>{selectedEvent.title}</h2>
                        <p>{selectedEvent.description}</p>
                        <p>
                            Date: {selectedEvent.date} <br />
                            Time: {selectedEvent.time} <br />
                            Location: {selectedEvent.location} <br />
                            <strong>RSVP Status:</strong> {selectedEvent.rsvpStatus.charAt(0).toUpperCase() + selectedEvent.rsvpStatus.slice(1)}
                        </p>
                        <div className="rsvp-button-group">
                            <button
                                className="rsvp-button"
                                onClick={() => handleRSVP("accepted")}
                                disabled={selectedEvent.rsvpStatus === "accepted"}
                            >
                                Accept
                            </button>
                            <button
                                className="rsvp-button"
                                onClick={() => handleRSVP("declined")}
                                disabled={selectedEvent.rsvpStatus === "declined"}
                            >
                                Decline
                            </button>
                        </div>
                    </div>
                )}
            </div>
        </div>
    );
};

export default Invitations;
