import React, { use, useEffect, useState } from "react";
import './Invitations.css';
import { type PrivateEventDetails, invitedEvents } from "../../types/types";
import InvitationsList from "../../components/invitations/InvitationsList";
import InvitationDetails from "../../components/invitations/InvitationDetails";
import { useUser } from "@clerk/clerk-react";
import { fetchUser } from "../../service/apicalls";
// Main Invitations page component
// Manages the list of invited events, selected event, and RSVP state
const Invitations: React.FC = () => {
    // Index of the currently selected event in the events array (null if none selected)
    const [selectedEventIdx, setSelectedEventIdx] = useState<number | null>(null);
    const [events, setEvents] = useState<PrivateEventDetails[]>(invitedEvents);
    const { isSignedIn, user } = useUser();
    const apiUrl = import.meta.env.VITE_API_URL;
    const [backendUser,setBackendUser] = useState<any>();
    if (!isSignedIn) {
        alert("You must be signed in to view your invitations.");
        window.location.href = "/login";
    }
    const fetchUserData = async () => {
        if (user) {
            const userData = await fetchUser(user.id);
            console.log("Fetched user data:", userData);
            setBackendUser(userData);
        }
    };
    useEffect(() => {
        fetchUserData();
    }, [user]);
    
    useEffect(() => {
        const fetchInvitedEvents = async () => {
            
            try {
                const response = await fetch(`${apiUrl}/users/${backendUser.id}/invites`);
                if (response.ok) {
                    const data = await response.json();
                    console.log("Fetched invited events:", data);
                    setEvents(data);
                } else {
                    console.error("Failed to fetch invited events", response.status);
                }
            } catch (err) {
                console.error("Error fetching invited events:", err);
            }

        };
        fetchInvitedEvents();
    }, [backendUser, apiUrl]);
    
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
    useEffect(() => {
        fetch(`${apiUrl}/users//invites`)
    }, [])

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
function useffect(arg0: () => void, arg1: never[]) {
    throw new Error("Function not implemented.");
}

