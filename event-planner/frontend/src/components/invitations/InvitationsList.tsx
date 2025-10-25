import React from "react";
import { type PrivateEventDetails } from "../../types/types";

// Props for InvitationsList: receives an array of events and a callback for selection
// onSelect should be called with the index of the selected event

type InvitationsListProps = {
  events: PrivateEventDetails[];
  onSelect: (idx: number) => void;
};

const InvitationsList: React.FC<InvitationsListProps> = ({ events, onSelect }) => (
  <ul className="invitations-list">
    {/* Render a button for each event; clicking sets the selected event in the parent */}
    {events.map((event, idx) => (
      <li key={idx}>
        <button onClick={() => onSelect(idx)}>{event.title}</button>
      </li>
    ))}
  </ul>
);

export default InvitationsList;
