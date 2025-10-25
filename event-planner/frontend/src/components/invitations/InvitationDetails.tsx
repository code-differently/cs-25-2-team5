import React from "react";
import { type PrivateEventDetails } from "../../types/types";

/* Props for InvitationDetails: receives a single event and a handler for RSVP actions
   handleRSVP should update the RSVP status in the parent component
   event contains all details for the selected invitation */

type InvitationDetailsProps = {
  event: PrivateEventDetails;
  handleRSVP: (status: "accepted" | "declined") => void;
};

const InvitationDetails: React.FC<InvitationDetailsProps> = ({ event, handleRSVP }) => (
  <div className="invitations-details">
    <div className="invitations-details-header">
      <h1>You have been invited!</h1>
    </div>
    <h2>{event.title}</h2>
    <p>{event.description}</p>
    <p>
      Date: {event.date} <br />
      Time: {event.time} <br />
      Location: {event.location} <br />
      {/* RSVP status is capitalized for display */}
      <strong>RSVP Status:</strong> {event.rsvpStatus.charAt(0).toUpperCase() + event.rsvpStatus.slice(1)}
    </p>
    <div className="rsvp-button-group">
      <button
        className="rsvp-button"
        onClick={() => handleRSVP("accepted")}
        disabled={event.rsvpStatus === "accepted"}
      >
        Accept
      </button>
      <button
        className="rsvp-button"
        onClick={() => handleRSVP("declined")}
        disabled={event.rsvpStatus === "declined"}
      >
        Decline
      </button>
    </div>
  </div>
);

export default InvitationDetails;
