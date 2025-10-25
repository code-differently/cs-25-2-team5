import React, { useState } from "react";
import './Invitations.css';
import { type PrivateEventDetails, invitedEvents } from "../../types/types";
import InvitationsList from "../../components/invitations/InvitationsList";
import InvitationDetails from "../../components/invitations/InvitationDetails";

// Main Invitations page component
// Manages the list of invited events, selected event, and RSVP state
const Invitations: React.FC = () => {
    // Index of the currently selected event in the events array (null if none selected)
    const [selectedEventIdx, setSelectedEventIdx] = useState<number | null>(null);
    const [events, setEvents] = useState<PrivateEventDetails[]>(invitedEvents);

    // Updates RSVP status for the selected event
    // Only updates if an event is selected
    const handleRSVP = (status: "accepted" | "declined") => {
        if (selectedEventIdx === null) return;
        const updatedEvents = [...events];
        updatedEvents[selectedEventIdx] = {
            ...updatedEvents[selectedEventIdx],
            rsvpStatus: status
        };
        setEvents(updatedEvents);
    };

    // Get the currently selected event object, or null if none selected
    const selectedEvent = selectedEventIdx !== null ? events[selectedEventIdx] : null;

    return (
        <div className="invitations-section">
            <div className="invitations-container">
                <h1 className="invitations-header">Event Invitations</h1>
                {/* Render the list of invitations; selecting an event sets selectedEventIdx */}
                <InvitationsList events={events} onSelect={setSelectedEventIdx} />
                {selectedEvent && (
                    <InvitationDetails event={selectedEvent} handleRSVP={handleRSVP} />
                )}
            </div>
        </div>
    );
};

export default Invitations;
