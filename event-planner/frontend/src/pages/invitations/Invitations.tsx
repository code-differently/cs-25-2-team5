import React, { useEffect, useState } from "react";
import './Invitations.css';
import { type PrivateEventDetails } from "../../types/types";
import InvitationsList from "../../components/invitations/InvitationsList";
import InvitationDetails from "../../components/invitations/InvitationDetails";
import { useUser } from "@clerk/clerk-react";
import { fetchUser } from "../../service/apicalls";

const Invitations: React.FC = () => {
  const [selectedEventIdx, setSelectedEventIdx] = useState<number | null>(null);
  const [events, setEvents] = useState<PrivateEventDetails[]>([]);
  const { isSignedIn, user } = useUser();
  const apiUrl = import.meta.env.VITE_API_URL;
  const [backendUser, setBackendUser] = useState<any>(null);

  if (!isSignedIn) {
    alert("You must be signed in to view your invitations.");
    window.location.href = "/login";
  }

  // Fetch backend user info
  useEffect(() => {
    const fetchUserData = async () => {
      if (user) {
        const userData = await fetchUser(user.id);
        setBackendUser(userData);
      }
    };
    fetchUserData();
  }, [user]);

  // Fetch invited events once backendUser is loaded
  useEffect(() => {
    const fetchInvitedEvents = async () => {
      if (!backendUser) return;
      try {
        const response = await fetch(`${apiUrl}/users/${backendUser.id}/invites`);
        if (response.ok) {
          const data = await response.json();
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

  // Get the currently selected event object, or null if none selected
  const selectedEvent = selectedEventIdx !== null ? events[selectedEventIdx] : null;

  return (
    <div className="invitations-section">
      <div className="invitations-container">
        <h1 className="invitations-header">Event Invitations</h1>
        {/* Render the list of invitations; selecting an event sets selectedEventIdx */}
        <InvitationsList events={events} onSelect={setSelectedEventIdx} />
        {selectedEvent && backendUser && (
          <InvitationDetails
            eventInvite={selectedEvent}
            userId={backendUser.id}
          />
        )}
      </div>
    </div>
  );
};

export default Invitations;
