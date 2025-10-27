import React, { useState, useEffect } from "react";
import { fetchEventById } from "../../service/apicalls";
import { updateRsvpStatus } from "../../service/apicalls";
/* Props for InvitationDetails: receives a single event and a handler for RSVP actions
   handleRSVP should update the RSVP status in the parent component
   event contains all details for the selected invitation */



const InvitationDetails: React.FC<any> = ({ eventInvite,userId }) => {
  const [event, setEvent] = useState<any>({});
  const [date,setDate] = useState<string>("");
  const [time,setTime] = useState<string>("");
  console.log("InvitationDetails received eventInvite:", eventInvite);
  useEffect(() => {
    const fetchEventDetails = async () => {
      const eventData = await fetchEventById(eventInvite.eventId);
      console.log("Fetched event data:", eventData);
      setEvent(eventData);
      const eventDate = eventData.startTime.slice(0,10);
      setDate(eventDate);
      console.log("Event date:", eventDate);
      const eventTime = eventData.startTime.slice(11,16);
      setTime(eventTime);
    };
    fetchEventDetails();
  }, [eventInvite]);

  const handleRSVP = async (status: "accepted" | "declined") => {
  if (!event) return;

  try {
    const updatedStatus = await updateRsvpStatus(
      event.id,
      userId,
      status
    );

    alert(`RSVP status updated to: ${updatedStatus}`);

    // Optionally update local state so UI reflects change instantly
    setEvent((prev: any) => ({
      ...prev,
      rsvpStatus: updatedStatus.toLowerCase(),
    }));
  } catch (error) {
    alert("Failed to update RSVP status. Please try again.");
  }
};

  return (
    <div className="invitations-details">
  `    <div className="invitations-details-header">
      <h1>You have been invited!</h1>
    </div>
    <h2>{event.title}</h2>
    <p>{event.description}</p>
    <p data-testid="rsvp-status">
      Date: {date} <br />
      Time: {time} <br />
      Location: {event.address} <br />
      <strong>RSVP Status:</strong>{" "}
{event.rsvpStatus ? event.rsvpStatus.charAt(0).toUpperCase() + event.rsvpStatus.slice(1) : "Pending"}
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
)
};

export default InvitationDetails;
